package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JDialogNouveauClient extends JDialog {
    private JPanel contentPane;
    private JButton ButtonCreer;
    private JButton ButtonAnnuler;
    private JTextField textFieldNom;
    private JTextField textFieldPrenom;
    private JTextField textFieldGSM;

    private String nom;
    private String prenom;
    private String gsm;
    private boolean ok;

    public JDialogNouveauClient()
    {
        super();
        setContentPane(contentPane);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setModal(true);
        getRootPane().setDefaultButton(ButtonCreer);
        setTitle("Nouveau Client...");

        ButtonAnnuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ok = false;
                setVisible(false);
            }
        });

        ButtonCreer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(textFieldNom.getText().isEmpty()) throw new Exception("veuillez entrer une valeur valide pour le nom");
                    else nom = textFieldNom.getText();

                    if(textFieldPrenom.getText().isEmpty()) throw new Exception("veuillez entrer une valeur valide pour le prenom");
                    else prenom = textFieldPrenom.getText();

                    if(textFieldGSM.getText().isEmpty()) throw new Exception("veuillez entrer une valeur valide pour le gsm");
                    else gsm = textFieldGSM.getText();

                    ok = true;
                    setVisible(false);
                }
                catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur Client", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        JDialogNouveauClient dialog = new JDialogNouveauClient();
        dialog.pack();
        dialog.setVisible(true);
        if (dialog.isOk())
        {
            System.out.println("Client : " + dialog.getNom() + " - " + dialog.getPrenom() + " - " + dialog.getGsm());
        }
        dialog.dispose();
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getGsm() {
        return gsm;
    }

    public boolean isOk() {
        return ok;
    }
}
