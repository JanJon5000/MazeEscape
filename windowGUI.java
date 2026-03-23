import java.awt.event.*;
import java.util.Set;
import java.util.HashSet;
import javax.swing.*;

public class windowGUI extends JFrame implements KeyListener{
    private short width;
    private short height;
    private playerClass player;
    private labirynth lab;
    private ImageIcon icon;
    private double frameTime = 60 / 1000.0;
    private JLabel label;
    private Set<String> currentlyPressed;

    public windowGUI(String title, short w, short h){
        super(title);
        this.addKeyListener(this);
        this.width = w;
        this.height = h;
        this.currentlyPressed = new HashSet<String>();
        this.lab = new labirynth(constClass.LAB_SIZE, constClass.LAB_SIZE);
        this.player = new playerClass(lab);
        this.icon = new ImageIcon(this.player.rayCast(this.width, this.height));
        this.label = new JLabel(this.icon);
        setSize(this.width, this.height);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
        this.add(this.label);  
        setVisible(true);
    }
    public short[] getDimentions(){
        short[] ans = {this.width, this.height};
        return ans;
    }
    public void keyPressed(KeyEvent e){
        this.currentlyPressed.add(Character.toString(e.getKeyChar()));     
    }

    public void keyReleased(KeyEvent e){
        this.currentlyPressed.remove(Character.toString(e.getKeyChar()));
    }
    public void keyTyped(KeyEvent e){}

    public static void main (String[] args){
        windowGUI win = new windowGUI("TheMaze", (short)constClass.SCRN_WIDTH, (short)constClass.SCRN_HEIGHT);  
        System.out.print("Statrowe wspolrzedne: ");
        System.out.println(win.player.getPosX());
        System.out.println(win.player.getPosY());    
        //game loop
        while(true){
            if(win.currentlyPressed.contains("d") || win.currentlyPressed.contains("a")){
                double rotSpeed = win.frameTime * constClass.ROT_SPEED;
                rotSpeed *= win.currentlyPressed.contains("d") ? 
                (win.currentlyPressed.contains("a") ? 0 : -1) : 
                (win.currentlyPressed.contains("a") ? 1 : 0);
                if(rotSpeed != 0){
                    double newDirX =   win.player.getDirX()   * Math.cos(rotSpeed) - win.player.getDirY()   * Math.sin(rotSpeed);
                    double newDirY =   win.player.getDirX()   * Math.sin(rotSpeed) + win.player.getDirY()   * Math.cos(rotSpeed);
                    double newPlaneX = win.player.getPlaneX() * Math.cos(rotSpeed) - win.player.getPlaneY() * Math.sin(rotSpeed);
                    double newPlaneY = win.player.getPlaneX() * Math.sin(rotSpeed) + win.player.getPlaneY() * Math.cos(rotSpeed);
                    //rescaling vectors to their original lenghts
                    double magDir = Math.sqrt(newDirX * newDirX + newDirY * newDirY);
                    newDirX /= magDir;
                    newDirY /= magDir;
                    magDir = Math.sqrt(newPlaneX * newPlaneX + newPlaneY * newPlaneY);
                    newPlaneX /= magDir;
                    newPlaneY /= magDir;
                    //new coords
                    win.player.setNewDirection(newDirX, newDirY, newPlaneX, newPlaneY);
                    win.icon.setImage(win.player.rayCast(win.width, win.height));
                    win.label.repaint();
                }
            }

            if(win.currentlyPressed.contains("w") || win.currentlyPressed.contains("s")){
                double moveSpeed = win.frameTime * constClass.MOV_SPEED;
                moveSpeed *= win.currentlyPressed.contains("w") ? 
                (win.currentlyPressed.contains("s") ? 0 : 1) : 
                (win.currentlyPressed.contains("s") ? -1 : 0);

                double moveX = moveSpeed * win.player.getDirX();
                double moveY = moveSpeed * win.player.getDirY();
            
                double currentX = win.player.getPosX();
                double currentY = win.player.getPosY();
                double newX = currentX + moveX;
                double newY = currentY + moveY;
                double finalX = currentX;
                double finalY = currentY;

                if (win.lab.arrayAccess()[(int)newX][(int)currentY] == constClass.PATH) {
                    finalX = newX; 
                }

                if (win.lab.arrayAccess()[(int)finalX][(int)newY] == constClass.PATH) {
                    finalY = newY; 
                }

                win.player.setNewPos(finalX, finalY);
                win.icon.setImage(win.player.rayCast(win.width, win.height));
                win.label.repaint();
            }
        }
    }
}
