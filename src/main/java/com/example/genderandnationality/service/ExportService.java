package com.example.genderandnationality.service;

import com.example.genderandnationality.model.Person;
import com.example.genderandnationality.repository.PersonRepository;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class ExportService {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private GenderNationalityService genderNationalityService;

    @Value("${export.csv.path}")
    private String csvPath;


    public String exportToCsv() throws IOException {
        List<Person> people = personRepository.findAll();

        try (CSVWriter writer = new CSVWriter(new FileWriter(csvPath))) {
            writer.writeNext(new String[]{"Name", "Surname", "Gender", "Nationality"});

            for (Person p : people) {
                String gender = genderNationalityService.guessGender(p.getName());
                String nationality = genderNationalityService.guessNationality(p.getSurname());

                writer.writeNext(new String[]{
                        p.getName(),
                        p.getSurname(),
                        gender,
                        nationality
                });
            }
        }

        return csvPath;
    }
}
