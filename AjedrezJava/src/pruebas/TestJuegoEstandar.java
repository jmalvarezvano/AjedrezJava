package pruebas;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import logica.FabricaPiezas;
import logica.Jugador;
import logica.Movimiento;
import logica.estrategia.JuegoEstandar;
import logica.estrategia.MockJuegoEstandar;
import logica.piezas.Alfil;
import logica.piezas.Caballo;
import logica.piezas.Peon;
import logica.piezas.Pieza;
import logica.piezas.Reina;
import logica.piezas.Rey;
import logica.piezas.Torre;
import logica.recuerdo.Conserje;
import logica.recuerdo.Memento;
import logica.recuerdo.Tablero;
import presentacion.MainPane;

public class TestJuegoEstandar {

	static MockJuegoEstandar je = new MockJuegoEstandar();
	static Jugador j1 = new Jugador(je);
	static Jugador j2 = new Jugador(je);
	static Tablero t = new Tablero(je);
	static Pieza[][] piezas;
	static FabricaPiezas fabrica;

	
	@BeforeClass
	public static void inicializar(){
		
		fabrica = FabricaPiezas.getSingleton();
		piezas = new Pieza[8][8];
		t.setPiezas(piezas);
		je.setJugador1(j1);
		je.setJugador2(j2);
		je.juegoNuevo(t,j1,j2);
	}
	
	@After
	public void limpiarTablero(){
		piezas = new Pieza[8][8];
		t.setPiezas(piezas);
	}
	
	@Test
	public void testMovimientoPeon(){
		//test movimiento peon j1
		Peon p1 = (Peon) fabrica.crear("peon", j1);
		Rey r1 = (Rey) fabrica.crear("rey", j1);
		Rey r2 = (Rey) fabrica.crear("rey", j2);
		j1.addPieza(p1);
		j1.addPieza(r1);
		j2.addPieza(r2);
		piezas[0][1] = p1;
		piezas[2][0] = r1;
		piezas[5][0] = r2;
		je.moverYAnimarPieza(new Movimiento(0,1,0,2,j1));
		assertNull(piezas[0][1]);
		assertEquals(p1,piezas[0][2]);
		je.moverYAnimarPieza(new Movimiento(0,2,0,1,j1));
		assertNull(piezas[0][1]);
		assertEquals(p1,piezas[0][2]);
		je.moverYAnimarPieza(new Movimiento(0,2,1,2,j1));
		assertNull(piezas[1][2]);
		assertEquals(p1,piezas[0][2]);
	}
	
	@Test
	public void testMovimientoTorre(){
		Pieza t1 = fabrica.crear("torre", j1);
		Rey r1 = (Rey) fabrica.crear("rey", j1);
		Rey r2 = (Rey) fabrica.crear("rey", j2);

		piezas[0][0] = t1;
		piezas[2][0] = r1;
		piezas[5][1] = r2;
		t.setPiezas(piezas);
		//test movimiento torre 
		
		Memento m = new Memento(piezas);
		Conserje c = Conserje.getSingleton();
		c.add(m);
	;
		je.moverYAnimarPieza(new Movimiento(0,0,0,5,j1));
		assertNull(t.getPieza(0, 0));
		assertEquals(t1.getTipo(),t.getPieza(0, 5).getTipo());
		
		je.moverYAnimarPieza(new Movimiento(0,5,3,5,j1));
		assertNull(piezas[0][5]);
		assertEquals(t1.getTipo(),t.getPieza(3, 5).getTipo());
		
		je.moverYAnimarPieza(new Movimiento(3,5,4,6,j1));
		assertNull(t.getPieza(4, 6));
		assertEquals(t1.getTipo(),t.getPieza(3, 5).getTipo());
	}
	
	@Test
	public void testMovimientoAlfil(){
		Alfil a1 = (Alfil) fabrica.crear("alfil", j1);
		Peon p1 = (Peon) fabrica.crear("peon", j1);
		Rey r1 = (Rey) fabrica.crear("rey", j1);
		Rey r2 = (Rey) fabrica.crear("rey", j2);
		piezas[0][0] = a1;
		piezas[2][0] = r1;
		piezas[5][0] = r2;
		t.setPiezas(piezas);
		
		//test movimiento alfil 
		je.moverYAnimarPieza(new Movimiento(0,0,5,5,j1));
		assertNull(piezas[0][0]);
		assertEquals(a1,piezas[5][5]);
		piezas[0][0] = a1;
		piezas[1][1] = p1;
		je.moverYAnimarPieza(new Movimiento(0,0,2,2,j1));
		assertNull(piezas[2][2]);
		assertEquals(a1,piezas[0][0]);
		je.moverYAnimarPieza(new Movimiento(0,0,-1,1,j1));
		assertEquals(a1,piezas[0][0]);
	}
	
