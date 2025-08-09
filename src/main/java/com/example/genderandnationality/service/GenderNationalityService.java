package com.example.genderandnationality.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GenderNationalityService {

    private static final Map<String, String> specialGenderCases = Map.of(
            "lilit", "Female",
            "anahit", "Female",
            "mariam", "Female",
            "gayane", "Female",
            "narine", "Female"
    );

    private static final Map<String, String> nationalityMap = Map.of(
            "petrosyan", "Armenian",
            "smith", "English",
            "kim", "Korean",
            "ivanov", "Russian"
    );

    public String guessGender(String name) {
        String n = name.trim().toLowerCase();
        if (specialGenderCases.containsKey(n)) {
            return specialGenderCases.get(n);
        }
        if (n.endsWith("a") || n.endsWith("e")) return "Female";
        return "Male";
    }

    public String guessNationality(String surname) {
        String s = surname.trim().toLowerCase();
        return nationalityMap.getOrDefault(s, "Unknown");
    }
}
