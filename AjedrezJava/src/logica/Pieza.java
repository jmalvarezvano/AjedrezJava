package logica;

public abstract class Pieza {
	private Jugador jugador;
	
	public Pieza(Jugador j) {
		jugador = j;
	}

	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}
	
}
