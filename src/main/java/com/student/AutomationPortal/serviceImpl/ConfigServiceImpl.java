package com.student.AutomationPortal.serviceImpl;

import com.student.AutomationPortal.model.Configuration;
import com.student.AutomationPortal.model.Project;
import com.student.AutomationPortal.repository.ConfigRepository;
import com.student.AutomationPortal.repository.ProjectRepository;
import com.student.AutomationPortal.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    ConfigRepository configRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Override
    public ResponseEntity<String> modifyConfig(String projectCode, Set<Configuration> configuration) {
        Project project= projectRepository.findByProjectCode(projectCode);
        if (project==null)
            project= projectRepository.findByProjectName(projectCode);
        if(project==null)
            CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Please check the Project details "+ projectCode);
        final Project projectDetails= project;
        configuration.stream().forEach(c->c.setProject(projectDetails));
        List<Configuration> currentConfigs = configRepository.findByProject(project);
        /*****************************************************************************/
        configuration.stream().forEach(c->
        {
            List<Configuration> cList = currentConfigs.stream().filter(cc -> cc.getName().equalsIgnoreCase(c.getName())).collect(Collectors.toList());
            if(cList.size()>0) {
                c.setId(cList.get(0).getId());
            }
        });
        /*****************************************************************************/
        List<Configuration> configToRemove= currentConfigs.stream().filter(config->!configuration.contains(config)).collect(Collectors.toList());
        List<Configuration> configToAdd= configuration.stream().filter(config->!currentConfigs.contains(config)).collect(Collectors.toList());

        currentConfigs.removeAll(configToRemove);
        currentConfigs.addAll(configToAdd);
        configRepository.saveAll(currentConfigs);
        return CompactServiceImpl.reportResponse(HttpStatus.OK, configRepository.findByProject(project));
    }
    @Override
    public ResponseEntity<String> deleteConfig(String projectCode) {
        Project project= projectRepository.findByProjectCode(projectCode);
        if (project==null)
            project= projectRepository.findByProjectName(projectCode);
        if(project==null)
            CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Please check the Project details "+ projectCode);
        configRepository.deleteAll(configRepository.findByProject(project));
        return CompactServiceImpl.reportResponse(HttpStatus.OK, configRepository.findByProject(project));
    }

    @Override
    public ResponseEntity<String> deleteConfig(String projectCode, Set<Configuration> configuration) {
            Project project= projectRepository.findByProjectCode(projectCode);
        if (project==null)
            project= projectRepository.findByProjectName(projectCode);
        if(project==null)
            CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Please check the Project details "+ projectCode);
        List<Configuration> currentConfigs= configRepository.findByProject(project);
        /*****************************************************************************/
        final Project projectDetails= project;
        configuration.forEach(c->c.setProject(projectDetails));
        configuration.stream().forEach(c->
        {
            List<Configuration> cList = currentConfigs.stream().filter(cc -> cc.getName().equalsIgnoreCase(c.getName())).collect(Collectors.toList());
            if(cList.size()>0) {
                c.setId(cList.get(0).getId());
                //c.setProject(projectDetails);
                c.setValue(cList.get(0).getValue());
            }
        });
        /*****************************************************************************/
        if (currentConfigs.containsAll(configuration)){
            configRepository.deleteAll(configuration);
            return CompactServiceImpl.reportResponse(HttpStatus.OK, configRepository.findByProject(project));
        }else{
            CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Configuration not found");
        }

        return CompactServiceImpl.reportResponse(HttpStatus.OK, configRepository.findByProject(project));
    }

    @Override
    public ResponseEntity<String> getConfig(String projectCode, String configName) {
        Project project= projectRepository.findByProjectCode(projectCode);
        if (project==null)
            project= projectRepository.findByProjectName(projectCode);
        if(project==null)
            CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Please check the Project details "+ projectCode);
        List<Configuration> currentConfigs= configRepository.findByProject(project).stream().filter(c->c.getName().equalsIgnoreCase(configName)).collect(Collectors.toList());
        if (currentConfigs.size()>0)
            return CompactServiceImpl.reportResponse(HttpStatus.OK, currentConfigs);
        else
            return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Configuration with name does not exist "+ configName);
    }

    @Override
    public ResponseEntity<String> getConfig(String projectCode) {
        Project project= projectRepository.findByProjectCode(projectCode);
        if (project==null)
            project= projectRepository.findByProjectName(projectCode);
        if(project==null)
            CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Please check the Project details "+ projectCode);
        return CompactServiceImpl.reportResponse(HttpStatus.OK, configRepository.findByProject(project));
    }
}
