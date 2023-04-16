package com.student.APIAutomationUtil;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

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
        if(reqSpecificationBuilder.getRequestSpecification()!=null)
            requestSpecification.spec(reqSpecificationBuilder.getRequestSpecification());
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
        return requestSpecification;
    }

    public Response getDeleteResponse(ReqSpecificationBuilder reqSpecificationBuilder){
       return reqSpec(reqSpecificationBuilder)
                .when()
                .delete()
                .thenReturn();
    }

    public Response getGetResponse(ReqSpecificationBuilder reqSpecificationBuilder){
        return reqSpec(reqSpecificationBuilder)
                .when()
                .get()
                .thenReturn();
    }

    public Response getPostResponse(ReqSpecificationBuilder reqSpecificationBuilder){
        return reqSpec(reqSpecificationBuilder)
                .when()
                .post()
                .thenReturn();

    }

    public Response getPutResponse(ReqSpecificationBuilder reqSpecificationBuilder){
        return reqSpec(reqSpecificationBuilder)
                .when()
                .put()
                .thenReturn();
    }

    public Response getDeleteResponse(ReqSpecificationBuilder reqSpecificationBuilder, String url){
        return reqSpec(reqSpecificationBuilder)
                .when()
                .delete(url)
                .thenReturn();
    }

    public Response getGetResponse(ReqSpecificationBuilder reqSpecificationBuilder, String url){
        return reqSpec(reqSpecificationBuilder)
                .when()
                .get(url)
                .thenReturn();
    }

    public Response getPostResponse(ReqSpecificationBuilder reqSpecificationBuilder, String url){
        return reqSpec(reqSpecificationBuilder)
                .when()
                .post(url)
                .thenReturn();

    }

    public Response getPutResponse(ReqSpecificationBuilder reqSpecificationBuilder, String url){
        return reqSpec(reqSpecificationBuilder)
                .when()
                .put(url)
                .thenReturn();
    }

}
