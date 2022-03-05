Feature: Account Settings Feature

  Scenario: A user receives their settings
    Given the id generator returns the following ids in order
      | clem-generated-id |
    When the following registration request is received with a 200 status
      | name | email                    |
      | clem | clemfandango@example.com |
    Then the user clemfandango@example.com receives a token
    Given the following account settings are saved
      | userId            | emailsEnabled |
      | clem-generated-id | true          |
    And the user requests the account settings endpoint
      | token      |
      | <received> |
    Then the following account settings are returned
      | userId            | emailsEnabled |
      | clem-generated-id | true          |

  Scenario: A user registers and receives the default settings
    Given the id generator returns the following ids in order
      | clem-generated-id |
    When the following registration request is received with a 200 status
      | name | email                    |
      | clem | clemfandango@example.com |
    Then the user clemfandango@example.com receives a token
    And the user requests the account settings endpoint
      | token      |
      | <received> |
    Then the following account settings are returned
      | userId            | emailsEnabled |
      | clem-generated-id | false         |

  Scenario: user settings are updated via REST
    Given the id generator returns the following ids in order
      | clem-generated-id |
    When the following registration request is received with a 200 status
      | name | email                    |
      | clem | clemfandango@example.com |
    Then the user clemfandango@example.com receives a token
    Given the following account settings are saved via REST with the token <received>
      | emailsEnabled |
      | true          |
    And the user requests the account settings endpoint
      | token      |
      | <received> |
    Then the following account settings are returned
      | userId            | emailsEnabled |
      | clem-generated-id | true          |
