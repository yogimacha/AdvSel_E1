package BaseclassUtility;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.Status;

import GenericUtilities.ClassObject_Utility;
import GenericUtilities.Database_Utility;
import GenericUtilities.Property_Utility;
import GenericUtilities.WebDriver_Utility;
import POMPages.HomePomPage;
import POMPages.LoginPomPage;

public class Baseclass {
	Property_Utility pro = new Property_Utility();
	Database_Utility db = new Database_Utility();
	WebDriver_Utility w_util = new WebDriver_Utility();
	public WebDriver driver = null;

	public static WebDriver sdriver = null;

	@BeforeSuite(groups = { "smoke", "regression" })
	public void beforeSuite() {
		Reporter.log("Configure the DB:Connect", true);
		ClassObject_Utility.getTest().log(Status.INFO, "Configure the DB:Connect");
		db.getDatabaseConnection();
	}

	@BeforeTest(groups = { "smoke", "regression" })
	public void beforeTest() {
		Reporter.log("BT:Parallel Exe", true);
		ClassObject_Utility.getTest().log(Status.INFO, "BT:Parallel Exe");
	}

//	@Parameters("browser")
	@BeforeClass(groups = { "smoke", "regression" })
	public void beforeClass() throws IOException {
		//Launch the Browser
		Reporter.log("Launch the browser", true);
		ClassObject_Utility.getTest().log(Status.INFO, "Launch the browser");
//		String Browser = pro.FetchDataFromPropFile("browser");
		String Browser = System.getProperty("browser", pro.FetchDataFromPropFile("browser"));
		if (Browser.equals("chrome")) {

			driver = new ChromeDriver();
		} else if (Browser.equals("edge")) {

			driver = new EdgeDriver();
		} else {

			driver = new ChromeDriver();
		}

		sdriver = driver;
		ClassObject_Utility.setDriver(driver);

	}

	@BeforeMethod(groups = { "smoke", "regression" })
	public void beforeMethod() throws IOException {
		Reporter.log("Login to the appln", true);
		ClassObject_Utility.getTest().log(Status.INFO, "Login to the appln");

//		String Url = pro.FetchDataFromPropFile("url");
//		String Username = pro.FetchDataFromPropFile("username");
//		String Password = pro.FetchDataFromPropFile("password");

		String Url = System.getProperty("url", pro.FetchDataFromPropFile("url"));
		String Username = System.getProperty("username", pro.FetchDataFromPropFile("username"));
		String Password = System.getProperty("password", pro.FetchDataFromPropFile("password"));

		LoginPomPage l = new LoginPomPage(driver);
		w_util.navigateToAnAppln(driver, Url);
		w_util.maximizeTheWindow(driver);
		l.login(Username, Password);
		String Timeouts = pro.FetchDataFromPropFile("timeouts");
		w_util.waitTillElementFound(Timeouts, driver);

	}

	@AfterMethod(groups = { "smoke", "regression" })
	public void afterMethod() {
		Reporter.log("Logout of the appln", true);
		ClassObject_Utility.getTest().log(Status.INFO, "Logout of the appln");
		HomePomPage home = new HomePomPage(driver);
		home.logout(driver);

	}

	@AfterClass(groups = { "smoke", "regression" })
	public void afterClass() {
		Reporter.log("Close the browser", true);
		ClassObject_Utility.getTest().log(Status.INFO, "Close the browser");
		WebDriver_Utility wb = new WebDriver_Utility();
		wb.QuitTheBrowser(driver);
	}

	@AfterTest(groups = { "smoke", "regression" })
	public void afterTest() {
		Reporter.log("AT:Parallel Exe", true);
		ClassObject_Utility.getTest().log(Status.INFO, "AT:Parallel Exe");
	}

	@AfterSuite(groups = { "smoke", "regression" })
	public void afterSuite() {
		Reporter.log("Close DB connection", true);
		ClassObject_Utility.getTest().log(Status.INFO, "Close DB connection");
		db.closeDatabaseConnection();
	}

}
