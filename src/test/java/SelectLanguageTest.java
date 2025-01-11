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

public class SelectLanguageTest {
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
    public void testSelectLanguage() throws InterruptedException {
        webDriver.get(baseUrl);
        Thread.sleep(3000);
        WebElement acceptCookiesButton = webDriver.findElement(By.id("onetrust-accept-btn-handler"));
        acceptCookiesButton.click();

        Thread.sleep(5000);

        WebElement closeButton = webDriver.findElement(By.id("wps-overlay-close-button"));
        closeButton.click();

        Thread.sleep(2000);

        WebElement languageSelector = webDriver.findElement(By.cssSelector(".p-meta-footer-language-link-mask"));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", languageSelector);
        Thread.sleep(2000);
        languageSelector.click();

        Thread.sleep(5000);

        WebElement inputBar = webDriver.findElement(By.id("locale-search-input"));
        inputBar.click();
        Thread.sleep(2000);
        inputBar.sendKeys("portugal");

        Thread.sleep(3000);

        WebElement portuguese = webDriver.findElement(By.cssSelector(".language"));
        portuguese.click();

        Thread.sleep(3000);

        assertEquals("https://eu.puma.com/pt/en/home", webDriver.getCurrentUrl());
    }

    @AfterAll
    public static void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
