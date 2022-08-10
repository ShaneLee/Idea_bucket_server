package ee.shanel.ideabucket.model.authentication;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class IdeaBucketRole
{
    public static final String ROLE_PREFIX = "ROLE_";

    private final String name;

    public String getUserRole()
    {
        return StringUtils.join(ROLE_PREFIX, name);
    }

    public static IdeaBucketRole standard()
    {
        return new IdeaBucketRole("BUCKET_STANDARD");
    }

    public static IdeaBucketRole subscribed()
    {
        return new IdeaBucketRole("BUCKET_SUBSCRIBED");
    }
}
