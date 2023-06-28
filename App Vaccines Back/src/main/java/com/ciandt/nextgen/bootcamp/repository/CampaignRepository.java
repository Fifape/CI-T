package com.ciandt.nextgen.bootcamp.repository;

import com.ciandt.nextgen.bootcamp.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    Optional<Campaign> findById(Long id);
    List<Campaign> findAll();

    Optional<Campaign> findByName(String name);
}
