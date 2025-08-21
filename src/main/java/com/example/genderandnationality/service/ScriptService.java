package com.example.genderandnationality.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ScriptService{

    private final RestTemplate restTemplate = new RestTemplate();

    public void processCsv(String inputPath, String outputPath) {
        try (
                CSVReader reader = new CSVReader(new FileReader(inputPath));
                CSVWriter writer = new CSVWriter(new FileWriter(outputPath))
        ) {
            String[] header = reader.readNext();
            if (header == null) {
                throw new RuntimeException("CSV file is empty");
            }

            // add new columns
            String[] newHeader = new String[header.length + 2];
            System.arraycopy(header, 0, newHeader, 0, header.length);
            newHeader[header.length] = "Gender";
            newHeader[header.length + 1] = "Nationality";
            writer.writeNext(newHeader);

            String[] row;
            while ((row = reader.readNext()) != null) {
                String name = row[0]; // assume first column is Name
                String firstName = name.split(" ")[0];

                String gender = getGender(firstName);
                String nationality = getNationality(firstName);

                String[] newRow = new String[row.length + 2];
                System.arraycopy(row, 0, newRow, 0, row.length);
                newRow[row.length] = gender;
                newRow[row.length + 1] = nationality;

                writer.writeNext(newRow);
            }

            System.out.println("✅ CSV processed and saved to " + outputPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getGender(String firstName) {
        try {
            String url = "https://api.genderize.io?name=" + firstName;
            Map response = restTemplate.getForObject(url, Map.class);
            return response != null ? (String) response.get("gender") : "";
        } catch (Exception e) {
            return "";
        }
    }

    private String getNationality(String firstName) {
        try {
            String url = "https://api.nationalize.io?name=" + firstName;
            Map response = restTemplate.getForObject(url, Map.class);
            if (response != null && response.containsKey("country")) {
                List<Map<String, Object>> countries = (List<Map<String, Object>>) response.get("country");
                if (!countries.isEmpty()) {
                    return (String) countries.get(0).get("country_id"); // take top prediction
                }
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }
}
