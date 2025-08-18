package com.example.genderandnationality.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "input_db")
public class Person {
    @Id
    private String id;
    private String FirstName;
    private String LastName;
}

