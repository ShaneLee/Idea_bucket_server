package ee.shanel.ideabucket.generator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DefaultIdGenerator implements IdGenerator
{
    @Override
    public String generate()
    {
        return UUID.randomUUID().toString();
    }
}
