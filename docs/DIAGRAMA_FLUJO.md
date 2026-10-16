# Diagrama de Flujo - Quetzal Space Defender

**Estudiante:** Oscar Rene Monzon Ortiz - 202402543

## Flujo general (Mermaid)
```mermaid
flowchart TD
    A[Inicio Main] --> B[Menu Principal]
    B --> C{Jugar?}
    B --> D[Crear Piloto]
    B --> E[Top Puntajes]
    B --> F[Salir]
    D --> D1[Validar nombre >=3 y no duplicado] --> D2[Guardar en Vector pilotos] --> B
    C -->|Elegir piloto| G[VentanaJuego + PanelJuego]
    G --> H[Generador Thread crea EnemigoHilo en Xmax]
    H --> I[Cada Proyectil/Enemigo Thread actualiza x,y con sleep]
    I --> J{Colision?}
    J -->|Proyectil vs Enemigo| K[+20 nave, +5 asteroide, +10 capsula, +150 nucleo y limpia]
    J -->|Jugador vs Capsula/Nucleo| L[+10 / +150]
    J -->|Jugador vs Asteroide| M{Ya bloqueado?}
    M -->|No| N[Bloquea 2s]
    M -->|Si| O[Derrota]
    J -->|Jugador vs Nave| O[Derrota]
    K --> I
    L --> I
    N --> I
    O --> P[Registrar Partida en Vector historial] --> Q[Pantalla resultados] --> B
    E --> E1[Tabla top + JFreeChart] --> E2[Exportar HTML con java.io + PNG ImageIO] --> B
```

## Flujo textual paso a paso
1. `Main` lanza `MenuPrincipal`.
2. Crear piloto valida y guarda en `Almacen.pilotos` (Vector).
3. Jugar pide elegir piloto y abre `VentanaJuego`.
4. `PanelJuego.iniciar` lanza generador + repintado.
5. Bucle: mover jugador (WASD/flechas), disparar (ESPACIO con cooldown), enemigos avanzan.
6. Colisiones sincronizadas definen puntaje, bloqueo 2s o derrota.
7. Al derrota se crea `Partida`, se guarda en `historial`, se muestra resultado.
8. Top ordena Vector con burbuja, dibuja JFreeChart y exporta HTML.

## Procesos principales
- Gestión pilotos: validación + Vector.
- Simulación combate: hilos + colisiones + sleep por dificultad.
- Mecánicas especiales: núcleo, asteroide, cápsula.
- Reportes: JFreeChart + FileWriter/PrintWriter + ImageIO.
