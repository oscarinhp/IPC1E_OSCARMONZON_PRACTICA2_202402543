package quetzal.modelo;

/**
 * Registro de una partida terminada.
 * Se guarda en un Vector dentro de Almacen.
 *
 * @author Oscar Rene Monzon Ortiz - 202402543
 */
public class Partida {
    private String pilotoNombre;
    private String naveNombre;
    private int puntaje;
    private String fecha;
    private int duracionSeg;

    public Partida(String pilotoNombre, String naveNombre, int puntaje,
                   String fecha, int duracionSeg) {
        this.pilotoNombre = pilotoNombre;
        this.naveNombre = naveNombre;
        this.puntaje = puntaje;
        this.fecha = fecha;
        this.duracionSeg = duracionSeg;
    }

    public String getPilotoNombre() {
        return pilotoNombre;
    }

    public String getNaveNombre() {
        return naveNombre;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public String getFecha() {
        return fecha;
    }

    public int getDuracionSeg() {
        return duracionSeg;
    }
}
