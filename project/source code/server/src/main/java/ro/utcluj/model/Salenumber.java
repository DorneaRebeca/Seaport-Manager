package ro.utcluj.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Salenumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idsalenumber;

    private int number;

   public Salenumber(){}


    public int getIdsalenumber() {
        return idsalenumber;
    }

    public int getNumber() {
        return number;
    }

    public void setIdsalenumber(int idsalenumber) {
        this.idsalenumber = idsalenumber;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
