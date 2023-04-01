package com.student.AutomationPortal.controller;

import com.student.AutomationPortal.service.ReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/release")
public class ReleaseController {
    @Autowired
    ReleaseService releaseService;

    @PostMapping("")
    public ResponseEntity<String> addRelease(@RequestParam(name="projectCode") String projectCode,@RequestParam(name="releaseName") String releaseName){
        return releaseService.addRelease(projectCode, releaseName);
    }
    @PutMapping("")
    public ResponseEntity<String> editRelease(@RequestParam(name="projectCode") String projectCode,@RequestParam(name="releaseName") String releaseName,@RequestParam(name="newReleaseName") String newReleaseName){
        return releaseService.editRelease(projectCode, releaseName, newReleaseName);
    }

    @GetMapping("")
    public ResponseEntity<String> getRelease(@RequestParam(name="projectCode") String projectCode,@RequestParam(name="releaseName", required = false) String releaseName){
        if(releaseName==null)
            return releaseService.getRelease(projectCode);
        else
            return releaseService.getRelease(projectCode, releaseName);
    }

    @DeleteMapping("")
    public ResponseEntity<String> delRelease(@RequestParam(name="projectCode") String projectCode,@RequestParam(name="releaseName", required = true) String releaseName){
        return releaseService.deleteRelease(projectCode, releaseName);
    }
}
