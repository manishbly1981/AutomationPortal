package com.student.UiAutomationUtil.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class DriverManager {

    public static WebDriver browserSetup(String browserName){
        WebDriver webDriver= null;
        switch (browserName.toLowerCase().trim()){
            case "chrome":
                WebDriverManager.chromedriver().arch64().setup();
                webDriver= new ChromeDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().arch64().setup();
                webDriver= new EdgeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().arch64().setup();
                webDriver= new FirefoxDriver();
                break;
        }
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        return webDriver;
    }
}
