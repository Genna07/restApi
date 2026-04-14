/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arniarest;

/**
 *
 * @author gennaioli.francesco
 */
import java.util.ArrayList;
import java.util.List;

/**
 * Classe di servizio statica per la gestione dei dati dell'Arnia
 */
public class ArniaService {
    
    // Questa lista simula il nostro database per il momento
    private static List<Rilevazione> databaseSimulato = new ArrayList<>();

    /**
     * Salva una nuova rilevazione nella lista
     */
    public static boolean salvaRilevazione(Rilevazione r) {
        if (r == null) {
            return false;
        }
        databaseSimulato.add(r);
        System.out.println("[SERVICE] Rilevazione salvata correttamente nel database simulato.");
        return true;
    }

    /**
     * Restituisce tutto lo storico delle rilevazioni
     */
    public static List<Rilevazione> ottieniTutte() {
        return databaseSimulato;
    }
}
