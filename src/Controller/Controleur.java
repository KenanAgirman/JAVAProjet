package Controller;

import GUI.*;
import Modele.Garage;
import Garage.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Vector;

public class Controleur extends WindowAdapter implements ActionListener
{
    private Garage instanceGarage;
    private JFrameGarage viewGarage;

    public Controleur(Garage instanceGarage, JFrameGarage viewGarage) {
        this.instanceGarage = instanceGarage;
        this.viewGarage = viewGarage;

        //Loader les employes et clients
        this.instanceGarage.LoadGarage("Fichiers/Garage.data");

        //pour l'interface

        //employe
        DefaultTableModel modelEmp = (DefaultTableModel) this.viewGarage.tableEmployes.getModel();

        for (Employe emp:this.instanceGarage.getEmployes()) {
            Vector ligne = new Vector();
            ligne.add(emp.getNumero());
            ligne.add(emp.getNom());
            ligne.add(emp.getPrenom());
            ligne.add(emp.getFonction());
            modelEmp.addRow(ligne);
        }

        //client

        DefaultTableModel modelCli = (DefaultTableModel) this.viewGarage.tableClients.getModel();

        for (Client cli:this.instanceGarage.getClients()) {
            Vector ligne = new Vector();
            ligne.add(cli.getNumero());
            ligne.add(cli.getNom());
            ligne.add(cli.getPrenom());
            ligne.add(cli.getGsm());
            modelCli.addRow(ligne);
        }

        //importer les modeles et options
        this.instanceGarage.importeOptions("Fichiers/Options.csv");
        this.instanceGarage.importeModeles("Fichiers/Modeles.csv");

        //les mettre dans le interface (utilisation du foreach de java)
        //pour le moment cela cause des probleme avec le interface parce qu'il prennent trop d'espace,
        // mais ca marche, il faut juste modifier l'interface un peut
        for (Option opt: this.instanceGarage.getOptions()) {
            this.viewGarage.comboBoxOptionsDisponibles.addItem(opt);
        }

        for (Modele mod: this.instanceGarage.getModeles()) {
            this.viewGarage.comboBoxModelesDisponibles.addItem(mod);
        }

        instanceGarage.LoadProperties("Fichiers/passwords.properties");

        //Etat initiale
        PasLogger();

        //instanceGarage.prop.list(System.out);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Quitter")) {
            onQuitter();
        }
        if(e.getActionCommand().equals("Login")) {
            onLogin();
        }
        if(e.getActionCommand().equals("Logout")){
            onLogout();
        }
        if(e.getActionCommand().equals("Reset mot de passe")){
            onResetMotDePasse();
        }
        if(e.getActionCommand().equals("Nouveau modèle")){
            onNouveauModele();
        }
        if(e.getActionCommand().equals("Nouvelle Option")){
            onNouveauOption();
        }
        if(e.getActionCommand().equals("Choisir Modele")){
            onChoisirModele();
        }
        if(e.getActionCommand().equals("Choisir Option")){
            onChoisirOption();
        }
        if(e.getActionCommand().equals("Nouveau Projet")){
            onNouveauProjet();
        }
        if(e.getActionCommand().equals("Supprimer Option")){
            onSupprimerOption();
        }
        if(e.getActionCommand().equals("Accorder réduction")){
            onAccorderReduction();
        }
        if(e.getActionCommand().equals("Enregistrer Projet")){
            onEnregistrerProjet();
        }
        if(e.getActionCommand().equals("Ouvrir Projet")){
            onOuvrirProjet();
        }
        if(e.getActionCommand().equals("Nouveau Employe")){
            onNouveauEmploye();
        }
        if(e.getActionCommand().equals("Supprimer employe par numero")){
            onSupprimerEmpParNum();
        }
        if(e.getActionCommand().equals("Supprimer employe selectionner")){
            onSupprimerEmpSelect();
        }
        if(e.getActionCommand().equals("Nouveau Client")){
            onNouveauClient();
        }
        if(e.getActionCommand().equals("Supprimer client par numero")){
            onSupprimerCliParNum();
        }
        if(e.getActionCommand().equals("Supprimer client selectionner")){
            onSupprimerCliSelect();
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        onQuitter();
    }

