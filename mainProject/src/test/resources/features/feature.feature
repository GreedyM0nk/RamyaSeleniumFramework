Feature: Login

  @test1
  Scenario Outline: Login
    Given I am a amazon user login with '<UserName>'
    Examples:
      | UserName                  |
      | swargam.ramya11@gmail.com |
