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
        Project project= projectRepository.findByProjectCode(projectCode);
        if(project==null)
            CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Please check the Project details "+ projectCode);
        /*//List<ExecutionRelease> releases= releaseRepository.findByProjectId(project.getId());

        List<ExecutionRelease> releases= releaseRepository.findByNameAndProjectId(releaseName, project.getId());

         */
        List<ExecutionRelease> releases=releaseRepository.findByNameAndProjectProjectCode(releaseName, projectCode);
        if (releases.size()>0)
            return CompactServiceImpl.reportResponse(HttpStatus.FOUND, "Release already exist with name "+ releaseName);
        else{
            ExecutionRelease release= new ExecutionRelease();
            release.setProject(project);
            release.setName(releaseName);
            releaseRepository.save(release);
            return CompactServiceImpl.reportResponse(HttpStatus.OK, releaseRepository.findByNameAndProjectProjectCode(releaseName, projectCode));
        }
    }

    @Override
    public ResponseEntity<String> editRelease(String projectCode, String releaseName, String newReleaseName) {
        Project project= projectRepository.findByProjectCode(projectCode);
        if(project==null)
            CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Please check the Project details "+ projectCode);
        List<ExecutionRelease> matchingRelease= releaseRepository.findByNameAndProjectId(releaseName, project.getId());
        if (matchingRelease.size()>0) {
            ExecutionRelease release= matchingRelease.get(0);
            release.setName(newReleaseName);
            releaseRepository.save(release);
            return CompactServiceImpl.reportResponse(HttpStatus.OK, releaseRepository.findByProjectId(project.getId()));
        }
        else{
            return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Release '"+ releaseName +"' does not exist");
//            Release release= new Release();
//            release.setProject(project);
//            release.setName(releaseName);
//            releaseRepository.save(release);
        }

    }

    @Override
    public ResponseEntity<String> getRelease(String projectCode, String releaseName) {
        List<ExecutionRelease> matchingRelease= new ArrayList<>();
        if (releaseName.length()>0)
            matchingRelease= releaseRepository.findByNameAndProjectProjectCode(releaseName, projectCode);
        else
            matchingRelease= releaseRepository.findByProjectProjectCode(projectCode);
        if(matchingRelease.size()==0)
            return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Release '"+ releaseName +"' does not exist");
        return CompactServiceImpl.reportResponse(HttpStatus.OK, matchingRelease);
    }

    @Override
    public ResponseEntity<String> deleteRelease(String projectCode, String releaseName) {
        Project project= projectRepository.findByProjectCode(projectCode);
        if(project==null)
            CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Please check the Project details "+ projectCode);
        List<ExecutionRelease> releases = project.getReleases();
        if (releaseName==null)
            return CompactServiceImpl.reportResponse(HttpStatus.OK, releases.stream().filter(r->r.getName().equalsIgnoreCase(releaseName)).collect(Collectors.toList()));
        else
            releaseRepository.deleteAll(releases);
            return CompactServiceImpl.reportResponse(HttpStatus.OK, releases);
    }

    /********************************************************************************/
    public List<ExecutionRelease> getReleaseInternally(String projectCode, String releaseName) {
        Project project= projectRepository.findByProjectCode(projectCode);
        if(project==null)
            project= projectRepository.findByProjectName(projectCode);
            if(project==null)
                return null;

        if(releaseName.equalsIgnoreCase("")) {
            return projectRepository.findByProjectCode(projectCode).getReleases();
        }
        else {
            List<ExecutionRelease> releases = projectRepository.findByProjectCode(projectCode).getReleases();
            return releaseRepository.findByProjectId(project.getId());
        }
    }

}