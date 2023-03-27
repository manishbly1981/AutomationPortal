package com.student.AutomationPortal.serviceImpl;

import com.student.AutomationPortal.model.Cycle;
import com.student.AutomationPortal.model.Release;
import com.student.AutomationPortal.repository.CycleRepository;
import com.student.AutomationPortal.service.CycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CycleServiceImpl implements CycleService {
    @Autowired
    ReleaseServiceImpl releaseService;

    @Autowired
    CycleRepository cycleRepository;
    @Override
    public ResponseEntity<String> addCycle(String projectName, String releaseName, String cycleName) {
        List<Release> releases = releaseService.getReleaseInternally(projectName, releaseName);
        if(releases==null ||releases.size()==0)
            return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Please check the project and release details");
        List<Cycle> cycles = releases.get(0).getCycles().stream().filter(c->c.getName().equalsIgnoreCase(cycleName)).collect(Collectors.toList());
        if(cycles.size()==0) {
            Cycle cycle = new Cycle();
            cycle.setRelease(releases.get(0));
            cycle.setName(cycleName);
            cycleRepository.save(cycle);
        }
        else{
            Cycle cycle= cycles.get(0);
            cycle.setName(releaseName);
            cycleRepository.save(cycle);
        }
        return CompactServiceImpl.reportResponse(HttpStatus.OK, releaseService.getReleaseInternally(projectName, releaseName).get(0).getCycles());
    }

    @Override
    public ResponseEntity<String> editCycle(String projectName, String releaseName, String cycleName, String newCycleName) {
        return editCycle(projectName, releaseName, cycleName, newCycleName);
    }

    @Override
    public ResponseEntity<String> getCycle(String projectName, String releaseName, String cycleName) {
        List<Release> releases = releaseService.getReleaseInternally(projectName, releaseName);
        if(releases==null ||releases.size()==0)
            return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Please check the project and release details");
        if(cycleName!=null) {
            List<Cycle> cycles = releases.get(0).getCycles().stream().filter(c -> c.getName().equalsIgnoreCase(cycleName)).collect(Collectors.toList());
            return CompactServiceImpl.reportResponse(HttpStatus.OK, cycles);
        }else{
            return CompactServiceImpl.reportResponse(HttpStatus.OK, releases.get(0).getCycles());
        }

    }

    @Override
    public ResponseEntity<String> delCycle(String projectName, String releaseName, String cycleName) {
        List<Release> releases = releaseService.getReleaseInternally(projectName, releaseName);
        if(releases==null ||releases.size()==0)
            return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Please check the project and release details");
        List<Cycle> cycles = releases.get(0).getCycles().stream().filter(c->c.getName().equalsIgnoreCase(cycleName)).collect(Collectors.toList());
        if (cycles.size()==0) {
            return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Cycle with name '"+ cycleName+"' not found");
        }else{
            cycleRepository.deleteAll(cycles);
            return CompactServiceImpl.reportResponse(HttpStatus.OK, "Cycle with name '"+ cycleName+"' Deleted");
        }
    }
}
