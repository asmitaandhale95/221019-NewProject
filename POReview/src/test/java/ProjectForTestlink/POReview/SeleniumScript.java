package ProjectForTestlink.POReview;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import junit.framework.TestCase;
import testlink.api.java.client.TestLinkAPIClient;
import testlink.api.java.client.TestLinkAPIException;
import testlink.api.java.client.TestLinkAPIResults;

public class SeleniumScript extends TestCase 
{
	public WebDriver driver;
	public static String APIKey = "be10a8bcee1c212d1b072dc094bb9942";
	public static String serverUrl = "http://127.0.0.1:8666/testlink-1.9.19/lib/api/xmlrpc/v1/xmlrpc.php";
	public static  String testlinkprojectName = "POReview";
	public static  String testPlanName = "NewPOReviewPlan";
	public static  String testCaseName = "PO---1";
	public static  String buildName = "POBuild1";

	//Declare result and exceptiion variable for displaying result into testlink
	
		@BeforeTest
		public void openMyBlog() throws Exception
		{
				ChromeOptions chromeOptions= new ChromeOptions();
				//Set the path of chromt.exe 
				chromeOptions.setBinary("C:\\Users\\user1\\AppData\\Local\\Google\\Chrome\\Application\\chrome.exe");
				//Find out the location of chrome.exe :-
				chromeOptions.addArguments("--disable-features=VizDisplayCompositor");
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
				String path = System.getProperty("user.dir");
				System.out.println(path);
				System.setProperty("webdriver.chrome.driver",path+"\\resources\\chromedriver.exe");
				
				
				String result = "";
				String exception = "";
				
				try
				{
				driver = new ChromeDriver(chromeOptions);
				System.out.println("Chrome Browser window is opened");
				driver.manage().timeouts().implicitlyWait(90,TimeUnit.SECONDS);
				driver.manage().window().maximize();
				String baseUrl = "http://192.168.1.12/Aras11_SP8_PCCS/Client/X-salt=std_11.0.0.6493-X/scripts/Innovator.aspx";
				driver.get(baseUrl);
				System.out.println("Aras URL is hit");
				
				driver.switchTo().frame("main");
				//Enter username
				driver.findElement(By.id("username")).sendKeys("Flx_QDM_Admin1");
				System.out.println("Username is entered");
				
				//Enter Password
				driver.findElement(By.id("password")).sendKeys("123");
				System.out.println("Password is entered");
				
				//Select the database
				Select selectdb = new Select(driver.findElement(By.id("database_select")));
				selectdb.selectByIndex(0);
				
				//Click on login button
				driver.findElement(By.id("login.login_btn_label")).click();
				System.out.println("Login is Successful");
				result = TestLinkAPIResults.TEST_PASSED;
				updateResult("PO---1",null,result);
				}
				catch(Exception e)
				{
					result = TestLinkAPIResults.TEST_FAILED;
			        exception = e.getMessage();
			        updateResult("PO---1",exception,result);
				}
  }
		@Test
		public void CreateNewPO() throws Exception
		{
			WebDriverWait wait = new WebDriverWait(driver, 30);
			//driver.manage().timeouts().implicitlyWait(40,TimeUnit.SECONDS);
			WebElement expanded = driver.findElement(By.id("main_frameset"));
			driver.switchTo().frame("main");
			WebElement expandPoReview = driver.findElement(By.xpath("//span[@class='dijitTreeLabel' and text() ='PCC Dynamic PO Review']//parent::span//parent::div//child::span[1]"));
			expandPoReview.click();
			expandPoReview = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='dijitTreeLabel' and text()='PCC Dynamic PO Review']//parent::span//parent::div//child::span[1]")));
			Thread.sleep(3000);
			//Click on the PO Review label
			WebElement poReview = driver.findElement(By.xpath("//span[@class='dijitTreeLabel' and text()='PO Reviews']"));
			poReview = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='dijitTreeLabel' and text()='PO Reviews']")));
			Thread.sleep(3000);
			poReview.click();	
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			WebElement poReviewNew = driver.findElement(By.xpath("//img[@src='../javascript/dojo/../../cbin/../images/New.svg']"));
			poReviewNew = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@src='../javascript/dojo/../../cbin/../images/New.svg']")));
			Thread.sleep(3000);
			poReviewNew.click();
			System.out.println("Click on + button to create new ");
			System.out.println("Task complete");
			
		}
		@AfterTest
		public void QuitBrowser()
		{
			driver.quit();	
		}
		public void updateResult(String testCaseName, String exception, String results) throws TestLinkAPIException 
		{
				TestLinkAPIClient testlink = new TestLinkAPIClient(APIKey,serverUrl);
				testlink.reportTestCaseResult(testlinkprojectName, testPlanName, testCaseName, buildName, exception, results);
		}
}
