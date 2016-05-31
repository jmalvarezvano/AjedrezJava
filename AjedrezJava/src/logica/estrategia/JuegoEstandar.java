package logica.estrategia;

import logica.Celda;
import logica.FabricaPiezas;
import logica.Jugador;
import logica.Movimiento;
import logica.piezas.Peon;
import logica.piezas.Pieza;
import logica.piezas.Rey;
import logica.recuerdo.Conserje;

public class JuegoEstandar extends Mediador {

	public boolean moverPieza(Movimiento movimiento) {
		Celda origen;
		Celda destino;
		
		origen = tablero.getCelda(movimiento.origenX, movimiento.origenY);
		destino = tablero.getCelda(movimiento.destinoX, movimiento.destinoY);
		
		System.out.println(origen + " to " + destino);
		if (origen.getPieza() == null || !origen.getPieza().getJugador().equals(movimiento.jugador))
			return false; // pieza origen existe y es del mismo jugador
		// if (((Rey)tablero.getCelda(origenX, origenY).getPieza()).isJaque() &&
		// !(origen.getPieza() instanceof Rey)) return false; //comprobar
		// jaque(tiene que mover el rey)
		if (destino.getPieza() != null && destino.getPieza().getJugador().equals(movimiento.jugador))
			return false; // si pieza destino existe tiene que ser del otro
							// jugador
		
		if (movimientoValido(movimiento, origen.getPieza().getTipo())) {			
			destino.setPieza(origen.quitarPieza());
			if (destino.getPieza().getTipo() == Pieza.PAWN) {
				if (movimiento.jugador == jugadores[0] && movimiento.destinoY == 7)
					promoverPeon(destino);
				if (movimiento.jugador == jugadores[1] && movimiento.destinoY == 0)
					promoverPeon(destino);
			}
			Conserje.getSingleton().add(tablero.saveStateToMemento());  //guardar estado
			cambiarTurno();
			if (destino.getPieza().getTipo() == Pieza.PAWN && ((Peon) destino.getPieza()).isPrimerMovimiento()) {
				((Peon) destino.getPieza()).setPrimerMovimiento(false);
				System.out.println("actualizar peon");
			}
			return true;
		}
		return false;
	}

	private void promoverPeon(Celda celda) {
		boolean esBlanco = celda.getPieza().getJugador().equals(jugadores[0]);
		int tipoNuevaPieza = interfaz.promoverPeon(esBlanco);
		Pieza nuevaPieza = null;

		/*cambiando la fabrica para que funcione con tipos de piezas en lugar de string
		 * se puede evitar este switch
		 */
		switch (tipoNuevaPieza) {
		case Pieza.BISHOP:
			nuevaPieza = FabricaPiezas.getSingleton().crear("alfil", celda.getPieza().getJugador());
			break;
		case Pieza.KNIGHT:
			nuevaPieza = FabricaPiezas.getSingleton().crear("caballo", celda.getPieza().getJugador());
			break;
		case Pieza.QUEEN:
			nuevaPieza = FabricaPiezas.getSingleton().crear("reina", celda.getPieza().getJugador());
			break;
		case Pieza.ROOK:
			nuevaPieza = FabricaPiezas.getSingleton().crear("torre", celda.getPieza().getJugador());
			break;
		}
		celda.setPieza(nuevaPieza);
	}

	private boolean movimientoValido(Movimiento movimiento, int tipoPieza) {
		int xRelativo = Math.abs(movimiento.destinoX - movimiento.origenX);
		int yRelativo = Math.abs(movimiento.destinoY - movimiento.origenY);
		System.out.println(tipoPieza);
		System.out.println(xRelativo);
		System.out.println(yRelativo);
		switch (tipoPieza) {
		case Pieza.BISHOP:
			return esMovimientoValidoAlfil(movimiento);
		case Pieza.KNIGHT:
			return esMovimientoValidoCaballo(movimiento);
		case Pieza.PAWN:
			return esMovimientoValidoPeon(movimiento);
		case Pieza.QUEEN:
			return esMovimientoValidoReina(movimiento);
		case Pieza.KING:
			return esMovimientoValidoRey(movimiento);
		case Pieza.ROOK:
			return esMovimientoValidoTorre(movimiento);
		}
		return false;
	}

	private boolean esMovimientoValidoAlfil(Movimiento movimiento) {
		int xRelativo = Math.abs(movimiento.destinoX - movimiento.origenX);
		int yRelativo = Math.abs(movimiento.destinoY - movimiento.origenY);
		if (xRelativo == yRelativo) {
			for (int x = movimiento.origenX + 1, y = movimiento.origenY + 1; x < movimiento.destinoX
					&& y < movimiento.destinoY; x++, y++) {
				if (tablero.getCelda(x, y).getPieza() != null)
					return false;
			}
			return true;
		}
		return false;
	}

