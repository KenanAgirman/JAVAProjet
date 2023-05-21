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
import java.util.Vector;

public class Controleur extends WindowAdapter implements ActionListener
{
    private Garage instanceGarage;
    private JFrameGarage viewGarage;

    public Controleur(Garage instanceGarage, JFrameGarage viewGarage) {
        this.instanceGarage = instanceGarage;
        this.viewGarage = viewGarage;

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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Quitter")) {
            onQuitter();
        }
        if(e.getActionCommand().equals("Login")) {
            onLogin();
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
        if(e.getActionCommand().equals("Nouveau Client")){
            onNouveauClient();
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
            System.exit(0);
    }

    private void onLogin()
    {
        JDialogLogin dialog = new JDialogLogin(null,true,"Entrée en session...");
        dialog.setVisible(true);
    }

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

    private void onNouveauEmploye()
    {
        JDialogNouveauEmploye dialog = new JDialogNouveauEmploye();
        dialog.pack();
        dialog.setVisible(true);
        if (dialog.isOk())
        {
            System.out.println("Employe: " + dialog.getNom() + " - " + dialog.getPrenom() + " - " + dialog.getLogin() + " - " + dialog.getFonction());
        }
        dialog.dispose();
    }

    private void onNouveauClient()
    {
        JDialogNouveauClient dialog = new JDialogNouveauClient();
        dialog.pack();
        dialog.setVisible(true);
        if (dialog.isOk())
        {
            System.out.println("Client : " + dialog.getNom() + " - " + dialog.getPrenom() + " - " + dialog.getGsm());
        }
        dialog.dispose();
    }
}
