package ee.shanel.ideabucket.security;

import ee.shanel.ideabucket.model.Token;

import java.util.Map;

public interface TokenSecurityService
{
    String generate(Map<String, Object> claims);

    Token decode(String token);

    Boolean validate(String token);
}
