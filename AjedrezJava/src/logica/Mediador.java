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
		} catch(Exception e) {
			System.out.println("1");
			return false;
		}
		System.out.println(origen+ " to " + destino);
		if (origen.getPieza() == null || !origen.getPieza().getJugador().equals(j)) return false; 			//pieza origen existe y es del mismo jugador
		if (j.isJaque() && !(origen.getPieza() instanceof Rey)) return false; 								//comprobar jaque(tiene que mover el rey)
		if (destino.getPieza() != null && destino.getPieza().getJugador().equals(j)) return false;			//si pieza destino existe tiene que ser del otro jugador
		if (movimientoValido(origenX, origenY, destinoX, destinoY, origen.getPieza().getMovimiento())) {	
				destino.setPieza(origen.quitarPieza());
				if(destino.getPieza().getMovimiento().equals("peonPrimerMovimiento")) {
					destino.getPieza().setMovimiento("peon");
					System.out.println("actualizar peon");
				}
				return true;
		}		
		return false;
	}

	public boolean movimientoValido(int origenX, int origenY, int destinoX, int destinoY, String movimiento) {
		int xRelativo = Math.abs(destinoX - origenX);
		int yRelativo = Math.abs(destinoY - origenY);
		System.out.println(movimiento);
		System.out.println(xRelativo);
		System.out.println(yRelativo);

		switch (movimiento) {
		case "alfil": 
			if(xRelativo == yRelativo) {
				for(int x = origenX + 1, y = origenY + 1; x < destinoX && y < destinoY; x++, y++) {
					if(tablero.getCelda(x, y).getPieza() != null) return false;
				}
				return true;
			}
			break;
		case "caballo":
			if((xRelativo == 1 && yRelativo == 2) || (xRelativo == 2 && yRelativo == 1)) return true;
			break;
		case "peonPrimerMovimiento":
			if(xRelativo == 0) 
				if(yRelativo == 1 && tablero.getCelda(destinoX, destinoY).getPieza() == null) return true;
				else return (yRelativo == 2 && tablero.getCelda(origenX, origenY + 1).getPieza() == null);				//esta linea no funciona si el jugador tiene que avanzar hacia abajo(comprueba si hay alguien en medio en el caso de saltar 2)
			else return (xRelativo == 1 && yRelativo == 1 && tablero.getCelda(destinoX, destinoY).getPieza() != null);
		case "peon":
			if(xRelativo == 0){
				if(yRelativo == 1 && tablero.getCelda(destinoX, destinoY).getPieza() == null) return true;
			} else return (xRelativo == 1 && (yRelativo == 1) && tablero.getCelda(destinoX, destinoY).getPieza() != null);
			break;
		case "reina":
			//torre
			if(xRelativo == 0) {
				for(int y = origenY + 1; y < destinoY; y++) 
					if(tablero.getCelda(origenX, y).getPieza() != null) return false;
				return true;
			}
			else if(yRelativo == 0) {
				for(int x = origenX + 1; x < destinoX; x++) 
					if(tablero.getCelda(x, origenY).getPieza() != null) return false;
				return true;
			}		
			//alfil
			if(xRelativo == yRelativo) {
				for(int x = origenX + 1, y = origenY + 1; x < destinoX && y < destinoY; x++, y++) 
					if(tablero.getCelda(x, y).getPieza() != null) return false;				
				return true;
			}			
			break;
		case "rey":
			if(xRelativo <= 1 && yRelativo <= 1) return true;			//no tiene implementado el jaque
			break;
		case "torre":
			if(xRelativo == 0) {
				for(int y = origenY + 1; y < destinoY; y++) 
					if(tablero.getCelda(origenX, y).getPieza() != null) return false;
				return true;
			}
			else if(yRelativo == 0) {
				for(int x = origenX + 1; x < destinoX; x++) 
					if(tablero.getCelda(x, origenY).getPieza() != null) return false;
				return true;
			}
		}
		return false;
	}
}
