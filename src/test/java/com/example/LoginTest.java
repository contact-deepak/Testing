package com.example;

import java.time.Duration;
import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginTest {
    WebDriver driver;

    @BeforeClass
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Run Chrome in headless mode
        options.addArguments("--no-sandbox"); // Avoids issues with Docker containers
        options.addArguments("--disable-dev-shm-usage"); // Overcome limited resource issues in CI/CD
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test
    public void testLogin() {
        driver.get("https://thinking-tester-contact-list.herokuapp.com/");
        WebElement usernameField = driver.findElement(By.id("email"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.tagName("button"));

        usernameField.sendKeys("gravityaitest1@gmail.com");
        passwordField.sendKeys("Gravity@123");
        loginButton.click();

        Assert.assertTrue(driver.findElement(By.id("logout")).isDisplayed());
    }
    @Test
    public void testfailed() {
        driver.get("https://thinking-tester-contact-list.herokuapp.com/");
        WebElement usernameField = driver.findElement(By.id("email"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.tagName("button"));

        usernameField.sendKeys("gravityaitest1@gmail.com");
        passwordField.sendKeys("Gravity@123");
        loginButton.click();

        Assert.assertTrue(driver.findElement(By.id("logou")).isDisplayed());
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
