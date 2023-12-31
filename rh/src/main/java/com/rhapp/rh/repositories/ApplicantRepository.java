package com.rhapp.rh.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhapp.rh.models.Applicant;
import com.rhapp.rh.models.Job;
import java.util.List;
import java.util.UUID;


@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, UUID> {

    Iterable<Applicant> findByJob(Job job);

    Applicant findByCpf(String cpf);
    
    List<Applicant> findByApplicantName(String applicantName);
}
