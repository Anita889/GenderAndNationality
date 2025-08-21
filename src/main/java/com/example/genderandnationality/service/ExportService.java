package com.example.genderandnationality.service;

import com.example.genderandnationality.model.EnrichedPerson;
import com.example.genderandnationality.model.Person;
import com.example.genderandnationality.repository.EnrichedPersonRepository;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExportService {
    @Autowired
    private PersonService personService;

    @Autowired
    private GenderNationalityService genderNationalityService;

    @Autowired
    private EnrichedPersonRepository enrichedPersonRepository;

    @Value("${export.csv.path}")
    private String csvPath;


    public String exportToCsv() throws IOException {
        List<Person> people = personService.findAll();

        try (CSVWriter writer = new CSVWriter(new FileWriter(csvPath))) {
            writer.writeNext(new String[]{"Name", "Surname", "Gender", "Nationality"});
            List<EnrichedPerson> enrichedPeople = new ArrayList<>();
            for (Person p : people) {
                String gender = genderNationalityService.guessGender(p.getFirstName());
                String nationality = genderNationalityService.guessNationality(p.getLastName());

                writer.writeNext(new String[]{
                        p.getFirstName(),
                        p.getLastName(),
                        gender,
                        nationality
                });
                EnrichedPerson enriched = new EnrichedPerson();
                enriched.setFirstName(p.getFirstName());
                enriched.setLastName(p.getLastName());
                enriched.setGender(gender);
                enriched.setNationality(nationality);
                enrichedPeople.add(enriched);
            }
            enrichedPersonRepository.saveAll(enrichedPeople);
        }

        return csvPath;
    }
}
