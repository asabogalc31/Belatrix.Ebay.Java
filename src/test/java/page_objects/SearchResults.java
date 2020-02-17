package page_objects;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import core.PageObjectBase;

public class SearchResults extends PageObjectBase {
	@FindBy(how = How.ID, using = "_nkw")
	private WebElement txt_KeyWord;

	@FindBy(how = How.XPATH, using = "//div[@class=\"clt\"]/h1/span[@class='rcnt']")
	private WebElement spn_ResultsNumber;

	@FindBy(how = How.CLASS_NAME, using = "sort-menu-container")
	private WebElement div_Order;

	@FindBy(how = How.XPATH, using = "//ul[@id='SortMenu']/li//a[text()='Precio + Envío: más bajo primero']")
	private WebElement a_AscendantOption;

	@FindBy(how = How.XPATH, using = "//ul[@id='SortMenu']/li//a[text()='Precio + Envío: más alto primero']")
	private WebElement a_DescendantOption;

	private By by_ResultsArticles = By.xpath("//ul[@id='ListViewInner']/li[position() < 6]");

	/**
	 * Constructs a new instance, that initialize the elements of the search results
	 * page
	 */
	public SearchResults() {
		PageFactory.initElements(driver, this);
	}

	/**
	 * Select the size option specified
	 * 
	 * @param size The size to search
	 */
	public void selectSize(String size) {
		try {
			String xpathSizeElement = "//div[@id='e1-22']/div[@class='pnl-b pad-bottom']/div/a/span[@class='cbx' and text()='"
					+ size + "'] /preceding-sibling::input";

			WebElement inputSizeElement = driver.findElement(By.xpath(xpathSizeElement));
			inputSizeElement.click();

			// Print the number of results
			String resultsMessage = String.format("---The results number is: %s", spn_ResultsNumber.getText());
			System.out.println(resultsMessage);
		} catch (Exception | AssertionError e) {
			String errorMessage = String.format("An error ocurred while selecting the size", e.getMessage());
			throw new Error(errorMessage);
		}
	}

	/**
	 * Order the results by ascendant price
	 */
	public void orderByAscendantPrice() {
		try {
			// Display the order list
			div_Order.click();

			// Select option order by ascendant price
			a_AscendantOption.click();

			List<WebElement> articleList = driver.findElements(by_ResultsArticles);
			Double previousTotalArticle = null;
			for (WebElement article : articleList) {
				Double priceArticle;
				Double totalArticle;
				String name = article.findElement(By.tagName("h3")).getText();
				String price = article.findElement(By.xpath("ul/li[@class='lvprice prc']/span")).getText();

				String shipping = null;
				Double shippingArticle = 0.0;
				if (checkShippingElementExist(article)) {
					shipping = article.findElement(By.xpath("ul/li[@class='lvshipping']//span[@class='fee']"))
							.getText();
					shippingArticle = getPriceNumber(shipping);
				}

				priceArticle = getPriceNumber(price);
				totalArticle = priceArticle + shippingArticle;

				// Assertions
				if (previousTotalArticle != null) {
					assertTrue(previousTotalArticle <= totalArticle);
				} else {
					assertTrue(0 < totalArticle);
				}

				// Print the first five results
				String infoArticle = String.format("The article %s has a total price of $%s", name,
						totalArticle.toString());
				System.out.println(infoArticle);

				previousTotalArticle = totalArticle;
			}
		} catch (Exception | AssertionError e) {
			String errorMessage = String.format("An error ocurred while ordering by ascendant price", e.getMessage());
			throw new Error(errorMessage);
		}
	}

	/**
	 * Order the results by descendant price
	 */
	public void orderByDescendantPrice() {
		try {
			// Display the order list
			div_Order.click();

			// Select option order by ascendant price
			a_DescendantOption.click();

			List<WebElement> articleList = driver.findElements(by_ResultsArticles);
			Double previousTotalArticle = null;
			for (WebElement article : articleList) {
				Double priceArticle;
				Double totalArticle;
				String name = article.findElement(By.tagName("h3")).getText();
				String price = article.findElement(By.xpath("ul/li[@class='lvprice prc']/span")).getText();

				String shipping = null;
				Double shippingArticle = 0.0;
				if (checkShippingElementExist(article)) {
					shipping = article.findElement(By.xpath("ul/li[@class='lvshipping']//span[@class='fee']"))
							.getText();
					shippingArticle = getPriceNumber(shipping);
				}

				priceArticle = getPriceNumber(price);
				totalArticle = priceArticle + shippingArticle;

				// Assertions
				if (previousTotalArticle != null) {
					assertTrue(previousTotalArticle >= totalArticle);
				} else {
					assertTrue(0 < totalArticle);
				}

				// Print the first five results
				String infoArticle = String.format("The article %s has a total price of $%s", name,
						totalArticle.toString());
				System.out.println(infoArticle);

				previousTotalArticle = totalArticle;
			}
		} catch (Exception | AssertionError e) {
			String errorMessage = String.format("An error ocurred while ordering by desscendant price", e.getMessage());
			throw new Error(errorMessage);
		}
	}

	/**
	 * Get the lowest number of a price value
	 * 
	 * @param price The price value
	 * @return The lowest price
	 */
	private Double getPriceNumber(String price) {
		try {
			List<Double> allMatches = new ArrayList<Double>();
			Pattern pattern = Pattern.compile("((\\d*\\s)+)\\d*\\.\\d{2}");
			Matcher matcher = pattern.matcher(price);
			while (matcher.find()) {
				Double priceMatch = Double.parseDouble(matcher.group().replace(" ", ""));
				allMatches.add(priceMatch);
			}

			return Collections.min(allMatches);
		} catch (Exception | AssertionError e) {
			String errorMessage = String.format("An error ocurred while getting the price number", e.getMessage());
			throw new Error(errorMessage);
		}
	}

	/**
	 * Check if shipping element exist
	 * 
	 * @return True if exist. False otherwise
	 */
	private boolean checkShippingElementExist(WebElement article) {
		try {
			article.findElement(By.xpath("ul/li[@class='lvshipping']//span[@class='fee']"));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
