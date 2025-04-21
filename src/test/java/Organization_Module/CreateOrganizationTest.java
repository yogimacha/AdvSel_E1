package Organization_Module;

import java.io.IOException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import BaseclassUtility.Baseclass;
import GenericUtilities.Excel_Utility;
import GenericUtilities.Java_Utility;
import GenericUtilities.WebDriver_Utility;
import POMPages.CreateNewOrgPompage;
import POMPages.HomePomPage;
import POMPages.OrgDetailPomPage;
import POMPages.OrganizationPomPage;

public class CreateOrganizationTest extends Baseclass {

	@Test(groups = "smoke")
	public void createOrgTest() throws IOException, InterruptedException {

		// Fetch data from excel file
		Excel_Utility ex_util = new Excel_Utility();
		Java_Utility j_util = new Java_Utility();
		int random = j_util.getRandomNumber();
		String orgname = ex_util.FetchDataFromExcel("org_tab", 1, 3) + random;

		WebDriver_Utility w_util = new WebDriver_Utility();

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
		CreateNewOrgPompage newcon = new CreateNewOrgPompage(driver);
		newcon.getOrgname_TF(orgname);
		newcon.getSaveBtn();

		// Verify actual org name with expected org name
		OrgDetailPomPage orgdetail = new OrgDetailPomPage(driver);
		boolean exp_res_org = orgdetail.getVerifyOrgname().contains(orgname);
		Assert.assertEquals(exp_res_org, true);

		// Click on org tab and delete the created org
		home.getOrg_tab();

		driver.findElement(
				By.xpath("//a[text()='" + orgname + "']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();

		// Handle the popup
		Thread.sleep(3000);

		w_util.HandleAlertAndAccept(driver);
		soft.assertAll();

	}

}
