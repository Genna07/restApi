package arniarest;
import java.sql.SQLException;
import java.util.List;

public interface ApiarioRepository {
    List<Apiario> leggiTutti() throws SQLException;
    boolean salva(String nome, String posizione) throws SQLException;
}