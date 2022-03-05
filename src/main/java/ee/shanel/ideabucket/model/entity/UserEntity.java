package ee.shanel.ideabucket.model.entity;

import ee.shanel.ideabucket.model.authentication.IdeaBucketRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document
public class UserEntity
{
    @Id
    private String id;
    @Indexed
    private String token;
    private String name;
    @Indexed
    private String email;
    private IdeaBucketRole role;
}
