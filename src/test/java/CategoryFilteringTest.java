import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryFilteringTest {
    private static WebDriver webDriver;
    private static String baseUrl;

    @BeforeAll
    public static void setUp() {
        Dotenv dotenv = Dotenv.load();
        String webDriverPath = dotenv.get("WEBDRIVER_URL");
        System.setProperty("webdriver.chrome.driver", webDriverPath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        webDriver = new ChromeDriver(options);
        baseUrl = "https://eu.puma.com/de/en/home";
    }

    @Test
    public void testCategoryFiltering() throws InterruptedException { // Optimize timers.
        webDriver.get(baseUrl);

        Thread.sleep(5000);
        WebElement acceptCookiesButton = webDriver.findElement(By.id("onetrust-accept-btn-handler"));
        acceptCookiesButton.click();

        Thread.sleep(5000);

        WebElement closeButton = webDriver.findElement(By.id("wps-overlay-close-button"));
        closeButton.click();

        Thread.sleep(2000);

        WebElement menDropdown = webDriver.findElement(By.xpath("//a[@class='p-nav-item-link js-top-level' and contains(., 'Men')]"));

        Actions actions = new Actions(webDriver);
        actions.moveToElement(menDropdown).perform();

        Thread.sleep(3000);

        WebElement shoeDropdownOption = webDriver.findElement(By.cssSelector(".p-nav-item:nth-child(2) .p-nav-subnav-item:nth-child(2) .p-nav-subnav-item-group:nth-child(1) > .p-sub-nav-tier2:nth-child(2) span:nth-child(1)"));
        shoeDropdownOption.click();

        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("window.scrollBy(0, 500);");

        WebElement priceDropdown = webDriver.findElement(By.cssSelector(".refinement--product_price_de > .btn > span"));
        priceDropdown.click();

        Thread.sleep(1000);

        WebElement fourthPrice = webDriver.findElement(By.cssSelector(".refinement-list .refinement-item:nth-of-type(4) button"));
        fourthPrice.click();

        Thread.sleep(2000);

        WebElement xPriceButton = webDriver.findElement(By.cssSelector(".refinement--product_price_de > .refinement-show > .btn"));
        xPriceButton.click();

        Thread.sleep(2000);

        WebElement sizeDropdown = webDriver.findElement(By.cssSelector(".refinement--size_facet > .btn"));
        sizeDropdown.click();

        WebElement fourthSize = webDriver.findElement(By.cssSelector("button[data-attribute='size_facet'][data-selected-alt*='37.5']"));
        fourthSize.click();

        Thread.sleep(2000);

        WebElement xSizeButton = webDriver.findElement(By.cssSelector(".refinement--size_facet > .refinement-show > .btn"));
        xSizeButton.click();

        Thread.sleep(2000);

        WebElement colorDropdown = webDriver.findElement(By.cssSelector(".refinement--refinement_color > .btn > span"));
        colorDropdown.click();

        Thread.sleep(1000);

        WebElement firstColor = webDriver.findElement(By.cssSelector("button[data-attribute='refinement_color'][data-selected-alt*='black']"));
        firstColor.click();

        Thread.sleep(2000);

        WebElement xColorButton = webDriver.findElement(By.cssSelector(".refinement--refinement_color > .refinement-show > .btn"));
        xColorButton.click();

        WebElement priceFilter = webDriver.findElement(By.xpath("//li[@class='filter-bar-item' and contains(@title, 'Price:')]"));
        WebElement sizeFilter = webDriver.findElement(By.xpath("//li[@class='filter-bar-item' and contains(@title, 'Size:')]"));
        WebElement colorFilter = webDriver.findElement(By.xpath("//li[@class='filter-bar-item' and contains(@title, 'Colour:')]"));

        assertEquals("Price: 120 € - 200 €", priceFilter.getText());
        assertEquals("Size: 37.5", sizeFilter.getText());
        assertEquals("Colour: Black", colorFilter.getText());

        Thread.sleep(5000);
    }

    @AfterAll
    public static void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
