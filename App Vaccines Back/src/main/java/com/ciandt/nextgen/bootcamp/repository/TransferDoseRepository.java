package com.ciandt.nextgen.bootcamp.repository;

import com.ciandt.nextgen.bootcamp.model.TransferRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferDoseRepository extends JpaRepository<TransferRequest, Long> {
    List<TransferRequest> findAll();
}
