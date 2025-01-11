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

public class ContactUsTest {
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
    public void testContactUs() throws InterruptedException {
        webDriver.get(baseUrl);

        Thread.sleep(3000);
        WebElement acceptCookiesButton = webDriver.findElement(By.id("onetrust-accept-btn-handler"));
        acceptCookiesButton.click();

        Thread.sleep(5000);

        WebElement closeButton = webDriver.findElement(By.id("wps-overlay-close-button"));
        closeButton.click();

        Thread.sleep(2000);

        WebElement contactUS = webDriver.findElement(By.linkText("Contact us"));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", contactUS);
        Thread.sleep(2000);
        contactUS.click();

        Thread.sleep(5000);

        WebElement track = webDriver.findElement(By.cssSelector("a strong"));
        track.click();

        Thread.sleep(3000);

        WebElement trackingNumber = webDriver.findElement(By.name("linked_shipments.tracking_number"));
        trackingNumber.click();
        trackingNumber.sendKeys("#1234567890");

        Thread.sleep(3000);

        WebElement trackNow = webDriver.findElement(By.cssSelector(".sc-fzpans"));
        trackNow.click();

        Thread.sleep(5000);
    }

    @AfterAll
    public static void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
