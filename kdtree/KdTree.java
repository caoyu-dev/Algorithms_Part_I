package kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.LinkedList;
import java.util.List;

public class KdTree {

    private Node root;  // 루트 노드
    private int size;   // 트리의 노드 수

    private static class Node { // 트리의 각 노드
        private Point2D p;  // 2차원에서의 위치
        private RectHV rect;    // 영역
        private Node left, right;
        private boolean vertical;

        public Node(Point2D p, RectHV rect, boolean vertical) {
            this.p = p;
            this.rect = rect;
            this.vertical = vertical;
        }
    }

    public KdTree() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (root == null) {
            root = new Node(p, new RectHV(0, 0, 1, 1), true);
            size++;
        }
        else if (!contains(p)) {
            root = insert(root, p);
            size++;
        }
    }

    private Node insert(Node x, Point2D p) {
        // 포인트가 트리 안에 없는 경우. 새로운 노드를 생성하고 반환
        if (x == null) {
            return new Node(p, null, false);
        }

        // 포인트가 이미 트리 안에 있는 경우. 기존 노드 사용
        if (p.equals(x.p)) {
            return x;
        }

        // 삽입하려는 포인트가 현재 노드의 포인트보다 작은지 큰지 비교.
        // vertical 이 true 이면 x 좌표를 비교하고, false 이면 y 좌표를 비교
        int comparePoints = comparePoints(p, x.p, x.vertical);
        RectHV newRect = createRect(x, comparePoints);
        if (comparePoints < 0) {
            x.left = insert(x.left, p);
            if (x.left.rect == null) {
                x.left.rect = newRect;
                x.left.vertical = !x.vertical;
            }
        }
        else {
            x.right = insert(x.right, p);
            if (x.right.rect == null) {
                x.right.rect = newRect;
                x.right.vertical = !x.vertical;
            }
        }
        return x;
    }

    private RectHV createRect(Node x, int comparePoints) {
        // 삽입하려는 포인트가 현재 노드 보다 작고 왼쪽 노드가 없는 경우
        // 해당 결과값을 근거로 새로운 사각형을 생성
        if ((comparePoints < 0) && (x.left == null)) {
            return x.vertical ?
                   new RectHV(x.rect.xmin(), x.rect.ymin(), x.p.x(), x.rect.ymax()) :
                   new RectHV(x.rect.xmin(), x.rect.ymin(), x.rect.xmax(), x.p.y());
        }
        else if ((comparePoints >= 0) && (x.right == null)) {
            return x.vertical ?
                   new RectHV(x.p.x(), x.rect.ymin(), x.rect.xmax(), x.rect.ymax()) :
                   new RectHV(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.rect.ymax());
        }
        return null;
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return contains(root, p);
    }

    private boolean contains(Node x, Point2D p) {
        if (x == null) {
            return false;
        }
        if (p.equals(x.p)) {
            return true;
        }
        int comparePoints = comparePoints(p, x.p, x.vertical);
        return comparePoints < 0 ? contains(x.left, p) : contains(x.right, p);
    }

    private int comparePoints(Point2D p, Point2D q, boolean vertical) {
        return vertical ? Double.compare(p.x(), q.x()) : Double.compare(p.y(), q.y());
    }

    public void draw() {
        draw(root, true);
    }

    private void draw(Node x, boolean vertical) {
        if (x == null) {
            return;
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        x.p.draw();

        StdDraw.setPenRadius();
        if (vertical) {
            StdDraw.setPenColor(StdDraw.RED);   // vertical
            StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);  // horizontal
            StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
        }

        draw(x.left, !vertical);
        draw(x.right, !vertical);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        List<Point2D> points = new LinkedList<>();
        range(root, rect, points);
        return points;
    }

    private void range(Node x, RectHV rect, List<Point2D> points) {
        if (x == null || !x.rect.intersects(rect)) {
            return;
        }

        if (rect.contains(x.p)) {
            points.add(x.p);
        }

        range(x.left, rect, points);
        range(x.right, rect, points);
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return nearest(root, p, root.p);
    }

    private Point2D nearest(Node x, Point2D p, Point2D nearest) {
        if (x == null || x.rect.distanceSquaredTo(p) > p.distanceSquaredTo(nearest)) {
            return nearest;
        }

        if (p.distanceSquaredTo(x.p) < p.distanceSquaredTo(nearest)) {
            nearest = x.p;
        }

        if (x.left != null && x.right != null) {
            if (x.left.rect.distanceSquaredTo(p) < x.right.rect.distanceSquaredTo(p)) {
                nearest = nearest(x.left, p, nearest);
                nearest = nearest(x.right, p, nearest);
            }
            else {
                nearest = nearest(x.right, p, nearest);
                nearest = nearest(x.left, p, nearest);
            }
        }
        else if (x.left != null) {
            nearest = nearest(x.left, p, nearest);
        }
        else if (x.right != null) {
            nearest = nearest(x.right, p, nearest);
        }
        return nearest;
    }

    public static void main(String[] args) {
        KdTree kdTree = new KdTree();

        kdTree.insert(new Point2D(0.5, 0.5));
        kdTree.insert(new Point2D(0.25, 0.25));
        kdTree.insert(new Point2D(0.75, 0.75));

        // 트리 사이즈
        System.out.println("트리 사이즈: " + kdTree.size());

        // contains 메서드 테스트
        System.out.println("1) (0.5, 0.5): " + kdTree.contains(new Point2D(0.5, 0.5)));
        System.out.println("1) Expected: true");
        System.out.println("2) (0.1, 0.1): " + kdTree.contains(new Point2D(0.1, 0.1)));
        System.out.println("2) Expected: false");
        System.out.println();

        // range 메서드 테스트
        RectHV rect = new RectHV(0, 0, 0.3, 0.3);
        System.out.println("3) 직사각형 포인트 (0, 0, 0.3, 0.3) 내에 있는 포인트:");
        for (Point2D p : kdTree.range(rect)) {
            System.out.println(p);
        }
        System.out.println();

        // nearest 메서드 테스트
        Point2D queryPoint = new Point2D(0.6, 0.6);
        System.out.println("4) 해당 포인트 (0.6, 0.6) 의 근처 포인트 값: " + kdTree.nearest(queryPoint));
    }
}
