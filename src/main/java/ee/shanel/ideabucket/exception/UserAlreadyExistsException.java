package ee.shanel.ideabucket.exception;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = true)
public class UserAlreadyExistsException extends Exception
{
    private static final long serialVersionUID = 1L;

    public UserAlreadyExistsException(final String message)
    {
        super(String.format("User already exists for e-mail %s", message));
    }
}
