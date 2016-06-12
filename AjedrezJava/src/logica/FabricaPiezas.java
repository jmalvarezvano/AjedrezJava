package logica;

import logica.piezas.Alfil;
import logica.piezas.Caballo;
import logica.piezas.Man;
import logica.piezas.Peon;
import logica.piezas.Pieza;
import logica.piezas.Reina;
import logica.piezas.Rey;
import logica.piezas.ReyDamas;
import logica.piezas.Torre;

public class FabricaPiezas extends Fabrica {
	
	private static FabricaPiezas fabricaPiezas;
	
	private FabricaPiezas() {}
	
	public Pieza crear(String p, Jugador j) {
		p = p.toLowerCase();
		if (p.equals("alfil")) return new Alfil(j);
		if (p.equals("caballo")) return new Caballo(j);
		if (p.equals("peon")) return new Peon(j);
		if (p.equals("reina")) return new Reina(j);
		if (p.equals("rey")) return new Rey(j);
		if (p.equals("torre")) return new Torre(j);
		if (p.equals("man")) return new Man(j);
		if (p.equals("reydamas")) return new ReyDamas(j);


		return null;
	}
	
	public static FabricaPiezas getSingleton() {
		if(fabricaPiezas == null) fabricaPiezas = new FabricaPiezas();
		return fabricaPiezas;
	}

	public Pieza clonar(Pieza pieza) {
		if(pieza == null) return null;
		switch (pieza.getTipo()) {
		case Pieza.BISHOP:
			return new Alfil(pieza.getJugador());
		case Pieza.KNIGHT:
			return new Caballo(pieza.getJugador());
		case Pieza.PAWN:
			   Peon p = new Peon(pieza.getJugador());
			   p.setPrimerMovimiento(((Peon) pieza).isPrimerMovimiento());
			   return p;
		case Pieza.QUEEN:
			return new Reina(pieza.getJugador());
		case Pieza.KING:
			return new Rey(pieza.getJugador());
		case Pieza.ROOK:
			return new Torre(pieza.getJugador());
		case Pieza.MAN:
			return new Man(pieza.getJugador());
		case Pieza.CHECKERS_KING:
			return new ReyDamas(pieza.getJugador());

		}
		return null;
	}
}
