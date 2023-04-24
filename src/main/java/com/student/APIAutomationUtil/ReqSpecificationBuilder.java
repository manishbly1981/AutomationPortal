package com.student.APIAutomationUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class ReqSpecificationBuilder {
    private Map<String, String> headerMap;
    private Map<String, String> pathParam;
    private Map<String, String> queryParam;
    private String body;
    private String bodyAsPojo;
    private String bodyFromFile;
    private RequestSpecification requestSpecification;
    private  String contentType;
    private String formData;
    private String accept;
    public ReqSpecificationBuilder setHeaderMap(Map<String, String> headerMap) {
        this.headerMap = headerMap;
        return this;
    }


    public ReqSpecificationBuilder setPathParam(Map<String, String> pathParam) {
        this.pathParam = pathParam;
        return this;
    }

    public ReqSpecificationBuilder setQueryParam(Map<String, String> queryParam) {
        this.queryParam = queryParam;
        return this;
    }

    public ReqSpecificationBuilder setBody(String body) {
        if(body!=null && !body.equalsIgnoreCase(""))
            this.body = body;
        return this;
    }

    public ReqSpecificationBuilder setBodyAsPojo(Object bodyAsPojo) throws JsonProcessingException {
        ObjectMapper mapper= new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.bodyAsPojo = mapper.writeValueAsString(bodyAsPojo);
        return this;
    }

    public ReqSpecificationBuilder setBodyFromFile(String bodyFromFile, GlobalData globalData) throws IOException {
        if(bodyFromFile==null || bodyFromFile.equalsIgnoreCase(""))
            return this;
        String fileBasePath= System.getProperty("user.dir") + "/TestResource/requests/";
        this.bodyFromFile = new CompactUtil(globalData).replaceRunTimeVal(new String(Files.readAllBytes(Paths.get(fileBasePath + bodyFromFile))));
        return this;
    }

    public ReqSpecificationBuilder setRequestSpecification(RequestSpecification req) {
        this.requestSpecification = req;
        return this;
    }

    public ReqSpecificationBuilder setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public ReqSpecificationBuilder setAccept(String accept) {
        this.accept = accept;
        return this;
    }

    public File getFormData() {
        try {
            return new File(this.formData);
        }catch(Exception e){
            return null;
        }
    }

    public ReqSpecificationBuilder setFormData(String formData) {
        if(formData==null || formData.equalsIgnoreCase(""))
            return this;
        this.formData = formData;
        return this;
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public Map<String, String> getPathParam() {
        return pathParam;
    }
    public Map<String, String> getQueryParam() {
        return queryParam;
    }

    public String getBody() {
        return body;
    }

    public String getBodyAsPojo() {
        return bodyAsPojo;
    }

    public String getBodyFromFile() {
        return bodyFromFile;
    }

    public RequestSpecification getRequestSpecification() {
        return requestSpecification;
    }

    public String getContentType() {
        return contentType;
    }

    public String getAccept() {
        return accept;
    }


}
