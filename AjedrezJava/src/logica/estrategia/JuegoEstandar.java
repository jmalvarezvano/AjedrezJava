package logica.estrategia;

import logica.FabricaPiezas;
import logica.Jugador;
import logica.Movimiento;
import logica.piezas.Peon;
import logica.piezas.Pieza;
import logica.piezas.Rey;
import logica.recuerdo.Conserje;

public class JuegoEstandar extends Mediador {

	// comprueba el jaque despues de mover y deshace si es el caso o anima el
	// movimiento
	public boolean moverYAnimarPieza(Movimiento movimiento) {
		Pieza origen = tablero.getPieza(movimiento.origenX, movimiento.origenY);
		boolean seMueve = moverPieza(movimiento);
		if (seMueve) {
			actualizarJaques();
			if (movimiento.jugador.esJaque()) {
				tablero.getStateFromMemento(Conserje.getSingleton().getLast());
				actualizarJaques();
				return false;
			}
			interfaz.animarMovimiento(movimiento);
			if (origen.getTipo() == Pieza.PAWN) {
				if (movimiento.jugador == jugadores[0] && movimiento.destinoY == 7)
					tablero.setPieza(movimiento.destinoX, movimiento.destinoY, promoverPeon(origen.getJugador()));
				if (movimiento.jugador == jugadores[1] && movimiento.destinoY == 0)
					tablero.setPieza(movimiento.destinoX, movimiento.destinoY, promoverPeon(origen.getJugador()));
			}
			Conserje.getSingleton().add(tablero.saveStateToMemento()); // guardar
			// estado
			cambiarTurno();
			actualizarJaqueInterfaz();
			comprobarFinJuego(turno);
			return true;
		}
		return false;

	}

	// muevue sin comprobar jaque ni cambiar turno
	protected boolean moverPieza(Movimiento movimiento) {
		Pieza origen = tablero.getPieza(movimiento.origenX, movimiento.origenY);
		if (movimientoValidoSinJaque(movimiento)) {
			tablero.quitarPieza(movimiento.origenX, movimiento.origenY);
			tablero.setPieza(movimiento.destinoX, movimiento.destinoY, origen);
			if (origen.getTipo() == Pieza.PAWN && ((Peon) origen).isPrimerMovimiento()) {
				((Peon) origen).setPrimerMovimiento(false);
			}
			return true;
		}
		return false;
	}

	// parecido a movimientoValido pero tiene en cuenta el jaque - mas costoso
	private boolean movimientoValidoConJaque(Movimiento movimiento) {
		boolean seMueve = moverPieza(movimiento);
		boolean res = false;
		if (seMueve) {
			actualizarJaques();
			if (!movimiento.jugador.esJaque()) {
				res = true;
			}
		}
		tablero.getStateFromMemento(Conserje.getSingleton().getLast());
		actualizarJaques();
		return res;
	}

	// comprueba si el movimiento esta permitido no tiene en cuenta el jaque -
	// menos costoso
	private boolean movimientoValidoSinJaque(Movimiento movimiento) {
		Pieza origen;
		Pieza destino;
		try {
			origen = tablero.getPieza(movimiento.origenX, movimiento.origenY);
			destino = tablero.getPieza(movimiento.destinoX, movimiento.destinoY);
		} catch (Exception e) {
			return false;
		}
		if (origen == null || !origen.getJugador().equals(movimiento.jugador))
			return false; // pieza origen existe y es del mismo jugador
		if (destino != null && (destino.getJugador().equals(movimiento.jugador)))
			return false; // si pieza destino existe tiene que ser del otro
							// jugador y no puede ser rey
		return movimientoValidoTipoPieza(movimiento, origen.getTipo());

	}

