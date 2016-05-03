package logica;

public class Main {

	/*
	 * Clase para hacer pruebas.
	 */
	public static void main(String[] args) {
		Mediador m = new Mediador();
		Jugador j1 = new Jugador(m);
		Celda[][] celdas = new Celda[8][8];
		
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				celdas[i][j] = new Celda();
		
		Fabrica fabrica = Fabrica.getSingleton();
		celdas[0][0].setPieza(fabrica.crearPieza("torre", j1));
	}
}
