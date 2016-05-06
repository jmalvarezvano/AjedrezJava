package logica;

public class Torre extends Pieza {

	public Torre(Jugador j) {
		super(j, "torre");
	}
	
	//toString para pruebas en consola
		@Override
		public String toString() {
			return "t";
		}

}
