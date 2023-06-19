package stacks_and_queues;
/* *****************************************************************************
 *  Name:              유민 조(yumin cho)
 *  Coursera User ID:  b825a9c2eed596fce92af80e0d7214d0
 *  Last modified:     June 18, 2023
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first;
    private Node<Item> last;
    private int size;

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> prev;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node<Item> oldFirst = first;
        first = new Node<>();
        first.item = item;
        first.next = oldFirst;

        if (isEmpty()) {
            last = first;
        }
        else {
            oldFirst.prev = first;
        }

        size = size + 1;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node<Item> oldLast = last;
        last = new Node<>();
        last.item = item;
        last.next = oldLast;

        if (isEmpty()) {
            first = last;
        }
        else {
            oldLast.next = last;
        }

        size = size + 1;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = first.item;
        first = first.next;

        size = size - 1;

        if (isEmpty()) {
            last = null;
        }
        else {
            first.prev = null;
        }

        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = last.item;
        last = last.prev;

        size = size - 1;

        if (isEmpty()) {
            first = null;
        }
        else {
            last.next = null;
        }

        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator(first);
    }

    private class DequeIterator implements Iterator<Item> {
        private Node<Item> current;

        public DequeIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item item = current.item;
            current = current.next;

            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        deque.addLast(2);

        System.out.println("deque.removeFirst() : " + deque.removeFirst());
        System.out.println("Expected : 1");

        System.out.println("deque.removeLast() : " + deque.removeLast());
        System.out.println("Expected : 2");
    }
}
