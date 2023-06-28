package com.ciandt.nextgen.bootcamp.service;

import com.ciandt.nextgen.bootcamp.exceptions.ForbiddenException;
import com.ciandt.nextgen.bootcamp.model.DoseRequest;
import com.ciandt.nextgen.bootcamp.model.TransferRequest;
import com.ciandt.nextgen.bootcamp.model.User;
import com.ciandt.nextgen.bootcamp.repository.DoseRequestRepository;
import com.ciandt.nextgen.bootcamp.repository.TransferDoseRepository;
import com.ciandt.nextgen.bootcamp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransferDoseService {

    @Autowired
    private TransferDoseRepository transferDoseRepository;
    @Autowired
    private DoseRequestRepository doseRequestRepository;
    @Autowired
    private UserRepository userRepository;


    //List all Transfer Doses without any filter
    public List<TransferRequest> getAllTransferDoses() {
        return transferDoseRepository.findAll();
    }

    //List all Pending Doses
    public List<TransferRequest> getTransferRequestsByStatusAndUserId(Long userId, Long campaignId) {
        List<TransferRequest> allTransferRequests = transferDoseRepository.findAll();
        return allTransferRequests.stream()
                .filter(transferRequest ->
                        (transferRequest.getReceiver().getId().equals(userId) ||
                                transferRequest.getDoseRequest().getRequester().getId().equals(userId))
                                && transferRequest.getDoseRequest().getCampaign().getId().equals(campaignId)
                                && transferRequest.getStatus() == 1)
                .collect(Collectors.toList());
    }

    public TransferRequest createTransferRequest(Long doseRequestId, String receiverUsername, User authenticatedUser) {

        // Checks if doseRequest exists
        DoseRequest doseRequest = doseRequestRepository.findById(doseRequestId)
                .orElseThrow(() -> new IllegalArgumentException("DoseRequest não encontrada com o ID: " + doseRequestId));

        // Checks if username exists
        User receiver = userRepository.findByUsername(receiverUsername)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        // Checks if Logged User is the Vaccine Owner
        if (!authenticatedUser.getId().equals(doseRequest.getRequester().getId())) {
            throw new ForbiddenException("Usuário não autorizado a atualizar a requisição de dose.");
        }

        // Checks if the vaccine has any transfer status
        if (doseRequest.getTransfer_request()) {
            throw new ForbiddenException("Não é possível atualizar a requisição de dose porque o status de transferência está pendente de alguma ação");
        }

        // Checks if Campaign Status allowed
        if (doseRequest.getCampaign().getCampaign_status_id() != 3) {
            throw new ForbiddenException("Esta campanha não está disponivel para alteração de titularidade");
        }

        // Checks if logged user isn't trying to send his vaccine to himself
        if(authenticatedUser.getUsername().equals(receiverUsername)){
            throw new ForbiddenException("Não é possivel transferir a dose para o proprio usuário");
        }
        // Checks requester and receiver location
        if ((doseRequest.getRequester().getBase().getId() == 1 || doseRequest.getRequester().getBase().getId() == 2) && (receiver.getBase().getId() != 1 && receiver.getBase().getId() != 2)){
            throw new ForbiddenException("Essa vacina não pode ser enviada para um colaborador fora do estado de SP");
        } else if(doseRequest.getRequester().getBase().getId() == 3 && receiver.getBase().getId() != 3) {
            throw new ForbiddenException("Essa vacina não pode ser enviada para um colaborador fora do estado de BH");
        }else if(doseRequest.getRequester().getBase().getId() == 4 && receiver.getBase().getId() != 4) {
            throw new ForbiddenException("Essa vacina não pode ser enviada para um colaborador fora do estado de MG");
        }

        // Set transfer request true
        doseRequest.setTransfer_request(true);

        // Create transfer Request
        TransferRequest transferRequest = new TransferRequest(doseRequest, receiver, 1L);
        return transferDoseRepository.save(transferRequest);
    }

    public TransferRequest refuseTransferRequest(Long transferRequestId, User authenticatedUser){
        TransferRequest transferRequest = transferDoseRepository.findById(transferRequestId)
                .orElseThrow(() -> new IllegalArgumentException("TransferRequest não encontrada com o ID: " + transferRequestId));

        if (!authenticatedUser.getId().equals(transferRequest.getReceiver().getId())) {
            throw new ForbiddenException("Usuário não autorizado a recusar a requisição de dose.");
        }

        transferRequest.setStatus(3L);
        transferRequest.getDoseRequest().setTransfer_request(false);

        transferRequest = transferDoseRepository.save(transferRequest);
        DoseRequest updatedDoseRequest = doseRequestRepository.save(transferRequest.getDoseRequest());
        transferRequest.setDoseRequest(updatedDoseRequest);

        return transferRequest;
    }

    public TransferRequest cancelTransferRequest(Long transferRequestId, User authenticatedUser){
        TransferRequest transferRequest = transferDoseRepository.findById(transferRequestId)
                .orElseThrow(() -> new IllegalArgumentException("DoseRequest não encontrada com o ID: " + transferRequestId));

        if (!authenticatedUser.getId().equals(transferRequest.getDoseRequest().getRequester().getId())) {
            throw new ForbiddenException("Usuário não autorizado a cancelar a requisição de dose.");
        }

        transferRequest.setStatus(2L);
        transferRequest.getDoseRequest().setTransfer_request(false);

        transferRequest = transferDoseRepository.save(transferRequest);
        DoseRequest updatedDoseRequest = doseRequestRepository.save(transferRequest.getDoseRequest());
        transferRequest.setDoseRequest(updatedDoseRequest);

        return transferRequest;
    }

    public TransferRequest acceptTransferRequest(Long transferRequestId, User authenticatedUser){
        TransferRequest transferRequest = transferDoseRepository.findById(transferRequestId)
                .orElseThrow(() -> new IllegalArgumentException("DoseRequest não encontrada com o ID: " + transferRequestId));

        if (!authenticatedUser.getId().equals(transferRequest.getReceiver().getId())) {
            throw new ForbiddenException("Usuário não autorizado a aceitar a requisição de dose.");
        }

        transferRequest.setStatus(4L);
        transferRequest.getDoseRequest().setRequester(transferRequest.getReceiver());
        transferRequest.getDoseRequest().setTransfer_request(false);

        transferRequest = transferDoseRepository.save(transferRequest);
        DoseRequest updatedDoseRequest = doseRequestRepository.save(transferRequest.getDoseRequest());
        transferRequest.setDoseRequest(updatedDoseRequest);

        return transferRequest;
    }
}
