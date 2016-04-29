package logica;

public class Celda {
	private Pieza pieza;
	private	boolean ocupado;
	 
	public Celda() {
		ocupado = false;
	}
	public Celda(Pieza p) {
		pieza = p;
		ocupado = true;
	}
	
	public Pieza getPieza() {
		return pieza;
	}
	public void setPieza(Pieza pieza) {
		this.pieza = pieza;
		ocupado = true;
	}
	public Pieza quitarPieza(){
		if (!ocupado) return null;
		ocupado = false;
		return pieza;
	}
	public boolean isOcupado() {
		return ocupado;
	}
}
