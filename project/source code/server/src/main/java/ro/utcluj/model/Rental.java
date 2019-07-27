package ro.utcluj.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idrentals;
    private int price;
    private Date rentdate;
    private Date enddate;

    @ManyToOne
    @JoinColumn(name = "idclient")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "iddocks")
    private Dock dock;

    public Rental(int price, Date rentDate, Date endDate) {
        this.price = price;
        this.rentdate = rentDate;
        this.enddate = endDate;
    }

    public Rental() {
    }

    public int getIdrentals() {
        return idrentals;
    }

    public Date getRentdate() {
        return rentdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public Client getClient() {
        return client;
    }

    public Dock getDock() {
        return dock;
    }

    public int getIdrental() {
        return idrentals;
    }

    public int getPrice() {
        return price;
    }

    public Date getRentDate() {
        return rentdate;
    }

    public Date getEndDate() {
        return enddate;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setRentDate(Date rentDate) {
        this.rentdate = rentDate;
    }

    public void setEndDate(Date endDate) {
        this.enddate = endDate;
    }

      public void setClient(Client client) {
        this.client = client;
    }

    public void setDock(Dock dock) {
        this.dock = dock;
    }

    @Override
    public String toString() {
        return "Rental{" +
                //"idrentals=" + idrentals +
                ", price=" + price +
                ", rentdate=" + rentdate +
                ", enddate=" + enddate +
                ", client=" + client.getUsername () +
                ", dock=" + dock.getIddocks () +
                '}';
    }
}
