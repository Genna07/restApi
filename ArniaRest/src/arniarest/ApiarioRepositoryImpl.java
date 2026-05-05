package arniarest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApiarioRepositoryImpl implements ApiarioRepository {
    private final DatabaseManager dbManager;

    public ApiarioRepositoryImpl(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public List<Apiario> leggiTutti() throws SQLException {
        List<Apiario> lista = new ArrayList<>();
        String sql = "SELECT api_id, api_nome, api_posizione FROM apiario";
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Apiario(rs.getInt("api_id"), rs.getString("api_nome"), rs.getString("api_posizione")));
            }
        }
        return lista;
    }

    @Override
    public boolean salva(String nome, String posizione) throws SQLException {
        String sql = "INSERT INTO apiario (api_nome, api_posizione) VALUES (?, ?)";
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, posizione);
            return stmt.executeUpdate() > 0;
        }
    }
}