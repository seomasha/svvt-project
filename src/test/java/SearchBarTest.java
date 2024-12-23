import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import static org.junit.jupiter.api.Assertions.*;

public class SearchBarTest {
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
    public void testSearchBar() throws InterruptedException { // Optimize timers.
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

        WebElement breadcrumbList = webDriver.findElement(By.className("breadcrumb"));

        WebElement lastBreadcrumbItem = breadcrumbList.findElements(By.className("breadcrumb-item")).getLast();
        String lastBreadcrumbText = lastBreadcrumbItem.getText().trim();
        assertEquals("black shoes", lastBreadcrumbText);

        Thread.sleep(5000);
    }

    @AfterAll
    public static void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
