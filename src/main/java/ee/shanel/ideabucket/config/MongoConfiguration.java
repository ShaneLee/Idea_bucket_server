package ee.shanel.ideabucket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoClientFactoryBean;

@Configuration
public class MongoConfiguration
{
    @Bean
    ReactiveMongoClientFactoryBean mongoClientFactory(final MongoDbProperties properties)
    {
        final var mongo = new ReactiveMongoClientFactoryBean();
        mongo.setHost(properties.getHost());
        return mongo;
    }
}

