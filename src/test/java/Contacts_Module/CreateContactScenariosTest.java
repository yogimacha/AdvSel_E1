package Contacts_Module;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import BaseclassUtility.Baseclass;
import GenericUtilities.ClassObject_Utility;
import GenericUtilities.Excel_Utility;
import GenericUtilities.Java_Utility;
import GenericUtilities.WebDriver_Utility;
import POMPages.ContactDetailPomPage;
import POMPages.ContactPomPage;
import POMPages.CreateNewContactPomPage;
import POMPages.CreateNewOrgPompage;
import POMPages.HomePomPage;
import POMPages.OrgDetailPomPage;
import POMPages.OrganizationPomPage;
//Git conflict in new rep
public class CreateContactScenariosTest extends Baseclass {

	@Test(groups = "smoke", retryAnalyzer = ListenersUtility.RetryAnalyser_utility.class)
	public void createConTest() throws Exception {

		WebDriver_Utility w_util = new WebDriver_Utility();

		ClassObject_Utility.getTest().log(Status.INFO, "Fetching Data From Excel File");
		Excel_Utility ex_util = new Excel_Utility();
		Java_Utility j_util = new Java_Utility();
		int random = j_util.getRandomNumber();
		String contname = ex_util.FetchDataFromExcel("contact_tab", 1, 3) + random;

		ClassObject_Utility.getTest().log(Status.INFO, "Home page verification");
		HomePomPage home = new HomePomPage(driver);
		boolean exp_res_home = home.getHeader().contains("Home");
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(exp_res_home, true);

		// Identify contact tab in home page and click
		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to Contacts Tab");
		home.getCont_tab();

		// Identify plus button and click
		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to Create New Contact page");
		ContactPomPage con = new ContactPomPage(driver);
		con.getPlusicon();

		// Enter contact name in create new org page and save
		ClassObject_Utility.getTest().log(Status.INFO, "Creating New contact");
		CreateNewContactPomPage newcon = new CreateNewContactPomPage(driver);
		newcon.getLastname_TF(contname);
		newcon.getSaveBtn();

		// Verify actual contact name with expected org name
		ClassObject_Utility.getTest().log(Status.INFO, "Verifying the contact");
		ContactDetailPomPage condetail = new ContactDetailPomPage(driver);
		boolean exp_res_con = condetail.getHeader().contains(contname);
		Assert.assertEquals(exp_res_con, true);

		// Click on contact tab and delete the created org
		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to contact tab and delete contact");
		home.getCont_tab();
		driver.findElement(
				By.xpath("//a[text()='" + contname + "']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();
		Thread.sleep(3000);
		// Handle the popup
		ClassObject_Utility.getTest().log(Status.INFO, "Handling delete popoup");
		w_util.HandleAlertAndAccept(driver);
		soft.assertAll();
	}

	@Test(groups = "regression", retryAnalyzer = ListenersUtility.RetryAnalyser_utility.class)
	public void createConwithorgTest() throws Exception {

		WebDriver_Utility w_util = new WebDriver_Utility();

		ClassObject_Utility.getTest().log(Status.INFO, "Fetching the data from excel");

		Excel_Utility ex_util = new Excel_Utility();
		Java_Utility j_util = new Java_Utility();
		int random = j_util.getRandomNumber();
		String contname = ex_util.FetchDataFromExcel("contact_tab", 7, 3) + random;
		String orgname = ex_util.FetchDataFromExcel("contact_tab", 7, 4) + random;

		ClassObject_Utility.getTest().log(Status.INFO, "Verifying home page");

		HomePomPage home = new HomePomPage(driver);
		boolean exp_res_home = home.getHeader().contains("Home");
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(exp_res_home, true);

		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to org tab");
		home.getOrg_tab();

		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to create new org");
		OrganizationPomPage org = new OrganizationPomPage(driver);
		org.getPlusicon();

		ClassObject_Utility.getTest().log(Status.INFO, "Create new org");
		CreateNewOrgPompage neworg = new CreateNewOrgPompage(driver);
		neworg.getOrgname_TF(orgname);
		neworg.getSaveBtn();

		ClassObject_Utility.getTest().log(Status.INFO, "Verifying org name");
		OrgDetailPomPage orgdetail = new OrgDetailPomPage(driver);
		boolean exp_res_org = orgdetail.getHeader().contains(orgname);
		Assert.assertEquals(exp_res_org, true);

		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to Contact tab");
		home.getCont_tab();

		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to create new contact page");
		ContactPomPage con = new ContactPomPage(driver);
		con.getPlusicon();

		ClassObject_Utility.getTest().log(Status.INFO, "Create new contact with org name");
		CreateNewContactPomPage newcon = new CreateNewContactPomPage(driver);
		newcon.getLastname_TF(contname);

		ClassObject_Utility.getTest().log(Status.INFO, "Select org name from Child window");
		String pwid = driver.getWindowHandle();
		newcon.getOrgplusicon();

		w_util.switchToTabUsingUrl(driver, "module=Accounts&action");

		newcon.getOrgsearchTF(orgname);
		newcon.getOrgsearchBtn();
		driver.findElement(By.xpath("//a[text()='" + orgname + "']")).click();

		w_util.switchBackToParentWindow(driver, pwid);

		newcon.getSaveBtn();

		ClassObject_Utility.getTest().log(Status.INFO, "Verifying contact name with orgname");
		ContactDetailPomPage condetail = new ContactDetailPomPage(driver);
		boolean exp_res_con = condetail.getHeader().contains(contname);
		Assert.assertEquals(exp_res_con, true);

		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to contact tab and delete");
		home.getCont_tab();

		driver.findElement(
				By.xpath("//a[text()='" + contname + "']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();
		Thread.sleep(3000);

		ClassObject_Utility.getTest().log(Status.INFO, "Handling delete popoup");
		w_util.HandleAlertAndAccept(driver);
		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to org tab and delete");
		home.getOrg_tab();

		driver.findElement(
				By.xpath("//a[text()='" + orgname + "']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();
		Thread.sleep(3000);

		ClassObject_Utility.getTest().log(Status.INFO, "Handling delete popoup");
		w_util.HandleAlertAndAccept(driver);
		soft.assertAll();

	}

	@Test(groups = "regression", retryAnalyzer = ListenersUtility.RetryAnalyser_utility.class)
	public void createConwithSuppDateTest() throws Exception {

		WebDriver_Utility w_util = new WebDriver_Utility();

		ClassObject_Utility.getTest().log(Status.INFO, "Fetching the data from excel");
		Excel_Utility ex_util = new Excel_Utility();
		Java_Utility j_util = new Java_Utility();
		int random = j_util.getRandomNumber();
		String contname = ex_util.FetchDataFromExcel("contact_tab", 4, 3) + random;

		ClassObject_Utility.getTest().log(Status.INFO, "verifying home page");
		HomePomPage home = new HomePomPage(driver);
		boolean exp_res_home = home.getHeader().contains("Home");
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(exp_res_home, true);

		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to contact page");
		home.getCont_tab();

		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to create new contact page");
		ContactPomPage con = new ContactPomPage(driver);
		con.getPlusicon();

		ClassObject_Utility.getTest().log(Status.INFO, "Creating new contact with support date");
		CreateNewContactPomPage newcon = new CreateNewContactPomPage(driver);
		newcon.getLastname_TF(contname);

		// specify start and end Support date
		WebElement start_dateTF = newcon.getConStartDate_TF();
		start_dateTF.clear();
		String startdate = j_util.getCurrentDate();
		start_dateTF.sendKeys(startdate);

		WebElement end_dateTF = newcon.getConEndDate_TF();
		end_dateTF.clear();
		String enddate = j_util.getDateAftergivendays(30);
		end_dateTF.sendKeys(enddate);

		newcon.getSaveBtn();

		ClassObject_Utility.getTest().log(Status.INFO, "Verifing contact name");
		ContactDetailPomPage condetail = new ContactDetailPomPage(driver);
		boolean exp_res_con = condetail.getHeader().contains(contname);
		Assert.assertEquals(exp_res_con, true);

		ClassObject_Utility.getTest().log(Status.INFO, "Verifying support date");
		boolean exp_strtdate = condetail.getVerifyStartdate().contains(startdate);
		Assert.assertEquals(exp_strtdate, true);

		boolean exp_enddate = condetail.getVerifyEnddate().contains(enddate);
		Assert.assertEquals(exp_enddate, true);

		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to Contact page and delete");
		home.getCont_tab();

		driver.findElement(
				By.xpath("//a[text()='" + contname + "']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();
		Thread.sleep(3000);

		ClassObject_Utility.getTest().log(Status.INFO, "Delete popup handled");
		w_util.HandleAlertAndAccept(driver);
		soft.assertAll();

	}
}
