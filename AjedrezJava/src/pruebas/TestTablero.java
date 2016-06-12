package pruebas;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Test;
import logica.Jugador;
import logica.estrategia.JuegoEstandar;
import logica.piezas.Pieza;
import logica.recuerdo.Tablero;

public class TestTablero {
	
	JuegoEstandar je = new JuegoEstandar();
	Jugador j1 = new Jugador(je);
	Jugador j2 = new Jugador(je);
	Tablero t = new Tablero(je);
	Pieza[][] tablero;
	
	@After
	public void limpiarTablero(){
		tablero = new Pieza[8][8];
		t.setPiezas(tablero);
	}
	
	@Test 
	public void testInicializarTableroEstandar(){
		t.inicializarTableroEstandar(j1, j2);
		tablero = t.getPiezas();
		for(int x = 0; x < 8; x++ ){
			for(int y = 0; y < 8; y++){
				//comprobar fichas de j1
				if(y < 2){
					assertNotNull(tablero[x][y]);
					assertTrue(tablero[x][y].getJugador().equals(j1));
					assertEquals(tablero[x][y].getTipo(),tipoPieza(x,y));
				}
				//comprobar que no hay fichas en el centro del tablero
				if(y >= 2 && y < 6){
					assertNull(tablero[x][y]);
				}
				//comprobar fichas de j2
				if(y >= 6){
					assertNotNull(tablero[x][y]);
					assertTrue(tablero[x][y].getJugador().equals(j2));
					assertEquals(tablero[x][y].getTipo(),tipoPieza(x,y));
				}
			}
		}
	}
	
	@Test
	public void testInicializarTableroDamas(){
		t.inicializarTableroDamas(j1, j2);
		tablero = t.getPiezas();
		for(int x = 0; x < 8; x++ ){
			if(x % 2 == 0){
				//para el j1
				assertNotNull(tablero[x][0]);
				assertTrue(tablero[x][0].getJugador().equals(j1));
				assertEquals(tablero[x][0].getTipo(),Pieza.MAN);
				assertNotNull(tablero[x][2]);
				assertTrue(tablero[x][2].getJugador().equals(j1));
				assertEquals(tablero[x][2].getTipo(),Pieza.MAN);
				//para el j2
				assertNotNull(tablero[x][6]);
				assertTrue(tablero[x][6].getJugador().equals(j2));
				assertEquals(tablero[x][6].getTipo(),Pieza.MAN);
			}else{
				//para el j1
				assertNotNull(tablero[x][1]);
				assertTrue(tablero[x][1].getJugador().equals(j1));
				assertEquals(tablero[x][1].getTipo(),Pieza.MAN);
				//para el j2
				assertNotNull(tablero[x][5]);
				assertTrue(tablero[x][5].getJugador().equals(j2));
				assertEquals(tablero[x][5].getTipo(),Pieza.MAN);
				assertNotNull(tablero[x][7]);
				assertTrue(tablero[x][7].getJugador().equals(j2));
				assertEquals(tablero[x][5].getTipo(),Pieza.MAN);
			}
			assertNull(tablero[x][3]);
			assertNull(tablero[x][4]);
		}
	}
	
	private int tipoPieza(int x,int y){
		if(y == 1 || y == 6){
			return Pieza.PAWN;
		}else{
			if(x == 0 || x == 7){
				return Pieza.ROOK;
			}
			if(x == 1 || x == 6){
				return Pieza.KNIGHT;
			}
			if(x == 2 || x == 5){
				return Pieza.BISHOP;
			}
			if(x == 3){
				return Pieza.QUEEN;
			}
			if(x == 4){
				return Pieza.KING;
			}
		}
		return 0;
	}

}
