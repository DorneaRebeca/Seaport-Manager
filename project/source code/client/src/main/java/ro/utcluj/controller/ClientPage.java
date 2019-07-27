package  ro.utcluj.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ro.utcluj.api.*;
import ro.utcluj.notification.NotificationService;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.WeakHashMap;

@Service
public class ClientPage implements Initializable {

    private  ClientBaseDTO client;


    private StringProcesorInterface stringProcessor;

    private ClientServiceInterface clientService;
    public ApplicationContext applicationContext;


    @FXML
    public TextField rentDate;

    @FXML
    public TextField endDate;
    @FXML
    public ChoiceBox shiptype;
    @FXML
    public Label clinfo;
    @FXML
    public Label warningLabel;
    @FXML
    public TableView<DockBaseDTO> dockTable;
    public TableColumn idColumn = new TableColumn("iddocks");
    public TableColumn sizeColumn = new TableColumn("size");
    public TableColumn regionColumn = new TableColumn("region");
    public  TableColumn priceColumn = new TableColumn("price");


    @FXML
    public Label rentalCreated;

    @FXML
    public Label warningVip;

    @FXML
    public TextField cargoId;

    @FXML
    public Label cargoWarning;

    @FXML
    public Pane vipServices;

    @FXML
    public TextField dockVip;

    @FXML
    public TableView<DockBaseDTO> availableDockTable;

    @FXML
    public Label warningRentals;

    @FXML
    public TextField rentalIdTF;

    @FXML
    public ChoiceBox unsatisfied;

    @FXML
    public TableView<RentalBaseDTO> rentalTable;
    public TableColumn idRentalColumn = new TableColumn("id");
    public TableColumn rentDateColumn = new TableColumn("rent Date");
    public TableColumn endDateColumn = new TableColumn("end Date");
    public  TableColumn priceRentalColumn = new TableColumn("price");

    @FXML
    public TableView<RentalBaseDTO> rentalHistory;


    @FXML
    public TableView<CargoBaseDTO> cargoTable;
    public TableColumn idCargoColumn = new TableColumn("idcargos");
    public TableColumn dateCargoColumn = new TableColumn("size");
    public TableColumn hourCargoColumn = new TableColumn("region");
    public  TableColumn priceCargoColumn = new TableColumn("price");


    public NotificationService notificationService;


    @Autowired
    public ClientPage( ApplicationContext applicationContext){

        this.applicationContext = applicationContext;
        this.clientService = applicationContext.getBean (ClientServiceInterface.class);
        this.stringProcessor = applicationContext.getBean (StringProcesorInterface.class);
        this.notificationService = applicationContext.getBean (NotificationService.class);
    }


    public ClientPage(ClientBaseDTO client, StringProcesorInterface stringProcessor, ClientServiceInterface clientService, ApplicationContext applicationContext, TextField rentDate, TextField endDate, ChoiceBox shiptype, Label clinfo, Label warningLabel, TableView<DockBaseDTO> dockTable, TableColumn idColumn, TableColumn sizeColumn, TableColumn regionColumn, TableColumn priceColumn, Label rentalCreated, Label warningVip, TextField cargoId, Label cargoWarning, Pane vipServices, TextField dockVip, TableView<DockBaseDTO> availableDockTable, TableView<CargoBaseDTO> cargoTable, TableColumn idCargoColumn, TableColumn dateCargoColumn, TableColumn hourCargoColumn, TableColumn priceCargoColumn, NotificationService notificationService) {
        this.client = client;
        this.stringProcessor = stringProcessor;
        this.clientService = clientService;
        this.applicationContext = applicationContext;
        this.rentDate = rentDate;
        this.endDate = endDate;
        this.shiptype = shiptype;
        this.clinfo = clinfo;
        this.warningLabel = warningLabel;
        this.dockTable = dockTable;
        this.idColumn = idColumn;
        this.sizeColumn = sizeColumn;
        this.regionColumn = regionColumn;
        this.priceColumn = priceColumn;
        this.rentalCreated = rentalCreated;
        this.warningVip = warningVip;
        this.cargoId = cargoId;
        this.cargoWarning = cargoWarning;
        this.vipServices = vipServices;
        this.dockVip = dockVip;
        this.availableDockTable = availableDockTable;
        this.cargoTable = cargoTable;
        this.idCargoColumn = idCargoColumn;
        this.dateCargoColumn = dateCargoColumn;
        this.hourCargoColumn = hourCargoColumn;
        this.priceCargoColumn = priceCargoColumn;
        this.notificationService = notificationService;
    }

