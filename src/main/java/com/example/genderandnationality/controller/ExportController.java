package com.example.genderandnationality.controller;

import com.example.genderandnationality.service.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ExportController {

    @Autowired
    private ExportService exportService;

    @GetMapping("/export")
    public String exportCsv() throws IOException {
        return "CSV exported to: " + exportService.exportToCsv();
    }
}

