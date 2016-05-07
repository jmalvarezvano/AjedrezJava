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
		if (ocupado) return pieza;
		return null;
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
	
	//toString para pruebas en consola
	@Override
	public String toString() {
		if(ocupado) return pieza.toString();
		else return "-";
	}
	
}
