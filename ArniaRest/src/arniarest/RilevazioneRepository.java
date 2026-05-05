package arniarest;

import java.sql.SQLException;
import java.util.List;

public interface RilevazioneRepository {
    boolean salva(int idSensore, double dato, String dataOra) throws SQLException;
    List<Rilevazione> leggiTutte() throws SQLException; 
}