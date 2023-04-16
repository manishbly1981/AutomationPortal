package com.student.APIAutomationUtil;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.io.FileWriter;

public class ExtentUtil {
    static ExtentHtmlReporter htmlReporter;
    static ExtentReports extent;
    static ExtentTest logger;
    static String extentReportPath="";
    String takeScreenshot="Always"; //OnError
    public String getReportFolder(){
        return extentReportPath;
    }
    public void initReport(){
        if(new ExtentUtil().extentReportPath.equalsIgnoreCase("")){
            extentReportPath = System.getProperty("user.dir") + "/test-output/" + new CompactUtil().getCurrentTimeStemp("yyyy_MM_dd_HH_mm_ss");
        }
        new CompactUtil().createFolder(extentReportPath);
        htmlReporter = new ExtentHtmlReporter(extentReportPath+ "/ExecutionSummaryReport" + ".html");
        htmlReporter.setAppendExisting(true);
        extent = new ExtentReports ();
        extent.attachReporter(htmlReporter);

        extent.setSystemInfo("Host Name", new CompactUtil().getSystemName());
//        extent.setSystemInfo("Environment", "SIT");
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        htmlReporter.config().setDocumentTitle("API Automation Execution Report");
        htmlReporter.config().setReportName("Automation Regression Report");
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setTheme(Theme.STANDARD);
    }

    public void initTest(String testName){
        logger= extent.createTest(testName);
        extent.flush();
    }

    public String getStatus(){
        return logger.getStatus().toString().toLowerCase();
    }

    public void logEvent(Status status,String description, String txtToWriteInFile){
        if(!txtToWriteInFile.equalsIgnoreCase(""))
            logger.log(status, description + captureScreenShot(txtToWriteInFile));
        else
            logger.log(status, description);
        extent.flush();
    }

    public void logInfo(String msgToPrint){
        logger.log(Status.INFO, MarkupHelper.createLabel(msgToPrint, ExtentColor.BLUE));
        extent.flush();
    }

    public void logPass(String msgToPrint){
        logger.log(Status.PASS, MarkupHelper.createLabel(msgToPrint, ExtentColor.GREEN));
        extent.flush();
    }

    public void logFail(String msgToPrint){
        logger.log(Status.FAIL, MarkupHelper.createLabel(msgToPrint, ExtentColor.RED));
        extent.flush();
    }

    public void compareResult(String logicalName, String expectedVal, String actualVal, boolean caseSensitive){
        String msgToPrint="Verify <B>'"+ logicalName + "'</B> value:<BR>Expected: <B>'"+ expectedVal +"'</B><BR>Actual: <B>'" + actualVal + "'</B>";
        if(caseSensitive){
            if(expectedVal==actualVal){
                logPass(msgToPrint);
            }else{
                logFail(msgToPrint);
            }
        }else{
            if(expectedVal.equalsIgnoreCase(actualVal)){
                logPass(msgToPrint);
            }else{
                logFail(msgToPrint);
            }
        }
    }

    public void logInfo(String msgToPrint, String takeScreenshot){
        if(!takeScreenshot.equalsIgnoreCase("")){
            logger.log(Status.INFO, MarkupHelper.createLabel(msgToPrint + captureScreenShot(takeScreenshot), ExtentColor.BLUE));
        }else{
            logger.log(Status.INFO, MarkupHelper.createLabel(msgToPrint, ExtentColor.BLUE));
        }
        extent.flush();
    }

    public void logWarning(String msgToPrint){
        logger.log(Status.WARNING, MarkupHelper.createLabel(msgToPrint, ExtentColor.AMBER));
        extent.flush();
    }

    public void logSkipped(String msgToPrint){
        logger.log(Status.SKIP, MarkupHelper.createLabel(msgToPrint, ExtentColor.ORANGE));
        extent.flush();
    }
    public String captureScreenShot(String strToWrite) {
        try {
            String filePath = extentReportPath + File.separator + "Screenshots" + File.separator;
            new CompactUtil().createFolder(filePath);

            String fileName = new CompactUtil().getCurrentTimeStemp("yyyyMMdd_hhmmssSSS") + ".txt";
            FileWriter fw= new FileWriter(filePath + fileName);
            fw.write(strToWrite);
            fw.close();
            return "<div align='right' style='float:right'><a " + newWindowPopUpHTMLCode() + " target='_blank' href= " + "." + File.separator + "Screenshots" + File.separator + fileName + ">Test Data</a></div>";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    public String newWindowPopUpHTMLCode() {
        return "onclick = \"window.open(this.href,'newwindow', 'width=1000" + ",height=500');return false;\"";
    }
}
