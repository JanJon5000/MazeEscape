import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;

public class playerClass extends JPanel{
    private int[] exitCords = new int[2];
    private double posX, posY;
    private double dirY = 0;
    private double dirX = -1;
    private byte[][] maze;
    private BufferedImage screen;
    private double planeX = 0;
    private double planeY = 0.66;
    private int mazeWidth;
    private int mazeHeight;
    //wall texture params
    private BufferedImage wall;
    private int wallTextureW;
    private int wallTextureH;
    private int[] wallRGB;
    //exit texture
    private BufferedImage exit;
    private int exitTextureH;
    private int exitTextureW;
    private int[] exitRGB;

    private int[] drawBuffer;
    private int screenWidth;
    private int screenHeight;

    public playerClass(labirynth map, int scrnW, int scrnH){
        this.oneUpStats(map, scrnW, scrnH);
    }

    //function creating an image that is later displayed on the screen in the main window
    public BufferedImage rayCast(int screenWidth, int screenHeight){
        Arrays.fill(this.drawBuffer, 0);
        //a loop creating a vertical line for every x coordinate in screen's width range
        for(int x=0;x<screenWidth;x++){
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
                if (mapX >= 0 && mapX < mazeWidth && mapY >= 0 && mapY < mazeHeight) {
                    if (this.maze[mapX][mapY] != constClass.PATH) {
                        hit = 1;
                    }
                } else {
                    hit = 1;
                }
               
            }

            //calculation of perpenducular distance to prevent fisheye effect
            if(side == 0)
                perpWallDist = (sideDistX - deltaDistX);
            else 
                perpWallDist = (sideDistY - deltaDistY);

            //calculation of the height of the line that needs to be drawn on the screen
            int lineHeight = (int)(screenHeight / perpWallDist);
            int drawStart = -lineHeight / 2 + screenHeight / 2;
            if(drawStart < 0) drawStart = 0;
            int drawEnd = lineHeight / 2 + screenHeight / 2;
            if(drawEnd >= screenHeight) drawEnd = screenHeight - 1;

            //texture pixel to draw calculations
            double wallX;
            if(side == 0)
                wallX = posY + perpWallDist * rayDirY;
            else
                wallX = posX + perpWallDist * rayDirX;
            wallX -= Math.floor(wallX);

            int texX = (int)(wallX * (double)(this.wallTextureW));
            if(side == 0 && rayDirX > 0)
                texX = this.wallTextureW - texX - 1;
            if(side == 1 && rayDirY < 0)
                texX = this.wallTextureW - texX - 1;

            double step = 1.0 * this.wallTextureH / lineHeight;
            double texPos = (drawStart - screenHeight / 2 + lineHeight / 2) * step;
            //walls
            for (int y = drawStart; y < drawEnd; y++) {
                int texY = (int)texPos & (this.wallTextureH - 1);
                texPos += step;
                int c = 0;
                if(this.maze[mapX][mapY] == constClass.WALL){
                    c = this.wallRGB[texY * this.wallTextureW + texX];
                }else if(this.maze[mapX][mapY] == constClass.EXIT){
                    c = this.exitRGB[texY * this.wallTextureW + texX];
                }
                //make color darker for y-sides: R, G and B byte each divided through two with a "shift" and an "and"
                if(side == 1)
                    c = (c >> 1) & 8355711;
                if((y * screenWidth + x) < screenHeight * screenWidth)
                    drawBuffer[y * screenWidth + x] = c;
            } 
        }

        int[] pixelData = ((DataBufferInt) this.screen.getRaster().getDataBuffer()).getData();
        System.arraycopy(drawBuffer, 0, pixelData, 0, drawBuffer.length);
        return this.screen;
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

    // void mapDebug(){
    //     System.out.println("");
    //     for(int i=0;i<=this.mazeWidth;i++){
    //         for(int j=0;j<=this.mazeHeight;j++){
    //             if(i != (int)this.posX || j != (int)this.posY){
    //                 if(this.maze[i][j] == constClass.PATH){
    //                     System.out.print(" ");
    //                 }else{
    //                     System.out.print("█");
    //                 }
    //             }else
    //                 System.out.print("P");
    //         }
    //         System.out.println("");
    //     }
    //     System.out.print(this.posX);
    //         System.out.print(" ");
    //         System.out.println(this.posY);
    // }

    public void oneUpStats(labirynth map, int scrnW, int scrnH){
        double magDir = Math.sqrt(dirX * dirX + dirY * dirY);
        dirX /= magDir;
        dirY /= magDir;
        magDir = Math.sqrt(planeX * planeX + planeY * planeY);
        planeX /= magDir;
        planeY /= magDir;

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
        found = false;
        while (!found){
            int y = r.nextInt(map.getHeight());
            int x = r.nextInt(map.getWidth());
            if(this.maze[x][y] == constClass.WALL){
                if(x + 1 < this.mazeHeight){
                    if(this.maze[x+1][y] == constClass.PATH){        
                        this.maze[x][y] = constClass.EXIT;
                        found = true;
                    }
                }
                if(x - 1 > 0){
                    if(this.maze[x-1][y] == constClass.PATH){        
                        this.maze[x][y] = constClass.EXIT;
                        found = true;
                    }
                }
                if(y + 1 < this.mazeWidth){
                    if(this.maze[x][y + 1] == constClass.PATH){        
                        this.maze[x][y] = constClass.EXIT;
                        found = true;
                    }
                }
                if(y - 1 > 0){
                    if(this.maze[x][y-1] == constClass.PATH){        
                        this.maze[x][y] = constClass.EXIT;
                        found = true;
                    }
                }
            }
            // System.out.println(x);
            // System.out.println(y);
        }
        this.posX = startIndexes[0] + 0.1;
        this.posY = startIndexes[1] + 0.1;
        this.wall = wallTexture.createTexture(constClass.LAB_SIZE);
        this.screenHeight = scrnH;
        this.screenWidth = scrnW;
        this.screen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);
        try{
            this.wall = ImageIO.read(new File("wall.png"));
            this.wallTextureH = this.wall.getHeight();
            this.wallTextureW = this.wall.getWidth();
            this.wallRGB = this.wall.getRGB(0, 0, this.wallTextureW, this.wallTextureH, null, 0, this.wallTextureW);

            this.exit = ImageIO.read(new File("exit.png"));
            this.exitTextureH = this.exit.getHeight();
            this.exitTextureW = this.exit.getWidth();
            this.exitRGB = this.exit.getRGB(0, 0, this.exitTextureW, this.exitTextureH, null, 0, this.exitTextureW);
        }catch(IOException e){
            e.printStackTrace();
        }
        this.drawBuffer = new int[screenHeight * screenWidth];
    }
}       