	private boolean atacandoAlRey(Jugador jugador, int[] posicionesRey) {
		Movimiento movimiento = new Movimiento();
		boolean esValido;
		movimiento.jugador = jugador;
		for (int i = 0; i <= 7; i++)
			for (int j = 0; j <= 7; j++) {
				movimiento.origenX = i;
				movimiento.origenY = j;
				movimiento.destinoX = posicionesRey[0];
				movimiento.destinoY = posicionesRey[1];
				esValido = movimientoValidoSinJaque(movimiento);
				if (esValido) {
					return true;
				}
			}
		return false;
	}

	private void actualizarJaques() {
		boolean jugador1AtacandoAlRey;
		boolean jugador2AtacandoAlRey;
		int[] posReyJ1 = tablero.getPosRey(jugadores[0]);
		int[] posReyJ2 = tablero.getPosRey(jugadores[1]);
		jugador1AtacandoAlRey = atacandoAlRey(jugadores[0], posReyJ2);
		jugador2AtacandoAlRey = atacandoAlRey(jugadores[1], posReyJ1);
		jugadores[0].setEsJaque(jugador2AtacandoAlRey);
		jugadores[1].setEsJaque(jugador1AtacandoAlRey);

	}

	private void actualizarJaqueInterfaz() {
		interfaz.setJaqueJ1(jugadores[0].esJaque());
		interfaz.setJaqueJ2(jugadores[1].esJaque());
	}

	private Pieza promoverPeon(Jugador jugador) {
		boolean esBlanco = jugador.equals(jugadores[0]);
		int tipoNuevaPieza = interfaz.promoverPeon(esBlanco);
		Pieza nuevaPieza = null;
		/*
		 * cambiando la fabrica para que funcione con tipos de piezas en lugar
		 * de string se puede evitar este switch
		 */
		switch (tipoNuevaPieza) {
		case Pieza.BISHOP:
			nuevaPieza = FabricaPiezas.getSingleton().crear("alfil", jugador);
			break;
		case Pieza.KNIGHT:
			nuevaPieza = FabricaPiezas.getSingleton().crear("caballo", jugador);
			break;
		case Pieza.QUEEN:
			nuevaPieza = FabricaPiezas.getSingleton().crear("reina", jugador);
			break;
		case Pieza.ROOK:
			nuevaPieza = FabricaPiezas.getSingleton().crear("torre", jugador);
			break;
		}
		return nuevaPieza;
	}

