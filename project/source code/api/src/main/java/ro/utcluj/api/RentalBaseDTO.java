package ro.utcluj.api;

import java.io.Serializable;
import java.sql.Date;

public class RentalBaseDTO implements Serializable {


    private int idrentals;
    private int price;
    private Date rentdate;
    private Date enddate;


    public int getIdrentals() {
        return idrentals;
    }

    public int getPrice() {
        return price;
    }

    public Date getRentdate() {
        return rentdate;
    }

    public Date getEnddate() {
        return enddate;
    }


    public void setIdrentals(int idrentals) {
        this.idrentals = idrentals;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setRentdate(Date rentdate) {
        this.rentdate = rentdate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    @Override
    public String toString() {
        return "RentalBaseDTO{" +
                "idrentals=" + idrentals +
                ", price=" + price +
                ", rentdate=" + rentdate +
                ", enddate=" + enddate +
                '}';
    }
}
