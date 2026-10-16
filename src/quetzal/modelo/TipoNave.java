package quetzal.modelo;

/**
 * Tipos de nave que definen la dificultad del juego.
 * Cada tipo ajusta los tiempos de sleep() de los hilos
 * y la velocidad / cadencia de disparo del jugador.
 *
 * @author Oscar Rene Monzon Ortiz - 202402543
 */
public enum TipoNave {
    EXPLORADOR("Explorador", "Facil", 8, 2000, 40),
    CAZA_ESTELAR("Caza Estelar", "Normal", 5, 1000, 30),
    ACORAZADO("Acorazado", "Dificil", 3, 300, 20);

    private final String nombre;
    private final String dificultad;
    private final int velocidadJugador;
    private final int cooldownDisparoMs;
    private final int sleepEnemigoMs;

    TipoNave(String nombre, String dificultad, int velocidadJugador,
             int cooldownDisparoMs, int sleepEnemigoMs) {
        this.nombre = nombre;
        this.dificultad = dificultad;
        this.velocidadJugador = velocidadJugador;
        this.cooldownDisparoMs = cooldownDisparoMs;
        this.sleepEnemigoMs = sleepEnemigoMs;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDificultad() {
        return dificultad;
    }

    public int getVelocidadJugador() {
        return velocidadJugador;
    }

    public int getCooldownDisparoMs() {
        return cooldownDisparoMs;
    }

    public int getSleepEnemigoMs() {
        return sleepEnemigoMs;
    }

    @Override
    public String toString() {
        return nombre + " (" + dificultad + ")";
    }
}
