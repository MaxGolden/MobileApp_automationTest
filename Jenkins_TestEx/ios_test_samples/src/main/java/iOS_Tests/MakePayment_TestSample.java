package iOS_Tests;

import Listeners_Tests.Listeners_Example;
import io.appium.java_client.ios.IOSElement;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static Listeners_Tests.Listeners_Example.saveTextLog_Allure;

@Listeners({Listeners_Example.class})
@Epic("Allure_Examples")
@Feature("Make_payment")
public class MakePayment_TestSample extends MainBase {

    @Test(groups = {"NonLogin", "MakePayment"}, priority = 1)
    @Description("Click Button and select 'Pay with Debit/Credit Card'")
    @Story("Click Button and select 'Pay with Debit/Credit Card'")
    public void MakePayment_PayWithCards()
    {
        iosDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        iosDriver.findElementByAccessibilityId("Make a payment").click();

        iosDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        iosDriver.findElementById("Pay with Debit/Credit Card").click();
        System.out.println("INFO: Click Button and select 'Pay with Debit/Credit Card'");
    }

    // Default parts test
    @Test(groups = {"NonLogin", "MakePayment"}, priority = 2)
    public void MakePayment_CameraAllow()
    {
        // Allow Camera if first launch
        iosDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        List<IOSElement> camera_m =iosDriver.findElementsById("Don't Allow");
        try {
            if(camera_m.size() > 0) {
                System.out.println("INFO: Camera Already been allowed.");
                saveTextLog_Allure("Allure_INFO: Camera Already been allowed.");
            } else {
                iosDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
                iosDriver.findElementById("OK").click();
                System.out.println("INFO: Allow Camera accessing ...");
                saveTextLog_Allure("Allure_INFO: Allow Camera accessing ...");
            }
        }catch (NoSuchElementException e) {
            System.out.println("Error: No such element found!");
        }
    }

    @Test(groups = {"NonLogin", "MakePayment"}, priority = 3)
    public void MakePayment_MoreInfoIcon() throws InterruptedException
    {
        // More info icon click
        iosDriver.findElementByAccessibilityId("More info").click();
        Thread.sleep(1000);
        iosDriver.navigate().back(); //Navigate back
        iosDriver.findElementByAccessibilityId("More info").click();

        iosDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        iosDriver.findElementByAccessibilityId("OK").click(); //Click Button 'OK' to get back

        System.out.println("Info: More Info Icon Click - Done");
    }

    @Test(groups = {"NonLogin", "MakePayment"}, priority = 3)
    public void MakePayment_ContinueEnable_Default1()
    {
        iosDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        Assert.assertFalse(iosDriver.findElementByAccessibilityId("Continue").isEnabled());
        System.out.println("INFO: Continue - Default status is disabled ... Passed");
    }

    @Test(groups = {"NonLogin", "MakePayment"}, priority = 4)
    public void MakePayment_ContinueEnable_Default2()
    {
        iosDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        iosDriver.findElementById("$0.00").sendKeys("100");
        iosDriver.findElementById("Name on card").sendKeys("Max");
        iosDriver.findElementById("Card number").sendKeys("4055011111111111");
        iosDriver.findElementById("Expiration date").click();
        iosDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        iosDriver.findElementById("July").sendKeys("December");
        iosDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        iosDriver.findElementById("2019").sendKeys("2021");
        iosDriver.findElementById("Done").click();
        iosDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        iosDriver.findElementById("ZIP").sendKeys("66223");
        iosDriver.findElementById("CVV number").sendKeys("000");
        iosDriver.findElementById("Done").click();

        iosDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        Assert.assertTrue(iosDriver.findElementByAccessibilityId("Continue").isEnabled());
        System.out.println("INFO: Fill card information to test the 'Continue' button is displayed ... Passed");
    }

    // =======================================
    // Amount part tests
    @Test(groups = {"NonLogin", "MakePayment"}, priority = 5)
    public void MakePayment_Amount_ErrorM1() throws InterruptedException
    {
        iosDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        iosDriver.findElementById("$1.00").clear();
        iosDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        iosDriver.findElementById("0.00").sendKeys("99"); //Less than 1
        iosDriver.findElementById("Next").click();

        Thread.sleep(500);
        iosDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        List<IOSElement> error_m = iosDriver.findElementsById(" - Min. payment amount limit $1");
        try {
            if(error_m.size() > 0) {
                System.out.println("INFO: Amount less than $1 - Error Message 'Min. payment amount limit $1' " +
                        "should show ... Passed");
            } else {
                System.out.println("INFO: Amount less than $1 - Error Message 'Min. payment amount limit $1' " +
                        "should show ... Failed");
                saveTextLog_Allure("INFO: Amount less than $1 - Error Message 'Min. payment amount limit $1' " +
                        "should show ... Failed");
                Assert.fail();
            }
        }catch (NoSuchElementException e) {
            System.out.println("No elements found");
        }
    }

