package ee.shanel.ideabucket.controller;

import ee.shanel.ideabucket.exception.UserAlreadyExistsException;
import ee.shanel.ideabucket.model.Registration;
import ee.shanel.ideabucket.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RegistrationController
{
    private final RegistrationService registrationService;

    @PostMapping("/rest/v1/register")
    public Mono<ResponseEntity<Object>> register(@RequestBody final Registration registration)
    {
        return Mono.just(registration)
                .map(RegistrationController::sanitise)
                .filter(val -> StringUtils.isNotBlank(registration.getName()))
                .filter(val -> EmailValidator.getInstance().isValid(val.getEmail()))
                .flatMap(registrationService::register)
                .doOnNext(val -> LOG.info("User registered {}", val))
                .flatMap(val -> Mono.just(ResponseEntity.ok().build()))
                .onErrorReturn(UserAlreadyExistsException.class, ResponseEntity.status(403).body("User already exists"))
                .switchIfEmpty(Mono.defer(() -> Mono.fromSupplier(() -> registration)
                        .doOnNext(val -> LOG.warn("Invalid registration submitted {}", val)))
                        .flatMap(val -> Mono.just(ResponseEntity.badRequest().build())));
    }

    private static Registration sanitise(final Registration registration)
    {
        return registration.toBuilder()
                .name(StringEscapeUtils.escapeHtml4(registration.getName()))
                .build();
    }
}
