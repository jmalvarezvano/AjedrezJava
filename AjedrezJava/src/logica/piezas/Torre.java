package logica.piezas;

import logica.Jugador;

public class Torre extends Pieza {

	public Torre(Jugador j) {
		super(j, Pieza.ROOK);
	}
	
	//toString para pruebas en consola
		@Override
		public String toString() {
			return "t";
		}

}