	@Test
	public void testMovimientoCaballo(){
		Caballo c1 = (Caballo) fabrica.crear("caballo", j1);
		Peon p1 = (Peon) fabrica.crear("peon", j1);
		Rey r1 = (Rey) fabrica.crear("rey", j1);
		Rey r2 = (Rey) fabrica.crear("rey", j2);
		piezas[0][0] = c1;
		piezas[2][0] = r1;
		piezas[5][0] = r2;
		piezas[0][1] = p1;
		t.setPiezas(piezas);
		je.moverYAnimarPieza(new Movimiento(0,0,1,2,j1));
		assertNull(piezas[0][0]);
		assertEquals(c1,piezas[1][2]);
		je.moverYAnimarPieza(new Movimiento(1,2,-1,3,j1));
		assertNotNull(piezas[1][2]);
		assertEquals(c1,piezas[1][2]);
	}
	
	@Test
	public void testMovimientoRey(){
		Rey r1 = (Rey) fabrica.crear("rey", j1);
		Rey r2 = (Rey) fabrica.crear("rey", j2);	
		piezas[2][0] = r1;
		piezas[5][0] = r2;
		t.setPiezas(piezas);
	
		je.moverYAnimarPieza(new Movimiento(2,0,1,0,j1));
		assertNull(piezas[2][0]);
		assertEquals(piezas[1][0],r1);
		je.moverYAnimarPieza(new Movimiento(1,0,1,3,j1));
		assertEquals(r1,piezas[1][0]);
	}
	
	@Test
	public void testFinJuego(){
		Torre t2 = (Torre) fabrica.crear("torre", j2);
		Rey r1 = (Rey) fabrica.crear("rey", j1);
		Rey r2 = (Rey) fabrica.crear("rey", j2);
		j1.addPieza(t2);
		j1.addPieza(r1);
		j2.addPieza(r2);

		piezas [6][0] = t2;
		piezas[3][7] = r1;
		piezas[3][5] = r2;
		
		je.cambiarTurno(1);
		je.moverYAnimarPieza(new Movimiento(6,0,6,7,j2));
		je.cambiarTurno(0);
		assertTrue(je.comprobarFinJuego(0));
	}

	@Test
	public void testJaque(){
		Torre t2 = (Torre) fabrica.crear("torre", j2);
		Rey r1 = (Rey) fabrica.crear("rey", j1);
		Rey r2 = (Rey) fabrica.crear("rey", j2);
		j1.addPieza(t2);
		j1.addPieza(r1);
		j2.addPieza(r2);

		piezas [6][0] = t2;
		piezas[3][7] = r1;
		piezas[3][5] = r2;
		
		je.cambiarTurno(1);
		je.moverYAnimarPieza(new Movimiento(6,0,6,7,j2));
		je.cambiarTurno(0);
		assertTrue(j1.esJaque());
		
	}
	
	@Test 
	public void testAhogado(){
		Reina reina1 = (Reina) fabrica.crear("reina", j2);
		Rey r1 = (Rey) fabrica.crear("rey", j1);
		Rey r2 = (Rey) fabrica.crear("rey", j2);
		j1.addPieza(reina1);
		j1.addPieza(r1);
		j2.addPieza(r2);

		piezas [2][6] = reina1;
		piezas[1][4] = r1;
		piezas[0][7] = r2;
		
		je.moverYAnimarPieza(new Movimiento(1,4,1,5,j1));
		assertTrue(je.comprobarAhogado(j1));
	}
	
	@Test
	public void testMatarFicha(){
		Peon p1 = (Peon) fabrica.crear("peon", j1);
		Peon p2 = (Peon) fabrica.crear("peon", j2);
		Rey r1 = (Rey) fabrica.crear("rey", j1);
		Rey r2 = (Rey) fabrica.crear("rey", j2);
		j1.addPieza(p1);
		j1.addPieza(r1);
		j2.addPieza(r2);
		piezas[0][1] = p1;
		piezas [1][2] = p2;
		piezas[2][0] = r1;
		piezas[5][0] = r2;
			
		je.moverYAnimarPieza(new Movimiento(0,1,1,2,j1));
		assertNull(piezas[0][1]);
		assertEquals(p1,piezas[1][2]);
	}
	
	@Test
	public void testPromocionarPeon(){
		Peon p1 = (Peon) fabrica.crear("peon", j1);
		Rey r1 = (Rey) fabrica.crear("rey", j1);
		Rey r2 = (Rey) fabrica.crear("rey", j2);
		j1.addPieza(p1);
		j1.addPieza(r1);
		j2.addPieza(r2);
		piezas[0][6] = p1;
		piezas[2][0] = r1;
		piezas[5][0] = r2;
		
		je.moverYAnimarPieza(new Movimiento(0,6,0,7,j1));
		assertNull(piezas[0][6]);
		assertTrue(t.getPieza(0,7).getTipo() == Pieza.ROOK);
	}
	
}
