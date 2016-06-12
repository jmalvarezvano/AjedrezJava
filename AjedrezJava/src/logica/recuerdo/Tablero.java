package logica.recuerdo;

import logica.Fabrica;
import logica.FabricaPiezas;
import logica.Jugador;
import logica.estrategia.Estrategia;
import logica.piezas.Pieza;

public class Tablero implements Cloneable {
	private Pieza[][] piezas;
	private Estrategia mediador;
	Fabrica fabricaPiezas;

	public Tablero(Estrategia m) {
		mediador = m;
		fabricaPiezas = FabricaPiezas.getSingleton();
	}
		
	public int[] getPosRey(Jugador jugador) {
		int[] res = null;
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++) 
				if(piezas[i][j] != null && piezas[i][j].getTipo() == Pieza.KING && piezas[i][j].getJugador().equals(jugador)) {
					res = new int[2];
					res[0] = i;
					res[1] = j;
					return res;
				}
		return null;
	}

	public void inicializarTableroEstandar(Jugador j1, Jugador j2) {

		piezas = new Pieza[8][8];
		
		piezas[0][0] = fabricaPiezas.crear(Pieza.ROOK, j1);
		piezas[0][7] = fabricaPiezas.crear(Pieza.ROOK, j2);
		piezas[7][0] = fabricaPiezas.crear(Pieza.ROOK, j1);
		piezas[7][7] = fabricaPiezas.crear(Pieza.ROOK, j2);
		piezas[4][0] = fabricaPiezas.crear(Pieza.KING, j1);
		piezas[4][7] = fabricaPiezas.crear(Pieza.KING, j2);
		piezas[3][0] = fabricaPiezas.crear(Pieza.QUEEN, j1);
		piezas[3][7] = fabricaPiezas.crear(Pieza.QUEEN, j2);
		for (int i = 0; i < 8; i++) {
			piezas[i][1] = fabricaPiezas.crear(Pieza.PAWN, j1);
			piezas[i][6] = fabricaPiezas.crear(Pieza.PAWN, j2);
		}
		piezas[1][0] = fabricaPiezas.crear(Pieza.KNIGHT, j1);
		piezas[6][0] = fabricaPiezas.crear(Pieza.KNIGHT, j1);
		piezas[1][7] = fabricaPiezas.crear(Pieza.KNIGHT, j2);
		piezas[6][7] = fabricaPiezas.crear(Pieza.KNIGHT, j2);
		piezas[2][0] = fabricaPiezas.crear(Pieza.BISHOP, j1);
		piezas[5][0] = fabricaPiezas.crear(Pieza.BISHOP, j1);
		piezas[2][7] = fabricaPiezas.crear(Pieza.BISHOP, j2);
		piezas[5][7] = fabricaPiezas.crear(Pieza.BISHOP, j2);
		
		Conserje.getSingleton().add(this.saveStateToMemento());
	}

	public void inicializarTableroInvertido(Jugador j1, Jugador j2) {
		this.inicializarTableroEstandar(j1, j2);
	}

	public void inicializarTableroDamas(Jugador j1, Jugador j2) {
		piezas = new Pieza[8][8];		
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 3; j++)
				if((i - j) % 2 == 0)
				piezas[i][j]  = fabricaPiezas.crear(Pieza.MAN, j1);
		for (int i = 0; i < 8; i++)
			for (int j = 5; j < 8; j++)
				if((i - j) % 2 == 0)
				piezas[i][j] = fabricaPiezas.crear(Pieza.MAN, j2);
		
		Conserje.getSingleton().add(this.saveStateToMemento());


		
	}

	// toString para pruebas en consola
	@Override
	public String toString() {
		String out = new String();
		for (int i = 7; i >= 0; i--) {
			out += "\n" + i;
			for (int j = 0; j <= 7; j++)
				out += " " + piezas[j][i];
		}
		out += "\n ";
		for (int j = 0; j <= 7; j++)
			out += " " + j;
		return out;
	}
	
	public void setPiezas(Pieza[][] piezas) {
		this.piezas = piezas;
	}
	
	public void setPieza(int x, int y, Pieza p) {
		piezas[x][y] = p;
	}
	
	public void quitarPieza(int  x, int y) {
		piezas[x][y] = null;
	}
	
	
	//patrón Memento
	public Memento saveStateToMemento() {		
		return new Memento(copiarPiezas());
	}
	
	public Pieza[][] copiarPiezas() {
		Pieza[][] res = new Pieza[8][8];
		for (int i = 0; i <= 7; i++) {			
			for (int j = 0; j <= 7; j++)
				res[i][j] = FabricaPiezas.getSingleton().crear(piezas[i][j]);
		}
		return res;		
	}
	
	public Pieza[][] getPiezas() {
		return piezas;
	}

	public void getStateFromMemento(Memento Memento) {
		Pieza[][] state = Memento.getState().clone();
		piezas = new Pieza[8][8];
		for (int i = 0; i <= 7; i++) {			
			for (int j = 0; j <= 7; j++)
				piezas[i][j] = FabricaPiezas.getSingleton().crear(state[i][j]);
		}				
	}	

	public Estrategia getMediador() {
		return mediador;
	}

	public void setMediador(Estrategia mediador) {
		this.mediador = mediador;
	}

	public Pieza getPieza(int x, int y) throws ArrayIndexOutOfBoundsException {
			return piezas[x][y];		
	}
		
}
