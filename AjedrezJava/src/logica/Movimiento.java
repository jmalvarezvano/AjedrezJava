package logica;

public class Movimiento {

	public int origenX;
	public int origenY;
	public int destinoX;
	public int destinoY;
	public Jugador jugador;
	
	public Movimiento(int origenX, int origenY, int destinoX, int destinoY, Jugador jugador) {
		this.origenX = origenX;
		this.origenY = origenY;
		this.destinoX = destinoX;
		this.destinoY = destinoY;
		this.jugador = jugador;
	}
	
}
