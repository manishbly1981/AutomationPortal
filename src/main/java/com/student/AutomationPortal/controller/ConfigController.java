package com.student.AutomationPortal.controller;

import com.student.AutomationPortal.model.Configuration;
import com.student.AutomationPortal.service.ConfigService;
import com.student.AutomationPortal.serviceImpl.CompactServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/config")
public class ConfigController {
    @Autowired
    ConfigService configService;
    @PutMapping("")
    public ResponseEntity<String> addLocator(@RequestParam(name="projectCode", required = false) String projectCode,@RequestParam(name="projectName", required = false) String projectName, @RequestBody Set<Configuration> requestBody) {
        if(projectCode!=null)
            return configService.modifyConfig(projectCode, requestBody);
        else if(projectName!=null)
            return configService.modifyConfig(projectName, requestBody);
        else
            return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Project Code or Project name is required");
    }

    @GetMapping("")
    public ResponseEntity<String> getLocator(@RequestParam(name="projectCode", required = false) String projectCode,@RequestParam(name="projectName", required = false) String projectName, @RequestParam(name="configName", required = false) String  configName) {
        if(projectCode!=null) {
            if(configName==null)
                return configService.getConfig(projectCode);
            else
                return configService.getConfig(projectCode, configName);
        }
        else if(projectName!=null) {
            if(configName==null)
                return configService.getConfig(projectName);
            else
                return configService.getConfig(projectName, configName);
        }
        else
            return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Project Code or Project name is required");
    }

    @DeleteMapping("")
    public ResponseEntity<String> delLocator(@RequestParam(name="projectCode", required = false) String projectCode,@RequestParam(name="projectName", required = false) String projectName, @RequestBody Set<Configuration> requestBody) {
        if(projectCode!=null)
            return configService.deleteConfig(projectCode, requestBody);
        else if(projectName!=null)
            return configService.deleteConfig(projectName, requestBody);
        else
            return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Project Code or Project name is required");
    }

}
