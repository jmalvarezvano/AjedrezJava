/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package presentacion;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author melvicYbanez
 */
public class AboutPane extends JPanel{
    public AboutPane(){
        setLayout(new BorderLayout());
        JPanel northPane = new NorthPane();       
        JPanel centerPane = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();        
        c.insets = new Insets(4,4,4,4);
        c.fill = GridBagConstraints.HORIZONTAL;        

        String[][] values = new String[][]{
            {"Project","Proyecto DDS "},
            {"Category","Game"},
            {"Authors","Jos� Miguel �lvarez Va��, Daniel Ionel Bizau, �lvaro Morro Ib��ez"},
            {"Date created","May-June 2016"},
            {"Other info","Base interface layout reused from MyChessmate Project by Melvic C. Ybañez"}
        };
        for(int i=0; i<values.length; i++){
            JLabel header = new JLabel(values[i][0]+": ");
            header.setFont(new Font(header.getFont().getName(),Font.BOLD,13));
            JLabel data = new JLabel(values[i][1]);
            c.gridx = 0;
            c.gridy = i;
            centerPane.add(header,c);
            c.gridx = 1;
            centerPane.add(data,c);
        }
        centerPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        add(northPane,BorderLayout.NORTH);
        add(centerPane,BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    }    
    public static void createAndShowUI(){                   
        JFrame f = new JFrame("AboutBox");
        AboutPane ap = new AboutPane();
        f.getContentPane().add(ap);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
    class NorthPane extends JPanel{
        NorthPane(){          
            JLabel label = new JLabel("About this project",JLabel.LEFT);
            label.setFont(new Font(label.getFont().getName(),Font.BOLD,15));
            label.setForeground(Color.decode("#9900AF"));
            add(label);
        }
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            int width = this.getWidth()-5;
            int height = this.getHeight() - 1;
            g.setColor(Color.decode("#9900FF"));
            g.drawLine(0, height, width, height);
        }
    }
}
