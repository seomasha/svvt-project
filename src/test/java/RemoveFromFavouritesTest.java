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

public class RemoveFromFavouritesTest {
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
        email = "sead.masetic@stu.ibu.edu.ba";
        // for login with already registered account
    }

    @Test
    public void testRemoveFavourites() throws InterruptedException {
        webDriver.get(baseUrl);

        Thread.sleep(3000);
        WebElement acceptCookiesButton = webDriver.findElement(By.id("onetrust-accept-btn-handler"));
        acceptCookiesButton.click();

        Thread.sleep(3000);

        WebElement closeButton = webDriver.findElement(By.id("wps-overlay-close-button"));
        closeButton.click();

        Thread.sleep(2000);

        WebElement shoeItem = webDriver.findElement(By.cssSelector(".products-carousel-glide-slide:nth-child(2) .is-lazy-loaded > .product-recommendation-image"));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", shoeItem);
        Thread.sleep(2000);
        shoeItem.click();

        Thread.sleep(2000);

        WebElement sizeField = webDriver.findElement(By.id("swatch-0290"));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", sizeField);
        Thread.sleep(2000);
        sizeField.click();

        Thread.sleep(2000);

        WebElement addToFavouritesButton = webDriver.findElement(By.cssSelector(".btn-add-to-wish-list-pdp"));
        addToFavouritesButton.click();

        Thread.sleep(2000);

        WebElement inputEmail = webDriver.findElement(By.id("login-form-email"));
        inputEmail.click();
        inputEmail.sendKeys("sead.masetic@stu.ibu.edu.ba");

        Thread.sleep(2000);

        WebElement continueWithPassword = webDriver.findElement(By.cssSelector(".btn-block:nth-child(3)"));
        continueWithPassword.click();

        Thread.sleep(3000);

        WebElement inputPassword = webDriver.findElement(By.id("login-form-password"));
        inputPassword.click();
        inputPassword.sendKeys("P@ssw0r.d");

        Thread.sleep(5000);

        WebElement loginButton = webDriver.findElement(By.cssSelector(".btn:nth-child(5)"));
        loginButton.click();

        Thread.sleep(8000);

        WebElement viewFavourites = webDriver.findElement(By.linkText("View Favourites"));
        viewFavourites.click();

        Thread.sleep(5000);

        WebElement shoeName = webDriver.findElement(By.cssSelector(".line-item-header"));
        String name = shoeName.getText();
        assertEquals("Speedcat OG Sneakers Unisex", name);

        Thread.sleep(3000);

        WebElement removeButton = webDriver.findElement(By.className("remove-from-wishlist"));
        removeButton.click();

        Thread.sleep(3000);

        WebElement confirmRemove = webDriver.findElement(By.cssSelector(".wish-delete-confirmation-btn"));
        confirmRemove.click();

        Thread.sleep(3000);

        WebElement checkRemove = webDriver.findElement(By.cssSelector(".wishlist-empty-message"));
        String text = checkRemove.getText();
        assertEquals("Your Favourites list is Empty", text);

        Thread.sleep(5000);
    }

    @AfterAll
    public static void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
