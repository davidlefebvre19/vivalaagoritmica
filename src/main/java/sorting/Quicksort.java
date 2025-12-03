package sorting;
import java.util.Arrays;
import java.util.Random;

import java.util.ArrayList;

public class Quicksort {

    ArrayList<Integer> list;
    int[] a;
    Integer[] b;

    public void createRandomArrayList(int size) {
        list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            list.add(random.nextInt(21));
        }
    }

    public void createRandomArray(int size) {
        a = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            a[i] = random.nextInt(21);
        }
    }

    public boolean checkIfSortedArrayList() {
        for (int i = 0; i < list.size()-1; i++) {
            if (list.get(i) > list.get(i+1)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkIfSortedArrayInteger() {
        for (int i = 0; i < b.length; i++) {
            System.out.println(b[i]);
        }
        for (int i = 0; i < b.length-1; i++) {
            if (b[i] > b[i+1]) {
                return false;
            }
            return true;
        }
        return true;
    }

    public boolean checkIfSortedArrayInt() {
        for (int i = 0; i < a.length-1; i++) {
            if (a[i] > a[i+1]) {
                return false;
            }
            return true;
        }
        return true;
    }

    private static void sort(Comparable[] a, int lo, int hi)
    {
        if (hi <= lo) return;
        int j = partition(a, lo, hi); // Partition (see page 291).
        sort(a, lo, j-1); // Sort left part a[lo .. j-1].
        sort(a, j+1, hi); // Sort right part a[j+1 .. hi].
    }

    private static int partition(Comparable[] a, int lo, int hi)
    { // Partition into a[lo..i-1], a[i], a[i+1..hi].
        int i = lo, j = hi+1; // left and right scan indices
        Comparable v = a[lo]; // partitioning item
        while (true)
        { // Scan right, scan left, check for scan complete, and exchange.
            while (less(a[++i], v)) if (i == hi) break;
            while (less(v, a[--j])) if (j == lo) break;
/*            while (true) {
                i++;
                if (less(a[i], v)) {
                    if (i == hi) break;
                } else break;
            }
            while (true) {
                j--;
                if (less(v, a[j])) {
                    if (j == lo) break;
                } else break;
            }*/
            if (i >= j) break;
            exch(a, i, j);
        }
        exch(a, lo, j); // Put v = a[j] into position
        return j; // with a[lo..j-1] <= a[j] <= a[j+1..hi].
    }

    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        if (v == w) return false;   // optimization when reference equals
        return v.compareTo(w) < 0;
    }

    // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    public static void main(String[] args) {
        Quicksort quicksort = new Quicksort();
/*        quicksort.createRandomArray(5);
        boolean res = quicksort.checkIfSorted();
        System.out.println(res);
        Collections.sort(quicksort.list);
        System.out.println(quicksort.checkIfSorted());*/

/*        quicksort.createRandomArray(5);
        quicksort.sort(Arrays.stream(quicksort.a).boxed().toArray(Integer[]::new), 0, quicksort.a.length-1);
        System.out.println(quicksort.checkIfSortedArrayInt());*/
/*        Integer[] ar = {12,10,13,4,19};
        quicksort.b = ar;
        quicksort.sort(Arrays.stream(quicksort.b).toArray(Integer[]::new), 0, 4);*/
        // System.out.println(quicksort.checkIfSortedArrayInteger());
    }
}
