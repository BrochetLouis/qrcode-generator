package modele;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.awt.image.BufferedImage;

public class GenerateurQRCode {

    private static final int TAILLE_PAR_DEFAUT = 300;

    public BufferedImage genererImage(String contenu) throws WriterException {
        if (contenu == null || contenu.trim().isEmpty()) {
            throw new IllegalArgumentException("Le contenu du QR code ne peut pas être vide");
        }

        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix matrice = writer.encode(contenu, BarcodeFormat.QR_CODE, TAILLE_PAR_DEFAUT, TAILLE_PAR_DEFAUT);
        return MatrixToImageWriter.toBufferedImage(matrice);
    }
}