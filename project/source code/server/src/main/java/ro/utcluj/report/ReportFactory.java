package ro.utcluj.report;


import org.springframework.stereotype.Component;

@Component
public class ReportFactory {

    public Report createReport(String reportType){

        if(reportType.contains ("PDF"))
            return new PDFReport ();
        else return new TxtReport ();

    }


}
