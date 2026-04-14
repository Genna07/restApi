/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package arniarest;

/**
 *
 * @author gennaioli.francesco
 */

/**
 * Entry point dell'applicazione Arnia Digitale REST
 */
public class ArniaRest {

    public static void main(String[] args) {
        // Configurazione della porta (di default usiamo la 8080)
        int porta = 8080;
        
        // Controllo se l'utente ha specificato una porta diversa
        if (args.length > 0) {
            try {
                porta = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Porta non valida, uso la porta di default: 8080");
            }
        }
        
        // Avvia il server REST dell'Arnia chiamando la classe ArniaServer
        ArniaServer.avviaServer(porta);
    }
}