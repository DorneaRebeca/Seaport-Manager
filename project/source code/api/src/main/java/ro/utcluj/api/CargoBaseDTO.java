package ro.utcluj.api;

import java.io.Serializable;
import java.sql.Date;

public class CargoBaseDTO implements Serializable {

    private int idcargos;
    private Date date;
    private int hour;
    private int price;
    private int clientId;

    public int getClientId() {
        return clientId;
    }

    public int getIdcargos() {
        return idcargos;
    }

    public Date getDate() {
        return date;
    }

    public int getHour() {
        return hour;
    }

    public int getPrice() {
        return price;
    }

    public void setIdcargos(int idcargos) {
        this.idcargos = idcargos;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "CargoBaseDTO{" +
                "idcargos=" + idcargos +
                ", date=" + date +
                ", hour=" + hour +
                ", price=" + price +
                ", idclient="+ clientId;
    }
}
