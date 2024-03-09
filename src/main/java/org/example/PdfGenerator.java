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
                "Adrian Gabriel Silva da Rosa",
                "Adriano Silva da Rosa",
                "Ana Luiza Passos de Godis",
                "Andressa Silva Bueno",
                "Bento Tavares Fernandes",
                "Daura Fagundes da Silva dos Santos",
                "Débora de Cassia Orquiz",
                "Dionatan Douglas da Silva",
                "Emeli Luísa Rosanelli Alves",
                "Gisele Silveira da Silva",
                "Higor Mateus Silva da Silva",
                "Jéssica Rodrigues Torales",
                "Jorge Bruno Luz do Nascimento",
                "Keila Machado Abadi",
                "Kellyta da Silva Bicca Alves",
                "Larissa de Oliveira Carati",
                "Leandro Vieira dos Santos",
                "Leandro Vilela Guimaraes",
                "Lis Alves Zucoloto Machado",
                "Luiz Filipe Torbes da Silva",
                "Mara Alice Diniz",
                "Paulo Isaías Maciel",
                "Rafaela Ferreira Dilsson",
                "Renata da Silva",
                "Rodrigo Rodrigues Soares",
                "Rosana Mara Villanova Bataglin",
                "Silvia Uiara Mota da Silva França",
                "Sonia Maria Andrade Machado",
                "Tailon Robert Taquátia da Silva",
                "Weslley Mikael Rodrigues Nunes",
                "Yasmin Juliane Almeida Vieira",
                "Yngrid Simas Ferreira"
        };
        String classCode = "171";
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
                document.add(new Paragraph("2024/I").setFontSize(14));
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
                    document.add(new Paragraph("2024/I").setFontSize(14));
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