    @Test(groups = {"NonLogin", "MakePayment"}, priority = 5)
    public void MakePayment_Amount_ContinueDisabled1() throws InterruptedException
    {
        Thread.sleep(500);
        Assert.assertFalse(iosDriver.findElementByAccessibilityId("Continue").isEnabled());
        System.out.println("INFO: 'Continue' Button should disabled after Amount error message showed ... Passed");
    }

    @Test(groups = {"NonLogin", "MakePayment"}, priority = 6)
    public void MakePayment_Amount_ErrorM2() throws InterruptedException
    {
        iosDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        iosDriver.findElementById("$0.99").clear(); //clear Amount number
        iosDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        iosDriver.findElementById("0.00").sendKeys("200001"); //More than $2000
        iosDriver.findElementById("Next").click();

        Thread.sleep(500);
        iosDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        List<IOSElement> error_m = iosDriver.findElementsById("- Max. payment amount limit $2000");
        try {
            if(error_m.size() > 0) {
                System.out.println("INFO: Amount more than $2000 - Error Message '- Max. payment amount limit $2000' " +
                        "should show ... Passed");
            } else {
                System.out.println("INFO: Amount more than $2000 - Error Message '- Max. payment amount limit $2000' " +
                        "should show ... Failed");
                saveTextLog_Allure("INFO: Amount more than $2000 - Error Message '- Max. payment amount limit $2000' " +
                        "should show ... Failed");
                Assert.fail();
            }
        }catch (NoSuchElementException e) {
            System.out.println("No elements found");
        }
    }

    @Test(groups = {"NonLogin", "MakePayment"}, priority = 7)
    public void MakePayment_Amount_ContinueDisabled2() throws InterruptedException
    {
        Thread.sleep(500);
        Assert.assertFalse(iosDriver.findElementByAccessibilityId("Continue").isEnabled());
        System.out.println("INFO: 'Continue' Button should disabled after Amount error message showed ... Passed");
    }

    @Test(groups = {"NonLogin", "MakePayment"}, priority = 8)
    public void MakePayment_Amount_SpecialSign1() throws InterruptedException
    {
        iosDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        iosDriver.findElementById("$2,000.01").clear(); //clear Amount number
        iosDriver.findElementById("0.00").sendKeys("100~!@#$%^&*"); //Special Sign

        Thread.sleep(500);
        iosDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        List<IOSElement> error_m = iosDriver.findElementsById("1.00");
        try {
            if(error_m.size() > 0) {
                System.out.println("INFO: The special signs should not put in the Amount field ... Passed");
            } else {
                System.out.println("INFO: The special signs should not put in the Amount field ... Failed");
                saveTextLog_Allure("INFO: The special signs should not put in the Amount field ... Failed");
                Assert.fail();
            }
        }catch (NoSuchElementException e) {
            System.out.println("No elements found");
        }
    }

    @Test(groups = {"NonLogin", "MakePayment"}, priority = 9)
    public void MakePayment_Amount_SpecialSign2()
    {
        Assert.assertFalse(iosDriver.findElementByAccessibilityId("Continue").isEnabled());
        System.out.println("INFO: 'Continue' Should be disable when special signs in the amount field ... Passed");
    }

    @Test(groups = {"NonLogin", "MakePayment"}, priority = 10)
    public void MakePayment_Amount_SpecialSign3() throws InterruptedException
    {
        Thread.sleep(500);
        iosDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        List<IOSElement> button_next = iosDriver.findElementsById("Next");
        button_next.get(0).click();

        Thread.sleep(500);
        iosDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        List<IOSElement> error_m = iosDriver.findElementsById("$1.00");
        try {
            if(error_m.size() > 0) {
                System.out.println("INFO: Amount should not include the special signs when user click next ... Passed");
            } else {
                System.out.println("INFO: Amount should not include the special signs when user click next ... Failed");
                saveTextLog_Allure("INFO: Amount should not include the special signs when user click next ... Failed");
                Assert.fail();
            }
        }catch (NoSuchElementException e) {
            System.out.println("No elements found");
        }
    }

