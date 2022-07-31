@tdd
Feature: Subscriptions

  Background: the following user is registered
    Given the following registration request is received with a 200 status
      | name | email                    |
      | clem | clemfandango@example.com |
    And the user clemfandango@example.com receives a token

  Scenario: An authenticated user can access a protected endpoint
    Given the user requests the /template endpoint with the method GET the status is 403
      | email                    | token      |
      | clemfandango@example.com | <received> |
    When the user with token <received> subscribes
      | token          |
      | external-token |
    And the user requests the /template endpoint with the method GET the status is 200
      | email                    | token      |
      | clemfandango@example.com | <received> |
