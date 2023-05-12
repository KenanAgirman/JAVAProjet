package Garage;

import java.util.Date;

public class Contrat implements estIdentifiable {
    private int numero;
    private Employe vendeur;
    private Client client;
    private String voiture;
    private Date datecontrat;

    public static int numCourantContrat = 0;

    public Contrat(){
        this.numero = 0;
        this.vendeur = new Employe();
        this.client = new Client();
        this.voiture = "";
        this.datecontrat = new Date();
    }

    public Contrat(int numero, Employe vendeur, Client client, String voiture) {
        this.numero = numero;
        this.vendeur = vendeur;
        this.client = client;
        this.voiture = voiture;
        this.datecontrat = new Date();
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

    public Date getDatecontrat() {
        return datecontrat;
    }

    public void setDatecontrat(Date datecontrat) {
        this.datecontrat = datecontrat;
    }

    @Override
    public int getNumero() {
        return numero;
    }

}
