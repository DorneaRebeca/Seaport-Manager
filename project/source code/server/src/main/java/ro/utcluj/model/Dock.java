package ro.utcluj.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Dock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int iddocks;
    private int size;
    private String region;
    private int price;

    @OneToOne(mappedBy = "dockSold", cascade = CascadeType.ALL)
    private Sold client;


    @OneToMany(mappedBy = "dock", cascade = CascadeType.ALL)
    private Set<Rental> rentalSet;

    public Dock() {
    }

    public void setDockid(int dockid) {
        this.iddocks = dockid;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDockid() {
        return iddocks;
    }

    public int getSize() {
        return size;
    }

    public String getRegion() {
        return region;
    }

    public int getPrice() {
        return price;
    }

    public Set<Rental> getRentalSet() {
        return rentalSet;
    }

    public void setRentalSet(Set<Rental> rentalSet) {
        this.rentalSet = rentalSet;
    }

    public int getIddocks() {
        return iddocks;
    }

    public void setIddocks(int iddocks) {
        this.iddocks = iddocks;
    }




    @Override
    public String toString() {
        return "Dock{" +
                "dockid=" + iddocks +
                ", size=" + size +
                ", region='" + region + '\'' +
                ", price=" + price +
                '}';
    }
}
