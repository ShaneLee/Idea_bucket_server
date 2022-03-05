package ee.shanel.ideabucket.controller;

import ee.shanel.ideabucket.service.LogoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LogoutController
{
    private final LogoutService logoutService;

    @GetMapping("/rest/v1/logout")
    public Mono<ResponseEntity<Object>> logout(final Authentication authentication)
    {
        return logoutService.logout(authentication.getName())
            .flatMap(LogoutController::hasLogoutSucceeded);
    }

    private static Mono<ResponseEntity<Object>> hasLogoutSucceeded(final Boolean success)
    {
        return Mono.just(success)
                .filter(Boolean.TRUE::equals)
                .flatMap(val -> Mono.just(ResponseEntity.ok().build()))
                .defaultIfEmpty(ResponseEntity.status(401).build());
    }
}
