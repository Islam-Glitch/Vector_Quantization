import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        String inputPath = "src/resources/input.png";
        String outputPath = "compressed_output.png";

        int blockSize = 4;
        int k = 16; // Number of clusters (codebook size)
        int maxIterations = 100;

        // Step 1: Load grayscale image
        GrayImageInput grayImage = new GrayImageInput(inputPath);
        int[][] pixels = grayImage.getPixels();

        // Step 2: Divide into blocks
        BlockProcessor blockProcessor = new BlockProcessor(blockSize);
        List<double[]> blocks = blockProcessor.divideIntoBlocks(pixels);

        // Step 3: Perform K-Means clustering
        KMeans kMeans = new KMeans(k, maxIterations);
        List<double[]> codebook = kMeans.cluster(blocks);
        int[] encoded = kMeans.encode(blocks, codebook);

        // Step 4: Decode the image
        int[][] reconstructed = blockProcessor.decode(encoded, codebook, grayImage.getWidth(), grayImage.getHeight());

        // Step 5: Save the reconstructed image
        ImageSaver.saveImage(reconstructed, outputPath);

        System.out.println("Compression complete. Output saved to: " + outputPath);
    }
}