    @FXML
    public void rentADock(Event event){
        String shipt = null;
        if(!shiptype.getSelectionModel().isEmpty())
            shipt = shiptype.getSelectionModel().getSelectedItem().toString();
        Date rent = Date.valueOf (rentDate.getText ());
        Date end = Date.valueOf (endDate.getText ());

        if(shipt==null || rent==null || end == null)
            warningLabel.setText ("You didn't fill all the spaces required to rent a dock man!!!");
        else{

            try{
                RentalBaseDTO r = clientService.rent (rent, end, shipt);
                client = clientService.findClient(client.getIdclient ());
                List<RentalBaseDTO> activeRentals = clientService.activeRentals ();
                ObservableList<RentalBaseDTO> rentalObservableList = FXCollections.observableArrayList();
                rentalObservableList.addAll (activeRentals);
                rentalTable.setItems(rentalObservableList);
                rentalTable.refresh ();
                warningLabel.setText ("New rental : "+ r.toString ());
            }catch (Exception e){
                warningLabel.setText (e.getMessage ());
            }
            }
    }

    @FXML
    public void search(Event event){
        Date rent = Date.valueOf (rentDate.getText ());
        Date end = Date.valueOf (endDate.getText ());

        if(rent==null || end == null)
            warningLabel.setText ("You didn't fill all the spaces required to rent a dock man!!!");
        else{
            try{
                List<DockBaseDTO> rez = clientService.findByDate (rent, end);
                ObservableList<DockBaseDTO> dockObservableList = FXCollections.observableArrayList();
                dockObservableList.addAll (rez);
                dockTable.setItems (dockObservableList);
            } catch (Exception e) {
                warningLabel.setText (e.getMessage ());
            }
        }
    }

    @FXML
    public void logOut(Event event){

        try {
            notificationService.sendMessageToServer ("logout "+client.getIdclient ());
            client = null;
            clientService = null;
            sendToAnotherPage (event,"/LoginPage.fxml", "Login Page");

        } catch (IOException e) {
            e.printStackTrace ();
        }

    }

    @FXML
    public void deleteCargo(Event event)  {
        int id = stringProcessor.toInt (cargoId.getText ());

        try {
            if(id == 0) throw new Exception ("We need a proper ID for this operation :) ");
            String s = clientService.deleteCargo (id);
            cargoWarning.setText (s);
            List<CargoBaseDTO> cargos = clientService.findCargos ();
            ObservableList<CargoBaseDTO> cargoObservableList = FXCollections.observableArrayList();
            cargoObservableList.addAll (cargos);
            cargoTable.setItems(cargoObservableList);
            cargoTable.refresh ();
        } catch (Exception e) {
            cargoWarning.setText (e.getMessage ());
        }
    }

    @FXML
    public void buyDock(Event event){
        int idVip = stringProcessor.toInt (dockVip.getText ());
       try {
           if(idVip == 0) throw new Exception ("Insert the id you grizzly!");
           String s = clientService.buyDock (idVip);
           warningVip.setText ("You just bought dock no " + idVip+"!!!");

           List<DockBaseDTO> availableDocks = clientService.findAvailableDocks();
           ObservableList<DockBaseDTO> avDockObservableList = FXCollections.observableArrayList();
           avDockObservableList.addAll (availableDocks);
           availableDockTable.setItems(avDockObservableList);
           availableDockTable.refresh ();
        } catch (Exception e) {
            warningVip.setText (e.getMessage ());
           e.printStackTrace ();
        }


    }


    @FXML
    public void handleComplaint(Event event){
            int idRent = stringProcessor.toInt (rentalIdTF.getText ());
            String why = null;
        if(!unsatisfied.getSelectionModel().isEmpty())
            why = unsatisfied.getSelectionModel().getSelectedItem().toString();

        try {
            if(idRent == 0 || why == null) throw new Exception ("You better fill all datas");
            clientService.changeRentalPrice (idRent);

            List<RentalBaseDTO> activeRentals = clientService.activeRentals ();
            ObservableList<RentalBaseDTO> rentalObservableList = FXCollections.observableArrayList();
            rentalObservableList.addAll (activeRentals);
            rentalTable.setItems(rentalObservableList);
            rentalTable.refresh ();
        } catch (Exception e) {
            warningRentals.setText (e.getMessage ());
            e.printStackTrace ();
        }

    }

