package testCases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class AddtoCart {

    WebDriver driver;
    @BeforeMethod
    public void launchpage()
    {
        driver = new ChromeDriver();
        driver.get("https://web-playground.ultralesson.com/");
        driver.manage().window().maximize();
    }


    @Test
    public void identifyProduct()
    {
        driver.findElement(By.xpath("//summary[@aria-label='Search']//span")).click();
        driver.findElement(By.id("Search-In-Modal")).sendKeys("Belted Jeans");
        driver.findElement(By.xpath("//button[@class='search__button field__button']")).click();
        WebElement prodlink = driver.findElement(By.xpath("//a[contains(text(),'Belted Jeans')]"));
        prodlink.click();
        String text = driver.findElement(By.className("product__title")).getText().trim();
        System.out.println(text);

        Assert.assertEquals(text,"Belted Jeans");

    }



   @AfterMethod
    public void closeBrowser()
    {
        driver.quit();
    }
}
