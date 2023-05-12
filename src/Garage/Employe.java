package Garage;

public class Employe extends Intervenant{
    private String login;
    private String motDePasse;
    private String fonction;

    public Employe(){
        super();
        this.login = "";
        this.motDePasse = "";
        this.fonction = "";
    }


    public Employe(String nom, String prenom, int numero, String login, String fonction) {
        super(nom, prenom, numero);
        this.login = login;
        this.motDePasse = "";
        this.fonction = fonction;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    @Override
    public String toString() {
        return  super.toString() +"Employe{" +
                "login='" + login + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", fonction='" + fonction + '\'' +
                '}';
    }
}
