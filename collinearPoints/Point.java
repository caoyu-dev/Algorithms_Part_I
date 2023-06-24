package collinearPoints;

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {
    private final int x;
    private final int y;

    // constructs the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // draws this point
    public void draw() {
        StdDraw.point(x, y);
    }

    // draws the line segment from this point to that point
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // string representation
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // compare two points by y-coordinates, breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (that == null) {
            throw new NullPointerException();
        }
        // less
        if (this.y < that.y || (this.y == that.y && this.x < that.x)) {
            return -1;
        }
        // same
        if (this.y == that.y && this.x == that.x) {
            return 0;
        }
        // bigger
        return 1;
    }

    // the slope between this point and that point
    public double slopeTo(Point that) {
        if (this.x == that.x) {
            // same
            if (this.y == that.y) {
                return Double.NEGATIVE_INFINITY;
            }
            // vertical
            return Double.POSITIVE_INFINITY;
        }
        if (this.y == that.y) {
            // horizontal
            return 0.0;
        }
        return (double) (that.y - this.y) / (that.x - this.x);
    }

    // compare two points by slopes they make with this point
    public Comparator<Point> slopeOrder() {
        return new SlopeOrder();
    }

    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point q1, Point q2) {
            double slope1 = slopeTo(q1);
            double slope2 = slopeTo(q2);
            return Double.compare(slope1, slope2);
        }
    }
}
