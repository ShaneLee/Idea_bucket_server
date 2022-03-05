package ee.shanel.ideabucket.controller;

import ee.shanel.ideabucket.model.LoginRequest;
import ee.shanel.ideabucket.security.AuthenticationService;
import ee.shanel.ideabucket.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController
{
    private final AuthenticationService authenticationService;

    private final LoginService loginService;

    @PostMapping("/rest/v1/login")
    public Mono<ResponseEntity<Object>> login(@RequestBody final LoginRequest loginRequest)
    {
        return Mono.just(loginRequest)
                .filter(val -> EmailValidator.getInstance().isValid(val.getEmail()))
                .doOnNext(val -> LOG.info("User login request {}", val))
                .flatMap(loginService::login)
                .flatMap(LoginController::hasLoginSucceeded)
                .switchIfEmpty(Mono.defer(() -> Mono.fromSupplier(() -> loginRequest)
                        .doOnNext(val -> LOG.warn("Invalid e-mail submitted {}", val.getEmail())))
                        .flatMap(val -> Mono.just(ResponseEntity.badRequest().build())));
    }

    @GetMapping("/rest/v1/login/expired/{token}")
    public Mono<ResponseEntity<Boolean>> isLoginExpired(@PathVariable("token") String token)
    {
        return authenticationService.authenticate(token)
                .map(val -> ResponseEntity.ok().body(Boolean.FALSE))
                .onErrorResume(err -> Mono.just(ResponseEntity.ok().body(Boolean.TRUE)));
    }

    @GetMapping("/rest/v1/login/{token}")
    public Mono<ResponseEntity<String>> login(@PathVariable("token") String token)
    {
        return authenticationService.authenticate(token)
                .map(val -> ResponseEntity.ok().body(token))
                .onErrorResume(err -> Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }

    private static Mono<ResponseEntity<Object>> hasLoginSucceeded(final Boolean success)
    {
        return Mono.just(success)
                .filter(Boolean.TRUE::equals)
                .flatMap(val -> Mono.just(ResponseEntity.ok().build()))
                .defaultIfEmpty(ResponseEntity.status(401).build());
    }
}
