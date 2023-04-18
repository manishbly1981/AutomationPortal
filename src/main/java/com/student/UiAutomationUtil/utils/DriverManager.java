package com.student.UiAutomationUtil.utils;

//import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class DriverManager {

    public static void main(String args[]) throws InterruptedException {
        WebDriver driver= browserSetup("chrome");
        driver.get("http://www.google.com");
        driver.findElement(By.id("q")).sendKeys("testing" + Keys.ENTER);
        Thread.sleep(1000);
        driver.quit();
    }
    public static WebDriver browserSetup(String browserName){
        WebDriver webDriver= null;
        switch (browserName.toLowerCase().trim()){
            case "chrome":
//                WebDriverManager.chromedriver().setup();
                webDriver= new ChromeDriver();
                break;
            case "edge":
                webDriver= new EdgeDriver();
                break;
            case "firefox":
                webDriver= new FirefoxDriver();
                break;
        }
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        return webDriver;
    }
}
