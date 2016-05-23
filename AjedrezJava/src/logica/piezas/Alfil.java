package logica.piezas;

import logica.Jugador;

public class Alfil extends Pieza {

	public Alfil(Jugador j) {
		super(j, Pieza.BISHOP);
		
	}
	
	//toString para pruebas en consola
	@Override
	public String toString() {
		return "a";
	}

}
