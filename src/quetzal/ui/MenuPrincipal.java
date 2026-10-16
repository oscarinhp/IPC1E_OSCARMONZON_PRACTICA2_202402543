package quetzal.ui;

import quetzal.juego.VentanaJuego;
import quetzal.modelo.Almacen;
import quetzal.modelo.Piloto;
import java.awt.GridLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Menu principal: Jugar, Crear Piloto, Top de Puntajes, Salir.
 *
 * @author Oscar Rene Monzon Ortiz - 202402543
 */
public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {
        super("Quetzal Space Defender - Menu Principal");
        setSize(480, 360);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        JLabel titulo = new JLabel("QUETZAL SPACE DEFENDER", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titulo);
        JLabel sub = new JLabel("Oscar Rene Monzon Ortiz - 202402543", JLabel.CENTER);
        panel.add(sub);

        JButton btnJugar = new JButton("Jugar");
        JButton btnCrear = new JButton("Crear Piloto");
        JButton btnTop = new JButton("Top de Puntajes");
        JButton btnSalir = new JButton("Salir");

        btnJugar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                jugar();
            }
        });
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                new DialogoPiloto(MenuPrincipal.this).setVisible(true);
            }
        });
        btnTop.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                new TopPuntajes(MenuPrincipal.this).setVisible(true);
            }
        });
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                System.exit(0);
            }
        });

        panel.add(btnJugar);
        panel.add(btnCrear);
        panel.add(btnTop);
        panel.add(btnSalir);
        add(panel);
    }

    private void jugar() {
        if (Almacen.getPilotos().size() == 0) {
            JOptionPane.showMessageDialog(this, "Primero crea un piloto.");
            return;
        }
        String[] nombres = new String[Almacen.getPilotos().size()];
        for (int i = 0; i < Almacen.getPilotos().size(); i++) {
            nombres[i] = Almacen.getPilotos().elementAt(i).getNombre();
        }
        String elegido = (String) JOptionPane.showInputDialog(this,
                "Elige piloto:", "Jugar",
                JOptionPane.QUESTION_MESSAGE, null, nombres, nombres[0]);
        if (elegido == null) {
            return;
        }
        Piloto p = Almacen.buscarPiloto(elegido);
        if (p == null) {
            return;
        }
        VentanaJuego v = new VentanaJuego(p);
        v.iniciarJuego();
    }
}
