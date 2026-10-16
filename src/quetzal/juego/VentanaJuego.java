package quetzal.juego;

import quetzal.modelo.Piloto;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Ventana de juego que contiene el PanelJuego.
 *
 * @author Oscar Rene Monzon Ortiz - 202402543
 */
public class VentanaJuego extends JFrame {

    private PanelJuego panel;

    public VentanaJuego(Piloto piloto) {
        super("Quetzal Space Defender - " + piloto.getNombre());
        setSize(PanelJuego.ANCHO + 16, PanelJuego.ALTO + 39);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        panel = new PanelJuego(piloto, this);
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
    }

    public void iniciarJuego() {
        setVisible(true);
        panel.requestFocusInWindow();
        panel.iniciar();
    }

    /** Pantalla de resultados al perder. */
    public void mostrarResultados(int puntaje) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(VentanaJuego.this,
                        "Partida finalizada.\nPuntaje: " + puntaje
                        + "\nHistorial guardado en vectores.",
                        "Resultados", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        });
    }
}
