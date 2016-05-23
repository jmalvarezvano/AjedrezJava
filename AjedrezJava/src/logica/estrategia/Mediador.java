package logica.estrategia;

import logica.Jugador;
import logica.Subject;
import logica.Tablero;
import presentacion.MainPane;

public abstract class Mediador implements Estrategia, Subject {

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
	
	public void actualizarTurnoInterfaz() {
		interfaz.setTurno(jugadores[turno]);
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

	protected void cambiarTurno() {
		turno = Math.abs(turno-1);
	}
	
}
