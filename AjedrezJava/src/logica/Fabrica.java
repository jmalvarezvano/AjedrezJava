package logica;

import logica.piezas.Pieza;

public abstract class Fabrica {

	public abstract Pieza crear(String p, Jugador j);

}