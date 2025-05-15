import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
public class imageCreator {
    public imageCreator(byte[][] inputMaze){
        byte cellSize = 10;
        var width = (short) (inputMaze[0].length * cellSize);
        var height = (short) (inputMaze.length * cellSize);

        BufferedImage outputMaze = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = outputMaze.createGraphics();
        for (int y = 0; y < inputMaze.length; y++) {
            for (int x = 0; x < inputMaze[y].length; x++) {
                if (inputMaze[y][x] == constClass.PATH) {
                    g.setColor(Color.WHITE); 
                } else {
                    g.setColor(Color.BLACK); 
                }
                g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
            }
        }

        g.dispose();
        try{
            ImageIO.write(outputMaze, "png", new File("maze.png"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
