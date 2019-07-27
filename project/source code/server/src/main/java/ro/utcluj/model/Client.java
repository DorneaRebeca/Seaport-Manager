package ro.utcluj.model;


import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Component
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private int idclient;

    private String name;
    private String shiptype;
    private String clienttype;
    private String email;
    private String username;
    private String password;
    private int currency;
    private int vip;


    @OneToMany(mappedBy = "beneficiar", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Sold> boughtDocks;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Cargo> cargoSet;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Rental> rentalSet;

    public Client(){}
    public Client(int id){
        this.idclient = id;
    }
    public Client(String username, String password, String type){
        this.username = username;
        this.password = password;
        this.clienttype = type;
    }

    public Client(String name, String username, String password, String shipType, String clientType, String email, int currency, boolean vip) {
        this.name = name;
        this.shiptype = shipType;
        this.clienttype = clientType;
        this.email = email;
        this.username = username;
        this.password = password;
        this.currency = currency;
        if(vip)
            this.vip = 1;
        else this.vip = 0;
    }

    public Client(Client client) {
        this.name = client.getName ();
        this.username = client.getUsername ();
        this.cargoSet = client.getCargoSet ();
        this.currency = client.getCurrency ();
        this.email = client.getEmail ();
        this.clienttype = client.getClienttype ();
        this.idclient = client.getIdclient ();
        this.shiptype = client.getShiptype ();
        this.password = client.getPassword ();
        this.vip = client.getVip ();
        this.boughtDocks = client.getBoughtDocks ();
        this.rentalSet = client.getRentalSet ();
    }

    public String getName() {
        return name;
    }

    public void setIdclient(int idstudent) {
        this.idclient = idstudent;
    }

    public int getIdclient() {
        return idclient;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setCargoSet(Set<Cargo> cargoSet) {
        this.cargoSet = cargoSet;
    }

    public void setRentalSet(Set<Rental> rentalSet) {
        this.rentalSet = rentalSet;
    }

    public Set<Cargo> getCargoSet() {
        return cargoSet;
    }

    public Set<Rental> getRentalSet() {
        return rentalSet;
    }

    public String getShiptype() {
        return shiptype;
    }

    public String getClienttype() {
        return clienttype;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setShiptype(String shiptype) {
        this.shiptype = shiptype;
    }

    public void setClienttype(String clienttype) {
        this.clienttype = clienttype;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    public int getVip() {
        return vip;
    }

    public List<Sold> getBoughtDocks() {
        return boughtDocks;
    }

    public void setBoughtDocks(List<Sold> boughtDocks) {
        this.boughtDocks = boughtDocks;
    }

    @Override
    public String toString() {
        return "Client{" +
                "idclient=" + idclient +
                ", name='" + name + '\'' +
                ", shiptype='" + shiptype + '\'' +
                ", clienttype='" + clienttype + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", currency= "+currency+
                '}';
    }
}
