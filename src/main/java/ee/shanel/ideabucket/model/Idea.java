package ee.shanel.ideabucket.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.OffsetDateTime;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Idea
{
    private String id;
    private String userId;
    private String idea;
    private String category;
    private OffsetDateTime timeSubmitted;
}
