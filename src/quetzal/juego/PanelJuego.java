package quetzal.juego;

import quetzal.modelo.Piloto;
import quetzal.modelo.Partida;
import quetzal.modelo.Almacen;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JPanel;

/**
 * Panel principal del juego Side Scroller.
 * Movimiento horizontal: enemigos nacen en X maximo y van a X minimo.
 * Cada proyectil y enemigo es un hilo independiente.
 * Mecanicas: nucleo (+150 y limpia), asteroide (bloquea 2s), capsula (+10).
 *
 * @author Oscar Rene Monzon Ortiz - 202402543
 */
public class PanelJuego extends JPanel implements KeyListener, Runnable {

    public static final int ANCHO = 900;
    public static final int ALTO = 550;

    private Piloto piloto;
    private VentanaJuego ventana;
    private int jugadorX = 60;
    private int jugadorY = 250;
    private int puntaje = 0;
    private boolean enJuego = true;
    private boolean bloqueado = false;
    private long bloqueadoHasta = 0;
    private long ultimoDisparo = 0;
    private long inicioMs;

    // Vectores para hilos vivos (exigencia de la practica).
    private Vector<ProyectilHilo> proyectiles = new Vector<ProyectilHilo>();
    private Vector<EnemigoHilo> enemigos = new Vector<EnemigoHilo>();

    private boolean arriba = false;
    private boolean abajo = false;
    private boolean izquierda = false;
    private boolean derecha = false;
    private Random rnd = new Random();
    private Thread generador;
    private Thread repintado;

    public PanelJuego(Piloto piloto, VentanaJuego ventana) {
        this.piloto = piloto;
        this.ventana = ventana;
        setSize(ANCHO, ALTO);
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        inicioMs = System.currentTimeMillis();
    }

