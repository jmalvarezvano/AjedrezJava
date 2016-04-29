package logica;

public class Rey extends Pieza {
	private boolean jaque;

	public boolean isJaque() {
		return jaque;
	}

	public void setJaque(boolean jaque) {
		this.jaque = jaque;
	}

	public Rey(Jugador j) {
		super(j);
		jaque = false;
		// TODO Auto-generated constructor stub
	}
}
