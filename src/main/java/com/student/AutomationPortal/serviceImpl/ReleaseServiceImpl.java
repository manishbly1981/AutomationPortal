package com.student.AutomationPortal.serviceImpl;

import com.student.AutomationPortal.model.Project;
import com.student.AutomationPortal.model.Release;
import com.student.AutomationPortal.repository.ProjectRepository;
import com.student.AutomationPortal.repository.ReleaseRepository;
import com.student.AutomationPortal.service.ReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
        List<Release> releases= releaseRepository.findByProjectId(project.getId());

        if (releases.size()>0)
            return CompactServiceImpl.reportResponse(HttpStatus.FOUND, "Release already exist with name "+ releaseName);
        else{
            Release release= new Release();
            release.setProject(project);
            release.setName(releaseName);
            releaseRepository.save(release);
            return CompactServiceImpl.reportResponse(HttpStatus.OK, releaseRepository.findByProjectId(project.getId()));
        }
    }

    @Override
    public ResponseEntity<String> editRelease(String projectCode, String releaseName, String newReleaseName) {
        Project project= projectRepository.findByProjectCode(projectCode);
        if(project==null)
            CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Please check the Project details "+ projectCode);
        List<Release> matchingRelease= releaseRepository.findByProjectId(project.getId());
        if (matchingRelease.size()>0) {
            Release release= matchingRelease.get(0);
            release.setName(newReleaseName);
            releaseRepository.save(release);
        }
        else{
            Release release= new Release();
            release.setProject(project);
            release.setName(releaseName);
            releaseRepository.save(release);
        }
        return CompactServiceImpl.reportResponse(HttpStatus.OK, releaseRepository.findByProjectId(project.getId()));
    }

    @Override
    public ResponseEntity<String> getRelease(String projectCode, String releaseName) {
        Project project= projectRepository.findByProjectCode(projectCode);
        if(project==null)
            CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Please check the Project details "+ projectCode);
        if(releaseName.equalsIgnoreCase("")) {
            return CompactServiceImpl.reportResponse(HttpStatus.OK, releaseRepository.findByProjectId(project.getId()));
        }
        else {
            return CompactServiceImpl.reportResponse(HttpStatus.OK, releaseRepository.findByProjectId(project.getId()).stream().filter(r->r.getName().equalsIgnoreCase(releaseName)).collect(Collectors.toList()));
        }
    }

    @Override
    public ResponseEntity<String> deleteRelease(String projectCode, String releaseName) {
        Project project= projectRepository.findByProjectCode(projectCode);
        if(project==null)
            CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Please check the Project details "+ projectCode);
        List<Release> releases = project.getReleases();
        if (releaseName==null)
            return CompactServiceImpl.reportResponse(HttpStatus.OK, releases.stream().filter(r->r.getName().equalsIgnoreCase(releaseName)).collect(Collectors.toList()));
        else
            return CompactServiceImpl.reportResponse(HttpStatus.OK, releases);
    }

    /********************************************************************************/
    public List<Release> getReleaseInternally(String projectCode, String releaseName) {
        Project project= projectRepository.findByProjectCode(projectCode);
        if(project==null)
            return null;
        if(releaseName.equalsIgnoreCase("")) {
            return projectRepository.findByProjectCode(projectCode).getReleases();
        }
        else {
            List<Release> releases = projectRepository.findByProjectCode(projectCode).getReleases();
            return releaseRepository.findByProjectId(project.getId());
        }
    }

}
