package arniarest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class GetMisurazioneHandler implements HttpHandler {
    private final ArniaService service;
    private final Gson gson = new Gson();

    public GetMisurazioneHandler(ArniaService service) {
        this.service = service;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Accettiamo solo richieste GET dal browser
        if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {
            inviaRisposta(exchange, 405, "{\"errore\": \"Metodo non consentito\"}");
            return;
        }

        try {
            // Chiediamo al service la lista di tutte le rilevazioni nel DB
            List<Rilevazione> lista = service.ottieniTutteLeRilevazioni();
            
            // Trasformiamo la lista Java in una stringa JSON
            String jsonRisposta = gson.toJson(lista);
            
            inviaRisposta(exchange, 200, jsonRisposta);
        } catch (Exception e) {
            inviaRisposta(exchange, 500, "{\"errore\": \"Errore interno del server\"}");
        }
    }

    private void inviaRisposta(HttpExchange exchange, int codice, String risposta) throws IOException {
        // Impostiamo il tipo di contenuto come JSON e usiamo UTF-8 per gli accenti
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        byte[] bytes = risposta.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(codice, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }
}