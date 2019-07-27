package ro.utcluj.api;

import java.io.Serializable;

public class DockBaseDTO implements Serializable {


    private int iddocks;
    private int size;
    private String region;
    private int price;


    public int getIddocks() {
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

    public void setIddocks(int iddocks) {
        this.iddocks = iddocks;
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

    @Override
    public String toString() {
        return "DockBaseDTO{" +
                "iddocks=" + iddocks +
                ", size=" + size +
                ", region='" + region + '\'' +
                ", price=" + price +
                '}';
    }
}
