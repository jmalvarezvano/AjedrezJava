package logica;

public class Fabrica {
	
	private static Fabrica fabrica;
	
	/*
	 * Patrón método fábrica.
	 */
	public Pieza crearPieza(String p, Jugador j) {
		p = p.toLowerCase();
		if (p.equals("alfil")) return new Alfil(j);
		if (p.equals("caballo")) return new Caballo(j);
		if (p.equals("peon")) return new Peon(j);
		if (p.equals("reina")) return new Reina(j);
		if (p.equals("rey")) return new Rey(j);
		if (p.equals("torre")) return new Torre(j);
		return null;
	}
	
	/*
	 * Patrón singleton.
	 */
	public static Fabrica getSingleton() {
		if(fabrica == null) fabrica = new Fabrica();
		return fabrica;
	}
}
