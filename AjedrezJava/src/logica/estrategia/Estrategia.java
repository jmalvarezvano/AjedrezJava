package logica.estrategia;

import logica.Movimiento;

public interface Estrategia {
		
	public void juegoNuevo();	
	public boolean moverYAnimarPieza(Movimiento movimiento);


}
