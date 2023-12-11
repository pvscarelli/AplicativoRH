package com.rhapp.rh.models;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_APPLICANTS")
public class Applicant implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idApplicant;

    private String cpf;
    private String applicantName;
    private String applicantEmail;
    private Job job;
    public UUID getIdApplicant() {
        return idApplicant;
    }
    public void setIdApplicant(UUID idApplicant) {
        this.idApplicant = idApplicant;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getApplicantName() {
        return applicantName;
    }
    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }
    public String getApplicantEmail() {
        return applicantEmail;
    }
    public void setApplicantEmail(String applicantEmail) {
        this.applicantEmail = applicantEmail;
    }
    public Job getJob() {
        return job;
    }
    public void setJob(Job job){
        this.job = job;
    }    
}

