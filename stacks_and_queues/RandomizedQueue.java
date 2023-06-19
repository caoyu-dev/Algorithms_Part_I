package stacks_and_queues;
/* *****************************************************************************
 *  Name:              유민 조(yumin cho)
 *  Coursera User ID:  b825a9c2eed596fce92af80e0d7214d0
 *  Last modified:     June 18, 2023
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int n;

    // construct an empty randomized queue
    public RandomizedQueue() {
        queue = (Item[]) new Object[2];
        n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (n == queue.length) {
            resize(2 * queue.length);
        }

        queue[n++] = item;
    }

    private void resize(int capacity) {
        if (capacity < n) {
            throw new IllegalArgumentException();
        }

        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = queue[i];
        }

        queue = temp;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int index = StdRandom.uniformInt(n);
        Item item = queue[index];
        queue[index] = queue[n - 1];
        queue[n - 1] = null;

        n--;
        if (n > 0 && n == queue.length / 4) {
            resize(queue.length / 2);
        }

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int index = StdRandom.uniformInt(n);
        return queue[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;
        private int[] temp;

        public ArrayIterator() {
            temp = new int[n];
            for (int i = 0; i < n; i++) {
                temp[i] = i;
            }
            StdRandom.shuffle(temp);
        }

        public boolean hasNext() {
            return i < n;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return queue[temp[i++]];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();
        randomizedQueue.enqueue(1);
        randomizedQueue.enqueue(2);

        System.out.println(randomizedQueue.dequeue());
        System.out.println(randomizedQueue.dequeue());
    }
}
