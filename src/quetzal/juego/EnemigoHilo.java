package quetzal.juego;

/**
 * Hilo de enemigo / objeto en pantalla.
 * Aparece en X maximo y se desplaza a X minimo.
 * tipo: 0=nave enemiga, 1=asteroide, 2=nucleo energia, 3=capsula suministro
 *
 * @author Oscar Rene Monzon Ortiz - 202402543
 */
public class EnemigoHilo extends Thread {
    public static final int NAVE_ENEMIGA = 0;
    public static final int ASTEROIDE = 1;
    public static final int NUCLEO = 2;
    public static final int CAPSULA = 3;

    private int x;
    private int y;
    private int tipo;
    private int velocidad;
    private int sleepMs;
    private boolean activo = true;
    private PanelJuego panel;

    public EnemigoHilo(int x, int y, int tipo, int velocidad, int sleepMs, PanelJuego panel) {
        this.x = x;
        this.y = y;
        this.tipo = tipo;
        this.velocidad = velocidad;
        this.sleepMs = sleepMs;
        this.panel = panel;
    }

    public int getXpos() {
        return x;
    }

    public int getYpos() {
        return y;
    }

    public int getTipo() {
        return tipo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void desactivar() {
        activo = false;
    }

    @Override
    public void run() {
        try {
            while (activo && x > -40) {
                x -= velocidad;
                if (panel != null) {
                    panel.revisarColisionJugador(this);
                }
                Thread.sleep(sleepMs);
            }
        } catch (InterruptedException e) {
            // Termina el hilo.
        }
        activo = false;
    }
}
