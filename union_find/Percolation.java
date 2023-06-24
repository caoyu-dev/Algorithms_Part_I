package union_find;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] grid; // open
    private final WeightedQuickUnionUF uf;
    private final int dimension;
    private final int top = 0;
    private final int bottom;
    private int openSites = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        dimension = n;
        bottom = dimension * dimension + 1;
        uf = new WeightedQuickUnionUF(bottom + 1);
        grid = new boolean[bottom];
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);

        if (!isOpen(row, col)) {
            grid[getIndex(row, col) - 1] = true;
            openSites = openSites + 1;

            if (row == 1) {
                uf.union(getIndex(row, col), top);
            }
            if (row == dimension) {
                uf.union(getIndex(row, col), bottom);
            }
            connectNeighbor(row, col);
        }
    }

    private void connectNeighbor(int row, int col) {
        if (row > 1 && isOpen(row - 1, col)) {
            uf.union(getIndex(row, col), getIndex(row - 1, col));
        }
        if (row < dimension && isOpen(row + 1, col)) {
            uf.union(getIndex(row, col), getIndex(row + 1, col));
        }
        if (col > 1 && isOpen(row, col - 1)) {
            uf.union(getIndex(row, col), getIndex(row, col - 1));
        }
        if (col < dimension && isOpen(row, col + 1)) {
            uf.union(getIndex(row, col), getIndex(row, col + 1));
        }
    }

    private void validate(int row, int col) {
        if (row <= 0 || row > dimension || col <= 0 || col > dimension) {
            throw new IllegalArgumentException();
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[getIndex(row, col) - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        return uf.find(top) == uf.find(getIndex(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(top) == uf.find(bottom);
    }

    private int getIndex(int row, int col) {
        return dimension * (row - 1) + col;
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation p = new Percolation(5);

        StdOut.println(p.numberOfOpenSites()); // 0

        p.open(1, 1);
        StdOut.println(p.numberOfOpenSites()); // 1
        StdOut.println(p.isOpen(1, 1)); // true
        StdOut.println(p.isFull(1, 1)); // true
        StdOut.println(p.percolates()); // false

        p.open(2, 1);
        StdOut.println(p.isOpen(2, 1)); // true
        StdOut.println(p.isFull(2, 1)); // true
        StdOut.println(p.percolates()); // false

        p.open(3, 1);
        p.open(4, 1);
        p.open(5, 1);

        StdOut.println(p.percolates()); // true
    }
}
