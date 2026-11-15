package ru.zyablitseva.NauJava.ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginLogoutUiTest {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void loginAndLogout_Success() {
        String baseUrl = "http://localhost:" + port;
        // 1. Страница логина
        driver.get(baseUrl + "/login");
        assertTrue(driver.getCurrentUrl().contains("/login"),
                "Ожидалась страница /login, а была: " + driver.getCurrentUrl());
        // 2. Ввод логина и пароля
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin123");
        // 3. Вход
        driver.findElement(By.cssSelector("input[type='submit']")).click();
        // 4. Домашняя страница после входа
        assertTrue(driver.getCurrentUrl().contains("/view/movies/list"),
                "После входа ожидалась /view/movies/list, а была: " + driver.getCurrentUrl());
        // 5. Выход
        driver.findElement(By.cssSelector("form[action*='/logout'] input[type='submit']")).click();
        // 6. Редирект на логин
        assertTrue(driver.getCurrentUrl().contains("/login"),
                "После выхода ожидалась /login, а была: " + driver.getCurrentUrl());
    }
}