package ro.utcluj.api;


import java.sql.Date;
import java.util.List;

public interface ClientServiceInterface {

    RentalBaseDTO rent(Date rent, Date endDate, String type) throws Exception;
    ClientBaseDTO findClient(int id);
    List<DockBaseDTO> findByDate(Date rent, Date end) throws Exception;
    List<DockBaseDTO> findDocks();
    List<ClientBaseDTO> findAll();
    String deleteCargo(int id) throws Exception;
    List<CargoBaseDTO> findCargos();
    String buyDock(int id) throws Exception;
    List<DockBaseDTO> findAvailableDocks();
    public void changeRentalPrice(int rental) throws Exception;
    public List<RentalBaseDTO> showHistory();
    public List<RentalBaseDTO> activeRentals();

}
