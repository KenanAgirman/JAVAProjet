package Garage;

public class Personne {

    public static int numCourant = 0;
    private String Nom;
    private String Prenom;


    public Personne() {

        this.Nom = "Agirman";
        this.Prenom = "Kenan";
    }

    public Personne(String nom,String prenom) {
        Nom = nom;
        Prenom = prenom;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    @Override
    public String toString() {
        return "Personne{" +
                "Nom='" + Nom + '\'' +
                ", Prenom='" + Prenom + '\'' +
                '}';
    }
}
