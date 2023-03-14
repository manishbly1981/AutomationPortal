package com.student.AutomationPortal.serviceImpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CompactServiceImpl {
	public static String generateRandNumberAsStr(int min, int max) {
		return Integer.toString((int)Math.floor(Math.random() * (max-min)+1));
	}
	
	public static String generateConfirmationCode() {
		//return generateRandNumberAsStr(1000, 9999);
		return "1234";
	}
	
	public static ResponseEntity<String> reportResponse(HttpStatus statusCode, String msgToWrite) {
		try {
		Map<String, Object> object = new HashMap<>();
		  object.put("msg", msgToWrite);

		  ObjectMapper mapper = new ObjectMapper();
		  String response= mapper.writeValueAsString(object);
		  return ResponseEntity.status(statusCode).contentType(MediaType.APPLICATION_JSON).body(response);
		  
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(statusCode).contentType(MediaType.APPLICATION_JSON).body(msgToWrite);
	}
	
	public static ResponseEntity<String> reportResponse(HttpStatus statusCode, Object object) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			String response= mapper.writeValueAsString(object);
			return ResponseEntity.status(statusCode).contentType(MediaType.APPLICATION_JSON).body(response);
		}catch(Exception e) {
			return reportResponse(HttpStatus.BAD_REQUEST, "Cannot convert Object to JSON");
		}
	}
}
