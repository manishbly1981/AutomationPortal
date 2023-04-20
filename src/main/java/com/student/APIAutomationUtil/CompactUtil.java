package com.student.APIAutomationUtil;

import org.apache.commons.lang3.StringUtils;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompactUtil {
    GlobalData globalData;
    public CompactUtil(){

    }
    public CompactUtil(GlobalData globalData){
        this.globalData= globalData;
    }
    public static void main(String args[]){

        System.out.println(new CompactUtil().replaceRunTimeVal("<Hello> this is testing mr <Manish>"));
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

    public static String covertMapToString(Map<String, String> map){
        return StringUtils.join(map);
    }


    public static String covertListToString(List<String> list){
        return StringUtils.join(list,",");
    }

    public String replaceRunTimeVal(String str){
        if (str==null)
            return null;
        if (str.equalsIgnoreCase(""))
            return "";
        if(!str.contains("<"))
            return str;
        String strToReturn=str;
        if (this.globalData==null)
            return "GlobalData constructor did not called";

        int strStartPos=-1;
        int strEndPos=-1;
        do {
            String varName="";
            strStartPos = str.indexOf("<", strStartPos);
            if (strStartPos>-1) {
                strEndPos = str.indexOf(">", strStartPos);
                varName = str.substring(strStartPos + 1, strEndPos);
            }else{
                strEndPos=strStartPos;
            }
            //Check if varName is an array
            try {
//                if (!varName.equalsIgnoreCase("")) {
                    if (varName.contains("[")) {
                        int arrStartPos = varName.indexOf("[", 0);
                        int arrEndPos = varName.indexOf("]", arrStartPos);
                        int varIndex = Integer.parseInt(varName.substring(arrStartPos + 1, arrEndPos));
                        String baseVarName = varName.substring(0, arrStartPos);

                        strToReturn = strToReturn.replace("<" + varName + ">", globalData.getRunTimeVal(baseVarName).split(",")[varIndex]);
                    } else {
                        if(!varName.equalsIgnoreCase(""))
                            strToReturn = strToReturn.replace(varName, globalData.getRunTimeVal(varName));
                    }
//                }
            }catch (Exception e){
                System.out.println("Issue is with "+ varName);
                throw e;
            }
            strStartPos= strEndPos;
        }while(strStartPos>-1);
        return strToReturn;
    }
}

