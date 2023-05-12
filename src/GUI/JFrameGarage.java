package GUI;

import Garage.Modele;
import Garage.Option;
import Modele.Garage;

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
    private JLabel labelImage;
    private JComboBox comboBoxModelesDisponibles;
    private JButton buttonChoisirModele;
    private JComboBox comboBoxOptionsDisponibles;
    private JButton buttonChoisirOption;
    private JScrollPane jScrollPaneOptionsChoisies;
    private JTable tableOptionsChoisies;
    private JTextField textFieldNomProjet;
    private JTextField textFieldModele;
    private JTextField textFieldPuissance;
    private JTextField textFieldPrixDeBase;
    private JRadioButton radioButtonEssence;
    private JRadioButton radioButtonDiesel;
    private JRadioButton radioButtonElectrique;
    private JRadioButton radioButtonHybride;
    private JButton buttonSupprimerOption;
    private JButton buttonAccorderReduction;
    private JTextField textFieldPrixAvecOptions;
    private JButton buttonNouveauProjet;
    private JButton buttonOuvrirProjet;
    private JButton buttonEnregistrerProjet;

    private JTable tableEmployes;

    private JTable tableClients;

    public JFrameGarage()
    {
        //setSize(800,600);
        setTitle("Application Garage JAVA");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        Garage garage;
        garage = Garage.getInstance();

        garage.importeModeles("Modeles.csv");
        garage.importeOptions("Options.csv");

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
        JMenuItem menuItemLogin = new JMenuItem("Login");
        menuConnexion.add(menuItemLogin);
        JMenuItem menuItemLogout = new JMenuItem("Logout");
        menuConnexion.add(menuItemLogout);
        menuConnexion.addSeparator();
        JMenuItem menuItemResetMotDePasse = new JMenuItem("Reset mot de passe");
        menuConnexion.add(menuItemResetMotDePasse);
        menuConnexion.addSeparator();
        JMenuItem menuItemQuitter = new JMenuItem("Quitter");
        menuConnexion.add(menuItemQuitter);

        JMenu menuEmployes = new JMenu("Employés");
        menuBar.add(menuEmployes);
        JMenu menuClients = new JMenu("Clients");
        menuBar.add(menuClients);
        JMenu menuVoiture = new JMenu("Voiture");
        menuBar.add(menuVoiture);
        JMenuItem menuItemNouveauModele = new JMenuItem("Nouveau modèle");
        menuVoiture.add(menuItemNouveauModele);
        JMenuItem menuItemNouvelleOption = new JMenuItem("Nouvelle Option");
        menuVoiture.add(menuItemNouvelleOption);

        menuItemNouveauModele.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JDialogNouveauModele dialog = new JDialogNouveauModele();
                        dialog.pack();
                        dialog.setVisible(true);
                        if (dialog.isOk())
                        {

                        }
                        dialog.dispose();
                    }
                }
        );

        menuItemNouvelleOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialogNouvelleOption dialog = new JDialogNouvelleOption();
                dialog.setVisible(true);
                if (dialog.isOk())
                {
                    System.out.println("Code = " + dialog.getCode());
                    System.out.println("Intitule = " + dialog.getIntitule());
                    System.out.println("Prix = " + dialog.getPrix());
                    Option option = new Option(dialog.getCode(),dialog.getIntitule(),dialog.getPrix());
                    comboBoxOptionsDisponibles.addItem(option);
                }
                dialog.dispose();
            }
        });

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

        menuItemQuitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        menuItemLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialogLogin dialog = new JDialogLogin(null,true,"Entrée en session...");
                dialog.setVisible(true);
            }
        });

        pack();
        setLocation((screen.width - this.getSize().width)/2,(screen.height - this.getSize().height)/2);
        buttonChoisirModele.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Modele modele = (Modele) comboBoxModelesDisponibles.getSelectedItem();
                if (modele == null) return;
                textFieldModele.setText(modele.getNom());
                textFieldPuissance.setText(String.valueOf(modele.getPuissance()));
                textFieldPrixDeBase.setText(String.valueOf(modele.getPrixDeBase()));
                if (modele.getMoteur().equals("Essence")) radioButtonEssence.setSelected(true);
                if (modele.getMoteur().equals("Diesel")) radioButtonDiesel.setSelected(true);
                if (modele.getMoteur().equals("Electrique")) radioButtonElectrique.setSelected(true);
                if (modele.getMoteur().equals("Hybride")) radioButtonHybride.setSelected(true);
            }
        });
        buttonChoisirOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Option option = (Option) comboBoxOptionsDisponibles.getSelectedItem();
                if (option == null) return;
                DefaultTableModel model = (DefaultTableModel) tableOptionsChoisies.getModel();
                Vector ligne = new Vector();
                ligne.add(option.getCode());
                ligne.add(option.getIntitule());
                ligne.add(option.getPrix());
                model.addRow(ligne);
            }
        });
    }

    public static void main(String[] args) {
        //FlatLightLaf.setup();
        JFrameGarage frame = new JFrameGarage();
        frame.setVisible(true);
    }
}
