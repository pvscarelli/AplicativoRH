package com.rhapp.rh.dtos;

import java.util.List;

import org.hibernate.validator.constraints.UniqueElements;

import com.rhapp.rh.models.Applicant;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record JobDto(@Valid @NotBlank String jobName,
                @NotBlank String jobDescription,
                @NotBlank String jobDate,
                @NotNull String jobSalary,
                @UniqueElements @OneToMany(mappedBy = "job", cascade = CascadeType.REMOVE) List<Applicant> applicants) {

}
