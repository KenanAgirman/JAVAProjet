package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JDialogNouvelleOption extends JDialog
{
    private JTextField textFieldIntitule;
    private JTextField textFieldCode;
    private JTextField textFieldPrix;
    private JButton buttonCreerOption;
    private JButton buttonAnnuler;
    private JPanel mainPanel;

    private String code;
    private String intitule;
    private float prix;
    private boolean ok;

    public JDialogNouvelleOption()
    {
        super();
        setContentPane(mainPanel);
        setTitle("Nouvelle Option...");
        pack();
        setModal(true);
        ok = false;
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        buttonAnnuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        buttonCreerOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(textFieldCode.getText().isEmpty()) throw new Exception("veuillez entrer une valeur pour le code");
                    else if (textFieldCode.getText().length() != 4) throw new Exception("la longueur minimale et maximale du code est de 4");
                    else code = textFieldCode.getText();

                    if (textFieldIntitule.getText().isEmpty()) throw new Exception("veuillez entrer une valeur pour l'intitule'");
                    else intitule = textFieldIntitule.getText();

                    if(textFieldPrix.getText().isEmpty()) throw new Exception("veuillez entrer une valeur valide pour le prix");
                    else prix = Float.parseFloat(textFieldPrix.getText());
                    ok = true;
                    setVisible(false);
                }
                catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(null, "Veuillez entrer un nombre", "Erreur Float", JOptionPane.ERROR_MESSAGE);
                }
                catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public String getCode() {
        return code;
    }

    public String getIntitule() {
        return intitule;
    }

    public float getPrix() {
        return prix;
    }

    public boolean isOk() {
        return ok;
    }

    public static void main(String[] args) {
        JDialogNouvelleOption dialog = new JDialogNouvelleOption();
        dialog.setVisible(true);
        if (dialog.isOk())
        {
            System.out.println("Code = " + dialog.getCode());
            System.out.println("Intitule = " + dialog.getIntitule());
            System.out.println("Prix = " + dialog.getPrix());
        }
        dialog.dispose();
    }
}
