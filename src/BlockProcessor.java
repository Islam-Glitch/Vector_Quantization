import java.util.ArrayList;
import java.util.List;

public class BlockProcessor {
    private int blockSize;

    public BlockProcessor(int blockSize) {
        this.blockSize = blockSize;
    }

    public List<double[]> divideIntoBlocks(int[][] pixels) {
        int height = pixels.length;
        int width = pixels[0].length;
        List<double[]> blocks = new ArrayList<>();

        for (int i = 0; i < height; i += blockSize) {
            for (int j = 0; j < width; j += blockSize) {
                double[] block = new double[blockSize * blockSize];
                int index = 0;

                for (int bi = 0; bi < blockSize; bi++) {
                    for (int bj = 0; bj < blockSize; bj++) {
                        int x = i + bi, y = j + bj;
                        block[index++] = (x < height && y < width) ? pixels[x][y] / 255.0 : 0.0;
                    }
                }
                blocks.add(block);
            }
        }
        return blocks;
    }

    public int[][] decode(int[] encoded, List<double[]> codebook, int width, int height) {
        int[][] reconstructed = new int[height][width];
        int blockIndex = 0;

        for (int i = 0; i < height; i += blockSize) {
            for (int j = 0; j < width; j += blockSize) {
                double[] block = codebook.get(encoded[blockIndex++]);
                int index = 0;

                for (int bi = 0; bi < blockSize; bi++) {
                    for (int bj = 0; bj < blockSize; bj++) {
                        int x = i + bi, y = j + bj;
                        if (x < height && y < width) {
                            reconstructed[x][y] = (int) (block[index++] * 255);
                        }
                    }
                }
            }
        }
        return reconstructed;
    }
}
