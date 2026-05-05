package arniarest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RilevazioneRepositoryImpl implements RilevazioneRepository {
    private final DatabaseManager dbManager;

    public RilevazioneRepositoryImpl(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public boolean salva(int idSensore, double dato, String dataOra) throws SQLException {
        String sql = "INSERT INTO Rilevazione (ril_sea_id, ril_dato, ril_dataOra) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, idSensore);
            stmt.setDouble(2, dato);
            stmt.setString(3, dataOra);
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public List<Rilevazione> leggiTutte() throws SQLException {
        List<Rilevazione> lista = new ArrayList<>();
        // Leggiamo i dati ordinandoli dal più recente al più vecchio
        String sql = "SELECT ril_sea_id, ril_dato, ril_dataOra FROM Rilevazione ORDER BY ril_dataOra DESC";
        
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Rilevazione r = new Rilevazione(
                    rs.getInt("ril_sea_id"),
                    rs.getDouble("ril_dato"),
                    rs.getString("ril_dataOra")
                );
                lista.add(r);
            }
        }
        return lista;
    }
}