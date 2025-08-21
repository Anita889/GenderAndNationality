package com.example.genderandnationality.controller;

import com.example.genderandnationality.service.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/basic/")
public class ExportController {

    @Autowired
    private ExportService exportService;

    @RequestMapping(value = "export", method = RequestMethod.GET)
    public String exportCsv() throws IOException {
        return "CSV exported to: " + exportService.exportToCsv();
    }
}

