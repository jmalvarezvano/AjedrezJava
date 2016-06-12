package pruebas;
import static org.junit.Assert.*;

import javax.swing.JPanel;
import org.junit.Test;
import logica.estrategia.JuegoEstandar;
import presentacion.MainPane;
public class TestInterfaz {

	MainPane main = new MainPane();
	JuegoEstandar je = new JuegoEstandar();

	
	
	@Test
	public void testInterfaz(){
		main.newGame(je);
		JPanel main_pane = main.getMainPane();
		assertTrue(main_pane.isVisible());
		assertTrue(main_pane.getComponents().length == 3);
	}
		
}
