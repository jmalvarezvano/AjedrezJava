package logica;

public class Mediador {
	private Tablero tablero;
	private Jugador jugador1;
	private Jugador jugador2;
	
	public Mediador() {
		
	}

	public Tablero getTablero() {
		return tablero;
	}

	public void setTablero(Tablero tablero) {
		this.tablero = tablero;
	}

	public Jugador getJugador1() {
		return jugador1;
	}

	public void setJugador1(Jugador jugador1) {
		this.jugador1 = jugador1;
	}

	public Jugador getJugador2() {
		return jugador2;
	}

	public void setJugador2(Jugador jugador2) {
		this.jugador2 = jugador2;
	}

	public boolean moverPieza(int origenX, int origenY, int destinoX, int destinoY, Jugador j) {
		Celda origen;
		Celda destino;
		try{
		origen = tablero.getCelda(origenX, origenY);		
		destino = tablero.getCelda(destinoX, destinoY);
		} catch(Exception e) {return false;}		
		if (origen.getPieza() == null || !origen.getPieza().getJugador().equals(j)) return false;
		if (destino.getPieza().getJugador().equals(j)) return false;
		if (movimientoValido(origenX, origenY, destinoX, destinoY, origen.getPieza().getMovimiento()) {
				destino.setPieza(origen.getPieza());
				return true;
		}		
		return false;
	}
	
	public boolean movimientoValido(int origenX, int origenY, int destinoX, int destinoY, String movimiento) {
		int xRelativo = destinoX - origenX;
		int yRelativo = destinoY - origenY;
		switch (movimiento) {
		case "alfil": 
			if(Math.abs(xRelativo) == Math.abs(yRelativo)){
				for(int x = origenX + 1, y = origenY + 1; x < destinoX && y < destinoY; x++, y++) {
					if(tablero.getCelda(x, y).getPieza() != null) return false;
				}
				return true;
			}
			break;
		case "caballo":
			if(Math.abs(xRelativo) 
		}
		return false;
	}
	
	
}
