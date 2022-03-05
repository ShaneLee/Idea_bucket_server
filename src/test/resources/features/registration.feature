Feature: Registration Feature

  Scenario: A new user registers and gets a token sent
    Given the id generator returns the following ids in order
      | clem-generated-id |
    When the following registration request is received with a 200 status
      | name | email                    |
      | clem | clemfandango@example.com |
    Then the user clemfandango@example.com receives a token
    And the user requests the /rest/v1/ideas endpoint with the method GET the status is 200
      | email                    | token      |
      | clemfandango@example.com | <received> |

  Scenario: A new user registers
    Given the id generator returns the following ids in order
      | clem-generated-id |
    When the following registration request is received with a 200 status
      | name | email                    |
      | clem | clemfandango@example.com |
    Then the following login token request receives a valid token
      | email                    |
      | clemfandango@example.com |
    And the user requests the /rest/v1/ideas endpoint with the method GET the status is 200
      | email                    | token      |
      | clemfandango@example.com | <received> |

  Scenario: A new user registers with a space in their name
    Given the id generator returns the following ids in order
      | clem-generated-id |
    When the following registration request is received with a 200 status
      | name          | email                    |
      | clem fandango | clemfandango@example.com |
    Then the following login token request receives a valid token
      | email                    |
      | clemfandango@example.com |
    And the user requests the /rest/v1/ideas endpoint with the method GET the status is 200
      | email                    | token      |
      | clemfandango@example.com | <received> |

  Scenario: A user attempts to register with an existing email address
    Given the id generator returns the following ids in order
      | clem-generated-id   |
      | clem-generated-id-2 |
    When the following registration request is received with a 200 status
      | name | email                    |
      | clem | clemfandango@example.com |
    Then the following registration request is received with a 401 status
      | name | email                    |
      | clem | clemfandango@example.com |

  Scenario: A user attempts to register with an invalid email address
    When the following registration request is received with a 400 status
      | name | email   |
      | clem | example |

  Scenario: A user attempts to register without an email address
    When the following registration request is received with a 400 status
      | name |
      | clem |

  Scenario: A user attempts to register without a name
    When the following registration request is received with a 400 status
      | email                    |
      | clemfandango@example.com |
