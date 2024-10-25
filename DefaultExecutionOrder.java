package Chapter5;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.time.Duration;


public class DefaultExecutionOrder {
    SoftAssert softassert = new SoftAssert();

    WebDriver driver;

    //@BeforeClass
    /* Using groups and dependsOnGroups*/

    @Test(groups = "initialize")
    public void setup () throws Exception {
        // Set up the Chrome WebDriver
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        // Open the OrangeHRM login page
        driver.get("https://opensourcedemo.orangehrmlive.com/web/index.php/auth/login");
    }
//Using DependsOnMethods
    @Test(dependsOnGroups = "initialize", groups = "env_applications")
   //@Test(dependsOnMethods = "setup")
  // @Test(priority = 1)
    public void signIn() {
        // Locate and interact with the login elements
        WebElement username = driver.findElement(By.name("username"));
        WebElement password = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));

        // Provide login credentials and submit
        username.sendKeys("Admin");
        password.sendKeys("admin123");
        loginButton.click();
       System.out.println("1.Logged in");
    }

   /* @Test(dependsOnMethods = "signin")
    //@Test(priority = 2)
    public void testHomePageVerification(){
      //Hard/Soft Assert implementation
       Assert.assertEquals(true,false,"The welcome link is not correct on the homepage");
       // softassert.assertEquals(true,false,"The welcome link is not correct on the homepage");
        System.out.println("2.Verify welcome link");
        Assert.assertFalse(false,"The admin tab is not displayed on the homepage");
        //softassert.assertFalse(false,"The admin tab is not displayed on the homepage");
        System.out.println("3.Verify admin tab");
        Assert.assertTrue(true,"The dashboard is not correct on the homepage");
        //softassert.assertTrue(false,"The dashboard is not correct on the homepage");
        System.out.println("4.Verify dashboard");
        //softassert.assertAll();
    }*/
    //@Test(priority = 3)
    @Test(dependsOnGroups = "env_applications")
    //@Test(dependsOnMethods = {"signIn", "setup"})
    public void searchClaim() {
        // Navigate to the "Buzz" page by clicking on the sidebar menu
        WebElement buzzMenu = driver.findElement(By.xpath("//span[text()='Claim']"));
        buzzMenu.click();
        System.out.println("5.Search for Claim");
    }
    @Test(dependsOnGroups = "env_applications")
   // @Test(dependsOnMethods = {"signIn", "setup"})
   // @Test(priority = 4)
    public void signout() {
        // Click on the user dropdown
        WebElement userDropdown = driver.findElement(By.xpath("//span[@class='oxd-userdropdown-tab']"));
        userDropdown.click();
        // Click on the "Logout" option
        WebElement logoutButton = driver.findElement(By.xpath("//a[text()='Logout']"));
        logoutButton.click();
      //  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        System.out.println("6.Logout");
    }
    @Test(dependsOnGroups = "env_applications")
    //@Test
    //@Test(dependsOnMethods = {"signIn", "setup"})
    public void tearDown () throws Exception
    {
        System.out.println("7.Close Browser");
        driver.quit();
    }

}
