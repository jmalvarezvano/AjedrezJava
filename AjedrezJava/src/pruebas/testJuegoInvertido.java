package pruebas;
import static org.junit.Assert.*;

import org.junit.Test;

import logica.FabricaPiezas;
import logica.Jugador;
import logica.piezas.Rey;
import logica.estrategia.MockJuegoInvertido;
import logica.piezas.Alfil;
import logica.piezas.Pieza;
import logica.recuerdo.Conserje;
import logica.recuerdo.Memento;
import logica.recuerdo.Tablero;
public class testJuegoInvertido {

	MockJuegoInvertido ji = new MockJuegoInvertido();
	
	@Test
	public void testFinJuegoInvertido(){
		FabricaPiezas fabrica =  FabricaPiezas.getSingleton();
		Tablero t = new Tablero(ji);
		Pieza[][] piezas = new Pieza[8][8];
		//t.setPiezas(piezas);
		Jugador j1 = new Jugador(ji);
		Jugador j2 = new Jugador(ji);
		
		Rey r1 = (Rey)fabrica.crear("Rey", j1);
		Rey r2 = (Rey)fabrica.crear("Rey", j2);
		Alfil a1 = (Alfil)fabrica.crear("Alfil", j1);
		Alfil a2 = (Alfil)fabrica.crear("Alfil", j1);
		
		piezas[0][7] = r1;
		piezas[1][6] = a1;
		piezas[1][5] = r2;
		piezas[3][5] = a2;
		t.setPiezas(piezas);
		ji.juegoNuevo(t, j1, j2);
		
		Memento m = new Memento(piezas);
		Conserje c = Conserje.getSingleton();
		c.add(m);
		
		//System.out.println(t.getPieza(0,0));
		assertTrue(ji.comprobarFinJuego(0));
		
	}
}
