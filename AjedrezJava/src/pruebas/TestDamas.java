package pruebas;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import logica.FabricaPiezas;
import logica.Jugador;
import logica.Movimiento;
import logica.estrategia.Damas;
import logica.estrategia.MockDama;
import logica.piezas.Man;
import logica.piezas.Pieza;
import logica.piezas.ReyDamas;
import logica.recuerdo.Conserje;
import logica.recuerdo.Memento;
import logica.recuerdo.Tablero;
import presentacion.MainPane;

public class TestDamas {
	
	 private static Man dama1,dama2,dama3;
	 private static ReyDamas rey;
	 private static MockDama d; 
	 private static Tablero t;
	 private static Jugador j1,j2;
	 private static Pieza[][] piezas;
	 private static FabricaPiezas fabrica; 
	
	@BeforeClass
	public static void inicializar(){
		d = new MockDama();
		t = new Tablero(d); 
		j1 = new Jugador(d);
		j2 = new Jugador(d);
		fabrica = FabricaPiezas.getSingleton();
		piezas = new Pieza[8][8];
		t.setPiezas(piezas);
		dama1 = (Man) fabrica.crear("man", j1);
		dama2 = (Man) fabrica.crear("man", j2);
		dama3 = (Man) fabrica.crear("man", j2);
		d.juegoNuevo(t,j1,j2);
	}
	
	@After
	public void limpiarTablero(){
		piezas = new Pieza[8][8];
		t.setPiezas(piezas);
	}
	
	@Test
	public void testMovimiento(){
		piezas[0][0] = dama1;
		piezas[7][7] = dama2;
		
		Memento m = new Memento(piezas);
		Conserje c = Conserje.getSingleton();
		c.add(m);
		
		//test movimiento dama jugador 1
		d.mover(new Movimiento(0, 0, 1, 1, j1));
		assertNull(t.getPieza(0, 0));
		assertEquals(dama1,t.getPieza(1, 1));
		d.mover(new Movimiento(1,1,0,0,j1));
		assertNull(t.getPieza(0,0));
		assertEquals(dama1,t.getPieza(1, 1));
		
		//test movimiento dama jugador 2
		d.mover(new Movimiento(7, 7, 6, 6, j2));
		assertNull(t.getPieza(7, 7));
		assertEquals(dama2, t.getPieza(6, 6));
		d.mover(new Movimiento(6,6,7,7,j2));
		assertNull(t.getPieza(7, 7));
		assertEquals(dama2,t.getPieza(6, 6));
		
		//test movimiento lateral
		d.mover(new Movimiento(1,1,2,1,j1));
		assertNull(t.getPieza(2,1));
		assertEquals(dama1,t.getPieza(1,1));
		
		//test avanzar solo una casilla
		d.mover(new Movimiento(1,1,3,3,j1));
		assertNull(t.getPieza(3, 3));
		assertEquals(dama1,t.getPieza(1, 1));
	}
	
	@Test
	public void testComerFicha(){
		piezas[0][0] = dama1;
		piezas[1][1] = dama2;
		
		Memento m = new Memento(piezas);
		Conserje c = Conserje.getSingleton();
		c.add(m);
		
		d.mover(new Movimiento(0, 0, 2, 2, j1));
		assertNull(piezas[0][0]);
		assertNull(piezas[1][1]);
		assertEquals(dama1,t.getPieza(2, 2));				
	}
	
	@Test 
	public void testSigueAtacando(){
		piezas[0][0] = dama1;
		piezas[1][1] = dama2;
		piezas[3][3] = dama3;
		
		Memento m = new Memento(piezas);
		Conserje c = Conserje.getSingleton();
		c.add(m);
		
		d.mover(new Movimiento(0, 0, 2, 2, j1));
		for(int i = 0; i <=3 ;i++){
			assertNull(piezas[i][i]);
		}
		assertEquals(dama1,t.getPieza(4, 4));		
	}
	
	@Test 
	public void testMovimientoRey(){
		rey = (ReyDamas) fabrica.crear("reydamas", j1);
		piezas[1][1] = rey;
		d.mover(new Movimiento(1,1,2,2,j1));
		assertNull(t.getPieza(1, 1));
		assertEquals(rey,t.getPieza(2, 2));
		piezas[2][2] = rey;
		d.mover(new Movimiento(2,2,1,1,j1));
		assertNull(t.getPieza(2, 2));
		assertEquals(rey,t.getPieza(1, 1));	
	}
	
	@Test
	public void testTransformacionDama(){
		piezas[6][6] = dama1;
		d.mover(new Movimiento(6,6,7,7,j1));
		assertNull(t.getPieza(6, 6));
		assertTrue(piezas[7][7].getTipo() == 2);
	}
}
