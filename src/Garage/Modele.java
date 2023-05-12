package Garage;

import java.util.ArrayList;
import java.util.List;

public class Modele {
    private String nom;
    private int puissance;
    private String moteur;
    private float prixDeBase;
    private List<Option> options;

    public Modele(){
        this.nom = "";
        this.puissance = 0;
        this.moteur = "";
        this.prixDeBase = 0.0f;
        this.options = new ArrayList<>();
    }

    public Modele(String nom, int puissance, String moteur, float prixDeBase, List<Option> options) {
        this.nom = nom;
        this.puissance = puissance;
        this.moteur = moteur;
        this.prixDeBase = prixDeBase;
        this.options = options;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPuissance() {
        return puissance;
    }

    public void setPuissance(int puissance) {
        this.puissance = puissance;
    }

    public String getMoteur() {
        return moteur;
    }

    public void setMoteur(String moteur) {
        this.moteur = moteur;
    }

    public float getPrixDeBase() {
        return prixDeBase;
    }

    public void setPrixDeBase(float prixDeBase) {
        this.prixDeBase = prixDeBase;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public void retireOption(Option option) {
        options.remove(option);
    }

    public void ajouterOption(Option option) {
        options.add(option);
    }

    @Override
    public String toString() {
        return "Modele{" +
                "nom='" + nom + '\'' +
                ", puissance=" + puissance +
                ", moteur='" + moteur + '\'' +
                ", prixDeBase=" + prixDeBase +
                ", options=" + options +
                '}';
    }
}
