package org.example;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.time.YearMonth;
import java.util.List;

public class PdfGenerator {

    private static final Color BRAND_BLUE = new DeviceRgb(30, 59, 95);
    private static final Color SOFT_BLUE = new DeviceRgb(236, 243, 251);
    private static final Color CARD_BORDER = new DeviceRgb(198, 212, 228);
    private static final Color TEXT_DARK = new DeviceRgb(34, 34, 34);

    private static final String DEST = "lista_de_alunos_turma.pdf";
    private static final String LOGO_PATH = "logo_vettorello.PNG";
    private static final int CARDS_PER_TERM = 2;
    private static final String[] WEEK_DAYS = {"S", "T", "Q", "Q", "S", "S", "D"};

    private record TermConfig(String label, int year, int[] months) {
    }

    public static void main(String[] args) {
        List<TermConfig> terms = List.of(
                new TermConfig("2026/I", 2026, new int[]{3, 4, 5, 6, 7}),
                new TermConfig("2026/II", 2026, new int[]{8, 9, 10, 11, 12})
        );

        String student = "";
        String classCode = "";

        try {
            PdfWriter writer = new PdfWriter(DEST);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf, PageSize.A4);
            document.setMargins(20, 20, 20, 20);

            ImageData logoData = ImageDataFactory.create(LOGO_PATH);

            for (int i = 0; i < terms.size(); i++) {
                if (i > 0) {
                    document.add(new AreaBreak());
                }

                TermConfig term = terms.get(i);
                for (int cardIndex = 0; cardIndex < CARDS_PER_TERM; cardIndex++) {
                    addStudentCard(document, logoData, student, classCode, term);
                }
            }

            document.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void addStudentCard(
            Document document,
            ImageData logoData,
            String student,
            String classCode,
            TermConfig term
    ) {
        Table card = new Table(UnitValue.createPercentArray(new float[]{1f})).useAllAvailableWidth();
        card.setBorder(new SolidBorder(CARD_BORDER, 1f));
        card.setMarginBottom(16);
        card.setKeepTogether(true);

        card.addCell(buildHeaderCell(logoData, term));
        card.addCell(buildInfoCell(student, classCode));
        card.addCell(buildMonthlyControlCell(term));
        card.addCell(buildSignatureCell());

        document.add(card);
    }

    private static Cell buildHeaderCell(ImageData logoData, TermConfig term) {
        Table header = new Table(UnitValue.createPercentArray(new float[]{72f, 28f})).useAllAvailableWidth();

        Paragraph title = new Paragraph("Cantina da Jaci")
                .setFontColor(ColorConstants.WHITE)
                .setBold()
                .setFontSize(16)
                .setMargin(0);

        Paragraph subtitle = new Paragraph("Período " + term.label())
                .setFontColor(ColorConstants.WHITE)
                .setFontSize(10)
                .setMarginTop(3)
                .setMarginBottom(0);

        Cell leftCell = new Cell()
                .add(title)
                .add(subtitle)
                .setBorder(Border.NO_BORDER)
                .setPadding(0)
                .setVerticalAlignment(VerticalAlignment.MIDDLE);

        Image logo = new Image(logoData)
                .setHorizontalAlignment(HorizontalAlignment.RIGHT)
                .setMaxHeight(36);

        Cell rightCell = new Cell()
                .add(logo)
                .setBorder(Border.NO_BORDER)
                .setPadding(0)
                .setVerticalAlignment(VerticalAlignment.MIDDLE);

        header.addCell(leftCell);
        header.addCell(rightCell);

        return new Cell()
                .add(header)
                .setBorder(Border.NO_BORDER)
                .setBackgroundColor(BRAND_BLUE)
                .setPaddingTop(10)
                .setPaddingBottom(10)
                .setPaddingLeft(14)
                .setPaddingRight(14);
    }

    private static Cell buildInfoCell(String student, String classCode) {
        Table info = new Table(UnitValue.createPercentArray(new float[]{18f, 82f})).useAllAvailableWidth();
        info.setBackgroundColor(SOFT_BLUE);

        addInfoRow(info, "Estudante", student, true);
        addInfoRow(info, "Turma", classCode, false);

        return new Cell()
                .add(info)
                .setBorder(Border.NO_BORDER)
                .setPaddingTop(12)
                .setPaddingBottom(10)
                .setPaddingLeft(14)
                .setPaddingRight(14);
    }

    private static void addInfoRow(Table info, String label, String value, boolean highlightValue) {
        boolean hasValue = value != null && !value.isBlank();

        Cell labelCell = new Cell()
                .add(new Paragraph(label)
                        .setBold()
                        .setFontColor(BRAND_BLUE)
                        .setFontSize(9)
                        .setMargin(0))
                .setBorder(Border.NO_BORDER)
                .setPadding(0)
                .setPaddingBottom(6);

        Paragraph valueParagraph = new Paragraph(hasValue ? value : " ")
                .setFontColor(TEXT_DARK)
                .setFontSize(highlightValue ? 13 : 11)
                .setMargin(0);

        if (highlightValue && hasValue) {
            valueParagraph.setBold();
        }

        Cell valueCell = new Cell()
                .add(valueParagraph)
                .setBorder(Border.NO_BORDER)
                .setPadding(0)
                .setPaddingBottom(4);

        if (!hasValue) {
            valueParagraph.setMinHeight(highlightValue ? 16 : 14);
            valueCell.setBorderBottom(new SolidBorder(BRAND_BLUE, 0.8f));
        }

        info.addCell(labelCell);
        info.addCell(valueCell);
    }

    private static Cell buildMonthlyControlCell(TermConfig term) {
        float[] monthColumns = new float[term.months().length];
        for (int i = 0; i < monthColumns.length; i++) {
            monthColumns[i] = 1f;
        }

        Table calendarGrid = new Table(UnitValue.createPercentArray(monthColumns))
                .useAllAvailableWidth()
                .setFixedLayout();

        for (int month : term.months()) {
            Cell monthCell = new Cell()
                    .setBorder(new SolidBorder(CARD_BORDER, 1f))
                    .setPadding(2);

            Paragraph monthLabel = new Paragraph(monthToLabel(month) + "/" + term.year())
                    .setFontSize(9)
                    .setBold()
                    .setFontColor(BRAND_BLUE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginTop(0)
                    .setMarginBottom(2);

            monthCell.add(monthLabel);
            monthCell.add(buildMonthTable(term.year(), month));

            calendarGrid.addCell(monthCell);
        }

        return new Cell()
                .add(calendarGrid)
                .setBorder(Border.NO_BORDER)
                .setPaddingTop(2)
                .setPaddingBottom(8)
                .setPaddingLeft(8)
                .setPaddingRight(8);
    }

    private static Table buildMonthTable(int year, int month) {
        Table table = new Table(UnitValue.createPercentArray(7))
                .useAllAvailableWidth()
                .setFixedLayout();

        for (String weekDay : WEEK_DAYS) {
            Cell dayHeader = new Cell()
                    .add(new Paragraph(weekDay)
                            .setTextAlignment(TextAlignment.CENTER)
                            .setFontSize(6)
                            .setBold()
                            .setMargin(0)
                            .setFontColor(BRAND_BLUE))
                    .setPadding(0)
                    .setBackgroundColor(SOFT_BLUE)
                    .setBorder(new SolidBorder(CARD_BORDER, 0.5f));

            table.addCell(dayHeader);
        }

        YearMonth yearMonth = YearMonth.of(year, month);
        int firstDayOffset = yearMonth.atDay(1).getDayOfWeek().getValue() - 1; // Monday starts at 0.

        for (int i = 0; i < firstDayOffset; i++) {
            table.addCell(buildDayCell(""));
        }

        for (int day = 1; day <= yearMonth.lengthOfMonth(); day++) {
            table.addCell(buildDayCell(String.valueOf(day)));
        }

        int usedCells = firstDayOffset + yearMonth.lengthOfMonth();
        int trailingCells = (7 - (usedCells % 7)) % 7;
        for (int i = 0; i < trailingCells; i++) {
            table.addCell(buildDayCell(""));
        }

        return table;
    }

    private static Cell buildDayCell(String dayValue) {
        return new Cell()
                .add(new Paragraph(dayValue)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFontSize(6)
                        .setMargin(0)
                        .setFontColor(TEXT_DARK))
                .setPadding(0)
                .setBorder(new SolidBorder(CARD_BORDER, 0.5f));
    }

    private static Cell buildSignatureCell() {
        LineSeparator line = new LineSeparator(new SolidLine(1f));
        line.setMarginTop(0);
        line.setMarginBottom(0);

        return new Cell()
                .add(new Paragraph("Assinatura Coord. de Turno")
                        .setFontSize(9)
                        .setFontColor(TEXT_DARK)
                        .setMarginTop(0)
                        .setMarginBottom(4))
                .add(line)
                .setBorder(Border.NO_BORDER)
                .setPaddingTop(2)
                .setPaddingBottom(12)
                .setPaddingLeft(14)
                .setPaddingRight(14);
    }

    private static String monthToLabel(int month) {
        return switch (month) {
            case 1 -> "Janeiro";
            case 2 -> "Fevereiro";
            case 3 -> "Março";
            case 4 -> "Abril";
            case 5 -> "Maio";
            case 6 -> "Junho";
            case 7 -> "Julho";
            case 8 -> "Agosto";
            case 9 -> "Setembro";
            case 10 -> "Outubro";
            case 11 -> "Novembro";
            case 12 -> "Dezembro";
            default -> String.valueOf(month);
        };
    }
}