/* *****************************************************************************
 * Assignment 2 Queues & Stacks
 * Deque implementation using double linked list structure
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;

    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    // construct an empty deque
    public Deque() {
        size = 0;
        first = null;
        last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("cannot add null first");
        }

        size++;

        // if currently empty, first = last
        if (isEmpty()) {
            first = newNode(item);
            last = first;
        }
        else {
            Node oldFirst = first;
            first = newNode(item);
            first.next = oldFirst;
            oldFirst.prev = first;
        }
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("cannot add null last");
        }

        size++;

        // if first item, first = last
        if (isEmpty()) {
            first = newNode(item);
            last = first;
        }
        else {
            Node oldLast = last;
            last = newNode(item);
            oldLast.next = last;
            last.prev = oldLast;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (first != null) {
            size--;
            Item item = first.item;
            first = first.next;
            if (first != null) {
                first.prev = null;
            }
            return item;
        }
        throw new NoSuchElementException("cannot remove empty first");
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (last != null) {
            size--;
            Item item = last.item;
            last = last.prev;
            if (last != null) {
                last.next = null;
            }
            return item;
        }
        throw new NoSuchElementException("cannot remove empty last");
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    //*******************
    // PRIVATE METHODS  *
    //*******************
    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("cannot get next, no more items to return");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    private Node newNode(Item item) {
        Node n = new Node();
        n.item = item;
        n.next = null;
        n.prev = null;
        return n;
    }

    private void reset() {
        if (isEmpty()) {
            return;
        }

        while (first.next != null) {
            first = first.next;
            first.prev = null;
        }
        first = null;
        last = null;
        size = 0;
    }

    private void print() {
        for (Item i : this) {
            StdOut.print(i + " ");
        }
        StdOut.println();
    }

    // unit testing (required)
    public static void main(String[] args) {
        // test call functions here
        Deque<Integer> d = new Deque<Integer>();

        // test initial setup
        assert (d.isEmpty());
        assert (d.size() == 0);
        Integer temp;

        // test 1: 1-2-3 (addFirst, removeFirst)
        StdOut.println("Test 1: addFirst, removeFirst");
        d.addFirst(3);
        assert (d.size() == 1);
        d.addFirst(2);
        d.addFirst(1);
        StdOut.print("Expect 1-2-3: Computed: ");
        d.print();
        temp = d.removeFirst();
        assert (temp == 1);
        StdOut.print("Expect 2-3: Computed: ");
        d.print();
        d.removeFirst();
        StdOut.print("Expect 3: Computed: ");
        d.print();
        assert (d.size() == 1);
        d.removeFirst();
        assert (d.size() == 0);
        assert (d.isEmpty());

        d.reset();
        assert (d.isEmpty());

        // test 2: 1-2-3 (addLast, removeLast)
        StdOut.println("");
        StdOut.println("Test 2: addlast, removeLast");
        assert (d.size() == 0);
        d.addLast(1);
        assert (d.size() == 1);
        d.addLast(2);
        assert (d.size() == 2);
        d.addLast(3);
        StdOut.print("Expect 1-2-3: Computed: ");
        d.print();
        StdOut.print("Expect 1-2: Computed: ");
        d.removeLast();
        d.print();
        StdOut.print("Expect 1: Computed: ");
        d.removeLast();
        d.print();

        StdOut.println("");
        StdOut.println("Test 3: adding lots");
        d.reset();
        for (int i = 0; i < 100; i++) {
            d.addFirst(i);
            d.addLast(i);
        }

        for (int i = 0; i < 100; i++) {
            d.removeLast();
        }
        System.out.println("deque tests passed");
    }
}
