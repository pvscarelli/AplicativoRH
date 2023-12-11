package com.rhapp.rh.controllers;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rhapp.rh.dtos.*;
import com.rhapp.rh.models.*;
import com.rhapp.rh.repositories.*;

import jakarta.validation.Valid;

@RestController
public class JobControlller {

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private ApplicantRepository applicantRepository;

    @GetMapping("/favicon.ico")
    @ResponseBody
    public void returnNoFavicon() {
    }

    @GetMapping("/jobs")
    public ModelAndView showJobs() {
        ModelAndView mv = new ModelAndView("job/showJobs");
        Iterable<Job> jobs = jobRepository.findAll();
        mv.addObject("jobs", jobs);
        return mv;
    }

    @GetMapping("/{id}")
    public ModelAndView jobDetails(@PathVariable("id") UUID id) {
        Optional<Job> jobOptional = jobRepository.findById(id);
        ModelAndView mv = new ModelAndView("job/jobDetails");

        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();
            mv.addObject("job", job);
            Iterable<Applicant> applicants = applicantRepository.findByJob(job);
            mv.addObject("applicants", applicants);
        }
        return mv;
    }

    @GetMapping("/editJob/{id}")
    public ModelAndView editJob(@PathVariable UUID id) {
        Optional<Job> jobOptional = jobRepository.findById(id);
        ModelAndView mv = new ModelAndView("job/updateJob");
        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();
            mv.addObject("job", job);
        }
        return mv;
    }

    @GetMapping("/postJob")
    public ModelAndView showForm() {
        return new ModelAndView("job/jobForm");
    }

    @PostMapping("/postJob")
    public ModelAndView postForm(@Valid JobDto jobDto, BindingResult result, RedirectAttributes attributes) {

        if (result.hasErrors()) {
            attributes.addFlashAttribute("error_message", "Verifique os campos...");
            return new ModelAndView("redirect:/postJob");
        }

        Job job = new Job();
        BeanUtils.copyProperties(jobDto, job);

        jobRepository.save(job);
        attributes.addFlashAttribute("message", "Vaga cadastrada com sucesso!");
        return new ModelAndView("redirect:/postJob");
    }

    @PostMapping("/{id}")
    public ModelAndView postJobDetails(@PathVariable("id") UUID id, @Valid ApplicantDto applicantDto,
            BindingResult result, RedirectAttributes attributes) {

        if (result.hasErrors()) {
            attributes.addFlashAttribute("error_message", "Verifique os campos");
        }

        Applicant applicant = new Applicant();
        BeanUtils.copyProperties(applicantDto, applicant);

        if (applicantRepository.findByCpf(applicant.getCpf()) != null) {
            attributes.addFlashAttribute("error_message", "CPF duplicado");
            return new ModelAndView("redirect:/{id}");
        }

        Optional<Job> jobOptional = jobRepository.findById(id);
        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();
            applicant.setJob(job);
        }
        applicantRepository.save(applicant);

        attributes.addFlashAttribute("message", "Candidato adicionado com sucesso!");
        return new ModelAndView("redirect:/{id}");
    }

    @PutMapping("/editJob/{id}")
    public ModelAndView updateJob(@PathVariable(value = "id") UUID id, @Valid JobDto jobDto,
            BindingResult result, RedirectAttributes attributes) {

        Optional<Job> jobOptional = jobRepository.findById(id);

        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();
            BeanUtils.copyProperties(jobDto, job);
            jobRepository.save(job);
        }

        attributes.addFlashAttribute("message", "Vaga alterada com sucesso!");

        return new ModelAndView("redirect:/editJob/{id}", Collections.singletonMap("id", id));
    }

    @RequestMapping("/deleteJob/{id}")
    public ModelAndView deleteJobById(@PathVariable("id") UUID id) {
        jobRepository.deleteById(id);
        return new ModelAndView("redirect:/jobs");
    }

    @RequestMapping("/deleteApplicant")
    public ModelAndView deleteApplicantByCpf(@RequestParam String cpf) {
        Applicant applicant = applicantRepository.findByCpf(cpf);
        Job job = applicant.getJob();
        applicantRepository.delete(applicant);

        return new ModelAndView("redirect:/" + job.getId());
    }
}
