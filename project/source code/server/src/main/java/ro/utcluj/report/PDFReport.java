package ro.utcluj.report;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import ro.utcluj.model.Cargo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PDFReport implements Report {

    private static ByteArrayInputStream cargoReport(List<Cargo> cargos) {
        String path = "D:\\2019-30236-DorneaRebeca\\asignment3";

        Document document =new Document( PageSize.A4, 20, 20, 20, 20 );
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            Random r = new Random ();
            PdfWriter.getInstance(document, new FileOutputStream(path + "\\T" + r.nextInt (50)+ "pdfRep.pdf"));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        document.open();

        try {

            document.add(new Paragraph ("                           Cargo Report Service"));
            for(int i=0; i< 5; i++)
                document.add( Chunk.NEWLINE );

            for (Cargo c : cargos) {
                document.add(new Paragraph ("  Cargo no :\t"+c.getIdcargo () + "  Client : " + c.getClient().getName ()));
                document.add(Chunk.NEWLINE);
                document.add(new Paragraph ( "                      Date : " + c.getDate () + "   Hour : "+c.getHour () + "   Price paid : "+c.getPrice ()));
                document.add( Chunk.NEWLINE );
            }

            for(int i=0; i< 5; i++)
                document.add( Chunk.NEWLINE );
            document.add(new Paragraph ("\bTerms & Conditions:\n"+
                    "\t __The Service reserves the right to select the method in which to remit funds on your behalf to your Payee. These payment methods may include, but may not be limited to, an electronic payment\n"+
                    ", an electronic to check payment, or a laser draft payment._\n" +
                    "In case of errors or questions about your transactions, you should as soon as possible notify us via one of the following:\n" +
                    "\t1.Telephone us at 0756556722 during customer service hours;\n" +
                    "\t2.Contact us by using the application's e-messaging feature; and/or,\n" +
                    "\t3.Write us at:\n" +
                    "\t\tFlorin’s Warehouse,\n" +
                    "\t\t48, 21 Decembrie 1989\n" +
                    "\t\tzipCode : 400600\n"));
            for(int i=0; i< 5; i++)
                document.add( Chunk.NEWLINE );
            document.add(new Paragraph ("Signature : "));

            document.addAuthor("Dornea Rebeca");
            document.addCreationDate();
            document.addTitle("Cargo Service Month Report");
            document.close();


        } catch (DocumentException ex) {

            Logger.getLogger (PDFReport.class.getName ()).log (Level.SEVERE, null, ex);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }


    @Override
    public void generateReport(List<Cargo> cargos) {
        cargoReport (cargos);
    }
}
