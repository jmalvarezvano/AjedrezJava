package logica;

public class Peon extends Pieza{

	public Peon(Jugador j) {		
		super(j, "peonPrimerMovimiento");
		
	}
	
	@Override
	public String toString() {
		return "p";
	}
	
}
