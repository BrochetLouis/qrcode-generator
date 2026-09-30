package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import controleur.Controle;

public class FrmQRCode extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private Controle controle;

    private final JLabel lblConsigne = new JLabel("Saisissez le texte ou lien à encoder :");
    private final JTextField txtContenu = new JTextField();
    private final JButton btnGenerer = new JButton("Générer QR Code");
    private final JLabel lblApercu = new JLabel();
    private final JButton btnEnregistrerPdf = new JButton("Enregistrer en PDF");
    private final JLabel lblStatut = new JLabel(" ");

    public FrmQRCode(Controle controle) {
        this.controle = controle;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 420, 500);
        contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        lblConsigne.setBounds(10, 10, 380, 20);
        contentPane.add(lblConsigne);

        txtContenu.setBounds(10, 40, 280, 25);
        contentPane.add(txtContenu);
        txtContenu.setColumns(10);

        btnGenerer.setBounds(300, 38, 90, 27);
        contentPane.add(btnGenerer);
        btnGenerer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                cmdGenerer();
            }
        });

        lblApercu.setBounds(60, 90, 300, 300);
        lblApercu.setHorizontalAlignment(SwingConstants.CENTER);
        lblApercu.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        contentPane.add(lblApercu);

        btnEnregistrerPdf.setBounds(100, 400, 200, 27);
        contentPane.add(btnEnregistrerPdf);
        btnEnregistrerPdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                cmdEnregistrerPdf();
            }
        });

        lblStatut.setBounds(10, 440, 380, 20);
        contentPane.add(lblStatut);
    }

    public void cmdGenerer() {
        controle.demandeGenererQRCode(txtContenu.getText());
    }

    public void cmdEnregistrerPdf() {
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new File("qrcode.pdf"));
        int resultat = chooser.showSaveDialog(this);
        if (resultat == JFileChooser.APPROVE_OPTION) {
            String chemin = chooser.getSelectedFile().getAbsolutePath();
            if (!chemin.toLowerCase().endsWith(".pdf")) {
                chemin += ".pdf";
            }
            controle.demandeEnregistrerPdf(txtContenu.getText(), chemin);
        }
    }

    public void afficherApercu(BufferedImage image) {
        lblApercu.setIcon(new ImageIcon(image));
        lblApercu.setText(null);
    }

    public void afficherStatut(String message) {
        lblStatut.setText(message);
    }
}