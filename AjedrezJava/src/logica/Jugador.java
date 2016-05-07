package logica;

import java.util.ArrayList;

public class Jugador {
	
	private ArrayList<Pieza> piezas;
	private Mediador mediador;
	private boolean jaque;
	private boolean celdasDefendidasPorRival[][];  //variable usada para el jaque y jaque mate
	

	public Jugador(Mediador m) {
		piezas = new ArrayList<Pieza>();
		mediador = m;
		jaque = false;
		celdasDefendidasPorRival = new boolean[8][8];
	}
	
	public boolean[][] getCeldasDefendidasPorRival() {
		return celdasDefendidasPorRival;
	}

	public void setCeldasDefendidasPorRival(boolean[][] piezasDefendidasPorRival) {
		this.celdasDefendidasPorRival = piezasDefendidasPorRival;
	}
	
	public boolean esCeldaDefendidaPorRival(int x, int y) {
		return celdasDefendidasPorRival[x][y];
	}
	
	public void setCeldaDefendidaPorRival(int x, int y, boolean nuevoEstado) {
		celdasDefendidasPorRival[x][y] = nuevoEstado;
	}

	public boolean isJaque() {
		return jaque;
	}

	public void setJaque(boolean jaque) {
		this.jaque = jaque;
	}
	
	
	public boolean moverPieza(int origenX, int origenY, int destinoX, int destinoY) {
		return mediador.moverPieza(origenX, origenY, destinoX, destinoY, this);
	}

	public Mediador getMediador() {
		return mediador;
	}

	public void setMediador(Mediador mediador) {
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