    @FXML
    public void viewHistory(Event event){

        List<RentalBaseDTO> activeRentals = clientService.showHistory ();
        ObservableList<RentalBaseDTO> rentalObservableList = FXCollections.observableArrayList();
        rentalObservableList.addAll (activeRentals);
        rentalHistory.setItems(rentalObservableList);
        rentalHistory.refresh ();
    }



    private void sendToAnotherPage(Event event, String pageName, String pageTitle){
        Parent root = null;
        try {
            FXMLLoader fxmlLoader;
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            fxmlLoader.setLocation(this.getClass().getResource(pageName));
            root = fxmlLoader.load();

            Stage stage = new Stage();
            Scene scene = new Scene(root);

            (((Node)event.getSource())).getScene().getWindow().hide();
            stage.setTitle(pageTitle);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (location.toString().contains("/clientPage.fxml")){
            ObservableList<String> choises  = FXCollections.observableArrayList("yacht","liner", "industrial");
             ObservableList<String> unsatisfiedCh = FXCollections.observableArrayList("poor customer service", "Transferring From One agent to Another", "Hidden Information and Costs"
             , " Low Quality of Services", "long response time", "lack of contact information ");
             unsatisfied.setItems (unsatisfiedCh);
            shiptype.setItems (choises);

            this.client = LoginController.logged;
            if(this.client.getCurrency () < 100000){
                vipServices.setVisible (false);
            }

            clinfo.setText (client.toString ());

            idColumn.setCellValueFactory(new PropertyValueFactory<DockBaseDTO,Integer> ("iddocks"));
            sizeColumn.setCellValueFactory(new PropertyValueFactory<DockBaseDTO,Integer>("size"));
            regionColumn.setCellValueFactory(new PropertyValueFactory<DockBaseDTO,String>("region"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<DockBaseDTO, Integer>("price"));

            dockTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            dockTable.getColumns().addAll(idColumn, sizeColumn, regionColumn, priceColumn);
            List<DockBaseDTO> docks = clientService.findDocks ();
            ObservableList<DockBaseDTO> dockObservableList = FXCollections.observableArrayList();
            dockObservableList.addAll (docks);
            dockTable.setItems(dockObservableList);

            idCargoColumn.setCellValueFactory(new PropertyValueFactory<CargoBaseDTO, Integer> ("idcargos"));
            dateCargoColumn.setCellValueFactory(new PropertyValueFactory<CargoBaseDTO, Date>("date"));
            hourCargoColumn.setCellValueFactory(new PropertyValueFactory<CargoBaseDTO,Integer>("hour"));
            priceCargoColumn.setCellValueFactory(new PropertyValueFactory<CargoBaseDTO, Integer>("price"));

            cargoTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            cargoTable.getColumns().addAll(idCargoColumn, dateCargoColumn, hourCargoColumn, priceCargoColumn);
            List<CargoBaseDTO> cargos = clientService.findCargos ();
            ObservableList<CargoBaseDTO> cargoObservableList = FXCollections.observableArrayList();
            cargoObservableList.addAll (cargos);
            cargoTable.setItems(cargoObservableList);

            if(this.client.getCurrency () > 1000000){
                availableDockTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                availableDockTable.getColumns().addAll(idColumn, sizeColumn, regionColumn, priceColumn);
                List<DockBaseDTO> availableDocks = clientService.findAvailableDocks();
                ObservableList<DockBaseDTO> avDockObservableList = FXCollections.observableArrayList();
                avDockObservableList.addAll (availableDocks);
                availableDockTable.setItems(avDockObservableList);
            }

            idRentalColumn.setCellValueFactory(new PropertyValueFactory<RentalBaseDTO,Integer> ("idrentals"));
            rentDateColumn.setCellValueFactory(new PropertyValueFactory<RentalBaseDTO,Date>("rentdate"));
            endDateColumn.setCellValueFactory(new PropertyValueFactory<DockBaseDTO,Date>("enddate"));
            priceRentalColumn.setCellValueFactory(new PropertyValueFactory<DockBaseDTO, Integer>("price"));
            rentalTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            rentalTable.getColumns().addAll(idRentalColumn, rentDateColumn, endDateColumn, priceCargoColumn);

            List<RentalBaseDTO> activeRentals = clientService.activeRentals ();
            ObservableList<RentalBaseDTO> rentalObservableList = FXCollections.observableArrayList();
            rentalObservableList.addAll (activeRentals);
            rentalTable.setItems(rentalObservableList);


            rentalHistory.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            rentalHistory.getColumns().addAll(idRentalColumn, rentDateColumn, endDateColumn, priceCargoColumn);





        }

    }






}
