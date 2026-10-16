# Manual de Usuario - Quetzal Space Defender

**Estudiante:** Oscar Rene Monzon Ortiz - 202402543

## 1. Requisitos
- Windows con JDK 17 instalado.
- Archivos: `QuetzalSpaceDefender.jar` + carpeta `lib/` con `jfreechart-1.5.3.jar`, o carpeta `out/` compilada.
- Ejecutar con `run.bat` o doble clic al JAR (con lib al lado).

## 2. Menú principal
Al abrir verás 4 botones:
1. **Jugar**: elige piloto de la lista y entra a la partida.
2. **Crear Piloto**: escribe nombre (mínimo 3 letras, no duplicado) y elige nave:
   - Explorador (Fácil): se mueve rápido, dispara cada 2s.
   - Caza Estelar (Normal): equilibrado, dispara cada 1s.
   - Acorazado (Difícil): se mueve lento, dispara cada 0.3s.
3. **Top de Puntajes**: tabla + gráfica de barras + botón Exportar Reporte HTML.
4. **Salir**: cierra el juego.

## 3. Cómo jugar
- Tu nave está a la izquierda (verde, naranja si bloqueado).
- Mover: flechas Arriba/Abajo o W/S.
- Disparar: ESPACIO (respeta cooldown de tu nave).
- Enemigos rojos y asteroides grises vienen de derecha a izquierda.
- Objetos:
  - Celeste (Núcleo): +150 pts y limpia enemigos, tócalo o dispárale.
  - Magenta (Cápsula): +10 pts.
  - Gris (Asteroide): al chocarlo te bloquea 2s; si chocas otro mientras bloqueado, pierdes.
  - Rojo (Nave enemiga): dispárale (+20) o esquívala; si te toca, pierdes.
- Arriba a la izquierda ves piloto, nave y puntos.
- Al perder sale ventana de resultados y tu puntaje queda en el historial.

## 4. Top y reporte
- En Top verás tabla ordenada y gráfica.
- Clic en Exportar Reporte HTML: crea `reporte.html` + `grafica_top.png` junto al JAR.
- Abre `reporte.html` en Chrome/Edge y usa Imprimir -> Guardar como PDF para entregar.

## 5. Solución de problemas
- No abre: instala JDK 17 y usa `run.bat`.
- Sin gráfica: verifica que `lib/jfreechart-1.5.3.jar` esté junto al proyecto.
- Piloto duplicado: usa otro nombre.
- Juego muy rápido/lento: es por la nave elegida, prueba otra dificultad.

## Capturas (tómalas al probar)
1. Menú principal con los 4 botones.
2. Crear piloto con las 3 naves.
3. Partida en curso con puntaje.
4. Top con gráfica.
5. `reporte.html` abierto en navegador.
