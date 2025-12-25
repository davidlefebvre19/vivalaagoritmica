package sorting;

import java.util.EmptyStackException;

public class MinPQ <Key extends Comparable<Key>> {
    private Key[] pq;
    private int size = 0;

    public MinPQ(int max) {
        pq = (Key[]) new Comparable[max+1];
    }

    private boolean greater(int i, int j) {return pq[i].compareTo(pq[j]) >0;}

    private void exch(int i, int j) {
        Key tmp = pq[i];
        pq[i] = pq[j];
        pq[j] = tmp;
    }

    private void swim(int v) {
        while (v > 1 && greater(v/2, v)) {
            exch(v, v/2);
            v = v/2;
        }
    }

    private void sink(int v) {
        while ( 2*v <= size) {
            int j = v*2;
            if (j < size && greater(j, j+1)) j++;
            if (!greater(v,j)) break;
            exch(v,j);
            v = j;
        }
    }

    public void insert(Key v) {
        pq[++size] = v;
        swim(size);
    }

    public Key min(){
        if (size >= 1) return pq[1];
        throw new EmptyStackException();
    }

    public Key delMin() {
        Key min = min();
        exch(1, size);
        pq[size] = null;
        size--;
        sink(1);
        return min;
    }

    public int size() {
        return this.size;
    }

    public static void main(String[] args) {
        MinPQ mpq = new MinPQ(5);
        for (int i = 3; i > 0; i--) {
            mpq.insert(i);
        }
        for (int i = 0; i < 3; i++) {
            System.out.println(mpq.delMin());
        }
    }
}
