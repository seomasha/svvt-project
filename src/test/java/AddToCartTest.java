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
import static org.junit.jupiter.api.Assertions.*;

public class AddToCartTest {
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
    public void testAddToCart() throws InterruptedException { // Optimize timers.
        webDriver.get(baseUrl);

        Thread.sleep(5000);
        WebElement acceptCookiesButton = webDriver.findElement(By.id("onetrust-accept-btn-handler"));
        acceptCookiesButton.click();

        Thread.sleep(5000);

        WebElement closeButton = webDriver.findElement(By.id("wps-overlay-close-button"));
        closeButton.click();

        Thread.sleep(2000);

        WebElement searchBar = webDriver.findElement(By.cssSelector(".search-box-for-web-desktop  .p-header-search-field"));
        searchBar.click();
        Thread.sleep(2000);

        searchBar.sendKeys("black shoes");

        Thread.sleep(2000);

        webDriver.findElement(By.cssSelector(".search-box-for-web-desktop .icon")).click();

        WebElement product = webDriver.findElement(By.linkText("Retaliate 3 Running Shoes Unisex"));
        product.click();

        WebElement sizeField = webDriver.findElement(By.id("swatch-0290"));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", sizeField);
        Thread.sleep(2000);
        sizeField.click();

        Thread.sleep(2000);

        WebElement addToCartButton = webDriver.findElement(By.cssSelector(".btn-full-width"));
        addToCartButton.click();

        Thread.sleep(2000);

        WebElement viewCartButton = webDriver.findElement(By.cssSelector(".js-addToBagOverlay-quantity-total"));
        viewCartButton.click();

        String quantity = webDriver.findElement(By.cssSelector(".grand-total-number")).getText();
        assertEquals("(1)", quantity);
        Thread.sleep(5000);
    }

    @AfterAll
    public static void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
