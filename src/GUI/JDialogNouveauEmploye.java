package GUI;

import javax.swing.*;
import java.awt.event.*;

public class JDialogNouveauEmploye extends JDialog {
    private JPanel contentPane;
    private JButton ButtonAnnuler;
    private JButton ButtonCree;
    private JTextField textFieldNom;
    private JTextField textFieldPrenom;
    private JTextField textFieldLogin;
    private JComboBox comboBoxFonction;

    private String nom;
    private String prenom;
    private String login;
    private String fonction;
    private boolean ok;

    public JDialogNouveauEmploye()
    {
        super();
        setContentPane(contentPane);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setModal(true);
        setTitle("Nouveau Employe...");

        comboBoxFonction.addItem("Administratif");
        comboBoxFonction.addItem("Vendeur");

        ButtonCree.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(textFieldNom.getText().isEmpty()) throw new Exception("veuillez entrer une valeur valide pour le nom");
                    else nom = textFieldNom.getText();

                    if(textFieldPrenom.getText().isEmpty()) throw new Exception("veuillez entrer une valeur valide pour le prenom");
                    else prenom = textFieldPrenom.getText();

                    if(textFieldLogin.getText().isEmpty()) throw new Exception("veuillez entrer une valeur valide pour le login");
                    else login = textFieldLogin.getText();

                    fonction = (String) comboBoxFonction.getSelectedItem();

                    ok = true;
                    setVisible(false);
                }
                catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur Employe", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        ButtonAnnuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ok = false;
                setVisible(false);
            }
        });
    }

    public static void main(String[] args) {
        JDialogNouveauEmploye dialog = new JDialogNouveauEmploye();
        dialog.pack();
        dialog.setVisible(true);
        if (dialog.isOk())
        {
            System.out.println("Employe: " + dialog.getNom() + " - " + dialog.getPrenom() + " - " + dialog.getLogin() + " - " + dialog.getFonction());
        }
        dialog.dispose();
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getLogin() {
        return login;
    }

    public String getFonction() {
        return fonction;
    }

    public boolean isOk() {
        return ok;
    }
}
