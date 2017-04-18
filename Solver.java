import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.*;


public class Solver {
    private int moves, minMoves;
    private Stack<Board> stack;
    public Solver(Board initial) {           // find a solution to the initial board (using the A* algorithm)
        if (initial== null)
            throw new java.lang.NullPointerException();
        moves = 0;
        Node node = new Node(initial, null,0);
        MinPQ<Node> queue = new MinPQ<>();
        while (!node.cur.isGoal()){
            moves++;
            for (Board it: node.cur.neighbors()){
                queue.insert(new Node(it,node,moves));
            }
            node = queue.delMin();
        }
        stack = new Stack<>();
        Node c = node;
        minMoves = c.steps;
        while (c != null){
            stack.add(c.cur);
            c = c.prev;
        }
    }
    private class Node implements Comparable{
        private Board cur;
        private int steps;
        private Node prev;

        public Node(Board cur, Node prev, int steps) {
            this.cur = cur;
            this.prev = prev;
            this.steps = steps;
        }

        @Override
        public int compareTo(Object o) {
            if (!(o instanceof Node))
                throw new RuntimeException("Wrong type of compared objects");
            Node compWith = (Node) o;
            return  (this.cur.manhattan() + this.steps) - (compWith.cur.manhattan() + compWith.steps);
        }
    }
    public boolean isSolvable() {            // is the initial board solvable?
        return true;
    }
    public int moves() {                     // min number of moves to solve initial board; -1 if unsolvable
        return minMoves;
    }
    public Iterable<Board> solution() {      // sequence of boards in a shortest solution; null if unsolvable
        return stack;
    }
    public static void main(String[] args) { // solve a slider puzzle (given below)
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
