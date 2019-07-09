package iOS_Tests;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;



import java.io.File;
import java.net.URL;

public class MainBase {

    public static IOSDriver<IOSElement> iosDriver=null;

    @BeforeSuite(groups = "MainBase")
    public static IOSDriver<IOSElement> setUp() throws Exception {
        File appDir = new File("src/main/java/iOS_Tests");
        File app = new File(appDir, "SprintBeta.app");

        DesiredCapabilities d = new DesiredCapabilities();
        d.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 8");
        d.setCapability(MobileCapabilityType.PLATFORM_NAME, "IOS");
        d.setCapability(MobileCapabilityType.PLATFORM_VERSION, "12.2");
        d.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
        d.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        iosDriver = new IOSDriver<IOSElement>(new URL("http://127.0.0.1:4723/wd/hub"), d);
        System.out.println("My Sprint Beta - \n Test Device: IPhone 8 \n App Version: Latest(Unknown) \n Others: N/A" +
                "\n -----------------------------------------------");

        return iosDriver;
    }

    @AfterSuite(groups = "MainBase")
    public void tearDown() {
        iosDriver.quit();

        System.out.println("INFO: Test Done, iOS Driver Quitting ...\n -----------------------------------------------");
    }
}
