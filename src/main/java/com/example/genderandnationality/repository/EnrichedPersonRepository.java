package com.example.genderandnationality.repository;

import com.example.genderandnationality.model.EnrichedPerson;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EnrichedPersonRepository extends MongoRepository<EnrichedPerson, String> {
}

