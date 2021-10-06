package com.amelin.simplecrm.repos.print;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.stream.Stream;

public class Printer {
    public void print(String pathname) throws Exception {

        PDDocument document = PDDocument.load(new File(pathname));

        PrintService myPrintService = findPrintService("My Windows printer Name");

        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPageable(new PDFPageable(document));
        job.setPrintService(myPrintService);
        job.print();

    }

    private PrintService findPrintService(String printerName) {
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService printService : printServices) {
            if (printService.getName().trim().equals(printerName)) {
                return printService;
            }
        }
        return null;
    }

    public void print(){
        try {
            Printer printer = new Printer();
            printer.print("iTextHelloWorld.pdf");
        } catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

//    public void print(){
//        try(FileOutputStream stream = new FileOutputStream("iTextHelloWorld.pdf")){
//            Document document = new Document();
//            PdfWriter.getInstance(document, stream);
//
//            document.open();
//            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
////            Chunk chunk = new Chunk(content, font);
//
//            PdfPTable table = new PdfPTable(3);
//            addTableHeader(table);
//            addRows(table);
//            addCustomRows(table);
//
//            document.add(table);
//            document.close();
//        } catch (FileNotFoundException exception){
//            System.out.println(exception.getMessage());
//        } catch (IOException exception){
//            System.out.println(exception.getMessage());
//        } catch (DocumentException exception){
//            System.out.println(exception.getMessage());
//        } catch (URISyntaxException exception){
//            System.out.println(exception.getMessage());
//        }
//    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("column header 1", "column header 2", "column header 3")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table) {
        table.addCell("row 1, col 1");
        table.addCell("row 1, col 2");
        table.addCell("row 1, col 3");
    }

    private void addCustomRows(PdfPTable table)
            throws URISyntaxException, BadElementException, IOException {
//        Path path = Paths.get(ClassLoader.getSystemResource("Java_logo.png").toURI());
//        Image img = Image.getInstance(path.toAbsolutePath().toString());
//        img.scalePercent(10);
//
//        PdfPCell imageCell = new PdfPCell(img);
//        table.addCell(imageCell);

        PdfPCell horizontalAlignCell = new PdfPCell(new Phrase("row 2, col 2"));
        horizontalAlignCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(horizontalAlignCell);

        PdfPCell verticalAlignCell = new PdfPCell(new Phrase("row 2, col 3"));
        verticalAlignCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        table.addCell(verticalAlignCell);
    }
}
