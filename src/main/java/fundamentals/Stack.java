package fundamentals;

import java.util.EmptyStackException;

/**
 * Author: Pierre Schaus
 *
 * You have to implement the interface using
 * - a simple linkedList as internal structure
 * - a growing array as internal structure
 */
public interface Stack<E> {

    /**
     * Looks at the object at the top of this stack
     * without removing it from the stack
     */
    public boolean empty();

    /**
     * Returns the first element of the stack, without removing it from the stack
     *
     * @throws EmptyStackException if the stack is empty
     */
    public E peek() throws EmptyStackException;

    /**
     * Remove the first element of the stack and returns it
     *
     * @throws EmptyStackException if the stack is empty
     */
    public E pop() throws EmptyStackException;

    /**
     * Adds an element to the stack
     *
     * @param item the item to add
     */
    public void push(E item);

}

/**
 * Implement the Stack interface above using a simple linked list.
 * You should have at least one constructor withtout argument.
 * You are not allowed to use classes from java.util
 * @param <E>
 */
class LinkedStack<E> implements Stack<E> {

    private Node<E> top;        // the node on the top of the stack
    private int size;        // size of the stack
    private Node<E> bottom;

    // helper linked list class
    private class Node<E> {
        private E item;
        private Node<E> next;

        public Node(E element, Node<E> next) {
            this.item = element;
            this.next = next;
        }
    }

    @Override
    public boolean empty() {
        return size == 0;
    }

    @Override
    public E peek() throws EmptyStackException {
        if (empty()) {throw new EmptyStackException();}
        return top.item;
    }

    @Override
    public E pop() throws EmptyStackException {
        if (empty()) {throw new EmptyStackException();}
        if(size == 1) {
            E cargo = top.item;
            top = null;
            bottom = null;
            size = 0;
            return cargo;
        }
        Node curr = bottom;
        while (curr.next != top) {
            curr = curr.next;
        }
        E cargo = top.item;
        top = curr;
        size--;
        return cargo;
    }

    @Override
    public void push(E item) {
        if (empty()) {
            bottom = new Node<>(item, null);
            top = bottom;
        }
        else {
            Node n = new Node<>(item, null);
            top.next = n;
            top = n;
        }
        size++;
    }
}


/**
 * Implement the Stack interface above using an array as internal representation
 * The capacity of the array should double when the number of elements exceed its length.
 * You should have at least one constructor withtout argument.
 * You are not allowed to use classes from java.util
 * @param <E>
 */
class ArrayStack<E> implements Stack<E> {

    private E[] array;        // array storing the elements on the stack
    private int size;        // size of the stack

    public ArrayStack() {
        array = (E[]) new Object[2];
    }

    @Override
    public boolean empty() {
        return size == 0;
    }

    @Override
    public E peek() throws EmptyStackException {
        if (empty()) {throw new EmptyStackException();}
        return array[size-1];
    }

    @Override
    public E pop() throws EmptyStackException {
        if (empty()) {throw new EmptyStackException();}
        return array[--size];
    }

    @Override
    public void push(E item) {
        if (size == array.length) {
            E[] newArray = (E[]) new Object[array.length * 2];
            System.arraycopy(array, 0, newArray, 0, array.length);
            newArray[size] = item;
            array = newArray;
            size++;
        } else {
            array[size++] = item;
        }
    }

    public static void main(String[] args) {
/*        LinkedStack<Integer> ls = new LinkedStack<>();
        ls.push(1);
        ls.push(2);
        System.out.println(ls.peek());
        System.out.println(ls.pop());
        System.out.println(ls.pop());*/
        // System.out.println(ls.pop());

        ArrayStack<Integer> as = new ArrayStack<>();
        as.push(1);
        as.push(2);
        as.push(3);
        as.push(4);
        System.out.println(as.peek());
        System.out.println(as.pop());
        System.out.println(as.pop());
    }
}

