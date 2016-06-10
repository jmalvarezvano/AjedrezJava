package logica.estrategia;

import logica.FabricaPiezas;
import logica.Jugador;
import logica.Movimiento;
import logica.piezas.Peon;
import logica.piezas.Pieza;
import logica.piezas.Rey;
import logica.recuerdo.Conserje;

public class JuegoEstandar extends Mediador {

	public boolean moverPieza(Movimiento movimiento) {
		
		Pieza origen = tablero.getPieza(movimiento.origenX, movimiento.origenY);
	
		//aunque sea movimiento valido destino no puede ser rey
		if (movimientoValido(movimiento)) {			
			tablero.quitarPieza(movimiento.origenX, movimiento.origenY);
			tablero.setPieza(movimiento.destinoX, movimiento.destinoY, origen);
			if (origen.getTipo() == Pieza.PAWN) {
				if (movimiento.jugador == jugadores[0] && movimiento.destinoY == 7)
					tablero.setPieza(movimiento.destinoX, movimiento.destinoY, promoverPeon(origen.getJugador()));
				if (movimiento.jugador == jugadores[1] && movimiento.destinoY == 0)
					tablero.setPieza(movimiento.destinoX, movimiento.destinoY, promoverPeon(origen.getJugador()));
			}

						if (origen.getTipo() == Pieza.PAWN && ((Peon) origen).isPrimerMovimiento()) {
				((Peon) origen).setPrimerMovimiento(false);
				System.out.println("actualizar peon");
			}
	
						
			actualizarCeldasDefendidas();
			
			//deshacer si el jugador que hace el movimiento sigue o entra en jaque 
			if(movimiento.jugador.isJaque()) {
				actualizarCeldasDefendidas();

				tablero.getStateFromMemento(Conserje.getSingleton().getLast());
				return false;
			}
			Conserje.getSingleton().add(tablero.saveStateToMemento()); // guardar
			// estado

			interfaz.animarMovimiento(movimiento);
			cambiarTurno();

			return true;
		}
		return false;
		
	}
	
	private boolean[][] getCeldasDefendidas(Jugador jugador, int[] posicionesRey) {
		boolean[][] res = new boolean[3][3];
		Movimiento movimiento = new Movimiento();
		boolean esValido;

		movimiento.jugador = jugador;
		for(int x1 = 0, x2 = -1; x1 <= 2 && x2 <= 1; x1++, x2++)
			for(int y1 = 0, y2 = -1; y1 <= 2 && y2 <= 1; y1++, y2++)
				for(int i = 0; i <= 7 && !res[x1][y1]; i++)
					for(int j = 0; j <= 7 && !res[x1][y1]; j++) {
						movimiento.origenX = i;
						movimiento.origenY = j;
						movimiento.destinoX = posicionesRey[0] + x2;
						movimiento.destinoY = posicionesRey[1] + y2;
						esValido = movimientoValido(movimiento);
						if(esValido) {
							res[x1][y1] = true;
							System.out.println("Es Valido con: origenX: " + i + " origenY: "+j
									+" destinoX: "+movimiento.destinoX+" destinoY: "+movimiento.destinoY);
						}
					}
		
		
		
		return res;
	}
	
