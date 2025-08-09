package com.example.genderandnationality.repository;
import com.example.genderandnationality.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<Person, String> {
}
