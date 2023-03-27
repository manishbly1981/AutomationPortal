package com.student.AutomationPortal.controller;

import com.student.AutomationPortal.service.CycleService;
import com.student.AutomationPortal.service.ReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cycle")
public class CycleController {
    @Autowired
    CycleService cycleService;

    @PostMapping("")
    public ResponseEntity<String> addCycle(@RequestParam(name = "projectCode") String projectCode, @RequestParam(name = "releaseName") String releaseName, @RequestParam(name = "cycleName") String cycleName) {
        return cycleService.addCycle(projectCode, releaseName, cycleName);
    }

    @PutMapping("")
    public ResponseEntity<String> editCycle(@RequestParam(name = "projectCode") String projectCode, @RequestParam(name = "releaseName") String releaseName, @RequestParam(name = "cycleName") String cycleName, @RequestParam(name = "newCycleName") String newCycleName) {
        return cycleService.editCycle(projectCode, releaseName, cycleName, newCycleName);
    }

    @GetMapping("")
    public ResponseEntity<String> getCycle(@RequestParam(name = "projectCode") String projectCode, @RequestParam(name = "releaseName") String releaseName, @RequestParam(name = "cycleName") String cycleName) {
        if (cycleName == null)
            return cycleService.getCycle(projectCode, releaseName, "");
        else
            return cycleService.getCycle(projectCode, releaseName, cycleName);
    }

    @DeleteMapping("")
    public ResponseEntity<String> delRelease(@RequestParam(name = "projectCode") String projectCode, @RequestParam(name = "releaseName", required = true) String releaseName, @RequestParam(name = "cycleName") String cycleName) {
        return cycleService.delCycle(projectCode, releaseName, cycleName);
    }
}
