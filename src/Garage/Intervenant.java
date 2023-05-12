package Garage;

abstract class Intervenant extends Personne implements estIdentifiable {
    private int numero;

    public Intervenant(){
        super();
        this.numero = 0;
    }

    public Intervenant(String nom, String prenom, int numero) {
        super(nom, prenom);
        this.numero = numero;
    }

    @Override
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return  super.toString() + "Intervenant{" +
                "numero=" + numero +
                '}';
    }
}
