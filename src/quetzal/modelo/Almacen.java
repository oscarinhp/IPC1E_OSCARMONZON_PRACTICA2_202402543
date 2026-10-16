package quetzal.modelo;

import java.util.Vector;

/**
 * Persistencia en memoria con vectores.
 * Guarda pilotos e historial de partidas.
 * Solo usa Vector por restriccion de la practica.
 *
 * @author Oscar Rene Monzon Ortiz - 202402543
 */
public class Almacen {

    private static Vector<Piloto> pilotos = new Vector<Piloto>();
    private static Vector<Partida> historial = new Vector<Partida>();

    // Datos iniciales de ejemplo para que el Top no salga vacio.
    static {
        Piloto p1 = new Piloto("Quetzal-01", TipoNave.CAZA_ESTELAR);
        p1.registrarPuntaje(120);
        Piloto p2 = new Piloto("Quetzal-02", TipoNave.EXPLORADOR);
        p2.registrarPuntaje(80);
        pilotos.add(p1);
        pilotos.add(p2);
        historial.add(new Partida("Quetzal-01", "Caza Estelar", 120,
                "2026-10-01 10:00", 60));
        historial.add(new Partida("Quetzal-02", "Explorador", 80,
                "2026-10-06 11:30", 45));
    }

    public static synchronized boolean agregarPiloto(String nombre, TipoNave nave) {
        if (nombre == null) {
            return false;
        }
        nombre = nombre.trim();
        if (nombre.length() < 3 || nave == null) {
            return false;
        }
        // Validacion: no duplicados (ignora mayusculas).
        for (int i = 0; i < pilotos.size(); i++) {
            Piloto p = pilotos.elementAt(i);
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return false;
            }
        }
        pilotos.add(new Piloto(nombre, nave));
        return true;
    }

    public static synchronized Piloto buscarPiloto(String nombre) {
        if (nombre == null) {
            return null;
        }
        for (int i = 0; i < pilotos.size(); i++) {
            Piloto p = pilotos.elementAt(i);
            if (p.getNombre().equalsIgnoreCase(nombre.trim())) {
                return p;
            }
        }
        return null;
    }

    public static synchronized Vector<Piloto> getPilotos() {
        return pilotos;
    }

    public static synchronized Vector<Partida> getHistorial() {
        return historial;
    }

    public static synchronized void registrarPartida(Partida partida) {
        if (partida == null) {
            return;
        }
        historial.add(partida);
        Piloto p = buscarPiloto(partida.getPilotoNombre());
        if (p != null) {
            p.registrarPuntaje(partida.getPuntaje());
        }
    }

    /**
     * Devuelve el top ordenado de mayor a menor puntaje
     * usando ordenamiento burbuja sobre un Vector copia.
     */
    public static synchronized Vector<Partida> topPuntajes(int limite) {
        Vector<Partida> copia = new Vector<Partida>();
        for (int i = 0; i < historial.size(); i++) {
            copia.add(historial.elementAt(i));
        }
        // Burbuja descendente por puntaje.
        for (int i = 0; i < copia.size(); i++) {
            for (int j = 0; j < copia.size() - 1 - i; j++) {
                Partida a = copia.elementAt(j);
                Partida b = copia.elementAt(j + 1);
                if (a.getPuntaje() < b.getPuntaje()) {
                    copia.set(j, b);
                    copia.set(j + 1, a);
                }
            }
        }
        Vector<Partida> top = new Vector<Partida>();
        int n = limite < copia.size() ? limite : copia.size();
        for (int k = 0; k < n; k++) {
            top.add(copia.elementAt(k));
        }
        return top;
    }
}
