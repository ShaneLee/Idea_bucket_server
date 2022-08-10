Feature: Subscriptions

  Background: the following user is registered
    Given the id generator returns the following ids in order
      | clem-generated-id |
    And the following registration request is received with a 200 status
      | name | email                    |
      | clem | clemfandango@example.com |
    And the user clemfandango@example.com receives a token

  Scenario: A user subscribes and receives a refreshed token with the role subscribed
    When the user with token <received> subscribes and receives the token <subscribed>
      | token          |
      | external-token |
    And the token <subscribed> contains the role BUCKET_SUBSCRIBED

  Scenario: A subscribed user can add more than the default limit of ideas per day
    Given the id generator returns the following ids in order
      | idea1 |
      | idea2 |
      | idea3 |
      | idea4 |
      | idea5 |
      | idea6 |
    When the user with token <received> subscribes and receives the token <subscribed>
      | token          |
      | external-token |
    And the following ideas are saved via REST with the token <subscribed>
      | idea  | category |
      | idea1 | test     |
      | idea2 | test     |
      | idea3 | test     |
      | idea4 | test     |
      | idea5 | test     |
      | idea6 | test     |
    Then the user requests the ideas endpoint with the token <subscribed>
    And the following ideas are returned
      | idea  | category |
      | idea1 | test     |
      | idea2 | test     |
      | idea3 | test     |
      | idea4 | test     |
      | idea5 | test     |
      | idea6 | test     |
