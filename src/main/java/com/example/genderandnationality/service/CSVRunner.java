package com.example.genderandnationality.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CSVRunner implements CommandLineRunner {

    @Autowired
    private ScriptService service;

    @Override
    public void run(String... args) {
        service.processCsv("people.csv", "people_with_gender_nationality.csv");
    }
}
