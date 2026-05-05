package arniarest;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;

public class ArniaRest {

    public static void main(String[] args) {
        try {
            // 1. Inizializzazione Database
            DatabaseManager dbManager = DatabaseManager.getInstance();

            // 2. Inizializzazione Repository (i "cassetti" per ogni tabella)
            RilevazioneRepository rilevazioneRepo = new RilevazioneRepositoryImpl(dbManager);
            ApiarioRepository apiarioRepo = new ApiarioRepositoryImpl(dbManager);
            
            // 3. Inizializzazione Service (la logica per le rilevazioni)
            ArniaService service = new ArniaService(rilevazioneRepo);

            // 4. Creazione del server sulla porta 8080
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

            // --- ENDPOINT PER RILEVAZIONI ---
            // POST: Riceve dati dall'ESP32
            server.createContext("/api/rilevazioni", new PostMisurazioneHandler(service));
            // GET: Legge dati per il browser
            server.createContext("/api/leggi", new GetMisurazioneHandler(service));

            // --- ENDPOINT PER APIARI ---
            // GET: Legge la lista degli apiari
            server.createContext("/api/apiari", new ApiarioHandler(apiarioRepo));

            // 5. Avvio del Server
            server.setExecutor(null); 
            server.start();

            System.out.println("=========================================");
            System.out.println("🚀 SERVER ARNIA MULTI-TABELLA AVVIATO!");
            System.out.println("✅ Database: apicoltura (MySQL)");
            System.out.println("-----------------------------------------");
            System.out.println("🔗 RILEVAZIONI (GET): http://localhost:8080/api/leggi");
            System.out.println("🔗 APIARI (GET):      http://localhost:8080/api/apiari");
            System.out.println("-----------------------------------------");
            System.out.println("📡 In attesa di dati...");
            System.out.println("=========================================");

        } catch (IOException | SQLException e) {
            System.err.println("❌ Errore durante l'avvio del server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}