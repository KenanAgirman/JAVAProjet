package Controller;

import GUI.JDialogLogin;
import GUI.JDialogNouveauModele;
import GUI.JDialogNouvelleOption;
import GUI.JFrameGarage;
import Modele.Garage;
import Garage.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
        this.instanceGarage.importeOptions("Options.csv");
        this.instanceGarage.importeModeles("Modeles.csv");

        //les mettre dans le interface (utilisation du foreach de java)
        //pour le moment cela cause des probleme avec le interface parce qu'il prennent trop d'espace,
        // mais ca marche, il faut juste modifier l'interface un peut
        for (Option opt: this.instanceGarage.getOptions()) {
            //this.viewGarage.comboBoxOptionsDisponibles.addItem(opt);
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
            Modele modele = new Modele(dialog.getNom(),dialog.getPuissance(),dialog.getMoteur(),dialog.getPrixDeBase(), new ArrayList<>(), "208.jpg");
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
    }

    private void onChoisirOption()
    {
        Option option = (Option) viewGarage.comboBoxOptionsDisponibles.getSelectedItem();
        if (option == null) return;
        DefaultTableModel model = (DefaultTableModel) viewGarage.tableOptionsChoisies.getModel();
        Vector ligne = new Vector();
        ligne.add(option.getCode());
        ligne.add(option.getIntitule());
        ligne.add(option.getPrix());
        model.addRow(ligne);
    }
}
