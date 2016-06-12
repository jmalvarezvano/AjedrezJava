package pruebas;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
@RunWith(Suite.class)
@Suite.SuiteClasses({ 
   TestDamas.class, TestInterfaz.class, TestJuegoEstandar.class, testJuegoInvertido.class, testSinPrisioneros.class
   ,TestTablero.class
})
public class TestAll {

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
