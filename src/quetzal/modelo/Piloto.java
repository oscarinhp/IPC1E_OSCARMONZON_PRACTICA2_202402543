package quetzal.modelo;

/**
 * Representa un piloto registrado por el usuario.
 * Guarda el nombre, la nave elegida y su mejor puntaje.
 *
 * @author Oscar Rene Monzon Ortiz - 202402543
 */
public class Piloto {
    private String nombre;
    private TipoNave nave;
    private int mejorPuntaje;

    public Piloto(String nombre, TipoNave nave) {
        this.nombre = nombre;
        this.nave = nave;
        this.mejorPuntaje = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoNave getNave() {
        return nave;
    }

    public void setNave(TipoNave nave) {
        this.nave = nave;
    }

    public int getMejorPuntaje() {
        return mejorPuntaje;
    }

    public void setMejorPuntaje(int mejorPuntaje) {
        this.mejorPuntaje = mejorPuntaje;
    }

    /**
     * Actualiza el mejor puntaje solo si el nuevo es mayor.
     */
    public void registrarPuntaje(int puntaje) {
        if (puntaje > mejorPuntaje) {
            mejorPuntaje = puntaje;
        }
    }

    @Override
    public String toString() {
        return nombre + " - " + nave.toString() + " - Best: " + mejorPuntaje;
    }
}
