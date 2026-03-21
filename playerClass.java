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
    private double planeX = 0;
    private double planeY = 0.66;
    private Graphics2D g2d;
    private int mazeWidth;
    private int mazeHeight;

    public playerClass(labirynth map){
        this.maze = map.arrayAccess();
        this.mazeHeight = map.getHeight();
        this.mazeWidth = map.getWidth();
        Random r = new Random();
        int[] startIndexes = {0, 0};
        boolean found = false;
        while (!found){
            int y = r.nextInt(map.getHeight());
            int x = r.nextInt(map.getWidth());
            if(this.maze[x][y] == constClass.PATH){
                startIndexes[0] = x; startIndexes[1] = y;
                found = true;
            }
        }
        this.posX = startIndexes[0] + 0.1;
        this.posY = startIndexes[1] + 0.1;
    }
    //function creating an image that is later displayed on the screen in the main window
    public BufferedImage rayCast(int screenWidth, int screenHeight){
        this.screen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);
        this.g2d = screen.createGraphics();
        //a loop creating a vertical line for every x coordinate in screen's width range
        for(int x=0;x<screenWidth;x+=6){
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
                try{
                    if(!(mapX > this.mazeWidth || mapY > this.mazeHeight || mapX < 0 || mapY < 0)){
                         if(this.maze[mapX][mapY] == 0) hit = 1;
                    }else
                        hit = 1;
                }catch(ArrayIndexOutOfBoundsException e){
                    e.printStackTrace();
                }
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
            
            //color and brightness selection 
            double brightness = 1.0 / (1 + (perpWallDist * 0.5));
            Color c;
            if(side == 1){
                c = new Color((int)(255*brightness), (int)(255*brightness), (int)(255*brightness));
            }else{
                c = new Color((int)(255*brightness), 0, 0);
            }
            g2d.setColor(c);
            g2d.drawLine(x, drawStart, x, drawEnd);
            
        }
        return screen;
    }
    double getDirX(){ return this.dirX; }
    double getDirY(){ return this.dirY; }
    double getPlaneX(){ return this.planeX; }
    double getPlaneY(){ return this.planeY; }
    double getPosX(){ return this.posX; }
    double getPosY(){ return this.posY; }


    void setDirX(double x){ this.dirX = x; }
    void setDirY(double x){ this.dirY = x; }
    void setPlaneX(double x){ this.planeX = x; }
    void setPlaneY(double x){ this.planeY = x; }
    void setNewDirection(double x, double y, double px, double py){
        this.dirX = x;
        this.dirY = y;
        this.planeX = px;
        this.planeY = py;
    }
    void setNewPos(double x, double y){
        this.posX = x;
        this.posY = y;
    }

    void mapDebug(){
        System.out.println("");
        for(int i=0;i<=this.mazeWidth;i++){
            for(int j=0;j<=this.mazeHeight;j++){
                if(i != (int)this.posX || j != (int)this.posY){
                    if(this.maze[i][j] == constClass.PATH){
                        System.out.print(" ");
                    }else{
                        System.out.print("█");
                    }
                }else
                    System.out.print("P");
            }
            System.out.println("");
        }
        System.out.print(this.posX);
            System.out.print(" ");
            System.out.println(this.posY);
    }
}       
