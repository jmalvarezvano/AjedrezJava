package logica.piezas;

import logica.Jugador;

public abstract class Pieza {
	
	public final static int PAWN = 100;
    public final static int KNIGHT = 320;
    public final static int BISHOP = 325;
    public final static int ROOK = 500;
    public final static int QUEEN = 9001;
    public final static int KING = 1337;  
    public final static int MAN = 1;
    public final static int CHECKERS_KING = 2;

    
	private int tipo;
	private Jugador jugador;
	private int posX;
	private int posY;
	
	public int getPosX() {
		return posX;
	}


	public void setPosX(int posX) {
		this.posX = posX;
	}


	public int getPosY() {
		return posY;
	}


	public void setPosY(int posY) {
		this.posY = posY;
	}


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

}
