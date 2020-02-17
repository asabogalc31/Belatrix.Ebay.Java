package steps_definition;

import io.cucumber.java8.En;
import page_objects.SearchResults;

public class SearchResultsSteps implements En {
	SearchResults searchResults;
	
	public SearchResultsSteps() {
		Given("I want to select size {string}", (String size)->{
			searchResults = new SearchResults();
			searchResults.selectSize(size);
		});
		
		Then("I want to order the results by ascendant price", ()->{
			searchResults = new SearchResults();
			searchResults.orderByAscendantPrice();
		});
		
		When("I want to order the results by descendant price", ()->{
			searchResults = new SearchResults();
			searchResults.orderByDescendantPrice();
		});
	}
}
