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

    @Test
    public void productAvailability()
    {
        driver.findElement(By.xpath("//summary[@aria-label='Search']//span")).click();
        driver.findElement(By.id("Search-In-Modal")).sendKeys("Skinny Stretch Jean in Indigo");
        driver.findElement(By.xpath("//button[@class='search__button field__button']")).click();
        WebElement prodlink = driver.findElement(By.xpath("//a[contains(text(),'Skinny Stretch Jean in Indigo')]"));
        prodlink.click();
        List<WebElement> soldOutIndicators = driver.findElements(By.xpath("//span[contains(text(),'Sold out')]"));
        if(!soldOutIndicators.isEmpty())
        {
            Reporter.log("Item is sold out and not available",true);
            Assert.assertTrue(soldOutIndicators.getFirst().isDisplayed(),"Sold out indicator should be displayed");
            return;
        }
        WebElement addtocartbutton = driver.findElement(By.xpath("//button[@class='product-form__submit button button--full-width button--secondary']"));
        Assert.assertTrue(addtocartbutton.isEnabled(),"Add to cart should be enabled");
        addtocartbutton.click();
    }

    @Test
    public void addAvailableProduct()
    {
        //getting inital cart size
        driver.findElement(By.xpath("//a[@id='cart-icon-bubble']//*[name()='svg']")).click();
        List<WebElement> initialitems = driver.findElements(By.xpath("//tr[@class='cart-item']"));

        //searching and clicking on product
        driver.findElement(By.xpath("//summary[@aria-label='Search']//span")).click();
        driver.findElement(By.id("Search-In-Modal")).sendKeys("Belted Jeans");
        driver.findElement(By.xpath("//button[@class='search__button field__button']")).click();
        WebElement prodlink = driver.findElement(By.xpath("//a[contains(text(),'Belted Jeans')]"));
        prodlink.click();
        WebElement addtocartbutton = driver.findElement(By.xpath("//button[@class='product-form__submit button button--full-width button--secondary']"));
        Assert.assertTrue(addtocartbutton.isEnabled(),"Add to cart should be enabled");
        addtocartbutton.click();

        //Verifying addtocart button enabled or not
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        WebElement viewcart = driver.findElement(By.xpath("//a[@class='button button--secondary button--full-width']"));
        wait.until(ExpectedConditions.elementToBeClickable(viewcart));
        String text = driver.findElement(By.xpath("//h2[@class='cart-notification__heading caption-large']")).getText().trim();
        viewcart.click();

        //getting the size of cart after adding product
        driver.findElement(By.xpath("//a[@id='cart-icon-bubble']//*[name()='svg']")).click();
        List<WebElement> items = driver.findElements(By.xpath("//tr[@class='cart-item']"));

        //Confirming 'Item added to your cart' message appears or not
        Assert.assertEquals(text,"Item added to your cart");

        //Verifying cart's item count is incremented or not
        Assert.assertTrue(items.size()>initialitems.size(),"Items not added");


    }

    @Test
    public void validateCart()  {

        String prodname ="Slim Fit Jean in Indigo";
        driver.findElement(By.xpath("//summary[@aria-label='Search']//span")).click();
        driver.findElement(By.id("Search-In-Modal")).sendKeys(prodname);
        driver.findElement(By.xpath("//button[@class='search__button field__button']")).click();
        WebElement prodlink = driver.findElement(By.xpath("//a[@class='full-unstyled-link']"));
        prodlink.click();
        String size = "34";
        driver.findElement(By.xpath("//label[normalize-space()='" + size + "']")).click();
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        WebElement addtocartbutton = driver.findElement(By.xpath("//button[@name='add']"));
        wait.until(ExpectedConditions.elementToBeClickable(addtocartbutton));
        //fetching price of the item
        String price = driver.findElement(By.xpath("//span[@class='price-item price-item--regular']")).getText().trim().substring(4);
        String name = driver.findElement(By.className("product__title")).getText().trim();
        addtocartbutton.click();


        //Verifying viewcart button enabled or not
        WebElement viewcart = driver.findElement(By.xpath("//a[@class='button button--secondary button--full-width']"));
        wait.until(ExpectedConditions.elementToBeClickable(viewcart));
        String text = driver.findElement(By.xpath("//h2[@class='cart-notification__heading caption-large']")).getText().trim();
        viewcart.click();

        //Verifying the name,size,color,quantity in the cart page
        List<WebElement> items= driver.findElements(By.xpath("//tr[@class='cart-item']"));
        int quantity = items.size();
        int expectedquantity = 1;
        String sizetext = driver.findElement(By.xpath("//div[@class='product-option'][1]//dd")).getText();
        String colortext = driver.findElement(By.xpath("//div[@class='product-option'][2]//dd")).getText();
        Assert.assertEquals(prodname,name);
        Assert.assertEquals(sizetext,size);
        Assert.assertEquals(colortext,"Indigo");
        Assert.assertEquals(quantity,expectedquantity);
        double discountpercentage = 0.05;

        //Verifying the price,total price in the cart page
        String pricetext = driver.findElement(By.xpath("//td[@class='cart-item__totals right small-hide']//span")).getText();
        String totalpricetext = driver.findElement(By.xpath("//p[@class='totals__subtotal-value']")).getText();
        double priceindouble = Double.valueOf(pricetext.substring(4));
        //double totalprice = priceindouble*discountpercentage;
        double finaltotalprice = priceindouble - (priceindouble * discountpercentage);
          //verifying price
        Assert.assertEquals(pricetext.substring(4),price);
        double totalpriceindouble = Double.valueOf(totalpricetext.substring(4));
          //verifying total
        Assert.assertEquals(totalpriceindouble, finaltotalprice);

    }



   @AfterMethod
    public void closeBrowser()
    {

        driver.quit();
   }
}
