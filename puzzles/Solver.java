package puzzles;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;

import java.util.LinkedList;

public class Solver {
    private boolean isSolvable;
    private LinkedList<Board> solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        MinPQ<SearchNode> pq = new MinPQ<SearchNode>();     // 원래 퍼즐
        MinPQ<SearchNode> twinpq = new MinPQ<SearchNode>(); // 한 쌍의 타일이 변경된 퍼즐

        // initial 상태의 보드를 삽입
        pq.insert(new SearchNode(initial, 0, null));
        twinpq.insert(new SearchNode(initial.twin(), 0, null));

        SearchNode node;
        SearchNode nodeTwin;

        while (true) {
            // 우선순위 큐에서 현재 가장 작은 요소 노출
            node = pq.delMin();
            nodeTwin = twinpq.delMin();

            if (node.board.isGoal()) {
                isSolvable = true;
                break;
            }
            else if (nodeTwin.board.isGoal()) {
                break;
            }

            processNeighbors(node, pq);
            processNeighbors(nodeTwin, twinpq);
        }

        // 해결할 수 있는 상태가 확인되면 isSolvable = true 로 두고 해결 상태에 대한 내용은 solution 에 저장
        solution = isSolvable ? new LinkedList<>() : null;
        if (isSolvable) {
            while (node != null) {
                solution.addFirst(node.board);
                node = node.prev;
            }
        }
        else {
            solution = null;
        }
    }

    private void processNeighbors(SearchNode node, MinPQ<SearchNode> pq) {
        for (Board neighbor : node.board.neighbors()) {
            if (node.prev == null || !neighbor.equals(node.prev.board)) {
                pq.insert(new SearchNode(neighbor, node.moves + 1, node));
            }
        }
    }

    private static class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final int moves;
        private final SearchNode prev;
        private final int priority;

        public SearchNode(Board board, int moves, SearchNode prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
            this.priority = moves + board.manhattan();
        }

        public int compareTo(SearchNode that) {
            return Integer.compare(this.priority, that.priority);
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable) {
            return -1;
        }
        return solution.size() - 1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
    }

    // test client (see below)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        // 초기 상태 보드 생성하고
        Board initial = new Board(tiles);
        // 그대로 solver 에 투입
        Solver solver = new Solver(initial);

        if (!solver.isSolvable())
            System.out.println("해결 방법 없음");
        else {
            System.out.println("해결 방법에 대한 최소한의 움직임: " + solver.moves());
            for (Board board : solver.solution()) {
                System.out.println(board);
            }
        }
    }
}
