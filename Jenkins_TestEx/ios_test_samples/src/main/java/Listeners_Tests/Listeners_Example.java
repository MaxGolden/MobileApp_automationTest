package Listeners_Tests;

import com.relevantcodes.extentreports.LogStatus;
import iOS_Tests.MainBase;
import io.appium.java_client.ios.IOSDriver;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.ExtentReports.ExtentManager;
import utils.ExtentReports.ExtentTestManager;

import java.io.File;
import java.io.IOException;

public class Listeners_Example extends MainBase implements ITestListener {

    IOSDriver driver=null;
    private String filePath = "/Users/mj621194/Desktop/SprintFile/AutomationTest/FailedTest_Screenshots/";

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    @Attachment(value = "Page Screenshot", type = "image/png")
    public byte[] saveScreenshotPNG_Allure(IOSDriver driver) {
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Fail info(Scripts)", type = "text/plain")
    public static String saveTextLog_Allure (String message) {
        return message;
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        System.out.println("LISTENER: onStart Test - " + iTestContext.getName());
        ExtentTestManager.startTest(iTestContext.getName(), "Test Start");
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        System.out.println("LISTENER: onFinish Test - " + iTestContext.getName());
        //Do tier down operations for extentreports reporting!
        ExtentManager.getReporter(iTestContext.getName()).flush();
        ExtentTestManager.endTest();
    }
    @Override
    public void onTestStart(ITestResult iTestResult) {
        System.out.println("LISTENER: The Test -" + getTestMethodName(iTestResult) + "- is Starting");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        ExtentTestManager.getTest().log(LogStatus.PASS, iTestResult.getName() + " passed");
    }

    // This belongs to ITestListener and will execute when a test a failed
    @Override
    public void onTestFailure(ITestResult iTestResult)
    {
        String methodName=iTestResult.getName().toString().trim();
        ITestContext context = iTestResult.getTestContext();
        File scrFile = ((TakesScreenshot)iosDriver).getScreenshotAs(OutputType.FILE);
        String screenshotPath= System.getProperty("user.dir") + File.separator + "/ER_screenshots/"+methodName+".png";
        //The below method will save the screen shot in the drive with test method name
        try {
            FileUtils.copyFile(scrFile, new File(screenshotPath));
            System.out.println("LISTENER: Placed screen shot in "+screenshotPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String screenshotsPath = "./ER_screenshots/" + methodName +".png";
        //ExtentReports log and screenshot operations for failed tests.
        ExtentTestManager.getTest().log(LogStatus.FAIL, "Test Failed",
                ExtentTestManager.getTest().addScreenCapture(screenshotsPath));
        saveScreenshotPNG_Allure(iosDriver);
        System.out.println("LISTENER: Test failed ... " + iTestResult.getName() + " --- " + context) ;

    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        System.out.println("LISTENER: The Test -" + getTestMethodName(iTestResult) + "- is Skipped");
        ExtentTestManager.getTest().log(LogStatus.SKIP, "Test Skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        System.out.println("LISTENER: Test failed but it is in defined success ratio - " + getTestMethodName(iTestResult));
    }



}
