package ro.utcluj.model;

import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
public class Sold {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idsold;


    @ManyToOne
    @JoinColumn(name = "idclient")
    private Client beneficiar;

    @OneToOne
    @JoinColumn(name = "iddocks")
    private Dock dockSold;


    public int getIdsold() {
        return idsold;
    }

    public Client getBeneficiar() {
        return beneficiar;
    }

    public Dock getDockSold() {
        return dockSold;
    }

    public void setIdsold(int idsold) {
        this.idsold = idsold;
    }

    public void setBeneficiar(Client beneficiar) {
        this.beneficiar = beneficiar;
    }

    public void setDockSold(Dock dockSold) {
        this.dockSold = dockSold;
    }
}
