package Garage;
public class Option {

    private String code;
    private String intitule;
    private float prix;
    public Option() {
        code = "ABCD";
        intitule = "Toit ouvrant";
        prix = 5;
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

    public void setCode(String code) {
        this.code = code;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String tostring()
    {
        return "[Code: " + code + ", Intitule: " + intitule + ", Prix: " + prix + "]";
    }
    public boolean equals(Option o)
    {
        if (code.equals(o.code) && intitule == o.intitule && prix == o.prix)
            return true;
        else
            return false;
    }
}
