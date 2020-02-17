package page_objects;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import core.PageObjectBase;

public class Home extends PageObjectBase{
	@FindBy(how = How.ID, using = "gh-as-a")
	private WebElement lbl_AdvancedSearch;

	@FindBy(how = How.ID, using = "gh-title")
	private WebElement lbl_Title;
	
	/**
	 * Constructs a new instance, that initialize the elements of the home page
	 */
	public Home() {
		PageFactory.initElements(driver, this);
	}
	
	/**
	 * Select the option advanced search
	 */
	public void selectAdvancedSearch() {
		try {
			lbl_AdvancedSearch.click();
			assertTrue(lbl_Title.getText().contains("Búsqueda avanzada"));
		} catch (Exception | AssertionError e) {
			String errorMessage = String.format("An error ocurred while selecting the advanced search", e.getMessage());
			throw new Error(errorMessage);
		}
	}
}
