package com.example.jpaap;

import com.example.jpaap.Repository.PatientRepository;
import com.example.jpaap.enities.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class JpaApApplication implements CommandLineRunner {

    @Autowired
    private PatientRepository patientRepository;

    public static void main(String[] args) {

        SpringApplication.run(JpaApApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        for(int i = 0; i < 100; i++){
            patientRepository.save(new Patient(null, "sutton", new Date(), (Math.random() > 0.5)?true: false, (int) (Math.random()*1000)));
        }
        Page<Patient> patients = patientRepository.findAll(PageRequest.of(0, 10));
        System.out.println(patients.getNumber() + "/" + patients.getTotalPages() + "---" + patients.getTotalElements());
        List<Patient> content = patients.getContent();
        Page<Patient> byMalade = patientRepository.findByMalade(true, PageRequest.of(0, 4));
        List<Patient> patientList = patientRepository.chercherPatients("%t%", 40);
        patientList.forEach(p ->{
            System.out.println("------------------------------");
            System.out.println(p.getId());
            System.out.println(p.getNom());
            System.out.println(p.getScore());
            System.out.println(p.getDateNaissance());
            System.out.println(p.isMalade());
        });
        System.out.println("------------------------------");
        Patient patient = patientRepository.findById(1L).orElse(null);
        if(patient != null){
            System.out.println(patient.getNom());
            System.out.println(patient.isMalade());
        }
        patient.setScore(870);
        patientRepository.save(patient);
        patientRepository.deleteById(1L);

    }
}
