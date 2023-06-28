package com.ciandt.nextgen.bootcamp.repository;

import com.ciandt.nextgen.bootcamp.model.VaccineTransferRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccineRepository extends JpaRepository<VaccineTransferRequest, Long> {
}
