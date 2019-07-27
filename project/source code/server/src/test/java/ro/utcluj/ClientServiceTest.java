package ro.utcluj;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import ro.utcluj.api.ClientBaseDTO;
import ro.utcluj.api.RentalBaseDTO;
import ro.utcluj.mapper.CargoMapper;
import ro.utcluj.mapper.ClientMapper;
import ro.utcluj.mapper.DockMapper;
import ro.utcluj.mapper.RentalMapper;
import ro.utcluj.model.Cargo;
import ro.utcluj.model.Client;
import ro.utcluj.model.Dock;
import ro.utcluj.model.Rental;
import ro.utcluj.repositories.*;
import ro.utcluj.service.ClientService;
import ro.utcluj.service.ContextGetter;

import java.sql.Date;
import java.util.*;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ClientServiceTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule ();

    @Mock
    private ClientRepository repsitory;
    @Mock
    private RentalRepository rentalRepository;
    @Mock
    private DockRepository dockRepository;
    @Mock
    private CargoRepository cargoRepository;
    @Mock
    private SoldRepository soldRepository;
    @Mock
    private SaleNoRepository saleRepo;
    @Mock
    private ClientMapper clientMapper;
    @Mock
    private DockMapper dockMapper;
    @Mock
    private RentalMapper rentalMapper;
    @Mock
    private CargoMapper cargoMapper;
    @Mock
    private ContextGetter contextGetter;

    private ClientService clientService;
    private Client client;
    private Rental rental;
    private List<Cargo> cargos;
    private List<Dock> docks;

    @Before
    public void start(){
        clientService = new ClientService (contextGetter, saleRepo, soldRepository, cargoMapper, cargoRepository, rentalMapper, dockMapper, clientMapper, repsitory, dockRepository, rentalRepository);
        client = new Client ();
        client.setIdclient (2);
        client.setCurrency (1443255);

        rental = new Rental ();
        rental.setRentDate (new Date (2019, 6, 4));
        rental.setEndDate (new Date (2019, 8, 4));
        rental.setPrice (20);
        rental.setClient (client);
        List<Rental> rentals = new LinkedList<> ();
        rentals.add (rental);
        Cargo c = new Cargo ();
        c.setIdcargo (2);
        c.setClient (client);
        cargos = new LinkedList<> ();
        cargos.add (c);
        client.setCargoSet (new HashSet<> (cargos));
        client.setRentalSet (new HashSet<> (rentals));

        Dock doc = new Dock();
        doc.setDockid (2);
        docks = new LinkedList<> ();
        docks.add(doc);
    }

    @Test
    public void findAllTest(){
        when(repsitory.findAll ()).thenReturn (new LinkedList<> ());
        when(clientMapper.mapAll (anyList ())).thenReturn (new LinkedList ());
        clientService.findAll ();
        verify (repsitory, times (1)).findAll ();
        verify (clientMapper, times (1)).mapAll (anyList ());
    }


    @Test
    public void findDocksTest(){
        when(dockRepository.findAll ()).thenReturn (new LinkedList<> ());
        when(dockMapper.mapAll (anyList ())).thenReturn (new LinkedList ());
        clientService.findDocks ();
        verify (dockRepository, times (1)).findAll ();
        verify (dockMapper, times (1)).mapAll (anyList ());
    }


    @Test
    public void findClientTest(){
        when(repsitory.getOne (anyInt ())).thenReturn (new Client ());
        when(clientMapper.map (anyObject ())).thenReturn (new ClientBaseDTO ());
        clientService.findClient (anyInt ());
        verify (repsitory, times (1)).getOne (anyInt ());
        verify (clientMapper, times (1)).map(anyObject ());
    }


    @Test
    public void rentTest() throws Exception {
        when(repsitory.findByUsername (anyString ())).thenReturn (client);
        when(contextGetter.getUserNameFromContext ()).thenReturn (new String());
        when (repsitory.save(anyObject ())).thenReturn (client);
        when(saleRepo.findAll ()).thenReturn (new LinkedList<> ());
        when (rentalRepository.save(anyObject ())).thenReturn (rental);
        when(rentalMapper.map (anyObject ())).thenReturn (new RentalBaseDTO ());

        clientService.rent (new Date (2019, 2, 1),  new Date(2019, 3, 1), "hallo");

        verify(repsitory, times (1)).findByUsername (anyString ());
        verify (contextGetter, times (1)).getUserNameFromContext ();
        verify(repsitory, times (1)).save (anyObject ());
        verify(rentalRepository, times (1)).save (anyObject ());
        verify (rentalMapper, times (1)).map(anyObject ());
    }

    @Test
    public void findByDateTest() throws Exception {
        when(dockRepository.findAll ()).thenReturn (new LinkedList<> ());
        when(rentalRepository.findAll ()).thenReturn (new LinkedList<> ());
        when(repsitory.findByUsername (anyString ())).thenReturn (new Client ());
        when(dockRepository.findAll ()).thenReturn (new LinkedList<> ());

        clientService.findByDate (new Date (2019, 2, 1),  new Date(2019, 3, 1));

        verify(repsitory, times (1)).findByUsername (anyString ());
        verify (dockRepository, times (1)).findAll ();
        verify (rentalRepository, times (1)).findAll ();
        verify (dockMapper, times (1)).mapAll (anyList ());
    }

    @Test
    public void findCargosTest() {
        when (cargoRepository.findAll ()).thenReturn (new LinkedList<> ());
        when(repsitory.findByUsername (anyString ())).thenReturn (client);
        when(contextGetter.getUserNameFromContext ()).thenReturn (new String());
        when(cargoMapper.mapAll (anyList ())).thenReturn (new LinkedList ());

        clientService.findCargos ();

        verify (cargoRepository, times (1)).findAll ();
        verify(repsitory, times (1)).findByUsername (anyString ());
        verify (contextGetter, times (1)).getUserNameFromContext ();
        verify (cargoMapper, times (1)).mapAll (anyList ());
    }

    @Test
    public void deleteCargo() throws Exception {
        when (cargoRepository.findAll ()).thenReturn (cargos);
        when(repsitory.findByUsername (anyString ())).thenReturn (client);
        when(contextGetter.getUserNameFromContext ()).thenReturn (new String());

        clientService.deleteCargo (2);

        verify (cargoRepository, times (1)).findAll ();
        verify(repsitory, times (1)).findByUsername (anyString ());
        verify (contextGetter, times (1)).getUserNameFromContext ();
    }

    @Test
    public void buyDockTest() throws Exception {
        when (dockRepository.findAll ()).thenReturn (docks);
        when(repsitory.findByUsername (anyString ())).thenReturn (client);
        when(contextGetter.getUserNameFromContext ()).thenReturn (new String());
        when(rentalRepository.findAll ()).thenReturn (new LinkedList<> ());

        clientService.buyDock (2);

        verify (dockRepository, times (1)).findAll ();
        verify (rentalRepository, times (2)).findAll ();
        verify(repsitory, times (1)).findByUsername (anyString ());
        verify (contextGetter, times (1)).getUserNameFromContext ();
    }

    @Test
    public void findAvDocksTest(){
        when (dockRepository.findAll ()).thenReturn (docks);
        when(soldRepository.findAll ()).thenReturn (new LinkedList<> ());
        when(rentalRepository.findAll ()).thenReturn (new LinkedList<> ());
        when(dockMapper.mapAll(anyList ())).thenReturn (new LinkedList ());

        clientService.findAvailableDocks ();

        verify (dockRepository, times (1)).findAll ();
        verify(soldRepository, times (1)).findAll ();
        verify (rentalRepository, times (1)).findAll ();
        verify (dockMapper, times (1)).mapAll (anyList ());
    }

    @Test
    public void changeRetalPriceTest() throws Exception {
        when(repsitory.findByUsername (anyString ())).thenReturn (client);
        when(contextGetter.getUserNameFromContext ()).thenReturn (new String());

        clientService.changeRentalPrice (2);

        verify(repsitory, times (1)).findByUsername (anyString ());
        verify (contextGetter, times (1)).getUserNameFromContext ();
    }

    @Test
    public void activeRentalsTest(){
        when(repsitory.findByUsername (anyString ())).thenReturn (client);
        when(contextGetter.getUserNameFromContext ()).thenReturn (new String());
        when (rentalMapper.map(anyObject ())).thenReturn (new RentalBaseDTO ());

        clientService.activeRentals ();

        verify(repsitory, times (1)).findByUsername (anyString ());
        verify (contextGetter, times (1)).getUserNameFromContext ();
        verify (rentalMapper, times (1)).map (anyObject ());
    }

    @Test
    public void showHistoryTest(){
        when(repsitory.findByUsername (anyString ())).thenReturn (client);
        when(contextGetter.getUserNameFromContext ()).thenReturn (new String());
        when (rentalMapper.map(anyObject ())).thenReturn (new RentalBaseDTO ());
        clientService.showHistory ();

        verify(repsitory, times (1)).findByUsername (anyString ());
        verify (contextGetter, times (1)).getUserNameFromContext ();
        verify (rentalMapper, times (1)).map (anyObject ());

    }


}
