package com.student.APIAutomationUtil;

import java.util.HashMap;
public class GlobalData {
    ExtentUtil extentUtil;
    HashMap<String, String> runTimeValMap;

    public GlobalData(ExtentUtil extentUtil){
        this.extentUtil= extentUtil;
        this.runTimeValMap= new HashMap<String, String>();
    }


    public void setRunTimeVal(String varName, String varVal){
        runTimeValMap.put(varName, varVal);
        extentUtil.logInfo(varName + " value stored at runtime as " +varVal);
    }
    public String getRunTimeVal(String varName){
        try {
            return runTimeValMap.get(varName);
        }catch(Exception e){
            extentUtil.logFail(varName + " value could not found in existing stored data");
            return null;
        }
    }

    public boolean existVar(String varName){
        if (runTimeValMap.containsKey(varName)){
            return true;
        }else{
            return false;
        }
    }
}
