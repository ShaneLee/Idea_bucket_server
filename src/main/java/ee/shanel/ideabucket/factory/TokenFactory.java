package ee.shanel.ideabucket.factory;

import ee.shanel.ideabucket.model.Token;
import ee.shanel.ideabucket.model.User;

public interface TokenFactory
{
    Token create(User user);
}
