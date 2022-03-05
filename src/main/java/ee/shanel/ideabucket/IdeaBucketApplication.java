package ee.shanel.ideabucket;

import ee.shanel.ideabucket.config.EmailProperties;
import ee.shanel.ideabucket.config.MongoDbProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(value = {
        EmailProperties.class,
        MongoDbProperties.class
    }
)
@SpringBootApplication
public class IdeaBucketApplication
{
    public static void main(final String[] args)
    {
        SpringApplication.run(IdeaBucketApplication.class, args);
    }
}
