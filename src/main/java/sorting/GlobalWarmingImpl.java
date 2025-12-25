package sorting;

import java.util.Arrays;

/**
 * Author Pierre Schaus
 *
 * Assume the following 5x5 matrix that represent a grid surface:
 * int [][] tab = new int[][] {{1,3,3,1,3},
 *                             {4,2,2,4,5},
 *                             {4,4,1,4,2},
 *                             {1,4,2,3,6},
 *                             {1,1,1,6,3}};
 *
 * Each entry in the matrix represents an altitude point at the corresponding grid coordinate.
 * The goal is to implement a GlobalWarmingImpl class that extends the GlobalWarming abstract class described below.
 *
 * Given a global water level, all positions in the matrix with a value <= the water level are flooded and therefore unsafe.
 * So, assuming the water level is 3, all safe points are highlighted between parenthesis:
 *
 *   1 , 3 , 3 , 1 , 3
 *  (4), 2 , 2 ,(4),(5)
 *  (4),(4), 1 ,(4), 2
 *   1 ,(4), 2 , 3 ,(6)
 *   1 , 1 , 1 ,(6), 3}
 *
 * The method you need to implement is nbSafePoints
 * - calculating the number of safe points for a given water level
 *
 * Complete the code below. Pay attention to the expected time complexity of each method.
 * To meet this time complexity, you need to do some pre-computation in the constructor.
 * Feel free to create internal classes in GlobalWarmingImpl to make your implementation easier.
 * Feel free to use any method or data structure available in the Java language and API.
 */

abstract class GlobalWarming {


    protected final int[][] altitude;

    /**
     * @param altitude is a n x n matrix of int values representing altitudes (positive or negative)
     */
    public GlobalWarming(int[][] altitude) {
        this.altitude = altitude;
    }

    /**
     *
     * @param waterLevel
     * @return the number of entries in altitude matrix that would be above
     *         the specified waterLevel.
     *         Warning: this is not the waterLevel given in the constructor/
     */
    public abstract int nbSafePoints(int waterLevel);

}


public class GlobalWarmingImpl extends GlobalWarming {

    private int [] flattend_altitudes;

    private boolean less(int i, int j){
        return flattend_altitudes[i-1] < flattend_altitudes[j-1];
    }

    private void exch(int i, int j) {
        int tmp = flattend_altitudes[i-1];
        flattend_altitudes[i-1] = flattend_altitudes[j-1];
        flattend_altitudes[j-1] = tmp;
    }

    private void sink(int k, int N) {
        while (k*2 <= N) {
            int j = k*2;
            if (j < N && less(j, j+1)) j++;
            if (less(k, j)) exch(k,j);
            k = j;
        }
    }

    private void heapsort() {
        int N = flattend_altitudes.length;
        for (int i = flattend_altitudes.length/2; i > 1 ; i--) {
            sink(i, N);
        }
        while (N > 1) {
            exch(1, N--);
            sink(1, N);
        }
    }

    public GlobalWarmingImpl(int[][] altitude) {
        super(altitude);
        // expected pre-processing time in the constructror : O(n^2 log(n^2))
        int idx = 0;
        flattend_altitudes = new int[altitude.length*altitude.length];
        for (int i = 0; i < altitude.length; i++) {
            for (int j = 0; j < altitude[i].length; j++) {
                flattend_altitudes[idx++] = altitude[i][j];
            }
        }
        heapsort();
    }

    /**
     * Returns the number of safe points given a water level
     *
     * @param waterLevel the level of water
     */
    public int nbSafePoints(int waterLevel) {
        int nbr =0;
        for (int i = 0; i < flattend_altitudes.length; i++) {
            if (flattend_altitudes[i] > waterLevel) nbr++;
        }
        return nbr;
    }

    public static void main(String[] args) {
        int[][] matrix_t = new int[][] {
            {0, 0, 0},
            {0, 1, 0},
            {0, 0, 0}
        };
        GlobalWarming gw = new GlobalWarmingImpl(matrix_t);
        System.out.println(gw.nbSafePoints(0));
    }

}
