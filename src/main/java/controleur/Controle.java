package controleur;

import vue.FrmQRCode;
import modele.GenerateurQRCode;
import modele.GenerateurPDF;

import java.awt.image.BufferedImage;

public class Controle {

    private FrmQRCode frmQRCode;
    private GenerateurQRCode generateurQRCode = new GenerateurQRCode();
    private GenerateurPDF generateurPDF = new GenerateurPDF();
    private BufferedImage dernierQRCodeGenere;

    public Controle() {
        frmQRCode = new FrmQRCode(this);
        frmQRCode.setVisible(true);
    }

    public void demandeGenererQRCode(String contenu) {
        try {
            dernierQRCodeGenere = generateurQRCode.genererImage(contenu);
            frmQRCode.afficherApercu(dernierQRCodeGenere);
            frmQRCode.afficherStatut("QR code généré avec succès.");
        } catch (IllegalArgumentException e) {
            frmQRCode.afficherStatut("Erreur : " + e.getMessage());
        } catch (Exception e) {
            frmQRCode.afficherStatut("Erreur inattendue lors de la génération du QR code.");
        }
    }

    public void demandeEnregistrerPdf(String texteOriginal, String cheminFichierPdf) {
        if (dernierQRCodeGenere == null) {
            frmQRCode.afficherStatut("Générez d'abord un QR code avant de l'enregistrer.");
            return;
        }
        try {
            generateurPDF.genererPdfAvecQRCode(texteOriginal, dernierQRCodeGenere, cheminFichierPdf);
            frmQRCode.afficherStatut("PDF enregistré : " + cheminFichierPdf);
        } catch (Exception e) {
            frmQRCode.afficherStatut("Erreur lors de l'enregistrement du PDF : " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Controle();
    }
}