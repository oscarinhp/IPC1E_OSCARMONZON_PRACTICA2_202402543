package quetzal.juego;

/**
 * Hilo de proyectil del jugador.
 * Cada disparo es un hilo independiente que avanza en X.
 *
 * @author Oscar Rene Monzon Ortiz - 202402543
 */
public class ProyectilHilo extends Thread {
    private int x;
    private int y;
    private boolean activo = true;
    private PanelJuego panel;
    private int velocidad = 12;

    public ProyectilHilo(int x, int y, PanelJuego panel) {
        this.x = x;
        this.y = y;
        this.panel = panel;
    }

    public int getXpos() {
        return x;
    }

    public int getYpos() {
        return y;
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
            while (activo && x < PanelJuego.ANCHO) {
                x += velocidad;
                // Avisa al panel para revisar impactos.
                if (panel != null) {
                    panel.revisarImpactoProyectil(this);
                }
                Thread.sleep(30);
            }
        } catch (InterruptedException e) {
            // Hilo interrumpido, termina.
        }
        activo = false;
    }
}
