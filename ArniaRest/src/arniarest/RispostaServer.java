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
 * Modello per la risposta generica del server (Payload JSON in uscita)
 */
public class RispostaServer {
    private int status;
    private String messaggio;

    // Costruttore vuoto
    public RispostaServer() {
    }

    public RispostaServer(int status, String messaggio) {
        this.status = status;
        this.messaggio = messaggio;
    }

    public int getStatus() { 
        return status; 
    }
    
    public String getMessaggio() { 
        return messaggio; 
    }
}