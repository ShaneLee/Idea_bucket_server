package ee.shanel.ideabucket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ee.shanel.ideabucket.model.authentication.IdeaBucketRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User
{
    private String id;
    private String token;
    private String name;
    private String email;
    private IdeaBucketRole role;

    @JsonIgnore
    public User withToken(final String val)
    {
        return this.toBuilder()
                .token(val)
                .build();
    }

    @JsonIgnore
    public boolean isUserStandard()
    {
        return StringUtils.equals(role.getUserRole(), IdeaBucketRole.standard().getUserRole());
    }

    @JsonIgnore
    public User withStandard()
    {
        return this.toBuilder()
                .role(IdeaBucketRole.standard())
                .build();
    }
}
