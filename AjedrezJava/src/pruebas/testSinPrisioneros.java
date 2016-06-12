package pruebas;
import static org.junit.Assert.*;

import org.junit.Test;

import logica.FabricaPiezas;
import logica.Jugador;
import logica.Movimiento;
import logica.estrategia.MockSinPrisioneros;
import logica.piezas.Alfil;
import logica.piezas.Pieza;
import logica.piezas.Rey;
import logica.recuerdo.Conserje;
import logica.recuerdo.Memento;
import logica.recuerdo.Tablero;
public class testSinPrisioneros {
	
	MockSinPrisioneros sp = new MockSinPrisioneros();
	
	@Test
	public void testMovimiento(){
		FabricaPiezas fabrica =  FabricaPiezas.getSingleton();
		Tablero t = new Tablero(sp);
		Pieza[][] piezas = new Pieza[8][8];
		t.setPiezas(piezas);
		Jugador j1 = new Jugador(sp);
		Jugador j2 = new Jugador(sp);
		sp.juegoNuevo(t, j1, j2);
		
		Rey r1 = (Rey)fabrica.crear("Rey", j1);
		Rey r2 = (Rey)fabrica.crear("Rey", j2);
		Alfil a1 = (Alfil)fabrica.crear("Alfil", j1);
		Alfil a2 = (Alfil)fabrica.crear("Alfil", j1);
		
		piezas[0][6] = r1;
		piezas[1][6] = a1;
		piezas[1][5] = r2;
		piezas[3][5] = a2;
		t.setPiezas(piezas);
		
		Memento m = new Memento(piezas);
		Conserje c = Conserje.getSingleton();
		c.add(m);
		
		sp.cambiarTurno(1);
		//la partida termina si se mata al rey
		assertTrue(sp.moverYAnimarPieza(new Movimiento(1,5,0,6,j2)));
		assertEquals(r2,t.getPieza(0, 6));
		
		
			
	}

}
