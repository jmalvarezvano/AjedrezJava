package logica.recuerdo;
/*
 * comentario de prueba
 * otra prueba
 * prueba dani
 */

import logica.Celda;
import logica.Fabrica;
import logica.FabricaPiezas;
import logica.Jugador;
import logica.estrategia.Mediador;

public class Tablero {
	private Celda[][] celdas;
	private Mediador mediador;
	Fabrica fabricaPiezas;

	public Tablero(Mediador m) {
		mediador = m;
		fabricaPiezas = FabricaPiezas.getSingleton();
		// this.inicializarTablero(mediador.getJugador1(),
		// mediador.getJugador2());
	}

	public void inicializarTableroEstandar(Jugador j1, Jugador j2) {

		celdas = new Celda[8][8];
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				celdas[i][j] = new Celda();

		celdas[0][0].setPieza(fabricaPiezas.crear("torre", j1));
		celdas[0][7].setPieza(fabricaPiezas.crear("torre", j2));
		celdas[7][0].setPieza(fabricaPiezas.crear("torre", j1));
		celdas[7][7].setPieza(fabricaPiezas.crear("torre", j2));
		celdas[4][0].setPieza(fabricaPiezas.crear("rey", j1));
		celdas[4][7].setPieza(fabricaPiezas.crear("rey", j2));
		celdas[3][0].setPieza(fabricaPiezas.crear("reina", j1));
		celdas[3][7].setPieza(fabricaPiezas.crear("reina", j2));
		for (int i = 0; i < 8; i++) {
			celdas[i][1].setPieza(fabricaPiezas.crear("peon", j1));
			celdas[i][6].setPieza(fabricaPiezas.crear("peon", j2));
		}
		celdas[1][0].setPieza(fabricaPiezas.crear("caballo", j1));
		celdas[6][0].setPieza(fabricaPiezas.crear("caballo", j1));
		celdas[1][7].setPieza(fabricaPiezas.crear("caballo", j2));
		celdas[6][7].setPieza(fabricaPiezas.crear("caballo", j2));
		celdas[2][0].setPieza(fabricaPiezas.crear("alfil", j1));
		celdas[5][0].setPieza(fabricaPiezas.crear("alfil", j1));
		celdas[2][7].setPieza(fabricaPiezas.crear("alfil", j2));
		celdas[5][7].setPieza(fabricaPiezas.crear("alfil", j2));
	}

	public void inicializarTableroInvertido(Jugador j1, Jugador j2) {
		this.inicializarTableroEstandar(j1, j2);
	}

	public void inicializarTableroDamas(Jugador j1, Jugador j2) {
		// a implementar
	}

	// toString para pruebas en consola
	@Override
	public String toString() {
		String out = new String();
		for (int i = 7; i >= 0; i--) {
			out += "\n" + i;
			for (int j = 0; j <= 7; j++)
				out += " " + celdas[j][i];
		}
		out += "\n ";
		for (int j = 0; j <= 7; j++)
			out += " " + j;
		return out;
	}

	public Celda[][] getCeldas() {
		return celdas;
	}

	public void setCeldas(Celda[][] celdas) {
		this.celdas = celdas;
	}
	
	
	//patrón Memento
	public Memento saveStateToMemento() {
		return new Memento(celdas.clone());
	}

	public void getStateFromMemento(Memento Memento) {
		celdas = Memento.getState();
	}
	

	public Mediador getMediador() {
		return mediador;
	}

	public void setMediador(Mediador mediador) {
		this.mediador = mediador;
	}

	public Celda getCelda(int x, int y) {
		return celdas[x][y];
	}
}
