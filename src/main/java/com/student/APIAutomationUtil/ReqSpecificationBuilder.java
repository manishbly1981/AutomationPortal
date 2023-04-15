package com.student.APIAutomationUtil;

import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class ReqSpecificationBuilder {
    private Map<String, String> headerMap;
    private Map<String, String> requestParam;
    private String body;
    private RequestSpecification requestSpecification;

    public ReqSpecification setHeaderMap(Map<String, String> headerMap) {
        this.headerMap = headerMap;
        return this;
    }

    public ReqSpecification setRequestParam(Map<String, String> requestParam) {
        this.requestParam = requestParam;
        return this;
    }

    public ReqSpecification setBody(String body) {
        this.body = body;
        return this;
    }

    public ReqSpecification setRequestSpecification(RequestSpecification req) {
        this.requestSpecification = req;
        return this;
    }
}
