package GUI;

import Controller.Controleur;
import Garage.Modele;
import Garage.Option;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.ButtonGroup;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class JFrameGarage extends JFrame
{
    private JPanel mainPanel;
    public JButton nouveauContratButton;
    public JButton supprimerContratButton;
    public JButton visualiserVoitureButton;
    public JTable tableContrats;
    public JScrollPane jScrollPaneEmployes;
    public JScrollPane jScrollPaneClients;
    public JLabel labelImage;
    public JComboBox comboBoxModelesDisponibles;
    public JButton buttonChoisirModele;
    public JComboBox comboBoxOptionsDisponibles;
    public JButton buttonChoisirOption;
    private JScrollPane jScrollPaneOptionsChoisies;
    public JTable tableOptionsChoisies;
    public JTextField textFieldNomProjet;
    public JTextField textFieldModele;
    public JTextField textFieldPuissance;
    public JTextField textFieldPrixDeBase;
    public JRadioButton radioButtonEssence;
    public JRadioButton radioButtonDiesel;
    public JRadioButton radioButtonElectrique;
    public JRadioButton radioButtonHybride;
    public JButton buttonSupprimerOption;
    public JButton buttonAccorderReduction;
    public JTextField textFieldPrixAvecOptions;
    public JButton buttonNouveauProjet;
    public JButton buttonOuvrirProjet;
    public JButton buttonEnregistrerProjet;

    public JMenu menuEmployes;
    public JMenu menuClients;
    public JMenu menuVoiture;
    private JMenuItem menuItemQuitter;
    public JMenuItem menuItemLogin;
    public JMenuItem menuItemLogout;
    public JMenuItem menuItemResetMotDePasse;
    private JMenuItem menuItemNouveauModele;
    private JMenuItem menuItemNouvelleOption;
    private JMenuItem menuItemNouveauEmploye;
    private JMenuItem menuItemSupprimerEmployeParNum;
    private JMenuItem menuItemSupprimerEmployeParSelect;
    private JMenuItem menuItemNouveauClient;
    private JMenuItem menuItemSupprimerClientParNum;
    private JMenuItem menuItemSupprimerClientParSelect;

    public JTable tableEmployes;
    public JTable tableClients;

    public JFrameGarage()
    {
        //setSize(800,600);
        setTitle("Application Garage JAVA");
        setContentPane(mainPanel);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButtonDiesel);
        buttonGroup.add(radioButtonElectrique);
        buttonGroup.add(radioButtonEssence);
        buttonGroup.add(radioButtonHybride);
        radioButtonEssence.setSelected(true);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menuConnexion = new JMenu("Connexion");
        menuBar.add(menuConnexion);
        menuItemLogin = new JMenuItem("Login");
        menuConnexion.add(menuItemLogin);
        menuItemLogout = new JMenuItem("Logout");
        menuConnexion.add(menuItemLogout);
        menuConnexion.addSeparator();
        menuItemResetMotDePasse = new JMenuItem("Reset mot de passe");
        menuConnexion.add(menuItemResetMotDePasse);
        menuConnexion.addSeparator();
        menuItemQuitter = new JMenuItem("Quitter");
        menuConnexion.add(menuItemQuitter);

        menuEmployes = new JMenu("Employés");
        menuBar.add(menuEmployes);
        menuItemNouveauEmploye = new JMenuItem("Nouveau Employe");
        menuEmployes.add(menuItemNouveauEmploye);
        menuItemSupprimerEmployeParNum = new JMenuItem("Supprimer employe par numero");
        menuEmployes.add(menuItemSupprimerEmployeParNum);
        menuItemSupprimerEmployeParSelect = new JMenuItem("Supprimer employe selectionner");
        menuEmployes.add(menuItemSupprimerEmployeParSelect);

        menuClients = new JMenu("Clients");
        menuBar.add(menuClients);
        menuItemNouveauClient = new JMenuItem("Nouveau Client");
        menuClients.add(menuItemNouveauClient);
        menuItemSupprimerClientParNum = new JMenuItem("Supprimer client par numero");
        menuClients.add(menuItemSupprimerClientParNum);
        menuItemSupprimerClientParSelect = new JMenuItem("Supprimer client selectionner");
        menuClients.add(menuItemSupprimerClientParSelect);

        menuVoiture = new JMenu("Voiture");
        menuBar.add(menuVoiture);
        menuItemNouveauModele = new JMenuItem("Nouveau modèle");
        menuVoiture.add(menuItemNouveauModele);
        menuItemNouvelleOption = new JMenuItem("Nouvelle Option");
        menuVoiture.add(menuItemNouvelleOption);

        // Table des employes
        // Table des employes
        tableEmployes = new JTable();
        int[] taillesColonnes = {30,60,60,60};
        TableColumn col = null;
        DefaultTableModel tableModelEmp = (DefaultTableModel) tableEmployes.getModel();
        String[] nomsColonnes = { "Num", "Nom", "Prénom", "Fonction"};
        tableModelEmp.setColumnIdentifiers(nomsColonnes);
        for (int i=0; i<taillesColonnes.length; i++)
        {
            col = tableEmployes.getColumnModel().getColumn(i);
            col.setPreferredWidth(taillesColonnes[i]);
        }
        jScrollPaneEmployes.setViewportView(tableEmployes);

        // Table des clients
        tableClients = new JTable();
        DefaultTableModel tableModel = (DefaultTableModel) tableClients.getModel();
        String[] nomsColonnes2 = { "Num", "Nom", "Prénom", "GSM"};
        tableModel.setColumnIdentifiers(nomsColonnes2);
        jScrollPaneClients.setViewportView(tableClients);

        // Table des contrats
        tableModel = (DefaultTableModel) tableContrats.getModel();
        String[] nomsColonnes3 = { "Num", "Vendeur", "Client", "Voiture", "Date"};
        tableModel.setColumnIdentifiers(nomsColonnes3);

        // Table des options
        tableModel = (DefaultTableModel) tableOptionsChoisies.getModel();
        String[] nomsColonnes4 = { "Code", "Prix", "Intitule"};
        tableModel.setColumnIdentifiers(nomsColonnes4);

        pack();
        setLocation((screen.width - this.getSize().width)/2,(screen.height - this.getSize().height)/2);

        buttonChoisirModele.setText("Choisir Modele");
        buttonChoisirOption.setText("Choisir Option");
        buttonNouveauProjet.setText("Nouveau Projet");
        buttonSupprimerOption.setText("Supprimer Option");
        buttonEnregistrerProjet.setText("Enregistrer Projet");
        buttonOuvrirProjet.setText("Ouvrir Projet");

        buttonSupprimerOption.setBackground(new Color(237, 43, 42));
        buttonSupprimerOption.setForeground(Color.white);
        buttonSupprimerOption.setFont(buttonSupprimerOption.getFont().deriveFont(Font.BOLD));
        buttonAccorderReduction.setBackground(new Color(3, 201, 136));
        buttonAccorderReduction.setForeground(Color.white);
        buttonAccorderReduction.setFont(buttonAccorderReduction.getFont().deriveFont(Font.BOLD));

        buttonChoisirModele.setBackground(new Color(32, 82, 149));
        buttonChoisirModele.setForeground(Color.white);
        buttonChoisirModele.setFont(buttonChoisirModele.getFont().deriveFont(Font.BOLD));
        buttonChoisirOption.setBackground(new Color(32, 82, 149));
        buttonChoisirOption.setForeground(Color.white);
        buttonChoisirOption.setFont(buttonChoisirOption.getFont().deriveFont(Font.BOLD));
        buttonNouveauProjet.setBackground(new Color(32, 82, 149));
        buttonNouveauProjet.setForeground(Color.white);
        buttonNouveauProjet.setFont(buttonNouveauProjet.getFont().deriveFont(Font.BOLD));
        buttonEnregistrerProjet.setBackground(new Color(32, 82, 149));
        buttonEnregistrerProjet.setForeground(Color.white);
        buttonEnregistrerProjet.setFont(buttonEnregistrerProjet.getFont().deriveFont(Font.BOLD));
        buttonOuvrirProjet.setBackground(new Color(32, 82, 149));
        buttonOuvrirProjet.setForeground(Color.white);
        buttonOuvrirProjet.setFont(buttonOuvrirProjet.getFont().deriveFont(Font.BOLD));

        visualiserVoitureButton.setBackground(new Color(78, 49, 170));
        visualiserVoitureButton.setForeground(Color.white);
        visualiserVoitureButton.setFont(visualiserVoitureButton.getFont().deriveFont(Font.BOLD));
        supprimerContratButton.setBackground(new Color(78, 49, 170));
        supprimerContratButton.setForeground(Color.white);
        supprimerContratButton.setFont(supprimerContratButton.getFont().deriveFont(Font.BOLD));
        nouveauContratButton.setBackground(new Color(78, 49, 170));
        nouveauContratButton.setForeground(Color.white);
        nouveauContratButton.setFont(nouveauContratButton.getFont().deriveFont(Font.BOLD));
    }

    public void setControleur(Controleur c)
    {
        //connexion
        menuItemQuitter.addActionListener(c);
        menuItemLogin.addActionListener(c);
        menuItemLogout.addActionListener(c);
        menuItemResetMotDePasse.addActionListener(c);

        //voiture
        menuItemNouveauModele.addActionListener(c);
        menuItemNouvelleOption.addActionListener(c);

        //employe
        menuItemNouveauEmploye.addActionListener(c);
        menuItemSupprimerEmployeParNum.addActionListener(c);
        menuItemSupprimerEmployeParSelect.addActionListener(c);

        //client
        menuItemNouveauClient.addActionListener(c);
        menuItemSupprimerClientParNum.addActionListener(c);
        menuItemSupprimerClientParSelect.addActionListener(c);

        //contrat
        nouveauContratButton.addActionListener(c);
        supprimerContratButton.addActionListener(c);
        visualiserVoitureButton.addActionListener(c);

        //les different bouton pour les options/modeles
        buttonChoisirModele.addActionListener(c);
        buttonChoisirOption.addActionListener(c);
        buttonNouveauProjet.addActionListener(c);
        buttonSupprimerOption.addActionListener(c);
        buttonAccorderReduction.addActionListener(c);
        buttonEnregistrerProjet.addActionListener(c);
        buttonOuvrirProjet.addActionListener(c);

        this.addWindowListener(c);
    }

    public static void main(String[] args) {
        //FlatLightLaf.setup();
        JFrameGarage frame = new JFrameGarage();
        frame.setVisible(true);
    }
}
