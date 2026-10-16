# Manual Técnico - Quetzal Space Defender

**Estudiante:** Oscar Rene Monzon Ortiz - 202402543
**Auxiliar:** Eduardo
**Lenguaje:** Java 17 + Swing
**Librerías:** JFreeChart 1.5.3 (gráficas), `java.io.*` (FileWriter/PrintWriter/File), `javax.imageio.ImageIO` (PNG)

## 1. Estructura del proyecto
```
src/quetzal/Main.java
src/quetzal/modelo/TipoNave.java (enum dificultad)
src/quetzal/modelo/Piloto.java
src/quetzal/modelo/Partida.java
src/quetzal/modelo/Almacen.java (Vector pilotos + Vector historial)
src/quetzal/juego/ProyectilHilo.java (Thread, avanza X, sleep 30ms)
src/quetzal/juego/EnemigoHilo.java (Thread, retrocede X, sleep por nave)
src/quetzal/juego/PanelJuego.java (KeyListener + Runnable generador + colisiones)
src/quetzal/juego/VentanaJuego.java (JFrame + resultados)
src/quetzal/ui/MenuPrincipal.java (4 botones)
src/quetzal/ui/DialogoPiloto.java (validaciones)
src/quetzal/ui/TopPuntajes.java (JTable + JFreeChart + export)
src/quetzal/reporte/GeneradorReporte.java (HTML/CSS + PNG)
lib/jfreechart-1.5.3.jar
```

## 2. Lógica general
- Side scroller horizontal: enemigos nacen en `X=ANCHO` y van a `X=-40`.
- Generador (`PanelJuego.run`) cada 900ms crea `EnemigoHilo` con `y` aleatoria.
- Cada proyectil y enemigo es `Thread` propio que actualiza `(x,y)` y llama al panel con `synchronized` para colisiones.
- Dificultad por nave:
  - Explorador: `velocidadJugador=8`, `cooldown=2000ms`, `sleepEnemigo=40ms`
  - Caza: `5`, `1000ms`, `30ms`
  - Acorazado: `3`, `300ms`, `20ms`
- Colisiones por distancia `|dx|<28-38` y `|dy|<28-38`.
- Mecánicas:
  - Núcleo: +150 y `destruirEnemigosVisibles()` (+10 c/u).
  - Asteroide: primer choque bloquea 2s (`bloqueadoHasta`), segundo choque en bloqueo = derrota.
  - Cápsula: +10 al tocar o disparar.
  - Nave enemiga: +20 al destruir con disparo, derrota al chocar.
- Derrota: `terminarPartida()` registra `Partida` en `Almacen` (vectores), detiene hilos y muestra `JOptionPane`.

## 3. Métodos importantes
- `Almacen.agregarPiloto(nombre, nave)`: valida largo>=3 y no duplicado.
- `Almacen.topPuntajes(limite)`: copia Vector + burbuja descendente, devuelve top.
- `PanelJuego.revisarImpactoProyectil(p)`: synchronized, recorre Vector enemigos.
- `PanelJuego.revisarColisionJugador(e)`: synchronized, aplica reglas derrota/bloqueo/premio.
- `GeneradorReporte.exportarTop()`: crea `DefaultCategoryDataset`, `ChartFactory.createBarChart`, `chart.createBufferedImage`, `ImageIO.write(png)`, `FileWriter+PrintWriter(html)`.

## 4. Restricciones cumplidas
- Solo `Vector`, sin `ArrayList/LinkedList/List/HashMap`.
- Archivos solo con `java.io.*`.
- POO: enum, clases modelo, herencia `JFrame/JPanel/Thread`, encapsulamiento.
- Hilos con `sleep()` modulado por dificultad.
- Interfaz gráfica Swing, nada en consola salvo arranque.

## 5. Compilación y JAR
```bat
build.bat
run.bat
```
JAR manual:
```bat
"C:\Program Files\Microsoft\jdk-17.0.20.101-hotspot\bin\jar.exe" --create --file QuetzalSpaceDefender.jar --main-class quetzal.Main -C out .
```
Ejecutar JAR:
```bat
java -cp "QuetzalSpaceDefender.jar;lib\jfreechart-1.5.3.jar" quetzal.Main
```
