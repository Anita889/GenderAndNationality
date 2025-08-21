package com.example.genderandnationality.service;

import com.example.genderandnationality.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.List;
import java.util.Map;

@Service
public class LLMService {

    @Value(value = "${openai.api-key}")
    private String openAiKey;

    @Autowired
    private PersonService personService;

    private static final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";

    public String processCsvWithLLM(String csvContent) {
        RestTemplate restTemplate = new RestTemplate();

        String prompt = """
        You are an information extraction assistant.
        I will provide you with CSV data that contains names of people.
        Your task is to analyze each name, infer the most likely Gender (Male/Female/Unknown)
        and the most likely Nationality (country or cultural origin).

        Return the output strictly in CSV format, with all original columns preserved
        and two additional columns: "Gender" and "Nationality".

        Do not add explanations or text outside of the CSV.

        Here is the data:
        """ + csvContent;

        Map<String, Object> request = Map.of(
                "model", "gpt-4o-mini",   // or gpt-4 if available
                "messages", new Object[]{
                        Map.of("role", "system", "content", "You are a CSV processing assistant."),
                        Map.of("role", "user", "content", prompt)
                }
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAiKey);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);
        ResponseEntity<Map> response = restTemplate.exchange(OPENAI_URL, HttpMethod.POST, entity, Map.class);

        return ((Map<String, String>) ((Map<?, ?>) ((java.util.List<?>) response.getBody().get("choices")).get(0))
                .get("message")).get("content");
    }

    public String enrichCsv() {
        List<Person> people = personService.findAll();
        String csvContent = convertPersonsToCsv(people);
        String f = processCsvWithLLM(csvContent);
        return f;
    }
    private String convertPersonsToCsv(List<Person> peaople) {
        if (peaople.isEmpty()) return "";

        StringBuilder sb = new StringBuilder("Name,SurName\n");
        for (Person p : peaople) {
            sb.append(p.getFirstName()).append(",").append(p.getLastName()).append("\n");
        }
        return processCsvWithLLM(String.valueOf(sb));
    }
}
