package ro.utcluj.mapper;

import org.springframework.stereotype.Component;
import ro.utcluj.api.CargoBaseDTO;
import ro.utcluj.model.Cargo;
import ro.utcluj.model.Client;

import java.util.LinkedList;
import java.util.List;

@Component
public class CargoMapper {


    public CargoBaseDTO map(Cargo cargo){
        CargoBaseDTO c = new CargoBaseDTO ();
        c.setIdcargos (cargo.getIdcargo ());
        c.setDate (cargo.getDate ());
        c.setHour (cargo.getHour ());
        c.setPrice (cargo.getPrice ());
        c.setClientId (cargo.getClient ().getIdclient ());
        return c;
    }


    public List<CargoBaseDTO> mapAll(List<Cargo> cargo){
        List<CargoBaseDTO> cls = new LinkedList<> ();
        for(Cargo c : cargo)
            cls.add (map (c));
        return cls;
    }


    public List<Cargo> mapAllBack(List<CargoBaseDTO> cargos){
        List<Cargo> cls = new LinkedList<> ();
        for(CargoBaseDTO cargo : cargos) {
            Cargo c = new Cargo ();
            c.setIdcargo (cargo.getIdcargos ());
            c.setDate (cargo.getDate ());
            c.setHour (cargo.getHour ());
            c.setPrice (cargo.getPrice ());
            Client client = new Client ();
            client.setIdclient (cargo.getClientId ());
            c.setClient (client);
            cls.add (c);
        }
        return cls;
    }
}
