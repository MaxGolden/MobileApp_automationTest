package utils.ExtentReports;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {

    private static ExtentReports extent;
    public synchronized static ExtentReports getReporter(String testName) {
        if (extent == null) {
            //Set HTML reporting file location
//            String workingDir = System.getProperty("/Users/mj621194/Desktop/SprintFile/AutomationTest/FailedTest_Screenshots");
            String workingDir = System.getProperty("/Users/mj621194/Documents/Java_Max/IntellijProjects_All/MySprint_ios_testng/");
            extent = new ExtentReports(workingDir + testName +"_ExtentReport.html", true);
        }
        return extent;
    }
}
