import Controller.Controleur;
import GUI.JFrameGarage;
import Garage.*;
import Modele.Garage;


import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

//        Garage garage;
//        garage = Garage.getInstance();
//
//        garage.importeModeles("Modeles.csv");
//        garage.importeOptions("Options.csv");
//
//        System.out.println(garage.getModeles());
//        System.out.println(garage.getOptions());

        Garage garage = Garage.getInstance();
        JFrameGarage fenetre = new JFrameGarage();

        Controleur controleur = new Controleur(garage, fenetre);
        fenetre.setControleur(controleur);

        fenetre.setVisible(true);
    }
}