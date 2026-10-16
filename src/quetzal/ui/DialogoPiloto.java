package quetzal.ui;

import quetzal.modelo.Almacen;
import quetzal.modelo.TipoNave;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Dialogo para crear piloto con validaciones y seleccion de nave.
 *
 * @author Oscar Rene Monzon Ortiz - 202402543
 */
public class DialogoPiloto extends JDialog {

    private JTextField txtNombre;
    private JComboBox<String> cmbNave;

    public DialogoPiloto(JFrame padre) {
        super(padre, "Crear Piloto", true);
        setSize(380, 220);
        setLocationRelativeTo(padre);
        setLayout(new GridLayout(4, 2, 8, 8));

        add(new JLabel("Nombre (min 3 letras):"));
        txtNombre = new JTextField();
        add(txtNombre);

        add(new JLabel("Modelo nave:"));
        cmbNave = new JComboBox<String>(new String[]{
            "Explorador - Facil (rapido, disparo 2s)",
            "Caza Estelar - Normal (medio, disparo 1s)",
            "Acorazado - Dificil (lento, disparo 0.3s)"
        });
        add(cmbNave);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        add(btnGuardar);
        add(btnCancelar);

        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                guardar();
            }
        });
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                dispose();
            }
        });
    }

    private void guardar() {
        String nombre = txtNombre.getText();
        if (nombre == null || nombre.trim().length() < 3) {
            JOptionPane.showMessageDialog(this,
                    "Nombre invalido: minimo 3 caracteres.");
            return;
        }
        TipoNave nave;
        int idx = cmbNave.getSelectedIndex();
        if (idx == 0) {
            nave = TipoNave.EXPLORADOR;
        } else if (idx == 2) {
            nave = TipoNave.ACORAZADO;
        } else {
            nave = TipoNave.CAZA_ESTELAR;
        }
        boolean ok = Almacen.agregarPiloto(nombre, nave);
        if (!ok) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo crear: nombre duplicado o datos invalidos.");
            return;
        }
        JOptionPane.showMessageDialog(this,
                "Piloto creado: " + nombre.trim() + " con " + nave.toString());
        dispose();
    }
}
