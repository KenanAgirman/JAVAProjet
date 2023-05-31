package Modele;
import Beans.TraceBean;
import Garage.*;

import java.io.*;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Properties;

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
    public static Properties prop;
    private TraceBean logger;

    private Garage(){
        this.clients = new LinkedList<>();
        this.contrats = new LinkedList<>();
        this.employes = new LinkedList<>();
        this.options = new LinkedList<>();
        this.modeles = new LinkedList<>();

        //notre bean qui va exrire dans notre fichier
        logger = new TraceBean();
        logger.setNomFichierLog("Fichiers/Trace.log");

        logger.log("Session du : " + Calendar.getInstance().getTime());
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

        logger.log("Nouveau client ajouter : "+ Personne.numCourant + " - " + nom + " - " + prenom);
    }

    public void afficheClients()
    {
        clients.toString();
    }

    public void supprimeClientParIndice(int ind)
    {
        logger.log("client supprimer : " + clients.get(ind).getNumero() + " - " + clients.get(ind).getNom() + " - " + clients.get(ind).getPrenom());
        clients.remove(ind);
    }

    public int supprimeClientParNumero(int num)
    {
        int i = 0;

        if(clients.size() == 0) return -3;

        for (Client cli: clients) {
            if(cli.getNumero() == num)
            {
                int veri = VerifieContratsClient(i);
                if(veri == 0)
                {
                    logger.log("client supprimer : " + clients.get(i).getNumero() + " - " + clients.get(i).getNom() + " - " + clients.get(i).getPrenom());
                    clients.remove(i);
                    return i;
                }
                else return -2;
            }
            i++;
        }

        return -1;
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

        logger.log("Nouveau employe ajouter : " + nom + " - " + prenom);
    }

    public void afficheEmployes()
    {
        employes.toString();
    }

    public void supprimeEmployeParIndice(int ind)
    {
        prop.remove(employes.get(ind).getLogin());
        logger.log("Employe supprimer : " + employes.get(ind).getNumero() + " - " + employes.get(ind).getNom() + " - " + employes.get(ind).getPrenom());
        employes.remove(ind);
    }

    public int supprimeEmployeParNumero(int num)
    {
        int i = 0;

        if(employes.size() == 0) return -3;

        for (Employe emp: employes) {
            if(emp.getNumero() == num)
            {
                int veri = VerifieContratsEmploye(i);
                if(veri == 0)
                {
                    prop.remove(employes.get(i).getLogin());
                    logger.log("Employe supprimer : " + employes.get(i).getNumero() + " - " + employes.get(i).getNom() + " - " + employes.get(i).getPrenom());
                    employes.remove(i);
                    return i;
                }
                else return -2;
            }
            i++;
        }

        return -1;
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

    public void ajouteContrat(Employe Vendeur, Client client, String voiture)
    {
        Contrat.numCourantContrat++;

        contrats.add(new Contrat(Contrat.numCourantContrat, Vendeur,client, voiture));

        logger.log("Nouveau contrat ajouter : " + Contrat.numCourantContrat + " " + Vendeur.getNom() + " " + Vendeur.getPrenom() + " - "
                 +  client.getNom() + " " + client.getPrenom() + " - " + voiture);
    }

    public void AfficheContrats()
    {
        contrats.toString();
    }

    public void supprimeContratParIndice(int ind)
    {
        logger.log("contrat supprimer : " + contrats.get(ind).getNumero() + " " + contrats.get(ind).getVendeur().getNom() + " " + contrats.get(ind).getVendeur().getPrenom() + " - "
                + contrats.get(ind).getClient().getNom() + " " +  contrats.get(ind).getClient().getPrenom() + " " + contrats.get(ind).getClient().getPrenom() + " - " + contrats.get(ind).getVoiture());
        contrats.remove(ind);
    }

    public String getProjet(int indice)
    {
        return contrats.get(indice).getVoiture();
    }

    //----------------------------------------------------------------------------------
    //---------		Flux modeles/options
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

            logger.log("Modeles importer avec succes");
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

            logger.log("Options importer avec succes");
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier " + nomFichier + " : " + e.getMessage());
        }
    }

    //----------------------------------------------------------------------------------
    //---------		Flux pour les projetEncour
    //----------------------------------------------------------------------------------

    public void SaveProjetEnCours(String nomFichier) throws FileNotFoundException, IOException
    {
        FileOutputStream fos = new FileOutputStream(nomFichier);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(projetEnCours);
        oos.flush();

        logger.log("Projet " + nomFichier + " Sauvegarder");
    }

    public void LoadProjetEnCours(String nomFichier) throws FileNotFoundException, IOException, ClassNotFoundException
    {
        FileInputStream fis = new FileInputStream(nomFichier);
        ObjectInputStream ois = new ObjectInputStream(fis);

        projetEnCours = (Voiture) ois.readObject();

        logger.log("Projet " + nomFichier + " Loader");
    }


    //----------------------------------------------------------------------------------
    //---------		Flux pour les properties
    //----------------------------------------------------------------------------------

    public void SaveProperties(String nomFichier)
    {
        try (OutputStream output = new FileOutputStream(nomFichier)) {
            prop.store(output, "Informations de connexion et de mot de passe");

            logger.log("Properties sauver sur le fichier!");
        } catch (IOException e) {
            System.err.println("Erreur pendant le enregistrement sur le fichier pour les properties: " + e.getMessage());
        }
    }

    public void LoadProperties(String nomFichier)
    {
        prop = new Properties();

        try (InputStream input = new FileInputStream(nomFichier)) {
            prop.load(input);

            logger.log("Propriétés chargées à partir du fichier!");
        } catch (IOException e) {
            System.out.println("Erreur de lecture du fichier: " + e.getMessage());
            // Fichier ne existe pas alors on va creer une par defaut
            prop = new Properties();
            prop.setProperty("admin", "admin1");
        }
    }

    //----------------------------------------------------------------------------------
    //---------		Flux pour Garage
    //----------------------------------------------------------------------------------

    public void SaveGarage(String nomFichier)
    {
        try {
            FileOutputStream fos = new FileOutputStream(nomFichier);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(Personne.numCourant);

            oos.writeObject(employes);
            oos.writeObject(clients);

            oos.writeObject(Contrat.numCourantContrat);
            oos.writeObject(contrats);

            oos.flush();

            logger.log("Garage sauvegarder");
        } catch (FileNotFoundException e) {
            System.err.println("Fichier non trouve!");
        } catch (IOException e) {
            System.err.println("Erreur de ecriture dans le fichier: " + e.getMessage());
        }
    }

    public void LoadGarage(String nomFichier)
    {
        try
        {
            FileInputStream fis = new FileInputStream(nomFichier);
            ObjectInputStream ois = new ObjectInputStream(fis);

            Personne.numCourant = (Integer) ois.readObject();

            employes = (LinkedList<Employe>) ois.readObject();
            clients = (LinkedList<Client>) ois.readObject();

            Contrat.numCourantContrat = (Integer) ois.readObject();
            contrats = (LinkedList<Contrat>) ois.readObject();

            logger.log("Garage loader");
        } catch (FileNotFoundException e) {
            logger.log("Fichier non trouve pour garage, prend des valeurs par defaut!");

            employes = new LinkedList<>();

            Personne.numCourant++;

            Employe admin = new Employe("admin", "admin", Personne.numCourant, "admin", "Administratif");
            admin.setMotDePasse("admin1");

            employes.add(admin);

            clients = new LinkedList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erreur de ecriture dans le fichier: " + e.getMessage());
        }
    }

}
