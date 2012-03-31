Feature: iTunes Library
  This feature checks that we're able to load and walk an iTunes "XML" library.

  Scenario Outline: Loading
    Given the iTunes file <filename>
    When searching for the artist <artist>
    Then there should be <albumcount> albums found

    Examples: Single digits
      | filename   | artist    | albumcount |
      | itunes.xml | Meshuggah |          5 |
