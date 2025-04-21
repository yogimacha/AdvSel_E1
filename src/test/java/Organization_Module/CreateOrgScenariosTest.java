package Organization_Module;

import java.io.IOException;

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
import POMPages.CreateNewOrgPompage;
import POMPages.HomePomPage;
import POMPages.OrgDetailPomPage;
import POMPages.OrganizationPomPage;

public class CreateOrgScenariosTest extends Baseclass {
	@Test(groups = "smoke", retryAnalyzer = ListenersUtility.RetryAnalyser_utility.class)
	public void createOrgTest() throws IOException, InterruptedException {

		ClassObject_Utility.getTest().log(Status.INFO, "Fetching Data From Excel File");
		Excel_Utility ex_util = new Excel_Utility();
		Java_Utility j_util = new Java_Utility();
		int random = j_util.getRandomNumber();
		String orgname = ex_util.FetchDataFromExcel("org_tab", 1, 3) + random;

		WebDriver_Utility w_util = new WebDriver_Utility();

		ClassObject_Utility.getTest().log(Status.INFO, "Verifying home page");
		HomePomPage home = new HomePomPage(driver);
		boolean exp_res_home = home.getHeader().contains("Home");
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(exp_res_home, true);

		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to org page");
		home.getOrg_tab();

		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to create new org page");
		OrganizationPomPage org = new OrganizationPomPage(driver);
		org.getPlusicon();

		ClassObject_Utility.getTest().log(Status.INFO, "create new org");
		CreateNewOrgPompage newcon = new CreateNewOrgPompage(driver);
		newcon.getOrgname_TF(orgname);
		newcon.getSaveBtn();

		ClassObject_Utility.getTest().log(Status.INFO, "Verify org name");
		OrgDetailPomPage orgdetail = new OrgDetailPomPage(driver);
		boolean exp_res_org = orgdetail.getVerifyOrgname().contains(orgname);
		Assert.assertEquals(exp_res_org, true);

		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to org tab and delete");
		home.getOrg_tab();

		driver.findElement(
				By.xpath("//a[text()='" + orgname + "']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();

		ClassObject_Utility.getTest().log(Status.INFO, "Delete popup handled");
		Thread.sleep(3000);

		w_util.HandleAlertAndAccept(driver);
		soft.assertAll();

	}

	@Test(groups = "regression", retryAnalyzer = ListenersUtility.RetryAnalyser_utility.class)
	public void createOrgWithPhnNoTest() throws Exception {

		ClassObject_Utility.getTest().log(Status.INFO, "Fetching Data From Excel File");
		Excel_Utility ex_util = new Excel_Utility();
		Java_Utility j_util = new Java_Utility();
		int random = j_util.getRandomNumber();
		String orgname = ex_util.FetchDataFromExcel("org_tab", 4, 3) + random;
		String phno = ex_util.FetchDataFromExcel("org_tab", 4, 4);

		WebDriver_Utility w_util = new WebDriver_Utility();

		ClassObject_Utility.getTest().log(Status.INFO, "Verifying home page");
		HomePomPage home = new HomePomPage(driver);
		boolean exp_res_home = home.getHeader().contains("Home");
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(exp_res_home, true);

		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to org tab");
		home.getOrg_tab();

		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to create new org page");
		OrganizationPomPage org = new OrganizationPomPage(driver);
		org.getPlusicon();

		ClassObject_Utility.getTest().log(Status.INFO, "Creating new org with phno");
		CreateNewOrgPompage neworg = new CreateNewOrgPompage(driver);
		neworg.getOrgname_TF(orgname);
		neworg.getOrgphno_TF(phno);
		neworg.getSaveBtn();

		ClassObject_Utility.getTest().log(Status.INFO, "verify org name and phno");
		OrgDetailPomPage orgdetail = new OrgDetailPomPage(driver);
		boolean exp_res_org = orgdetail.getHeader().contains(orgname);
		Assert.assertEquals(exp_res_org, true);

		boolean exp_res_phno = orgdetail.getVerifyOrgPhno().contains(phno);
		Assert.assertEquals(exp_res_phno, true);

		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to org tab and delete");
		home.getOrg_tab();

		driver.findElement(
				By.xpath("//a[text()='" + orgname + "']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();
		Thread.sleep(3000);

		ClassObject_Utility.getTest().log(Status.INFO, "Delete popup handled");
		w_util.HandleAlertAndAccept(driver);
		soft.assertAll();

	}

	@Test(groups = "regression", retryAnalyzer = ListenersUtility.RetryAnalyser_utility.class)
	public void createOrgWithIndAndType() throws Exception {

		ClassObject_Utility.getTest().log(Status.INFO, "Fetching Data From Excel File");
		Excel_Utility ex_util = new Excel_Utility();
		Java_Utility j_util = new Java_Utility();
		int random = j_util.getRandomNumber();
		String orgname = ex_util.FetchDataFromExcel("org_tab", 7, 3) + random;
		String industry = ex_util.FetchDataFromExcel("org_tab", 7, 4);
		String type = ex_util.FetchDataFromExcel("org_tab", 7, 5);

		WebDriver_Utility w_util = new WebDriver_Utility();

		ClassObject_Utility.getTest().log(Status.INFO, "Verifying home page");
		HomePomPage home = new HomePomPage(driver);
		boolean exp_res_home = home.getHeader().contains("Home");
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(exp_res_home, true);
		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to org tab");
		home.getOrg_tab();

		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to create new org page");
		OrganizationPomPage org = new OrganizationPomPage(driver);
		org.getPlusicon();

		ClassObject_Utility.getTest().log(Status.INFO, "Creating new org with industry and type");
		CreateNewOrgPompage neworg = new CreateNewOrgPompage(driver);
		neworg.getOrgname_TF(orgname);
		WebElement ind_dd = neworg.getOrgIndustryDD();
		WebElement type_dd = neworg.getOrgTypeDD();

		w_util.HandleDropdownUsingValue(ind_dd, industry);
		w_util.HandleDropdownUsingValue(type_dd, type);

		neworg.getSaveBtn();

		ClassObject_Utility.getTest().log(Status.INFO, "Verify org name");
		OrgDetailPomPage orgdetail = new OrgDetailPomPage(driver);
		boolean exp_res_org = orgdetail.getHeader().contains(orgname);
		Assert.assertEquals(exp_res_org, true);

		ClassObject_Utility.getTest().log(Status.INFO, "verify indutry and type");
		boolean exp_ind = orgdetail.getVerifyIndustry().contains(industry);
		Assert.assertEquals(exp_ind, true);

		boolean exp_type = orgdetail.getVerifyType().contains(type);
		Assert.assertEquals(exp_type, true);

		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to org tab and delete");
		home.getOrg_tab();

		driver.findElement(
				By.xpath("//a[text()='" + orgname + "']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();
		Thread.sleep(3000);

		ClassObject_Utility.getTest().log(Status.INFO, "Delete popup handled");
		w_util.HandleAlertAndAccept(driver);
		soft.assertAll();

	}

}
