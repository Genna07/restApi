package arniarest;
import com.sun.net.httpserver.*;
import com.google.gson.Gson;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class ApiarioHandler implements HttpHandler {
    private final ApiarioRepository repository;
    private final Gson gson = new Gson();

    public ApiarioHandler(ApiarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String risposta = "";
        int statusCode = 200;

        try {
            if (method.equalsIgnoreCase("GET")) {
                risposta = gson.toJson(repository.leggiTutti());
            } else {
                statusCode = 405;
                risposta = "{\"errore\": \"Metodo non supportato\"}";
            }
        } catch (Exception e) {
            statusCode = 500;
            risposta = "{\"errore\": \"" + e.getMessage() + "\"}";
        }

        exchange.getResponseHeaders().set("Content-Type", "application/json");
        byte[] bytes = risposta.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }
}