    private void onQuitter()
    {
        int ret = JOptionPane.showConfirmDialog(null,"Êtes-vous certain de vouloir quitter ?");
        if (ret == JOptionPane.YES_OPTION)
        {
            instanceGarage.SaveGarage("Fichiers/Garage.data");
            instanceGarage.SaveProperties("Fichiers/passwords.properties");
            System.exit(0);
        }
    }

    private void onLogin()
    {
        try
        {
            JDialogLogin dialog = new JDialogLogin(null,true,"Entrée en session...");
            dialog.setVisible(true);

            if(dialog.getLogin() != null)
            {
                if(instanceGarage.prop.containsKey(dialog.getLogin()))
                {
                    int i = 0;
                    Employe emp = instanceGarage.getEmployes().get(i);

                    while (i < instanceGarage.getEmployes().size() && !emp.getLogin().equals(dialog.getLogin()))
                    {
                        i++;
                        emp = instanceGarage.getEmployes().get(i);
                    }

                    if(instanceGarage.prop.getProperty(dialog.getLogin()).isEmpty())
                    {
                        //System.out.println("nouveau");

                        instanceGarage.EmpLogger = emp;

                        JOptionPane.showMessageDialog(null, "étant donné que ce compte n'a pas de mot de passe, le mot de passe que vous avez entré a été défini comme mot de passe pour le compte", "Mot de Passe", JOptionPane.INFORMATION_MESSAGE);

                        instanceGarage.EmpLogger.setMotDePasse(dialog.getMotDePasse());
                        instanceGarage.prop.setProperty(dialog.getLogin(), dialog.getMotDePasse());

                        if(instanceGarage.EmpLogger.getFonction().equals("Administratif"))
                        {
                            JOptionPane.showMessageDialog(null, "Vous avez été connecté avec succès en tant qu'administrateur", "Succes", JOptionPane.INFORMATION_MESSAGE);
                            AdminLogger();
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Vous avez été connecté avec succès en tant que vendeur", "Succes", JOptionPane.INFORMATION_MESSAGE);
                            VendeurLogger();
                        }
                    }
                    else if(!instanceGarage.prop.getProperty(dialog.getLogin()).equals(dialog.getMotDePasse())) throw new Exception("Mot de passe incorrect");
                    else
                    {
                        //System.out.println("logger");

                        instanceGarage.EmpLogger = emp;

                        if(instanceGarage.EmpLogger.getFonction().equals("Administratif"))
                        {
                            JOptionPane.showMessageDialog(null, "Vous avez été connecté avec succès en tant qu'administrateur", "Succes", JOptionPane.INFORMATION_MESSAGE);
                            AdminLogger();
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Vous avez été connecté avec succès en tant que vendeur", "Succes", JOptionPane.INFORMATION_MESSAGE);
                            VendeurLogger();
                        }
                    }

                    //System.out.println(instanceGarage.EmpLogger);
                }
                else throw new Exception("Cet utilisateur n'existe pas");
            }
        }
        catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onLogout()
    {
        instanceGarage.EmpLogger = null;

        //System.out.println(instanceGarage.EmpLogger);

        PasLogger();
    }

    private void onResetMotDePasse()
    {
        instanceGarage.EmpLogger.setMotDePasse("");
        instanceGarage.prop.setProperty(instanceGarage.EmpLogger.getLogin(), "");

        JOptionPane.showMessageDialog(null, "votre mot de passe a été réinitialisé", "Mot de Passe", JOptionPane.INFORMATION_MESSAGE);
    }

    //----------------------------------------------------------------------------------
    //---------		Fonctions pour les modeles/options et aussi pour le projet en cours
    //----------------------------------------------------------------------------------

    private void onNouveauModele()
    {
        JDialogNouveauModele dialog = new JDialogNouveauModele();
        dialog.pack();
        dialog.setVisible(true);
        if (dialog.isOk())
        {
            System.out.println("Choix : " + dialog.getNom() + "-" + dialog.getMoteur() + "-" + dialog.getPuissance() + "-" + dialog.getPrixDeBase());
            Modele modele = new Modele(dialog.getNom(),dialog.getPuissance(),dialog.getMoteur(),dialog.getPrixDeBase(), new ArrayList<>(), dialog.getImage());
            viewGarage.comboBoxModelesDisponibles.addItem(modele);
        }
        dialog.dispose();
    }

    private void onNouveauOption()
    {
        JDialogNouvelleOption dialog = new JDialogNouvelleOption();
        dialog.setVisible(true);
        if (dialog.isOk())
        {
            System.out.println("Code = " + dialog.getCode());
            System.out.println("Intitule = " + dialog.getIntitule());
            System.out.println("Prix = " + dialog.getPrix());
            Option option = new Option(dialog.getCode(),dialog.getIntitule(),dialog.getPrix());
            viewGarage.comboBoxOptionsDisponibles.addItem(option);
        }
        dialog.dispose();
    }

    private void onChoisirModele()
    {
        Modele modele = (Modele) viewGarage.comboBoxModelesDisponibles.getSelectedItem();

        if (modele == null) return;

        viewGarage.textFieldModele.setText(modele.getNom());
        viewGarage.textFieldPuissance.setText(String.valueOf(modele.getPuissance()));
        viewGarage.textFieldPrixDeBase.setText(String.valueOf(modele.getPrixDeBase()));
        if (modele.getMoteur().equals("Essence")) viewGarage.radioButtonEssence.setSelected(true);
        if (modele.getMoteur().equals("Diesel")) viewGarage.radioButtonDiesel.setSelected(true);
        if (modele.getMoteur().equals("Electrique")) viewGarage.radioButtonElectrique.setSelected(true);
        if (modele.getMoteur().equals("Hybride")) viewGarage.radioButtonHybride.setSelected(true);

        String filepath;

        if(modele.getImage().startsWith("C:"))
        {
            filepath = modele.getImage();
        } else filepath = "src/GUI/images/"+modele.getImage();

        ImageIcon imageIcon = new ImageIcon(filepath);
        viewGarage.labelImage.setIcon(imageIcon);

        instanceGarage.getProjetEnCours().setModele(modele);

        DefaultTableModel model = (DefaultTableModel) viewGarage.tableOptionsChoisies.getModel();
        model.setRowCount(0);

        float prixtotal = modele.getPrixDeBase();

        for (Option opt: instanceGarage.getProjetEnCours().getModele().getOptions()) {
            Vector ligne = new Vector();
            ligne.add(opt.getCode());
            ligne.add(opt.getIntitule());
            ligne.add(opt.getPrix());
            model.addRow(ligne);

            prixtotal += opt.getPrix();
        }

        viewGarage.textFieldPrixAvecOptions.setText(String.valueOf(prixtotal));

        //System.out.println(instanceGarage.getProjetEnCours().toString());
        //System.out.println(instanceGarage.getProjetEnCours().getModele().getOptions());
    }

    private void onChoisirOption()
    {
        try {
            if(instanceGarage.getProjetEnCours().getModele().getNom().isEmpty()) throw new Exception("veuillez sélectionner un modèle");

            Option option = (Option) viewGarage.comboBoxOptionsDisponibles.getSelectedItem();
            if (option == null) return;

            DefaultTableModel model = (DefaultTableModel) viewGarage.tableOptionsChoisies.getModel();
            Vector ligne = new Vector();
            ligne.add(option.getCode());
            ligne.add(option.getIntitule());
            ligne.add(option.getPrix());
            model.addRow(ligne);

            instanceGarage.getProjetEnCours().getModele().ajouterOption(option);

            float prixtotal = Float.parseFloat(viewGarage.textFieldPrixAvecOptions.getText());
            prixtotal += option.getPrix();
            viewGarage.textFieldPrixAvecOptions.setText(String.valueOf(prixtotal));

            //System.out.println(instanceGarage.getProjetEnCours().getModele().getOptions());
        }
        catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onNouveauProjet()
    {
        DefaultTableModel model = (DefaultTableModel) viewGarage.tableOptionsChoisies.getModel();
        model.setRowCount(0);

        viewGarage.textFieldModele.setText("");
        viewGarage.textFieldPuissance.setText("");
        viewGarage.textFieldPrixDeBase.setText("");
        viewGarage.radioButtonEssence.setSelected(true);
        ImageIcon imageIcon = new ImageIcon("src/GUI/images/208.jpg");
        viewGarage.labelImage.setIcon(imageIcon);
        viewGarage.textFieldPrixAvecOptions.setText("");
        viewGarage.textFieldNomProjet.setText("");

        instanceGarage.getProjetEnCours().setModele(new Modele());
        instanceGarage.getProjetEnCours().setNom("");

        viewGarage.comboBoxModelesDisponibles.setSelectedItem(viewGarage.comboBoxModelesDisponibles.getItemAt(0));
        viewGarage.comboBoxOptionsDisponibles.setSelectedItem(viewGarage.comboBoxOptionsDisponibles.getItemAt(0));

        //System.out.println(instanceGarage.getProjetEnCours().getModele().getOptions());
    }

    private void onSupprimerOption()
    {
        int index = viewGarage.tableOptionsChoisies.getSelectedRow();

        try {
            if(index == -1) throw new Exception("veuillez choisir une option pour supprimer");
            //System.out.println("Avant:  "+instanceGarage.getProjetEnCours().getModele().getOptions());

            instanceGarage.getProjetEnCours().getModele().retireOption(instanceGarage.getProjetEnCours().getModele().getOptions().get(index));

            DefaultTableModel model = (DefaultTableModel) viewGarage.tableOptionsChoisies.getModel();
            model.removeRow(index);

            float prixtotal = instanceGarage.getProjetEnCours().getModele().getPrixDeBase();

            for (Option opt: instanceGarage.getProjetEnCours().getModele().getOptions()) {
                prixtotal += opt.getPrix();
            }

            viewGarage.textFieldPrixAvecOptions.setText(String.valueOf(prixtotal));

            //System.out.println("Apres:  "+instanceGarage.getProjetEnCours().getModele().getOptions());
        }
        catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onAccorderReduction()
    {
        int index = viewGarage.tableOptionsChoisies.getSelectedRow();

        try {
            if(index == -1) throw new Exception("veuillez choisir une option pour reduction");

            if(instanceGarage.getProjetEnCours().getModele().getOptions().get(index).reduction() == -1) throw new Exception("limite de réduction atteinte");

            DefaultTableModel model = (DefaultTableModel) viewGarage.tableOptionsChoisies.getModel();
            model.setValueAt(instanceGarage.getProjetEnCours().getModele().getOptions().get(index).getPrix(),index,2);

            float prixtotal = Float.parseFloat(viewGarage.textFieldPrixAvecOptions.getText());
            prixtotal -= 50.0f;
            viewGarage.textFieldPrixAvecOptions.setText(String.valueOf(prixtotal));

            //System.out.println(instanceGarage.getProjetEnCours().getModele().getOptions());
        }
        catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    //---------------------------------------------------------------------------------------------------
    //---------		Flux Save/Load pour le projet (les fonctions du flux se trouve dans le garage modele)
    //---------------------------------------------------------------------------------------------------

    private void onEnregistrerProjet()
    {
        try {
            if(viewGarage.textFieldNomProjet.getText().isEmpty()) throw new Exception("veuillez entrer un nom pour votre projet");

            String ProjetNom = viewGarage.textFieldNomProjet.getText();
            instanceGarage.getProjetEnCours().setNom(ProjetNom);
            instanceGarage.getProjetEnCours().getModele().setNom( viewGarage.textFieldModele.getText());
            instanceGarage.getProjetEnCours().getModele().setPuissance(Integer.parseInt(viewGarage.textFieldPuissance.getText()));
            instanceGarage.getProjetEnCours().getModele().setPrixDeBase(Float.parseFloat(viewGarage.textFieldPrixDeBase.getText()));

            if(viewGarage.radioButtonEssence.isSelected()) instanceGarage.getProjetEnCours().getModele().setMoteur("Essence");
            if(viewGarage.radioButtonDiesel.isSelected()) instanceGarage.getProjetEnCours().getModele().setMoteur("Diesel");
            if(viewGarage.radioButtonElectrique.isSelected()) instanceGarage.getProjetEnCours().getModele().setMoteur("Electrique");
            if(viewGarage.radioButtonHybride.isSelected()) instanceGarage.getProjetEnCours().getModele().setMoteur("Hybride");

            String FichierNom = "Fichiers/" + ProjetNom + ".car";
            instanceGarage.SaveProjetEnCours(FichierNom);

            //System.out.println(instanceGarage.getProjetEnCours().toString());
        }
        catch (FileNotFoundException e)
        {
            JOptionPane.showMessageDialog(null, "Fichier non trouve...", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        catch (IOException e)
        {
            System.out.println("Erreur IO");
        }
        catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onOuvrirProjet()
    {
        try {
            if(viewGarage.textFieldNomProjet.getText().isEmpty()) throw new Exception("veuillez entrer un nom pour votre projet");

            String ProjetNom = viewGarage.textFieldNomProjet.getText();

            String FichierNom = "Fichiers/" + ProjetNom + ".car";
            instanceGarage.LoadProjetEnCours(FichierNom);

            Modele modele = instanceGarage.getProjetEnCours().getModele();

            if (modele.getNom().isEmpty()) return;

            viewGarage.textFieldModele.setText(modele.getNom());
            viewGarage.textFieldPuissance.setText(String.valueOf(modele.getPuissance()));
            viewGarage.textFieldPrixDeBase.setText(String.valueOf(modele.getPrixDeBase()));
            if (modele.getMoteur().equals("Essence")) viewGarage.radioButtonEssence.setSelected(true);
            if (modele.getMoteur().equals("Diesel")) viewGarage.radioButtonDiesel.setSelected(true);
            if (modele.getMoteur().equals("Electrique")) viewGarage.radioButtonElectrique.setSelected(true);
            if (modele.getMoteur().equals("Hybride")) viewGarage.radioButtonHybride.setSelected(true);

            String filepath;

            if(modele.getImage().startsWith("C:"))
            {
                filepath = modele.getImage();
            } else filepath = "src/GUI/images/"+modele.getImage();

            ImageIcon imageIcon = new ImageIcon(filepath);
            viewGarage.labelImage.setIcon(imageIcon);

            DefaultTableModel model = (DefaultTableModel) viewGarage.tableOptionsChoisies.getModel();
            model.setRowCount(0);

            float prixtotal = modele.getPrixDeBase();

            for (Option opt: modele.getOptions()) {
                Vector ligne = new Vector();
                ligne.add(opt.getCode());
                ligne.add(opt.getIntitule());
                ligne.add(opt.getPrix());
                model.addRow(ligne);

                prixtotal += opt.getPrix();
            }

            viewGarage.textFieldPrixAvecOptions.setText(String.valueOf(prixtotal));

            //System.out.println(instanceGarage.getProjetEnCours().toString());
        }
        catch (FileNotFoundException e)
        {
            JOptionPane.showMessageDialog(null, "Fichier non trouve...", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        catch (IOException e)
        {
            System.out.println("Erreur IO");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Erreur ! Classe non trouvee...");
        }
        catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    //----------------------------------------------------------------------------------
    //---------		Fonctions pour les employes
    //----------------------------------------------------------------------------------

    private void onNouveauEmploye()
    {
        JDialogNouveauEmploye dialog = new JDialogNouveauEmploye();
        dialog.pack();
        dialog.setVisible(true);
        if (dialog.isOk())
        {
            System.out.println("Employe: " + dialog.getNom() + " - " + dialog.getPrenom() + " - " + dialog.getLogin() + " - " + dialog.getFonction());

            try {
                for (Employe emp:instanceGarage.getEmployes()) {
                    if(emp.getLogin().equals(dialog.getLogin())) throw new Exception("ce login existe déjà !");
                }

                instanceGarage.ajouteEmploye(dialog.getNom(),dialog.getPrenom(),dialog.getLogin(),dialog.getFonction());
                instanceGarage.prop.setProperty(dialog.getNom(), "");

                JTable tableEmployes =  (JTable) viewGarage.jScrollPaneEmployes.getViewport().getView();
                DefaultTableModel model = (DefaultTableModel) tableEmployes.getModel();

                Vector ligne = new Vector();
                ligne.add(Integer.valueOf(Personne.numCourant));
                ligne.add(dialog.getNom());
                ligne.add(dialog.getPrenom());
                ligne.add(dialog.getFonction());
                model.addRow(ligne);

                viewGarage.jScrollPaneEmployes.setViewportView(tableEmployes);
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur Ajout client", JOptionPane.ERROR_MESSAGE);
            }

            //System.out.println(instanceGarage.getEmployes());
        }
        dialog.dispose();
    }

    private void onSupprimerEmpParNum()
    {
        try {
            JDialogSupprimerParNumero dialog = new JDialogSupprimerParNumero();
            dialog.pack();
            dialog.setTitle("Suppresion Employe");
            dialog.setVisible(true);
            if (dialog.isOk())
            {
                System.out.println("Numero Employe a Supprimer: " + dialog.getNumero());

                System.out.println("Avant : " + instanceGarage.getEmployes());

                int index = instanceGarage.supprimeEmployeParNumero(dialog.getNumero());

                if(index == -3) throw new Exception("il n'y a actuellement aucun employé");
                if(index == -1) throw new Exception("L'employé avec ce numéro n'existe pas");
                if(index == -2) throw new Exception("cet employé existe dans la table des contrats et ne peut pas être supprimé");

                JTable tableEmployes =  (JTable) viewGarage.jScrollPaneEmployes.getViewport().getView();
                DefaultTableModel model = (DefaultTableModel) tableEmployes.getModel();
                model.removeRow(index);

                System.out.println("Apres : " + instanceGarage.getEmployes());

                instanceGarage.prop.list(System.out);
            }
            dialog.dispose();
        }
        catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur Suppresion", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onSupprimerEmpSelect()
    {
        try {
            JTable tableEmployes =  (JTable) viewGarage.jScrollPaneEmployes.getViewport().getView();

            if(tableEmployes.getSelectedRow() == -1) throw new Exception("veuillez sélectionner un employé à supprimer");

            System.out.println("Avant : " + instanceGarage.getEmployes());

            if(instanceGarage.VerifieContratsEmploye(tableEmployes.getSelectedRow()) == 1)
                throw new Exception("cet employé existe dans la table des contrats et ne peut pas être supprimé");

            instanceGarage.supprimeEmployeParIndice(tableEmployes.getSelectedRow());

            DefaultTableModel model = (DefaultTableModel) tableEmployes.getModel();
            model.removeRow(tableEmployes.getSelectedRow());

            System.out.println("Apres : " + instanceGarage.getEmployes());

            instanceGarage.prop.list(System.out);
        }
        catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur Suppresion", JOptionPane.ERROR_MESSAGE);
        }
    }

    //----------------------------------------------------------------------------------
    //---------		Fonctions pour les clients
    //----------------------------------------------------------------------------------

    private void onNouveauClient()
    {
        JDialogNouveauClient dialog = new JDialogNouveauClient();
        dialog.pack();
        dialog.setVisible(true);
        if (dialog.isOk())
        {
            System.out.println("Client : " + dialog.getNom() + " - " + dialog.getPrenom() + " - " + dialog.getGsm());

            instanceGarage.ajouteClient(dialog.getNom(),dialog.getPrenom(),dialog.getGsm());

            JTable tableClients =  (JTable) viewGarage.jScrollPaneClients.getViewport().getView();
            DefaultTableModel model = (DefaultTableModel) tableClients.getModel();

            Vector ligne = new Vector();
            ligne.add(Integer.valueOf(Personne.numCourant));
            ligne.add(dialog.getNom());
            ligne.add(dialog.getPrenom());
            ligne.add(dialog.getGsm());
            model.addRow(ligne);

            viewGarage.jScrollPaneClients.setViewportView(tableClients);

            //System.out.println(instanceGarage.getClients());
        }
        dialog.dispose();
    }

    private void onSupprimerCliParNum()
    {
        try {
            JDialogSupprimerParNumero dialog = new JDialogSupprimerParNumero();
            dialog.pack();
            dialog.setTitle("Suppresion Client");
            dialog.setVisible(true);
            if (dialog.isOk())
            {
                System.out.println("Numero Client a Supprimer: " + dialog.getNumero());

                System.out.println("Avant : " + instanceGarage.getClients());

                int index = instanceGarage.supprimeClientParNumero(dialog.getNumero());

                if(index == -3) throw new Exception("il n'y a actuellement aucun client");
                if(index == -1) throw new Exception("client avec ce numéro n'existe pas");
                if(index == -2) throw new Exception("cet client existe dans la table des contrats et ne peut pas être supprimé");

                JTable tableClients =  (JTable) viewGarage.jScrollPaneClients.getViewport().getView();
                DefaultTableModel model = (DefaultTableModel) tableClients.getModel();
                model.removeRow(index);

                System.out.println("Apres : " + instanceGarage.getClients());
            }
            dialog.dispose();
        }
        catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur Suppresion", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onSupprimerCliSelect()
    {
        try {
            JTable tableClients =  (JTable) viewGarage.jScrollPaneClients.getViewport().getView();

            if(tableClients.getSelectedRow() == -1) throw new Exception("veuillez sélectionner un client à supprimer");

            System.out.println("Avant : " + instanceGarage.getClients());

            if(instanceGarage.VerifieContratsClient(tableClients.getSelectedRow()) == 1)
                throw new Exception("ce client ne peut pas être supprimé car il existe dans la table des contrats");

            instanceGarage.supprimeClientParIndice(tableClients.getSelectedRow());

            DefaultTableModel model = (DefaultTableModel) tableClients.getModel();
            model.removeRow(tableClients.getSelectedRow());

            System.out.println("Apres : " + instanceGarage.getClients());
        }
        catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur Suppresion", JOptionPane.ERROR_MESSAGE);
        }
    }


    //----------------------------------------------------------------------------------
    //---------		Les differents etats de logger
    //----------------------------------------------------------------------------------

    private void PasLogger()
    {
        //projet
        viewGarage.textFieldNomProjet.setEnabled(false);
        viewGarage.textFieldModele.setEnabled(false);
        viewGarage.textFieldPuissance.setEnabled(false);
        viewGarage.textFieldPrixDeBase.setEnabled(false);
        viewGarage.radioButtonHybride.setEnabled(false);
        viewGarage.radioButtonDiesel.setEnabled(false);
        viewGarage.radioButtonElectrique.setEnabled(false);
        viewGarage.radioButtonEssence.setEnabled(false);

        viewGarage.buttonSupprimerOption.setEnabled(false);
        viewGarage.buttonAccorderReduction.setEnabled(false);
        viewGarage.textFieldPrixAvecOptions.setEnabled(false);

        viewGarage.buttonOuvrirProjet.setEnabled(false);
        viewGarage.buttonEnregistrerProjet.setEnabled(false);
        viewGarage.buttonNouveauProjet.setEnabled(false);

        //modeles et options
        viewGarage.comboBoxModelesDisponibles.setEnabled(false);
        viewGarage.comboBoxOptionsDisponibles.setEnabled(false);
        viewGarage.buttonChoisirOption.setEnabled(false);
        viewGarage.buttonChoisirModele.setEnabled(false);

        //contrat
        viewGarage.nouveauContratButton.setEnabled(false);
        viewGarage.supprimerContratButton.setEnabled(false);
        viewGarage.visualiserVoitureButton.setEnabled(false);

        //les menus
        viewGarage.menuEmployes.setEnabled(false);
        viewGarage.menuClients.setEnabled(false);
        viewGarage.menuVoiture.setEnabled(false);

        //menu connexion
        viewGarage.menuItemLogout.setEnabled(false);
        viewGarage.menuItemResetMotDePasse.setEnabled(false);
        viewGarage.menuItemLogin.setEnabled(true);
    }

    private void AdminLogger()
    {
        //menu connexion
        viewGarage.menuItemLogout.setEnabled(true);
        viewGarage.menuItemResetMotDePasse.setEnabled(true);
        viewGarage.menuItemLogin.setEnabled(false);

        //les menus
        viewGarage.menuEmployes.setEnabled(true);

        //contrat
        viewGarage.supprimerContratButton.setEnabled(true);
        viewGarage.visualiserVoitureButton.setEnabled(true);
    }

    private void VendeurLogger()
    {
        //projet
        viewGarage.textFieldNomProjet.setEnabled(true);
        viewGarage.textFieldModele.setEnabled(true);
        viewGarage.textFieldPuissance.setEnabled(true);
        viewGarage.textFieldPrixDeBase.setEnabled(true);
        viewGarage.radioButtonHybride.setEnabled(true);
        viewGarage.radioButtonDiesel.setEnabled(true);
        viewGarage.radioButtonElectrique.setEnabled(true);
        viewGarage.radioButtonEssence.setEnabled(true);

        viewGarage.buttonSupprimerOption.setEnabled(true);
        viewGarage.buttonAccorderReduction.setEnabled(true);
        viewGarage.textFieldPrixAvecOptions.setEnabled(true);

        viewGarage.buttonOuvrirProjet.setEnabled(true);
        viewGarage.buttonEnregistrerProjet.setEnabled(true);
        viewGarage.buttonNouveauProjet.setEnabled(true);

        //modeles et options
        viewGarage.comboBoxModelesDisponibles.setEnabled(true);
        viewGarage.comboBoxOptionsDisponibles.setEnabled(true);
        viewGarage.buttonChoisirOption.setEnabled(true);
        viewGarage.buttonChoisirModele.setEnabled(true);

        //contrat
        viewGarage.nouveauContratButton.setEnabled(true);
        viewGarage.supprimerContratButton.setEnabled(true);
        viewGarage.visualiserVoitureButton.setEnabled(true);

        //les menus
        viewGarage.menuClients.setEnabled(true);
        viewGarage.menuVoiture.setEnabled(true);

        //menu connexion
        viewGarage.menuItemLogout.setEnabled(true);
        viewGarage.menuItemResetMotDePasse.setEnabled(true);
        viewGarage.menuItemLogin.setEnabled(false);
    }
}
