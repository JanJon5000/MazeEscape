import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class wallTexture {
    public static BufferedImage createTexture(int num){
        int width = 64;
        int height = 64;
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = bufferedImage.createGraphics();
            
            Random r = new Random();
            g2d.setColor(new Color(r.nextFloat(), r.nextFloat(), r.nextFloat()));
            g2d.fillRect(0, 0, width, height);

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 20));

            g2d.drawString(Integer.toString(num), 25, 35);
            g2d.dispose();

            File file = new File("wall.png");
            try{
                ImageIO.write(bufferedImage, "png", file);
            }catch(IOException e){
                e.printStackTrace();
            }
            return bufferedImage;
    }
}
