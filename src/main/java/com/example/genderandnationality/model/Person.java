package com.example.genderandnationality.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "people")
public class Person {
    @Id
    private String id;
    private String name;
    private String surname;
}

