package arniarest;

public class Rilevazione {
    private int idSensore;
    private double dato;
    private String dataOra;

    public Rilevazione(int idSensore, double dato, String dataOra) {
        this.idSensore = idSensore;
        this.dato = dato;
        this.dataOra = dataOra;
    }

    public int getIdSensore() { return idSensore; }
    public double getDato() { return dato; }
    public String getDataOra() { return dataOra; }
}