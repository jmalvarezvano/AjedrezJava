package logica;

public class Peon extends Pieza{
	
	private boolean primerMovimiento;

	public boolean isPrimerMovimiento() {
		return primerMovimiento;
	}

	public void setPrimerMovimiento(boolean primerMovimiento) {
		this.primerMovimiento = primerMovimiento;
	}

	public Peon(Jugador j) {		
		super(j);
		primerMovimiento = true;
	}
	
}
