import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ImageSaver {
    public static void saveImage(int[][] pixels, String outputFilePath) throws Exception {
        int width = pixels[0].length;
        int height = pixels.length;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                img.getRaster().setSample(x, y, 0, pixels[y][x]);
            }
        }
        ImageIO.write(img, "png", new File(outputFilePath));
    }
}
