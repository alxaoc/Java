import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class GoogleSearchTest {

    private WebDriver driver;

    @BeforeTest
    public void setup() {
        // Завантажуємо веб-драйвер Chrome за допомогою bonigarcia/webdrivermanager
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testGoogleSearch() {
        // Відкриваємо сторінку Google
        driver.get("https://www.google.com/");

        // Чекаємо, поки не з'явиться модальне вікно Before you continue to Google
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement rejectAllButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("W0wltc")));

        // Натискаємо на кнопку Reject all
        rejectAllButton.click();

        // Знаходимо поле пошукового запиту та вводимо слово "WPT"
        WebElement searchField = driver.findElement(By.name("q"));
        searchField.sendKeys("WPT");

        // Натискаємо кнопку "Пошук"
        WebElement searchButton = driver.findElement(By.name("btnK"));
        searchButton.submit();

        // Чекаємо формування пошукового запиту
        WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait1.until(webDriver -> webDriver.getTitle().toLowerCase().startsWith("WPT"));

        // Клікаємо на перший результат пошуку
        WebElement firstResult = driver.findElement(By.cssSelector("div.rc h3"));
        String firstResultTitle = firstResult.getText();
        firstResult.click();

        // Чекаємо, поки сторінка повністю завантажиться
        wait.until(ExpectedConditions.titleContains("WPT"));

        // Перевіряємо співпадання заголовка списку та відкритої сторінки
        String pageTitle = driver.getTitle();
        if (firstResultTitle.equalsIgnoreCase(pageTitle)) {
            System.out.println("Заголовки співпадають");
        } else {
            System.out.println("Заголовки не співпадають");
        }

    }

    @AfterTest
    public void teardown() {
        // Закриваємо браузер
        driver.quit();
    }
}
