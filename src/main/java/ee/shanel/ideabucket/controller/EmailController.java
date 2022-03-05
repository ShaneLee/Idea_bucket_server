package ee.shanel.ideabucket.controller;

import ee.shanel.ideabucket.model.MailingList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
public class EmailController
{
    @PostMapping("/rest/v1/mailingList")
    public Mono<ResponseEntity<Object>> mailingList(@RequestBody final MailingList mailingList)
    {
        return Mono.just(mailingList)
                .filter(val -> EmailValidator.getInstance().isValid(val.getEmail()))
                .doOnNext(val -> LOG.info("Email subscribed {}", val.getEmail()))
                .flatMap(val -> Mono.just(ResponseEntity.ok().build()))
                .switchIfEmpty(Mono.defer(() -> Mono.fromSupplier(() -> mailingList)
                    .doOnNext(val -> LOG.warn("Invalid e-mail submitted {}", val.getEmail())))
                    .flatMap(val -> Mono.just(ResponseEntity.badRequest().build())));
    }
}
