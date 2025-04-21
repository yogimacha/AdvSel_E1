package Contacts_Module;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Listeners;
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

//@Listeners(ListenersUtility.Listeners.class)
public class CreateContactTest extends Baseclass {

	@Test(groups = "smoke")
	public void createConTest() throws Exception {

		WebDriver_Utility w_util = new WebDriver_Utility();
		// Fetch data from excel file
		Excel_Utility ex_util = new Excel_Utility();
		Java_Utility j_util = new Java_Utility();
		int random = j_util.getRandomNumber();
		String contname = ex_util.FetchDataFromExcel("contact_tab", 1, 3) + random;

		HomePomPage home = new HomePomPage(driver);
		boolean exp_res_home = home.getHeader().contains("Home");
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(exp_res_home, true);

		// Identify contact tab in home page and click
		home.getCont_tab();

		// Identify plus button and click
		ContactPomPage con = new ContactPomPage(driver);
		con.getPlusicon();

		// Enter contact name in create new org page and save
		CreateNewContactPomPage newcon = new CreateNewContactPomPage(driver);
		newcon.getLastname_TF(contname);
		newcon.getSaveBtn();

		// Verify actual contact name with expected org name
		ContactDetailPomPage condetail = new ContactDetailPomPage(driver);
		boolean exp_res_con = condetail.getHeader().contains(contname);
		Assert.assertEquals(exp_res_con, true);

		// Click on contact tab and delete the created org
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
