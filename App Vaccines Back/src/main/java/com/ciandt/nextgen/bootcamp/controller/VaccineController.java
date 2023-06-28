package com.ciandt.nextgen.bootcamp.controller;

import com.ciandt.nextgen.bootcamp.model.User;
import com.ciandt.nextgen.bootcamp.model.VaccineTransferRequest;
import com.ciandt.nextgen.bootcamp.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vaccine")
public class VaccineController {
    @Autowired
    private VaccineService vaccineService;

    @PostMapping("/{id}/request")
    public ResponseEntity<VaccineTransferRequest> vaccineRequest(
            @PathVariable Long id,
            @RequestParam Long amounts,
            @AuthenticationPrincipal User authUser) {

        VaccineTransferRequest vaccineRequest = vaccineService.createTransferRequest(id, amounts, authUser);
        return ResponseEntity.ok(vaccineRequest);
    }
}
