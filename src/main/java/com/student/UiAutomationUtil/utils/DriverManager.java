package com.student.UiAutomationUtil.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

public class DriverManager {
    private String  fireFoxBinaryPath;

    public void setFireFoxBinaryPath(String fireFoxBinaryPath){
        this.fireFoxBinaryPath= fireFoxBinaryPath;
    }

    public String getFireFoxBinaryPath(){
        return this.fireFoxBinaryPath;
    }

    public WebDriver browserSetup(String browserName){
        WebDriver webDriver= null;
        switch (browserName.toLowerCase().trim()){
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions= new ChromeOptions();
                chromeOptions.addArguments("--remote-allow-origins=*");
                chromeOptions.setAcceptInsecureCerts(true);
                webDriver= new ChromeDriver(chromeOptions);
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions= new EdgeOptions();
                edgeOptions.addArguments("--remote-allow-origins=*");
                edgeOptions.setAcceptInsecureCerts(true);
                webDriver= new EdgeDriver(edgeOptions);
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions= new FirefoxOptions();
                if (getFireFoxBinaryPath()!=null)
                    firefoxOptions.setBinary(getFireFoxBinaryPath());
                webDriver= new FirefoxDriver(firefoxOptions);
                break;
        }
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        return webDriver;
    }
}
