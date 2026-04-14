/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arniarest;

/**
 *
 * @author gennaioli.francesco
 */
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class GetMisurazioneHandler implements HttpHandler {
    
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        
        // CORS: Permette ai siti web di leggere i dati
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        
        // Controllo: deve essere per forza una richiesta GET
        if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {
            RispostaServer errore = new RispostaServer(405, "Errore: Usa il metodo GET per leggere i dati.");
            inviaRisposta(exchange, 405, gson.toJson(errore));
            return;
        }

        try {
            // 1. Chiediamo al Service di darci TUTTA la lista delle rilevazioni salvate
            List<Rilevazione> listaDati = ArniaService.ottieniTutte();
            
            // 2. GSON converte l'intera lista di oggetti in una lunga stringa JSON
            String jsonRisposta = gson.toJson(listaDati);
            
            // 3. Inviamo i dati al client
            inviaRisposta(exchange, 200, jsonRisposta);
            
        } catch (Exception e) {
            RispostaServer erroreGenerico = new RispostaServer(500, "Errore interno del server: " + e.getMessage());
            inviaRisposta(exchange, 500, gson.toJson(erroreGenerico));
        }
    }

    /**
     * Metodo di supporto per inviare la risposta al client
     */
    private void inviaRisposta(HttpExchange exchange, int codiceHttp, String jsonRisposta) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        byte[] bytes = jsonRisposta.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(codiceHttp, bytes.length);
        
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }
}