package com.ciandt.nextgen.bootcamp.repository;
import com.ciandt.nextgen.bootcamp.model.DoseRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoseRequestRepository extends JpaRepository<DoseRequest, Long> {
    List<DoseRequest> findAll();

    List<DoseRequest> findAllByCampaignId(Long campaignId);
}
