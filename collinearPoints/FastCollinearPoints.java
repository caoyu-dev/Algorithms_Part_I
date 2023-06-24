package collinearPoints;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private List<LineSegment> segments = new ArrayList<>();

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        checkPoints(points);
        Arrays.sort(points);

        if (hasDuplicate(points)) {
            throw new IllegalArgumentException("warning: duplicate points");
        }
        calculateSegments(points);
    }

    private boolean hasDuplicate(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                return true;
            }
        }
        return false;
    }

    private void calculateSegments(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            Point[] pointsCopy = Arrays.copyOf(points, points.length);
            Arrays.sort(pointsCopy, p.slopeOrder());

            for (int j = 0, first = 1, last = 2; last < pointsCopy.length; last++) {
                double currentSlope = p.slopeTo(pointsCopy[first]);
                double nextSlope = p.slopeTo(pointsCopy[last]);

                while (last < pointsCopy.length
                        && Double.compare(currentSlope, nextSlope) == 0) {
                    last++;

                    if (last < pointsCopy.length) {
                        nextSlope = p.slopeTo(pointsCopy[last]);
                    }
                }

                if (last - first >= 3 && p.compareTo(pointsCopy[first]) < 0) {
                    segments.add(new LineSegment(p, pointsCopy[last - 1]));
                }

                first = last;
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
