package ro.utcluj.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.utcluj.api.AdminServiceInterface;
import ro.utcluj.api.CargoBaseDTO;
import ro.utcluj.api.ClientBaseDTO;
import ro.utcluj.mapper.CargoMapper;
import ro.utcluj.mapper.ClientMapper;
import ro.utcluj.model.Cargo;
import ro.utcluj.model.Client;
import ro.utcluj.model.Salenumber;
import ro.utcluj.report.ReportFactory;
import ro.utcluj.repositories.CargoRepository;
import ro.utcluj.repositories.ClientRepository;
import ro.utcluj.repositories.RentalRepository;
import ro.utcluj.repositories.SaleNoRepository;


import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@Transactional
@Service
public class AdminService implements AdminServiceInterface {

    private ClientRepository repo;
    private CargoRepository cargoRepo;
    private RentalRepository rentalRepo;
    private SaleNoRepository saleRepo;


    private ReportFactory reportFactory;

    private ClientMapper clientMapper;
    private CargoMapper cargoMapper;



    @Autowired
    public AdminService(SaleNoRepository saleNoRepository, CargoMapper cargoMapper, ClientMapper clientMapper, ReportFactory reportFactory, ClientRepository clientRepository, CargoRepository cargoRepository, RentalRepository rentalRepository) {
        this.cargoRepo = cargoRepository;
        this.rentalRepo = rentalRepository;
        this.repo = clientRepository;
        this.reportFactory = reportFactory;
        this.clientMapper = clientMapper;
        this.cargoMapper = cargoMapper;
        this.saleRepo = saleNoRepository;
    }

    public List<ClientBaseDTO> findAll(){


        return clientMapper.mapAll (repo.findAll ());
    }

    public List<CargoBaseDTO> findCargos(){
        return cargoMapper.mapAll (cargoRepo.findAll ());
    }

    public void updateClient(ClientBaseDTO clnt) throws Exception {
        Client cl = clientMapper.mapBackward (clnt);
        List<Client> clients = repo.findAll ();
        Client c = null;

        for (Client cln : clients)
            if (cln.getIdclient () == cl.getIdclient ())
                c = cln;
        if(c == null) throw new Exception ("Couldn't find the account!!");
        if(cl.getShiptype () == null || cl.getShiptype ().equals (""))
            cl.setShiptype (c.getShiptype ());
        if(cl.getClienttype () == null || cl.getClienttype ().equals (""))
            cl.setClienttype (c.getClienttype ());
        if (cl.getUsername () == null || cl.getUsername ().equals (""))
            cl.setUsername (c.getUsername ());
        if (cl.getCurrency () == 0)
            cl.setCurrency (c.getCurrency ());
        if (cl.getName () == null || cl.getName ().equals (""))
            cl.setName (c.getName ());
        if (cl.getPassword () == null || cl.getPassword ().equals (""))
            cl.setPassword (c.getPassword ());
        else{
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder ();
            cl.setPassword (passwordEncoder.encode (c.getPassword ()));
        }
        if (cl.getEmail () == null || cl.getEmail ().equals (""))
            cl.setEmail (c.getEmail ());
        repo.save (cl);

    }

    public void deleteClient(ClientBaseDTO clnt) throws Exception {
        Client cl = clientMapper.mapBackward (clnt);
        List<Client> clients = repo.findAll ();
        Client c = null;
        for (Client cln : clients)
            if (cln.getIdclient () == cl.getIdclient ())
                c = cln;
            if(c == null) throw new Exception ("Couldn't find the account!!");
        repo.delete (c);
        }


    public ClientBaseDTO findByUsername(String username) throws Exception {

            List<Client> clients = repo.findAll ();
            if(username.equals (""))
                throw new Exception ("Insert Username dear!");
            for (Client c : clients) {
                if (c.getUsername ().equals (username))
                    return clientMapper.map (c);
            }

        throw new Exception ("Couldn't find the account!!");
    }


    public void generateRaport(String type) {

        List<Cargo> cargos = cargoRepo.findAll ();

        LocalDateTime localDateTime = LocalDateTime.now ();
        List<Cargo> rez = new LinkedList<> ();
        for (Cargo cargo : cargos) {
            if (!cargos.isEmpty ()) {
                if (cargo.getDate ().toLocalDate ().getYear () == localDateTime.getYear ()) {
                    if (cargo.getDate ().toLocalDate ().getMonthValue () == localDateTime.getMonthValue () - 1)
                        rez.add (cargo);
                }
            }
        }

        reportFactory.createReport (type).generateReport (rez);

    }




    public List<CargoBaseDTO> makeSchedule() {

        List<Cargo> cargos = cargoRepo.findAll ();
        List<Cargo> rez = new LinkedList<> ();
        cargos.sort (new Comparator<Cargo> () {
            @Override
            public int compare(Cargo o1, Cargo o2) {
                return o1.getDate ().compareTo (o2.getDate ());
            }
        });
        LocalDateTime localDateTime = LocalDateTime.now ();

        for (Cargo c : cargos) {
            if (c.getDate ().toLocalDate ().getYear () == localDateTime.getYear ())
                if (c.getDate ().toLocalDate ().getMonthValue () == localDateTime.getMonthValue ())
                    if (c.getDate ().toLocalDate ().getDayOfMonth () - localDateTime.getDayOfMonth () <= 7 && c.getDate ().toLocalDate ().getDayOfMonth () - localDateTime.getDayOfMonth () >= 0)
                        rez.add (c);
        }

        return cargoMapper.mapAll (rez);
    }


    public CargoBaseDTO modifyCargoSchedule(int id, int price, Date date, int hour) throws Exception {

        List<CargoBaseDTO> crgs = makeSchedule ();
        List<Cargo> cargos = cargoMapper.mapAllBack (crgs);
        Cargo foundCargo = null;
        for(Cargo c : cargos){
            if(c.getIdcargo () == id)
                foundCargo = c;

        if(foundCargo == null) throw new Exception ("Cargo Service you entered couldn't be found!!");

            if(date != null) {
                if (date.getYear () == c.getDate ().toLocalDate ().getYear () &&
                        date.getMonth () == c.getDate ().toLocalDate ().getMonthValue () &&
                        date.getDay () == c.getDate ().toLocalDate ().getDayOfMonth () &&
                        hour == c.getHour ()) {
                    throw new Exception ("The date is already taken");
                }else foundCargo.setDate (date);
            }
        }
        if(hour != 0)
             foundCargo.setHour (hour);
        if(price != 0) {
            foundCargo.setPrice (price);

        }
        cargoRepo.save (foundCargo);
        return cargoMapper.map(foundCargo);
    }


    public void setSaleNumber(int no){
        List<Salenumber> numbers = saleRepo.findAll ();
        if(numbers != null && !numbers.isEmpty ()) {
            numbers.get (0).setNumber (no);
            saleRepo.save (numbers.get (0));
        }
        else {
            Salenumber nou = new Salenumber ();
            nou.setNumber (no);
            saleRepo.save(nou);

        }
    }



}