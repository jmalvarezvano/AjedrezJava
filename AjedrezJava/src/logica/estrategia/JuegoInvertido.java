package logica.estrategia;

public class JuegoInvertido extends JuegoEstandar {

	@Override
	protected boolean comprobarFinJuego(int jugador) {
		boolean esJaque = jugadores[jugador].esJaque();
		boolean esAhogado = comprobarAhogado(jugadores[jugador]);
		if(esJaque && esAhogado) interfaz.gameEnded(jugador);
		if(!esJaque && esAhogado) interfaz.gameEnded(Math.abs(2));		
		return esAhogado;		  
	}
}
