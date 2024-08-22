package com.example;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest {
	WebDriver driver;

	@BeforeMethod
        public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");// Run Chrome in headless mode
        options.addArguments("--no-sandbox"); // Avoids issues with Docker containers
        options.addArguments("--disable-dev-shm-usage"); // Overcome limited resource issues in CI/CD
        driver = new ChromeDriver(options);
	driver.get("https://d3lb3cevpf2y2n.cloudfront.net/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        }
	
	@Test
	public void LoginWithValidEmailAndValidPassword() throws InterruptedException {
        driver.findElement(By.className("login-btn")).click();
        driver.findElement(By.id("1-email")).sendKeys("contact.deepakb@gmail.com");
        driver.findElement(By.id("1-password")).sendKeys("Deepak@123");
        driver.findElement(By.id("1-submit")).click();
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    try {
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ant-dropdown-trigger")));
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message should be displayed.");
    } catch (TimeoutException e) {
        Assert.fail("The error message did not appear within the expected time.");
	    File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
FileUtils.copyFile(screenshot, new File("screenshot.png"));

    }
	}
	
	@Test
	public void LoginWithInvalidEmailAndValidPassword() throws InterruptedException {
        driver.findElement(By.className("login-btn")).click();
        driver.findElement(By.id("1-email")).sendKeys("contact.deep@gmail.com");
        driver.findElement(By.id("1-password")).sendKeys("Deepak@123");
        driver.findElement(By.id("1-submit")).click();
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".auth0-global-message-error")));	
        Assert.assertTrue(dropdown.isDisplayed());	
	}
	
	@Test
	public void LoginWithValidEmailAndInvalidPassword() throws InterruptedException {
        driver.findElement(By.className("login-btn")).click();
        driver.findElement(By.id("1-email")).sendKeys("contact.deepakb@gmail.com");
        driver.findElement(By.id("1-password")).sendKeys("Deepak@456");
        driver.findElement(By.id("1-submit")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".auth0-global-message-error")));	
        Assert.assertTrue(dropdown.isDisplayed());
	}
	
	@Test
	public void LoginWithInvalidEmailAndInvalidPassword() throws InterruptedException {
        driver.findElement(By.className("login-btn")).click();
        driver.findElement(By.id("1-email")).sendKeys("contact.deep@gmail.com");
        driver.findElement(By.id("1-password")).sendKeys("Deepak");
        driver.findElement(By.id("1-submit")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".auth0-global-message-error")));	
        Assert.assertTrue(dropdown.isDisplayed());
	}

	@Test
	public void ForgotPasswordWithValidEmail() throws InterruptedException {
        driver.findElement(By.className("login-btn")).click();
        driver.findElement(By.className("auth0-lock-alternative-link")).click();
        Thread.sleep(3000);
        driver.findElement(By.id("1-email")).sendKeys("deepak1@gmail.com");
        driver.findElement(By.id("1-submit")).click();
	Thread.sleep(3000);
        Assert.assertTrue(driver.findElement(By.className("auth0-global-message-success")).isDisplayed());
	}
	
	@Test
	public void ForgotPasswordWithInvalidEmail() throws InterruptedException {
        driver.findElement(By.className("login-btn")).click();
        driver.findElement(By.className("auth0-lock-alternative-link")).click();
        Thread.sleep(3000);
        driver.findElement(By.id("1-email")).sendKeys("deepak0@gmail.com");
        driver.findElement(By.id("1-submit")).click();
	Thread.sleep(3000);
        Assert.assertTrue(driver.findElement(By.className("auth0-global-message-success")).isDisplayed());
	}

	@Test
	public void ContinueWithGoogle() throws InterruptedException {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.findElement(By.className("login-btn")).click();
        driver.findElement(By.xpath("//a[@data-provider='google-oauth2']")).click();
        driver.findElement(By.id("identifierId")).sendKeys("gravityaitest1@gmail.com");
        driver.findElement(By.className("VfPpkd-LgbsSe-OWXEXe-k8QpJ")).click();
        driver.findElement(By.name("Passwd")).sendKeys("Gravity@123");
	Thread.sleep(3000);
        WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.className("VfPpkd-LgbsSe-OWXEXe-k8QpJ")));
        nextButton.click();
        Assert.assertTrue(driver.findElement(By.className("ant-dropdown-trigger")).isDisplayed());
	}

	@AfterMethod
	public void close() {
		driver.close();
	}

}

