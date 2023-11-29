package Cooking.School.Project.cookingSchool.Services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DownloadService {

    private String fileName = "test.pdf";

    public void createPDF(String content) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(10, 700);
                contentStream.showText(content);
                contentStream.endText();
            }

            document.save(fileName);
            System.out.println("PDF created successfully.");
        } catch (IOException e) {
            System.err.println("Error creating the PDF: " + e.getMessage());
        }
    }
}
