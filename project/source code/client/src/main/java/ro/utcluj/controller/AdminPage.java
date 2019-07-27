package ro.utcluj.controller;



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


@Service
public class AdminPage  implements Initializable {


    private ClientBaseDTO client;

    private StringProcesorInterface stringProcessor;

    private AdminServiceInterface adminService;
    private LoginServiceInterface loginServiceInterface;
    public ApplicationContext applicationContext;


    @FXML
    public TextField email;
    @FXML
    public TextField name;
    @FXML
    public TextField username;

    @FXML
    public TextField password;
    @FXML
    public TextField idClient;
    @FXML
    public Label rezLabel;
    @FXML
    public ChoiceBox shipType;
    @FXML
    public TextField currency;

    @FXML
    public TextField modifyId;

    @FXML
    public TextField modifyDate;

    @FXML
    public TextField modifyHour;

    @FXML
    public TextField modifyClientId;


    @FXML
    public Label modifierWarning;


    @FXML
    public TextField saleNo;


    @FXML
    public TableView<CargoBaseDTO> scheduleTable;

    @FXML
    public ChoiceBox reportType;

    @FXML
    public Label warningLabel;


    @FXML
    public TableView<ClientBaseDTO> clientTable;
    public TableColumn nameColumn = new TableColumn("name");
    public TableColumn usernameColumn = new TableColumn("username");
    public TableColumn shipTypeColumn = new TableColumn("shipType");
    public  TableColumn passColumn = new TableColumn("password");
    public  TableColumn emailColumn = new TableColumn("email");
    public  TableColumn idClientColumn = new TableColumn("idclient");
    public  TableColumn currencyColumn = new TableColumn("currency");



    @FXML
    public TableView<CargoBaseDTO> cargoTable;
    public TableColumn idColumn = new TableColumn("idcargos");
    public TableColumn dateColumn = new TableColumn("size");
    public TableColumn hourColumn = new TableColumn("region");
    public  TableColumn priceColumn = new TableColumn("price");



    @FXML
    public TableView<CargoBaseDTO> schTable;
    public TableColumn idColumnSch = new TableColumn("idcargos");
    public TableColumn idClientColumnSch = new TableColumn("idClient");
    public TableColumn dateColumnSch = new TableColumn("size");
    public TableColumn hourColumnSch = new TableColumn("hour");
    public  TableColumn priceColumnSch = new TableColumn("price");


    @Autowired
    public AdminPage( ApplicationContext applicationContext){

        this.applicationContext = applicationContext;
        this.adminService = applicationContext.getBean (AdminServiceInterface.class);
        this.stringProcessor = applicationContext.getBean (StringProcesorInterface.class);
        this.notificationService = applicationContext.getBean (NotificationService.class);
        this.loginServiceInterface = applicationContext.getBean (LoginServiceInterface.class);
    }

