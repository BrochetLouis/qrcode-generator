package modele;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class GenerateurQRCodeTest {

    private GenerateurQRCode generateur;

    @BeforeEach
    void setUp() {
        generateur = new GenerateurQRCode();
    }

    @Test
    void testGenererImageAvecContenuValide() throws Exception {
        BufferedImage image = generateur.genererImage("https://example.com");
        assertNotNull(image, "L'image générée ne doit pas être nulle");
        assertTrue(image.getWidth() > 0, "L'image doit avoir une largeur positive");
        assertTrue(image.getHeight() > 0, "L'image doit avoir une hauteur positive");
    }

    @Test
    void testGenererImageAvecContenuVideLeveException() {
        assertThrows(IllegalArgumentException.class, () -> generateur.genererImage(""),
            "Un contenu vide doit être rejeté");
    }

    @Test
    void testGenererImageAvecContenuNullLeveException() {
        assertThrows(IllegalArgumentException.class, () -> generateur.genererImage(null),
            "Un contenu null doit être rejeté");
    }

    @Test
    void testGenererImageAvecEspacesUniquementLeveException() {
        assertThrows(IllegalArgumentException.class, () -> generateur.genererImage("   "),
            "Un contenu composé uniquement d'espaces doit être rejeté");
    }
}