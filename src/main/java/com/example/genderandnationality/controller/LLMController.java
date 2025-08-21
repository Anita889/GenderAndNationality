package com.example.genderandnationality.controller;

import com.example.genderandnationality.service.LLMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/llm")
public class LLMController {

    @Autowired
    private LLMService llmService;
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public ResponseEntity<?> processCsv() {
        return ResponseEntity.ok(llmService.enrichCsv());
    }
}
