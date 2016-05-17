package logica;

public abstract class Pieza {
	
	public final static int PAWN = 100;
    public final static int KNIGHT = 320;
    public final static int BISHOP = 325;
    public final static int ROOK = 500;
    public final static int QUEEN = 900;
    public final static int KING = 1000000;   

	private int tipo;
	private Jugador jugador;
	private Celda celda;
	
	public Pieza(Jugador j, int m) {
		jugador = j;
		tipo = m;
	}

	

	public int getTipo() {
		return tipo;
	}

	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}



	public void setCelda(Celda celda) {
		this.celda = celda;
	}
	
}
