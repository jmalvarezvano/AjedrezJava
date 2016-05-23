package logica.estrategia;

import logica.Jugador;
import logica.Movimiento;
import logica.Tablero;
import presentacion.MainPane;

public interface Estrategia {
		
	public Jugador getTurno();
	
	public Tablero getTablero();

	public void setTablero(Tablero tablero);

	public Jugador getJugador1();

	public void setJugador1(Jugador jugador1);
	
	public void juegoNuevo();

	public Jugador getJugador2();

	public void setJugador2(Jugador jugador2);
	
	public abstract boolean moverPieza(Movimiento movimiento);


}
