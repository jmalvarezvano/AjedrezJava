package logica.recuerdo;

import logica.piezas.Pieza;

public class Memento {
	
	private Pieza[][] state;

	   public Memento(Pieza[][] state){
	      this.state = state; 	     
	   }

	   public Pieza[][] getState(){
	      return state;
	   }	   
	  
}
