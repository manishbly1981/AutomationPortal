package com.student.AutomationPortal.serviceImpl;


import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.student.AutomationPortal.model.Locators;
import com.student.AutomationPortal.repository.LocatorRepository;
import com.student.AutomationPortal.service.LocatorService;

@Service
public class LocatorServiceImpl implements LocatorService{

	@Autowired 
	LocatorRepository locatorRepository;
	
	@Override
	public ResponseEntity<String> addLocator(String requestBody) {
		ObjectMapper objectMapper= new ObjectMapper();
		try {
			Set<Locators> locators= new LinkedHashSet<>();
			JsonNode jsonNode= objectMapper.readTree(requestBody);
			String page= jsonNode.get("page").asText();
			String name= jsonNode.get("name").asText();
			List<Locators> pageWiseLocators= locatorRepository.findByPage(page);
			if (pageWiseLocators.size()>0) {
				List<Locators> locatorList= pageWiseLocators.stream().filter(r->r.getLogicalName().equalsIgnoreCase(name)).collect(Collectors.toList());
				if(locatorList.size()>0) {
					return CompactServiceImpl.reportResponse(HttpStatus.FOUND, page +" Page already has an element with name "+ name);
				}
			}
			
			JsonNode properties= jsonNode.get("properties");
			if (properties.isArray()) {
				Iterator<Entry<String, JsonNode>> fields = properties.get(0).fields();
				 fields.forEachRemaining(field->{
					 String key=field.getKey();
					 String val=field.getValue().asText();
					 int sNo = locators.size()+1;
					 Locators locator= new Locators();
					 locator.setSeq(sNo);
					 locator.setPage(page);
					 locator.setLogicalName(name);
					 locator.setPriority(sNo);
					 locator.setLocatorType(key);
					 locator.setLocatorValue(val);
					 locators.add(locator);
				 });
				 locatorRepository.saveAll(locators);
				 return CompactServiceImpl.reportJSONResponse(HttpStatus.OK, requestBody);
				 }

			else {
				return CompactServiceImpl.reportResponse(HttpStatus.BAD_REQUEST, "JSon format is not as expected\n" + requestBody);
			}
			
		} catch (JsonProcessingException e) {
			return CompactServiceImpl.reportResponse(HttpStatus.BAD_REQUEST, "JSon format is not as expected\n"+ requestBody);
		}
	}

	@Override
	public ResponseEntity<String> editLocator(String requestBody) {
		ObjectMapper objectMapper= new ObjectMapper();
		try {
			Set<Locators> locators= new LinkedHashSet<>();
			JsonNode jsonNode= objectMapper.readTree(requestBody);
			String page= jsonNode.get("page").asText();
			String name= jsonNode.get("name").asText();
			List<Locators> pageWiseLocators= locatorRepository.findByPage(page);
			if (pageWiseLocators.size()>0) {
				List<Locators> locatorList = pageWiseLocators.stream().filter(r -> r.getLogicalName().equalsIgnoreCase(name)).collect(Collectors.toList());
				if (locatorList.size() > 0) {
					locatorRepository.deleteAll(locatorList); //Delete existing object is persist in the system
				}
			}
			JsonNode properties= jsonNode.get("properties");
			if (properties.isArray()) {
				Iterator<Entry<String, JsonNode>> fields = properties.get(0).fields();
				fields.forEachRemaining(field->{
					String key=field.getKey();
					String val=field.getValue().asText();
					int sNo = locators.size()+1;
					Locators locator= new Locators();
					locator.setSeq(sNo);
					locator.setPage(page);
					locator.setLogicalName(name);
					locator.setPriority(sNo);
					locator.setLocatorType(key);
					locator.setLocatorValue(val);
					locators.add(locator);
				});
				locatorRepository.saveAll(locators);
				return CompactServiceImpl.reportJSONResponse(HttpStatus.OK, requestBody);
			}

			else {
				return CompactServiceImpl.reportResponse(HttpStatus.BAD_REQUEST, "JSon format is not as expected\n" + requestBody);
			}

		} catch (JsonProcessingException e) {
			return CompactServiceImpl.reportResponse(HttpStatus.BAD_REQUEST, "JSon format is not as expected\n"+ requestBody);
		}

	}

	@Override
	public ResponseEntity<String> delLocator(String page, String name) {
		try {
		List<Locators> pageWiseLocators= locatorRepository.findByPage(page);
		if (pageWiseLocators.size()>0) {
			List<Locators> locatorList= pageWiseLocators.stream().filter(r->r.getLogicalName().equalsIgnoreCase(name)).collect(Collectors.toList());
			if(locatorList.size()<=0) {
				return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, page +" Page does not have an element with name "+ name);
			}else {
				locatorRepository.deleteAll(locatorList);
				return CompactServiceImpl.reportResponse(HttpStatus.OK, name + " element deleted from the page " + page);
			}
		}else {
			return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, page +" Page does not exist");
		}
		} catch (Exception e) {
			return CompactServiceImpl.reportResponse(HttpStatus.BAD_REQUEST, "Request is not as expected");
		}
		
	}

	@Override
	public ResponseEntity<String> getLocator(String page, String name) {
		try {
		List<Locators> pageWiseLocators= locatorRepository.findByPage(page);
		if (pageWiseLocators.size()>0) {
			List<Locators> locatorList= pageWiseLocators.stream().filter(r->r.getLogicalName().equalsIgnoreCase(name)).collect(Collectors.toList());
			if(locatorList.size()>0) {
				return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, page +" Page does not have an element with name "+ name);
			}else {
				return CompactServiceImpl.reportResponse(HttpStatus.OK, locatorList);
			}
		}else {
			return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, page +" Page does not exist");
		}
		} catch (Exception e) {
			return CompactServiceImpl.reportResponse(HttpStatus.BAD_REQUEST, "Request is not as expected");
		}
	}
}
