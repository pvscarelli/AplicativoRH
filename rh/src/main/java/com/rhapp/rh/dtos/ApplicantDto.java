package com.rhapp.rh.dtos;

import com.rhapp.rh.models.Job;

import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record ApplicantDto(@Valid @NotBlank String cpf, @NotBlank String applicantName, @NotBlank String applicantEmail, @ManyToOne Job job) {

}
