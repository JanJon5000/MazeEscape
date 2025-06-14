import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class mainFile {
    public static void main (String[] args){
        labirynth lab = new labirynth((short)10, (short)10);
        imageCreator img = new imageCreator(lab.arrayAccess());
        windowGUI win = new windowGUI("TheMaze", (short)500, (short)300);
        playerClass player = new playerClass(lab);
        BufferedImage b = player.rayCast(win.getDimentions()[0], win.getDimentions()[1]);
        ImageIcon icon = new ImageIcon(b);
        JLabel label = new JLabel(icon);
        double time = 0; //time of current frame
        double oldTime = 0; //time of previous frame
        //game loop
        while(true){
            b = player.rayCast(win.getDimentions()[0], win.getDimentions()[1]);
            icon = new ImageIcon(b);
            label = new JLabel(icon); 
            win.getContentPane().removeAll();
            win.getContentPane().add(label);
            win.revalidate();
            win.repaint();
        }
    }
}
