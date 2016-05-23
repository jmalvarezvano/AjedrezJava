package logica.estrategia;

import logica.Celda;
import logica.Jugador;
import logica.piezas.Peon;
import logica.piezas.Pieza;
import logica.piezas.Rey;
import presentacion.MainPane;

public class JuegoInvertido extends Mediador {


	
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
		//if (((Rey)tablero.getCelda(origenX, origenY).getPieza()).isJaque() && !(origen.getPieza() instanceof Rey)) return false; 								//comprobar jaque(tiene que mover el rey)
		if (destino.getPieza() != null && destino.getPieza().getJugador().equals(j)) return false;			//si pieza destino existe tiene que ser del otro jugador
		if (movimientoValido(origenX, origenY, destinoX, destinoY, origen.getPieza().getTipo())) {	
				destino.setPieza(origen.quitarPieza());
				cambiarTurno();
				if(destino.getPieza().getTipo() == Pieza.PAWN && ((Peon) destino.getPieza()).isPrimerMovimiento()) {
					((Peon) destino.getPieza()).setPrimerMovimiento(false);
					System.out.println("actualizar peon");
				}
				return true;
		}		
		return false;
	}

	public boolean movimientoValido(int origenX, int origenY, int destinoX, int destinoY, int tipo) {
		int xRelativo = Math.abs(destinoX - origenX);
		int yRelativo = Math.abs(destinoY - origenY);
		System.out.println(tipo);
		System.out.println(xRelativo);
		System.out.println(yRelativo);

		switch (tipo) {
		case Pieza.BISHOP: 
			if(xRelativo == yRelativo) {
				for(int x = origenX + 1, y = origenY + 1; x < destinoX && y < destinoY; x++, y++) {
					if(tablero.getCelda(x, y).getPieza() != null) return false;
				}
				return true;
			}
			break;
		case Pieza.KNIGHT:
			if((xRelativo == 1 && yRelativo == 2) || (xRelativo == 2 && yRelativo == 1)) return true;
			break;
		case Pieza.PAWN:
			if(((Peon) tablero.getCelda(origenX, origenY).getPieza()).isPrimerMovimiento()){
				
			
			int aux;
			if (tablero.getCelda(origenX, origenY).getPieza().getJugador().equals(jugadores[0])) {
				if (origenY >= destinoY) return false;
				aux = 1;
			} else {
				if (origenY <= origenX) return false;
				aux = -1;
			}
			
			if(xRelativo == 0) 
				if(yRelativo == 1 && tablero.getCelda(destinoX, destinoY).getPieza() == null) return true;
				else return (yRelativo == 2 && tablero.getCelda(origenX, origenY + aux).getPieza() == null);				//esta linea no funciona si el jugador tiene que avanzar hacia abajo(comprueba si hay alguien en medio en el caso de saltar 2)
			else return (xRelativo == 1 && yRelativo == 1 && tablero.getCelda(destinoX, destinoY).getPieza() != null);
			
			} else {
			if (tablero.getCelda(origenX, origenY).getPieza().getJugador().equals(jugadores[0])) {
				if (origenY >= destinoY) return false;
			} else {
				if (origenY <= origenX) return false;
			}
			if(xRelativo == 0){
				if(yRelativo == 1 && tablero.getCelda(destinoX, destinoY).getPieza() == null) return true;
			} else return (xRelativo == 1 && (yRelativo == 1) && tablero.getCelda(destinoX, destinoY).getPieza() != null);
			break;
			
			}
		case Pieza.QUEEN:
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
		case Pieza.KING:
			Rey rey = (Rey)tablero.getCelda(origenX, origenY).getPieza();
			if (rey.isJaque()) {
				if (rey.getCeldasDefendidasPorRival()[destinoX][destinoY]) return false;
     		}
			if(xRelativo <= 1 && yRelativo <= 1) return true;			//no tiene implementado el jaque
			break;
		case Pieza.ROOK:
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
	
	public boolean isJaqueMate(int origenX, int origenY) {
		for (int x = -1; x < 2; x++)
			for (int y = -1; y < 2; y++)
				if (!((Rey)(tablero.getCelda(origenX, origenY).getPieza())).getCeldasDefendidasPorRival()[origenX + x][origenY + y]) return false;
		return true;
	}
	
	

	public Jugador getTurno() {
		return jugadores[turno];
	}

	
	public boolean finJuego() {
		// TODO Auto-generated method stub
		return false;
	}

	
	public void promoverPeon(Celda celda) {
		celda.setPieza(interfaz.promoverPeon());		
	}

}