	private void actualizarCeldasDefendidas() {
		boolean[][] celdasDefendidasPorJ1 = new boolean[3][3];
		boolean[][] celdasDefendidasPorJ2 = new boolean[3][3];

		int[] posReyJ1 = tablero.getPosRey(jugadores[0]);
		int[] posReyJ2 = tablero.getPosRey(jugadores[1]);
		
		boolean esValido;
		Movimiento movimiento = new Movimiento();
		movimiento.jugador = jugadores[0];
		for(int x1 = 0, x2 = -1; x1 <= 2 && x2 <= 1; x1++, x2++)
			for(int y1 = 0, y2 = -1; y1 <= 2 && y2 <= 1; y1++, y2++)
				for(int i = 0; i <= 7 && !celdasDefendidasPorJ1[x1][y1]; i++)
					for(int j = 0; j <= 7 && !celdasDefendidasPorJ1[x1][y1]; j++) {
						movimiento.origenX = i;
						movimiento.origenY = j;
						movimiento.destinoX = posReyJ2[0] + x2;
						movimiento.destinoY = posReyJ2[1] + y2;
						esValido = movimientoValido(movimiento);
						if(esValido) {
							celdasDefendidasPorJ1[x1][y1] = true;
							System.out.println("Es Valido con: origenX: " + i + " origenY: "+j
									+" destinoX: "+movimiento.destinoX+" destinoY: "+movimiento.destinoY);
						}
					}
		
		movimiento.jugador = jugadores[1];
		for(int x1 = 0, x2 = -1; x1 <= 2 && x2 <= 1; x1++, x2++)
			for(int y1 = 0, y2 = -1; y1 <= 2 && y2 <= 1; y1++, y2++)
				for(int i = 0; i <= 7 && !celdasDefendidasPorJ2[x1][y1]; i++)
					for(int j = 0; j <= 7 && !celdasDefendidasPorJ2[x1][y1]; j++) {
						movimiento.origenX = i;
						movimiento.origenY = j;
						movimiento.destinoX = posReyJ1[0] + x2;
						movimiento.destinoY = posReyJ1[1] + y2;
						esValido = movimientoValido(movimiento);
						if(esValido) {
							celdasDefendidasPorJ2[x1][y1] = true;
							System.out.println("Es Valido con: origenX: " + i + " origenY: "+j
									+" destinoX: "+movimiento.destinoX+" destinoY: "+movimiento.destinoY);
						}
					}

		jugadores[0].setCeldasDefendidasPorRival(celdasDefendidasPorJ2);
		jugadores[1].setCeldasDefendidasPorRival(celdasDefendidasPorJ1);
		actualizarJaqueInterfaz();
		
		
		System.out.println("Defendidas porJ1:");

		
		for(int i = 0; i <= 2; i++){
			System.out.print("\n");
			for(int j = 0; j <= 2; j++) {
				System.out.print(celdasDefendidasPorJ1[i][j]+" ");

			}
		}
		
		System.out.println("Defendidas porJ2:");

		for(int i = 0; i <= 2; i++){
			System.out.print("\n");
			for(int j = 0; j <= 2; j++) {
				System.out.print(celdasDefendidasPorJ2[i][j]+" ");

			}
		}
										
	}

	private void actualizarJaqueInterfaz() {
		interfaz.setJaqueJ1(jugadores[0].isJaque());
		interfaz.setJaqueJ2(jugadores[1].isJaque());

	}

	private boolean movimientoValido(Movimiento movimiento) {
		Pieza origen;
		Pieza destino;
		
		try{
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
			for (int x = movimiento.origenX + 1, y = movimiento.origenY + 1; x < movimiento.destinoX
					&& y < movimiento.destinoY; x++, y++) {
				if (tablero.getPieza(x, y) != null)
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

		if (yRelativo > 2 || yRelativo == 0)
			return false;
		if (xRelativo > 1)
			return false;

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

//		Rey rey = (Rey) tablero.getPieza(movimiento.origenX, movimiento.origenY);
//		if (rey.isJaque()) {
//			if (rey.getCeldasDefendidasPorRival()[movimiento.destinoX][movimiento.destinoY])
//				return false;
//		}
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
				if (tablero.getPieza(movimiento.origenX, y) != null)
					return false;
			return true;
		} else if (yRelativo == 0) {
			for (int x = movimiento.origenX + 1; x < movimiento.destinoX; x++)
				if (tablero.getPieza(x, movimiento.origenY) != null)
					return false;
			return true;
		}
		return false;
	}

//	private boolean isJaqueMate(int origenX, int origenY) {
//		for (int x = -1; x < 2; x++)
//			for (int y = -1; y < 2; y++)
//				if (!((Rey) (tablero.getPieza(origenX, origenY))).getCeldasDefendidasPorRival()[origenX + x][origenY
//						+ y])
//					return false;
//		return true;
//	}

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
