package iOS_Tests;

import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class PreSteps extends MainBase {


    @BeforeTest(groups = {"MainBase"})
    public void basicOptionsPre() throws Exception {

            //Test
            TouchAction t = new TouchAction(iosDriver);
            t.tap(PointOption.point(250, 400)).release().perform();

            //        WebDriverWait wait = new WebDriverWait(iosDriver, 30);
            //        wait.until(ExpectedConditions.elementToBeClickable(By
            //        .xpath("//XCUIElementTypeButton[@name=\"Continue\"]")));

            iosDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            List<IOSElement> elements1 = iosDriver.findElements(By.name("Retry"));
            try {
                if (elements1.size() > 0) {
                    System.out.println("INFO: Server error ... ... Driver Quitting " +
                            "\n -----------------------------------------------");
                    Assert.assertFalse(elements1.size() >= 1);
                    iosDriver.quit();
                } else {
                    iosDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                    iosDriver.findElementByAccessibilityId("Continue").click();
                    iosDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                    iosDriver.findElementByAccessibilityId("CONTINUE").click();

                    System.out.println("INFO: Pre-pages passing ... DONE \n -----------------------------------------------");
                    Thread.sleep(3000);
                }
            } catch (NoSuchElementException e) {
                System.out.println("INFO: Error catching: " + e.getMessage());
                iosDriver.quit();
            }
    }
}
