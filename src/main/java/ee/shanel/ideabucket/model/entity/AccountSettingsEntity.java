package ee.shanel.ideabucket.model.entity;

import ee.shanel.ideabucket.model.settings.EmailFrequency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document
public class AccountSettingsEntity
{
    @Id
    private String userId;
    private Boolean emailsEnabled;
    private EmailFrequency emailFrequency;
}
