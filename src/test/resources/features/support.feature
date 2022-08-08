Feature: Support feature

  Scenario: A user sends a support request
    Given the id generator returns the following ids in order
      | clem-generated-id  |
      | support-request-id |
    When the following registration request is received with a 200 status
      | name | email                    |
      | clem | clemfandango@example.com |
    And the user clemfandango@example.com receives a token
    When the user with token <received> sends the following support request
      | subject           | body               |
      | Something's wrong | There was an error |
    Then the following support request is found in the repository
      | id                 | subject           | body               | username | userEmail                | userId            |
      | support-request-id | Something's wrong | There was an error | clem     | clemfandango@example.com | clem-generated-id |
    And the following support request is sent
      | body               |
      | There was an error |

