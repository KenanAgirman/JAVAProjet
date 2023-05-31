package Beans;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TraceBean {
    private String nomFichierLog;

    public TraceBean()
    {
        this.nomFichierLog = "";
    }

    // Gestion des proprietes du bean
    public String getNomFichierLog() {
        return nomFichierLog;
    }

    public void setNomFichierLog(String nomFichierLog) {
        this.nomFichierLog = nomFichierLog;
    }

    // manipulation du bean

    public void log(String message)
    {
        if(nomFichierLog.isEmpty()) return;

        try (PrintWriter writer = new PrintWriter(new FileWriter(nomFichierLog, true))) {
            writer.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
