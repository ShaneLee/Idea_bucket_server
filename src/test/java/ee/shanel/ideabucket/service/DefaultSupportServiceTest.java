package ee.shanel.ideabucket.service;


import ee.shanel.ideabucket.factory.SupportEmailFactory;
import ee.shanel.ideabucket.factory.SupportFactory;
import ee.shanel.ideabucket.model.User;
import ee.shanel.ideabucket.model.entity.SupportEntity;
import ee.shanel.ideabucket.model.support.Support;
import ee.shanel.ideabucket.repository.SupportRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class DefaultSupportServiceTest
{
    private static final SupportEntity SUPPORT_ENTITY = createSupportEntity();

    private static final Support SUPPORT = createSupport();

    private static final User USER = createUser();

    private static final MailMessage MESSAGE = new SimpleMailMessage();

    @Mock
    private ConversionService mockConversionService;

    @Mock
    private SenderService mockSenderService;

    @Mock
    private SupportEmailFactory mockSupportEmailFactory;

    @Mock
    private SupportFactory mockSupportFactory;

    @Mock
    private SupportRepository mockSupportRepository;

    private DefaultSupportService subject;

    @BeforeEach
    void setup()
    {
        subject = new DefaultSupportService(
                mockConversionService,
                mockSenderService,
                mockSupportEmailFactory,
                mockSupportFactory,
                mockSupportRepository
        );
    }

    @Test
    void itProcesses()
    {
        Mockito.when(mockSupportFactory.create(Mockito.any(), Mockito.any()))
                .thenReturn(SUPPORT);
        Mockito.when(mockConversionService.convert(Mockito.any(), Mockito.eq(SupportEntity.class)))
                .thenReturn(SUPPORT_ENTITY);
        Mockito.when(mockSupportRepository.save(Mockito.any()))
                .thenReturn(Mono.just(SUPPORT_ENTITY));
        Mockito.when(mockConversionService.convert(Mockito.any(), Mockito.eq(Support.class)))
                .thenReturn(SUPPORT);
        Mockito.when(mockSupportEmailFactory.create(Mockito.any()))
                .thenReturn(MESSAGE);
        Mockito.when(mockSenderService.send(Mockito.any()))
                .thenReturn(Mono.empty());

        subject.process(SUPPORT, USER)
                .as(StepVerifier::create)
                .assertNext(res -> Assertions.assertEquals(SUPPORT, res))
                .verifyComplete();
    }

    private static Support createSupport()
    {
        return Support.builder()
                .build();
    }

    private static SupportEntity createSupportEntity()
    {
        return SupportEntity.builder()
                .build();
    }

    private static User createUser()
    {
        return User.builder()
                .build();
    }

}
