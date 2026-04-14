/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arniarest;

/**
 *
 * @author gennaioli.francesco
 */
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class ArniaServer {
    
    public static void avviaServer(int porta) {
        try {
            // Crea il server sulla porta specificata
            HttpServer server = HttpServer.create(new InetSocketAddress(porta), 0);
            
            // Registra gli indirizzi (endpoint) per ricevere e inviare i dati
            server.createContext("/api/rilevazioni/post", new PostMisurazioneHandler());
            server.createContext("/api/rilevazioni/get", new GetMisurazioneHandler());
            
            server.setExecutor(null); // Usa l'esecutore di base
            server.start(); // Accende il server
            
            System.out.println("==============================================");
            System.out.println("  Server REST ARNIA DIGITALE avviato!");
            System.out.println("==============================================");
            System.out.println("In ascolto sulla porta: " + porta);
            System.out.println("Endpoint POST: http://localhost:" + porta + "/api/rilevazioni/post");
            System.out.println("Endpoint GET:  http://localhost:" + porta + "/api/rilevazioni/get");
            System.out.println("Premi Ctrl+C per spegnere il server.");
            
        } catch (IOException e) {
            System.err.println("Errore durante l'avvio del server: " + e.getMessage());
        }
    }
}