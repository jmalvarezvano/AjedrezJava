package logica;

import java.util.ArrayList;

public class Jugador {
	
	private ArrayList<Pieza> piezas;
	private JuegoEstandar mediador;	 
	
	public Jugador(JuegoEstandar m) {
		piezas = new ArrayList<Pieza>();
		mediador = m;
	}	
	
	public boolean moverPieza(int origenX, int origenY, int destinoX, int destinoY) {
		return mediador.moverPieza(origenX, origenY, destinoX, destinoY, this);
	}

	public JuegoEstandar getMediador() {
		return mediador;
	}

	public void setMediador(JuegoEstandar mediador) {
		this.mediador = mediador;
	}

	public ArrayList<Pieza> getPiezas() {
		return piezas;
	}
	
	public boolean existePieza(Pieza p) {
		return piezas.contains(p);
	}

	public void addPieza(Pieza pieza) {
		piezas.add(pieza);
	}
	
	public void removePieza(Pieza pieza) {
		piezas.remove(pieza);
	}
	
}
