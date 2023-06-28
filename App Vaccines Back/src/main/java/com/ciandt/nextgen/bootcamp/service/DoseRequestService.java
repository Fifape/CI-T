package com.ciandt.nextgen.bootcamp.service;

import com.ciandt.nextgen.bootcamp.exceptions.ForbiddenException;
import com.ciandt.nextgen.bootcamp.exceptions.NotFoundException;
import com.ciandt.nextgen.bootcamp.model.Campaign;
import com.ciandt.nextgen.bootcamp.model.DoseRequest;
import com.ciandt.nextgen.bootcamp.model.User;
import com.ciandt.nextgen.bootcamp.repository.DoseRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DoseRequestService {
    @Autowired
    private DoseRequestRepository doseRequestRepository;

    //List all Doses Requests without any filter
    public List<DoseRequest> getAllDosesRequest() {
        return doseRequestRepository.findAll();
    }

    // List all Doses that are not pendent
    public List<DoseRequest> getNonPendentDoseRequestsByCampaignId(Long campaignId, User authenticatedUser) {
        List<DoseRequest> doseRequests = doseRequestRepository.findAllByCampaignId(campaignId);
        return doseRequests.stream()
                .filter(d -> !d.getTransfer_request() && d.getRequester().equals(authenticatedUser)).collect(Collectors.toList());
    }

    // Update Patient Name from Doses
    public DoseRequest updatePatientName(Long id, String patientName, User authenticatedUser) {
        // Find DoseRequest by ID
        Optional<DoseRequest> optionalDoseRequest = doseRequestRepository.findById(id);
        if (optionalDoseRequest.isPresent()) {
            DoseRequest doseRequest = optionalDoseRequest.get();

            // Checks if Logged User is the Vaccine Owner
            if (!authenticatedUser.getId().equals(doseRequest.getRequester().getId())) {
                throw new ForbiddenException("Usuário não autorizado a atualizar a requisição de dose.");
            }

            // Checks Campaign Status is current available to edit patient
            Campaign campaign = doseRequest.getCampaign();
            Long campaignStatusId = campaign.getCampaign_status_id();
            if (campaignStatusId != 2 && campaignStatusId != 3 && campaignStatusId != 4) {
                throw new ForbiddenException("Não é possível atualizar a requisição de dose porque o status da campanha não está dentro dos valores esperados.");
            }

            // Checks if the vaccine has any transfer status
            if (doseRequest.getTransfer_request()) {
                throw new ForbiddenException("Não é possível atualizar a requisição de dose porque o status de transferência está pendente de alguma ação");
            }

            // Change Patient Name
            doseRequest.setPatient_name(patientName);
            return doseRequestRepository.save(doseRequest);
        } else {
            throw new NotFoundException("Requisição de dose não encontrada com id " + id);
        }
    }

    //Delete Dose Request
    public String deleteDoseRequest(Long id, User authenticatedUser) {
        // Find DoseRequest by ID
        Optional<DoseRequest> optionalDoseRequest = doseRequestRepository.findById(id);
        if (optionalDoseRequest.isPresent()) {
            DoseRequest doseRequest = optionalDoseRequest.get();

            // Checks if Logged User is the Vaccine Owner
            if (!authenticatedUser.getId().equals(doseRequest.getRequester().getId())) {
                throw new ForbiddenException("Usuário não autorizado a atualizar a requisição de dose.");
            }

            // Checks Campaign Status is current available to delete dose
            Campaign campaign = doseRequest.getCampaign();
            Long campaignStatusId = campaign.getCampaign_status_id();
            if (campaignStatusId != 2) {
                throw new ForbiddenException("Não é possível atualizar a requisição de dose porque o status da campanha não está dentro dos valores esperados.");
            }

            // Checks if the vaccine has any transfer status
            if (doseRequest.getTransfer_request()) {
                throw new ForbiddenException("Não é possível atualizar a requisição de dose porque o status de transferência está pendente de alguma ação");
            }

            // Delete Dose Request
            doseRequestRepository.delete(doseRequest);

            return "Dose excluída com sucesso!";
        } else {
            throw new NotFoundException("Requisição de dose não encontrada com id " + id);
        }
    }
}
