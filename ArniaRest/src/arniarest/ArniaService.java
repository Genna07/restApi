package arniarest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArniaService {
    private final RilevazioneRepository repository;

    public ArniaService(RilevazioneRepository repository) {
        this.repository = repository;
    }

    public boolean registraNuovoDato(Rilevazione rilevazione) {
        try {
            return repository.salva(rilevazione.getIdSensore(), rilevazione.getDato(), rilevazione.getDataOra());
        } catch (SQLException e) {
            System.err.println("Errore SQL salvataggio: " + e.getMessage());
            return false;
        }
    }

    public List<Rilevazione> ottieniTutteLeRilevazioni() {
        try {
            return repository.leggiTutte();
        } catch (SQLException e) {
            System.err.println("Errore SQL lettura: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}