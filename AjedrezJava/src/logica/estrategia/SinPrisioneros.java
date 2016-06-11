package logica.estrategia;

import logica.Movimiento;
import logica.piezas.Pieza;
import logica.recuerdo.Conserje;

public class SinPrisioneros extends JuegoEstandar {

	private boolean esFin;

	@Override
	public boolean moverYAnimarPieza(Movimiento movimiento) {

		if (esFin) return false;
		boolean destinoEsRey = false;
		if (tablero.getPieza(movimiento.destinoX, movimiento.destinoY) != null
				&& tablero.getPieza(movimiento.destinoX, movimiento.destinoY).getTipo() == Pieza.KING) {
			destinoEsRey = true;
		}
		boolean seMueve = moverPieza(movimiento);
		if (seMueve) {
			Conserje.getSingleton().add(tablero.saveStateToMemento()); // guardar
			// estado
			interfaz.animarMovimiento(movimiento);
			if (destinoEsRey) {
				interfaz.gameEnded(turno);
				esFin = true;
			}
			else
				cambiarTurno();
		}
		return seMueve;

	}

}
