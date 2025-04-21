package ListenersUtility;

import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import BaseclassUtility.Baseclass;
import GenericUtilities.ClassObject_Utility;

public class Listeners implements ITestListener, ISuiteListener {

	public ExtentReports report = null;
	public static ExtentTest test;

	@Override
	public void onStart(ISuite suite) {
		Reporter.log("Report configuration", true);
//		test.log(Status.INFO, "On Suite Execution Started");
		String time = new Date().toString().replace(" ", "_").replace(":", "_");

		// Report configuration
		ExtentSparkReporter spark = new ExtentSparkReporter("./AdvancedReports/Report" + time + ".html");
		spark.config().setDocumentTitle("VTiger Suite Execution Report");
		spark.config().setReportName("VTiger Report");
		spark.config().setTheme(Theme.DARK);

		// Add Environment Details
		report = new ExtentReports();
		report.attachReporter(spark);
		report.setSystemInfo("OS Version", "Windows-11");
		report.setSystemInfo("Browser", "Chrome-135");
		test = report.createTest("VTiger Runtime Events");
		ClassObject_Utility.setTest(test);
	}

	@Override
	public void onFinish(ISuite suite) {
		Reporter.log("Report Backup", true);
		report.flush();
		test.log(Status.INFO, "Suite Execution Finished");
	}

	@Override
	public void onTestStart(ITestResult result) {
		Reporter.log(result.getMethod().getMethodName() + "--Started--", true);
		test = report.createTest(result.getMethod().getMethodName());
		test.log(Status.INFO, result.getMethod().getMethodName() + "--Started--");

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		Reporter.log(result.getMethod().getMethodName() + "--Success--", true);
		test.log(Status.INFO, result.getMethod().getMethodName() + "--Success--");

	}

	@Override
	public void onTestFailure(ITestResult result) {
		String testname = result.getMethod().getMethodName();
		Reporter.log(testname + "--Failed--", true);
		test.log(Status.FAIL, testname + "--Failed--");

		String time = new Date().toString().replace(" ", "_").replace(":", "_");
		TakesScreenshot ts = (TakesScreenshot) Baseclass.sdriver;
		String filepath = ts.getScreenshotAs(OutputType.BASE64);
		test.addScreenCaptureFromBase64String(filepath, testname + "_" + time);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		Reporter.log(result.getMethod().getMethodName() + "--Skipped--", true);
		test.log(Status.INFO, result.getMethod().getMethodName() + "--Skipped--");

	}

}
