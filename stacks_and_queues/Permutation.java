package stacks_and_queues;
/* *****************************************************************************
 *  Name:              유민 조(yumin cho)
 *  Coursera User ID:  b825a9c2eed596fce92af80e0d7214d0
 *  Last modified:     June 18, 2023
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int num = Integer.parseInt(args[0]);

        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            randomizedQueue.enqueue(StdIn.readString());
        }

        for (int i = 0; i < num; i++) {
            StdOut.println(randomizedQueue.dequeue());
        }
    }
}
