import Controller.Controleur;
import GUI.JFrameGarage;
import Modele.Garage;

import com.formdev.flatlaf.FlatDarculaLaf;

public class Main {
    public static void main(String[] args) {
        //pour ajouter notre nouveau theme sur le application
        FlatDarculaLaf.setup();

        Garage garage = Garage.getInstance();
        JFrameGarage fenetre = new JFrameGarage();

        Controleur controleur = new Controleur(garage, fenetre);
        fenetre.setControleur(controleur);

        fenetre.setVisible(true);
    }
}