package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.ExtentSparkReporterConfig;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.Constants;

import java.io.File;

public class TestListener implements ITestListener {

    private static final Logger logger = LogManager.getLogger(TestListener.class);

    ExtentReports extentReports = new ExtentReports();
    File file = new File(Constants.projectRoot + "/target/eReport.html");

    ExtentSparkReporter sparkReporter = new ExtentSparkReporter(file);
    ExtentTest extentTest;

    public void createReport(ITestResult result){
        sparkReporter.config(ExtentSparkReporterConfig.builder()
                .theme(Theme.DARK)
                .documentTitle("Rest Assured Report")
                .build());
        extentReports.attachReporter(sparkReporter);
        extentTest = extentReports.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Starting Test Method" + result.getMethod().getMethodName());
        //ITestListener.super.onTestStart(result);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        createReport(result);
        extentTest.log(Status.PASS, "Test:" + result.getMethod().getMethodName() + " passed.");
        logger.info("Test method" + result.getMethod().getMethodName() + " passed.");
        extentReports.flush();
       // ITestListener.super.onTestSuccess(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        createReport(result);
        extentTest.log(Status.FAIL, "Test:" + result.getMethod().getMethodName() + " failed.");
        logger.info("Test method" + result.getMethod().getMethodName() + " failed.");
        extentReports.flush();
        //ITestListener.super.onTestFailure(result);

    }

    @Override
    public void onTestSkipped(ITestResult result) {
        createReport(result);
        extentTest.log(Status.SKIP, "Test:" + result.getMethod().getMethodName() + " skiped.");
        logger.info("Test method" + result.getMethod().getMethodName() + " skiped.");
        extentReports.flush();
        // ITestListener.super.onTestSkipped(result);

    }

}
