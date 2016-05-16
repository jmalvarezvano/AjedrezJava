package logica;

public class Peon extends Pieza {
	
	private boolean primerMovimiento;
	
	public Peon(Jugador j) {
		super(j, Pieza.PAWN);	
		
		primerMovimiento = true;
	}
	
	public boolean isPrimerMovimiento() {
		return primerMovimiento;
	}

	public void setPrimerMovimiento(boolean primerMovimiento) {
		this.primerMovimiento = primerMovimiento;
	}

	
	@Override
	public String toString() {
		return "p";
	}
	
}
