package com.ciandt.nextgen.bootcamp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CAMPAIGNS")
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long campaign_status_id;
    private String name;
    private Date start_date;
    private Date end_date;

    public Campaign(String name, Long status, Date startDate, Date endDate) {
        this.name = name;
        this.campaign_status_id = status;
        this.start_date = startDate;
        this.end_date = endDate;
    }
}
