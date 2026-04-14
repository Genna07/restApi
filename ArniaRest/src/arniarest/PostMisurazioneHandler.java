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
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class PostMisurazioneHandler implements HttpHandler {
    
    // Inizializziamo GSON per gestire i file JSON
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        
        // Permettiamo a qualsiasi sito/dispositivo di chiamare questa API (CORS)
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        
        // Controllo: se non è una richiesta POST, blocca tutto
        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            RispostaServer errore = new RispostaServer(405, "Errore: Devi usare il metodo POST per inserire dati.");
            inviaRisposta(exchange, 405, gson.toJson(errore));
            return;
        }

        try {
            // 1. Leggiamo il testo JSON che ci è stato inviato nel "body" della richiesta
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8)
            );
            
            // 2. Usiamo GSON per trasformare il testo JSON nel nostro oggetto Java
            Rilevazione nuovaRilevazione = gson.fromJson(reader, Rilevazione.class);
            reader.close();
            
            // 3. Passiamo il dato alla classe Service che si occuperà di salvarlo
            boolean salvato = ArniaService.salvaRilevazione(nuovaRilevazione);
            
            // 4. Inviamo la risposta di successo
            if (salvato) {
                RispostaServer ok = new RispostaServer(200, "Dato inserito con successo nel sistema!");
                inviaRisposta(exchange, 200, gson.toJson(ok));
            } else {
                RispostaServer erroreBody = new RispostaServer(400, "Errore: Dati inviati vuoti o non validi.");
                inviaRisposta(exchange, 400, gson.toJson(erroreBody));
            }
            
        } catch (JsonSyntaxException e) {
            // Se il JSON è scritto male (es. manca una virgola)
            RispostaServer erroreJson = new RispostaServer(400, "Errore di sintassi nel file JSON fornito.");
            inviaRisposta(exchange, 400, gson.toJson(erroreJson));
        } catch (Exception e) {
            // Per qualsiasi altro errore del server
            RispostaServer erroreGenerico = new RispostaServer(500, "Errore interno del server: " + e.getMessage());
            inviaRisposta(exchange, 500, gson.toJson(erroreGenerico));
        }
    }

    /**
     * Metodo di supporto per inviare la risposta testuale al client
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