    public void iniciar() {
        enJuego = true;
        generador = new Thread(this);
        generador.start();
        repintado = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (enJuego) {
                        moverJugador();
                        limpiarInactivos();
                        repaint();
                        Thread.sleep(30);
                    }
                } catch (InterruptedException e) {
                }
            }
        });
        repintado.start();
    }

    private void moverJugador() {
        if (bloqueado && System.currentTimeMillis() > bloqueadoHasta) {
            bloqueado = false;
        }
        if (bloqueado) {
            return;
        }
        int v = piloto.getNave().getVelocidadJugador();
        if (arriba) {
            jugadorY -= v;
        }
        if (abajo) {
            jugadorY += v;
        }
        if (izquierda) {
            jugadorX -= v;
        }
        if (derecha) {
            jugadorX += v;
        }
        if (jugadorY < 20) {
            jugadorY = 20;
        }
        if (jugadorY > ALTO - 60) {
            jugadorY = ALTO - 60;
        }
        if (jugadorX < 10) {
            jugadorX = 10;
        }
        if (jugadorX > ANCHO - 120) {
            jugadorX = ANCHO - 120;
        }
    }

    private void limpiarInactivos() {
        // Limpia proyectiles muertos.
        for (int i = proyectiles.size() - 1; i >= 0; i--) {
            ProyectilHilo p = proyectiles.elementAt(i);
            if (!p.isAlive() || !p.isActivo() || p.getXpos() >= ANCHO) {
                proyectiles.remove(i);
            }
        }
        // Limpia enemigos muertos o fuera de pantalla.
        for (int i = enemigos.size() - 1; i >= 0; i--) {
            EnemigoHilo e = enemigos.elementAt(i);
            if (!e.isAlive() || !e.isActivo() || e.getXpos() <= -40) {
                enemigos.remove(i);
            }
        }
    }

    /** Generador de oleadas: crea enemigos en el borde derecho. */
    @Override
    public void run() {
        try {
            while (enJuego) {
                int tipo = rnd.nextInt(10);
                int t;
                if (tipo <= 4) {
                    t = EnemigoHilo.NAVE_ENEMIGA;
                } else if (tipo <= 6) {
                    t = EnemigoHilo.ASTEROIDE;
                } else if (tipo <= 8) {
                    t = EnemigoHilo.CAPSULA;
                } else {
                    t = EnemigoHilo.NUCLEO;
                }
                int y = 30 + rnd.nextInt(ALTO - 90);
                int vel = 3 + rnd.nextInt(3);
                int sleep = piloto.getNave().getSleepEnemigoMs();
                EnemigoHilo h = new EnemigoHilo(ANCHO, y, t, vel, sleep, this);
                synchronized (enemigos) {
                    enemigos.add(h);
                }
                h.start();
                Thread.sleep(900);
            }
        } catch (InterruptedException e) {
        }
    }

    /** Disparo con cooldown segun nave (2s / 1s / 0.3s). */
    private void disparar() {
        long ahora = System.currentTimeMillis();
        int cooldown = piloto.getNave().getCooldownDisparoMs();
        if (ahora - ultimoDisparo < cooldown) {
            return;
        }
        ultimoDisparo = ahora;
        ProyectilHilo p = new ProyectilHilo(jugadorX + 40, jugadorY + 15, this);
        synchronized (proyectiles) {
            proyectiles.add(p);
        }
        p.start();
    }

    /** Llamado por cada ProyectilHilo para ver si golpeo algo. */
    public synchronized void revisarImpactoProyectil(ProyectilHilo p) {
        if (!enJuego || !p.isActivo()) {
            return;
        }
        for (int i = 0; i < enemigos.size(); i++) {
            EnemigoHilo e = enemigos.elementAt(i);
            if (!e.isActivo()) {
                continue;
            }
            int dx = Math.abs(p.getXpos() - e.getXpos());
            int dy = Math.abs(p.getYpos() - e.getYpos());
            if (dx < 28 && dy < 28) {
                // Impacto.
                if (e.getTipo() == EnemigoHilo.NAVE_ENEMIGA) {
                    puntaje += 20;
                    e.desactivar();
                    p.desactivar();
                    break;
                } else if (e.getTipo() == EnemigoHilo.ASTEROIDE) {
                    puntaje += 5;
                    e.desactivar();
                    p.desactivar();
                    break;
                } else if (e.getTipo() == EnemigoHilo.CAPSULA) {
                    puntaje += 10;
                    e.desactivar();
                    p.desactivar();
                    break;
                } else if (e.getTipo() == EnemigoHilo.NUCLEO) {
                    // Nucleo: 150 pts y destruye enemigos visibles.
                    puntaje += 150;
                    e.desactivar();
                    p.desactivar();
                    destruirEnemigosVisibles();
                    break;
                }
            }
        }
    }

    private synchronized void destruirEnemigosVisibles() {
        for (int i = 0; i < enemigos.size(); i++) {
            EnemigoHilo e = enemigos.elementAt(i);
            if (e.getTipo() == EnemigoHilo.NAVE_ENEMIGA
                    || e.getTipo() == EnemigoHilo.ASTEROIDE) {
                puntaje += 10;
                e.desactivar();
            }
        }
    }

    /** Llamado por cada EnemigoHilo para ver si choco con el jugador. */
    public synchronized void revisarColisionJugador(EnemigoHilo e) {
        if (!enJuego || !e.isActivo()) {
            return;
        }
        int dx = Math.abs(e.getXpos() - jugadorX);
        int dy = Math.abs(e.getYpos() - jugadorY);
        if (dx < 38 && dy < 38) {
            if (e.getTipo() == EnemigoHilo.CAPSULA) {
                puntaje += 10;
                e.desactivar();
                return;
            }
            if (e.getTipo() == EnemigoHilo.NUCLEO) {
                puntaje += 150;
                e.desactivar();
                destruirEnemigosVisibles();
                return;
            }
            if (e.getTipo() == EnemigoHilo.ASTEROIDE) {
                long ahora = System.currentTimeMillis();
                if (ahora > bloqueadoHasta) {
                    // Primera colision: bloquea 2 segundos.
                    bloqueado = true;
                    bloqueadoHasta = ahora + 2000;
                    e.desactivar();
                    return;
                } else {
                    // Choco durante el bloqueo: derrota.
                    terminarPartida();
                    return;
                }
            }
            // Nave enemiga: derrota inmediata.
            terminarPartida();
        }
    }

    private synchronized void terminarPartida() {
        if (!enJuego) {
            return;
        }
        enJuego = false;
        int dur = (int) ((System.currentTimeMillis() - inicioMs) / 1000);
        String fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        Partida partida = new Partida(piloto.getNombre(),
                piloto.getNave().getNombre(), puntaje, fecha, dur);
        Almacen.registrarPartida(partida);
        // Detiene hilos.
        for (int i = 0; i < enemigos.size(); i++) {
            enemigos.elementAt(i).desactivar();
        }
        for (int i = 0; i < proyectiles.size(); i++) {
            proyectiles.elementAt(i).desactivar();
        }
        if (ventana != null) {
            ventana.mostrarResultados(puntaje);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Fondo estrellas simples.
        g.setColor(Color.DARK_GRAY);
        for (int i = 0; i < 40; i++) {
            int sx = (i * 137) % ANCHO;
            int sy = (i * 89) % ALTO;
            g.fillRect(sx, sy, 2, 2);
        }
        // Jugador (nave modelo quetzal).
        if (bloqueado) {
            g.setColor(Color.ORANGE);
        } else {
            g.setColor(Color.GREEN);
        }
        g.fillRect(jugadorX, jugadorY, 40, 30);
        g.setColor(Color.WHITE);
        g.fillRect(jugadorX + 40, jugadorY + 12, 10, 6);

        // Proyectiles.
        g.setColor(Color.YELLOW);
        synchronized (proyectiles) {
            for (int i = 0; i < proyectiles.size(); i++) {
                ProyectilHilo p = proyectiles.elementAt(i);
                g.fillRect(p.getXpos(), p.getYpos(), 12, 4);
            }
        }
        // Enemigos y objetos.
        synchronized (enemigos) {
            for (int i = 0; i < enemigos.size(); i++) {
                EnemigoHilo e = enemigos.elementAt(i);
                if (e.getTipo() == EnemigoHilo.NAVE_ENEMIGA) {
                    g.setColor(Color.RED);
                    g.fillRect(e.getXpos(), e.getYpos(), 34, 26);
                } else if (e.getTipo() == EnemigoHilo.ASTEROIDE) {
                    g.setColor(Color.GRAY);
                    g.fillOval(e.getXpos(), e.getYpos(), 32, 32);
                } else if (e.getTipo() == EnemigoHilo.NUCLEO) {
                    g.setColor(Color.CYAN);
                    g.fillOval(e.getXpos(), e.getYpos(), 24, 24);
                } else {
                    g.setColor(Color.MAGENTA);
                    g.fillRect(e.getXpos(), e.getYpos(), 22, 22);
                }
            }
        }
        // HUD.
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Piloto: " + piloto.getNombre(), 10, 20);
        g.drawString("Nave: " + piloto.getNave().getNombre(), 10, 40);
        g.drawString("Puntos: " + puntaje, 10, 60);
        if (bloqueado) {
            g.setColor(Color.ORANGE);
            g.drawString("BLOQUEADO 2s por asteroide!", 350, 30);
        }
        g.setColor(Color.LIGHT_GRAY);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString("Flechas/WASD mover - ESPACIO disparar", 550, 20);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();
        if (c == KeyEvent.VK_UP || c == KeyEvent.VK_W) {
            arriba = true;
        }
        if (c == KeyEvent.VK_DOWN || c == KeyEvent.VK_S) {
            abajo = true;
        }
        if (c == KeyEvent.VK_LEFT || c == KeyEvent.VK_A) {
            izquierda = true;
        }
        if (c == KeyEvent.VK_RIGHT || c == KeyEvent.VK_D) {
            derecha = true;
        }
        if (c == KeyEvent.VK_SPACE) {
            disparar();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int c = e.getKeyCode();
        if (c == KeyEvent.VK_UP || c == KeyEvent.VK_W) {
            arriba = false;
        }
        if (c == KeyEvent.VK_DOWN || c == KeyEvent.VK_S) {
            abajo = false;
        }
        if (c == KeyEvent.VK_LEFT || c == KeyEvent.VK_A) {
            izquierda = false;
        }
        if (c == KeyEvent.VK_RIGHT || c == KeyEvent.VK_D) {
            derecha = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public int getPuntaje() {
        return puntaje;
    }
}
