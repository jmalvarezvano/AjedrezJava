package logica;

import java.util.Scanner;

import logica.estrategia.JuegoEstandar;
import logica.estrategia.Mediador;
import presentacion.MainPane;

public class Main {
	/*
	 * Clase para hacer pruebas.
	 */
	public static void main(String[] args) {
		//prueba1();
		prueba2();
	}
	public static void prueba1() {
		JuegoEstandar m = new JuegoEstandar();
		Jugador j1 = new Jugador(m);
		Celda[][] celdas = new Celda[8][8];
		
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				celdas[i][j] = new Celda();
		
		FabricaPiezas fabricaPiezas = FabricaPiezas.getSingleton();
		celdas[0][0].setPieza(fabricaPiezas.crear("torre", j1));
	}
	public static void prueba2() {
		//Scanner in = new Scanner(System.in);
		Mediador mediador = new JuegoEstandar();		
		Jugador j1 = new Jugador(mediador);
		Jugador j2 = new Jugador(mediador);
		mediador.setJugador1(j1);
		mediador.setJugador2(j2);
		Tablero tablero = new Tablero(mediador);
		mediador.setTablero(tablero);
		//System.out.println(tablero);
		
//		Jugador jugador[] = new Jugador[] {j1, j2};
//		int turno = 0;
//		boolean mover;	
		MainPane.main(mediador);
//		int xOr;
//		int yOr;
//		int xDes;
//		int yDes;
//		while(true) {
//			System.out.println("Turno: Jugador " + (turno + 1));	
//			System.out.println("x origen: ");
//			xOr = Integer.parseInt(in.nextLine());
//			System.out.println("y origen: ");
//			yOr = Integer.parseInt(in.nextLine());
//			System.out.println("x destino: ");
//			xDes = Integer.parseInt(in.nextLine());
//			System.out.println("y destino: ");
//			yDes = Integer.parseInt(in.nextLine());
//			mover = jugador[turno].moverPieza(xOr, yOr, xDes, yDes);
//			if (mover) 	turno = Math.abs(turno - 1);			
//			System.out.println(mover);
//			System.out.println(tablero);
//
//		}
	}
}
