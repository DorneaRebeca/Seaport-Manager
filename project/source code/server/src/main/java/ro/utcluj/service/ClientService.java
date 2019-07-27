package ro.utcluj.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ro.utcluj.api.*;
import ro.utcluj.mapper.CargoMapper;
import ro.utcluj.mapper.ClientMapper;
import ro.utcluj.mapper.DockMapper;
import ro.utcluj.mapper.RentalMapper;
import ro.utcluj.model.*;
import ro.utcluj.repositories.*;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class ClientService implements ClientServiceInterface {


    private ClientRepository repsitory;
    private RentalRepository rentalRepository;
    private DockRepository dockRepository;
    private CargoRepository cargoRepository;
    private SoldRepository soldRepository;
    private SaleNoRepository saleRepo;


    private ClientMapper clientMapper;
    private DockMapper dockMapper;
    private RentalMapper rentalMapper;
    private CargoMapper cargoMapper;
    private ContextGetter contextGetter;


    @Autowired
    public ClientService(ContextGetter contextGetter, SaleNoRepository saleNoRepository, SoldRepository soldRepository, CargoMapper cargoMapper, CargoRepository cargoRepository, RentalMapper rentalMapper, DockMapper dockMapper, ClientMapper clientMapper, ClientRepository repsitory, DockRepository dockRepo, RentalRepository rentalRepository){
        this.repsitory = repsitory;
        this.dockRepository = dockRepo;
        this.rentalRepository = rentalRepository;
        this.soldRepository = soldRepository;
        this.clientMapper = clientMapper;
        this.dockMapper = dockMapper;
        this.rentalMapper = rentalMapper;
        this.cargoRepository = cargoRepository;
        this.cargoMapper = cargoMapper;
        this.saleRepo = saleNoRepository;
        this.contextGetter = contextGetter;

    }

    public List<ClientBaseDTO> findAll(){

        List<Client> clients = repsitory.findAll ();
       return clientMapper.mapAll (clients);
    }

    public List<DockBaseDTO> findDocks(){

        return dockMapper.mapAll (dockRepository.findAll ());


    }

    public ClientBaseDTO findClient(int id){
        return clientMapper.map(repsitory.getOne (id));
    }


    public RentalBaseDTO rent(Date starDate, Date endDate, String shipType) throws Exception {
        int dif;
        Rental r = new Rental ();
        r.setEndDate (endDate);
        r.setRentDate (starDate);
        Dock d;
        int delta = 0;
        List<DockBaseDTO> availableDocks = findAvailableDocks ();

        if(availableDocks.size () <= saleRepo.findAll ().get (0).getNumber ())
            delta = 1;
            Client c = repsitory.findByUsername (contextGetter.getUserNameFromContext ());
            r.setClient (c);
            if(endDate.toLocalDate ().getYear () ==  starDate.toLocalDate ().getYear () )
                dif = endDate.toLocalDate ().getMonthValue ()- starDate.toLocalDate ().getMonthValue ();
            else if(endDate.toLocalDate ().getYear () >  starDate.toLocalDate ().getYear () )
                dif = 12 - starDate.toLocalDate ().getMonthValue () + endDate.toLocalDate ().getMonthValue ();
            else throw new Exception ("Incorrect time, you Smurf!");

                if (dif > 0) {
                        r.setPrice ((int)(dif * 530 + delta*0.2*dif*530));
                        d = sizeGrater (45, starDate, endDate);

                } else throw new Exception ("Incorrect time, you Smurf!");

                if (d != null)
                    r.setDock (d);
                else throw new Exception ("There is no dock available for this ship at this date");
                if (r.getPrice () <=c.getCurrency ()) {
                    c.setCurrency (c.getCurrency () - r.getPrice ());
                    repsitory.save (c);
                    if(c.getRentalSet ()!=null && !c.getRentalSet ().isEmpty ()) {

                        Set<Rental> list = c.getRentalSet ();
                        list.add (r);
                        c.setRentalSet (list);

                    }else {
                        Set<Rental> newSet = new HashSet<> ();
                        newSet.add (r);
                        c.setRentalSet (newSet);
                    }
                     rentalRepository.save (r);
                } else throw new Exception ("You don't have enough money");

        return rentalMapper.map (r);
    }

    public List<DockBaseDTO> findByDate(Date rentDate, Date endDate) throws Exception {
        List<Dock> docks = dockRepository.findAll ();
        List<Rental> rentals = rentalRepository.findAll ();
        Client c = repsitory.findByUsername (contextGetter.getUserNameFromContext ());

        if(rentDate.getYear () > endDate.getYear ())
            throw new Exception ("Incorrect dates");
        if(rentDate.getYear () ==  endDate.getYear () && rentDate.getMonth () > endDate.getMonth ())
            throw new Exception ("Incorrect dates!");

        for (Rental r : rentals) {
            if (r.getRentDate ().toLocalDate ().getYear () == rentDate.getYear ()) {
                if (r.getRentDate ().toLocalDate ().getMonthValue () <= rentDate.getMonth () && r.getEnddate ().toLocalDate ().getMonthValue () >= rentDate.getMonth ())
                    docks.remove (r.getDock ());
            }
            if(r.getEndDate ().toLocalDate ().getYear () == endDate.getYear ())
            if (r.getRentdate ().toLocalDate ().getMonthValue () <= endDate.getMonth () && r.getEndDate ().toLocalDate ().getMonthValue () >= endDate.getMonth())
                docks.remove (r.getDock ());
            if(r.getEndDate ().toLocalDate ().getYear () == endDate.getYear () && r.getRentDate ().toLocalDate ().getYear () == rentDate.getYear ())
            if(r.getRentdate ().toLocalDate ().getMonthValue () > rentDate.getMonth () && r.getEndDate ().toLocalDate ().getMonthValue () > endDate.getMonth())
                docks.remove (r.getDock ());
        }
        return dockMapper.mapAll (docks);
    }

    private Dock sizeGrater(int size, Date rent, Date end) throws Exception {

        List<DockBaseDTO> docks =findByDate (rent, end);
        List<Dock> ds = dockMapper.mapAllBack (docks);
        for(Dock d : ds)
            if(d.getSize () >= size)
                return d;
        return null;
    }

    public List<CargoBaseDTO> findCargos(){
        List<Cargo> cargos = cargoRepository.findAll ();
        List<Cargo> clientsC = new LinkedList<> ();
        Client clnt = repsitory.findByUsername (contextGetter.getUserNameFromContext ());
        for(Cargo c : cargos){
            if(c.getClient ().getIdclient () == clnt.getIdclient ())
                clientsC.add (c);
        }
        return cargoMapper.mapAll (clientsC);
    }

    public String deleteCargo(int id) throws Exception {

        List<Cargo> cargos = cargoRepository.findAll ();
        Cargo removable = null;
        for(Cargo c : cargos){
            if(c.getIdcargo () == id )
                removable = c;
        }
        Client c = repsitory.findByUsername (contextGetter.getUserNameFromContext ());

        if(removable != null && removable.getClient ().getIdclient () == c.getIdclient ())
        {
            Set<Cargo> set = c.getCargoSet ();
            set.remove (removable);
            c.setCargoSet (set);
            cargoRepository.deleteById (removable.getIdcargo ());
        }

        else throw new Exception ("Wrong cargo id");

        return "Delete succesfully";
    }


    public String buyDock(int idDock) throws Exception {

        List<Dock> docks = dockRepository.findAll ();
        Client c = repsitory.findByUsername (contextGetter.getUserNameFromContext ());

        if(c.getCurrency () > 1000000){
            List<Sold> solded = soldRepository.findAll ();
                for (Sold d : solded) {
                    docks.remove (d.getDockSold ());
                }
            Dock buy = null;
            for(Dock d : docks) {
                if (d.getDockid () == idDock)
                    buy = d;
            }
            if(buy == null) throw new Exception ("Dock dosen't exist:))))...or is already sold");

            for(Rental r : rentalRepository.findAll ()){
                if(r.getDock ().getDockid () == buy.getDockid ()){
                    if(r.getEndDate ().toLocalDate ().getYear () == LocalDateTime.now ().getYear ()
                    && r.getEndDate ().toLocalDate ().getMonthValue () >= LocalDateTime.now ().getMonthValue ())
                        throw new Exception ("Dock is rented for this period of time. Try later :))");
                }
            }

            if(c.getCurrency () < buy.getPrice ())
                throw new Exception ("You don't have enough money for this dock");
            else
                c.setCurrency (c.getCurrency () - buy.getPrice ());
            Sold s = new Sold();
            s.setDockSold (buy);
            s.setBeneficiar (c);
            solded.add (s);
            c.setBoughtDocks (solded);
            repsitory.save (c);

            for(Rental r : rentalRepository.findAll ()){
                if(r.getDock ().getDockid () == buy.getDockid ())
                    rentalRepository.delete (r);
            }
        }else throw new Exception ("You are not a VIP client dear!");
        return "Done!";
    }


    public List<DockBaseDTO> findAvailableDocks(){
        List<Dock> all = dockRepository.findAll ();
        List<Sold> solded = soldRepository.findAll ();
        if(solded != null && !solded.isEmpty ()) {
            for (Sold d : solded) {
                all.remove (d.getDockSold ());
            }
        }

        for(Rental r : rentalRepository.findAll ()){
            if(all.contains (r.getDock ()))
                all.remove (r.getDock ());
        }
        return dockMapper.mapAll (all);
    }

    public void changeRentalPrice(int rental) throws Exception {
        Client c = repsitory.findByUsername (contextGetter.getUserNameFromContext ());

        if(c.getRentalSet ()!=null && !c.getRentalSet ().isEmpty ()) {
            int ok = 0;
            Set<Rental> list  = c.getRentalSet ();
            for(Rental r : list ){
                if(r.getIdrental () == rental){
                    r.setPrice ((int)(r.getPrice () - 0.1*r.getPrice ()));
                    ok = 1;
                }
            }
            if(ok == 0) throw new Exception ("The rental id you introduced is incorrect!!");

            c.setRentalSet (list);
            repsitory.save (c);

        }
    }

    public List<RentalBaseDTO> activeRentals(){
        Client c = repsitory.findByUsername (contextGetter.getUserNameFromContext ());
        Set<Rental> all = c.getRentalSet ();
        List<RentalBaseDTO> rez = new LinkedList<> ();
        if(all!=null && !all.isEmpty ()){
            for (Rental r : all) {
                if (r.getEndDate ().toLocalDate ().getYear () > LocalDateTime.now ().getYear ())
                    rez.add(rentalMapper.map(r));
                if (r.getEndDate ().toLocalDate ().getYear () == LocalDateTime.now ().getYear () &&
                        r.getEndDate ().toLocalDate ().getMonthValue () > LocalDateTime.now ().getMonthValue ())
                    rez.add(rentalMapper.map(r));

                if (r.getEndDate ().toLocalDate ().getYear () == LocalDateTime.now ().getYear () &&
                        r.getEndDate ().toLocalDate ().getMonthValue () == LocalDateTime.now ().getMonthValue ()
                        && r.getEndDate ().toLocalDate ().getDayOfMonth () > LocalDateTime.now ().getDayOfMonth ())
                    rez.add(rentalMapper.map(r));
            }
        }
        return rez;
    }

    public List<RentalBaseDTO> showHistory(){
        List<RentalBaseDTO> rez = new LinkedList<> ();
        Client c = repsitory.findByUsername (contextGetter.getUserNameFromContext ());
        for(Rental r :c.getRentalSet ()){
            rez.add(rentalMapper.map(r));
        }
        return rez;
    }





}
