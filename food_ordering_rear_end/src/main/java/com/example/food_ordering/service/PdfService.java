package com.example.food_ordering.service;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperPrint;

import java.io.IOException;

public interface PdfService {


    byte[] generatePdf(String name) throws IOException;

    JasperPrint generateReport(String username) throws Exception;
}
