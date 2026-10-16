package quetzal.reporte;

import quetzal.modelo.Almacen;
import quetzal.modelo.Partida;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Vector;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Genera reporte HTML/CSS con FileWriter y grafica PNG con ImageIO.
 * Solo usa java.io.* para archivos, segun la practica.
 *
 * @author Oscar Rene Monzon Ortiz - 202402543
 */
public class GeneradorReporte {

    /**
     * Exporta top 10 a reporte.html + grafica_top.png en la carpeta actual.
     * @return ruta del HTML generado.
     */
    public static String exportarTop() throws Exception {
        Vector<Partida> top = Almacen.topPuntajes(10);

        // 1) Crear dataset y grafica.
        DefaultCategoryDataset ds = new DefaultCategoryDataset();
        for (int i = 0; i < top.size(); i++) {
            Partida p = top.elementAt(i);
            ds.addValue(p.getPuntaje(), "Puntaje", p.getPilotoNombre());
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Mejores Puntajes - Quetzal Space Defender",
                "Piloto", "Puntos", ds);

        // 2) Guardar PNG con ImageIO (solo java.io.File de por medio).
        File img = new File("grafica_top.png");
        BufferedImage bi = chart.createBufferedImage(640, 400);
        ImageIO.write(bi, "png", img);

        // 3) Escribir HTML con FileWriter / PrintWriter.
        File html = new File("reporte.html");
        FileWriter fw = new FileWriter(html);
        PrintWriter pw = new PrintWriter(fw);
        pw.println("<!DOCTYPE html>");
        pw.println("<html><head><meta charset='UTF-8'>");
        pw.println("<title>Reporte Quetzal - Oscar Monzon 202402543</title>");
        pw.println("<style>");
        pw.println("body{font-family:Arial;background:#0b1020;color:#fff;margin:20px;}");
        pw.println("table{border-collapse:collapse;width:100%;background:#fff;color:#000;}");
        pw.println("th,td{border:1px solid #333;padding:8px;text-align:left;}");
        pw.println("th{background:#1e88e5;color:#fff;}");
        pw.println(".card{background:#16213e;padding:16px;border-radius:10px;margin-bottom:16px;}");
        pw.println("</style></head><body>");
        pw.println("<div class='card'><h1>Quetzal Space Defender - Reporte</h1>");
        pw.println("<p>Estudiante: Oscar Rene Monzon Ortiz - 202402543</p>");
        pw.println("<p>Para PDF: abre este archivo en el navegador y usa Imprimir -&gt; Guardar como PDF.</p></div>");
        pw.println("<div class='card'><h2>Top de Puntajes</h2>");
        pw.println("<table><tr><th>#</th><th>Piloto</th><th>Nave</th><th>Puntaje</th><th>Fecha</th></tr>");
        for (int i = 0; i < top.size(); i++) {
            Partida p = top.elementAt(i);
            pw.println("<tr><td>" + (i + 1) + "</td><td>" + escapar(p.getPilotoNombre())
                    + "</td><td>" + escapar(p.getNaveNombre())
                    + "</td><td>" + p.getPuntaje()
                    + "</td><td>" + escapar(p.getFecha()) + "</td></tr>");
        }
        pw.println("</table></div>");
        pw.println("<div class='card'><h2>Grafica</h2>");
        pw.println("<img src='grafica_top.png' width='640' height='400'>");
        pw.println("</div></body></html>");
        pw.close();
        fw.close();
        return html.getAbsolutePath();
    }

    private static String escapar(String s) {
        if (s == null) {
            return "";
        }
        String r = s;
        r = r.replace("&", "&amp;");
        r = r.replace("<", "&lt;");
        r = r.replace(">", "&gt;");
        return r;
    }
}
