# Générateur de QR Code — Java Swing / MVC

## 1. Présentation

Cette application permet à un utilisateur de saisir un texte ou un lien, de générer un QR code
correspondant, de le visualiser dans l'interface, puis de l'exporter dans un fichier PDF contenant
à la fois le texte d'origine et l'image du QR code.

## 2. Architecture (MVC)

Le projet suit le patron Modèle-Vue-Contrôleur :

| Couche | Package | Classes | Rôle |
|---|---|---|---|
| Modèle | `modele` | `GenerateurQRCode`, `GenerateurPDF` | Logique métier : génération de l'image du QR code (ZXing) et génération du fichier PDF (iText). Ne dépend d'aucun composant graphique. |
| Vue | `vue` | `FrmQRCode` | Interface Swing : saisie du texte, affichage de l'aperçu du QR code, sélection du fichier de sortie. Ne contient aucune logique de génération. |
| Contrôleur | `controleur` | `Controle` | Fait le lien entre la Vue et le Modèle, orchestre les appels, centralise la gestion des erreurs. |

Ce découpage permet par exemple de changer complètement l'interface graphique (une version web,
par exemple) sans toucher à la logique de génération de QR code ou de PDF, et inversement de changer
de bibliothèque de génération de PDF sans toucher à l'interface.

## 3. Bibliothèques utilisées

- **ZXing** (`com.google.zxing:core` et `com.google.zxing:javase`, v3.5.3) : génération de l'image du QR code à partir d'un texte.
- **iText** (`com.itextpdf:itextpdf`, v5.5.13.3) : génération du document PDF et insertion de l'image du QR code dedans.
- **JUnit 5** (v5.11.0) : présent dans les dépendances Maven pour de futurs tests unitaires.

Toutes les dépendances sont gérées par Maven (`pom.xml`), aucune installation manuelle de `.jar` n'est nécessaire.

## 4. Fonctionnement de l'application

1. L'utilisateur saisit un texte ou un lien dans le champ de saisie.
2. Au clic sur **"Générer QR Code"**, la Vue transmet le texte au Contrôleur (`demandeGenererQRCode`).
3. Le Contrôleur appelle `GenerateurQRCode.genererImage(...)`, qui encode le texte en `BitMatrix` puis
   le convertit en `BufferedImage`.
4. Le Contrôleur transmet cette image à la Vue (`afficherApercu`), qui l'affiche dans un `JLabel`.
5. Au clic sur **"Enregistrer en PDF"**, la Vue ouvre un sélecteur de fichier (`JFileChooser`), puis
   transmet le chemin choisi au Contrôleur (`demandeEnregistrerPdf`).
6. Le Contrôleur appelle `GenerateurPDF.genererPdfAvecQRCode(...)`, qui crée un document iText, y insère
   un paragraphe de texte puis l'image du QR code (convertie en PNG au passage), et sauvegarde le fichier.

## 5. Gestion des erreurs

- Un contenu vide, `null`, ou composé uniquement d'espaces est rejeté avant toute tentative de génération,
  via une `IllegalArgumentException` levée dans `GenerateurQRCode.genererImage`.
- Une tentative d'enregistrement en PDF sans avoir généré de QR code au préalable est bloquée côté
  Contrôleur, avec un message d'information à l'utilisateur plutôt qu'une erreur technique.
- Toute exception inattendue (`WriterException` de ZXing, `IOException`/`DocumentException` d'iText)
  est capturée dans le Contrôleur et traduite en message lisible affiché dans la Vue (`lblStatut`),
  plutôt que de faire planter l'application.

## 6. Comment lancer l'application

Avec Maven, depuis la racine du projet :
```bash
mvn compile exec:java -Dexec.mainClass="controleur.Controle"
```

Ou directement dans Eclipse : clic droit sur `Controle.java` → **Run As** → **Java Application**.

## 7. Limitations connues

- Aucun test unitaire automatisé n'a été mis en place sur ce projet à ce stade (les dépendances JUnit 5
  sont prêtes dans le `pom.xml` pour une évolution future).
- La taille du QR code généré est fixée à 300x300 pixels (constante `TAILLE_PAR_DEFAUT` dans
  `GenerateurQRCode`), non configurable depuis l'interface pour l'instant.
