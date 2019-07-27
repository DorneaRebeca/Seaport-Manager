package ro.utcluj.report;

import ro.utcluj.model.Cargo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class TxtReport implements Report {


    private void getDoocument(List<Cargo> cargos){
        String path = "D:\\2019-30236-DorneaRebeca\\asignment3";

        try {
            Random r = new Random ();
            System.out.println ("Hello doc report ");
            File bill = new File(path+"\\T"+r.nextInt (50)+"report.doc");
            bill.createNewFile();

            FileWriter fr = new FileWriter(bill);
            BufferedWriter scrie = new BufferedWriter(fr);

            scrie.write("                           Cargo Report Service");
            for(int i=0; i< 5; i++)
                scrie.newLine();

            for(int i=0; i< 5; i++)
                scrie.newLine();

            for(Cargo c : cargos){
                scrie.write("  Cargo no :\t"+c.getIdcargo () + "  Client : " + c.getClient ().getName () );
                scrie.newLine();
                scrie.write( "                      Date : " + c.getDate () + "   Hour : "+c.getHour () + "   Price paid : "+c.getPrice ());
                scrie.newLine();
            }


            for(int i=0; i< 6; i++)
                scrie.newLine();
            scrie.write("\bTerms & Conditions:\n"+
                    "\t __The Service reserves the right to select the method in which to remit funds on your behalf to your Payee. These payment methods may include, but may not be limited to, an electronic payment\n"+
                    ", an electronic to check payment, or a laser draft payment._\n" +
                    "In case of errors or questions about your transactions, you should as soon as possible notify us via one of the following:\n" +
                    "\t1.Telephone us at 0756556722 during customer service hours;\n" +
                    "\t2.Contact us by using the application's e-messaging feature; and/or,\n" +
                    "\t3.Write us at:\n" +
                    "\t\tFlorin’s Warehouse,\n" +
                    "\t\t48, 21 Decembrie 1989\n" +
                    "\t\tzipCode : 400600\n");
            scrie.newLine();
            scrie.newLine();
            scrie.newLine();
            scrie.newLine();
            scrie.write("Signature : ");
            scrie.flush();
            scrie.close();
            System.out.println ("Done with this .doc report!");
        } catch (IOException e) {
            e.printStackTrace ();
        }


    }



    @Override
    public void generateReport(List<Cargo> cargos) {
        getDoocument (cargos);
    }
}
