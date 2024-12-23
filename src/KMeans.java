import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KMeans {
    private int k;
    private int maxIterations;

    public KMeans(int k, int maxIterations) {
        this.k = k;
        this.maxIterations = maxIterations;
    }

    public List<double[]> cluster(List<double[]> data) {
        List<double[]> centroids = initializeCentroids(data);
        int[] assignments = new int[data.size()];

        for (int iter = 0; iter < maxIterations; iter++) {
            // Assign blocks to the nearest centroid
            for (int i = 0; i < data.size(); i++) {
                assignments[i] = findNearestCentroid(data.get(i), centroids);
            }

            // Recompute centroids
            centroids = recomputeCentroids(data, assignments);
        }
        return centroids;
    }

    public int[] encode(List<double[]> blocks, List<double[]> codebook) {
        int[] encoded = new int[blocks.size()];
        for (int i = 0; i < blocks.size(); i++) {
            encoded[i] = findNearestCentroid(blocks.get(i), codebook);
        }
        return encoded;
    }

    private List<double[]> initializeCentroids(List<double[]> data) {
        List<double[]> centroids = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < k; i++) {
            centroids.add(data.get(rand.nextInt(data.size())));
        }
        return centroids;
    }

    private int findNearestCentroid(double[] block, List<double[]> centroids) {
        int nearestIndex = -1;
        double minDistance = Double.MAX_VALUE;

        for (int i = 0; i < centroids.size(); i++) {
            double distance = 0.0;
            for (int j = 0; j < block.length; j++) {
                distance += Math.pow(block[j] - centroids.get(i)[j], 2);
            }

            if (distance < minDistance) {
                minDistance = distance;
                nearestIndex = i;
            }
        }
        return nearestIndex;
    }

    private List<double[]> recomputeCentroids(List<double[]> data, int[] assignments) {
        List<double[]> centroids = new ArrayList<>();
        int[] clusterSizes = new int[k];

        for (int i = 0; i < k; i++) {
            centroids.add(new double[data.get(0).length]);
        }

        for (int i = 0; i < data.size(); i++) {
            int clusterId = assignments[i];
            double[] centroid = centroids.get(clusterId);

            for (int j = 0; j < centroid.length; j++) {
                centroid[j] += data.get(i)[j];
            }
            clusterSizes[clusterId]++;
        }

        for (int i = 0; i < k; i++) {
            for (int j = 0; j < centroids.get(i).length; j++) {
                centroids.get(i)[j] /= clusterSizes[i];
            }
        }
        return centroids;
    }
}
