package logica;

import presentacion.MainPane;

public interface Estrategia {

	public Tablero getTablero() ;

	public void setTablero(Tablero tablero);

	public Jugador getJugador1();

	public void setJugador1(Jugador jugador1);

	public Jugador getJugador2();

	public void setJugador2(Jugador jugador2);

	public void cambiarTurno();
	
	public boolean moverPieza(int origenX, int origenY, int destinoX, int destinoY, Jugador j);

	public boolean movimientoValido(int origenX, int origenY, int destinoX, int destinoY, int tipo) ;
	
	public boolean isJaqueMate(int origenX, int origenY) ;
	
	public boolean finJuego();

	public void setInterfaz(MainPane mainPane) ;

	public Jugador getTurno();

	public void promoverPeon(Celda celda);
}
