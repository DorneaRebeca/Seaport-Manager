package ro.utcluj.api;

import java.io.Serializable;

public class ClientBaseDTO implements Serializable {

    private int idclient;
    private String name;
    private String shiptype;
    private String clienttype;
    private String email;
    private String username;
    private String password;
    private int currency;


    public int getIdclient() {
        return idclient;
    }

    public String getName() {
        return name;
    }

    public String getShiptype() {
        return shiptype;
    }

    public String getClienttype() {
        return clienttype;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getCurrency() {
        return currency;
    }

    public void setIdclient(int idclient) {
        this.idclient = idclient;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShiptype(String shiptype) {
        this.shiptype = shiptype;
    }

    public void setClienttype(String clienttype) {
        this.clienttype = clienttype;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "ClientBaseDTO{" +
                "idclient=" + idclient +
                ", name='" + name + '\'' +
                ", shiptype='" + shiptype + '\'' +
                ", clienttype='" + clienttype + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", currency=" + currency +
                '}';
    }
}
