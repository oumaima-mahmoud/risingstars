package services;

import entite.Reclamation;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

import java.io.IOException;
import java.awt.Color;

public class PDFExportService {

    public void exportReclamationToPDF(Reclamation reclamation, String filePath) {
        try (PDDocument document = new PDDocument()) {
            // Create a new page
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            // Create a content stream for the page
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Set up fonts
            PDType1Font titleFont = PDType1Font.HELVETICA_BOLD;
            PDType1Font headerFont = PDType1Font.HELVETICA_BOLD;
            PDType1Font bodyFont = PDType1Font.HELVETICA;

            // Set up colors
            contentStream.setLineWidth(1.5f); // Thicker lines for borders
            contentStream.setStrokingColor(0, 51, 102); // Dark Blue for lines
            contentStream.setNonStrokingColor(0, 0, 0); // Black text color

            // Draw page title with large font
            contentStream.beginText();
            contentStream.setFont(titleFont, 22);
            contentStream.newLineAtOffset(50, 800);
            contentStream.setNonStrokingColor(0, 102, 204); // Blue title color
            contentStream.showText("Reclamation Details");
            contentStream.endText();

            // Insert a logo or image (optional)
            PDImageXObject logo = PDImageXObject.createFromFile("/Users/cherif/Downloads/logo.png", document);
            contentStream.drawImage(logo, 450, 750, 100, 100); // Adjust the logo size and position

            // Add a separator line after the title
            contentStream.moveTo(50, 790);
            contentStream.lineTo(550, 790);
            contentStream.stroke();

            // Add reclamation details in a more structured format
            int yOffset = 760; // Starting Y position

            // Set header font for sections
            contentStream.setFont(headerFont, 14);
            contentStream.beginText();
            contentStream.setNonStrokingColor(0, 51, 102); // Blue for headers
            contentStream.newLineAtOffset(50, yOffset);
            contentStream.showText("ID:");
            contentStream.endText();

            // Add reclamation ID
            contentStream.setFont(bodyFont, 12);
            contentStream.beginText();
            contentStream.setNonStrokingColor(0, 0, 0); // Black for text
            contentStream.newLineAtOffset(150, yOffset);
            contentStream.showText(String.valueOf(reclamation.getId())); // Convert int to String
            contentStream.endText();

            yOffset -= 20;
            contentStream.beginText();
            contentStream.setNonStrokingColor(0, 51, 102); // Blue for headers
            contentStream.newLineAtOffset(50, yOffset);
            contentStream.showText("Description:");
            contentStream.endText();

            // Add reclamation description
            contentStream.setFont(bodyFont, 12);
            contentStream.beginText();
            contentStream.setNonStrokingColor(0, 0, 0); // Black text
            contentStream.newLineAtOffset(150, yOffset);
            contentStream.showText(reclamation.getDescription());
            contentStream.endText();

            yOffset -= 20;
            contentStream.beginText();
            contentStream.setNonStrokingColor(0, 51, 102); // Blue for headers
            contentStream.newLineAtOffset(50, yOffset);
            contentStream.showText("Type:");
            contentStream.endText();

            // Add type info
            contentStream.setFont(bodyFont, 12);
            contentStream.beginText();
            contentStream.setNonStrokingColor(0, 0, 0); // Black text
            contentStream.newLineAtOffset(150, yOffset);
            contentStream.showText(reclamation.getType());
            contentStream.endText();

            // Continue with other reclamation details (Etc.)
            yOffset -= 20;
            contentStream.beginText();
            contentStream.setNonStrokingColor(0, 51, 102); // Blue for headers
            contentStream.newLineAtOffset(50, yOffset);
            contentStream.showText("Etat:");
            contentStream.endText();

            // Add Etat info
            contentStream.setFont(bodyFont, 12);
            contentStream.beginText();
            contentStream.setNonStrokingColor(0, 0, 0); // Black text
            contentStream.newLineAtOffset(150, yOffset);
            contentStream.showText(reclamation.getEtat());
            contentStream.endText();

            yOffset -= 20;
            contentStream.beginText();
            contentStream.setNonStrokingColor(0, 51, 102); // Blue for headers
            contentStream.newLineAtOffset(50, yOffset);
            contentStream.showText("Date:");
            contentStream.endText();

            // Add Date info
            contentStream.setFont(bodyFont, 12);
            contentStream.beginText();
            contentStream.setNonStrokingColor(0, 0, 0); // Black text
            contentStream.newLineAtOffset(150, yOffset);
            contentStream.showText(reclamation.getDateReclamation());
            contentStream.endText();

            yOffset -= 20;
            contentStream.beginText();
            contentStream.setNonStrokingColor(0, 51, 102); // Blue for headers
            contentStream.newLineAtOffset(50, yOffset);
            contentStream.showText("Phone:");
            contentStream.endText();

            // Add Phone info
            contentStream.setFont(bodyFont, 12);
            contentStream.beginText();
            contentStream.setNonStrokingColor(0, 0, 0); // Black text
            contentStream.newLineAtOffset(150, yOffset);
            contentStream.showText(reclamation.getPhoneNumber());
            contentStream.endText();

            // Footer with page number (optional)
            contentStream.setFont(bodyFont, 10);
            contentStream.beginText();
            contentStream.setNonStrokingColor(169, 169, 169); // Grey for footer
            contentStream.newLineAtOffset(50, 40);
            contentStream.showText("Page 1 of 1");  // You can calculate dynamic page numbers if needed
            contentStream.endText();

            // Close the content stream
            contentStream.close();

            // Save the document to a file
            document.save(filePath);
            System.out.println("PDF created successfully at: " + filePath);
        } catch (IOException e) {
            System.out.println("Error creating PDF: " + e.getMessage());
        }
    }
}
