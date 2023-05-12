import Garage.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //creation des donnees
        List<Option> options = new ArrayList<Option>();
        options.add(new Option("LLL","TestOptionModele",75));
        options.add(new Option("KKKK", "HEHE", 99.6F));

        Modele m = new Modele("Test", 55, "Diesel", 2544.66F, options);
        Option o = new Option("566ss","TestOption",55);
        Voiture v = new Voiture(m,"Voiture1");

        //test des tostring
        System.out.println(m.toString());
        System.out.println(o.toString());
        System.out.println(v.toString());
        System.out.println(v.getModele().getOptions().get(0));

        //Test du method ajout et retire
        m.ajouterOption(o);
        System.out.println(m.toString());

        m.retireOption(o);
        System.out.println(m.toString());
    }
}