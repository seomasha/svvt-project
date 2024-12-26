import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LiveChatTest {
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
    public void testLiveChat() throws InterruptedException {
        webDriver.get(baseUrl);
        Thread.sleep(3000);
        WebElement acceptCookiesButton = webDriver.findElement(By.id("onetrust-accept-btn-handler"));
        acceptCookiesButton.click();

        Thread.sleep(3000);

        WebElement closeButton = webDriver.findElement(By.id("wps-overlay-close-button"));
        closeButton.click();

        Thread.sleep(2000);

        WebElement chatIcon = webDriver.findElement(By.cssSelector(".p-header-actions-icon--chat"));
        chatIcon.click();

        Thread.sleep(2000);

        WebElement firstName = webDriver.findElement(By.cssSelector("div#firstName input"));
        Thread.sleep(2000);
        firstName.click();
        Thread.sleep(1000);
        firstName.sendKeys("Sead");

        Thread.sleep(2000);
        WebElement lastName = webDriver.findElement(By.cssSelector("div#lastName input"));
        lastName.click();
        Thread.sleep(1000);
        lastName.sendKeys("Masetic");

        Thread.sleep(2000);
        WebElement email = webDriver.findElement(By.cssSelector("div#email input"));
        email.click();
        Thread.sleep(1000);
        email.sendKeys("sead.masetic@stu.ibu.edu.ba");

        Thread.sleep(2000);
        WebElement consentButton = webDriver.findElement(By.cssSelector("div#consentCheckbox input"));
        consentButton.click();
        Thread.sleep(1000);

        WebElement pushButton = webDriver.findElement(By.cssSelector(".ac-pushButton"));
        pushButton.click();
        Thread.sleep(2000);

        WebElement reply = webDriver.findElement(By.cssSelector("button[aria-label='Item 3 of 3: How to return?']"));
        reply.click();
        Thread.sleep(3000);

        List<WebElement> replyMessage = webDriver.findElements(By.cssSelector(".webchat-quick-reply-template-header-message"));
        Thread.sleep(2000);

        assertEquals("The required return documents can be found in your parcel. " +
                        "You can hand it over to the closest carrier office printed on the label. " +
                        "Please keep the receipt for your own records.",
                        replyMessage.get(1).getText()
                );

        Thread.sleep(2000);
    }

    @AfterAll
    public static void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
