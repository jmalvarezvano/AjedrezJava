package logica.estrategia;

import logica.FabricaPiezas;
import logica.Movimiento;
import logica.piezas.Pieza;
import logica.recuerdo.Conserje;

public class Damas extends Mediador {

	private boolean seguirAtacando;

	
	@Override
	public boolean moverPieza(Movimiento movimiento) {
		seguirAtacando = false;
		return mover(movimiento);
	}

	
	public boolean mover(Movimiento movimiento) {
		System.out.println("Jugador1: " + jugadores[0] + "Jugador2: " + jugadores[1]);
		System.out.println("intento muevo pieza de damas");

		Pieza origen = tablero.getPieza(movimiento.origenX, movimiento.origenY);
		Pieza destino = tablero.getPieza(movimiento.destinoX, movimiento.destinoY);

		// si no hay pieza o no es jugador correcto
		if (origen == null || origen.getJugador() != movimiento.jugador)
			return false;

		if (destino != null)
			return false;
		System.out.println(origen + " to " + destino);

		if (movimientoValido(movimiento)) {
			tablero.quitarPieza(movimiento.origenX, movimiento.origenY);

			// promover si es el caso
			if (movimiento.destinoY == 7 || movimiento.destinoY == 0) {
				Pieza rey = FabricaPiezas.getSingleton().crear("reydamas", origen.getJugador());
				tablero.setPieza(movimiento.destinoX, movimiento.destinoY, rey);
			} else
				tablero.setPieza(movimiento.destinoX, movimiento.destinoY, origen);
			
			
			Conserje.getSingleton().add(tablero.saveStateToMemento()); // guardar estado
			interfaz.animarMovimiento(movimiento);
			
			if(seguirAtacando) sigueAtacando(movimiento);
				else cambiarTurno();

			return true;
		}
		return false;
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
		System.out.println("Compruebo movimiento valido");

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
		System.out.println("si estoy aqui entonces va mal");

		return false;
	}

	private boolean movimientoValidoManNorte(Movimiento movimiento) {
		if (movimiento.origenY > movimiento.destinoY)
			return false;
		int xRelativo = Math.abs(movimiento.destinoX - movimiento.origenX);
		int yRelativo = Math.abs(movimiento.destinoY - movimiento.origenY);
		if (xRelativo == 1 && yRelativo == 1) {
			System.out.println("Estoy en validomanj1 y es true");
			return true;
		}
		if (atacando(movimiento)) {
			seguirAtacando = true;
			return true;
		}
		System.out.println("Estoy en validomanj1 y es false");
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

		System.out.println("SaltoX: " + xSalto + " SaltoY: " + ySalto);
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
			System.out.println("Estoy en validomanj2 y es true");

			return true;
		}
		if (atacando(movimiento)) {
			seguirAtacando = true;
			return true;
		}
		System.out.println("Estoy en validomanj2 y es false");
		return false;
	}

	private boolean movimientoValidoRey(Movimiento movimiento) {
		return movimientoValidoManNorte(movimiento) || movimientoValidoManSur(movimiento);
	}

	@Override
	public void inicializarTablero() {
		tablero.inicializarTableroDamas(jugadores[0], jugadores[1]);
	}

}
