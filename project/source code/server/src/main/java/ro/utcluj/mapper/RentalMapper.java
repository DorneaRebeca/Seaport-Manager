package ro.utcluj.mapper;

import org.springframework.stereotype.Component;
import ro.utcluj.api.RentalBaseDTO;
import ro.utcluj.model.Rental;

import java.util.LinkedList;
import java.util.List;

@Component
public class RentalMapper {


    public RentalBaseDTO map(Rental rental){
        RentalBaseDTO r = new RentalBaseDTO ();
        r.setIdrentals (rental.getIdrentals ());
        r.setEnddate (rental.getEndDate ());
        r.setRentdate (rental.getRentdate ());
        r.setPrice (rental.getPrice ());
        return r;
    }



    public List<RentalBaseDTO> mapAll(List<Rental> rentals){
        List<RentalBaseDTO> cls = new LinkedList<> ();
        for(Rental c : rentals)
            cls.add (map (c));
        return cls;
    }
}
