package logica;

public class Tablero {
	private Celda[][] celdas;
	private Mediador mediador;
	Fabrica fabrica;
	
	public Tablero(Mediador m) {
		mediador = m;
		fabrica = Fabrica.getSingleton();
		this.inicializarTablero(mediador.getJugador1(), mediador.getJugador2());
	}
	
	public void inicializarTablero(Jugador j1, Jugador j2) {
		
		celdas = new Celda[8][8];
		for(int i = 0; i < 8; i++) 
			for(int j = 0; j < 8; j++) 
				celdas[i][j] = fabrica.crearCelda();
		
		celdas[0][0].setPieza(fabrica.crearPieza("torre", j1));
		celdas[0][7].setPieza(fabrica.crearPieza("torre", j1));
		celdas[7][0].setPieza(fabrica.crearPieza("torre", j2));
		celdas[7][7].setPieza(fabrica.crearPieza("torre", j2));
		celdas[0][4].setPieza(fabrica.crearPieza("rey", j1));
		celdas[7][4].setPieza(fabrica.crearPieza("rey", j2));
		celdas[0][3].setPieza(fabrica.crearPieza("reina", j1));
		celdas[7][3].setPieza(fabrica.crearPieza("reina", j2));
		for(int i = 0; i < 8; i++) {
			celdas[1][i].setPieza(fabrica.crearPieza("peon", j1));
			celdas[6][i].setPieza(fabrica.crearPieza("peon", j2));
		}
		celdas[0][1].setPieza(fabrica.crearPieza("caballo", j1));
		celdas[0][6].setPieza(fabrica.crearPieza("caballo", j1));
		celdas[7][1].setPieza(fabrica.crearPieza("caballo", j2));
		celdas[7][6].setPieza(fabrica.crearPieza("caballo", j2));
		celdas[0][2].setPieza(fabrica.crearPieza("alfil", j1));
		celdas[0][5].setPieza(fabrica.crearPieza("alfil", j1));
		celdas[7][2].setPieza(fabrica.crearPieza("alfil", j2));
		celdas[7][5].setPieza(fabrica.crearPieza("alfil", j2));		
	}

	
	//toString para pruebas en consola
	@Override
	public String toString() {
		String out = new String();
		for (int i = 0 ; i <= 7; i++) {			
			out += "\n";
			for (int j = 0 ; j <= 7; j++)
				out += " " + celdas[i][j];
		}
		return out;
	}

	public Celda[][] getCeldas() {
		return celdas;
	}

	public void setCeldas(Celda[][] celdas) {
		this.celdas = celdas;
	}

	public Mediador getMediador() {
		return mediador;
	}

	public void setMediador(Mediador mediador) {
		this.mediador = mediador;
	}
	
	public Celda getCelda(int x, int y) {
		return celdas[x][y];
	}
}
