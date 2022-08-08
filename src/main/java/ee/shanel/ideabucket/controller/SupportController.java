package ee.shanel.ideabucket.controller;

import ee.shanel.ideabucket.model.support.Support;
import ee.shanel.ideabucket.service.SupportService;
import ee.shanel.ideabucket.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SupportController
{
    private final SupportService supportService;

    private final UserService userService;

    @PostMapping("/rest/v1/supportRequest")
    public Mono<ResponseEntity<Object>> supportRequest(final Authentication authentication,
                                                       @RequestBody final Support support)
    {
        return Mono.just(authentication.getName())
                .doOnNext(val -> LOG.info("Support request received"))
                .flatMap(userService::findUser)
                .flatMap(val -> supportService.process(support, val))
                .map(val -> ResponseEntity.ok().build())
                .defaultIfEmpty(ResponseEntity.internalServerError().build());
    }

}
