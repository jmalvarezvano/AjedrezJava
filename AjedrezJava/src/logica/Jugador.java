package logica;

import java.util.ArrayList;

import logica.estrategia.Estrategia;
import logica.piezas.Pieza;

public class Jugador {
	
	private ArrayList<Pieza> piezas;
	private Estrategia mediador;	 
	
	public Jugador(Estrategia m) {
		piezas = new ArrayList<Pieza>();
		mediador = m;
	}	
	
	public boolean moverPieza(int origenX, int origenY, int destinoX, int destinoY) {
		return mediador.moverPieza(new Movimiento(origenX, origenY, destinoX, destinoY, this));
	}

	public Estrategia getMediador() {
		return mediador;
	}

	public void setMediador(Estrategia mediador) {
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
