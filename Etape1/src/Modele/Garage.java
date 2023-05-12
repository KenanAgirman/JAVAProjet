package Modele;
import Garage.*;

import java.util.Iterator;
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
        Iterator<Client> iter = clients.iterator();
        Client temp;

        boolean end = false;

        int i = 0;
        while (iter.hasNext() && !end) {
            temp = iter.next();
            if (temp.getNumero() == num) {
                end = true;
            } else {
                i++;
            }
        }

        if (end == true) {
            clients.remove(i);
        }
    }

    public int VerifieContratsClient(int ind)
    {
        int veri = 0, i = 0;

        while(i < contrats.size() && veri == 0)
        {
            if(clients.get(ind).getNumero() == contrats.get(i).getClient().getNumero()) veri = 1;
            else i++;
        }

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
        Iterator<Employe> iter = employes.iterator();
        Employe temp;

        boolean end = false;

        int i = 0;
        while (iter.hasNext() && !end) {
            temp = iter.next();
            if (temp.getNumero() == num) {
                end = true;
            } else {
                i++;
            }
        }

        if (end == true) {
            employes.remove(i);
        }

    }

    public int VerifieContratsEmploye(int ind)
    {
        int trouve = 0, i = 0;

        while(i < contrats.size() && trouve == 0)
        {
            if(employes.get(ind).getNumero() == contrats.get(i).getVendeur().getNumero()) trouve = 1;
            else i++;
        }

        return trouve;
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
}
