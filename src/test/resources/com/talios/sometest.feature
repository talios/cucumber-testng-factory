Feature: iTunes Library
  This feature checks that we're able to load and walk an iTunes "XML" library.

  Scenario: Loading
    Given the iTunes file itunes.xml
    When playing music
    Then noise should be heard

