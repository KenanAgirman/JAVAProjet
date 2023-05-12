package Garage;

public class Employe extends Intervenant{
    private String Login;
    private String MotDePasse;
    private String Fonction;
    public static String ADMINISTRATIF = "ADMINISTRATIF";
    public static String VENDEUR = "VENDEUR";

    public Employe()
    {
        this.Login = " ";
        this.Fonction = " ";
        this.MotDePasse = " ";
    }
    public Employe(String nom, String prenom, int numCourant, String login, String fonction) {
        super();
        Login = "login";
        MotDePasse = "motDePasse";
        Fonction = "fonction";
    }

    public Employe(String Nom, String Prenom, int Numero, String login, String motDePasse, String fonction){
        super(Nom, Prenom, Numero);
        Login = login;
        MotDePasse = " ";
        Fonction = fonction;

    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getFonction() {
        return Fonction;
    }

    public void setFonction(String fonction) {
        if(fonction==ADMINISTRATIF)
        {
            Fonction = fonction;
        }
        else
        {
            if(fonction==VENDEUR)
            {
                Fonction = fonction;
            }
        }
    }

    public String getMotDePasse() {
        return MotDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        MotDePasse = motDePasse;
    }

    @Override
    public String toString() {
        return "Employe{" +
                "Login='" + Login + '\'' +
                ", MotDePasse='" + MotDePasse + '\'' +
                ", Fonction='" + Fonction + '\'' +
                '}';
    }
}
