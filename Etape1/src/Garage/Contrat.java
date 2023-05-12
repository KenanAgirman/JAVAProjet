package Garage;

public class Contrat implements EstIdentifiee {
        /* ajout date dans contrat*/
    private int numero;
    private Employe vendeur;
    private Client client;
    private String voiture;
    public static int numCourantContrat = 0;
    public Contrat(){
        this.numero = 0;
        this.vendeur = new Employe();
        this.client = new Client();
        this.voiture = "";
    }
    public Contrat(int numero, Employe vendeur, Client client, String voiture) {
        this.numero = numero;
        this.vendeur = vendeur;
        this.client = client;
        this.voiture = voiture;
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

    @Override
    public int getNumero() {
        return 0;
    }
}
