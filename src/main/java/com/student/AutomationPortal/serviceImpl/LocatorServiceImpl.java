package com.student.AutomationPortal.serviceImpl;


import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.student.AutomationPortal.repository.ProjectRepository;
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
	ProjectRepository projectRepository;
	@Autowired
	LocatorRepository locatorRepository;

	@Override
	public ResponseEntity<String> addLocator(String projectCode, String requestBody) {
		try{
			objectUpdater(projectCode, requestBody);
			return CompactServiceImpl.reportResponse(HttpStatus.OK, locatorRepository.findByProjectProjectCode(projectCode));
		} catch (JsonProcessingException e) {
			return CompactServiceImpl.reportResponse(HttpStatus.BAD_REQUEST, "Please check the request body");
		}
	}

	@Override
	public ResponseEntity<String> editLocator(String projectCode, String requestBody) {
		try{
			objectUpdater(projectCode, requestBody);
			return CompactServiceImpl.reportResponse(HttpStatus.OK, locatorRepository.findByProjectProjectCode(projectCode));
		} catch (JsonProcessingException e) {
			return CompactServiceImpl.reportResponse(HttpStatus.BAD_REQUEST, "Please check the request body");
		}
	}

	@Override
	public ResponseEntity<String> delLocator(String projectCode, String page, String name) {
		try {
			List<Locators> locatorList= locatorRepository.findByProjectProjectCodeAndPageAndLogicalName(projectCode, page, name);
			if(locatorList.size()==0)
				return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, page +" Page does not have an element with name "+ name);

			locatorRepository.deleteAll(locatorList);
			return CompactServiceImpl.reportResponse(HttpStatus.OK, "Locator with name '"+ name + "' deleted");
		} catch (Exception e) {
			return CompactServiceImpl.reportResponse(HttpStatus.BAD_REQUEST, "Request is not as expected");
		}
		
	}

	@Override
	public ResponseEntity<String> getLocator(String projectCode, String page, String name) {
		try {
		List<Locators> locatorsList= locatorRepository.findByPageAndLogicalName(page, name);
			return CompactServiceImpl.reportResponse(HttpStatus.OK, locatorsList);

		} catch (Exception e) {
			return CompactServiceImpl.reportResponse(HttpStatus.BAD_REQUEST, "Request is not as expected");
		}
	}

	@Override
	public ResponseEntity<String> getLocator(String projectCode, String page, String name, int seqNo) {
		try {
			Locators locator= locatorRepository.findByPageAndLogicalNameAndSeq(page, name, seqNo);
			return CompactServiceImpl.reportResponse(HttpStatus.OK, locator);

		} catch (Exception e) {
			return CompactServiceImpl.reportResponse(HttpStatus.BAD_REQUEST, "Request is not as expected");
		}
	}
	private void objectUpdater(String projectCode, String propertyData) throws JsonProcessingException {

		ObjectMapper objectMapper= new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		List<Map<String, Object>> orList= objectMapper.readValue(propertyData, List.class);
		for(Map<String, Object> or: orList){
			int counter=0;
			int toDelStartSeq=99;
			int toDelCurrentSeq=99;
			String page=or.get("page").toString();
			String logicalname=or.get("name").toString();

			List<Locators> locatorPropertyListBeforeUpdate = locatorRepository.findByProjectProjectCodeAndPageAndLogicalName(projectCode, page, logicalname);

			//To make list unique
			if(locatorPropertyListBeforeUpdate.size()>0) {
				for (Locators locators : locatorPropertyListBeforeUpdate) {
					toDelCurrentSeq += 1;
					locators.setSeq(toDelCurrentSeq);
				}
				locatorRepository.saveAll(locatorPropertyListBeforeUpdate);
			}
			Map<String, String> properties= (LinkedHashMap<String, String>) or.get("properties");
			for(Map.Entry<String, String> es: properties.entrySet()){
				Locators locators= new Locators();
				if(counter<locatorPropertyListBeforeUpdate.size()) {
					locators.setId(locatorPropertyListBeforeUpdate.get(counter).getId());
				}
				locators.setPage(page);
				locators.setLogicalName(logicalname);
				locators.setProject(projectRepository.findByProjectCode(projectCode));
				counter+=1;
				locators.setSeq(counter);
				locators.setPriority(counter);
				locators.setLocatorType(es.getKey());
				locators.setLocatorValue(es.getValue());
				locatorRepository.save(locators);
			}
			List<Locators> listToDel= new ArrayList<>();
			for(int eleToDel=counter+1;eleToDel<=locatorPropertyListBeforeUpdate.size();eleToDel++){
				listToDel.add(locatorRepository.findByProjectProjectCodeAndPageAndLogicalNameAndSeq(projectCode, page, logicalname, (toDelStartSeq+eleToDel) ));
			}
			if(listToDel.size()>0)
				locatorRepository.deleteAll(listToDel);
		}

	}
}
