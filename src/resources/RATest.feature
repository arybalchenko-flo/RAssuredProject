Feature: I want to get project list
# Scenario: Get project list
#   Given GET request on "/projects" link with header and status code 200
#     | HEADER  | Authorization                            | Bearer 24805392219f3cbb91525b3c1f50f56463147f60 |
#
#  Scenario: Post request to create project
#    Given POST request on "/projects" link with authorization header, project name and status code 200
#      | HEADER | Authorization | Bearer 24805392219f3cbb91525b3c1f50f56463147f60 |
#      | BODY   |               | JsonsToRequests/createProject                    |

#  Scenario: Get request to project
#    Given GET request on "/projects" link with data from "name" and save in variable names and check status code 200
#      | HEADER | Authorization | Bearer 24805392219f3cbb91525b3c1f50f56463147f60  |
#      | BODY   |               | JsonsToRequests/createProject                    |

  Scenario: Get request to get project info with check json info
    Given GET request on "/projects/2283908538" link and check JSON file JsonsToCheck/getProject and check status code 200
      | HEADER | Authorization | Bearer 24805392219f3cbb91525b3c1f50f56463147f60  |

  Scenario: Get request to get project info and fix mistake
    Given GET request on "/projects/2283908538" link, rename key thisIsNotId to the new name id and check JSON file JsonsToCheck/getProjectWithMistake and check status code 200
      | HEADER | Authorization | Bearer 24805392219f3cbb91525b3c1f50f56463147f60  |
