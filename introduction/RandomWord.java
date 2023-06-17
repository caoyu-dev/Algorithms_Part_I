package introduction;/* *****************************************************************************
 *  Name:              유민 조(yumin cho)
 *  Coursera User ID:  b825a9c2eed596fce92af80e0d7214d0
 *  Last modified:     June 7, 2023
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String champion = null;
        for (int i = 1; !StdIn.isEmpty(); i++) {
            String word = StdIn.readString();
            if (StdRandom.bernoulli(1.0 / i)) {
                champion = word;
            }
        }
        if (champion != null) {
            StdOut.println(champion);
        }
    }
}
