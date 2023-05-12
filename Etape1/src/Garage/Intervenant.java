package Garage;

public abstract class Intervenant extends Personne implements EstIdentifiee{
    private int Numero;

    public Intervenant() {
        super();
        Numero = 1;
    }

    public Intervenant(String nom, String prenom,int numero) {
        super(nom, prenom);
        this.Numero = numero;
    }

    public int getNumero() {
        return Numero;
    }

    public void setNumero(int numero) {
        Numero = numero;
    }

    @Override
    public String toString() {
        return "Intervenant{" +
                "Numero=" + Numero +
                '}';
    }
}
