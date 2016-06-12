package logica.estrategia;

import logica.Movimiento;
import logica.piezas.Pieza;
import logica.recuerdo.Conserje;

public class MockSinPrisioneros extends MockJuegoEstandar{
	private boolean esFin;

	@Override
	public boolean moverYAnimarPieza(Movimiento movimiento) {
		Pieza p = null;
		if (esFin) return false;
		boolean destinoEsRey = false;
		p = tablero.getPieza(movimiento.destinoX, movimiento.destinoY);
		if (p != null && tablero.getPieza(movimiento.destinoX, movimiento.destinoY).getTipo() == Pieza.KING) {
			destinoEsRey = true;
		}
		boolean seMueve = moverPieza(movimiento);
		if (seMueve) {
			Conserje.getSingleton().add(tablero.saveStateToMemento()); // guardar
			// estado
			//interfaz.animarMovimiento(movimiento);
			if (destinoEsRey) {
				//interfaz.gameEnded(turno);
				esFin = true;
			}
			else
				cambiarTurno();
		}
		return seMueve;
	}
}
