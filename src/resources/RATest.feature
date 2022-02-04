Feature: I want to get project list
 Scenario: Get project list
   Given GET request on "/projects" link with header and status code 200
     | HEADER  | Authorization                            | Bearer 24805392219f3cbb91525b3c1f50f56463147f60 |