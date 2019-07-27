package ro.utcluj.report;


import ro.utcluj.model.Cargo;

import java.util.List;

public interface Report {

    void generateReport(List<Cargo> cargos);


}
