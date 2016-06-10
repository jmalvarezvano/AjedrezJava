package logica.piezas;

import logica.Jugador;

public class Rey extends Pieza {
	
	public Rey(Jugador j) {
		super(j, Pieza.KING);
	}
	
	
	
	//toString para pruebas en consola
		@Override
		public String toString() {
			return "y";
		}
}
