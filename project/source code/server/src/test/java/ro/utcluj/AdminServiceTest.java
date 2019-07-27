package ro.utcluj;

import ch.qos.logback.core.util.COWArrayList;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import ro.utcluj.api.CargoBaseDTO;
import ro.utcluj.api.ClientBaseDTO;
import ro.utcluj.mapper.CargoMapper;
import ro.utcluj.mapper.ClientMapper;
import ro.utcluj.model.Cargo;
import ro.utcluj.model.Client;
import ro.utcluj.model.Salenumber;
import ro.utcluj.report.PDFReport;
import ro.utcluj.report.ReportFactory;
import ro.utcluj.repositories.CargoRepository;
import ro.utcluj.repositories.ClientRepository;
import ro.utcluj.repositories.RentalRepository;
import ro.utcluj.repositories.SaleNoRepository;
import ro.utcluj.service.AdminService;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;


import static org.mockito.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AdminServiceTest {

    @Mock
    private ClientRepository repo;
    @Mock
    private CargoRepository cargoRepo;
    @Mock
    private RentalRepository rentalRepo;
    @Mock
    private SaleNoRepository saleRepo;
    @Mock
    private ReportFactory reportFactory;
    @Mock
    private ClientMapper clientMapper;
    @Mock
    private CargoMapper cargoMapper;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule ();

    private AdminService adminService;
    private Client client;
    private List<Client> c;
    private List<Cargo>  cargos;

    @Before
    public void start(){
        adminService = new AdminService (saleRepo, cargoMapper, clientMapper, reportFactory, repo, cargoRepo, rentalRepo);
        client = new Client ();
        client.setUsername ("ma");
        client.setPassword ("pass");
        client.setClienttype ("client");
        c = new LinkedList<> ();
        c.add (client);
        cargos = new LinkedList<> ();
        Cargo cr = new Cargo ();
        cr.setPrice (1233);
        cr.setHour (13);
        cr.setDate (new Date (2019, 12,3));
        cr.setIdcargo (2);
        cargos.add(cr);

    }


    @Test
    public void findAllTest(){
        when (clientMapper.mapAll (anyObject ())).thenReturn (new LinkedList<> ());
        when (repo.findAll ()).thenReturn (c);
        adminService.findAll ();
        verify (clientMapper, times (1)).mapAll(anyList ());
        verify (repo, times (1)).findAll ();



    }

    @Test
    public void findCargosTest()
    {
        when(cargoMapper.mapAll (anyList ())).thenReturn (new LinkedList<CargoBaseDTO> ());
        when (cargoRepo.findAll ()).thenReturn (new LinkedList<> ());
        adminService.findCargos ();
        verify (cargoMapper, times (1)).mapAll(anyList ());
        verify (cargoRepo, times (1)).findAll ();
    }


    @Test
    public void updateClientTest() throws Exception {
        when (clientMapper.mapBackward (anyObject ())).thenReturn (client);
        when (repo.findAll ()).thenReturn (c);
        when (repo.save (anyObject ())).thenReturn (new Client ());
        adminService.updateClient (anyObject ());
        verify (clientMapper, times (1)).mapBackward (anyObject ());
        verify (repo, times (1)).findAll ();
        verify (repo, times (1)).save (anyObject ());
    }


    @Test
    public void deleteClientTest() throws Exception {
        when (clientMapper.mapBackward (anyObject ())).thenReturn (client);
        when (repo.findAll ()).thenReturn (c);
        adminService.deleteClient (anyObject ());
        verify (clientMapper, times (1)).mapBackward (anyObject ());
        verify (repo, times (1)).findAll ();
    }

    //WRONG
    @Test
    public void findByUsernameTest() throws Exception {
        when (repo.findAll ()).thenReturn (c);
        when (clientMapper.map (anyObject ())).thenReturn (new ClientBaseDTO ());
        adminService.findByUsername ("ma");
        verify (repo, times (1)).findAll ();
        verify (clientMapper, times (1)).map (anyObject ());
    }

    //WRONG
    @Test
    public void generateReportTest(){
        when (cargoRepo.findAll ()).thenReturn (cargos);
        when(reportFactory.createReport (anyString ())).thenReturn (new PDFReport ());
        adminService.generateRaport ("PDF");
        verify (cargoRepo, times (1)).findAll ();
        verify (reportFactory, times (1)).createReport (anyString ());
    }

    @Test
    public void makeScheduleTest(){
        when (cargoRepo.findAll ()).thenReturn (new LinkedList<> ());
        when (cargoMapper.mapAll (anyList ())).thenReturn (new LinkedList ());
        adminService.makeSchedule ();
        verify (cargoRepo, times (1)).findAll ();
        verify (cargoMapper, times (1)).mapAll (anyObject ());

    }


    @Test
    public void modifyScheduleTest() throws Exception {
        when (cargoMapper.mapAllBack (anyList ())).thenReturn (cargos);
        when(cargoRepo.save(anyObject ())).thenReturn (new Cargo ());
        when(cargoMapper.map(anyObject ())).thenReturn (new CargoBaseDTO ());

        adminService.modifyCargoSchedule (2, 323, new Date (2019, 3, 2), 13);

        verify (cargoMapper, times (1)).mapAllBack (anyObject ());
        verify (cargoRepo, times (1)).save (anyObject ());
        verify (cargoMapper, times (1)).map(anyObject ());
    }


    @Test
    public void setSaleNoTest(){
        when (saleRepo.findAll ()).thenReturn (new LinkedList<> ());
        when (cargoRepo.save (anyObject ())).thenReturn (new Salenumber ());

        adminService.setSaleNumber (1);
        verify (saleRepo, times (1)).findAll ();
        verify (saleRepo, times (1)).save(anyObject ());



    }
}
