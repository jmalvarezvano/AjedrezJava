package logica;

public abstract class Pieza {
	private String movimiento;
	private Jugador jugador;
	
	public Pieza(Jugador j, String m) {
		jugador = j;
	}

	public String getMovimiento() {
		return movimiento;
	}

	public void setMovimiento(String movimiento) {
		this.movimiento = movimiento;
	}

	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}
	
}
