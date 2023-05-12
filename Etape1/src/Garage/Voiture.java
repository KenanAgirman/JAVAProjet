package Garage;

import java.util.ArrayList;

public class Voiture {

    private String nom;
    private Modele modele;
    private ArrayList<Option> options;

    public Voiture() {
        nom = "kenan";
        modele = modele;
        options = new ArrayList<Option>();
    }

    public String getNom()
    {
        return nom;
    }

    public Modele getModele()
    {
        return modele;
    }

    public ArrayList<Option> getOption()
    {
        return options;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setModele(Modele modele) {
        this.modele = modele;
    }

    public ArrayList<Option> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<Option> options) {
        this.options = options;
    }

    public String tostring()
    {
        return "[Nom: " + nom + ", Modele: " + modele + ", ArrayList<Option>: " + options +"]";
    }

}

