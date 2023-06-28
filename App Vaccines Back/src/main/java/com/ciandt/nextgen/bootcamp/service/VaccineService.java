package com.ciandt.nextgen.bootcamp.service;

import com.ciandt.nextgen.bootcamp.exceptions.ForbiddenException;
import com.ciandt.nextgen.bootcamp.model.Campaign;
import com.ciandt.nextgen.bootcamp.model.User;
import com.ciandt.nextgen.bootcamp.model.VaccineTransferRequest;
import com.ciandt.nextgen.bootcamp.repository.CampaignRepository;
import com.ciandt.nextgen.bootcamp.repository.VaccineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VaccineService {

    @Autowired
    private VaccineRepository vaccineRepository;
    @Autowired
    private CampaignRepository campaignRepository;

    public VaccineTransferRequest createTransferRequest(Long campaignId, Long amounts, User authenticatedUser) {
       //Find Campaign
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new IllegalArgumentException("Campanha não encontrada"));

        //Set vaccine request information
        VaccineTransferRequest vaccineTransferRequest = new VaccineTransferRequest(authenticatedUser, amounts, 1L, campaign);


        return vaccineRepository.save(vaccineTransferRequest);
    }
}
