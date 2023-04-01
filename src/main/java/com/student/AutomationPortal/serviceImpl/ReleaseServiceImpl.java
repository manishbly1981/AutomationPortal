package com.student.AutomationPortal.serviceImpl;

import com.student.AutomationPortal.model.Project;
import com.student.AutomationPortal.model.ExecutionRelease;
import com.student.AutomationPortal.repository.ProjectRepository;
import com.student.AutomationPortal.repository.ReleaseRepository;
import com.student.AutomationPortal.service.ReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class ReleaseServiceImpl implements ReleaseService {

    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    ReleaseRepository releaseRepository;
    @Override
    public ResponseEntity<String> addRelease(String projectCode, String releaseName) {
        try {
            ExecutionRelease release = releaseRepository.findByProjectProjectCodeAndName(projectCode, releaseName);
            if (release == null) {
                release = new ExecutionRelease();
                release.setName(releaseName);
                release.setProject(projectRepository.findByProjectCode(projectCode));
                releaseRepository.save(release);
                return CompactServiceImpl.reportResponse(HttpStatus.OK, "Release '" + releaseName + "' created");
            } else {
                return CompactServiceImpl.reportResponse(HttpStatus.FOUND, "Release '" + releaseName + "' already created");
            }
        }catch (Exception e){
            return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Please check the Project details "+ projectCode);
        }
    }

    @Override
    public ResponseEntity<String> editRelease(String projectCode, String releaseName, String newReleaseName) {
        ExecutionRelease release = releaseRepository.findByProjectProjectCodeAndName(projectCode, releaseName);
        if (release==null){
            return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Please check the Project/release details ");
        }else {
            release.setName(newReleaseName);
            releaseRepository.save(release);
            return CompactServiceImpl.reportResponse(HttpStatus.OK, "Release Name changed");
        }
    }

    @Override
    public ResponseEntity<String> getRelease(String projectCode) {
        List<ExecutionRelease> release = releaseRepository.findByProjectProjectCode(projectCode);
        if (release.size()==0) {
            return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Please check the Project details ");
        } else {
            return CompactServiceImpl.reportResponse(HttpStatus.OK, release);
        }
    }
    @Override
    public ResponseEntity<String> getRelease(String projectCode, String releaseName) {
        ExecutionRelease release = releaseRepository.findByProjectProjectCodeAndName(projectCode, releaseName);
        if (release==null) {
            return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Please check the Project/release details ");
        }else{
            return CompactServiceImpl.reportResponse(HttpStatus.OK, release);
        }
    }

    @Override
    public ResponseEntity<String> deleteRelease(String projectCode, String releaseName) {
        ExecutionRelease release = releaseRepository.findByProjectProjectCodeAndName(projectCode, releaseName);
        if (release==null) {
            return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Please check the Project/release details ");
        }else{
            releaseRepository.delete(release);
            return CompactServiceImpl.reportResponse(HttpStatus.OK, "Release deleted");
        }
    }


    /********************************************************************************/
    public ExecutionRelease getReleaseInternally(String projectCode, String releaseName) {
        return releaseRepository.findByProjectProjectCodeAndName(projectCode, releaseName);
    }

    public List<ExecutionRelease> getReleaseInternally(String projectCode) {
        return releaseRepository.findByProjectProjectCode(projectCode);
    }
}
