package Runner;

import Utilities.Retry;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.IOException;

import static Utilities.ReusableMethods.deleteRequiredFolder;
import static Utilities.ReusableMethods.threadSleep;

@CucumberOptions(
        features = "@target/failedRerun.txt",
        glue = {"Utilities", "StepDefinations"},
        plugin = {"pretty", "html:target/cucumber-reports",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7jvm",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "rerun:target/failedRerun.txt"},
        monochrome = true
)

public class RerunFailedTests extends AbstractTestNGCucumberTests {

    @Test(
            groups = {"cucumber"},
            description = "Runs Cucumber Scenarios",
            dataProvider = "scenarios",
            retryAnalyzer = Retry.class
    )

    @Override
    public void runScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
        super.runScenario(pickleWrapper, featureWrapper);
    }

    @BeforeSuite
    public static void beforeSuite() throws IOException {
        System.out.println("Before Suite");
        deleteRequiredFolder("target\\allure");
    }

    @AfterSuite
    public static void afterSuite() throws IOException {
        System.out.println("After Suite");

        String newDir = System.getProperty("user.dir") + "target\\allure";
        Runtime.getRuntime().exec("cmd.exe /c cd \"" + newDir + "\" & start cmd.exe /k \"allure generate --single-file allure-results --clean\"");
        threadSleep(8000);
        Runtime.getRuntime().exec("TASKKILL /F /IM cmd.exe");
    }
}
