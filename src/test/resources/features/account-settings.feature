Feature: Account Settings Feature

  Scenario: A user receives their settings
    Given the id generator returns the following ids in order
      | clem-generated-id |
    When the following registration request is received with a 200 status
      | name | email                    |
      | clem | clemfandango@example.com |
    Then the user clemfandango@example.com receives a token
    Given the following account settings are saved
      | userId            | emailsEnabled | emailFrequency |
      | clem-generated-id | true          | Weekly         |
    And the user requests the account settings endpoint
      | token      |
      | <received> |
    Then the following account settings are returned
      | userId            | emailsEnabled | emailFrequency |
      | clem-generated-id | true          | Weekly         |

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
      | userId            | emailsEnabled | emailFrequency |
      | clem-generated-id | false         | Monthly        |

  Scenario: user settings are updated via REST and those not changed are not updated
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
      | userId            | emailsEnabled | emailFrequency |
      | clem-generated-id | true          | Monthly        |
