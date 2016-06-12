package logica.estrategia;

public class MockJuegoInvertido extends MockJuegoEstandar {

	@Override
	public boolean comprobarFinJuego(int jugador) {
		boolean esJaque = jugadores[jugador].esJaque();
		boolean esAhogado = comprobarAhogado(jugadores[jugador]);
		if(esJaque && esAhogado) return true;
		if(!esJaque && esAhogado)return true;
		return esAhogado;	
		
	}
}
