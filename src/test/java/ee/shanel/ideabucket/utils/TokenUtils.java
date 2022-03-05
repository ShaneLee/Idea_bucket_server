package ee.shanel.ideabucket.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TokenUtils
{
    private static final String RECEIVED = "<received>";

    public static String getReceived()
    {
        return RECEIVED;
    }

    public static String expand(final String tokenString, final String replacement)
    {
        return StringUtils.replace(tokenString, RECEIVED, replacement);
    }
}
