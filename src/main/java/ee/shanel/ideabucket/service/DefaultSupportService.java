package ee.shanel.ideabucket.service;

import ee.shanel.ideabucket.factory.SupportEmailFactory;
import ee.shanel.ideabucket.factory.SupportFactory;
import ee.shanel.ideabucket.model.User;
import ee.shanel.ideabucket.model.entity.SupportEntity;
import ee.shanel.ideabucket.model.support.Support;
import ee.shanel.ideabucket.repository.SupportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DefaultSupportService implements SupportService
{
    private final ConversionService conversionService;

    private final SenderService senderService;

    private final SupportEmailFactory supportEmailFactory;

    private final SupportFactory supportFactory;

    private final SupportRepository supportRepository;

    @Override
    public Mono<Support> process(final Support support, final User user)
    {
        return Mono.just(support)
                .map(val -> supportFactory.create(val, user))
                .mapNotNull(val -> conversionService.convert(val, SupportEntity.class))
                .flatMap(supportRepository::save)
                .mapNotNull(val -> conversionService.convert(val, Support.class))
                .flatMap(val -> senderService.send(supportEmailFactory.create(val)).thenReturn(val));
    }
}
