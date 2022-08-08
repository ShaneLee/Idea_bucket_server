package ee.shanel.ideabucket.converter;

import ee.shanel.ideabucket.model.entity.SupportEntity;
import ee.shanel.ideabucket.model.support.Support;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;

class SupportEntityToSupportConverterTest
{
    private static final String ID = "ID";

    private static final String SUBJECT = "SUBJECT";

    private static final String USER_EMAIL = "USER_EMAIL";

    private static final String USER_NAME = "USER_NAME";

    private static final String USER_ID = "USER_ID";

    private static final String BODY = "BODY";

    private static final String CATEGORY = "CATEGORY";

    private static final Date NOW = Date.from(Instant.EPOCH);

    private SupportEntityToSupportConverter subject;

    @BeforeEach
    void beforeEach()
    {
        subject = new SupportEntityToSupportConverter();
    }

    @Test
    void itConverts()
    {
        final var source = SupportEntity.builder()
                .id(ID)
                .subject(SUBJECT)
                .username(USER_NAME)
                .userEmail(USER_EMAIL)
                .userId(USER_ID)
                .body(BODY)
                .category(CATEGORY)
                .timeReceived(NOW)
                .build();

        final var expected = Support.builder()
                .id(ID)
                .subject(SUBJECT)
                .username(USER_NAME)
                .userEmail(USER_EMAIL)
                .userId(USER_ID)
                .body(BODY)
                .category(CATEGORY)
                .timeReceived(NOW)
                .build();

        Assertions.assertEquals(expected, subject.convert(source));
    }
}
