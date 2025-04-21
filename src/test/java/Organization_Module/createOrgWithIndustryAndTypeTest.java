package Organization_Module;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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

public class createOrgWithIndustryAndTypeTest extends Baseclass {

	@Test(groups = "regression")
	public void createOrgWithIndAndType() throws Exception {

		// Fetch data from excel file
		Excel_Utility ex_util = new Excel_Utility();
		Java_Utility j_util = new Java_Utility();
		int random = j_util.getRandomNumber();
		String orgname = ex_util.FetchDataFromExcel("org_tab", 7, 3) + random;
		String industry = ex_util.FetchDataFromExcel("org_tab", 7, 4);
		String type = ex_util.FetchDataFromExcel("org_tab", 7, 5);

		WebDriver_Utility w_util = new WebDriver_Utility();

		// Identify org tab in home page and click
		HomePomPage home = new HomePomPage(driver);
		boolean exp_res_home = home.getHeader().contains("Home");
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(exp_res_home, true);
		home.getOrg_tab();

		// Identify plus button andd click
		OrganizationPomPage org = new OrganizationPomPage(driver);
		org.getPlusicon();

		// Enter org name in create new org page and save
		CreateNewOrgPompage neworg = new CreateNewOrgPompage(driver);
		neworg.getOrgname_TF(orgname);
		WebElement ind_dd = neworg.getOrgIndustryDD();
		WebElement type_dd = neworg.getOrgTypeDD();

		w_util.HandleDropdownUsingValue(ind_dd, industry);
		w_util.HandleDropdownUsingValue(type_dd, type);

		neworg.getSaveBtn();

		// Verify actual org name with expected org name
		OrgDetailPomPage orgdetail = new OrgDetailPomPage(driver);
		boolean exp_res_org = orgdetail.getHeader().contains(orgname);
		Assert.assertEquals(exp_res_org, true);

		// Verify actual industry with expected industry
		boolean exp_ind = orgdetail.getVerifyIndustry().contains(industry);
		Assert.assertEquals(exp_ind, true);

		// Verify actual type with expected type
		boolean exp_type = orgdetail.getVerifyType().contains(type);
		Assert.assertEquals(exp_type, true);

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
