import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.abs;

/**
 * Created by Алексей on 17.04.2017.
 */


public class Board {
    private int[][] blocks;
    private int dim, zi, zj;

    public Board(int[][] blocks) {
        this.dim = blocks.length;
        this.blocks = new int[dim][dim];

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                this.blocks[i][j] = blocks[i][j];
                if (blocks[i][j] == 0) {
                    zi = i;
                    zj = j;
                }
            }
        }
    }

    public int dimension(){  // board dimension n
        return dim;
    }
    public int hamming() {                  // number of blocks out of place
        int counter = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if ((blocks[i][j] != 0) &&((i*dim + j+1) != blocks[i][j])) counter++;
            }
        }
        return counter;
    }
    public int manhattan() {                 // sum of Manhattan distances between blocks and goal
        int counter = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (blocks[i][j] != 0){
                    counter += abs(i-blocks[i][j]/dim) + abs(j - (blocks[i][j]-1)%dim);
                }
            }
        }
        return counter;
    }
    public boolean isGoal() {                // is this board the goal board?
        if (this.hamming() == 0)
            return true;
        return false;
    }
    public Board twin() {                    // a board that is obtained by exchanging any pair of blocks
        int[][] twin = copyArray(blocks);

        int i1=0,j1=0,i2=0,j2=1;
        if (twin[i1][j1] == 0) i1 = 1;
        else if (twin[i2][j2] == 0) i2 = 1;

        exchange(twin,i1,j1,i2,j2);
        return new Board(twin);
    }
    public boolean equals(Object y) {        // does this board equal y?
        if (!(y instanceof Board))
            return false;
        Board t = (Board) y;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (this.blocks[i][j] != t.blocks[i][j])
                    return false;
            }
        }
        return true;
    }
    private void exchange(int[][] arr, int i1, int j1, int i2, int j2){
        int buf = arr[i1][j1];
        arr[i1][j1] = arr[i2][j2];
        arr[i2][j2] = buf;
    }
    private int[][] copyArray(int[][] arr){
        int l = arr.length;
        int [][] res = new int[l][l];
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < l; j++) {
                res[i][j] = arr[i][j];
            }
        }
        return res;
    }
    public Iterable<Board> neighbors() {     // all neighboring boards
        List<Board> list = new ArrayList<>();
        int[][] initArr = copyArray(blocks);
        if (zi != 0){
            exchange(initArr,zi,zj,zi-1,zj);
            list.add(new Board(initArr));
            exchange(initArr,zi,zj,zi-1,zj);
        }
        if (zi != dim -1){
            exchange(initArr,zi,zj,zi+1,zj);
            list.add(new Board(initArr));
            exchange(initArr,zi,zj,zi+1,zj);
        }
        if (zj != 0){
            exchange(initArr,zi,zj,zi,zj-1);
            list.add(new Board(initArr));
            exchange(initArr,zi,zj,zi,zj-1);
        }
        if (zj != dim-1){
            exchange(initArr,zi,zj,zi,zj+1);
            list.add(new Board(initArr));
            exchange(initArr,zi,zj,zi,zj+1);
        }
        return list;
    }
    public String toString() {               // string representation of this board (in the output format specified below)
        StringBuilder sb = new StringBuilder();
        sb.append(dim).append('\n');
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                sb.append(this.blocks[i][j]).append(" ");
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public static void main(String[] args) { // unit tests (not graded)
        int[][] a = {{0,1,3},{4,8,2},{7,6,5}};

        Board b = new Board(a);
        System.out.println(b);
        for(Board it: b.neighbors()){
            System.out.println(it);
        }
    }
}
