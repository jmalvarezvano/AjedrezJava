package logica;

public class Mediador {
	private Tablero tablero;
	private Jugador jugador1;
	private Jugador jugador2;
	
	public Mediador() {
		
	}

	public Tablero getTablero() {
		return tablero;
	}

	public void setTablero(Tablero tablero) {
		this.tablero = tablero;
	}

	public Jugador getJugador1() {
		return jugador1;
	}

	public void setJugador1(Jugador jugador1) {
		this.jugador1 = jugador1;
	}

	public Jugador getJugador2() {
		return jugador2;
	}

	public void setJugador2(Jugador jugador2) {
		this.jugador2 = jugador2;
	}

	public boolean moverPieza(int origenX, int origenY, int destinoX, int destinoY, Jugador j) {
		Celda origen = tablero.getCelda(origenX, origenY);
		Celda destino = tablero.getCelda(destinoX, destinoY);
		
		if (origen.getPieza() == null || !origen.getPieza().getJugador().equals(j)) return false;
		
		return false;
	}

	
	
}
