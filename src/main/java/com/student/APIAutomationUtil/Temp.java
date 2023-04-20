package com.student.APIAutomationUtil;


import com.jayway.jsonpath.JsonPath;

import java.sql.Array;

public class Temp {

    public static void main(String[] args) {

        String[] arr= new String[3];
        arr[0]="test";
        arr[1]="testing";
        arr[2]="temp";
        System.out.println(arr.length);
        String jsonString = "{\n" +
                "    \"data\": [\n" +
                "        {\n" +
                "            \"id\": 1,\n" +
                "            \"email\": \"vikas1@vikas.com\",\n" +
                "            \"firstName\": \"vikas\",\n" +
                "            \"lastName\": \"pandey\",\n" +
                "            \"password\": \"Niit@123\",\n" +
                "            \"attempts\": 0,\n" +
                "            \"confirmationCode\": \"\",\n" +
                "            \"roles\": [\n" +
                "                {\n" +
                "                    \"id\": 1,\n" +
                "                    \"role\": \"USER\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"id\": 2,\n" +
                "                    \"role\": \"ADMIN\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"projects\": [\n" +
                "                {\n" +
                "                    \"id\": 1,\n" +
                "                    \"projectCode\": \"SAN\",\n" +
                "                    \"projectName\": \"SANTANDER\",\n" +
                "                    \"modules\": [\n" +
                "                        \n" +
                "                    ],\n" +
                "                    \"releases\": [\n" +
                "                        \n" +
                "                    ],\n" +
                "                    \"configurations\": [\n" +
                "                        \n" +
                "                    ],\n" +
                "                    \"locators\": [\n" +
                "                        \n" +
                "                    ]\n" +
                "                }\n" +
                "            ],\n" +
                "            \"active\": true\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 2,\n" +
                "            \"email\": \"manish1@manish.com\",\n" +
                "            \"firstName\": \"manish\",\n" +
                "            \"lastName\": \"gupta\",\n" +
                "            \"password\": \"Niit@123\",\n" +
                "            \"attempts\": 0,\n" +
                "            \"confirmationCode\": \"\",\n" +
                "            \"roles\": [\n" +
                "                {\n" +
                "                    \"id\": 2,\n" +
                "                    \"role\": \"ADMIN\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"projects\": [\n" +
                "                {\n" +
                "                    \"id\": 1,\n" +
                "                    \"projectCode\": \"SAN\",\n" +
                "                    \"projectName\": \"SANTANDER\",\n" +
                "                    \"modules\": [\n" +
                "                        \n" +
                "                    ],\n" +
                "                    \"releases\": [\n" +
                "                        \n" +
                "                    ],\n" +
                "                    \"configurations\": [\n" +
                "                        \n" +
                "                    ],\n" +
                "                    \"locators\": [\n" +
                "                        \n" +
                "                    ]\n" +
                "                }\n" +
                "            ],\n" +
                "            \"active\": true\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 3,\n" +
                "            \"email\": \"arti1@arti.com\",\n" +
                "            \"firstName\": \"arti\",\n" +
                "            \"lastName\": \"jararia\",\n" +
                "            \"password\": \"Niit@123\",\n" +
                "            \"attempts\": 0,\n" +
                "            \"confirmationCode\": \"\",\n" +
                "            \"roles\": [\n" +
                "                {\n" +
                "                    \"id\": 1,\n" +
                "                    \"role\": \"USER\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"projects\": [\n" +
                "                {\n" +
                "                    \"id\": 1,\n" +
                "                    \"projectCode\": \"SAN\",\n" +
                "                    \"projectName\": \"SANTANDER\",\n" +
                "                    \"modules\": [\n" +
                "                        \n" +
                "                    ],\n" +
                "                    \"releases\": [\n" +
                "                        \n" +
                "                    ],\n" +
                "                    \"configurations\": [\n" +
                "                        \n" +
                "                    ],\n" +
                "                    \"locators\": [\n" +
                "                        \n" +
                "                    ]\n" +
                "                }\n" +
                "            ],\n" +
                "            \"active\": true\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        //Get JsonPath instance of above JSON string
        Object jsonDetailsAsStr = JsonPath.read(jsonString, "$.data[0].email");
        Object jsonDetailsAsList = JsonPath.read(jsonString, "$.data");
        System.out.println("test");

    }

}