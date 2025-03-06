package services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class QRCodeGenerator {
    public static void generateQRCode(String reservationData, String filePath) {
        int width = 300;
        int height = 300;
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(reservationData, BarcodeFormat.QR_CODE, width, height);
            // Ensure the parent directory exists
            File file = new File(filePath);
            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }
            Path path = FileSystems.getDefault().getPath(filePath);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
            System.out.println("QR Code generated successfully at: " + filePath);
        } catch (WriterException | IOException e) {
            System.err.println("Error generating QR Code: " + e.getMessage());
        }
    }
}
