package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JDialogSupprimerParNumero extends JDialog {
    private JPanel contentPane;
    private JButton ButtonSupprimer;
    private JButton ButtonAnnuler;
    private JTextField textFieldNumero;

    private int numero;
    private boolean ok;

    public JDialogSupprimerParNumero() {
        super();
        setContentPane(contentPane);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setModal(true);
        getRootPane().setDefaultButton(ButtonSupprimer);

        ButtonAnnuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ok = false;
                setVisible(false);
            }
        });

        ButtonSupprimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if(textFieldNumero.getText().isEmpty()) throw new Exception("entrer un nombre, SVP");

                    if(!textFieldNumero.getText().matches("[0-9]+")) throw new Exception("S'il vous plait entrez uniquement des nombres");

                    numero = Integer.parseInt(textFieldNumero.getText());

                    ok = true;
                    setVisible(false);
                }
                catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur Suppression", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        JDialogSupprimerParNumero dialog = new JDialogSupprimerParNumero();
        dialog.pack();
        dialog.setVisible(true);
        if (dialog.isOk())
        {
            System.out.println("Numero Employe a Supprimer: " + dialog.getNumero());
        }
        dialog.dispose();
    }

    public int getNumero() {
        return numero;
    }

    public boolean isOk() {
        return ok;
    }
}
