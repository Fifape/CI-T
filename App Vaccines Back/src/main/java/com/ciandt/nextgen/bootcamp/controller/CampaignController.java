package com.ciandt.nextgen.bootcamp.controller;

import com.ciandt.nextgen.bootcamp.model.*;
import com.ciandt.nextgen.bootcamp.service.CampaignService;
import com.ciandt.nextgen.bootcamp.service.DoseRequestService;
import com.ciandt.nextgen.bootcamp.service.TransferDoseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController //Indicates that it is the class responsible for the HTTP request and returns JSON
@RequestMapping(value = "/campaign") //Defines that routes go through /"campaign"
public class CampaignController {

    @Autowired //Inject CampaignServices dependencies
    private CampaignService campaignService;

    @Autowired
    private DoseRequestService doseRequestService;

    @Autowired
    private TransferDoseService transferDoseService;

    @GetMapping("/all")
    public List<Campaign> getAllCampaigns() {
        return campaignService.getAllCampaigns();
    }

    @GetMapping("/active")
    public ResponseEntity<List<Campaign>> getActiveCampaigns() {
        List<Campaign> activeCampaigns = campaignService.findAllActive();
        if (activeCampaigns.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(activeCampaigns);
    }

    @GetMapping("/inactive")
    public ResponseEntity<List<Campaign>> getInactiveCampaigns() {
        List<Campaign> inactiveCampaigns = campaignService.findAllInactive();
        if (inactiveCampaigns.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(inactiveCampaigns);
    }

    @GetMapping("/{campaignId}/doseRequests")
    public List<DoseRequest> getNonPendentDoseRequestsByCampaignId(@PathVariable Long campaignId,
                                                                   @AuthenticationPrincipal User authUser) {
        return doseRequestService.getNonPendentDoseRequestsByCampaignId(campaignId, authUser);
    }

    @GetMapping("/pendentDoseRequests")
    public List<TransferRequest> getTransferRequestsByStatusAndReceiverId(@AuthenticationPrincipal User authUser, @RequestParam Long campaignId) {
        return transferDoseService.getTransferRequestsByStatusAndUserId(authUser.getId(), campaignId);
    }

    @PostMapping("/create")
    public ResponseEntity<Campaign> createCampaign(@RequestParam String name, Long status, @DateTimeFormat(pattern = "yyyy-MM-dd")Date startDate, @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, @AuthenticationPrincipal User authUser) {

        Campaign campaignCreated = campaignService.creteCampaign(name, status, startDate, endDate, authUser);
        return ResponseEntity.ok(campaignCreated);
    }

    @PutMapping("/update/{campaignId}/status")
    public ResponseEntity<Campaign> updateCampaignStatus(@PathVariable Long campaignId, @RequestParam Long status, @AuthenticationPrincipal User authUser){
        Campaign campaignUpdated = campaignService.updateCampaignStatus(campaignId, status, authUser);
        return ResponseEntity.ok(campaignUpdated);
    }
}

