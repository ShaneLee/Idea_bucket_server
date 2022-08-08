package ee.shanel.ideabucket.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document
public class SupportEntity
{
    private String id;
    private String subject;
    private String userEmail;
    private String username;
    private String userId;
    private String body;
    private String category;
    @Indexed(expireAfter = "P30D")
    private Date timeReceived;
}
