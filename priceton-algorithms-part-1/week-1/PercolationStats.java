import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import java.lang.IllegalArgumentException;

public class PercolationStats {
    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        double[] percolationThreshold = new double[trials];
        int numberOfCells = (n * n);

        for (int i = 0; i < trials; i++) {
            Percolation grid = new Percolation(n);
            while (!grid.percolates()) {
                int randomRow = StdRandom.uniform(1, n + 1);
                int randomColumn = StdRandom.uniform(1, n + 1);

                if (!grid.isOpen(randomRow, randomColumn)) {
                    grid.open(randomRow, randomColumn);
                }
            }
            double threshold = (double) grid.numberOfOpenSites() / numberOfCells;
            percolationThreshold[i] = threshold;
        }

        // NOTE: Derive information from simulation
        this.mean = StdStats.mean(percolationThreshold);
        this.stddev = StdStats.stddev(percolationThreshold);

        this.confidenceLo = this.mean - (1.96 * this.stddev / Math.sqrt(trials));
        this.confidenceHi = this.mean + (1.96 * this.stddev / Math.sqrt(trials));
    }

    // // sample mean of percolation threshold
    public double mean() {
        return this.mean;
    }

    // // sample standard deviation of percolation threshold
    public double stddev() {
        return this.stddev;
    }

    // // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.confidenceLo;
    }

    // // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.confidenceHi;
    }

    // test client (described below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);
        System.out.format("mean\t\t\t= %f\n", stats.mean());
        System.out.format("stddev\t\t\t= %f\n", stats.stddev());
        System.out.format("95%% confidence interval\t= [%f, %f]\n", stats.confidenceLo(), stats.confidenceHi());
    }
}