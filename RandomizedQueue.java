/* *****************************************************************************
 * Randomized Queue - resizing array implementation
 ***************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] a;
    private int n;

    // construct an empty randomized queue
    public RandomizedQueue() {
        n = 0;
        a = (Item[]) new Object[2];
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
            throw new IllegalArgumentException("cannot enqueue null item");
        }
        if (n == a.length) resize(2 * a.length); // double array size if needed
        a[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("cannot dequeue empty queue");
        }
        // randomly selects array index
        int random = StdRandom.uniform(0, n);

        // saves data at chosen index to item
        Item item = a[random];

        // set last item to fill this index, and remove last
        a[random] = a[n - 1];
        n--;

        // reduce array size if needed
        if (n > 0 && n == a.length / 4) resize(a.length / 2);

        // return item
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("cannot dequeue empty queue");
        }
        // randomly selects array index
        int random = StdRandom.uniform(0, n);
        return a[random];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    //*******************
    // PRIVATE METHODS  *
    //*******************
    private class RandomizedQueueIterator implements Iterator<Item> {
        private int current = n - 1;
        private boolean shuffled = false;

        public boolean hasNext() {
            return current >= 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("cannot get next, no more items to return");
            }
            if (!shuffled) {
                StdRandom.shuffle(a, 0, n - 1); // shuffle subarray of full sites
                shuffled = true;
            }
            return a[current--];
        }
    }

    private void resize(int capacity) {
        assert capacity > n;

        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    private void print() {
        for (Item i : this) {
            StdOut.print(i + " ");
        }
        StdOut.println();
    }

    private void printArr() {
        StdOut.print("Hard Print: ");
        for (int i = 0; i < n; i++) {
            StdOut.print(a[i] + "-");
        }
        StdOut.println("");
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> r = new RandomizedQueue<>();

        // init tests
        assert (r.isEmpty());
        assert (r.size() == 0);

        // enqueue()
        StdOut.println("");
        StdOut.println("Test 1 - enqueue");
        StdOut.println("Expected: 9 digits random order(1-9) Computed: ");
        r.enqueue(1);
        assert (!r.isEmpty());
        assert (r.size() == 1);
        r.enqueue(2);
        r.enqueue(3);
        r.enqueue(4);
        r.enqueue(5);
        assert (r.size() == 5);
        r.enqueue(6);
        r.enqueue(7);
        r.enqueue(8);
        r.enqueue(9);
        r.print();
        r.printArr();

        StdOut.println("");
        StdOut.println("Test 2 - sample");
        StdOut.println("Expected: 15 random single num (1,2,3,4,5) Computed: ");
        StdOut.println(r.sample());
        StdOut.println(r.sample());
        StdOut.println(r.sample());
        StdOut.println(r.sample());
        StdOut.println(r.sample());
        StdOut.println(r.sample());
        StdOut.println(r.sample());
        StdOut.println(r.sample());
        StdOut.println(r.sample());
        StdOut.println(r.sample());
        StdOut.println(r.sample());
        StdOut.println(r.sample());
        StdOut.println(r.sample());
        StdOut.println(r.sample());
        StdOut.println(r.sample());

        StdOut.println("");
        StdOut.println("Test 3 - dequeue");
        StdOut.println("Expected: 4 random single digits Computed: ");
        StdOut.println(r.dequeue());
        StdOut.println(r.dequeue());
        StdOut.println(r.dequeue());
        StdOut.println(r.dequeue());
        StdOut.println("Expected: random length 5 (1,2,3,4,5) Computed: ");
        r.print();
        // is error iteration or dequeue?
        r.printArr();

        // empty
        while (!r.isEmpty()) {
            r.dequeue();
        }

        StdOut.println("");
        StdOut.println("Test 4 - iterator");

        // test multiple iterators
        int n = 5;
        for (int i = 0; i < n; i++)
            r.enqueue(i);
        for (int a : r) {
            for (int b : r)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }

        StdOut.println("");
        StdOut.println("RandomizedQueue tests passed");
    }
}
