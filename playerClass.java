import java.awt.*;
import java.awt.image.*;
import java.util.Random;
import javax.swing.*;

public class playerClass extends JPanel{
    private double posX, posY;
    private double dirY = 0;
    private double dirX = -1;
    private byte[][] maze;
    private BufferedImage screen;
    private double planeX = 0.66;
    private double planeY = 0;
    public playerClass(labirynth map){
        this.maze = map.arrayAccess();
        Random r = new Random();
        int[] startIndexes = {0, 0};
        while (startIndexes[0]==0 && startIndexes[1]==0) {
            int x = r.nextInt(map.getHeight());
            int y = r.nextInt(map.getWidth());
            if(this.maze[x][y] == constClass.PATH){
                startIndexes[0] = x; startIndexes[1] = y;
            }
        }
        this.posX = startIndexes[0];
        this.posY = startIndexes[1];
        System.out.println(posX);
        System.out.println(posY);
    }
    //function creating an image that is later displayed on the screen in the main window
    public BufferedImage rayCast(int screenWidth, int screenHeight){
        screen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);
        //a loop creating a vertical line for every x coordinate in screen's width range
        for(short x=0;x<screenWidth;x++){
            //cameraX is a variable that contains current x coordinate of the camera corresponding to the current x in the loop
            //it has following characteristic points - right side of the screen gets 1, left gets -1 and center obvoisly gets 0
            //rayDirX and Y variables determine the ray direction (self explanatory)
            double cameraX = ((2 * x) / ((double)screenWidth)) - 1;
            double rayDirX = dirX + planeX * cameraX;
            double rayDirY = dirY + planeY * cameraX; 
            
            //mapX and Y represent the square which the player is currently in
            int mapX = (int) posX;
            int mapY = (int) posY;

            //sideDistX and Y represent distance needed for the player to travel in order to reach first side of the square 
            //respectively in X and Y plane
            double sideDistX;
            double sideDistY;
            //deltaDistX and Y are distances needed to travel from first side to another respectively in X and Y plane
            double deltaDistX = (rayDirX == 0) ? 1e30 : Math.abs(1 / rayDirX);
            double deltaDistY = (rayDirY == 0) ? 1e30 : Math.abs(1 / rayDirY);
            
            //variable that contains proper distance to the wall
            //"perpenducular distance"
            double perpWallDist;
            //stepX and Y are variables determining in which direction to step in x and y plane
            int stepX;
            int stepY;
            int hit = 0;//was the wall hit?
            int side = 0; //is x or y side hit?
            //preparation for DDA algorithm
            if(rayDirX < 0){
                stepX = -1;
                sideDistX = (posX - mapX) * deltaDistX;
            }else{
                stepX = 1;
                sideDistX = (mapX + 1.0 - posX) * deltaDistX;
            }
            if(rayDirY < 0){
                stepY = -1;
                sideDistY = (posY - mapY) * deltaDistY;
            }else{
                stepY = 1;
                sideDistY = (mapY + 1.0 - posY) * deltaDistY;
            }
            //DDA algorithm
            while(hit == 0){
                if(sideDistX < sideDistY){
                    sideDistX += deltaDistX;
                    mapX += stepX;
                    side = 0;
                }else{
                    sideDistY += deltaDistY;
                    mapY += stepY;
                    side = 1;
                }
                if(this.maze[mapX][mapY] > 0) hit = 1;
            }

            //calculation of perpenducular distance to prevent fisheye effect
            if(side == 0) perpWallDist = (sideDistX - deltaDistX);
            else perpWallDist = (sideDistY - deltaDistY);
            //calculation of the height of the line that needs to be drawn on the screen
            int lineHeight = (int)(screenHeight / perpWallDist);
            int drawStart = -lineHeight / 2 + screenHeight / 2;
            if(drawStart < 0) drawStart = 0;
            int drawEnd = lineHeight / 2 + screenHeight / 2;
            if(drawEnd >= screenHeight) drawEnd = screenHeight - 1;

            int c = Color.RED.getRGB();
            Graphics2D g2d = screen.createGraphics();
            g2d.setColor(new Color(c));
            g2d.drawLine(x, drawStart, x, drawEnd);
            
        }
        return screen;
    }
}
