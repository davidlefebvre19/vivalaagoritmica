package sorting;

import java.security.Key;
import java.util.Comparator;
import java.util.EmptyStackException;

/*Differents internal implementations exist :
- Unordered array (insert = O(1), deleteMax() = O(N))
- Ordered array (insert = O(N), deleteMax = O(1))
There is also the possibility of using a linked list with a stack that keeps the maximum (or minimum) on the top of the stack
Those approaches will take linear time an can be accelerated using a heap */

// -> We will use a heap in this implementation.

// Wa add genericity to this class using a ganeric Key datatype, this genereic datatype has to implement Comparable interface
// This approach has the advantage of allowing us to use the less method for the insert method logic, keeping the genericity.
// Client code can use primitive or wrappers of primitives datatypes or, if the user uses a custom object, will be obligated
// to implement the Comparable<> interface on their custom object
public class MaxPQ <Key extends Comparable<Key>> {
    private Key[] pq;
    private int size = 0;

    // Three differents constructors

    // create pq
    public MaxPQ() {
        // TODO
    }

    // create pq of initial capacity "max"
    public MaxPQ(int max) {
        pq = (Key[]) new Comparable[max+1]; // Key a comme type le plus générique, l'interface Comparable qui est connu par le compilateur.

    }

    // create pq from the keys in a
    public MaxPQ(Key[] a) {
        // TODO
    }

    // Compare two elements on the heap using array representation
    private boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    private void exch(int i, int j) {
        Key tmp = pq[i];
        pq[i] = pq[j];
        pq[j] = tmp;
    }

    private void swim(int v) {
        while (v > 1 && less(v/2, v)) {
            exch(v, v/2);
            v = v/2;
        }
    }

    private void sink(int v) {
        while (2*v <= size()) { // why size ?
            int j = 2*v;
            if (j < size() && less(j, j+1)) { // if j does not have a right brother, j == size() and j < size() will not be true and comparision between j and j+1 IS relevant, reminider, we sink to the biggest of the two childs
                j++;
            }
            if (!less(v, j)) break;
            exch(v,j);
            v = j;
        }
    }

    // insert a key in the priority queue
    public void insert(Key v) {
        pq[++size] = v;
        swim(size());
    }

    // return largest key
    public Key max() {
        if (!isEmpty()) return pq[1];
        else throw new EmptyStackException();
    }

    // remove the largest key
    public Key delMax() {
        Key max = max();
        exch(1, size());
        pq[size] = null;
        size--;
        sink(1);
        return max;
    }

    // is pq empty
    public boolean isEmpty() {
        return size == 0;
    }

    // size of pq
    public int size() {
        return size;
    }

    private static void static_exch(Comparable[] a,  int i, int j) {
        Comparable tmp = a[i-1];
        a[i-1] = a[j-1];
        a[j-1] = tmp;
    }

    private static boolean static_less(Comparable[] a, int i, int j) {
        return a[i-1].compareTo(a[j-1]) < 0;
    }

    public static void static_sink(Comparable[] a, int k, int N) {
        while(k*2 <= N) {
            int j = k*2;
            if (j < N && static_less(a, j,j+1)) j++;
            if (!static_less(a, k, j)) break;
            static_exch(a, k,j);
            k = j;
        }
    }

    public static void heapsort(Comparable[] a) {
        int N = a.length;
        for (int k = N/2; k >= 1; k--) {
            static_sink(a, k, N);
        }
        while (N > 1) {
            static_exch(a, 1, N--);
            static_sink(a, 1, N);
        }
    }

    public static void main(String[] args) {
/*        // test of heap
        Character[] data = new Character[] {'R', 'N', 'G', 'T', 'O', 'S', 'H', 'I', 'A', 'P', 'E'};
        MaxPQ maxpq = new MaxPQ(11);
        for (int i = 0; i < data.length; i++) {
            maxpq.insert(data[i]);
        }
        for (int i = 0; i < maxpq.size+1; i++) {
            System.out.println(maxpq.pq[i]);
        }
        System.out.println("max deleted, elemeent deleted : " +  maxpq.delMax());
        for (int i = 0; i < maxpq.size+1; i++) {
            System.out.println(maxpq.pq[i]);
        }*/

        // test on heap sort
        Character[] data = new Character[] {'S', 'O', 'R', 'T', 'E', 'X', 'A', 'M', 'P', 'L', 'E'};
        heapsort(data);
        for (int i = 0; i < data.length; i++) {
            System.out.println(data[i]);
        }
    }
}
