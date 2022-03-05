package ee.shanel.ideabucket.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder(toBuilder = true)
@ToString
@EqualsAndHashCode
public class TestUser
{
    private String id;
    private String email;
    private String token;
    private String role;
}
