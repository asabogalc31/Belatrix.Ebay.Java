package page_objects;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.google.gson.JsonObject;

import core.PageObjectBase;

public class AdvancedSearch extends PageObjectBase {
	@FindBy(how = How.ID, using = "_nkw")
	private WebElement txt_KeyWord;

	@FindBy(how = How.ID, using = "e1-1")
	private WebElement lst_Category;

	@FindBy(how = How.XPATH, using = "//div[@class='adv-s mb space-top']//button[text()='Buscar']")
	private WebElement btn_BtnSearch;
	
	@FindBy(how = How.ID, using = "Results")
	private WebElement div_Results;
	
	/**
	 * Constructs a new instance, that initialize the elements of the advance search
	 * page
	 */
	public AdvancedSearch() {
		PageFactory.initElements(driver, this);
	}

	/**
	 * Search the article using keyword and category information
	 * 
	 * @param article The information of the article to be searched
	 */
	public void searchArticle(JsonObject article) {
		// Enter the key word
		txt_KeyWord.sendKeys(article.get("kewWord").getAsString());

		// Select the category option
		new Select(lst_Category).selectByVisibleText(article.get("category").getAsString());

		// Click on search button
		btn_BtnSearch.click();
		
		// Assertion
		assertTrue(div_Results.isDisplayed());		
	}
}