	private boolean movimientoValidoTipoPieza(Movimiento movimiento, int tipoPieza) {
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
			int incrementoX;
			int incrementoY;
			if (movimiento.destinoX > movimiento.origenX)
				incrementoX = 1;
			else
				incrementoX = -1;
			if (movimiento.destinoY > movimiento.origenY)
				incrementoY = 1;
			else
				incrementoY = -1;

			for (int x = movimiento.origenX + incrementoX, y = movimiento.origenY + incrementoY;
					x != movimiento.destinoX && y != movimiento.destinoY; x = x + incrementoX, y = y + incrementoY) {
				if (tablero.getPieza(x, y) != null) {
					return false;
				}
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

		if (yRelativo > 2 || yRelativo == 0)
			return false;
		if (xRelativo > 1)
			return false;
		if(xRelativo == 0 && tablero.getPieza(movimiento.destinoX, movimiento.destinoY) != null) return false;
		yRelativo = movimiento.destinoY - movimiento.origenY;
		if (((Peon) tablero.getPieza(movimiento.origenX, movimiento.origenY)).isPrimerMovimiento()) {
			if (tablero.getPieza(movimiento.origenX, movimiento.origenY).getJugador().equals(getJugador1())) {
				if (yRelativo == 2 && xRelativo == 0)
					return true;
			} else {
				if (yRelativo == -2 && xRelativo == 0)
					return true;
			}
		}

		if (tablero.getPieza(movimiento.origenX, movimiento.origenY).getJugador().equals(getJugador1())) {
			if (yRelativo == 1)
				if (xRelativo == 0)
					return true;
				else {
					Pieza p = tablero.getPieza(movimiento.destinoX, movimiento.destinoY);
					if (p != null) {
						Jugador jug = p.getJugador();
						if (xRelativo == 1 && (jug != null) && (jug.equals(getJugador2())))
							return true;
					}
				}
		} else {
			if (yRelativo == -1)
				if (xRelativo == 0)
					return true;
				else {
					Pieza p = tablero.getPieza(movimiento.destinoX, movimiento.destinoY);
					if (p != null) {
						Jugador jug = p.getJugador();
						if (xRelativo == 1 && (jug != null) && (jug.equals(getJugador1())))
							return true;
					}
				}
		}

		return false;
	}

	private boolean esMovimientoValidoRey(Movimiento movimiento) {
		int xRelativo = Math.abs(movimiento.destinoX - movimiento.origenX);
		int yRelativo = Math.abs(movimiento.destinoY - movimiento.origenY);

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
		
		
		int incrementoX = 0;
		int incrementoY = 0;
		
		if (xRelativo == 0) {	
			if (movimiento.destinoY > movimiento.origenY)
				incrementoY = 1;
			else
				incrementoY = -1;
			for (int y = movimiento.origenY + incrementoY; y != movimiento.destinoY; y = y + incrementoY)
				if (tablero.getPieza(movimiento.origenX, y) != null)
					return false;
			return true;
		} else if (yRelativo == 0) {		
			if (movimiento.destinoX > movimiento.origenX)
				incrementoX = 1;
			else
				incrementoX = -1;
			for (int x = movimiento.origenX + incrementoX; x != movimiento.destinoX; x = x + incrementoX)
				if (tablero.getPieza(x, movimiento.origenY) != null)
					return false;
			return true;
		}
		return false;
	}

	protected boolean comprobarFinJuego(int jugador) {
		boolean esJaque = jugadores[jugador].esJaque();
		boolean esAhogado = comprobarAhogado(jugadores[jugador]);
		if(esJaque && esAhogado) interfaz.gameEnded(Math.abs(jugador-1));
		if(!esJaque && esAhogado) interfaz.gameEnded(Math.abs(2));		
		return esAhogado;		  
	}

	// busca si al jugador que se le pasa le quendan movimientos legales
	// devuelve true si esta ahogado
	protected boolean comprobarAhogado(Jugador jugador) {
		Pieza pieza = null;
		for (int i = 0; i <= 7; i++)
			for (int j = 0; j <= 7; j++) {
				pieza = tablero.getPieza(i, j);
				if (pieza == null || pieza.getJugador() != jugador)
					continue;
				if (tieneMovimiento(pieza, i, j))
					return false;
			}
		return true;
	}

	private boolean tieneMovimiento(Pieza pieza, int x, int y) {
		int tipo = pieza.getTipo();
		switch (tipo) {
		case Pieza.BISHOP:
			return tieneMovimientoAlfil(pieza, x, y);
		case Pieza.KING:
			return tieneMovimientoRey(pieza, x, y);
		case Pieza.KNIGHT:
			return tieneMovimientoCaballo(pieza, x, y);
		case Pieza.PAWN:
			return tieneMovimientoPeon(pieza, x, y);
		case Pieza.QUEEN:
			return tieneMovimientoReina(pieza, x, y);
		case Pieza.ROOK:
			return tieneMovimientoTorre(pieza, x, y);
		}
		return false;
	}

	private boolean tieneMovimientoReina(Pieza pieza, int x, int y) {
		return tieneMovimientoAlfil(pieza, x, y) || tieneMovimientoTorre(pieza, x, y);
	}

	private boolean tieneMovimientoTorre(Pieza pieza, int x, int y) {
		Movimiento movimiento = new Movimiento();
		movimiento.jugador = pieza.getJugador();
		movimiento.origenX = x;
		movimiento.origenY = y;

		movimiento.destinoY = y;
		for (int i = 0; i <= 7; i++) {
			movimiento.destinoX = i;
			if (i != x)
				if (movimientoValidoConJaque(movimiento))
					return true;
		}
		movimiento.destinoX = x;
		for (int i = 0; i <= 7; i++) {
			movimiento.destinoY = i;
			if (i != y)
				if (movimientoValidoConJaque(movimiento))
					return true;
		}
		return false;
	}

	private boolean tieneMovimientoPeon(Pieza pieza, int x, int y) {
		Movimiento movimiento = new Movimiento();
		movimiento.jugador = pieza.getJugador();
		movimiento.origenX = x;
		movimiento.origenY = y;

		movimiento.destinoX = x;
		if (((Peon) pieza).isPrimerMovimiento()) {
			for (int i = -2; i <= 2; i++) {
				movimiento.destinoY = i;
				if (i != y)
					if (movimientoValidoConJaque(movimiento))
						return true;
			}
		} else {
			for (int i = -1; i <= 1; i++) {
				movimiento.destinoY = i;
				if (i != y)
					if (movimientoValidoConJaque(movimiento))
						return true;
			}
		}
		// comprobar ataques posibles
		movimiento.destinoX = x - 1;
		movimiento.destinoY = y + 1;
		if (movimientoValidoConJaque(movimiento))
			return true;
		movimiento.destinoX = x + 1;
		movimiento.destinoY = y - 1;
		if (movimientoValidoConJaque(movimiento))
			return true;
		movimiento.destinoX = x + 1;
		movimiento.destinoY = y + 1;
		if (movimientoValidoConJaque(movimiento))
			return true;
		movimiento.destinoX = x - 1;
		movimiento.destinoY = y - 1;
		if (movimientoValidoConJaque(movimiento))
			return true;

		return false;
	}

	private boolean tieneMovimientoCaballo(Pieza pieza, int x, int y) {
		Movimiento movimiento = new Movimiento();
		movimiento.jugador = pieza.getJugador();
		movimiento.origenX = x;
		movimiento.origenY = y;
		int[] casosX = { -2, -1, 1, 2, 2, 1, -1, -2 };
		int[] casosY = { 1, 2, 2, 1, -1, -2, -2, -1 };
		for (int i = 0; i < casosX.length; i++) {
			movimiento.destinoX = x + casosX[i];
			movimiento.destinoY = y + casosY[i];
			if (movimientoValidoConJaque(movimiento))
				return true;
		}
		return false;
	}

	private boolean tieneMovimientoRey(Pieza pieza, int x, int y) {
		Movimiento movimiento = new Movimiento();
		movimiento.jugador = pieza.getJugador();
		movimiento.origenX = x;
		movimiento.origenY = y;
		for (int i = -1; i <= 1; i++)
			for (int j = -1; j <= 1; j++) {
				movimiento.destinoX = x + i;
				movimiento.destinoY = y + j;
				if (i != 0 && j != 0)
					if (movimientoValidoConJaque(movimiento))
						return true;
			}
		return false;
	}

	private boolean tieneMovimientoAlfil(Pieza pieza, int x, int y) {
		Movimiento movimiento = new Movimiento();
		movimiento.jugador = pieza.getJugador();
		movimiento.origenX = x;
		movimiento.origenY = y;
		for (int i = y - x, j = 0; i < 8 && j < 8; i++, j++) {
			movimiento.destinoX = i;
			movimiento.destinoY = j;
			if (i != 0 && j != 0)
				if (movimientoValidoConJaque(movimiento))
					return true;
		}
		for (int i = y + x, j = 0; i < 8 && j < 8; i--, j++) {
			movimiento.destinoX = i;
			movimiento.destinoY = j;
			if (i != 0 && j != 0)
				if (movimientoValidoConJaque(movimiento))
					return true;
		}

		return false;
	}

	@Override
	public void inicializarTablero() {
		tablero.inicializarTableroEstandar(jugadores[0], jugadores[1]);
	}
}
