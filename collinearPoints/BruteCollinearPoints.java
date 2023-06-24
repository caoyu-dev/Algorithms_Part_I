package collinearPoints;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private List<LineSegment> segments = new ArrayList<>();

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        checkPoints(points);
        Arrays.sort(points);
        calculateSegments(points);
    }

    private void calculateSegments(Point[] points) {
        for (int p = 0; p < points.length - 3; p++) {
            for (int q = p + 1; q < points.length - 2; q++) {
                for (int r = p + 2; r < points.length - 1; r++) {
                    for (int s = p + 3; s < points.length; s++) {
                        double pq = points[p].slopeTo(points[q]);
                        double pr = points[p].slopeTo(points[r]);
                        double ps = points[p].slopeTo(points[s]);
                        if (pq == pr && pq == ps) {
                            segments.add(new LineSegment(points[p], points[s]));
                        }
                    }
                }
            }
        }
    }

    private static void checkPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("argument is null");
        }
        for (Point point : points) {
            if (point == null) throw new IllegalArgumentException("point is null");
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }
}
