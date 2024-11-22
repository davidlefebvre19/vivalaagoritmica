package fundamentals;

import jdk.jfr.internal.tool.Main;

import java.util.ArrayDeque;
import java.util.EmptyStackException;
import java.util.Queue;

/**
 * Author: Pierre Schaus and Auguste Burlats
 * Implement the abstract data type stack using two queues
 * You are not allowed to modify or add the instance variables,
 * only the body of the methods
 */
public class StackWithTwoQueues<E> {

    Queue<E> queue1;
    Queue<E> queue2;

    public StackWithTwoQueues() {
        queue1 = new ArrayDeque();
        queue2 = new ArrayDeque();
    }

    /**
     * Looks at the object at the top of this stack
     * without removing it from the stack
     */
    public boolean empty() {
         return queue2.isEmpty();
    }

    /**
     * Returns the first element of the stack, without removing it from the stack
     *
     * @throws EmptyStackException if the stack is empty
     */
    public E peek() throws EmptyStackException {
         if (queue2.isEmpty()) {
             throw new EmptyStackException();
         }
         return queue2.peek();
    }

    /**
     * Remove the first element of the stack and returns it
     *
     * @throws EmptyStackException if the stack is empty
     */
    public E pop() throws EmptyStackException {
        if (queue2.isEmpty()) {
            throw new EmptyStackException();
        }
        if (queue1.isEmpty()) {
            return queue2.remove();
        }
        E cargo = queue2.remove();
        while (queue1.size() != 1) {
            queue2.add(queue1.remove());
        }
        Queue<E> transfer = queue1;
        queue1 = queue2;
        queue2 = transfer;
        return cargo;
    }

    /**
     * Adds an element to the stack
     *
     * @param item the item to add
     */
    public void push(E item) {
        if (empty()) {
            queue2.add(item);
        } else {
            queue1.add(queue2.remove());
            queue2.add(item);
        }
    }

    // Méthode statique de démonstration
    public static void testStack() {
        StackWithTwoQueues<Integer> stack = new StackWithTwoQueues<>();

        stack.push(Integer.valueOf(1));
        stack.push(Integer.valueOf(2));
        stack.push(Integer.valueOf(3));

        System.out.println("Element au sommet: " + stack.peek()); // Devrait afficher 3
        System.out.println("Element retiré: " + stack.pop()); // Devrait afficher 3
        System.out.println("Element au sommet après pop: " + stack.peek()); // Devrait afficher 2
        System.out.println("La stack est-elle vide ? " + stack.empty()); // Devrait afficher false
    }

    public static void main(String[] args) {
        StackWithTwoQueues.testStack();
    }

}

