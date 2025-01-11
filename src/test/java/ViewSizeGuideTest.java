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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewSizeGuideTest {
    private static WebDriver webDriver;
    private static String baseUrl;

    @BeforeAll
    public static void setUp() {
        Dotenv dotenv = Dotenv.load();
        String webDriverPath = dotenv.get("WEBDRIVER_URL");
        System.setProperty("webdriver.chrome.driver", webDriverPath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");
        webDriver = new ChromeDriver(options);
        baseUrl = "https://eu.puma.com/de/en/home";
    }

    @Test
    public void testViewSizeGuide() throws InterruptedException {
        webDriver.get(baseUrl);

        Thread.sleep(3000);
        WebElement acceptCookiesButton = webDriver.findElement(By.id("onetrust-accept-btn-handler"));
        acceptCookiesButton.click();

        Thread.sleep(5000);

        WebElement closeButton = webDriver.findElement(By.id("wps-overlay-close-button"));
        closeButton.click();

        Thread.sleep(2000);

        WebElement shoeItem = webDriver.findElement(By.cssSelector(".products-carousel-glide-slide:nth-child(3) .is-lazy-loaded > .product-recommendation-image"));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", shoeItem);
        Thread.sleep(2000);
        shoeItem.click();

        WebElement sizeGuide = webDriver.findElement(By.cssSelector(".size-chart-button"));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", sizeGuide);
        Thread.sleep(2000);
        sizeGuide.click();

        Thread.sleep(3000);

        WebElement checkSizeGuide = webDriver.findElement(By.cssSelector(".main-heading"));
        String text = checkSizeGuide.getText();
        assertEquals("International Size Conversions", text);

        Thread.sleep(5000);
    }

    @AfterAll
    public static void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
