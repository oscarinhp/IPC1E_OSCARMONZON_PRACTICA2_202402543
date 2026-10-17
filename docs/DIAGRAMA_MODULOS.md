# Diagrama de Modulos - Quetzal Space Defender

**Estudiante:** Oscar Rene Monzon Ortiz - 202402543
**Practica 2 - IPC1E - 2S2026**

El proyecto se organiza en 4 paquetes (modulos) con una dependencia descendente:
la interfaz depende del juego, el juego depende del modelo, y los reportes leen el modelo.

## 1. Diagrama de paquetes (Mermaid)

```mermaid
flowchart TD
    MAIN[quetzal.Main] --> UI[quetzal.ui]
    UI --> JUEGO[quetzal.juego]
    UI --> REPORTE[quetzal.reporte]
    JUEGO --> MODELO[quetzal.modelo]
    REPORTE --> MODELO
    MODELO --> JFC[(JFreeChart)]
    REPORTE --> JFC
    MODELO --> IO[(java.io / ImageIO)]
```

## 2. Diagrama de clases por modulo

```mermaid
classDiagram
    class Main
    class MenuPrincipal {
        -JButton jugar, crear, top, salir
        +jugar()
    }
    class DialogoPiloto {
        -JTextField txtNombre
        -JComboBox cmbNave
        +guardar()
    }
    class TopPuntajes {
        -JTable tabla
        +exportar()
    }
    class VentanaJuego {
        -PanelJuego panel
        +iniciarJuego()
        +mostrarResultados(int)
    }
    class PanelJuego {
        -Vector~ProyectilHilo~ proyectiles
        -Vector~EnemigoHilo~ enemigos
        -Piloto piloto
        -int puntaje
        +iniciar()
        +moverJugador()
        +disparar()
        +revisarImpactoProyectil()
        +revisarColisionJugador()
        +terminarPartida()
        +paintComponent()
    }
    class ProyectilHilo {
        -int x, y
        -boolean activo
        +run()
    }
    class EnemigoHilo {
        -int x, y, tipo
        -boolean activo
        +run()
    }
    class GeneradorReporte {
        +exportarTop()$
        -escapar(String)$
    }
    class Almacen {
        -Vector~Piloto~ pilotos$
        -Vector~Partida~ historial$
        +agregarPiloto()$
        +buscarPiloto()$
        +registrarPartida()$
        +topPuntajes()$
    }
    class Piloto {
        -String nombre
        -TipoNave nave
        -int mejorPuntaje
        +registrarPuntaje(int)
    }
    class Partida {
        -String pilotoNombre
        -String naveNombre
        -int puntaje
        -String fecha
        -int duracionSeg
    }
    class TipoNave {
        <<enumeration>>
        EXPLORADOR
        CAZA_ESTELAR
        ACORAZADO
    }

    Main --> MenuPrincipal
    MenuPrincipal --> DialogoPiloto
    MenuPrincipal --> TopPuntajes
    MenuPrincipal --> VentanaJuego
    VentanaJuego --> PanelJuego
    PanelJuego --> ProyectilHilo
    PanelJuego --> EnemigoHilo
    PanelJuego --> Piloto
    PanelJuego --> Partida
    PanelJuego --> Almacen
    TopPuntajes --> Almacen
    TopPuntajes --> GeneradorReporte
    GeneradorReporte --> Almacen
    Almacen --> Piloto
    Almacen --> Partida
    Piloto --> TipoNave
```

## 3. Descripcion de cada modulo

| Modulo (paquete) | Responsabilidad | Clases |
|---|---|---|
| `quetzal` | Arranque de la aplicacion | `Main` |
| `quetzal.modelo` | Entidades y persistencia en vectores | `Piloto`, `Partida`, `TipoNave`, `Almacen` |
| `quetzal.juego` | Simulacion, hilos, colisiones y dibujo | `PanelJuego`, `VentanaJuego`, `ProyectilHilo`, `EnemigoHilo` |
| `quetzal.ui` | Interfaz grafica | `MenuPrincipal`, `DialogoPiloto`, `TopPuntajes` |
| `quetzal.reporte` | Generacion de reportes | `GeneradorReporte` |

## 4. Flujo de dependencias (regla)

```mermaid
flowchart LR
    ui --> juego --> modelo
    ui --> reporte --> modelo
```

- La interfaz **nunca** es referenciada por el modelo (bajo acoplamiento).
- `Almacen` es el punto unico de acceso a los datos (patron tipo repositorio simple).
- La logica de negocio (puntajes, colisiones) vive en `PanelJuego`, no en la vista.
