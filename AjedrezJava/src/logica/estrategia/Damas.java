package logica.estrategia;

import logica.FabricaPiezas;
import logica.Movimiento;
import logica.piezas.Pieza;
import logica.recuerdo.Conserje;

public class Damas extends Estrategia {
	
	private int turnos;
	private boolean esFin;
	private static int MAX_TURNOS = 60;

	private boolean seguirAtacando;

	
	@Override
	public boolean moverYAnimarPieza(Movimiento movimiento) {
		seguirAtacando = false;
		if (esFin) {
			return false;
		}
		boolean seMueve = mover(movimiento);
		if (seMueve) esFin();
		return seMueve;
	}

	
	public boolean mover(Movimiento movimiento) {
		
		Pieza origen = tablero.getPieza(movimiento.origenX, movimiento.origenY);
		Pieza destino = tablero.getPieza(movimiento.destinoX, movimiento.destinoY);

		// si no hay pieza o no es jugador correcto
		if (origen == null || origen.getJugador() != movimiento.jugador)
			return false;

		if (destino != null)
			return false;

		if (movimientoValido(movimiento)) {
			tablero.quitarPieza(movimiento.origenX, movimiento.origenY);

			// promover si es el caso
			if (movimiento.destinoY == 7 || movimiento.destinoY == 0) {
				Pieza rey = FabricaPiezas.getSingleton().crear(Pieza.CHECKERS_KING, origen.getJugador());
				tablero.setPieza(movimiento.destinoX, movimiento.destinoY, rey);
			} else
				tablero.setPieza(movimiento.destinoX, movimiento.destinoY, origen);
			
			
			Conserje.getSingleton().add(tablero.saveStateToMemento()); // guardar estado
			interfaz.animarMovimiento(movimiento);
						
			if(seguirAtacando) sigueAtacando(movimiento);
				else cambiarTurno();
			turnos++;
			
			return true;
		}
		return false;
	}

	private void esFin() {
		int contJ1 = 0;
		int contJ2 = 0;
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++)
				if(tablero.getPieza(i, j) != null) 
					if(tablero.getPieza(i, j).getJugador().equals(jugadores[0])) {
						contJ1++;
					} else contJ2++;
		
		if(contJ1 == 0) {
			esFin = true;
			interfaz.gameEnded(1);
		}
		if(contJ2 == 0) {
			esFin = true;
			interfaz.gameEnded(0);
		}
		if(turnos >= MAX_TURNOS) {
			esFin = true;
			interfaz.gameEnded(2);
		}
		
	}


	private boolean sigueAtacando(Movimiento movimiento) {
		try {
		if(mover(new Movimiento(movimiento.destinoX, movimiento.destinoY, 
				movimiento.destinoX - 2, movimiento.destinoY + 2, movimiento.jugador))) return true;
		} catch (ArrayIndexOutOfBoundsException e){	}
		try {
		if(mover(new Movimiento(movimiento.destinoX, movimiento.destinoY, 
				movimiento.destinoX + 2, movimiento.destinoY + 2, movimiento.jugador))) return true;
		} catch (ArrayIndexOutOfBoundsException e){	}
		try {
		if(mover(new Movimiento(movimiento.destinoX, movimiento.destinoY,  
				movimiento.destinoX - 2, movimiento.destinoY - 2, movimiento.jugador))) return true;
		} catch (ArrayIndexOutOfBoundsException e){	}
		try {
		if(mover(new Movimiento(movimiento.destinoX, movimiento.destinoY, 
				movimiento.destinoX + 2, movimiento.destinoY - 2, movimiento.jugador))) return true;
		} catch (ArrayIndexOutOfBoundsException e){	}
		seguirAtacando = false;
		cambiarTurno();
		return false;
	}

	private boolean movimientoValido(Movimiento movimiento) {

		if (movimiento.jugador.equals(jugadores[0])) {
			switch (tablero.getPieza(movimiento.origenX, movimiento.origenY).getTipo()) {
			case Pieza.MAN:
				return movimientoValidoManNorte(movimiento);
			case Pieza.CHECKERS_KING:
				return movimientoValidoRey(movimiento);
			}
		} else {
			switch (tablero.getPieza(movimiento.origenX, movimiento.origenY).getTipo()) {
			case Pieza.MAN:
				return movimientoValidoManSur(movimiento);
			case Pieza.CHECKERS_KING:
				return movimientoValidoRey(movimiento);
			}
		}
		return false;
	}

	private boolean movimientoValidoManNorte(Movimiento movimiento) {
		if (movimiento.origenY > movimiento.destinoY)
			return false;
		int xRelativo = Math.abs(movimiento.destinoX - movimiento.origenX);
		int yRelativo = Math.abs(movimiento.destinoY - movimiento.origenY);
		if (xRelativo == 1 && yRelativo == 1) {
			return true;
		}
		if (atacando(movimiento)) {
			seguirAtacando = true;
			return true;
		}
		return false;
	}

	private boolean atacando(Movimiento movimiento) {
		int xRelativo = Math.abs(movimiento.destinoX - movimiento.origenX);
		int yRelativo = Math.abs(movimiento.destinoY - movimiento.origenY);

		int ySalto;
		int xSalto;

		if (movimiento.destinoX > movimiento.origenX)
			xSalto = movimiento.destinoX - 1;
		else
			xSalto = movimiento.origenX - 1;

		if (movimiento.destinoY > movimiento.origenY)
			ySalto = movimiento.destinoY - 1;
		else
			ySalto = movimiento.origenY - 1;

		if (xRelativo == 2 && yRelativo == 2) {
			Pieza origen = tablero.getPieza(movimiento.origenX, movimiento.origenY);
			Pieza salto = tablero.getPieza(xSalto, ySalto);
			if (salto != null && !salto.getJugador().equals(origen.getJugador())) {
				tablero.quitarPieza(xSalto, ySalto);
				return true;				
			}
		}
		return false;
	}

	private boolean movimientoValidoManSur(Movimiento movimiento) {
		if (movimiento.origenY < movimiento.destinoY)
			return false;
		int xRelativo = Math.abs(movimiento.destinoX - movimiento.origenX);
		int yRelativo = Math.abs(movimiento.destinoY - movimiento.origenY);
		if (xRelativo == 1 && yRelativo == 1) {
			return true;
		}
		if (atacando(movimiento)) {
			seguirAtacando = true;
			return true;
		}
		return false;
	}

	private boolean movimientoValidoRey(Movimiento movimiento) {
		return movimientoValidoManNorte(movimiento) || movimientoValidoManSur(movimiento);
	}

	@Override
	public void inicializarTablero() {
		tablero.inicializarTableroDamas(jugadores[0], jugadores[1]);
		turnos = 0;
	}

}
