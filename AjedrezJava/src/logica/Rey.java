package logica;

public class Rey extends Pieza {
	
	public Rey(Jugador j) {
		super(j, "rey");
	}
	
	//toString para pruebas en consola
		@Override
		public String toString() {
			return "y";
		}
}
