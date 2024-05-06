import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class GenerujObraz {
    public static void GenerujObraz(int[][] Lab, int Columns, int Rows) {
        BufferedImage image = new BufferedImage(Columns, Rows, BufferedImage.TYPE_INT_RGB);
        for(int i = 0; i < Rows; i++){
            for(int j = 0; j < Columns; j++){
                if(Lab[j][i] == 0){
                    image.setRGB(j, i, Color.WHITE.getRGB());
                }
                else if(Lab[j][i] == 1){
                    image.setRGB(j, i, Color.BLACK.getRGB());
                }
                else if(Lab[j][i] == 2){
                    image.setRGB(j, i, Color.GREEN.getRGB());
                }
                else if(Lab[j][i] == 3){
                    image.setRGB(j, i, Color.RED.getRGB());
                }
               else if(Lab[j][i] == 4){
                    image.setRGB(j, i, Color.BLUE.getRGB());
                }
            }
        }
        try {
            ImageIO.write(image, "png", new File("Labirynt.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
