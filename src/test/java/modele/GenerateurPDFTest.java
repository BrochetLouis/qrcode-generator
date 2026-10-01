package modele;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class GenerateurPDFTest {

    private GenerateurPDF generateurPDF;
    private GenerateurQRCode generateurQRCode;
    private BufferedImage imageTest;
    private final String cheminFichierTest = "test_qrcode.pdf";

    @BeforeEach
    void setUp() throws Exception {
        generateurPDF = new GenerateurPDF();
        generateurQRCode = new GenerateurQRCode();
        imageTest = generateurQRCode.genererImage("https://example.com");
    }

    @AfterEach
    void nettoyer() {
        File fichier = new File(cheminFichierTest);
        if (fichier.exists()) {
            fichier.delete();
        }
    }

    @Test
    void testGenererPdfCreeUnFichier() throws Exception {
        generateurPDF.genererPdfAvecQRCode("https://example.com", imageTest, cheminFichierTest);

        File fichier = new File(cheminFichierTest);
        assertTrue(fichier.exists(), "Le fichier PDF doit être créé sur le disque");
        assertTrue(fichier.length() > 0, "Le fichier PDF ne doit pas être vide");
    }

    @Test
    void testGenererPdfAvecCheminVideLeveException() {
        assertThrows(IllegalArgumentException.class,
            () -> generateurPDF.genererPdfAvecQRCode("texte", imageTest, ""),
            "Un chemin de fichier vide doit être rejeté");
    }

    @Test
    void testGenererPdfAvecCheminNullLeveException() {
        assertThrows(IllegalArgumentException.class,
            () -> generateurPDF.genererPdfAvecQRCode("texte", imageTest, null),
            "Un chemin de fichier null doit être rejeté");
    }
}