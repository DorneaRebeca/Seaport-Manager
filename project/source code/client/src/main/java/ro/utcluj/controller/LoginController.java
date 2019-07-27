package  ro.utcluj.controller;


import com.sun.org.apache.xalan.internal.xsltc.dom.NthIterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ro.utcluj.api.*;
import ro.utcluj.notification.NotificationService;

import java.io.IOException;

@Service
public class LoginController  {


    @FXML
    public Label warning;
    @FXML
    public TextField loginUser;
    @FXML
    public TextField loginPass;
    @FXML
    public ChoiceBox clientType;
    @FXML
    public TextField name;
    @FXML
    public TextField regUsername;
    @FXML
    public TextField email;
    @FXML
    public TextField currency;
    @FXML
    public TextField shipType;
    @FXML
    public TextField regPass;

    private StringProcesorInterface stringProcessor;

    private ClientServiceInterface clientService;
    private LoginServiceInterface loginServiceInterface;

    public NotificationService notificationService;

    @Autowired
    public static ClientBaseDTO logged;

    public ApplicationContext applicationContext;
    private AdminServiceInterface adminService;

    @Autowired
    public LoginController( ApplicationContext applicationContext){


        this.applicationContext = applicationContext;
        this.adminService = applicationContext.getBean (AdminServiceInterface.class);
        this.clientService = applicationContext.getBean (ClientServiceInterface.class);
        this.stringProcessor = applicationContext.getBean (StringProcesorInterface.class);
        this.notificationService = applicationContext.getBean (NotificationService.class);
        this.loginServiceInterface = applicationContext.getBean (LoginServiceInterface.class);

    }


    public LoginController( Label warning, TextField loginUser, TextField loginPass, ChoiceBox clientType, TextField name, TextField regUsername, TextField email, TextField currency, TextField shipType, TextField regPass, StringProcesorInterface stringProcessor, ClientServiceInterface clientService, NotificationService notificationService, ApplicationContext applicationContext, AdminServiceInterface adminService) {
        this.warning = warning;
        this.loginUser = loginUser;
        this.loginPass = loginPass;
        this.clientType = clientType;
        this.name = name;
        this.regUsername = regUsername;
        this.email = email;
        this.currency = currency;
        this.shipType = shipType;
        this.regPass = regPass;
        this.stringProcessor = stringProcessor;
        this.clientService = clientService;
        this.notificationService = notificationService;
        this.applicationContext = applicationContext;
        this.adminService = adminService;
    }

    private void sendToAnotherPage(Event event, String pageName, String pageTitle){

        try {
            FXMLLoader fxmlLoader;
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            fxmlLoader.setLocation(this.getClass().getResource(pageName));
            fxmlLoader.setClassLoader(this.getClass().getClassLoader());
            System.out.println (this.getClass().getResource(pageName));
            Parent root = fxmlLoader.load();

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


    @FXML
    public void handleLogin(Event event){
        String usernN = loginUser.getText ();
        String pass = loginPass.getText ();
        String type = null;
        if( !clientType.getSelectionModel().isEmpty())
            type = clientType.getSelectionModel().getSelectedItem().toString();

        if(usernN == null || pass== null || type == null)
            warning.setText ("You didn't complete all the spaces!! DO IT!!!");
        else if(type.equals ("admin")){
            try {
                Authentication token = new UsernamePasswordAuthenticationToken(usernN,pass);
                SecurityContextHolder.getContext().setAuthentication(token);
                logged = loginServiceInterface.login ();
                notificationService.connectToNotificationServer (logged.getUsername ());
                notificationService.sendMessageToServer ("login "+logged.getIdclient ());
                sendToAnotherPage (event,"/adminPage.fxml", "Admin Page");
            } catch (Exception e) {
                e.printStackTrace ();
                warning.setText (e.getMessage ());

            }
        }else{
            try {
                Authentication token = new UsernamePasswordAuthenticationToken(usernN,pass);
                SecurityContextHolder.getContext().setAuthentication(token);
                logged = loginServiceInterface.login ();
                notificationService.connectToNotificationServer (logged.getUsername ());
                notificationService.sendMessageToServer ("login "+logged.getIdclient ());
                sendToAnotherPage (event,"/clientPage.fxml", "User Page");
            } catch (Exception e) {
                warning.setText (e.getMessage ());
                e.printStackTrace ();
            }

        }
    }

    @FXML
    public void handleRegister(Event event){

        String user = regUsername.getText ();
        String na = name.getText ();
        String emailStr = email.getText ();
        int curren = stringProcessor.toInt (currency.getText ());
        String shiptype = shipType.getText ();
        String pass = regPass.getText ();
        if( user==null || na==null || emailStr==null || curren==0 || shiptype==null || pass==null )
            warning.setText ("You did something wrong while inserting tha data!! Check it out");
        else {
            try {

                logged = loginServiceInterface.addClient (na, user, curren, pass, shiptype, emailStr);
                notificationService.sendMessageToServer ("login "+logged.getIdclient ());
                warning.setText ("You've been registered! Login to experience our world :)");
            } catch (Exception e) {
                warning.setText (e.getMessage ());
                e.printStackTrace ();
            }
        }
    }

    @FXML
    public void initialize(){
         ObservableList<String> choises = FXCollections.observableArrayList("admin","client");

        clientType.setItems (choises);
        clientType.setValue ("mmm");

    }

    public void getOut(Event event){

    }






}
