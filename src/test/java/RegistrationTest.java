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

public class RegistrationTest {
    private static WebDriver webDriver;
    private static String baseUrl;
    private static String email;

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
        email = "sead.masetic@stu.ibu.edu.ba"; // When running the test, always change to new email
                                                // for successful registration
    }

    @Test
    public void testRegistration() throws InterruptedException {
        webDriver.get(baseUrl);

        Thread.sleep(3000);
        WebElement acceptCookiesButton = webDriver.findElement(By.id("onetrust-accept-btn-handler"));
        acceptCookiesButton.click();

        Thread.sleep(3000);

        WebElement closeButton = webDriver.findElement(By.id("wps-overlay-close-button"));
        closeButton.click();

        Thread.sleep(2000);

        WebElement accountIcon = webDriver.findElement(By.cssSelector(".p-header-actions-icon--account"));
        accountIcon.click();

        Thread.sleep(2000);

        WebElement registerLink = webDriver.findElement(By.id("registerFromQuickLinks"));
        registerLink.click();

        Thread.sleep(2000);

        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("window.scrollBy(0, 300);");

        Thread.sleep(2000);

        WebElement registerFormEmail = webDriver.findElement(By.id("login-form-email"));
        registerFormEmail.click();
        registerFormEmail.sendKeys(email);

        Thread.sleep(2000);

        WebElement continueButton = webDriver.findElement(By.cssSelector(".btn-block:nth-child(3)"));
        continueButton.click();

        Thread.sleep(5000);

        WebElement registerFormPassword = webDriver.findElement(By.id("dwfrm_profile_registration_password"));
        registerFormPassword.click();
        registerFormPassword.sendKeys("P@ssw0r.d");

        Thread.sleep(2000);

        WebElement continuePasswordButton = webDriver.findElement(By.cssSelector(".btn:nth-child(4)"));
        continuePasswordButton.click();

        Thread.sleep(5000);

        // Confirm registration via email.
    }

    @AfterAll
    public static void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
