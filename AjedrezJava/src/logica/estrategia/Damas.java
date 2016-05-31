package logica.estrategia;

import logica.Movimiento;

public class Damas extends Mediador {

	@Override
	public boolean moverPieza(Movimiento movimiento) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void inicializarTablero() {
		tablero.inicializarTableroDamas(jugadores[0], jugadores[1]);
	}

}
