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
    private JButton nouveauContratButton;
    private JButton supprimerContratButton;
    private JButton visualiserVoitureButton;
    private JTable tableContrats;
    private JScrollPane jScrollPaneEmployes;
    private JScrollPane jScrollPaneClients;
    public JLabel labelImage;
    public JComboBox comboBoxModelesDisponibles;
    private JButton buttonChoisirModele;
    public JComboBox comboBoxOptionsDisponibles;
    private JButton buttonChoisirOption;
    private JScrollPane jScrollPaneOptionsChoisies;
    public JTable tableOptionsChoisies;
    private JTextField textFieldNomProjet;
    public JTextField textFieldModele;
    public JTextField textFieldPuissance;
    public JTextField textFieldPrixDeBase;
    public JRadioButton radioButtonEssence;
    public JRadioButton radioButtonDiesel;
    public JRadioButton radioButtonElectrique;
    public JRadioButton radioButtonHybride;
    private JButton buttonSupprimerOption;
    private JButton buttonAccorderReduction;
    private JTextField textFieldPrixAvecOptions;
    private JButton buttonNouveauProjet;
    private JButton buttonOuvrirProjet;
    private JButton buttonEnregistrerProjet;
    private JMenuItem menuItemQuitter;
    private JMenuItem menuItemLogin;
    private JMenuItem menuItemNouveauModele;
    private JMenuItem menuItemNouvelleOption;
    private JTable tableEmployes;
    private JTable tableClients;

    public JFrameGarage()
    {
        //setSize(800,600);
        setTitle("Application Garage JAVA");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        JMenuItem menuItemLogout = new JMenuItem("Logout");
        menuConnexion.add(menuItemLogout);
        menuConnexion.addSeparator();
        JMenuItem menuItemResetMotDePasse = new JMenuItem("Reset mot de passe");
        menuConnexion.add(menuItemResetMotDePasse);
        menuConnexion.addSeparator();

        menuItemQuitter = new JMenuItem("Quitter");
        menuConnexion.add(menuItemQuitter);

        JMenu menuEmployes = new JMenu("Employés");
        menuBar.add(menuEmployes);
        JMenu menuClients = new JMenu("Clients");
        menuBar.add(menuClients);
        JMenu menuVoiture = new JMenu("Voiture");
        menuBar.add(menuVoiture);
        menuItemNouveauModele = new JMenuItem("Nouveau modèle");
        menuVoiture.add(menuItemNouveauModele);
        menuItemNouvelleOption = new JMenuItem("Nouvelle Option");
        menuVoiture.add(menuItemNouvelleOption);

        // Table des employes
        Object[][] data = new Object[][]{
                {Integer.valueOf(1), "Wagner", "Jean-Marc", "Vendeur"},
                {Integer.valueOf(2), "Charlet", "Christophe", "Administratif"}
        };
        String[] nomsColonnes = { "Num", "Nom", "Prénom", "Fonction"};
        tableEmployes = new JTable(data, nomsColonnes);
        int[] taillesColonnes = {30,60,60,60};
        TableColumn col = null;
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
        Vector ligne = new Vector();
        ligne.add(Integer.valueOf(1));
        ligne.add("Wagner");
        ligne.add("Jean-Marc");
        ligne.add("0478/75.53.36");
        tableModel.addRow(ligne);
        //tableClients.setModel(tableModel);
        jScrollPaneClients.setViewportView(tableClients);

        // Table des contrats
        tableModel = (DefaultTableModel) tableContrats.getModel();
        String[] nomsColonnes3 = { "Num", "Vendeur", "Client", "Voiture"};
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
    }

    public void setControleur(Controleur c)
    {
        menuItemQuitter.addActionListener(c);
        menuItemLogin.addActionListener(c);

        menuItemNouveauModele.addActionListener(c);
        menuItemNouvelleOption.addActionListener(c);

        buttonChoisirModele.addActionListener(c);
        buttonChoisirOption.addActionListener(c);
        buttonNouveauProjet.addActionListener(c);

        this.addWindowListener(c);
    }

    public static void main(String[] args) {
        //FlatLightLaf.setup();
        JFrameGarage frame = new JFrameGarage();
        frame.setVisible(true);
    }
}
