package ro.utcluj.api;

import java.sql.Date;
import java.util.List;

public interface AdminServiceInterface {

    void deleteClient(ClientBaseDTO c) throws Exception;
    void updateClient(ClientBaseDTO c) throws Exception;
    ClientBaseDTO findByUsername(String usrn) throws Exception;
    void generateRaport(String type);
    List<CargoBaseDTO> makeSchedule();
    List<ClientBaseDTO> findAll();
    List<CargoBaseDTO> findCargos();
    CargoBaseDTO modifyCargoSchedule(int id,int price,  Date date, int hour) throws Exception;
    public void setSaleNumber(int no);
}
