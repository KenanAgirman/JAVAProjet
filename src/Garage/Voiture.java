package Garage;

import java.io.Serializable;

public class Voiture implements Serializable {
    private Modele modele;
    private String nom;

    public Voiture(){
        this.modele = new Modele();
        this.nom = "";
    }

    public Voiture(Modele modele, String nom) {
        this.modele = modele;
        this.nom = nom;
    }

    public Modele getModele() {
        return modele;
    }

    public void setModele(Modele modele) {
        this.modele = modele;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Voiture{" +
                "modele=" + modele +
                ", nom='" + nom + '\'' +
                '}';
    }
}
