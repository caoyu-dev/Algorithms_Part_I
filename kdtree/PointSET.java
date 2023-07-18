package kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;
import java.util.List;

public class PointSET {

    private SET<Point2D> pointSet;

    public PointSET() {
        pointSet = new SET<>();
    }

    public boolean isEmpty() {
        return pointSet.isEmpty();
    }

    public int size() {
        return pointSet.size();
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        pointSet.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return pointSet.contains(p);
    }

    public void draw() {
        for (Point2D p : pointSet) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        List<Point2D> pointsInRectangle = new ArrayList<>();
        for (Point2D p : pointSet) {
            if (rect.contains(p)) {
                pointsInRectangle.add(p);
            }
        }
        return pointsInRectangle;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (isEmpty()) {
            return null;
        }

        Point2D nearestPoint = null;
        double nearestDistance = Double.POSITIVE_INFINITY;
        for (Point2D point : pointSet) {
            double currentDistance = p.distanceSquaredTo(point);
            if (currentDistance < nearestDistance) {
                nearestPoint = point;
                nearestDistance = currentDistance;
            }
        }
        return nearestPoint;
    }

    public static void main(String[] args) {
        PointSET pointSet = new PointSET();

        // 포인트를 삽입
        pointSet.insert(new Point2D(0.1, 0.1));
        pointSet.insert(new Point2D(0.2, 0.2));
        pointSet.insert(new Point2D(0.3, 0.3));
        pointSet.insert(new Point2D(0.4, 0.4));

        // 포인트가 삽입되었는지 확인
        System.out.println("1) kdtree.PointSET size: " + pointSet.size());
        System.out.println("1) Expected: 4\n");

        // 사각형 내 포인트 찾는 테스트
        RectHV rect = new RectHV(0.15, 0.15, 0.35, 0.35);
        Iterable<Point2D> pointsInRectangle = pointSet.range(rect);
        System.out.println("2) 사각형 내 포인트:");
        for (Point2D p : pointsInRectangle) {
            System.out.println(p);
        }
        System.out.println("2) Expected: \n(0.2, 0.2) \n(0.3, 0.3)\n");

        // 가장 가까운 포인트 찾는 테스트
        Point2D queryPoint = new Point2D(0.25, 0.25);
        Point2D nearestPoint = pointSet.nearest(queryPoint);
        System.out.println("3) 다음 포인트 " + queryPoint + " 에서 가장 가까운 포인트: " + nearestPoint);
        System.out.println("3) Expected: 다음 포인트 (0.25, 0.25) 에서 가장 가까운 포인트: (0.2, 0.2)");
    }
}
