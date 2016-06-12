package logica;

import logica.piezas.Pieza;

public abstract class Fabrica {

	public abstract Pieza crear(int p, Jugador j);
	
	public abstract Pieza crear(Pieza pieza);


}