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

      # TODO test for authenication after token expires

  Scenario: An anyone can access a public endpoint
    Given the following registration request is received with a 200 status
      | name | email                    |
      | clem | clemfandango@example.com |
    And the user clemfandango@example.com receives a token
    And the user requests the mailing list endpoint the status is 200
      | email                    | token      |
      | clemfandango@example.com | <received> |
      | dave@example.com         |            |
