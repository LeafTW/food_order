package com.example.food_ordering.service.impl;

import com.example.food_ordering.repository.TotalOrderRepository;
import com.example.food_ordering.service.PdfService;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

@Service
public class PdfServiceimpl implements PdfService {

    @Autowired
    TotalOrderRepository totalOrderRepository;

    @Override
    public byte[] generatePdf(String name) throws IOException {
        // 加載支持中文的字體
//        String fontPath = "/Volumes/Data/Code/Java/My_Project/food_order/food_ordering_rear_end/src/main/resources/NotoSansCJKsc Regular.otf";
//        PdfFont font = PdfFontFactory.createFont(fontPath, PdfEncodings.IDENTITY_H, true);
//
//
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
//        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
//        Document document = new Document(pdfDocument);
//
//        List<TotalOrderEntity> items = totalOrderRepository.getTotalOrderEntityByUsername(name);
//
//        // 添加標題
//        document.add(new Paragraph("Items List"));
//
//        // 創建表格
//        Table table = new Table(new float[]{3, 12, 3});
//        table.addCell("ID");
//        table.addCell("Name");
//        table.addCell("Price");
//
//        for (TotalOrderEntity item : items) {
//            table.addCell(new Cell().add(new Paragraph(String.valueOf(item.getId())).setFont(font)));
//            table.addCell(new Cell().add(new Paragraph(item.getName()).setFont(font)));
//            table.addCell(new Cell().add(new Paragraph(String.valueOf(item.getTotalprice())).setFont(font)));
//        }
//
//        document.add(table);
//        document.close();
//
//        return byteArrayOutputStream.toByteArray();
        return null;
    }

    @Override
    public JasperPrint generateReport(String username) throws Exception {

        // 設置 JasperReports 屬性以加載字體
        JRPropertiesUtil.getInstance(DefaultJasperReportsContext.getInstance())
                .setProperty("net.sf.jasperreports.default.font.name", "教育部楷書字型");

        // 加載 jasper 文件
        Resource resource = new ClassPathResource("static/order_pdf.jasper");
        FileInputStream fis = new FileInputStream(resource.getFile());

        // 填充數據
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(totalOrderRepository.getTotalOrderEntityByUsername(username));
        HashMap<String, Object> parameters = new HashMap<>();
        JasperPrint jasperPrint = JasperFillManager.fillReport(fis, parameters, dataSource);

        return jasperPrint;

    }
}
