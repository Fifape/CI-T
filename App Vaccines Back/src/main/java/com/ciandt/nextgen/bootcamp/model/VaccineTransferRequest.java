package com.ciandt.nextgen.bootcamp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "VACCINE_TRANSFER_REQUESTS")
public class VaccineTransferRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Long amount;
    private Timestamp creation;

    @Column(name = "vaccine_status_id")
    private Long status;

    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;

    public VaccineTransferRequest(User authenticatedUser,Long amount, Long status, Campaign campaign) {
        this.user = authenticatedUser;
        this.amount = amount;
        this.status = status;
        this.campaign = campaign;
    }
}
