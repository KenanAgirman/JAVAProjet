package Garage;

import java.util.List;

public class Modele {
    private String Nom;
    private int Puissance;

    private float PrixdeBase;
    private Moteur moteur;

    private List<Option> options;

    public Modele() {
        Nom = "Kenan";
        Puissance = 1;
        PrixdeBase = 20000.0f;
        moteur = Moteur.Essence;
    }

    public String getNom() {
        return Nom;
    }

    public int getPuissance() {
        return Puissance;
    }

    public float getPrixdeBase() {
        return PrixdeBase;
    }

    public Moteur getMoteur() {
        return moteur;
    }

    public enum Moteur {
        Essence,
        Diesel,
        Electrique,
        Hybride
    };

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
    public String tostring()
    {
        return "[Nom: " + Nom + ", Puissance: " + Puissance + ", PrixdeBase: " + PrixdeBase + ", Moteur: " + moteur + "]";
    }

    public boolean equals(Modele m)
    {
        if (Nom.equals(m.Nom) && Puissance == m.Puissance && PrixdeBase == m.PrixdeBase && moteur == m.moteur)
            return true;
        else
            return false;
    }

}
