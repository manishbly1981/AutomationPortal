package com.student.AutomationPortal.serviceImpl;

import com.student.AutomationPortal.model.Cycle;
import com.student.AutomationPortal.model.ExecutionRelease;
import com.student.AutomationPortal.repository.CycleRepository;
import com.student.AutomationPortal.repository.ReleaseRepository;
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
    ReleaseRepository releaseRepository;
    @Autowired
    CycleRepository cycleRepository;
    @Override
    public ResponseEntity<String> addCycle(String projectCode, String releaseName, String cycleName) {
        ExecutionRelease release = releaseService.getReleaseInternally(projectCode, releaseName);
        if(release==null)
            return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Please check the project and release details");
        List<Cycle> cycles = release.getCycles().stream().filter(c->c.getName().equalsIgnoreCase(cycleName)).collect(Collectors.toList());
        if(cycles.size()==0) {
            Cycle cycle = new Cycle();
            cycle.setExecutionRelease(release);
            cycle.setName(cycleName);
            cycleRepository.save(cycle);
            return CompactServiceImpl.reportResponse(HttpStatus.OK, cycleRepository.findByName(cycleName));
        }
        else{
            return CompactServiceImpl.reportResponse(HttpStatus.FOUND, "Cycle already exist");
        }

    }

    @Override
    public ResponseEntity<String> editCycle(String projectCode, String releaseName, String cycleName, String newCycleName) {
        ExecutionRelease release = releaseService.getReleaseInternally(projectCode, releaseName);
        if(release==null){
            return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Release '"+ releaseName+ "' does not exist");
        }
        List<Cycle> cycles = cycleRepository.findByNameAndExecutionReleaseId(cycleName, release.getId());
        if(cycles.size()==0){
            return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Cycle "+ cycleName +" does not exist");
        }
        Cycle cycle=cycles.get(0);
        cycle.setName(newCycleName);
        cycleRepository.save(cycle);
        return CompactServiceImpl.reportResponse(HttpStatus.OK, cycleRepository.findByName(newCycleName));
    }

    @Override
    public ResponseEntity<String> getCycle(String projectName, String releaseName, String cycleName) {
        ExecutionRelease releases = releaseService.getReleaseInternally(projectName, releaseName);
        if(releases==null)
            return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Please check the project and release details");
        if (cycleName==null|| cycleName.equalsIgnoreCase("")){
            return CompactServiceImpl.reportResponse(HttpStatus.OK, releases.getCycles());
        }
        else{
            List<Cycle> cycles = cycleRepository.findByNameAndExecutionReleaseId(cycleName, releases.getId());
            return CompactServiceImpl.reportResponse(HttpStatus.OK, cycles);
        }

    }

    @Override
    public ResponseEntity<String> delCycle(String projectCode, String releaseName, String cycleName) {
        ExecutionRelease release = releaseRepository.findByProjectProjectCodeAndName(projectCode, releaseName);
        if(release==null)
            return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Please check the project and release details");
        Cycle cycle= cycleRepository.findByExecutionReleaseIdAndName(release.getId(), cycleName);
        if(cycle==null)
            return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Cycle '" +cycleName + "' not found");
        cycleRepository.delete(cycle);
        return CompactServiceImpl.reportResponse(HttpStatus.OK, "Cycle with name '"+ cycleName+"' Deleted");

//        Cycle cycle= cycleRepository.findByExecutionReleaseNameAndName(releaseName, cycleName);
//        if(cycle==null)
//            return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Cycle " +cycleName + "not found");
//        cycleRepository.delete(cycle);
//        return CompactServiceImpl.reportResponse(HttpStatus.OK, "Cycle with name '"+ cycleName+"' Deleted");
//
//        List<Cycle> cycles = cycleRepository.findByNameAndExecutionReleaseId(cycleName, releases.getId());
//        if (cycles.size()==0) {
//            return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Cycle with name '"+ cycleName+"' not found");
//        }else{
//            cycleRepository.deleteAll(cycles);
//            cycleRepository.delete(cycles.get(0));
//            return CompactServiceImpl.reportResponse(HttpStatus.OK, "Cycle with name '"+ cycleName+"' Deleted");
//        }
    }
}
