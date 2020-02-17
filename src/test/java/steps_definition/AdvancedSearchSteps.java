package steps_definition;

import com.google.gson.JsonObject;

import io.cucumber.java8.En;
import page_objects.AdvancedSearch;
import page_objects.Home;

public class AdvancedSearchSteps implements En {
	Home home;
	AdvancedSearch advancedSearch;
	
	public AdvancedSearchSteps() {		
		Given("I want to do an advanced search with {string} and {string}", (String keyWord, String category) -> {
			home = new Home();
			home.selectAdvancedSearch();
			
			JsonObject articleInfo = new JsonObject();
			articleInfo.addProperty("kewWord", keyWord);
			articleInfo.addProperty("category", category);
			advancedSearch = new AdvancedSearch();
			advancedSearch.searchArticle(articleInfo);
		});
	}
}
