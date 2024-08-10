package com.example.food_ordering.controller;

import com.example.food_ordering.service.PdfService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @GetMapping("/download/{username}")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable String username) throws IOException {
        byte[] pdfContent = null;
        pdfContent = pdfService.generatePdf(username);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "items.pdf");

        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
    }

    @GetMapping("download/jasperreports/{username}")
    public void generateReport(HttpServletResponse response, @PathVariable String username) {
        try {
            pdfService.generateReport(response, username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}