    @Test(groups = {"NonLogin", "MakePayment"}, dependsOnMethods = "MakePayment_Amount_SpecialSign3")
    public void MakePayment_Amount_SpecialSign4()
    {
        Assert.assertTrue(iosDriver.findElementByAccessibilityId("Continue").isEnabled());
        System.out.println("INFO: If 'MakePayment_Amount_SpecialSign3' passed which means the Amount field does not" +
                "include the special signs. The 'Continue' button should be enabled ... Passed");
    }

    // =======================================
    // Name part test
    @Test(groups = {"NonLogin", "MakePayment"}, priority = 11)
    public void MakePayment_Name_Empty() throws InterruptedException
    {
        iosDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        iosDriver.findElementById("Max").clear();
        iosDriver.findElementById("Next").click();

        Thread.sleep(500);
        Assert.assertFalse(iosDriver.findElementByAccessibilityId("Continue").isEnabled());
        System.out.println("INFO: Continue button should be disabled when the name field is empty ... Passed");
    }

    @Test(groups = {"NonLogin", "MakePayment"}, priority = 12)
    public void MakePayment_Name_FillAgain() throws InterruptedException
    {
        iosDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        iosDriver.findElementById("Name on card").sendKeys("Max");
        iosDriver.findElementById("Next").click();

        Thread.sleep(500);
        Assert.assertTrue(iosDriver.findElementByAccessibilityId("Continue").isEnabled());
        System.out.println("INFO: Continue button should be enabled when the name field is filled again ... Passed");
    }


    // Data picker tests (Later) false enabled


    // Zip code tests
    @Test(groups = {"NonLogin", "MakePayment"}, priority = 13)
    public void MakePayment_Zip_Empty() throws InterruptedException
    {
        iosDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        iosDriver.findElementById("66223").clear();
        iosDriver.findElementById("Next").click();

        Thread.sleep(500);
        Assert.assertFalse(iosDriver.findElementByAccessibilityId("Continue").isEnabled());
        System.out.println("INFO: Continue button should be disabled when the name field is empty ... Passed");
    }

    @Test(groups = {"NonLogin", "MakePayment"}, priority = 14)
    public void MakePayment_Zip_Less5Numbers1() throws InterruptedException
    {
        iosDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        iosDriver.findElementById("ZIP").sendKeys("6622"); // Less than 5 digits
        iosDriver.findElementById("Next").click();

        Thread.sleep(500);
        iosDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        List<IOSElement> error_m = iosDriver.findElementsById("- Invalid ZIP code.");
        try {
            if(error_m.size() > 0) {
                System.out.println("INFO: Error message '- Invalid ZIP code.' should display when the ZIP code " +
                        "is less than 5 digits ... Passed");
            } else {
                System.out.println("INFO: Error message '- Invalid ZIP code.' should display when the ZIP code " +
                        "is less than 5 digits ... Failed");
                saveTextLog_Allure("INFO: Error message '- Invalid ZIP code.' should display when the ZIP code " +
                        "is less than 5 digits ... Failed");
                Assert.fail();
            }
        }catch (NoSuchElementException e) {
            System.out.println("No elements found");
        }
    }

    @Test(groups = {"NonLogin", "MakePayment"}, priority = 15)
    public void MakePayment_Zip_Less5Numbers2() throws InterruptedException
    {
        Thread.sleep(500);
        Assert.assertFalse(iosDriver.findElementByAccessibilityId("Continue").isEnabled());
        System.out.println("INFO: Continue button should be disabled also ... Passed");
    }

    @Test(groups = {"NonLogin", "MakePayment"}, priority = 16, enabled = false)
    public void MakePayment_CardNumber_Not_A_Test() throws InterruptedException
    {
        iosDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        iosDriver.findElementByAccessibilityId("Cancel").click();
        Thread.sleep(1000);
    }

    @Test(groups = {"NonLogin", "MakePayment"}, priority = 17)
    public void MakePayment_Cancel() throws InterruptedException
    {
        //Cancel Payment
        iosDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        iosDriver.findElementByAccessibilityId("Cancel").click();

        Thread.sleep(3000);
        System.out.println("INFO: MakePayment - 'cancel' ... Done" +
                "\n -----------------------------------------------");
    }
}
