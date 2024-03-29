package com.student.APIAutomationUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.restassured.response.Response;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class RestExecutor {
    public static void main(String args[]) throws IOException {

        /**************************************************/
        ExtentUtil extentUtil= new ExtentUtil();
        extentUtil.initReport();
        GlobalData globalData= new GlobalData(extentUtil);
        /**************************************************/
        System.out.println(System.getProperty("user.dir"));
        String excelFilePath=System.getProperty("user.dir") + "/TestResource/ApiTestData.xlsx";
        String inputSheet="InputSheet";
        ExcelUtil excelUtil= new ExcelUtil(excelFilePath, inputSheet);
        for (int counter=1; counter<=excelUtil.getRowCount();counter++){
            /**************************************************************************/
            //*READ ALL DATA FROM EXCEL
            Row row = excelUtil.getRow(counter);
            String tcNo= excelUtil.getCellData(row,"tcNo");
            String tcName= excelUtil.getCellData(row,"tcName");
            String execution= excelUtil.getCellData(row,"Execution");
            String baseUri= excelUtil.getCellData(row,"BaseUri");
            String basePath= excelUtil.getCellData(row,"BasePath");
            String operation= excelUtil.getCellData(row,"Operation");
            String headers= excelUtil.getCellData(row,"Headers");
            String pathParam= excelUtil.getCellData(row,"PathParam");
            String queryParam= excelUtil.getCellData(row,"queryParam");
            String formData= excelUtil.getCellData(row,"FormData");
            String bodyData= excelUtil.getCellData(row,"body");
            String reqFile= excelUtil.getCellData(row,"ReqFile");
            String statusCode= excelUtil.getCellData(row,"StatusCode");
            String resposneColData= excelUtil.getCellData(row,"Response");
            String resFile= excelUtil.getCellData(row,"ResFile");
            RestUtil restUtil= new RestUtil();
            if(execution.equalsIgnoreCase("yes")){
                CompactUtil compactUtil= new CompactUtil(globalData);

                extentUtil.initTest(tcNo + " : " + tcName);
                restUtil.setBaseURI(baseUri);

                basePath= compactUtil.replaceRunTimeVal(basePath);
                restUtil.setBasePath(basePath);

                /**************************************************************************/
                //updated parameterized values for body, queryparam, pathparam and headers


                headers= compactUtil.replaceRunTimeVal(headers);
                pathParam= compactUtil.replaceRunTimeVal(pathParam);
                queryParam= compactUtil.replaceRunTimeVal(queryParam);
                bodyData= compactUtil.replaceRunTimeVal(bodyData);
                //reqFile is not catered here
                /**************************************************************************/

                ReqSpecificationBuilder reqSpecificationBuilder= new ReqSpecificationBuilder();
                reqSpecificationBuilder.setHeaderMap(CompactUtil.convertRestStrToMap(headers));
                reqSpecificationBuilder.setPathParam(CompactUtil.convertRestStrToMap(pathParam));
                reqSpecificationBuilder.setQueryParam(CompactUtil.convertRestStrToMap(queryParam));
                reqSpecificationBuilder.setBody(bodyData);
                reqSpecificationBuilder.setBodyFromFile(reqFile, globalData);
                reqSpecificationBuilder.setFormData(formData);
                Response res = null;
                switch (operation.toLowerCase().trim()){
                    case "post":
                        res= restUtil.getPostResponse(reqSpecificationBuilder,pathParam);
                        break;
                    case "put":
                        res=restUtil.getPutResponse(reqSpecificationBuilder, pathParam);
                        break;
                    case "get":
                        res=restUtil.getGetResponse(reqSpecificationBuilder, pathParam);
                        break;
                    case "getfile":
                        res= restUtil.getGetFileResponse(reqSpecificationBuilder, pathParam);
                        break;
                    case "delete":
                        res=restUtil.getDeleteResponse(reqSpecificationBuilder, pathParam);
                        break;
                }

                /*****************Response validation********************/
                if((resposneColData!=null) && !resposneColData.equalsIgnoreCase(""))
                    new ResponseUtil(res, resposneColData, extentUtil, globalData).responseHelper();
                if(operation.equalsIgnoreCase("getFile")){
                    if(res.getStatusCode()==200){
                        String[] resArr= resFile.split(",");
                        File outputFile = new File(resArr[0], resArr[1]);
                        if (outputFile.exists()) {
                            outputFile.delete();
                        }
                        byte[] fileContents = res.getBody().asByteArray();
                        // output contents to file
                        OutputStream outStream=null;

                        try {
                            outStream = new FileOutputStream(outputFile);
                            outStream.write(fileContents);
                        }catch(Exception e){
                            System.out.println("Error writing file " + outputFile.getAbsolutePath());
                        }finally {
                            if(outStream!=null){
                                outStream.close();
                            }
                        }
                    }
                }
                /*************************************/
                ObjectMapper mapper= new ObjectMapper();
                mapper.enable(SerializationFeature.INDENT_OUTPUT);
                String reqDataToRecord="";

                reqDataToRecord+="Query URI: " + baseUri + basePath + "\n";

                if(reqSpecificationBuilder.getHeaderMap()!=null) {
                    reqDataToRecord += "Header Data: " + CompactUtil.covertMapToString(reqSpecificationBuilder.getHeaderMap());
                    reqDataToRecord +="\n";
                }
                if(reqSpecificationBuilder.getPathParam()!=null) {
                    reqDataToRecord += "Path Data: " + CompactUtil.covertMapToString(reqSpecificationBuilder.getPathParam());
                    reqDataToRecord +="\n";
                }


                if(reqSpecificationBuilder.getQueryParam()!=null) {
                    reqDataToRecord += "Query Param Data: " + CompactUtil.covertMapToString(reqSpecificationBuilder.getQueryParam());
                    reqDataToRecord +="\n";
                }


                if(reqSpecificationBuilder.getBodyFromFile()!=null) {
                    reqDataToRecord += "Body: " + reqSpecificationBuilder.getBodyFromFile();
                    reqDataToRecord +="\n";
                }

                if(reqSpecificationBuilder.getBodyAsPojo()!=null) {
                    reqDataToRecord += "Body: " + reqSpecificationBuilder.getBodyAsPojo();
                    reqDataToRecord +="\n";
                }

                if(reqSpecificationBuilder.getBody()!=null) {
                    reqDataToRecord += "Body: " + reqSpecificationBuilder.getBody();
                    reqDataToRecord +="\n";
                }


                if(reqSpecificationBuilder.getFormData()!=null) {
                    reqDataToRecord += "Form Data: " + reqSpecificationBuilder.getFormData();
                    reqDataToRecord +="\n";
                }

                extentUtil.logInfo("Request Data ", mapper.writeValueAsString(reqDataToRecord));

                extentUtil.compareResult("Status Code", statusCode, Integer.toString(res.getStatusCode()), false);
                extentUtil.logInfo("Response Data ", res.getBody().prettyPrint());
                System.out.println(res.prettyPrint());

                /*************************************/

            }else{
                //do nothing as execution is no
            }

        }
    }
}
