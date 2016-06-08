/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.imageio.ImageIO;

import logica.Jugador;
import logica.Movimiento;
import logica.Observer;
import logica.estrategia.Damas;
import logica.estrategia.Mediador;
import logica.piezas.Pieza;
import logica.recuerdo.Conserje;

/**
 *
 * @author Melvic
 */
public class MainPane extends JFrame implements MouseListener, Observer {
	ChessBoardPane board_pane;
	HistoryBoardPane history_pane;
	Mediador mediador;
	JPanel east_pane;
	Resource resource = new Resource();
	Jugador turno;
	int indiceHistorial, imagenTurno;
	boolean juegoActivo;
	Map<Integer, Image> images = new HashMap<Integer, Image>();
	Map<Integer, Icon> icon_images = new HashMap<Integer, Icon>();
	JLabel new_game, quit, about, history, first, prev, next, last;
	JPanel main_pane = new JPanel(new BorderLayout());
	PreferencesPane play_options;
	Color bg_color = Color.decode("#efd39c");

	public MainPane() {
		super("Ajedrez Proyecto DDS");
		setContentPane(main_pane);

		System.out.println(mediador);

		loadMenuIcons();
		loadBoardImages();

		board_pane = new ChessBoardPane();

		main_pane.add(createMenuPane(), BorderLayout.WEST);
		main_pane.add(board_pane, BorderLayout.CENTER);
		main_pane.setBackground(bg_color);
		createEastPane();
		pack();

		Dimension size = getSize();
		size.height = 523;
		setSize(size);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				quit();
			}
		});
	}

	public JPanel createMenuPane() {
		new_game = new JLabel(icon_images.get(GameData.NEW_BUTTON));
		about = new JLabel(icon_images.get(GameData.ABOUT_BUTTON));
		history = new JLabel(icon_images.get(GameData.HISTORY_BUTTON));
		quit = new JLabel(icon_images.get(GameData.QUIT_BUTTON));

		new_game.addMouseListener(this);
		about.addMouseListener(this);
		history.addMouseListener(this);
		quit.addMouseListener(this);

		JPanel pane = new JPanel(new GridLayout(4, 1));
		pane.add(new_game);
		pane.add(history);
		pane.add(about);
		pane.add(quit);
		pane.setBackground(bg_color);
		JPanel menu_pane = new JPanel(new BorderLayout());
		menu_pane.setBackground(bg_color);
		menu_pane.add(pane, BorderLayout.SOUTH);
		menu_pane.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 0));
		return menu_pane;
	}

	public void setTurno(Jugador jugador) {
		turno = jugador;
	}

	public void animarMovimiento(Movimiento movimiento) {
		board_pane.animarMovimiento(movimiento);
	}

	public void createEastPane() {
		east_pane = new JPanel(new BorderLayout());
		history_pane = new HistoryBoardPane();

		JPanel pane = new JPanel(new GridLayout(1, 4));
		first = new JLabel(icon_images.get(GameData.FIRST_BUTTON));
		prev = new JLabel(icon_images.get(GameData.PREV_BUTTON));
		next = new JLabel(icon_images.get(GameData.NEXT_BUTTON));
		last = new JLabel(icon_images.get(GameData.LAST_BUTTON));

		pane.add(first);
		pane.add(prev);
		pane.add(next);
		pane.add(last);

		JPanel pane2 = new JPanel();
		pane2.setLayout(new BoxLayout(pane2, BoxLayout.Y_AXIS));
		pane2.add(history_pane);
		pane2.add(pane);

		east_pane.add(pane2, BorderLayout.SOUTH);
		east_pane.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		east_pane.setBackground(bg_color);
		east_pane.setVisible(false);
		main_pane.add(east_pane, BorderLayout.EAST);

		pane.setBorder(BorderFactory.createEmptyBorder(0, 14, 0, 14));
		pane.setBackground(bg_color);

		first.addMouseListener(this);
		prev.addMouseListener(this);
		next.addMouseListener(this);
		last.addMouseListener(this);
	}

	public void newGame(Mediador mediador) {
		this.mediador = mediador;
		mediador.setInterfaz(this);
		if (mediador instanceof Damas)
			imagenTurno = Pieza.MAN;
		else
			imagenTurno = Pieza.KING;
		if (!east_pane.isVisible()) {
			east_pane.setVisible(true);
			pack();
			// setLocationRelativeTo(null);
		}

		mediador.juegoNuevo();
		loadPieceImages();
		board_pane.newGame();
		board_pane.repaint();

	}

	public void showEndGameResult(String message) {
		int option = JOptionPane.showOptionDialog(null, message, "Game Over", 0, JOptionPane.PLAIN_MESSAGE, null,
				new Object[] { "Play again", "Cancel" }, "Play again");
		if (option == 0) {
			play_options.setVisible(true);
		}
	}

	public void showNewGameWarning() {
		JOptionPane.showMessageDialog(null, "Start a new game after I made my move.\n", "Message",
				JOptionPane.PLAIN_MESSAGE);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	// OK
	@Override
	public void mouseEntered(MouseEvent e) {
		Object source = e.getSource();
		if (source == new_game) {
			new_game.setIcon(icon_images.get(GameData.NEW_BUTTON2));
		} else if (source == about) {
			about.setIcon(icon_images.get(GameData.ABOUT_BUTTON2));
		} else if (source == history) {
			history.setIcon(icon_images.get(GameData.HISTORY_BUTTON2));
		} else if (source == quit) {
			quit.setIcon(icon_images.get(GameData.QUIT_BUTTON2));
		} else if (source == first) {
			first.setIcon(icon_images.get(GameData.FIRST_BUTTON2));
		} else if (source == prev) {
			prev.setIcon(icon_images.get(GameData.PREV_BUTTON2));
		} else if (source == next) {
			next.setIcon(icon_images.get(GameData.NEXT_BUTTON2));
		} else if (source == last) {
			last.setIcon(icon_images.get(GameData.LAST_BUTTON2));
		}
	}

	// OK
	@Override
	public void mouseExited(MouseEvent e) {
		Object source = e.getSource();
		if (source == new_game) {
			new_game.setIcon(icon_images.get(GameData.NEW_BUTTON));
		} else if (source == about) {
			about.setIcon(icon_images.get(GameData.ABOUT_BUTTON));
		} else if (source == history) {
			history.setIcon(icon_images.get(GameData.HISTORY_BUTTON));
		} else if (source == quit) {
			quit.setIcon(icon_images.get(GameData.QUIT_BUTTON));
		} else if (source == first) {
			first.setIcon(icon_images.get(GameData.FIRST_BUTTON));
		} else if (source == prev) {
			prev.setIcon(icon_images.get(GameData.PREV_BUTTON));
		} else if (source == next) {
			next.setIcon(icon_images.get(GameData.NEXT_BUTTON));
		} else if (source == last) {
			last.setIcon(icon_images.get(GameData.LAST_BUTTON));
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Object source = e.getSource();
		if (source == quit) {
			quit();
		} else if (source == new_game) {

			if (play_options == null) {
				play_options = new PreferencesPane(this);
			}

			play_options.setVisible(true);
		} else if (source == about) {
			AboutPane.createAndShowUI();
		} else if (source == history) {
			east_pane.setVisible(!east_pane.isVisible());
			pack();
			// setLocationRelativeTo(null);
		} else if (source == first) {
			indiceHistorial = 0;
			history_pane.repaint();

		} else if (source == prev) {
			if (indiceHistorial > 0) {
				indiceHistorial--;
				history_pane.repaint();
			}

		} else if (source == next) {
			if (indiceHistorial < Conserje.getSingleton().numEstadosGuardados() - 1) {
				indiceHistorial++;
				history_pane.repaint();
			}

		} else if (source == last) {
			indiceHistorial = Conserje.getSingleton().numEstadosGuardados() - 1;
			history_pane.repaint();

		}
	}

	public class ChessBoardPane extends JPanel implements MouseListener {
		int origenX, origenY, destinoX, destinoY, destinoXAnimacion, destinoYAnimacion;
		boolean segundoClick, seleccionandoPieza, animando;
		double animadoX, animadoY;

		public ChessBoardPane() {
			setPreferredSize(new Dimension(450, 495));
			setBackground(bg_color);
			addMouseListener(this);
		}

		public void newGame() {
			segundoClick = false;
			seleccionandoPieza = false;
			juegoActivo = true;
			indiceHistorial = 0;
			history_pane.repaint();
		}

		// dibujar tabla
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(images.get(GameData.TITLE), 20, 30, this);
			g.drawImage(images.get(GameData.BOARD_IMAGE), 20, 65, this);
			if (juegoActivo) {
				g.drawImage(images.get(GameData.TURN), 320, 35, this);
				if (turno == mediador.getJugador1())
					g.drawImage(images.get(imagenTurno), 385, 20, this);
				else
					g.drawImage(images.get(-imagenTurno), 385, 20, this);
				int x = 0, y = 0;
				Pieza pieza;
				if (seleccionandoPieza)
					g.drawImage(images.get(GameData.GLOW), indiceXToCoordinada(origenX), indiceYToCoordinada(origenY),
							this);
				for (int i = 45; i <= 360; i += 45) {
					for (int j = 90; j <= 405; j += 45) {
						x = coordinadaXToIndice(i);
						y = coordinadaYToIndice(j);
						pieza = mediador.getTablero().getPieza(x, y);
						if (pieza != null) {
							if (x == destinoXAnimacion && y == destinoYAnimacion && animando) {
								g.drawImage(images.get(GameData.GLOW2), indiceXToCoordinada(destinoXAnimacion),
										indiceYToCoordinada(destinoYAnimacion), this);
								if (pieza.getJugador().equals(mediador.getJugador1())) {
									g.drawImage(images.get(pieza.getTipo()), (int) animadoX, (int) animadoY, this);
								} else {
									g.drawImage(images.get(-pieza.getTipo()), (int) animadoX, (int) animadoY, this);
								}
							} else {
								if (pieza.getJugador().equals(mediador.getJugador1())) {
									g.drawImage(images.get(pieza.getTipo()), i, j, this);
								} else {
									g.drawImage(images.get(-pieza.getTipo()), i, j, this);
								}
							}
						}

					}
				}

			}

		}

		public int indiceXToCoordinada(int indice) {
			return indice * 45 + 45;
		}

		public int indiceYToCoordinada(int indice) {
			return (7 - indice) * 45 + 90;
		}

		public int coordinadaXToIndice(double coord) {
			return (int) ((coord - 45) / 45);
		}

		public int coordinadaYToIndice(double coord) {
			return (int) (7 - ((coord - 90) / 45));
		}

		@Override
		public void mouseClicked(MouseEvent e) {

		}


		public void animarMovimiento(Movimiento movimiento) {
			Thread t = new Thread() {
				public void run() {
					animar(movimiento);
				}

			};
			t.start();
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		private void animar(Movimiento movimiento) {

			destinoXAnimacion = movimiento.destinoX;
			destinoYAnimacion = movimiento.destinoY;
			double incrementoX = (indiceXToCoordinada(movimiento.destinoX) - indiceXToCoordinada(movimiento.origenX))
					/ 30.0;
			double incrementoY = (indiceYToCoordinada(movimiento.destinoY) - indiceYToCoordinada(movimiento.origenY))
					/ 30.0;
			animadoX = indiceXToCoordinada(movimiento.origenX);
			animadoY = indiceYToCoordinada(movimiento.origenY);
			animando = true;
			for (int i = 0; i < 30; i++) {
				animadoX += incrementoX;
				animadoY += incrementoY;
				repaint();
				try {
					Thread.sleep(16);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			animando = false;
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {

			Point p = e.getPoint();

			// si está dentro de los bordes del tablero
			if (juegoActivo && p.getX() <= 405 && p.getX() >= 45 && p.getY() <= 450 && p.getY() >= 90) {
				int xActual = (int) (p.getX() - 45) / 45;
				int yActual = (int) (8 - (p.getY() - 90) / 45);

				System.out.println("x: " + p.getX() + " y: " + p.getY());
				if (segundoClick) {
					destinoX = xActual;
					destinoY = yActual;

					segundoClick = false;
					seleccionandoPieza = false;
					System.out.println(origenX + " " + origenY + " to " + destinoX + " " + destinoY);

					new Thread() {
						public void run() {
							turno.moverPieza(origenX, origenY, destinoX, destinoY);
							indiceHistorial = Conserje.getSingleton().numEstadosGuardados() - 1;
							history_pane.repaint();
							repaint();
						}
					}.start();
				} else {
					Pieza seleccionada = mediador.getTablero().getPieza(xActual, yActual);
					if (seleccionada != null && seleccionada.getJugador().equals(turno)) {

						seleccionandoPieza = true;
						origenX = xActual;
						origenY = yActual;
						segundoClick = true;
					}
				}
				repaint();
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}

	public class HistoryBoardPane extends JPanel {
		public HistoryBoardPane() {
			setBackground(bg_color);
			setPreferredSize(new Dimension(300, 330));
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(images.get(GameData.HISTORY_TITLE), 20, 15, this);
			g.drawImage(images.get(GameData.BOARD_IMAGE2), 15, 45, this);
			if (juegoActivo) {
				System.out.println("indiceHistorial: " + indiceHistorial);
				Pieza[][] celdas = Conserje.getSingleton().get(indiceHistorial).getState();
				System.out.println(Conserje.getSingleton().get(indiceHistorial).toString());

				Pieza pieza;
				for (int i = 30; i <= 240; i += 30) {
					for (int j = 60; j <= 270; j += 30) {
						pieza = celdas[(i - 30) / 30][7 - (j - 60) / 30];
						if (pieza != null) {
							if (pieza.getJugador().equals(mediador.getJugador1())) {
								g.drawImage(images.get(pieza.getTipo() + 10), i, j, this);
							} else {
								g.drawImage(images.get(-pieza.getTipo() + 10), i, j, this);
							}
						}
					}
				}
			}
		}

	}

	// Cargar imagenes en la memoria para el uso posterior
	// Modificar este método cuando se tienen los iconos para damas
	public void loadPieceImages() {
		char[] resource_keys = { 'p', 'n', 'b', 'r', 'q', 'k', 'm', 'c' };
		int[] images_keys = { Pieza.PAWN, Pieza.KNIGHT, Pieza.BISHOP, Pieza.ROOK, Pieza.QUEEN, Pieza.KING, Pieza.MAN,
				Pieza.CHECKERS_KING };
		try {
			for (int i = 0; i < resource_keys.length; i++) {
				images.put(images_keys[i], ImageIO.read(resource.getResource("w" + resource_keys[i])));
				images.put(-images_keys[i], ImageIO.read(resource.getResource("b" + resource_keys[i])));
				images.put(images_keys[i] + 10, ImageIO.read(resource.getResource("w" + resource_keys[i] + '2')));
				images.put(-images_keys[i] + 10, ImageIO.read(resource.getResource("b" + resource_keys[i] + '2')));
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	// Cargar imagenes en la memoria para el uso posterior
	public void loadBoardImages() {
		try {
			images.put(GameData.BOARD_IMAGE, ImageIO.read(resource.getResource("chessboard")));
			images.put(GameData.BOARD_IMAGE2, ImageIO.read(resource.getResource("history_board")));
			images.put(GameData.GLOW, ImageIO.read(resource.getResource("glow")));
			images.put(GameData.GLOW2, ImageIO.read(resource.getResource("glow2")));
			images.put(GameData.HISTORY_TITLE, ImageIO.read(resource.getResource("history_title")));
			images.put(GameData.TURN, ImageIO.read(resource.getResource("turn")));
			images.put(GameData.TITLE, ImageIO.read(resource.getResource("title")));

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	// Cargar imagenes en la memoria para el uso posterior
	public void loadMenuIcons() {
		icon_images.put(GameData.NEW_BUTTON, new ImageIcon(resource.getResource("new_game")));
		icon_images.put(GameData.NEW_BUTTON2, new ImageIcon(resource.getResource("new_game_hover")));
		icon_images.put(GameData.QUIT_BUTTON, new ImageIcon(resource.getResource("quit")));
		icon_images.put(GameData.QUIT_BUTTON2, new ImageIcon(resource.getResource("quit_hover")));
		icon_images.put(GameData.HISTORY_BUTTON, new ImageIcon(resource.getResource("history")));
		icon_images.put(GameData.HISTORY_BUTTON2, new ImageIcon(resource.getResource("history_hover")));
		icon_images.put(GameData.ABOUT_BUTTON, new ImageIcon(resource.getResource("about")));
		icon_images.put(GameData.ABOUT_BUTTON2, new ImageIcon(resource.getResource("about_hover")));

		icon_images.put(GameData.FIRST_BUTTON, new ImageIcon(resource.getResource("first")));
		icon_images.put(GameData.FIRST_BUTTON2, new ImageIcon(resource.getResource("first_hover")));
		icon_images.put(GameData.NEXT_BUTTON, new ImageIcon(resource.getResource("next")));
		icon_images.put(GameData.NEXT_BUTTON2, new ImageIcon(resource.getResource("next_hover")));
		icon_images.put(GameData.PREV_BUTTON, new ImageIcon(resource.getResource("previous")));
		icon_images.put(GameData.PREV_BUTTON2, new ImageIcon(resource.getResource("previous_hover")));
		icon_images.put(GameData.LAST_BUTTON, new ImageIcon(resource.getResource("last")));
		icon_images.put(GameData.LAST_BUTTON2, new ImageIcon(resource.getResource("last_hover")));

	}

	public void quit() {
		int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Exit",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (option == JOptionPane.YES_OPTION)
			System.exit(0);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					boolean nimbusFound = false;
					for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
						if (info.getName().equals("Nimbus")) {
							UIManager.setLookAndFeel(info.getClassName());
							nimbusFound = true;
							break;
						}
					}
					if (!nimbusFound) {
						int option = JOptionPane.showConfirmDialog(null,
								"Nimbus Look And Feel not found\n" + "Do you want to proceed?", "Warning",
								JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
						if (option == JOptionPane.NO_OPTION) {
							System.exit(0);
						}
					}
					MainPane mcg = new MainPane();
					mcg.pack();
					mcg.setLocationRelativeTo(null);
					mcg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					mcg.setResizable(false);
					mcg.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getStackTrace());
					e.printStackTrace();
				}

			}
		});

	}

	public int promoverPeon(boolean esBlanco) {
		PromotionPane promotion_pane = new PromotionPane(this);
		promotion_pane.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		promotion_pane.setIcons(esBlanco);
		return promotion_pane.showDialog();
	}

}
