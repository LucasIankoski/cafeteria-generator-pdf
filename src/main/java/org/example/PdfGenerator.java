package org.example;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.property.HorizontalAlignment;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;

public class PdfGenerator {

    public static void main(String[] args) {
        String dest = "lista_de_alunos_turma.pdf";
        String logoPath = "logo_vettorello.png"; // Substitua "path_to_logo.png" pelo caminho real para o seu logo
        String[] students = {
                "Alexsander Santos Pantaleão",
                "Alicia Pazim da Costa",
                "Anderson Riquelme Massena dos Santos",
                "Débora de Cassia Orquiz",
                "Gabrieli Skibinski de Freitas",
                "Giovanna Silveira da Silva",
                "Igor Farias Corrêa",
                "Isabella Camboim Gomes",
                "Jorge Leandro Saldanha Guimarães",
                "Julia Nascimento Costa",
                "Kauã Evandro Crisostomo da Rosa",
                "Lorrana de Castro Cubas",
                "Lucas Vigil Rodrigues",
                "Maria Eduarda Ferreira Goulart",
                "Maximiliano da Silva Nunes",
                "Nathaly Saraiva Rodrigues",
                "Pâmela Correia de Campos",
                "Pedro Henrique da Cunha",
                "Renata Simões Soares",
                "Vanessa Leticia Bianchi de Matos",
                "Vanessa Martins Bairros",
                "Yamira Rafaela Campos Decke",
                "Kathiely do Nascimento Severo",
                "Elen Alessandra Onofre"
        };
        String classCode = "EJ7AN";
        String[] months = {"03", "04", "05", "06", "07"};

        try {
            PdfWriter writer = new PdfWriter(dest);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf, PageSize.A4);
            document.setMargins(20, 20, 20, 20);

            ImageData logoData = ImageDataFactory.create(logoPath);
            Image logo = new Image(logoData).setWidth(100).setHorizontalAlignment(HorizontalAlignment.RIGHT);

            for (int i = 0; i < students.length; i += 2) {
                document.add(logo);

                document.add(new Paragraph("Cantina da Jaci").setFontSize(14));
                document.add(new Paragraph("2026/I").setFontSize(14));
                document.add(new Paragraph("Nome do estudante: " + students[i]).setFontSize(20));
                document.add(new Paragraph("Turma: " + classCode).setFontSize(20));
                document.add(new Paragraph("Assinatura Coord. de Turno:").setFontSize(20));

                Table table1 = new Table(months.length);
                for (String month : months) {
                    try {
                        String imagePath = "calendario_2024_" + month + ".png";
                        ImageData data = ImageDataFactory.create(imagePath);
                        Image img = new Image(data).setAutoScale(true);
                        table1.addCell(img);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }

                document.add(table1);
                document.add(new Paragraph("------------------------------------------------------------------------").setFontSize(20));

                if (i + 1 < students.length) {
                    document.add(new Paragraph("\n"));

                    document.add(logo);
                    document.add(new Paragraph("Cantina da Jaci").setFontSize(14));
                    document.add(new Paragraph("2026/I").setFontSize(14));
                    document.add(new Paragraph("Nome do estudante: " + students[i + 1]).setFontSize(20));
                    document.add(new Paragraph("Turma: " + classCode).setFontSize(20));
                    document.add(new Paragraph("Assinatura Coord. de Turno:").setFontSize(20));

                    Table table2 = new Table(months.length);
                    for (String month : months) {
                        try {
                            String imagePath = "calendario_2024_" + month + ".png";
                            ImageData data = ImageDataFactory.create(imagePath);
                            Image img = new Image(data).setAutoScale(true);
                            table2.addCell(img);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }

                    document.add(table2);

                    if (i + 2 < students.length) {
                        document.add(new AreaBreak());
                    }
                }
            }

            document.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}


