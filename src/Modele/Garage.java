package Modele;
import Garage.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Garage {
    private LinkedList<Employe> employes;
    private LinkedList<Client> clients;
    private LinkedList<Modele> modeles;
    private LinkedList<Option> options;
    private LinkedList<Contrat> contrats;
    private int indEmpLogger = 0;
    private static Garage instance = null;
    private static Voiture projetEnCours = null;
    public static Employe EmpLogger = null;

    private Garage(){
       this.clients = new LinkedList<>();
       this.contrats = new LinkedList<>();
       this.employes = new LinkedList<>();
       this.options = new LinkedList<>();
       this.modeles = new LinkedList<>();
    }

    public static Garage getInstance(){
        if(instance == null)
            instance =  new Garage();
        return instance;
    }

    public static Voiture getProjetEnCours(){
        if(projetEnCours == null)
            projetEnCours = new Voiture();
        return projetEnCours;
    }

    public LinkedList<Employe> getEmployes() {
        return employes;
    }

    public void setEmployes(LinkedList<Employe> employes) {
        this.employes = employes;
    }

    public LinkedList<Client> getClients() {
        return clients;
    }

    public void setClients(LinkedList<Client> clients) {
        this.clients = clients;
    }

    public LinkedList<Modele> getModeles() {
        return modeles;
    }

    public void setModeles(LinkedList<Modele> modeles) {
        this.modeles = modeles;
    }

    public LinkedList<Option> getOptions() {
        return options;
    }

    public void setOptions(LinkedList<Option> options) {
        this.options = options;
    }

    public LinkedList<Contrat> getContrats() {
        return contrats;
    }

    public void setContrats(LinkedList<Contrat> contrats) {
        this.contrats = contrats;
    }

    public int getIndEmpLogger() {
        return indEmpLogger;
    }

    public void setIndEmpLogger(int indEmpLogger) {
        this.indEmpLogger = indEmpLogger;
    }

    public static void resetProjetEnCours()
    {
        projetEnCours = new Voiture();
    }

    //----------------------------------------------------------------------------------
    //---------		MODELE
    //----------------------------------------------------------------------------------
    public void ajouteModele(Modele m)
    {
        modeles.add(m);
    }

    public void afficheModelesDisponibles()
    {
        modeles.toString();
    }

    public Modele getModele(int indice)
    {
        if(indice >= modeles.size()) return modeles.get(0);
        else return modeles.get(indice);
    }

    //----------------------------------------------------------------------------------
    //---------		OPTION
    //----------------------------------------------------------------------------------

    public void ajouteOption(Option o)
    {
        options.add(o);
    }

    public void afficheOptionsDisponibles()
    {
        options.toString();
    }

    public Option getOption(int indice)
    {
        if(indice >= options.size()) return options.get(0);
        else return options.get(indice);
    }

    //----------------------------------------------------------------------------------
    //---------		CLIENT
    //----------------------------------------------------------------------------------

    public void ajouteClient(String nom,String prenom,String gsm)
    {
        Personne.numCourant++;

        clients.add(new Client(nom, prenom, Personne.numCourant, gsm));
    }

    public void afficheClients()
    {
        clients.toString();
    }

    public void supprimeClientParIndice(int ind)
    {
        clients.remove(ind);
    }

    public void supprimeClientParNumero(int num)
    {
        int i;

        for(i = 0;i < clients.size() && clients.get(i).getNumero() != num;i++);

        if(clients.get(i).getNumero() == num)
        {
            int veri = VerifieContratsClient(i);
            if(veri == 0) clients.remove(i);
        }
        else return;
    }

    public int VerifieContratsClient(int ind)
    {
        int veri = 0, i = 0;

        while(i < contrats.size() && veri == 0)
        {
            if(clients.get(ind).getNumero() == contrats.get(i).getClient().getNumero()) veri = 1;
            else i++;
        }

        //si 1 alors il existe dans le table des contrats est donc ne peut pas etre supprimer
        //si 0 il ne existe pas dans le table des contrats est donc peut etre supprimer
        return veri;
    }

    //----------------------------------------------------------------------------------
    //---------		EMPLOYE
    //----------------------------------------------------------------------------------

    public void ajouteEmploye(String nom,String prenom,String login,String fonction)
    {
        Personne.numCourant++;

        employes.add(new Employe(nom, prenom,Personne.numCourant,login, fonction));
    }

    public void afficheEmployes()
    {
        employes.toString();
    }

    public void supprimeEmployeParIndice(int ind)
    {
        employes.remove(ind);
    }

    public void supprimeEmployeParNumero(int num)
    {
        int i;

        for(i = 0;i < employes.size() && employes.get(i).getNumero() != num;i++);

        if(employes.get(i).getNumero() == num)
        {
            int veri = VerifieContratsEmploye(i);
            if(veri == 0) employes.remove(i);
        }
        else return;
    }

    public int VerifieContratsEmploye(int ind)
    {
        int veri = 0, i = 0;

        while(i < contrats.size() && veri == 0)
        {
            if(employes.get(ind).getNumero() == contrats.get(i).getVendeur().getNumero()) veri = 1;
            else i++;
        }

        //si 1 alors il existe dans le table des contrats est donc ne peut pas etre supprimer
        //si 0 il ne existe pas dans le table des contrats est donc peut etre supprimer
        return veri;
    }

    //----------------------------------------------------------------------------------
    //---------		Contrats
    //----------------------------------------------------------------------------------

    public void ajouteContrat(int IndVendeur, int IndClient, String voiture)
    {
        Contrat.numCourantContrat++;

        contrats.add(new Contrat(Contrat.numCourantContrat, getEmployes().get(IndVendeur),
                getClients().get(IndClient), voiture));
    }

    public void AfficheContrats()
    {
        contrats.toString();
    }

    public void supprimeContratParIndice(int ind)
    {
        contrats.remove(ind);
    }

    public String getProjet(int indice)
    {
        return contrats.get(indice).getVoiture();
    }

    //----------------------------------------------------------------------------------
    //---------		Flux
    //----------------------------------------------------------------------------------

    public void importeModeles(String nomFichier) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(nomFichier));

            String buffer;
            String[] tokens;
            Modele m;
            int choix;

            br.readLine(); // skip first line

            while ((buffer = br.readLine()) != null) {
                //System.out.println("Voici la chaine lue:\n" + buffer + "\n");

                tokens = buffer.split(";");

                m = new Modele();

                m.setNom(tokens[0]);

                m.setPuissance(Integer.parseInt(tokens[1]));

                if ("essence".equals(tokens[2])) choix = 1;
                else if ("hybride".equals(tokens[2])) choix = 2;
                else if ("electrique".equals(tokens[2])) choix = 3;
                else choix = 4;

                switch (choix) {
                    case 1: m.setMoteur("Essence");
                        break;

                    case 2: m.setMoteur("Hybride");
                        break;

                    case 3: m.setMoteur("Electrique");
                        break;

                    case 4: m.setMoteur("Diesel");
                }

                m.setImage(tokens[3]);

                m.setPrixDeBase((float) Double.parseDouble(tokens[4]));

                ajouteModele(m);
            }

            br.close();
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier " + nomFichier + " : " + e.getMessage());
        }
    }

    public void importeOptions(String nomFichier) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(nomFichier));

            String buffer;
            String[] tokens;
            Option o;

            br.readLine(); // skip first line

            while ((buffer = br.readLine()) != null) {
                tokens = buffer.split(";");

                o = new Option();

                o.setCode(tokens[0]);

                o.setIntitule(tokens[1]);

                o.setPrix((float) Double.parseDouble(tokens[2]));

                ajouteOption(o);
            }

            br.close();
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier " + nomFichier + " : " + e.getMessage());
        }
    }

}
