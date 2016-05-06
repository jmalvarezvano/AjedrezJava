package logica;


public class Reina extends Pieza {

	public Reina(Jugador j) {
		super(j, "reina");
	}
	
	//toString para pruebas en consola
		@Override
		public String toString() {
			return "r";
		}

}
