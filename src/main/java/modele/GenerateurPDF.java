package modele;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class GenerateurPDF {

    public void genererPdfAvecQRCode(String texteOriginal, BufferedImage imageQRCode, String cheminFichierPdf)
            throws DocumentException, IOException {

        if (cheminFichierPdf == null || cheminFichierPdf.trim().isEmpty()) {
            throw new IllegalArgumentException("Le chemin du fichier PDF ne peut pas être vide");
        }

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(cheminFichierPdf));
            document.open();

            document.add(new Paragraph("QR Code généré pour : " + texteOriginal));

            ByteArrayOutputStream flux = new ByteArrayOutputStream();
            ImageIO.write(imageQRCode, "png", flux);
            Image image = Image.getInstance(flux.toByteArray());
            document.add(image);

        } finally {
            document.close();
        }
    }
}