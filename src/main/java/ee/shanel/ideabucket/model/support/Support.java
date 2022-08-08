package ee.shanel.ideabucket.model.support;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Support
{
    private String id;
    private String subject;
    private String userEmail;
    private String username;
    private String userId;
    private String body;
    private String category;
    private Date timeReceived;
}
