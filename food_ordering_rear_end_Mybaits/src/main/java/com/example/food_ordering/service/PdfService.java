package com.example.food_ordering.service;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface PdfService {


    byte[] generatePdf(String name) throws IOException;

    void generateReport(HttpServletResponse response, String username) throws Exception;
}
