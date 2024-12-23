import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class GrayImageInput {
    private int[][] pixels;

    public GrayImageInput(String filePath) throws Exception {
        loadGrayscaleImage(filePath);
    }

    private void loadGrayscaleImage(String filePath) throws Exception {
        BufferedImage img = ImageIO.read(new File(filePath));
        if (img.getType() != BufferedImage.TYPE_BYTE_GRAY) {
            throw new IllegalArgumentException("Image is not grayscale!");
        }

        int width = img.getWidth();
        int height = img.getHeight();
        pixels = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[y][x] = img.getRaster().getSample(x, y, 0); // Get grayscale intensity
            }
        }
    }

    public int[][] getPixels() {
        return pixels;
    }

    public int getWidth() {
        return pixels[0].length;
    }

    public int getHeight() {
        return pixels.length;
    }
}
