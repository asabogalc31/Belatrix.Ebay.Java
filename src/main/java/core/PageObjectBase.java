package core;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PageObjectBase {
	public static WebDriver driver;

	/**
	 * Initiate the browser specified on Configuration.properties file
	 */
	public static void initiateBrowser() {
		try {
			// Load the properties from Configuration.properties file
			ConfigurationFilesReader.loadPropertiesFromFile(PageObjectBase.class);

			switch (ConfigurationFilesReader.getStringProperty("selenium.browser")) {
			case "IE":
				WebDriverManager.iedriver().arch32().setup();
				System.setProperty("webdriver.path", System.getProperty("webdriver.ie.driver"));
				InternetExplorerOptions ieOptions = new InternetExplorerOptions();
				ieOptions.setCapability("ignoreProtectedModeSettings", true);
				ieOptions.setPageLoadStrategy(PageLoadStrategy
						.fromString(ConfigurationFilesReader.getStringProperty("selenium.pageLoadStrategy")));
				driver = new InternetExplorerDriver(ieOptions);
				break;
			case "FF":
				WebDriverManager.firefoxdriver().arch32().setup();
				System.setProperty("webdriver.path", System.getProperty("webdriver.gecko.driver"));
				FirefoxOptions firefoxOptions = new FirefoxOptions();
				driver = new FirefoxDriver(firefoxOptions);
				break;
			default:
				WebDriverManager.chromedriver().arch32().setup();
				System.setProperty("webdriver.path", System.getProperty("webdriver.chrome.driver"));
				ChromeOptions chromeOptions = new ChromeOptions();
				driver = new ChromeDriver(chromeOptions);
				break;
			}

			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(
					ConfigurationFilesReader.getLongProperty("selenium.implicitlyWait"), TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(
					ConfigurationFilesReader.getLongProperty("selenium.pageLoadTimeOut"), TimeUnit.SECONDS);

			driver.get(ConfigurationFilesReader.getStringProperty("site"));
		} catch (Exception e) {
			String errorMessage = String.format("----------- Was not possible to initiate the browser", e);
			throw new Error(errorMessage);
		}
	}

	/**
	 * Close the browser
	 */
	public static void closeBrowser() {
		driver.close();
		if (driver != null) {
			driver.quit();
		}

		killBrowser();
	}

	/**
	 * Kill the browser that is currently used
	 */
	private static void killBrowser() {
		String browser = "";
		switch (ConfigurationFilesReader.getStringProperty("selenium.browser")) {
		case "IE":
			browser = "iexplore.exe";
			break;
		case "FF":
			browser = "firefox.exe";
			break;
		default:
			browser = "chromedriver.exe";
			break;
		}

		killProcess(browser);
	}

	/**
	 * Identify a process and finish it
	 * 
	 * @param task
	 */
	private static void killProcess(String task) {
		String cmdKill = String.format("taskill /IM %s /F", task);
		Process processToKill;
		try {
			processToKill = Runtime.getRuntime().exec(cmdKill);
			processToKill.waitFor();
			if (processToKill.exitValue() == 0) {
				System.out.println(String.format("The task %s was finished", task));
			} else {
				System.out.println(String.format("The task %s was not finished", task));
			}
		} catch (Exception e) {
			System.out.println(String.format("Impossible to kill the process %s", task));
		}
	}
}
