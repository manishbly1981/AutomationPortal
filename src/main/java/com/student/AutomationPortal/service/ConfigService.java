package com.student.AutomationPortal.service;

import com.student.AutomationPortal.model.Configuration;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface ConfigService {

    ResponseEntity<String> modifyConfig(String project, Set<Configuration> configuration);
    //ResponseEntity<String> modifyConfig(String project, Configuration configuration);
    ResponseEntity<String> deleteConfig(String project);
    ResponseEntity<String> deleteConfig(String project, Set<Configuration> configuration);
    ResponseEntity<String> getConfig(String project, String configName);
    ResponseEntity<String> getConfig(String project);
}
