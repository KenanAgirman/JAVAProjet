package GUI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JDialogNouveauModele extends JDialog
{
    private JPanel contentPane;
    private JButton buttonCreer;
    private JButton buttonAnnuler;
    private JTextField textFieldNom;
    private JSpinner spinnerPuissance;
    private JComboBox comboBoxMoteur;
    private JTextField textFieldPrixDeBase;
    private JButton BrowseImage;

    private String nom;
    private int puissance;
    private String moteur;
    private float prixDeBase;
    private String image = "";
    private boolean ok;

    public JDialogNouveauModele()
    {
        super();
        setContentPane(contentPane);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setModal(true);
        setTitle("Nouveau Modèle...");

        comboBoxMoteur.addItem("Essence");
        comboBoxMoteur.addItem("Diesel");
        comboBoxMoteur.addItem("Hybride");
        comboBoxMoteur.addItem("Electrique");

        SpinnerModel model = new SpinnerNumberModel(100,30,300,5);
        spinnerPuissance.setModel(model);

        BrowseImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG and JPG Images", "png", "jpg");
                fileChooser.setFileFilter(filter);
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    String selectedFilePath = fileChooser.getSelectedFile().getAbsolutePath();
                    //System.out.println("Selected file: " + selectedFilePath);

                    image = selectedFilePath;
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
        buttonCreer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if(textFieldNom.getText().isEmpty()) throw new Exception("veuillez entrer une valeur valide pour le nom");
                    else nom = textFieldNom.getText();
                    puissance = (int) spinnerPuissance.getValue();
                    moteur = (String) comboBoxMoteur.getSelectedItem();
                    if(textFieldPrixDeBase.getText().isEmpty()) throw new Exception("veuillez entrer une valeur valide pour le prix");
                    else prixDeBase = Float.parseFloat(textFieldPrixDeBase.getText());
                    if(image.isEmpty()) throw new Exception("veuillez sélectionner une image");
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

    public static void main(String[] args) {
        JDialogNouveauModele dialog = new JDialogNouveauModele();
        dialog.pack();
        dialog.setVisible(true);
        if (dialog.isOk())
        {
            System.out.println("Choix : " + dialog.getNom() + "-" + dialog.getMoteur() + "-" + dialog.getPuissance() + "-" + dialog.getPrixDeBase());
        }
        dialog.dispose();
    }

    public String getNom() {
        return nom;
    }

    public int getPuissance() {
        return puissance;
    }

    public String getMoteur() {
        return moteur;
    }

    public float getPrixDeBase() {
        return prixDeBase;
    }
    public String getImage() {
        return image;
    }

    public boolean isOk() {
        return ok;
    }
}
