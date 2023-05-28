package Garage;

import java.io.Serializable;

public class Client extends Intervenant implements Serializable {

    private String gsm;

    public Client(){
        super();
        this.gsm = "";
    }

    public Client(String nom, String prenom, int numero, String gsm) {
        super(nom, prenom, numero);
        this.gsm = gsm;
    }

    public String getGsm() {
        return gsm;
    }

    public void setGsm(String gsm) {
        this.gsm = gsm;
    }

    @Override
    public String toString() {
        return  super.toString() + "Client{" +
                "gsm='" + gsm + '\'' +
                '}';
    }
}
