import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int count;
    private int capacity;
    private Item[] queue;

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.count = 0;
        this.capacity = 1;
        this.queue = (Item[]) new Object[this.capacity];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return this.count == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return this.count;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        this.queue[this.count] = item;
        this.count += 1;

        // NOTE: If we have reached capacity, increase the size of the array
        if (this.count == this.capacity) {
            this.capacity = 2 * this.capacity;
            Item[] expandedQueue = (Item[]) new Object[this.capacity];
            for (int i = 0; i < this.size(); i++) {
                expandedQueue[i] = this.queue[i];
            }
            this.queue = expandedQueue;
        }
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int randomIndex = StdRandom.uniform(this.count);
        Item result = this.queue[randomIndex];
        int lastIndex = this.count - 1;

        // NOTE: Swapping randomIndex with last index and clearing it from memory
        this.queue[randomIndex] = this.queue[lastIndex];
        this.queue[lastIndex] = null;
        count--;

        // NOTE: If we have reached a fraction of the capacity, reduce the size of the array
        if ((this.capacity / 4) == this.count) {
            this.capacity = this.capacity / 2;
            Item[] contractedQueue = (Item[]) new Object[this.capacity];
            for (int i = 0; i < size(); i++) {
                contractedQueue[i] = this.queue[i];
            }
            this.queue = contractedQueue;
        }
        return result;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int randomIndex = StdRandom.uniform(this.count);
        return this.queue[randomIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        // Store Items in a ResizingArray
        private int iteratorCount = count;
        private final Item[] copy;

        /**
         * Copy the Items in the original array to the Iterator.
         */
        private RandomizedQueueIterator() {
            copy = (Item[]) new Object[count];
            for (int i = 0; i < count; i++) copy[i] = queue[i];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public boolean hasNext() {
            return this.iteratorCount > 0;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int randomIndex = StdRandom.uniform(this.iteratorCount);
            Item result = copy[randomIndex];

            copy[randomIndex] = copy[this.iteratorCount - 1];
            copy[this.iteratorCount - 1] = null;
            this.iteratorCount--;

            return result;
        }
    }
}