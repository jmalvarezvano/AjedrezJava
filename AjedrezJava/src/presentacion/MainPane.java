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

import javax.imageio.ImageIO;

import logica.Jugador;
import logica.Observer;
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
	int indiceHistorial;
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
		if (!east_pane.isVisible()) {
			east_pane.setVisible(true);
			pack();
			setLocationRelativeTo(null);
		}

		mediador.juegoNuevo();
		loadPieceImages();
		board_pane.newGame();
		board_pane.repaint();

	}

	// public void play(){
	// Thread t = new Thread(){
	// public void run(){
	// while(true){
	// switch(state){
	// case GameData.HUMAN_MOVE:
	// break;
	// case GameData.COMPUTER_MOVE:
	// if(gameEnded(GameData.COMPUTER)){
	// state = GameData.GAME_ENDED;
	// break;
	// }
	// move = move_searcher.alphaBeta(GameData.COMPUTER, position,
	// Integer.MIN_VALUE, Integer.MAX_VALUE,
	// play_options.levelSlider.getValue()).last_move;
	// state = GameData.PREPARE_ANIMATION;
	// break;
	// case GameData.PREPARE_ANIMATION:
	// prepareAnimation();
	// break;
	// case GameData.ANIMATING:
	// animate();
	// break;
	// case GameData.GAME_ENDED: return;
	// }
	// try{
	// Thread.sleep(3);
	// }catch(Exception e){
	// e.printStackTrace();
	// }
	// }
	// }
	// };
	// t.start();
	// }
	// public boolean gameEnded(int player){
	// int result = game.getResult(player);
	// boolean end_game = false;
	// String color ="";
	// if(player == GameData.COMPUTER){
	// color = (is_white)?"White":"Black";
	// }else color = (is_white)?"Black":"White";
	// if(result == GameData.CHECKMATE){
	// showEndGameResult(color+" wins by CHECKMATE");
	// end_game = true;
	// }else if(result == GameData.DRAW){
	// showEndGameResult("DRAW");
	// end_game = true;
	// }
	// return end_game;
	// }
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
			setLocationRelativeTo(null);
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
	}

	public class ChessBoardPane extends JPanel implements MouseListener {
		Image animating_image;
		int origenX, origenY, destinoX, destinoY;
		boolean segundoClick, seleccionandoPieza;

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
			g.drawImage(images.get(GameData.MYCHESSMATE), 20, 36, this);

			g.drawImage(images.get(GameData.BOARD_IMAGE), 20, 65, this);
			if (juegoActivo) {
				if (turno == mediador.getJugador1())
					g.drawImage(images.get(Pieza.KING), 20, 20, this); // cambiar
																		// esta
																		// imagen
				else
					g.drawImage(images.get(-Pieza.KING), 20, 20, this); // cambiar
																		// esta
																		// imagen

				int x = 0, y = 0;
				Pieza pieza;
				System.out.println(Thread.activeCount());
				if (seleccionandoPieza)
					g.drawImage(images.get(GameData.GLOW), origenX * 45 + 45, (7 - origenY) * 45 + 90, this);

				// bloque try catch para pruebas. Se puede quitar, x e y tambien
				try {
					for (int i = 45; i <= 360; i += 45) {

						for (int j = 90; j <= 405; j += 45) {
							x = i;
							y = j;
							pieza = mediador.getTablero().getPieza((i - 45) / 45, 7 - (j - 90) / 45);
							if (pieza != null) {
								if (pieza.getJugador().equals(mediador.getJugador1())) {
									g.drawImage(images.get(pieza.getTipo()), i, j, this);
								} else {
									g.drawImage(images.get(-pieza.getTipo()), i, j, this);
								}
							}

						}
					}
				} catch (Exception e) {
					System.out.println("i: " + x + " j: " + y);
				}
			}

		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (juegoActivo) {

				Point p = e.getPoint();
				int xActual = (int) (p.getX() - 45) / 45;
				int yActual = (int) (8 - (p.getY() - 90) / 45);

				System.out.println("x: " + p.getX() + " y: " + p.getY());
				if (segundoClick) {
					destinoX = xActual;
					destinoY = yActual;

					turno.moverPieza(origenX, origenY, destinoX, destinoY);
					indiceHistorial = Conserje.getSingleton().numEstadosGuardados() - 1;
					segundoClick = false;
					seleccionandoPieza = false;
					System.out.println(origenX + " " + origenY + " to " + destinoX + " " + destinoY);
				} else {
					seleccionandoPieza = true;
					origenX = xActual;
					origenY = yActual;
					segundoClick = true;

				}
				history_pane.repaint();
				repaint();
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}

	public class HistoryBoardPane extends JPanel implements MouseListener {
		public HistoryBoardPane() {
			setBackground(bg_color);
			setPreferredSize(new Dimension(300, 330));
			addMouseListener(this);

		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(images.get(GameData.HISTORY_TITLE), 20, 15, this);
			g.drawImage(images.get(GameData.BOARD_IMAGE2), 14, 44, this);
			if (juegoActivo) {
				System.out.println("indiceHistorial: " + indiceHistorial);
				Pieza[][] celdas = Conserje.getSingleton().get(indiceHistorial).getState();
				System.out.println(Conserje.getSingleton().get(indiceHistorial).toString());

				Pieza pieza;
				for (int i = 30; i <= 240; i += 30) {
					for (int j = 60; j <= 270; j += 30) {
						pieza = celdas[(i - 30) / 30][7 - (j - 60) / 30];
						// pieza = mediador.getTablero().getCelda((i - 30) / 30,
						// 7 - (j - 60) / 30).getPieza();
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

		@Override
		public void mouseClicked(MouseEvent e) {

			Point p = e.getPoint();

			System.out.println("x: " + p.getX() + " y: " + p.getY());

		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}

	// super.paintComponent(g);
	// g.drawImage(images.get(GameData.HISTORY_TITLE), 20, 15, this);
	// g.drawImage(images.get(GameData.BOARD_IMAGE2), 14, 44, this);
	// if (history_positions.size() <= 0)
	// return;
	// Position _position = history_positions.get(history_count);
	// for (int i = 0; i < _position.board.length - 11; i++) {
	// if (_position.board[i] == GameData.EMPTY)
	// continue;
	// if (_position.board[i] == GameData.ILLEGAL)
	// continue;
	// int x = i % 10;
	// int y = (i - x) / 10;
	// if (_position.board[i] > 0) {
	// int piece = _position.human_pieces[_position.board[i]].value;
	// g.drawImage(images.get(piece + 10), x * 30, y * 30, this);
	// } else {
	// int piece = _position.computer_pieces[-_position.board[i]].value;
	// g.drawImage(images.get(-piece + 10), x * 30, y * 30, this);
	// }
	// }
	// }
	// }

	// public boolean checkCastling(int destination){
	// Piece king = position.human_pieces[8];
	// Piece right_rook = position.human_pieces[6];
	// Piece left_rook = position.human_pieces[5];
	//
	// if(king.has_moved) return false;
	// int source = move.source_location;
	//
	// if(right_rook == null && left_rook == null) return false;
	// if(right_rook != null && right_rook.has_moved &&
	// left_rook != null && left_rook.has_moved) return false;
	//
	// if(is_white){
	// if(source != 95) return false;
	// if(destination != 97 && destination != 93) return false;
	// if(destination == 97){
	// if(position.board[96] != GameData.EMPTY) return false;
	// if(position.board[97] != GameData.EMPTY) return false;
	// if(!game.safeMove(GameData.HUMAN,source,96)) return false;
	// if(!game.safeMove(GameData.HUMAN,source,97)) return false;
	// }else if(destination == 93){
	// if(position.board[94] != GameData.EMPTY) return false;
	// if(position.board[93] != GameData.EMPTY) return false;
	// if(!game.safeMove(GameData.HUMAN,source,94)) return false;
	// if(!game.safeMove(GameData.HUMAN,source,93)) return false;
	// }
	// }else{
	// if(source != 94) return false;
	// if(destination != 92 && destination != 96) return false;
	// if(destination == 92){
	// if(position.board[93] != GameData.EMPTY) return false;
	// if(position.board[92] != GameData.EMPTY) return false;
	// if(!game.safeMove(GameData.HUMAN,source,93)) return false;
	// if(!game.safeMove(GameData.HUMAN,source,92)) return false;
	// }else if(destination == 96){
	// if(position.board[95] != GameData.EMPTY) return false;
	// if(position.board[96] != GameData.EMPTY) return false;
	// if(!game.safeMove(GameData.HUMAN,source,95)) return false;
	// if(!game.safeMove(GameData.HUMAN,source,96)) return false;
	// }
	// }
	// return castling=true;
	// }
	public int boardValue(int value) {
		return value / 45;
	}

	// Cargar imagenes en la memoria para el uso posterior
	public void loadPieceImages() {
		char[] resource_keys = { 'p', 'n', 'b', 'r', 'q', 'k' };
		int[] images_keys = { Pieza.PAWN, Pieza.KNIGHT, Pieza.BISHOP, Pieza.ROOK, Pieza.QUEEN, Pieza.KING };
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
			images.put(GameData.MYCHESSMATE, ImageIO.read(resource.getResource("mychessmate")));
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
		int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "MyChessmate1.1",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (option == JOptionPane.YES_OPTION)
			System.exit(0);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main() {
		// TODO code application logic here
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {

			}
		});
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

	public int promoverPeon(boolean esBlanco) {
		PromotionPane promotion_pane = new PromotionPane(this);
		promotion_pane.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		promotion_pane.setIcons(esBlanco);
		return promotion_pane.showDialog();
	}

}
