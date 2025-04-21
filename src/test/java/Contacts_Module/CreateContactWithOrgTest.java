package Contacts_Module;

import org.openqa.selenium.By;
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
import POMPages.CreateNewOrgPompage;
import POMPages.HomePomPage;
import POMPages.OrgDetailPomPage;
import POMPages.OrganizationPomPage;

public class CreateContactWithOrgTest extends Baseclass {

	@Test(groups = "regression")
	public void createConwithorgTest() throws Exception {

		WebDriver_Utility w_util = new WebDriver_Utility();

		// Fetch data from excel file
		Excel_Utility ex_util = new Excel_Utility();
		Java_Utility j_util = new Java_Utility();
		int random = j_util.getRandomNumber();
		String contname = ex_util.FetchDataFromExcel("contact_tab", 7, 3) + random;
		String orgname = ex_util.FetchDataFromExcel("contact_tab", 7, 4) + random;

		// Identify org tab in home page and click

		HomePomPage home = new HomePomPage(driver);
		boolean exp_res_home = home.getHeader().contains("Home");
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(exp_res_home, true);

		home.getOrg_tab();

		// Identify plus button and click
		OrganizationPomPage org = new OrganizationPomPage(driver);
		org.getPlusicon();

		// Enter org name in create new org page and save
		CreateNewOrgPompage neworg = new CreateNewOrgPompage(driver);
		neworg.getOrgname_TF(orgname);
		neworg.getSaveBtn();

		// Verify actual org name with expected org name
		OrgDetailPomPage orgdetail = new OrgDetailPomPage(driver);
		boolean exp_res_org = orgdetail.getHeader().contains(orgname);
		Assert.assertEquals(exp_res_org, true);

		// Identify contact tab in home page and click
		home.getCont_tab();

		// Identify plus button and click
		ContactPomPage con = new ContactPomPage(driver);
		con.getPlusicon();

		// Enter org name in create new org page and save
		CreateNewContactPomPage newcon = new CreateNewContactPomPage(driver);
		newcon.getLastname_TF(contname);

		// select org name
		String pwid = driver.getWindowHandle();
		newcon.getOrgplusicon();

		w_util.switchToTabUsingUrl(driver, "module=Accounts&action");

		newcon.getOrgsearchTF(orgname);
		newcon.getOrgsearchBtn();
		driver.findElement(By.xpath("//a[text()='" + orgname + "']")).click();

		w_util.switchBackToParentWindow(driver, pwid);

		newcon.getSaveBtn();

		// Verify actual org name with expected org name
		ContactDetailPomPage condetail = new ContactDetailPomPage(driver);
		boolean exp_res_con = condetail.getHeader().contains(contname);
		Assert.assertEquals(exp_res_con, true);

		// Click on contact tab and delete the created contact
		home.getCont_tab();

		driver.findElement(
				By.xpath("//a[text()='" + contname + "']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();
		Thread.sleep(3000);

		// Handle the popup
		w_util.HandleAlertAndAccept(driver);
		// Click on org tab and delete the created org
		home.getOrg_tab();

		driver.findElement(
				By.xpath("//a[text()='" + orgname + "']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();
		Thread.sleep(3000);

		// Handle the popup
		w_util.HandleAlertAndAccept(driver);
		soft.assertAll();

	}
}
