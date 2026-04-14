/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arniarest;

/**
 *
 * @author gennaioli.francesco
 */
/**
 * Modello dati per le rilevazioni dei sensori (Payload JSON in ingresso)
 */
public class Rilevazione {
    private double ril_dato;
    private String ril_dataOra;
    private int ril_sea_id;

    // Costruttore vuoto (Obbligatorio per far funzionare GSON)
    public Rilevazione() {
    }

    // Costruttore con parametri
    public Rilevazione(double ril_dato, String ril_dataOra, int ril_sea_id) {
        this.ril_dato = ril_dato;
        this.ril_dataOra = ril_dataOra;
        this.ril_sea_id = ril_sea_id;
    }

    // --- GETTER E SETTER ---
    public double getRil_dato() { 
        return ril_dato; 
    }
    
    public void setRil_dato(double ril_dato) { 
        this.ril_dato = ril_dato; 
    }

    public String getRil_dataOra() { 
        return ril_dataOra; 
    }
    
    public void setRil_dataOra(String ril_dataOra) { 
        this.ril_dataOra = ril_dataOra; 
    }

    public int getRil_sea_id() { 
        return ril_sea_id; 
    }
    
    public void setRil_sea_id(int ril_sea_id) { 
        this.ril_sea_id = ril_sea_id; 
    }
}
