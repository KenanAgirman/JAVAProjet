package Garage;

public class Client extends Intervenant{

    private String GSM;

    public Client() {
        super();
        this.GSM = " ";
    }

    public Client(String nom, String prenom,int numero,String Gsm) {
        super(nom, prenom,numero);
        this.GSM = Gsm;
    }

    public String getGSM() {
        return GSM;
    }

    public void setGSM(String GSM) {
        this.GSM = GSM;
    }

    @Override
    public String toString() {
        return "Client{" +
                "GSM='" + GSM + '\'' +
                '}';
    }
}
