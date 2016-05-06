package logica;

public class Alfil extends Pieza {

	public Alfil(Jugador j) {
		super(j, "alfil");
		
	}
	
	//toString para pruebas en consola
	@Override
	public String toString() {
		return "a";
	}

}
