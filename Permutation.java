/* *****************************************************************************
 *  Client program
 *  takes int k as command-line argument
 *      - reads a sequence of strings from standard input string using
 *        Stdn.readString()
 *      - prints exactly k of them, uniformly at random
 *  print each item from the sequence at most once
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> r = new RandomizedQueue<>();

        // TODO extra credit: only read in k elements
        while (!StdIn.isEmpty()) {
            r.enqueue(StdIn.readString());
        }

        // print exactly k times, each unique items
        int k = Integer.parseInt(args[0]);
        for (int i = 0; i < k; i++) {
            StdOut.println(r.dequeue());
        }
    }
}
