package Graficos;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PruebaGraficos extends JPanel {
	    
    public void paint(Graphics g){
    	
    	Image im = null;
    	try {
    		File file = new File("src/Graficos/peon_318-11097.png");
			im = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    	//Crea un rectángulo de 400x400 
        g.fillRect(100, 100, 400, 400);
        
        //Estos dos bucles crean las casillas blancas
        for(int i = 100; i <= 400; i+=100){
            for(int j = 100; j <= 400; j+=100){
                g.clearRect(i, j, 50, 50);
            }
        }
        
        for(int i = 150; i <= 450; i+=100){
            for(int j = 150; j <= 450; j+=100){
                g.clearRect(i, j, 50, 50);
            }
        }
        
        //Crea los peones
        for(int i = 100; i <= 450; i += 50){
        	g.drawImage(im, i, 150, 50, 50, null);
        	g.drawImage(im, i, 400, 50, 50, null);
        }
    }
    
    public static void main(String[] args){
    	
    	//Inicializa la ventana del juego
        JFrame frame = new JFrame("Ajedrez");
        frame.setSize(600,600);
        frame.getContentPane().add(new PruebaGraficos());
        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.LIGHT_GRAY);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }  
}
