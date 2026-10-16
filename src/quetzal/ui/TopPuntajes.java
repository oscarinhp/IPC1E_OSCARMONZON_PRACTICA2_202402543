package quetzal.ui;

import quetzal.modelo.Almacen;
import quetzal.modelo.Partida;
import quetzal.reporte.GeneradorReporte;
import java.awt.BorderLayout;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Top de puntajes con tabla y grafica JFreeChart.
 *
 * @author Oscar Rene Monzon Ortiz - 202402543
 */
public class TopPuntajes extends JDialog {

    public TopPuntajes(JFrame padre) {
        super(padre, "Top de Puntajes", true);
        setSize(720, 520);
        setLocationRelativeTo(padre);
        setLayout(new BorderLayout());

        Vector<Partida> top = Almacen.topPuntajes(10);

        // Tabla con arreglo Object[][] (sin colecciones dinamicas por restriccion).
        String[] columnas = new String[]{"Piloto", "Nave", "Puntaje", "Fecha"};
        Object[][] datos = new Object[top.size()][4];
        for (int i = 0; i < top.size(); i++) {
            Partida p = top.elementAt(i);
            datos[i][0] = p.getPilotoNombre();
            datos[i][1] = p.getNaveNombre();
            datos[i][2] = Integer.valueOf(p.getPuntaje());
            datos[i][3] = p.getFecha();
        }
        JTable tabla = new JTable(datos, columnas);
        add(new JScrollPane(tabla), BorderLayout.NORTH);

        // Grafica de barras con JFreeChart.
        DefaultCategoryDataset ds = new DefaultCategoryDataset();
        for (int i = 0; i < top.size(); i++) {
            Partida p = top.elementAt(i);
            ds.addValue(p.getPuntaje(), "Puntaje", p.getPilotoNombre());
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Mejores Puntajes - Quetzal",
                "Piloto", "Puntos", ds);
        add(new ChartPanel(chart), BorderLayout.CENTER);

        JPanel abajo = new JPanel();
        JButton btnExport = new JButton("Exportar Reporte HTML");
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                exportar();
            }
        });
        abajo.add(btnExport);
        add(abajo, BorderLayout.SOUTH);
    }

    private void exportar() {
        try {
            String ruta = GeneradorReporte.exportarTop();
            JOptionPane.showMessageDialog(this,
                    "Reporte generado:\n" + ruta
                    + "\nAbre el HTML y guardalo como PDF desde el navegador.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}

