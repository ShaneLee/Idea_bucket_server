Feature: Authentication Feature

  Background:
    Given the id generator returns the following ids in order
      | clem-generated-id |

  Scenario: An authenticated user can access a protected endpoint
    Given the following registration request is received with a 200 status
      | name | email                    |
      | clem | clemfandango@example.com |
    And the user clemfandango@example.com receives a token
    When the user requests the /rest/v1/ideas endpoint with the method GET the status is 200
      | email                    | token      |
      | clemfandango@example.com | <received> |
    When the user requests the /rest/v1/logout endpoint with the method GET the status is 200
      | email                    | token      |
      | clemfandango@example.com | <received> |

  Scenario: An authenticated user can access a protected endpoint but can't after logging out
    Given the following registration request is received with a 200 status
      | name | email                    |
      | clem | clemfandango@example.com |
    And the user clemfandango@example.com receives a token
    And the user requests the /rest/v1/ideas endpoint with the method GET the status is 200
      | email                    | token      |
      | clemfandango@example.com | <received> |
    When the authenticated users logout
    And the user requests the /rest/v1/logout endpoint with the method GET the status is 403
      | email                    | token      |
      | clemfandango@example.com | <received> |

  Scenario: A user gets a token following a login after a token expiry
    Given the id generator returns the following ids in order
      | clem-generated-id |
    When the following registration request is received with a 200 status
      | name | email                    |
      | clem | clemfandango@example.com |
    Then the user clemfandango@example.com receives a token
    And the user requests the /rest/v1/ideas endpoint with the method GET the status is 200
      | email                    | token      |
      | clemfandango@example.com | <received> |
    When the token <received> is deleted
    And the following login token request receives a valid token
      | email                    |
      | clemfandango@example.com |
    Then the user clemfandango@example.com receives a token with key <second-token>
    And the user requests the /rest/v1/ideas endpoint with the method GET the status is 200
      | email                    | token          |
      | clemfandango@example.com | <second-token> |

  # TODO test for authenication after token expires via the database

  Scenario: An anyone can access a public endpoint
    Given the following registration request is received with a 200 status
      | name | email                    |
      | clem | clemfandango@example.com |
    And the user clemfandango@example.com receives a token
    And the user requests the mailing list endpoint the status is 200
      | email                    | token      |
      | clemfandango@example.com | <received> |
      | dave@example.com         |            |

  Scenario: An unregistered user can't login
    Given the following login token request receives the status 401
      | name         | email                    |
      | Steven Toast | yesICanHearYou@example.com |
    And the user clemfandango@example.com doesn't receive a token
