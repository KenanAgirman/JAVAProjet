package Garage;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Contrat implements estIdentifiable, Serializable {
    private int numero;
    private Employe vendeur;
    private Client client;
    private String voiture;
    private Calendar datecontrat;

    public static int numCourantContrat = 0;

    public Contrat(){
        this.numero = 0;
        this.vendeur = new Employe();
        this.client = new Client();
        this.voiture = "";
        this.datecontrat = Calendar.getInstance();
    }

    public Contrat(int numero, Employe vendeur, Client client, String voiture) {
        this.numero = numero;
        this.vendeur = vendeur;
        this.client = client;
        this.voiture = voiture;
        this.datecontrat = Calendar.getInstance();
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Employe getVendeur() {
        return vendeur;
    }

    public void setVendeur(Employe vendeur) {
        this.vendeur = vendeur;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getVoiture() {
        return voiture;
    }

    public void setVoiture(String voiture) {
        this.voiture = voiture;
    }

    public Calendar getDatecontrat() {
        return datecontrat;
    }

    public void setDatecontrat(Calendar datecontrat) {
        this.datecontrat = datecontrat;
    }

    @Override
    public int getNumero() {
        return numero;
    }

    @Override
    public String toString() {
        return "Contrat{" +
                "numero=" + numero +
                ", vendeur=" + vendeur +
                ", client=" + client +
                ", voiture='" + voiture + '\'' +
                ", datecontrat=" + datecontrat.getTime() +
                '}';
    }
}
