package logica;

import java.util.ArrayList;

import logica.estrategia.Estrategia;
import logica.piezas.Pieza;

public class Jugador {
	
	private ArrayList<Pieza> piezas;
	private Estrategia mediador;
	private boolean celdasDefendidasPorRival[][];  //variable usada para el jaque y jaque mate	

	
	public Jugador(Estrategia m) {
		piezas = new ArrayList<Pieza>();
		mediador = m;
	}	
	
	public boolean[][] getCeldasDefendidasPorRival() {
		return celdasDefendidasPorRival;
	}
	
	
	public void setCeldasDefendidasPorRival(boolean[][] piezasDefendidasPorRival) {
		this.celdasDefendidasPorRival = piezasDefendidasPorRival;
	}
	
	public boolean esCeldaDefendidaPorRival(int x, int y) {
		if (x < 0 || x > 8 || y < 0 || y > 8) return false;
		return celdasDefendidasPorRival[x][y];
	}
	
	public void setCeldaDefendidaPorRival(int x, int y, boolean nuevoEstado) {
		celdasDefendidasPorRival[x][y] = nuevoEstado;
	}

	public boolean isJaque() {
		return celdasDefendidasPorRival[1][1];
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
