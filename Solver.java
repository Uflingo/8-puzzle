import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Solver {
    private int moves;
    private List<Board> gameTree;
    public Solver(Board initial) {           // find a solution to the initial board (using the A* algorithm)
        if (initial== null)
            throw new java.lang.NullPointerException();
        moves = 0;
        gameTree = new ArrayList<>();
        MinPQ<Board> queue =  new MinPQ<>(new ManhattanOrder());
        Board cur = initial;
        gameTree.add(cur);
        Board prev = initial;
        while (!cur.isGoal()){
            for(Board it: cur.neighbors()){
                if (!prev.equals(it))
                    queue.insert(it);
            }
            prev = cur;
            moves++;
            cur = queue.delMin();
            gameTree.add(cur);
        }

    }
    public boolean isSolvable() {            // is the initial board solvable?
        return true;
    }
    public int moves() {                     // min number of moves to solve initial board; -1 if unsolvable
        return moves;
    }
    public Iterable<Board> solution() {      // sequence of boards in a shortest solution; null if unsolvable
        return gameTree;
    }
    private class ManhattanOrder implements Comparator<Board>{
        public int compare(Board a, Board b){
            return b.manhattan() - a.manhattan();
        }
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