    @FXML
    public void logOut(Event event){

        try {
            int idNeeded = client.getIdclient ();
            client = null;
            adminService = null;
            notificationService.sendMessageToServer ("logout "+ idNeeded);
            sendToAnotherPage (event,"/LoginPage.fxml", "Login Page");
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }

    @FXML
    public void delete(Event event){
        int id = 0;
        id = stringProcessor.toInt (idClient.getText ());

        if(id == 0){
            rezLabel.setText ("In order to delet an account we need an id :)");
        }else{
            try {
                ClientBaseDTO c = new ClientBaseDTO ();
                c.setIdclient (id);
                adminService.deleteClient (c);
                List<ClientBaseDTO> clients = adminService.findAll ();
                ObservableList<ClientBaseDTO> clientObservableList = FXCollections.observableArrayList();
                clientObservableList.addAll (clients);
                clientTable.setItems(clientObservableList);
                clientTable.refresh ();
            } catch (Exception e) {
                rezLabel.setText (e.getMessage ());
            }


        }

    }

    public NotificationService notificationService;

    @FXML
    public void update(Event event){
        int id;
        id = stringProcessor.toInt (idClient.getText ());
        String usrname = username.getText ();
        String n = name.getText ();
        String shipt = null;
        if( !shipType.getSelectionModel().isEmpty())
            shipt = shipType.getSelectionModel().getSelectedItem().toString();
        String em = email.getText ();
        String pass = password.getText ();

        if(id == 0){
            rezLabel.setText ("In order to update an account we need an id :)");
        }else{
            try {
                ClientBaseDTO c = new ClientBaseDTO ();
                c.setIdclient (id);
                c.setPassword (pass);
                c.setEmail (em);
                c.setName (n);
                c.setUsername (usrname);
                c.setShiptype (shipt);
                adminService.updateClient (c);
                List<ClientBaseDTO> clients = adminService.findAll ();
                ObservableList<ClientBaseDTO> clientObservableList = FXCollections.observableArrayList();
                clientObservableList.addAll (clients);
                clientTable.setItems(clientObservableList);
                clientTable.refresh ();


            } catch (Exception e) {
                rezLabel.setText (e.getMessage ());
            }


        }
    }

    @FXML
    public void addClient(Event event){
        String usrname = username.getText ();
        String n = name.getText ();
        String shipt = null;
        if( !shipType.getSelectionModel().isEmpty())
            shipt = shipType.getSelectionModel().getSelectedItem().toString();
        String em = email.getText ();
        String pass = password.getText ();
        int c = 0;
        c = stringProcessor.toInt (currency.getText ());
        if(c == 0 || usrname==null || n==null || shipt==null || em==null || pass==null)
            rezLabel.setText ("You did something wrong while inserting tha data!! Check it out");
        else {
            try {

                loginServiceInterface.addClient (n, usrname, c, pass, shipt, em);
                List<ClientBaseDTO> clients = adminService.findAll ();
                ObservableList<ClientBaseDTO> clientObservableList = FXCollections.observableArrayList();
                clientObservableList.addAll (clients);
                clientTable.setItems(clientObservableList);
                clientTable.refresh ();
            } catch (Exception e) {
                rezLabel.setText (e.getMessage ());
            }
        }



    }

    @FXML
    public void searchClient(Event event){
        String usrnm = username.getText ();

        ClientBaseDTO cl = null;
        try {
            cl = adminService.findByUsername (usrnm);
            rezLabel.setText ("Found client : "+cl.toString ());
        } catch (Exception e) {
            rezLabel.setText (e.getMessage ());
        }

    }

    @FXML
    public void generateReport(Event event){

        String rep = null;

        try {
            if( !reportType.getSelectionModel().isEmpty())
                rep = reportType.getSelectionModel().getSelectedItem().toString();
            else throw new Exception ("Choose a report type!");
            adminService.generateRaport (rep);

        } catch (Exception e) {
            warningLabel.setText (e.getMessage ());
            e.printStackTrace ();
        }
    }

    @FXML
    public void makeSchedule(Event event){
        List<CargoBaseDTO> cargos = adminService.makeSchedule ();
        ObservableList<CargoBaseDTO> cargoObservableList = FXCollections.observableArrayList();
        cargoObservableList.addAll (cargos);
        schTable.setItems(cargoObservableList);

    }

    @FXML
    public void modifySchedule(Event event){


        int modId = stringProcessor.toInt (modifyId.getText ());
        int price = stringProcessor.toInt (modifyClientId.getText ());
        int modHour = stringProcessor.toInt (modifyHour.getText ());

        try {
            Date modDate = null;
            if(!modifyDate.getText ().equals ("") && modifyDate.getText ()!=null)
                 modDate = Date.valueOf (modifyDate.getText ());
            if(modHour >= 24 || modHour < 0) throw new Exception ("Wrong hour you Martian!!");
            if(modId == 0) throw new Exception ("No cargo to be modified! Pick one :)");
            CargoBaseDTO rez = adminService.modifyCargoSchedule (modId, price, modDate, modHour);

            List<CargoBaseDTO> schedule = adminService.makeSchedule ();
            ObservableList<CargoBaseDTO> scheduleObservableList = FXCollections.observableArrayList();
            scheduleObservableList.addAll (schedule);
            scheduleTable.setItems(scheduleObservableList);
            scheduleTable.refresh ();
            notificationService.sendMessageToServer ("Your appointment for cargo service has been modified! New data : " + rez.toString ());


        } catch (Exception e) {
            modifierWarning.setText (e.getMessage ());
        }
    }

    @FXML
    public void setSaleNumber(Event event){
            int no = stringProcessor.toInt (saleNo.getText ());
             try {
                 if(no == 0) throw new Exception ("Fill the space required for this operation!");
                 adminService.setSaleNumber (no);
                 rezLabel.setText ("Number changed in the database");
            } catch (Exception e) {
                rezLabel.setText (e.getMessage ());
                e.printStackTrace ();
            }
    }





    private void sendToAnotherPage(Event event, String pageName, String pageTitle){
        Parent root = null;
        try {
            FXMLLoader fxmlLoader;
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            fxmlLoader.setLocation(getClass().getResource(pageName));
            fxmlLoader.setClassLoader(this.getClass().getClassLoader());
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
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (url.toString().contains("/adminPage.fxml")){

            this.client = LoginController.logged;

             ObservableList<String> choises = FXCollections.observableArrayList("yacht","liner", "industrial");
            ObservableList<String> reports = FXCollections.observableArrayList("PDF", "txt document");

            shipType.setItems(choises);
            reportType.setItems (reports);
            rezLabel.setText (this.client.toString ());

            usernameColumn.setCellValueFactory(new PropertyValueFactory<ClientBaseDTO,String> ("username"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<ClientBaseDTO,String>("name"));
            shipTypeColumn.setCellValueFactory(new PropertyValueFactory<ClientBaseDTO,String>("shiptype"));
            emailColumn.setCellValueFactory(new PropertyValueFactory<ClientBaseDTO, String>("email"));
            passColumn.setCellValueFactory(new PropertyValueFactory<ClientBaseDTO, String>("password"));
            currencyColumn.setCellValueFactory(new PropertyValueFactory<ClientBaseDTO, String>("currency"));
            idClientColumn.setCellValueFactory(new PropertyValueFactory<ClientBaseDTO, String>("idclient"));

            clientTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            clientTable.getColumns().addAll(idClientColumn, nameColumn, usernameColumn, emailColumn, passColumn, shipTypeColumn, currencyColumn);
            List<ClientBaseDTO> clients = adminService.findAll ();
            ObservableList<ClientBaseDTO> clientObservableList = FXCollections.observableArrayList();
            clientObservableList.addAll (clients);
            clientTable.setItems(clientObservableList);

            idColumn.setCellValueFactory(new PropertyValueFactory<CargoBaseDTO, Integer> ("idcargos"));
            dateColumn.setCellValueFactory(new PropertyValueFactory<CargoBaseDTO, Date>("date"));
            hourColumn.setCellValueFactory(new PropertyValueFactory<CargoBaseDTO,Integer>("hour"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<CargoBaseDTO, Integer>("price"));

            cargoTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            cargoTable.getColumns().addAll(idColumn, dateColumn, hourColumn, priceColumn);
            List<CargoBaseDTO> cargos = adminService.findCargos ();
            ObservableList<CargoBaseDTO> cargoObservableList = FXCollections.observableArrayList();
            cargoObservableList.addAll (cargos);
            cargoTable.setItems(cargoObservableList);

            idColumnSch.setCellValueFactory(new PropertyValueFactory<CargoBaseDTO, Integer> ("idcargos"));
            idClientColumnSch.setCellValueFactory(new PropertyValueFactory<CargoBaseDTO, Integer> ("clientId"));
            dateColumnSch.setCellValueFactory(new PropertyValueFactory<CargoBaseDTO, Date>("date"));
            hourColumnSch.setCellValueFactory(new PropertyValueFactory<CargoBaseDTO,Integer>("hour"));
            priceColumnSch.setCellValueFactory(new PropertyValueFactory<CargoBaseDTO, Integer>("price"));

            schTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            schTable.getColumns().addAll(idColumnSch,idClientColumnSch, dateColumnSch, hourColumnSch, priceColumnSch);


            scheduleTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            scheduleTable.getColumns().addAll(idColumnSch,idClientColumnSch, dateColumnSch, hourColumnSch, priceColumnSch);
            List<CargoBaseDTO> schedule = adminService.makeSchedule ();
            ObservableList<CargoBaseDTO> scheduleObservableList = FXCollections.observableArrayList();
            scheduleObservableList.addAll (schedule);
            scheduleTable.setItems(scheduleObservableList);

        }
    }
}
