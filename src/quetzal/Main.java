package quetzal;

import quetzal.ui.MenuPrincipal;

/**
 * Punto de entrada del juego Quetzal Space Defender.
 * Side Scroller horizontal con hilos, vectores y reportes.
 *
 * @author Oscar Rene Monzon Ortiz - 202402543
 */
public class Main {
    public static void main(String[] args) {
        // Ventana principal en el hilo de Swing.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MenuPrincipal menu = new MenuPrincipal();
                menu.setVisible(true);
            }
        });
    }
}
