package fundamentals;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Author Pierre Schaus
 *
 * We are interested in the implementation of a circular simply linked list,
 * i.e. a list for which the last position of the list refers, as the next position,
 * to the first position of the list.
 *
 * The addition of a new element (enqueue method) is done at the end of the list and
 * the removal (remove method) is done at a particular index of the list.
 *
 * A (single) reference to the end of the list (last) is necessary to perform all operations on this queue.
 *
 * You are therefore asked to implement this circular simply linked list by completing the class see (TODO's)
 * Most important methods are:
 *
 * - the enqueue to add an element;
 * - the remove method [The exception IndexOutOfBoundsException is thrown when the index value is not between 0 and size()-1];
 * - the iterator (ListIterator) used to browse the list in FIFO.
 *
 * @param <Item>
 */
public class CircularLinkedList<Item> implements Iterable<Item> {

    private long nOp = 0; // count the number of operations
    private int n;          // size of the stack
    private Node  last;   // trailer of the list

    // helper linked list class
    private class Node {
        private Item item;
        private Node next;
    }

    public CircularLinkedList() {
        n = 0;
        last = null;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    private long nOp() {
        return nOp;
    }



    /**
     * Append an item at the end of the list
     * @param item the item to append
     */
    public void enqueue(Item item) {
        Node node = new Node();
        node.item = item;
        if (last == null) {
            last = node;
            last.next = node;
            nOp++;
            n++;
            return;
        }
        node.next = last.next;
        last.next = node;
        last = node;
        nOp++;
        n++;
    }

    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their indices).
     * Returns the element that was removed from the list.
     */
    public Item remove(int index) {
         if(index < 0 || index >= n) {throw new IndexOutOfBoundsException();}
         Node node = last;
         int idx = size()-1;
         while((idx+1)%size() != index) {
            node = node.next;
            idx = (idx+1)%size();
         }
         Item cargo = node.next.item;
         node.next = node.next.next;
         n--;
         nOp++;
         return cargo;
    }


    /**
     * Returns an iterator that iterates through the items in FIFO order.
     * @return an iterator that iterates through the items in FIFO order.
     */
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    /**
     * Implementation of an iterator that iterates through the items in FIFO order.
     * The iterator should implement a fail-fast strategy, that is ConcurrentModificationException
     * is thrown whenever the list is modified while iterating on it.
     * This can be achieved by counting the number of operations (nOp) in the list and
     * updating it everytime a method modifying the list is called.
     * Whenever it gets the next value (i.e. using next() method), and if it finds that the
     * nOp has been modified after this iterator has been created, it throws ConcurrentModificationException.
     */
    private class ListIterator implements Iterator<Item> {

        long currnOp = nOp;
        int idx = 0;
        int size = size();
        Node curr = null;

        public ListIterator() {
            if (!isEmpty()) {
                curr = last.next;
            }
        }

        @Override
        public boolean hasNext() {
             if (currnOp != nOp) {throw new ConcurrentModificationException();}
             return idx < size && size != 0;
        }

        @Override
        public Item next() {
             if (currnOp != nOp) {throw new ConcurrentModificationException();}
             if (hasNext()) {
                 Item cargo = curr.item;
                 curr = curr.next;
                 idx++;
                 return cargo;
             } else {
                 throw new NoSuchElementException();
             }
        }

    }

    public static void main(String[] args) {
        CircularLinkedList<Integer> list = new CircularLinkedList<Integer>();
        list.enqueue(1);
        list.enqueue(2);
        list.enqueue(3);
        list.enqueue(4);

/*        Iterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }*/

        list.remove(3);

        Iterator<Integer> et = list.iterator();
        while (et.hasNext()) {
            System.out.println(et.next());
        }
    }

}
