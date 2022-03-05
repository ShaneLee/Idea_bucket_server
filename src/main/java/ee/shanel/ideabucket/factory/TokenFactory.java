package ee.shanel.ideabucket.factory;

import ee.shanel.ideabucket.model.User;

public interface TokenFactory
{
    String create(User user);
}