	private boolean esMovimientoValidoCaballo(Movimiento movimiento) {
		int xRelativo = Math.abs(movimiento.destinoX - movimiento.origenX);
		int yRelativo = Math.abs(movimiento.destinoY - movimiento.origenY);
		return ((xRelativo == 1 && yRelativo == 2) || (xRelativo == 2 && yRelativo == 1));
	}

	private boolean esMovimientoValidoPeon(Movimiento movimiento) {
		int xRelativo = Math.abs(movimiento.destinoX - movimiento.origenX);
		int yRelativo = Math.abs(movimiento.destinoY - movimiento.origenY);
		if (((Peon) tablero.getCelda(movimiento.origenX, movimiento.origenY).getPieza()).isPrimerMovimiento()) {

			int aux;
			if (tablero.getCelda(movimiento.origenX, movimiento.origenY).getPieza().getJugador().equals(jugadores[0])) {
				if (movimiento.origenY >= movimiento.destinoY)
					return false;
				aux = 1;
			} else {
				if (movimiento.origenY <= movimiento.origenX)
					return false;
				aux = -1;
			}

			if (xRelativo == 0)
				if (yRelativo == 1 && tablero.getCelda(movimiento.destinoX, movimiento.destinoY).getPieza() == null)
					return true;
				else
					return (yRelativo == 2
							&& tablero.getCelda(movimiento.origenX, movimiento.origenY + aux).getPieza() == null);
			else
				return (xRelativo == 1 && yRelativo == 1
						&& tablero.getCelda(movimiento.destinoX, movimiento.destinoY).getPieza() != null);

		} else {
			if (tablero.getCelda(movimiento.origenX, movimiento.origenY).getPieza().getJugador().equals(jugadores[0])) {
				if (movimiento.origenY >= movimiento.destinoY)
					return false;
			} else {
				if (movimiento.origenY <= movimiento.origenX)
					return false;
			}
			if (xRelativo == 0) {
				if (yRelativo == 1 && tablero.getCelda(movimiento.destinoX, movimiento.destinoY).getPieza() == null)
					return true;
			} else
				return (xRelativo == 1 && (yRelativo == 1)
						&& tablero.getCelda(movimiento.destinoX, movimiento.destinoY).getPieza() != null);
		}
		return false;
	}

	private boolean esMovimientoValidoRey(Movimiento movimiento) {
		int xRelativo = Math.abs(movimiento.destinoX - movimiento.origenX);
		int yRelativo = Math.abs(movimiento.destinoY - movimiento.origenY);

		Rey rey = (Rey) tablero.getCelda(movimiento.origenX, movimiento.origenY).getPieza();
		if (rey.isJaque()) {
			if (rey.getCeldasDefendidasPorRival()[movimiento.destinoX][movimiento.destinoY])
				return false;
		}
		if (xRelativo <= 1 && yRelativo <= 1)
			return true;
		return false;
	}

	private boolean esMovimientoValidoReina(Movimiento movimiento) {
		return esMovimientoValidoAlfil(movimiento) || esMovimientoValidoTorre(movimiento);
	}

	private boolean esMovimientoValidoTorre(Movimiento movimiento) {
		int xRelativo = Math.abs(movimiento.destinoX - movimiento.origenX);
		int yRelativo = Math.abs(movimiento.destinoY - movimiento.origenY);
		if (xRelativo == 0) {
			for (int y = movimiento.origenY + 1; y < movimiento.destinoY; y++)
				if (tablero.getCelda(movimiento.origenX, y).getPieza() != null)
					return false;
			return true;
		} else if (yRelativo == 0) {
			for (int x = movimiento.origenX + 1; x < movimiento.destinoX; x++)
				if (tablero.getCelda(x, movimiento.origenY).getPieza() != null)
					return false;
			return true;
		}
		return false;
	}

	private boolean isJaqueMate(int origenX, int origenY) {
		for (int x = -1; x < 2; x++)
			for (int y = -1; y < 2; y++)
				if (!((Rey) (tablero.getCelda(origenX, origenY).getPieza())).getCeldasDefendidasPorRival()[origenX
						+ x][origenY + y])
					return false;
		return true;
	}

	private boolean ahogado(Jugador jugador, int origenX, int origenY) {
		/*
		 * if(!rey.isJaque()){ return (rey.esCeldaDefendidaPorRival(origenX,
		 * origenY + 1) && rey.esCeldaDefendidaPorRival(origenX, origenY - 1) &&
		 * rey.esCeldaDefendidaPorRival(origenX + 1, origenY) &&
		 * rey.esCeldaDefendidaPorRival(origenX - 1, origenY)); }
		 */
		return false;
	}

	@Override
	public void inicializarTablero() {
		tablero.inicializarTableroEstandar(jugadores[0], jugadores[1]);
	}

	public boolean finJuego() {
		return false;
	}
}
