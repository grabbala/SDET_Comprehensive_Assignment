
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchFlight_Automation {
	private static final String CHROME_DRIVER_PATH = System.getProperty("user.dir") + "/chromedriver.exe";
	private static final String MAKE_MY_TRIP_URL = "https://www.makemytrip.com/";

	public static void main(String[] args) {
		// Set up WebDriver
		System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();

		try {
			// Open MakeMyTrip
			driver.get(MAKE_MY_TRIP_URL);
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			// Close login popup if present
			closeLoginPopup(driver, wait);

			// Perform flight search
			searchFlights(driver, wait, "HYD", "MAA", "Thu Feb 20 2025", "Thu Mar 13 2025");

			// Verify search results page
			if (isSearchResultsDisplayed(driver, wait)) {
				System.out.println("✅ Test Passed: Flight search page displayed successfully!");
			} else {
				System.out.println("❌ Test Failed: Search results page not displayed!");
			}

		} catch (Exception e) {
			System.err.println("❌ Test Failed: " + e.getMessage());
		} finally {
			// Close the browser
			driver.quit();
		}
	}

	/**
	 * Closes the login popup if present.
	 */
	private static void closeLoginPopup(WebDriver driver, WebDriverWait wait) {
		try {
			WebElement closePopup = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='commonModal__close']")));
			closePopup.click();
			System.out.println("ℹ️ Login popup closed.");
		} catch (Exception e) {
			System.out.println("ℹ️ No login popup displayed.");
		}
	}

	/**
	 * Performs the flight search operation.
	 */
	private static void searchFlights(WebDriver driver, WebDriverWait wait, String from, String to, String departure,
			String returnDate) {
		// Click on "Flights"
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(@class, 'chNavText') and text()='Flights']"))).click();

		// Select "Round Trip"
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[text()='Round Trip']"))).click();

		// Select "From" location
		selectCity(driver, wait, By.id("fromCity"), By.xpath("//input[@placeholder='From']"), from);

		// Select "To" location
		selectCity(driver, wait, By.id("toCity"), By.xpath("//input[@placeholder='To']"), to);

		// Select Departure Date
		selectDate(driver, wait, By.xpath("//label[@for='departure']"), departure);

		// Select Return Date
		selectDate(driver, wait, By.xpath("//label[@for='return']"), returnDate);

		// Click on Search Button
		driver.findElement(By.xpath("//a[text()='Search']")).click();
	}

	/**
	 * Selects a city in the "From" or "To" search box.
	 */
	private static void selectCity(WebDriver driver, WebDriverWait wait, By cityField, By searchBox, String city) {
		driver.findElement(cityField).click();
		driver.findElement(searchBox).sendKeys(city);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='" + city + "'] | //div[normalize-space()='" + city + "']")))
				.click();
		System.out.println("✅ Selected City: " + city);
	}

	/**
	 * Selects a date from the calendar.
	 */
	private static void selectDate(WebDriver driver, WebDriverWait wait, By dateField, String date) {
		//driver.findElement(dateField).click();
		//wait.until(ExpectedConditions.elementToBeClickable(dateField)).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@aria-label='" + date + "']"))).click();
		System.out.println("✅ Selected Date: " + date);
	}

	/**
	 * Verifies if the search results page is displayed.
	 */
	private static boolean isSearchResultsDisplayed(WebDriver driver, WebDriverWait wait) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Flights from')]")));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}