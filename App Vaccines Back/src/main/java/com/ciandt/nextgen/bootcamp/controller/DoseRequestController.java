package com.ciandt.nextgen.bootcamp.controller;

import com.ciandt.nextgen.bootcamp.model.DoseRequest;
import com.ciandt.nextgen.bootcamp.model.User;
import com.ciandt.nextgen.bootcamp.service.DoseRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/request")
public class DoseRequestController {
    @Autowired //Inject DoseRequest dependencies
    private DoseRequestService doseRequestService;

    @GetMapping("/all")
    public List<DoseRequest> getAllDosesRequest() {
        return doseRequestService.getAllDosesRequest();
    }

    @PostMapping("/{id}/patient-name")
    public ResponseEntity<DoseRequest> updatePatientName(
            @PathVariable Long id,
            @RequestParam String patientName,
            @AuthenticationPrincipal User authUser) {

        DoseRequest updatedDoseRequest = doseRequestService.updatePatientName(id, patientName, authUser);
        return ResponseEntity.ok(updatedDoseRequest);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteDose(
            @PathVariable Long id,
            @AuthenticationPrincipal User authUser) {

        String message = doseRequestService.deleteDoseRequest(id, authUser);
        return ResponseEntity.ok(message);
    }
}
