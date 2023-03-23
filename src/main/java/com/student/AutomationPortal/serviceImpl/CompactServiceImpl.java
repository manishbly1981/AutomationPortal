package com.student.AutomationPortal.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.student.AutomationPortal.model.TestCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class CompactServiceImpl {
	Logger logger= Logger.getLogger(this.getClass().getName());

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
	
	public static ResponseEntity<String> reportResponse(HttpStatus statusCode, Set set) {
		try {
			ObjectMapper mapper= new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			String json= mapper.writeValueAsString(set);
			return ResponseEntity.status(statusCode).contentType(MediaType.APPLICATION_JSON).body(json);
		}catch(Exception e) {
			return reportResponse(HttpStatus.BAD_REQUEST, "Cannot convert value to JSON");
		}
	}
	
	public static ResponseEntity<String> reportResponse(HttpStatus statusCode, List list) {
		try {
			ObjectMapper mapper= new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			String json= mapper.writeValueAsString(list);
			return ResponseEntity.status(statusCode).contentType(MediaType.APPLICATION_JSON).body(json);
		}catch(Exception e) {
			return reportResponse(HttpStatus.BAD_REQUEST, "Cannot convert value to JSON");
		}
	}

	public static ResponseEntity<String> reportJSONResponse(HttpStatus statusCode, String msgToWrite) {
		  return ResponseEntity.status(statusCode).contentType(MediaType.APPLICATION_JSON).body(msgToWrite);
	}

	public static ResponseEntity<String> reportResponse(HttpStatus statusCode, TestCase tc) {
		try {
			ObjectMapper mapper= new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			String json= mapper.writeValueAsString(tc);
			return ResponseEntity.status(statusCode).contentType(MediaType.APPLICATION_JSON).body(json);
		}catch(Exception e) {
			return reportResponse(HttpStatus.BAD_REQUEST, "Cannot convert value to JSON");
		}
	}

}
