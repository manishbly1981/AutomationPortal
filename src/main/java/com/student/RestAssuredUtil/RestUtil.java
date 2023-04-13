package com.student.RestAssuredUtil;
/*
import io.restassured*.RestAssured;
import io.restassured.response.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
*/
public class RestUtil {
  /*  public static String downloadFileFromServer(final String fileUrl, final String filePath) throws IOException {
        String fileName="";
        File outputFile= new File(filePath, fileName);

        final Response response= RestAssured.given().auth().basic("","").when().get(fileUrl).andReturn();
        if(response.statusCode()==200){
            if(outputFile.exists())
                outputFile.delete();
            byte[] fileContents= response.getBody().asByteArray();
            OutputStream outputStream=null;
            try{
                outputStream= new FileOutputStream(outputFile);
                outputStream.write(fileContents);
            }catch (Exception e){
                System.out.println("Error writing file "+ outputFile.getAbsolutePath());
            }finally {
                {
                    if(outputStream!=null){
                        outputStream.close();
                    }
                }
            }
        }
        return outputFile.getAbsolutePath();
    }

   */
}
