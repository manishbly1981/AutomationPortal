package com.student.APIAutomationUtil;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class CompactUtil {

    public static void main(String args[]){
//        System.out.println(new CompactUtil().removeSpecialCharacterInFileName("man /:*?\"<>|ish"));
    }
    //To be used in Reporting to create the folder and file
    public String getCurrentTimeStemp(String pattern){
        return new SimpleDateFormat(pattern).format(new Timestamp(System.currentTimeMillis()));
    }

    //To create the folder on hard drive
    public void createFolder(String directory){
        File fileLocation= new File(directory);
        if(!fileLocation.exists())
            new File(directory).mkdirs();
    }

    public String getSystemName(){
        try {
            InetAddress ip = InetAddress.getLocalHost();
            return ip.getHostName();
        }catch(Exception e){
            return "";
        }
    }

    public String removeSpecialCharacterInFileName(String fileName){
        fileName=fileName.replace(" ", "_");
        fileName=fileName.replace("\\", "");
        fileName=fileName.replace("/", "");
        fileName=fileName.replace(":", "");
        fileName=fileName.replace("*", "");
        fileName=fileName.replace("?", "");
        fileName=fileName.replace("\"", "");
        fileName=fileName.replace("<", "");
        fileName=fileName.replace(">", "");
        fileName=fileName.replace("|", "");
        return fileName;
    }

    public static void closeSpecificProcess(String processName){
        try {
            Process process = Runtime.getRuntime().exec("taskkill /F /IM "+ processName);
            process.waitFor();
        }catch(IOException | InterruptedException e){
            e.printStackTrace();
        }

    }

    public static Map convertRestStrToMap(String str){
        if(str!=null && !str.equalsIgnoreCase("")) {
            Map<String, String> headerMap = new HashMap<>();
            String[] strArray = str.split(",");
            for (String subStr : strArray) {
                String[] subStrContent = subStr.split(":");
                headerMap.put(subStrContent[0], subStrContent[1]);
            }
            return headerMap;
        }else{
            return null;
        }
    }
}

