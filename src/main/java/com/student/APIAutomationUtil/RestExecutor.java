package com.student.APIAutomationUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.restassured.response.Response;
import org.apache.poi.ss.usermodel.Row;

import java.io.IOException;

public class RestExecutor {
    public static void main(String args[]) throws IOException {

        /**************************************************/
        ExtentUtil extentUtil= new ExtentUtil();
        extentUtil.initReport();
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
            String bodyData= excelUtil.getCellData(row,"body");
            String reqFile= excelUtil.getCellData(row,"ReqFile");
            String statusCode= excelUtil.getCellData(row,"StatusCode");
            String resposne= excelUtil.getCellData(row,"Response");
            /**************************************************************************/
            RestUtil restUtil= new RestUtil();
            if(execution.equalsIgnoreCase("yes")){
                extentUtil.initTest(tcName);
                restUtil.setBaseURI(baseUri);
                restUtil.setBasePath(basePath);


                ReqSpecificationBuilder reqSpecificationBuilder= new ReqSpecificationBuilder();
                reqSpecificationBuilder.setHeaderMap(CompactUtil.convertRestStrToMap(headers));
                reqSpecificationBuilder.setPathParam(CompactUtil.convertRestStrToMap(pathParam));
                reqSpecificationBuilder.setQueryParam(CompactUtil.convertRestStrToMap(queryParam));
                reqSpecificationBuilder.setBody(bodyData);
                reqSpecificationBuilder.setBodyFromFile(reqFile);
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
                    case "delete":
                        res=restUtil.getDeleteResponse(reqSpecificationBuilder, pathParam);
                        break;
                }

                /*************************************/

                ObjectMapper mapper= new ObjectMapper();
                mapper.enable(SerializationFeature.INDENT_OUTPUT);
                extentUtil.logInfo("Request Data ", mapper.writeValueAsString(reqSpecificationBuilder));

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
