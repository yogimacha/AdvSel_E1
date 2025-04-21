package Contacts_Module;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import BaseclassUtility.Baseclass;
import GenericUtilities.Excel_Utility;
import GenericUtilities.Java_Utility;
import GenericUtilities.WebDriver_Utility;
import POMPages.ContactDetailPomPage;
import POMPages.ContactPomPage;
import POMPages.CreateNewContactPomPage;
import POMPages.HomePomPage;

public class CreateContactWithSupportDateTest extends Baseclass {

	@Test(groups = "regression")
	public void createConwithSuppDateTest() throws Exception {

		WebDriver_Utility w_util = new WebDriver_Utility();

		// Fetch data from excel file
		Excel_Utility ex_util = new Excel_Utility();
		Java_Utility j_util = new Java_Utility();
		int random = j_util.getRandomNumber();
		String contname = ex_util.FetchDataFromExcel("contact_tab", 4, 3) + random;

		// Identify contact tab in home page and click
		HomePomPage home = new HomePomPage(driver);
		boolean exp_res_home = home.getHeader().contains("Home");
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(exp_res_home, true);
		home.getCont_tab();

		// Identify plus button and click
		ContactPomPage con = new ContactPomPage(driver);
		con.getPlusicon();

		// Enter org name in create new org page and save
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

		// Verify actual org name with expected org name
		ContactDetailPomPage condetail = new ContactDetailPomPage(driver);
		boolean exp_res_con = condetail.getHeader().contains(contname);
		Assert.assertEquals(exp_res_con, true);

		// Verify start support date and end support date
		boolean exp_strtdate = condetail.getVerifyStartdate().contains(startdate);
		Assert.assertEquals(exp_strtdate, true);

		boolean exp_enddate = condetail.getVerifyEnddate().contains(enddate);
		Assert.assertEquals(exp_enddate, true);

		// Click on org tab and delete the created org
		home.getCont_tab();

		driver.findElement(
				By.xpath("//a[text()='" + contname + "']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();
		Thread.sleep(3000);

		// Handle the popup
		w_util.HandleAlertAndAccept(driver);
		soft.assertAll();

	}
}