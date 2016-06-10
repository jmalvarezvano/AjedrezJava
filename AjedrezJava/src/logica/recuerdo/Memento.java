package logica.recuerdo;

import logica.piezas.Pieza;

public class Memento {
	
	private Pieza[][] state;

	   public Memento(Pieza[][] state){
	      this.state = state; 	     
	   }

	   //en lugar de devolver state se crea un nuevo array para poder volver a este estado varias veces
	   public Pieza[][] getState(){
		   Pieza[][] res = new Pieza[8][8];
			for (int i = 0; i <= 7; i++) {			
				for (int j = 0; j <= 7; j++)
					res[i][j] = state[i][j];
			}
			return res;	
	   }	   
	  
}
