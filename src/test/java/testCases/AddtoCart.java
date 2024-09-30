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

    public static int randomnum(int num)
    {
        Random rand = new Random();
        return rand.nextInt(num)+1;
    }

    @Test
    public void identifyProduct()
    {
        driver.findElement(By.xpath("//summary[@aria-label='Search']//span")).click();
        driver.findElement(By.id("Search-In-Modal")).sendKeys("Jeans");
        driver.findElement(By.xpath("//button[@class='search__button field__button']")).click();
        List<WebElement> products = driver.findElements(By.xpath("//li[@class='grid__item']"));
        int randnum = randomnum(products.size());
        System.out.println(randnum);
        products.get(randnum).click();
        String text = driver.findElement(By.className("product__title")).getText().trim();

        Assert.assertTrue(text.contains("Jean"));

    }

    @Test
    public void productAvailability()
    {
        driver.findElement(By.xpath("//summary[@aria-label='Search']//span")).click();
        driver.findElement(By.id("Search-In-Modal")).sendKeys("Jeans");
        driver.findElement(By.xpath("//button[@class='search__button field__button']")).click();
        List<WebElement> products = driver.findElements(By.xpath("//li[@class='grid__item']"));
        int randnum = randomnum(products.size());
        System.out.println(randnum);
        products.get(randnum).click();
        WebElement addtocartbutton = driver.findElement(By.xpath("//button[@class='product-form__submit button button--full-width button--secondary']"));

       // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
       // wait.until(ExpectedConditions.presenceOfElementLocated(By.id("product-form__submit button button--full-width button--secondary")));
        if(addtocartbutton.isEnabled())
        {
            addtocartbutton.click();
        }
        else {
            Reporter.log("Product is sold out",true);
        }
    }

    @Test
    public void availableProductToCart()
    {
        driver.findElement(By.xpath("//summary[@aria-label='Search']//span")).click();
        driver.findElement(By.id("Search-In-Modal")).sendKeys("Jeans");
        driver.findElement(By.xpath("//button[@class='search__button field__button']")).click();
        List<WebElement> products = driver.findElements(By.xpath("//li[@class='grid__item']"));
        int randnum = randomnum(products.size());
        System.out.println(randnum);
        products.get(randnum).click();
        WebElement addtocartbutton = driver.findElement(By.xpath("//button[@class='product-form__submit button button--full-width button--secondary']"));
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));

        if(addtocartbutton.isEnabled())
        {
            addtocartbutton.click();
            WebElement addtocart2 = driver.findElement(By.id("cart-notification-button"));
            wait.until(ExpectedConditions.elementToBeClickable(addtocart2));
            String text = driver.findElement(By.xpath("//div[@class='cart-notification__header']//h2")).getText().trim();
            addtocart2.click();
            System.out.println(text);
            Assert.assertEquals("Item added to your cart",text);
            //tr[@class='cart-item'][1]//td[@class='cart-item__details']//a

        }
        else {
            Reporter.log("Product is sold out",true);
        }
    }

    @Test
    public void validateCartcontents()
    {

        //getting the list of items in cart
        driver.findElement(By.xpath("//a[contains(@href, 'cart')]//*[name()='svg']")).click();
        List<WebElement> items = driver.findElements(By.xpath("//tr[@class='cart-item']"));
        int initialnoofitems = items.size();
        //driver.findElement(By.xpath("//a[@class='menu-drawer__menu-item list-menu__item link link--text focus-inset menu-drawer__menu-item--active']")).click();

        driver.findElement(By.xpath("//summary[@aria-label='Search']//span")).click();
        driver.findElement(By.id("Search-In-Modal")).sendKeys("Jeans");
        driver.findElement(By.xpath("//button[@class='search__button field__button']")).click();
        List<WebElement> products = driver.findElements(By.xpath("//li[@class='grid__item']"));
        int randnum = randomnum(products.size());
        System.out.println(randnum);
        products.get(randnum).click();
        WebElement addtocartbutton = driver.findElement(By.xpath("//button[@class='product-form__submit button button--full-width button--secondary']"));
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));

        if(addtocartbutton.isEnabled())
        {
            addtocartbutton.click();
            WebElement addtocart2 = driver.findElement(By.id("cart-notification-button"));
            wait.until(ExpectedConditions.elementToBeClickable(addtocart2));
            String text = driver.findElement(By.xpath("//div[@class='cart-notification__header']//h2")).getText().trim();
            addtocart2.click();
            System.out.println(text);
            //below is delete button of an item in cart
            List<WebElement> items2 = driver.findElements(By.xpath("//tr[@class='cart-item'][1]//td[@class='cart-item__quantity']//a"));
            int noofitems = items2.size();
            Assert.assertEquals(noofitems,initialnoofitems+1);
            //deleting items
            for(WebElement ele:items2)
            {
                ele.click();
           }
           // Assert.assertEquals("Item added to your cart",text);
            //tr[@class='cart-item'][1]//td[@class='cart-item__details']//a

        }
        else {
            Reporter.log("Product is sold out",true);
        }
    }



//    @AfterMethod
//    public void closeBrowser()
//    {
//        driver.quit();
//    }
}
