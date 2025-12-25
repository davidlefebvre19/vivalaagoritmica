package sorting;

/**
 * In this task, you must implement the `push` operation on a binary heap data structure.
 * As a reminder, a heap is a tree data structure such that the following invariant is respected
 *  
 *      For any node in the tree, the value associated with the node is higher (for a maxHeap) or lower
 *      (for a minHeap) than the value of its children.
 *
 * As a consequence, the minimum/maximum value is located at the root and can be retrieved in constant time.
 * In particular, this invariant must be respected after a push (or remove) operation.
 *
 * In this exercise the tree is represented with an array. The parent-child relation is implicitely represented
 * by the indices. A node at index i has its two children at index 2i and 2i+1.
 * For this is it assumed that the root is located at index 1 in the array.
 */
public class BinaryHeap {

    private int [] content;
    private int size;

    public BinaryHeap(int initialSize) {
        this.content = new int[initialSize];
        this.size = 0;
    }

    /**
     * Doubles the available size of this binary heap
     */
    private void increaseSize() {
        int [] newContent = new int[this.content.length*2];
        System.arraycopy(this.content, 0, newContent, 0, this.content.length);
        this.content = newContent;
    }

    private boolean less(int i, int j) {
        return content[i] < content[j];
    }

    private void exch(int i, int j) {
        int tmp = content[i];
        content[i] = content[j];
        content[j] = tmp;
    }

    private void swim(int value) {
        content[++size] = value;
        int k = size;
        while (k/2 >= 1) {
            int j = k/2;
            if (less(j,k)) break;
            exch(k,j);
            k = k/2;
        }
    }

    /**
     * Add a new value in this heap
     * The expected time complexity is O(log(n)) with n the size of the binary heap
     *
     * @param value the added value
     */
    public void push(int value) {
        if (size()+1 == content.length) increaseSize();
        swim(value);
    }

    /**
     * Returns the content of this heap
     */
    public int[] getContent() {
        return this.content;
    }

    /**
     * Returns the size of this heap
     */
    public int size() {
        return this.size;
    }

    /**
     * Returns the root of the tree representing this heap
     */
    public int getRoot() {
        return this.content[1];
    }

    public static void main(String[] args) {
        BinaryHeap bh = new BinaryHeap(5);
        bh.push(5);
        bh.push(4);
        bh.push(6);
    }
}
