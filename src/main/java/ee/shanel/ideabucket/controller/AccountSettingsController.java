package ee.shanel.ideabucket.controller;

import ee.shanel.ideabucket.model.settings.AccountSettings;
import ee.shanel.ideabucket.service.AccountSettingsService;
import ee.shanel.ideabucket.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AccountSettingsController
{
    private final AccountSettingsService accountSettingsService;

    private final UserService userService;

    @PutMapping("/rest/v1/accountSettings")
    public Mono<ResponseEntity<AccountSettings>> putAccountSettings(
            final Authentication authentication,
            @RequestBody final AccountSettings accountSettings)
    {
        return Mono.just(authentication.getName())
                .flatMap(userService::findUser)
                .flatMap(val -> accountSettingsService.put(accountSettings, val))
                .map(ResponseEntity::ok);
    }

    @GetMapping("/rest/v1/accountSettings")
    public Mono<ResponseEntity<AccountSettings>> getAccountSettings(final Authentication authentication)
    {
        return Mono.just(authentication.getName())
                .flatMap(userService::findUser)
                .flatMap(accountSettingsService::findAccountSettings)
                .map(ResponseEntity::ok);
    }
}
