# Matriz de Ubicaciones - Quetzal Space Defender

**Estudiante:** Oscar Rene Monzon Ortiz - 202402543
**Practica 2 - IPC1E - 2S2026**

Referencia rapida: cada requerimiento de la practica y el archivo/metodo exacto
donde esta implementado en el codigo.

## 1. Diagramas y documentacion

| Requerimiento | Archivo | Ubicacion |
|---|---|---|
| Manual tecnico | `docs/MANUAL_TECNICO.md` | Documento completo |
| Manual de usuario | `docs/MANUAL_USUARIO.md` | Documento + capturas en `docs/img/` |
| Diagrama de flujo | `docs/DIAGRAMA_FLUJO.md` | Mermaid + flujo textual |
| Diagrama de modulos | `docs/DIAGRAMA_MODULOS.md` | Mermaid + tabla de modulos |
| Matriz de ubicaciones | `docs/MATRIZ_UBICACIONES.md` | Este documento |

## 2. Habilidades - como se programa

| Requerimiento | Pts | Archivo | Metodo / Linea |
|---|---|---|---|
| Arquitectura POO por entidad | 3.0 | paquetes `quetzal.modelo/juego/ui/reporte` | Clases `Nave(TipoNave)`, `Enemigo(EnemigoHilo)`, `Proyectil(ProyectilHilo)`, `Piloto`, `Partida` |
| Nombres claros y comentarios | 2.0 | todos los `.java` | JavaDoc `@author` en cada clase |
| Vectores y ciclos (sin colecciones dinamicas) | 3.0 | `Almacen.java`, `PanelJuego.java` | `Vector` lineas 14-15; `for` con `elementAt()` |
| HTML con java.io | 2.0 | `reporte/GeneradorReporte.java` | `exportarTop()` lineas 45-77 |

## 3. Conocimiento - que se programa

| Requerimiento | Pts | Archivo | Metodo / Linea |
|---|---|---|---|
| Menu Jugar/Crear/Top/Salir | 2.0 | `ui/MenuPrincipal.java` | constructor + `jugar()` |
| Navegacion y regreso al menu | 1.0 | `ui/MenuPrincipal.java`, `juego/VentanaJuego.java` | `jugar()`, `mostrarResultados()` |
| Crear piloto con validaciones | 2.0 | `ui/DialogoPiloto.java`, `modelo/Almacen.java` | `guardar()`, `agregarPiloto()` |
| Seleccion de piloto existente | 1.0 | `ui/MenuPrincipal.java` | `jugar()` (`showInputDialog`) |
| Seleccion de nave | 1.0 | `ui/DialogoPiloto.java`, `modelo/TipoNave.java` | `JComboBox` + enum |
| Sleep/disparo segun nave (2s/1s/0.3s) | 3.0 | `modelo/TipoNave.java`, `juego/PanelJuego.java` | lineas 11-13; `disparar()` |
| Movimiento con teclado en limites | 1.0 | `juego/PanelJuego.java` | `keyPressed`/`keyReleased`, `moverJugador()` |
| Enemigos de Xmax a Xmin | 2.0 | `juego/EnemigoHilo.java` | `run()` (`x -= velocidad`) |
| Proyectil como hilo (x,y) | 3.0 | `juego/ProyectilHilo.java` | `run()` (`x += 12`) |
| Enemigo como hilo | 3.0 | `juego/EnemigoHilo.java` | `run()` |
| Colisiones sin fantasmas | 2.0 | `juego/PanelJuego.java` | `revisarImpactoProyectil()` + `isActivo()` |
| Eliminacion fuera de pantalla | 0.5 | `juego/PanelJuego.java` | `limpiarInactivos()` |
| Puntaje en pantalla | 0.5 | `juego/PanelJuego.java` | `paintComponent()` (`drawString "Puntos"`) |
| Colision enemigo/asteroide = derrota | 2.0 | `juego/PanelJuego.java` | `revisarColisionJugador()`, `terminarPartida()` |
| Detencion de hilos al terminar | 1.0 | `juego/PanelJuego.java` | `terminarPartida()` lineas 280-286 |
| Registro en historial + resultados | 1.0 | `juego/PanelJuego.java`, `modelo/Almacen.java` | `terminarPartida()` + `registrarPartida()` |
| Nucleo +150 y destruye visibles | 2.0 | `juego/PanelJuego.java` | `revisarImpactoProyectil()`/`revisarColisionJugador()` + `destruirEnemigosVisibles()` |
| Asteroide bloquea 2s | 2.0 | `juego/PanelJuego.java` | `revisarColisionJugador()` (`bloqueadoHasta = ahora + 2000`) |
| Capsula +10 | 2.0 | `juego/PanelJuego.java` | `revisarImpactoProyectil()`/`revisarColisionJugador()` |
| Top ordenado mayor-menor | 1.0 | `modelo/Almacen.java` | `topPuntajes()` (burbuja) |
| Grafica JFreeChart | 2.0 | `ui/TopPuntajes.java`, `reporte/GeneradorReporte.java` | `createBarChart()` |
| Historial en vectores | 1.0 | `modelo/Almacen.java` | atributo `historial` |
| Imagen con ImageIO | 1.0 | `reporte/GeneradorReporte.java` | `ImageIO.write()` linea 43 |
| HTML/PDF desde navegador | 1.0 | `reporte/GeneradorReporte.java` | `reporte.html` + instrucciones impresion |

## 4. Archivos de soporte

| Archivo | Proposito |
|---|---|
| `README.md` | Descripcion, como compilar y ejecutar |
| `build.bat` | Compila con javac + classpath JFreeChart |
| `run.bat` | Ejecuta la aplicacion |
| `QuetzalSpaceDefender.jar` | Ejecutable distribuible |
| `lib/jfreechart-1.5.3.jar` | Libreria de graficas |
| `.gitignore` | Ignores de compilacion |

## 5. Como compilar y ejecutar

```bat
build.bat
run.bat
```

O desde el jar:

```bat
java -cp "QuetzalSpaceDefender.jar;lib\jfreechart-1.5.3.jar" quetzal.Main
```
