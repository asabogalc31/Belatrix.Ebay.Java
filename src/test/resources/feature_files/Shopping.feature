@shopping
Feature: Shopping a product on Ebay page

  @shoes
  Scenario Outline: Shopping Puma shoes on Ebay
    Given I want to do an advanced search with "<keyword>" and "<category>"
    And I want to select size "<size>"
    Then I want to order the results by ascendant price
    When I want to order the results by descendant price

    Examples: 
      | keyword | category                   | size |
      | Puma    | Ropa, calzado y accesorios |   10 |
