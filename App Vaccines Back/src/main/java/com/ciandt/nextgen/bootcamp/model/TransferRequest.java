package com.ciandt.nextgen.bootcamp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DOSE_TRANSFER_REQUESTS ")
public class TransferRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "requested_dose_id")
    private DoseRequest doseRequest;

    @ManyToOne
    @JoinColumn(name = "recipient_user_id")
    private User receiver;

    @Column(name = "dose_transfer_request_status_id")
    private Long status;

    public TransferRequest(DoseRequest doseRequest, User receiver, Long status) {
        this.doseRequest = doseRequest;
        this.receiver = receiver;
        this.status = status;
    }
}
