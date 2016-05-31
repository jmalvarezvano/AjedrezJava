/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package presentacion;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import logica.piezas.Pieza;
/**
 *
 * @author Melvic
 */
public class PromotionPane extends JDialog implements ActionListener{
    JPanel main_pane;
    MainPane chessmate;
    Resource resource;
    int promotion_piece;
    
    public PromotionPane(MainPane chessmate){
        setTitle("New Piece");
        this.chessmate = chessmate;
        main_pane = new JPanel(new GridLayout(1,4,10,0));
        resource = new Resource();

        int[] cmdActions = {
            Pieza.QUEEN,Pieza.ROOK,Pieza.BISHOP,Pieza.KNIGHT
        };        
        for(int i=0; i<cmdActions.length; i++){
            JButton button = new JButton();
            button.addActionListener(this);
            button.setActionCommand(cmdActions[i]+"");
            main_pane.add(button);
        }
        setContentPane(main_pane);        
        setResizable(false);
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                promotion_piece = Pieza.QUEEN;
            }
        });
    }
    public void setIcons(boolean white){
        Component[] components = main_pane.getComponents();
        String[] resourceStrings = {"q","r","b","n"};
        for(int i=0; i<components.length; i++){
            JButton button = (JButton)components[i];
            button.setIcon(new ImageIcon(
                    resource.getResource((white?"w":"b")+resourceStrings[i])));
        }
        pack();
        setLocationRelativeTo(null);
    }
    public void actionPerformed(ActionEvent e){
        promotion_piece = Integer.parseInt(e.getActionCommand());
        setVisible(false);
        dispose();
    }
    
    public int showDialog() {
    	setVisible(true);
    	return promotion_piece;
    }
   
}
