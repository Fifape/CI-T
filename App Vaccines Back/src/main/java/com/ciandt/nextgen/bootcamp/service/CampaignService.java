package com.ciandt.nextgen.bootcamp.service;

import com.ciandt.nextgen.bootcamp.exceptions.ForbiddenException;
import com.ciandt.nextgen.bootcamp.model.Campaign;
import com.ciandt.nextgen.bootcamp.model.DoseRequest;
import com.ciandt.nextgen.bootcamp.model.TransferRequest;
import com.ciandt.nextgen.bootcamp.model.User;
import com.ciandt.nextgen.bootcamp.repository.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CampaignService {

    @Autowired
    private CampaignRepository campaignRepository;

    public List<Campaign> getAllCampaigns() {
        return campaignRepository.findAll();
    }

    public List<Campaign> findAllActive() {
        List<Campaign> activeCampaigns = campaignRepository.findAll();
        List<Campaign> activeCampaignsList = activeCampaigns.stream()
                //Only filter the actives campaigns
                .filter(c -> c.getId() > 1 && c.getId() < 5)
                //Order by End Date
                .sorted(Comparator.comparing(Campaign::getEnd_date).reversed())
                .collect(Collectors.toList());

        if (activeCampaignsList.isEmpty()) throw new RuntimeException("No active campaigns");

        return activeCampaignsList;
    }

    public List<Campaign> findAllInactive() {
        List<Campaign> inactiveCampaigns = campaignRepository.findAll();
        List<Campaign> inactiveCampaignsList = inactiveCampaigns.stream()
                .filter(c -> c.getId() == 1 || c.getId() == 5)
                .sorted(Comparator.comparing(Campaign::getEnd_date).reversed())
                //Limit 3 Campaigns
                .limit(3)
                .collect(Collectors.toList());

        if (inactiveCampaignsList.isEmpty()) throw new RuntimeException("No inactive campaigns");

        return inactiveCampaignsList;
    }

    public Campaign creteCampaign(String name, Long status, Date startDate, Date endDate, User authenticatedUser) {

        // Checks if campaign already exists
        Optional<Campaign> existingCampaign = campaignRepository.findByName(name);
        if (existingCampaign.isPresent()){
            throw new ForbiddenException("Campanha já existe");
        }

        // Checks if Logged User is Admin
        if (!authenticatedUser.getAdmin()) {
            throw new ForbiddenException("Usuário não autorizado a criar campanhas.");
        }

        // Create transfer Request
        Campaign createdCampaign = new Campaign(name, status, startDate, endDate);


        return campaignRepository.save(createdCampaign);
    }

    public Campaign updateCampaignStatus(Long campaignId, Long status, User authenticatedUser) {

        // Checks if campaign exists
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new IllegalArgumentException("Campanha não encontrada"));

        // Checks if Logged User is Admin
        if (!authenticatedUser.getAdmin()) {
            throw new ForbiddenException("Usuário não autorizado a criar campanhas.");
        }

        //Checks if status is legal
        if (status < 1 || status > 3){
            throw new ForbiddenException("Status inválido.");
        }

        //Update Campaign Status
        campaign.setCampaign_status_id(status);

        return campaignRepository.save(campaign);
    }
}
