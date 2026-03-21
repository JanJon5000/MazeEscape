
import java.awt.event.*;
import java.io.Serial;

import javax.swing.*;

public class windowGUI extends JFrame implements KeyListener{
    private short width;
    private short height;
    private playerClass player;
    private labirynth lab;
    private ImageIcon icon;
    private double frameTime = 60 / 1000.0;
    private JLabel label;

    public windowGUI(String title, short w, short h){
        super(title);
        this.addKeyListener(this);
        this.width = w;
        this.height = h;
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
        if(e.getKeyChar() == 'a'){
            double rotSpeed = this.frameTime * 3.0;
            double newDirX =   this.player.getDirX()   * Math.cos(rotSpeed) - this.player.getDirY()   * Math.sin(rotSpeed);
            double newDirY =   this.player.getDirX()   * Math.sin(rotSpeed) + this.player.getDirY()   * Math.cos(rotSpeed);
            double newPlaneX = this.player.getPlaneX() * Math.cos(rotSpeed) - this.player.getPlaneY() * Math.sin(rotSpeed);
            double newPlaneY = this.player.getPlaneX() * Math.sin(rotSpeed) + this.player.getPlaneY() * Math.cos(rotSpeed);
            //rescaling vectors to their original lenghts
            double magDir = Math.sqrt(newDirX * newDirX + newDirY * newDirY);
            newDirX /= magDir;
            newDirY /= magDir;
            magDir = Math.sqrt(newPlaneX * newPlaneX + newPlaneY * newPlaneY);
            newPlaneX /= magDir;
            newPlaneY /= magDir;
            //new coords
            this.player.setNewDirection(newDirX, newDirY, newPlaneX, newPlaneY);
        }else if(e.getKeyChar() == 'd'){
            double rotSpeed = -this.frameTime * 3.0;
            double newDirX =   this.player.getDirX()   * Math.cos(rotSpeed) - this.player.getDirY()   * Math.sin(rotSpeed);
            double newDirY =   this.player.getDirX()   * Math.sin(rotSpeed) + this.player.getDirY()   * Math.cos(rotSpeed);
            double newPlaneX = this.player.getPlaneX() * Math.cos(rotSpeed) - this.player.getPlaneY() * Math.sin(rotSpeed);
            double newPlaneY = this.player.getPlaneX() * Math.sin(rotSpeed) + this.player.getPlaneY() * Math.cos(rotSpeed);
             //rescaling vectors to their original lenghts
            double magDir = Math.sqrt(newDirX * newDirX + newDirY * newDirY);
            newDirX /= magDir;
            newDirY /= magDir;
            magDir = Math.sqrt(newPlaneX * newPlaneX + newPlaneY * newPlaneY);
            newPlaneX /= magDir;
            newPlaneY /= magDir;
            //new coords
            this.player.setNewDirection(newDirX, newDirY, newPlaneX, newPlaneY);
        }else if(e.getKeyChar() == 'w'){
            double moveSpeed = this.frameTime * 0.5;

            double moveX = moveSpeed * this.player.getDirX();
            double moveY = moveSpeed * this.player.getDirY();
            
            // 1. Get the current position
            double currentX = this.player.getPosX();
            double currentY = this.player.getPosY();

            // 2. Calculate the intended destination based on input (moveX/moveY)
            double newX = currentX + moveX;
            double newY = currentY + moveY;

            // 3. Variables to hold our final approved position
            double finalX = currentX;
            double finalY = currentY;

            // 4. Check the X-axis independently
            // We check the grid cell at the NEW X, but the CURRENT Y.
            if (this.lab.arrayAccess()[(int)newX][(int)currentY] == constClass.PATH) {
                finalX = newX; // Move is safe, update finalX
            }

            // 5. Check the Y-axis independently
            // We check the grid cell at the FINAL X (which might have just been updated!), and the NEW Y.
            if (this.lab.arrayAccess()[(int)finalX][(int)newY] == constClass.PATH) {
                finalY = newY; // Move is safe, update finalY
            }

            // 6. Apply the final position
            this.player.setNewPos(finalX, finalY);
            //this.player.setNewPos(this.player.getPosX() + moveX, this.player.getPosY() + moveY);
        }
        
        if(e.getKeyChar() == 'd' || e.getKeyChar() == 'a' || e.getKeyChar() == 'w' || e.getKeyChar() == 's'){
            this.icon.setImage(this.player.rayCast(width, height));
            this.label.repaint();
            this.player.mapDebug();
            
        }
        
       
    }

    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}

    public static void main (String[] args){
        windowGUI win = new windowGUI("TheMaze", (short)constClass.SCRN_WIDTH, (short)constClass.SCRN_HEIGHT);  
        System.out.print("Statrowe wspolrzedne: ");
        System.out.println(win.player.getPosX());
        System.out.println(win.player.getPosY());    
        //game loop
        while(true){
            
        }
    }
}
