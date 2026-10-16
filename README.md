# Quetzal Space Defender - Side Scroller

Práctica 2 - IPC1E - Oscar Rene Monzon Ortiz - 202402543

Juego de nave espacial con desplazamiento horizontal (izquierda a derecha),
hilos por proyectil/enemigo, vectores para persistencia, JFreeChart y
exportación HTML con `java.io.*`.

## Requisitos
- JDK 17
- `lib/jfreechart-1.5.3.jar`

## Compilar
```bat
build.bat
```

## Ejecutar
```bat
run.bat
```

## Estructura
- `src/quetzal/modelo/` : Piloto, TipoNave, Partida, Almacen (vectores)
- `src/quetzal/juego/` : PanelJuego, VentanaJuego, ProyectilHilo, EnemigoHilo
- `src/quetzal/ui/` : MenuPrincipal, DialogoPiloto, TopPuntajes
- `src/quetzal/reporte/` : GeneradorReporte (FileWriter/PrintWriter + ImageIO)
- `docs/` : Manual Técnico, Manual Usuario, Diagrama de Flujo
- `lib/` : JFreeChart

## Estado
Proyecto en desarrollo por commits. Ver historial git.
