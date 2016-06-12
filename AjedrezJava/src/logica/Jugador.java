package logica;


import logica.estrategia.Estrategia;

public class Jugador {
	
	private Estrategia mediador;
	private boolean esJaque;  //variable usada para el jaque y jaque mate	

	
	public Jugador(Estrategia m) {
		mediador = m;
		esJaque = false;
	}	
		
	public boolean esJaque() {
		return esJaque;
	}

	public void setEsJaque(boolean esJaque) {
		this.esJaque = esJaque;
	}

	public boolean moverPieza(int origenX, int origenY, int destinoX, int destinoY) {
		return mediador.moverYAnimarPieza(new Movimiento(origenX, origenY, destinoX, destinoY, this));
	}

	public Estrategia getMediador() {
		return mediador;
	}

	public void setMediador(Estrategia mediador) {
		this.mediador = mediador;
	}

}
