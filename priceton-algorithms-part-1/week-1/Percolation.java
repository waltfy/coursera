import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.lang.IllegalArgumentException;

public class Percolation {
    private boolean[][] openedSites;
    private int numberOfOpenSites = 0;
    private int virtualNodeTop = 0;
    private int virtualNodeBottom;
    private int base;
    private int size;

    private WeightedQuickUnionUF uf;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException(String.format("Percolation: n must be larger than 0, received: %d", n));
        }

        this.base = n;
        // NOTE: We add two, because we need to introduce "virtual nodes" at the top and
        // at the bottom.
        this.size = (n * n) + 2;
        this.virtualNodeBottom = this.size - 1;

        this.uf = new WeightedQuickUnionUF(this.size);
        this.openedSites = new boolean[n][n];
    }

    private int rowColToIndex(int row, int col) {
        int index = this.base * (row - 1) + col;
        // NOTE: Stay within the boundaries which are:
        //  - Virtual top: 0
        //  - Virtual bottom: (n * 2) + 1
        if (index < 1 || index > this.base * this.base) {
            throw new IllegalArgumentException();
        } else {
            return index;
        }
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        this.openedSites[row - 1][col - 1] = true;

        int siteToOpenIndex = rowColToIndex(row, col);

        if (row == 1) {
            this.uf.union(siteToOpenIndex, this.virtualNodeTop);
        }

        if (row == base) {
            this.uf.union(siteToOpenIndex, this.virtualNodeBottom);
        }

        if (col > 1 && isOpen(row, col - 1)) {
            this.uf.union(siteToOpenIndex, rowColToIndex(row, col - 1));
        }

        if (col < base && isOpen(row, col + 1)) {
            this.uf.union(siteToOpenIndex, rowColToIndex(row, col + 1));
        }

        if (row > 1 && isOpen(row - 1, col)) {
            this.uf.union(siteToOpenIndex, rowColToIndex(row - 1, col));
        }

        if (row < base && isOpen(row + 1, col)) {
            this.uf.union(siteToOpenIndex, rowColToIndex(row + 1, col));
        }

        this.numberOfOpenSites += 1;
    }

    // is site (row, col) open?
    // is it connected to the 4 adjacent nodes? Top, Right, Bottom, Left?
    public boolean isOpen(int row, int col) {
        return this.openedSites[row - 1][col - 1];
    }

    // // is site (row, col) full?
    // NOTE: A full site is an open site that can be connected to an open site in the top
    // row via a chain of neighboring (left, right, up, down) open sites.
    public boolean isFull(int row, int col) {
        return this.uf.connected(rowColToIndex(row, col), this.virtualNodeTop);
    }

    // // number of open sites
    public int numberOfOpenSites() {
        return this.numberOfOpenSites;
    }

    // // does the system percolate?
    // NOTE: We say the system percolates if there is a full site in the bottom row.
    // i.e. is the virtual bottom connected to the virtual top?
    public boolean percolates() {
        return this.uf.connected(this.virtualNodeTop, this.virtualNodeBottom);
    }

    public static void main(String[] args) {
        Percolation grid = new Percolation(3);
    }
}