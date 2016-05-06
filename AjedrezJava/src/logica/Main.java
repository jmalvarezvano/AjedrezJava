package logica;

import java.util.Scanner;

public class Main {

	/*
	 * Clase para hacer pruebas.
	 */
	public static void main(String[] args) {
		//prueba1();
		prueba2();
	}
	public static void prueba1() {
		Mediador m = new Mediador();
		Jugador j1 = new Jugador(m);
		Celda[][] celdas = new Celda[8][8];
		
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				celdas[i][j] = new Celda();
		
		Fabrica fabrica = Fabrica.getSingleton();
		celdas[0][0].setPieza(fabrica.crearPieza("torre", j1));
	}
	public static void prueba2() {
		Scanner in = new Scanner(System.in);
		
		Fabrica fabrica = Fabrica.getSingleton();
		Mediador mediador = new Mediador();		
		Jugador j1 = fabrica.crearJugador(mediador);
		Jugador j2 = fabrica.crearJugador(mediador);
		mediador.setJugador1(j1);
		mediador.setJugador2(j2);
		Tablero tablero = new Tablero(mediador);
		mediador.setTablero(tablero);
		System.out.println(tablero);
		
		int xOr;
		int yOr;
		int xDes;
		int yDes;
		while(true) {
			System.out.println("x origen: ");
			xOr = Integer.parseInt(in.nextLine());
			System.out.println("y origen: ");
			yOr = Integer.parseInt(in.nextLine());
			System.out.println("x destino: ");
			xDes = Integer.parseInt(in.nextLine());
			System.out.println("y destino: ");
			yDes = Integer.parseInt(in.nextLine());
			System.out.println(j1.moverPieza(xOr, yOr, xDes, yDes));
			System.out.println(tablero);
			
			System.out.println("x origen: ");
			xOr = Integer.parseInt(in.nextLine());
			System.out.println("y origen: ");
			yOr = Integer.parseInt(in.nextLine());
			System.out.println("x destino: ");
			xDes = Integer.parseInt(in.nextLine());
			System.out.println("y destino: ");
			yDes = Integer.parseInt(in.nextLine());
			System.out.println(j2.moverPieza(xOr, yOr, xDes, yDes));
			System.out.println(tablero);

		}
	}
}
