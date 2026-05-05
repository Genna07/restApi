package arniarest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class PostMisurazioneHandler implements HttpHandler {
    private final ArniaService service;
    private final Gson gson = new Gson();

    public PostMisurazioneHandler(ArniaService service) {
        this.service = service;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            inviaRisposta(exchange, 405, "{\"errore\": \"Usa POST\"}");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))) {
            Rilevazione rilevazione = gson.fromJson(reader, Rilevazione.class);
            boolean salvato = service.registraNuovoDato(rilevazione);

            if (salvato) {
                inviaRisposta(exchange, 201, "{\"messaggio\": \"Dato salvato nel DB!\"}");
            } else {
                inviaRisposta(exchange, 500, "{\"errore\": \"Errore di salvataggio\"}");
            }
        } catch (Exception e) {
            inviaRisposta(exchange, 400, "{\"errore\": \"JSON non valido\"}");
        }
    }

    private void inviaRisposta(HttpExchange exchange, int codice, String risposta) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        byte[] bytes = risposta.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(codice, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }
}