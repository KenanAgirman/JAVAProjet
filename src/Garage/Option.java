package Garage;

import java.io.Serializable;

public class Option implements Serializable {
    private String code;
    private String intitule;
    private float prix;

    public Option() {
        this.code = "";
        this.intitule = "";
        this.prix = 0.0f;
    }

    public Option(String code, String intitule, float prix) {
        this.code = code;
        this.intitule = intitule;
        this.prix = prix;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public int reduction()
    {
        if(getPrix() < 50.0f) return -1;

        prix -= 50.0f;

        return 0;
    }

    @Override
    public String toString() {
        return "Option{" + code + " | " + intitule + " | " + prix + '}';
    }

}
