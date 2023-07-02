package puzzles;

import java.util.LinkedList;

public class Board {
    private final int[][] tiles;    // 타일의 위치
    private final int n;    // 타일이 들어간 게임 크기(가로, 새로 크기 동일하여 n 으로 한정)
    private int hamming;
    private int manhattan;
    private int blankRow;
    private int blankCol;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null || tiles.length == 0 || tiles.length != tiles[0].length) {
            throw new IllegalArgumentException();
        }
        this.n = tiles.length;
        this.tiles = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];
                if (tiles[i][j] == 0) {
                    blankRow = i;
                    blankCol = j;
                }
                else {
                    int goal = i * n + j + 1;
                    if (tiles[i][j] != goal) {
                        hamming++;
                    }
                }
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(n + "\n");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                str.append(String.format("%2d ", tiles[i][j]));
            }
            str.append("\n");
        }

        return str.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null || y.getClass() != getClass()) {
            return false;
        }

        Board that = (Board) y;

        if (this.n != that.n) {
            return false;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != that.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int[] dx = { -1, 1, 0, 0 };   // 상하
        int[] dy = { 0, 0, -1, 1 };   // 좌우

        LinkedList<Board> neighbors = new LinkedList<>();

        int zeroX = 0, zeroY = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    zeroX = i;
                    zeroY = j;
                    break;
                }
            }
        }

        for (int k = 0; k < 4; k++) {
            int newX = zeroX + dx[k];
            int newY = zeroY + dy[k];

            if (newX >= 0 && newX < n && newY >= 0 && newY < n) {
                int[][] tilesCopy = copy(tiles);
                tilesCopy[zeroX][zeroY] = tilesCopy[newX][newY];
                tilesCopy[newX][newY] = 0;
                neighbors.add(new Board(tilesCopy));
            }
        }

        return neighbors;
    }

    private int[][] copy(int[][] tiles) {
        int[][] copy = new int[tiles.length][];
        for (int i = 0; i < tiles.length; i++) {
            copy[i] = tiles[i].clone();
        }
        return copy;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board twin = new Board(tiles);

        int row = (twin.blankRow + 1) % n;
        int tmp = twin.tiles[row][0];
        twin.tiles[row][0] = twin.tiles[row][1];
        twin.tiles[row][1] = tmp;

        return twin;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = {
                { 0, 1, 3 },
                { 4, 2, 5 },
                { 7, 8, 6 }
        };

        Board board = new Board(tiles);

        System.out.println("1) 게임 시작할 때의 보드 상태: \n" + board.toString());
        System.out.println("2) Dimension 테스트: " + board.dimension());
        System.out.println("3) Hamming 테스트: " + board.hamming());

        Board sameBoard = new Board(tiles);
        System.out.println("4) 타일 번호가 다 같은가: " + board.equals(sameBoard));
        System.out.println("5) 이 보드가 목표 보드에 해당하는가: " + board.isGoal());

        System.out.println("6) neighbors 테스트:");
        for (Board neighbor : board.neighbors()) {
            System.out.println(neighbor.toString());
        }

        // twin 테스트
        System.out.println("7) Twin 테스트:");
        System.out.println(board.twin().toString());
    }

}
