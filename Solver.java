import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;



public class Solver {
    private int moves, minMoves;
    private Stack<Board> stack;
    private boolean isSolv;
    public Solver(Board initial) {           // find a solution to the initial board (using the A* algorithm)
        if (initial== null)
            throw new java.lang.NullPointerException();
        isSolv = true;
        moves = 0;
        Node node = new Node(initial, null,0);
        Node twinNode = new Node(initial.twin(),null,0);
        MinPQ<Node> queue = new MinPQ<>();
        MinPQ<Node> twinQueue = new MinPQ<>();
        while ((!node.board.isGoal()) && (!twinNode.board.isGoal()) ){
            moves++;
            for (Board it: node.board.neighbors()){
                if ((node.prev == null) || (!it.equals(node.prev.board)))
                    queue.insert(new Node(it,node,moves));
            }
            node = queue.delMin();

            for (Board it: twinNode.board.neighbors()){
                if ((twinNode.prev == null) || (!it.equals(twinNode.prev.board)))
                    twinQueue.insert(new Node(it,twinNode,moves));
            }
            twinNode = twinQueue.delMin();
        }
        if(twinNode.board.isGoal()){
            minMoves = -1;
            isSolv = false;
            stack = null;
        }
        else {
            stack = new Stack<>();
            Node c = node;
            minMoves = 0;
            while (c != null) {
                stack.push(c.board);
                c = c.prev;
                minMoves++;
            }
            minMoves--;
        }
    }
    private class Node implements Comparable<Node>{
        private Board board;
        private int steps;
        private Node prev;

        public Node(Board cur, Node prev, int steps) {
            this.board = cur;
            this.prev = prev;
            this.steps = steps;
        }

        @Override
        public int compareTo(Node o) {
            return  (this.board.manhattan() + this.steps) - (o.board.manhattan() + o.steps);
        }
    }
    public boolean isSolvable() {            // is the initial board solvable?
        return isSolv;
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
