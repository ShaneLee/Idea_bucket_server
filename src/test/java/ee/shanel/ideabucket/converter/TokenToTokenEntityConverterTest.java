package ee.shanel.ideabucket.converter;

import ee.shanel.ideabucket.model.Token;
import ee.shanel.ideabucket.model.entity.TokenEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

class TokenToTokenEntityConverterTest
{
    private static final Clock CLOCK = Clock.fixed(Instant.parse("2020-06-04T14:30:30.000Z"), ZoneId.of("UTC"));

    private static final Date DATE = Date.from(CLOCK.instant());

    private static final String TOKEN = "TOKEN";

    private static final String EMAIL = "EMAIL";

    private static final String USER_ID = "USER_ID";

    private TokenToTokenEntityConverter subject;

    @BeforeEach
    void beforeEach()
    {
        subject = new TokenToTokenEntityConverter();
    }

    @Test
    void itConverts()
    {
        final var token = Token.builder()
                .token(TOKEN)
                .email(EMAIL)
                .userId(USER_ID)
                .creationTime(DATE)
                .build();

        final var expected = TokenEntity.builder()
                .token(TOKEN)
                .email(EMAIL)
                .userId(USER_ID)
                .creationTime(DATE)
                .build();

        Assertions.assertEquals(expected, subject.convert(token));
    }
}
