package logica;

public class Rey extends Pieza {
	
	private boolean jaque;
	private boolean celdasDefendidasPorRival[][];  //variable usada para el jaque y jaque mate

	public Rey(Jugador j) {
		super(j, "rey");
		jaque = false;
		celdasDefendidasPorRival = new boolean[8][8];
	}
	
	public boolean[][] getCeldasDefendidasPorRival() {
		return celdasDefendidasPorRival;
	}

	public void setCeldasDefendidasPorRival(boolean[][] piezasDefendidasPorRival) {
		this.celdasDefendidasPorRival = piezasDefendidasPorRival;
	}
	
	public boolean esCeldaDefendidaPorRival(int x, int y) {
		if (x < 0 || x > 8 || y < 0 || y > 8) return false;
		return celdasDefendidasPorRival[x][y];
	}
	
	public void setCeldaDefendidaPorRival(int x, int y, boolean nuevoEstado) {
		celdasDefendidasPorRival[x][y] = nuevoEstado;
	}

	public boolean isJaque() {
		return jaque;
	}

	public void setJaque(boolean jaque) {
		this.jaque = jaque;
	}
	
	//toString para pruebas en consola
		@Override
		public String toString() {
			return "y";
		}
}
