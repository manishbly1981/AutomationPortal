package com.student.APIAutomationUtil;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class ResponseUtil {
    Response response;
    String responseHelperString;
    ExtentUtil extentUtil;
    String actualResponseBody;
    GlobalData globalData;
    public ResponseUtil(Response response, String responseHelperString, ExtentUtil extentUtil, GlobalData globalData){
        this.response= response;
        this.responseHelperString= responseHelperString;
        this.extentUtil= extentUtil;
        this.globalData= globalData;
        this.actualResponseBody=response.getBody().prettyPrint();
    }

    public void responseHelper(){
        String[] resStrAsArray = responseHelperString.split(",");
        for(String restStr: resStrAsArray){
            try{
                String[] resData= restStr.split(":");
                String curJsonPath=resData[0].trim();
                String curResVal=resData[1].trim();
                String validationExpression="";
                if(resData.length>2){
                    validationExpression=resData[2];
                }
                try {
                    Object obj= JsonPath.read(actualResponseBody, curJsonPath);
                    /*
                    Check if runtimeVar exist in the collection then verify value
                    If runtimeVar does not exist in collection then store its value
                    if hard code value present then verify the hard code value using the function verifyVal
                     */
                    if (curResVal.startsWith("<")) {
                        String expectedVal = curResVal.substring(1, curResVal.length() - 1);
                        if(globalData.existVar(expectedVal))
                            if (obj instanceof List)
                                verifyVal("Verify " + curJsonPath, globalData.getRunTimeVal(expectedVal), StringUtils.join(obj).replace("[","").replace("]",""), validationExpression);
                            else
                                verifyVal("Verify " + curJsonPath, globalData.getRunTimeVal(expectedVal), obj.toString(), validationExpression);
                        else {
                            if (obj instanceof List)
                                globalData.setRunTimeVal(expectedVal, CompactUtil.covertListToString((List<String>) obj));
                            else
                                globalData.setRunTimeVal(expectedVal, obj.toString());
                        }
                    } else {
                        verifyVal("Verify " + curJsonPath, curResVal, obj, validationExpression);
                    }
                }
                 catch (Exception e) {
                    extentUtil.logFail("Input Data issue in response column for the value " + restStr);
                }
            }catch(IndexOutOfBoundsException outOfBoundsException){
                extentUtil.logFail("Input Data issue in response column for the value "+ restStr);
            }
        }
    }

    public void verifyVal(String objective, String expectedVal,Object actualVal, String validationExpression){
        if(validationExpression.equalsIgnoreCase("any"))
            System.out.println("any");
        if(actualVal instanceof List) {
            List<?> actualList= ((List<?>) actualVal);
            switch (validationExpression.toLowerCase().trim()) {
                case "all":
                    for(int counter=0;counter<actualList.size();counter++){
                        extentUtil.compareResult(objective +"["+ (counter+1) +"]", expectedVal,actualList.get(counter).toString(),false);
                    }
                    break;
                case "any":
                    if (actualList.stream().anyMatch(s->s.toString().equals(expectedVal))){
                        extentUtil.logPass(objective + " json path contains "+ expectedVal);
                    }else{
                        extentUtil.logFail(objective + " json path does not contains "+ expectedVal);
                    }
                    break;
                case "none":
                    if (actualList.stream().anyMatch(s->s.toString().equals(expectedVal))){
                        extentUtil.logFail(objective + " json path does not contains "+ expectedVal);
                    }else{
                        extentUtil.logPass(objective + " json path contains "+ expectedVal);
                    }
                    break;
            }

        }else{
            extentUtil.compareResult(objective, expectedVal,actualVal.toString(),false);
        }
    }
}
