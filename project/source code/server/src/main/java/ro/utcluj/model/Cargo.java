package ro.utcluj.model;


import javax.persistence.*;
import java.sql.Date;

@Entity
public class Cargo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idcargos;
    private Date date;
    private int hour;
    private int price;

    @ManyToOne
    @JoinColumn(name = "idclient")
    private Client client;


    public Cargo(Date date, int hour, int price) {
        this.date = date;
        this.hour = hour;
        this.price = price;
    }

    public Cargo() {
    }

    public int getIdcargo() {
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

    public Client getClient(){ return client;}

    public void setIdcargo(int idcargo) {
        this.idcargos = idcargo;
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

    public void setClient(Client client){ this.client = client;}

    @Override
    public String toString() {
        return "Cargo{" +
                "idcargo=" + idcargos +
                ", date=" + date +
                ", hour='" + hour + '\'' +
                ", price=" + price +
                ", client=" + client +
                '}';
    }
}
