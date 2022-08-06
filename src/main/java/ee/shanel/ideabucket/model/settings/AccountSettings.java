package ee.shanel.ideabucket.model.settings;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class AccountSettings
{
    private String userId;
    private Boolean emailsEnabled;
    private EmailFrequency emailFrequency;

    @JsonIgnore
    public AccountSettings withUserId(final String id)
    {
        return this.toBuilder()
                .userId(id)
                .build();
    }
}
