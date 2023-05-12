import Garage.*;
public class Main {
    public static void main(String[] args) {

        Modele modele = new Modele();
        System.out.println("Nom: " + modele.getNom());
        System.out.println("Puissance: " + modele.getPuissance());
        System.out.println("PrixDeBase: " + modele.getPrixdeBase());
        System.out.println("Moteur : " + modele.getMoteur());
        System.out.println("Modele : " + modele.tostring());
        System.out.println("--------------------------------");
        Option option = new Option();
        System.out.println("Code: " + option.getCode());
        System.out.println("Intitule: " + option.getIntitule());
        System.out.println("Prix: " + option.getPrix());
        System.out.println("Option: " + option.tostring());
        System.out.println("--------------------------------");
        Voiture voiture = new Voiture();
        System.out.println("Nom voiture: " + voiture.getNom());
        System.out.println("Modele voiture: " + voiture.getModele());
        System.out.println("Options voiture: " + voiture.getOption());
        Personne personne = new Personne();
        System.out.println("--------------------------------");
        System.out.println("Nom: " + personne.getNom());
        System.out.println("Prenom: "+personne.getPrenom());
        System.out.println("Personne : " + personne.toString());
        System.out.println("--------------------------------");
        Client client = new Client();


    }
}


