package logica.recuerdo;

import logica.Celda;

public class Memento {
	
	private Celda[][] state;

	   public Memento(Celda[][] state){
	      this.state = state;
	   }

	   public Celda[][] getState(){
	      return state;
	   }

}
