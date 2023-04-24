package com.student.APIAutomationUtil;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RestUtil {

    private static ThreadLocal<RestAssured> restAssuredThreadLocal= new ThreadLocal<>();
    public void setBaseURI(String baseURI){
        if(baseURI!=null & !baseURI.equalsIgnoreCase(""))
            RestAssured.baseURI=baseURI;
    }

    public void setBasePath(String basePath){
        if(basePath!=null & !basePath.equalsIgnoreCase(""))
            RestAssured.basePath= basePath;
    }

    public void resetBaseURI(){
        RestAssured.baseURI=null;
    }

    public void resetBasePath(){
        RestAssured.basePath= null;
    }

    public void setProxy(String host, int port){
        RestAssured.proxy(host, port);
    }

    public void setContentType(String contentType){
        restAssuredThreadLocal.get().given().contentType(contentType);
    }

    public void setAccept(String accept){
        restAssuredThreadLocal.get().given().accept(accept);
    }

    private RequestSpecification reqSpec(ReqSpecificationBuilder reqSpecificationBuilder){
        RequestSpecification requestSpecification= restAssuredThreadLocal.get()
                .given().log().all();
        if(reqSpecificationBuilder.getRequestSpecification()!=null) {
            requestSpecification.spec(reqSpecificationBuilder.getRequestSpecification());
        }
        if(reqSpecificationBuilder.getBody()!=null && !reqSpecificationBuilder.getBody().equalsIgnoreCase(""))
            requestSpecification.body(reqSpecificationBuilder.getBody());
        if(reqSpecificationBuilder.getHeaderMap()!=null)
            requestSpecification.headers(reqSpecificationBuilder.getHeaderMap());
        if(reqSpecificationBuilder.getQueryParam()!=null)
            requestSpecification.queryParams(reqSpecificationBuilder.getQueryParam());
        if(reqSpecificationBuilder.getPathParam()!=null)
            requestSpecification.params(reqSpecificationBuilder.getPathParam());
        if(reqSpecificationBuilder.getContentType()!=null)
            requestSpecification.contentType(reqSpecificationBuilder.getContentType());
        if(reqSpecificationBuilder.getAccept()!=null)
            requestSpecification.accept(reqSpecificationBuilder.getAccept());
        if(reqSpecificationBuilder.getBodyAsPojo()!=null)
            requestSpecification.body(reqSpecificationBuilder.getBodyAsPojo());
        if(reqSpecificationBuilder.getBodyFromFile()!=null)
            requestSpecification.body(reqSpecificationBuilder.getBodyFromFile());
        if(reqSpecificationBuilder.getFormData()!=null)
            requestSpecification.multiPart(reqSpecificationBuilder.getFormData());
        return requestSpecification;
    }

    public Response getDeleteResponse(ReqSpecificationBuilder reqSpecificationBuilder, String url){
        if (url==null)
            return reqSpec(reqSpecificationBuilder)
                    .when()
                    .delete()
                    .thenReturn();

        else
            return reqSpec(reqSpecificationBuilder)
                .when()
                .delete(url)
                .thenReturn();
    }

    public Response getGetResponse(ReqSpecificationBuilder reqSpecificationBuilder, String url){
        if (url==null)
            return reqSpec(reqSpecificationBuilder)
                    .when()
                    .get()
                    .thenReturn();
        else
            return reqSpec(reqSpecificationBuilder)
                .when()
                .get(url)
                .thenReturn();
    }

    public void getGetFileResponse(ReqSpecificationBuilder reqSpecificationBuilder, String url, String localPath){
        byte[] img;
        if (url==null)
            img= reqSpec(reqSpecificationBuilder)
                    .when()
                    .get()
                    .asByteArray();
        else
            img= reqSpec(reqSpecificationBuilder)
                    .when()
                    .get(url)
                    .asByteArray();

        OutputStream outStream = null;
        try {
            Files.deleteIfExists(Paths.get(localPath));
            outStream = new FileOutputStream(localPath);
            outStream.write(img);
            outStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public Response getPostResponse(ReqSpecificationBuilder reqSpecificationBuilder, String url){
        if (url==null)
            return reqSpec(reqSpecificationBuilder)
                    .when()
                    .post()
                    .thenReturn();

        else
        return reqSpec(reqSpecificationBuilder)
                .when()
                .post(url)
                .thenReturn();

    }

    public Response getPutResponse(ReqSpecificationBuilder reqSpecificationBuilder, String url){
        if (url==null)
            return reqSpec(reqSpecificationBuilder)
                    .when()
                    .put()
                    .thenReturn();

        else
        return reqSpec(reqSpecificationBuilder)
                .when()
                .put(url)
                .thenReturn();
    }

}
