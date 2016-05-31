/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package presentacion;

import javax.swing.*;

import logica.estrategia.JuegoEstandar;
import logica.recuerdo.Conserje;

import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Melvic
 */
public class PreferencesPane extends JFrame implements ActionListener {
	JSlider levelSlider;
	JRadioButton clasico;
	JRadioButton invertido;
	JRadioButton damas;
	JButton ok;
	JButton cancel;
	MainPane chessmate;

	public PreferencesPane(MainPane chessmate) {
		super("New Game");
		this.chessmate = chessmate;
		JPanel mainPane = new JPanel(new BorderLayout());
		mainPane.add(createColorPane(), BorderLayout.CENTER);
		mainPane.add(createButtonPane(), BorderLayout.SOUTH);
		mainPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setContentPane(mainPane);
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		ok.addActionListener(this);
		cancel.addActionListener(this);
	}

	public JPanel createColorPane() {
		JPanel colorPane = new JPanel(new GridLayout(2, 2));
		clasico = new JRadioButton("Classic Chess", true);
		invertido = new JRadioButton("Inverted Chess");
		damas = new JRadioButton("Checkers");

		ButtonGroup group = new ButtonGroup();
		group.add(clasico);
		group.add(invertido);
		group.add(damas);

		colorPane.add(clasico);
		colorPane.add(invertido);
		colorPane.add(damas);

		colorPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
				BorderFactory.createTitledBorder("Game mode")));
		return colorPane;
	}

	public JPanel createButtonPane() {
		JPanel buttonPane = new JPanel(new BorderLayout());
		JPanel pane = new JPanel(new GridLayout(1, 2, 5, 0));
		pane.add(ok = new JButton("OK"));
		pane.add(cancel = new JButton("Cancel"));
		buttonPane.add(pane, BorderLayout.EAST);
		buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		return buttonPane;
	}

	public void actionPerformed(ActionEvent e) {
		Conserje.getSingleton().reset();
		if (e.getSource() == ok) {
			if (clasico.isSelected())
				chessmate.newGame(new JuegoEstandar());
			if (invertido.isSelected())
				chessmate.newGame(new JuegoEstandar()); //cambiar por la clase correcta
			if (damas.isSelected())
				chessmate.newGame(new JuegoEstandar()); //cambiar por la clase correcta

		}
		setVisible(false);
	}
}
