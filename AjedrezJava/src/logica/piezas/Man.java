package logica.piezas;

import logica.Jugador;

public class Man extends Pieza {

	public Man(Jugador j) {

		super(j, Pieza.MAN);
		System.out.println("inicializando man con el jugador " + j);

	}

}
