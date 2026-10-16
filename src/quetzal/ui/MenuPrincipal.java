package quetzal.ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.FlowLayout;

/**
 * Menu principal (version inicial). Se ampliara en commit 2.
 */
public class MenuPrincipal extends JFrame {
    public MenuPrincipal() {
        super("Quetzal Space Defender");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        add(new JLabel("Quetzal Space Defender - cargando modulos..."));
    }
}
