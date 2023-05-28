package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class JDialogLogin extends JDialog
{
    private JTextField textFieldLogin;
    private JTextField textFieldMotDePasse;
    private JPanel mainPanel;
    private JButton buttonOk;
    private JButton buttonAnnuler;

    private String login;
    private String motDePasse;
    private boolean ok;

    public JDialogLogin(JFrame parent,boolean modal,String titre)
    {
        super(parent,modal);
        setTitle(titre);
        setContentPane(mainPanel);
        setLocationRelativeTo(null);
        pack();
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - this.getSize().width)/2,(screen.height - this.getSize().height)/2);

        ok = false;
        buttonOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(textFieldLogin.getText().isEmpty()) throw new Exception("veuillez entrer une valeur valide pour le login");
                    else login = textFieldLogin.getText();

                    if(textFieldMotDePasse.getText().isEmpty()) throw new Exception("veuillez entrer une valeur valide pour le mot de passe");
                    else
                    {
                        if(!Pattern.matches("^.{6,}$", textFieldMotDePasse.getText())) throw new Exception("mot de passe doit comporter d'une taille de 6 au moins");
                        else
                        {
                            if(!Pattern.matches("[0-9]{1,}[a-zA-Z]{1,}|[a-zA-Z]{1,}[0-9]{1,}", textFieldMotDePasse.getText())) throw new Exception("le mot de passe doit avoir 1 chiffre et 1 lettre");
                            else motDePasse = textFieldMotDePasse.getText();
                        }
                    }

                    ok = true;
                    setVisible(false);
                }
                catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur Client", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonAnnuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ok = false;
                setVisible(false);
            }
        });
    }
    public static void main(String[] args) {
        JDialogLogin dialogLogin = new JDialogLogin(null,true,"Entrée en session...");
        dialogLogin.setVisible(true);
        if (dialogLogin.isOk())
        {
            System.out.println("Login = " + dialogLogin.getLogin());
            System.out.println("Mot de passe = " + dialogLogin.getMotDePasse());
        }
        dialogLogin.dispose();
    }

    public String getLogin() {
        return login;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public boolean isOk() {
        return ok;
    }
}
