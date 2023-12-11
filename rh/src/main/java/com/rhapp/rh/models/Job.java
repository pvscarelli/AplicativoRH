package com.rhapp.rh.models;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.GenerationType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_JOBS")
public class Job implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name="id")
    private UUID jobId;
    private String jobName;
    private String jobDescription;
    private String jobDate;
    private String jobSalary;

    @ElementCollection
    @CollectionTable(name = "applicants", joinColumns = @JoinColumn(name = "job_id"))
    @Column(name = "applicant")
    private List<Applicant> applicants;

    public UUID getId() {
        return jobId;
    }

    public void setId(UUID jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getJobDate() {
        return jobDate;
    }

    public void setJobDate(String jobDate) {
        this.jobDate = jobDate;
    }

    public String getJobSalary() {
        return jobSalary;
    }

    public void setJobSalary(String jobSalary) {
        this.jobSalary = jobSalary;
    }

    public List<Applicant> getApplicants() {
        return applicants;
    }

    public void setApplicants(List<Applicant> applicants) {
        this.applicants = applicants;
    }

    
}
