package logica;

import presentacion.MainPane;

public abstract class Mediador {

	protected Tablero tablero;
	protected Jugador[] jugadores;
	protected MainPane interfaz;
	protected int turno;

	public Mediador() {
		jugadores = new Jugador[2];
		turno = 0;
	}

	public void setInterfaz(MainPane mainPane) {

		interfaz = mainPane;
	}

	public Jugador getTurno() {
		return jugadores[turno];
	}
	public Tablero getTablero() {
		return tablero;
	}

	public void setTablero(Tablero tablero) {
		this.tablero = tablero;
	}

	public Jugador getJugador1() {
		return jugadores[0];
	}

	public void setJugador1(Jugador jugador1) {
		this.jugadores[0] = jugador1;
	}
	
	public void juegoNuevo() {
		tablero.inicializarTablero(jugadores[0], jugadores[1]);
	}

	public Jugador getJugador2() {
		return jugadores[1];
	}

	public void setJugador2(Jugador jugador2) {
		this.jugadores[1] = jugador2;
	}

	public void cambiarTurno() {
		turno = Math.abs(turno-1);
		interfaz.setTurno(jugadores[turno]);
	}
	
	public abstract boolean moverPieza(int origenX, int origenY, int destinoX, int destinoY, Jugador j);

	
}
