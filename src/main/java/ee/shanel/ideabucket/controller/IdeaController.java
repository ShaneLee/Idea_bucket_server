package ee.shanel.ideabucket.controller;

import ee.shanel.ideabucket.model.Idea;
import ee.shanel.ideabucket.service.IdeaService;
import ee.shanel.ideabucket.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class IdeaController
{
    private final IdeaService ideaService;

    private final UserService userService;

    @GetMapping("/rest/v1/ideas")
    public Flux<Idea> ideas(final Authentication authentication,
                            @RequestParam(required = false) final String category)
    {
        return userService.findUser(authentication.getName())
                .flatMapMany(val -> ideaService.findIdeas(val, category));
    }

    @PostMapping("/rest/v1/submitIdea")
    public Mono<Idea> submitIdea(final Authentication authentication, @RequestBody final Idea idea)
    {
        return userService.findUser(authentication.getName())
                .flatMap(val -> ideaService.put(idea, val));
    }

    @DeleteMapping("/rest/v1/delete")
    public Mono<Void> delete(final Authentication authentication, @RequestBody final String id)
    {
        // TODO need to think how to make this more robust - any user can delete any user's ids atm
        return ideaService.delete(id);
    }
}
