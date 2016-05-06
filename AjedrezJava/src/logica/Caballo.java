package logica;

public class Caballo extends Pieza {

	public Caballo(Jugador j) {
		super(j, "caballo");
	}
	
	//toSring para pruebas en consola
	@Override
	public String toString() {
		return "c";
	}
	
}