package logica.recuerdo;

import java.util.ArrayList;
import java.util.List;

public class Conserje {

	private List<Memento> mementoList = new ArrayList<Memento>();

	private static Conserje conserje;
	
	private Conserje() {}

	public static Conserje getSingleton() {
		if (conserje == null)
			conserje = new Conserje();
		return conserje;
	}

	public void add(Memento state) {
		mementoList.add(state);
		System.out.println(mementoList.size()+" estados del Tablero guardados");
	}

	public Memento get(int index) {
		return mementoList.get(index);
		
	}
	
	public int numEstadosGuardados() {
		return mementoList.size();
	}

	public void reset() {
		mementoList = new ArrayList<Memento>();
	}

	public void resetFrom(int i) {
		// a implementar
	}

}
