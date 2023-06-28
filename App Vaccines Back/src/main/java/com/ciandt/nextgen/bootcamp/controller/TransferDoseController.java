package com.ciandt.nextgen.bootcamp.controller;

import com.ciandt.nextgen.bootcamp.model.TransferRequest;
import com.ciandt.nextgen.bootcamp.model.User;
import com.ciandt.nextgen.bootcamp.service.TransferDoseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/transfer")
public class TransferDoseController {

    @Autowired //Inject TransferDose dependencies
    private TransferDoseService transferDoseService;

    @GetMapping("/all")
    public List<TransferRequest> getAllTransferDoses() {
        return transferDoseService.getAllTransferDoses();
    }

    @PostMapping("/create")
    public ResponseEntity<TransferRequest> createTransferRequest(@RequestParam Long doseRequestId, @RequestParam String receiverUsername,
                                                                 Authentication authentication) {

        User authenticatedUser = (User) authentication.getPrincipal();
        TransferRequest createdTransferRequest = transferDoseService.createTransferRequest(doseRequestId, receiverUsername, authenticatedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTransferRequest);
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<TransferRequest> refuseTransferRequest(@PathVariable Long id,
                                                                 @AuthenticationPrincipal User authUser) {

        TransferRequest refuseTransferRequest = transferDoseService.refuseTransferRequest(id, authUser);
        return ResponseEntity.ok(refuseTransferRequest);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<TransferRequest> cancelTransferRequest(@PathVariable Long id,
                                                                 @AuthenticationPrincipal User authUser) {

        TransferRequest cancelTransferRequest = transferDoseService.cancelTransferRequest(id, authUser);
        return ResponseEntity.ok(cancelTransferRequest);
    }

    @PostMapping("/{id}/accept")
    public ResponseEntity<TransferRequest> acceptTransferRequest(@PathVariable Long id,
                                                                 @AuthenticationPrincipal User authUser) {

        TransferRequest acceptTransferRequest = transferDoseService.acceptTransferRequest(id, authUser);
        return ResponseEntity.ok(acceptTransferRequest);
    }
}

