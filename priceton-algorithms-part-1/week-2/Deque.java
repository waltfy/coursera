import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int count;

    private class Node {
        private Item data;
        private Node next;
        private Node previous;

        public Node(Item item) {
            this.data = item;
            this.next = null;
            this.previous = null;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node node) {
            this.next = node;
        }

        public Node getPrevious() {
            return previous;
        }

        public void setPrevious(Node node) {
            this.previous = node;
        }

        public Item getItem() {
            return data;
        }

        public void setItem(Item item) {
            this.data = item;
        }
    }

    // construct an empty deque
    public Deque() {
        this.first = null;
        this.last = null;
        this.count = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return this.count == 0;
    }

    // return the number of items on the deque
    public int size() {
        return this.count;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node oldFirst = this.first;
        Node toBeAdded = new Node(item);
        this.first = toBeAdded;

        if (this.isEmpty()) {
            this.last = first;
        }
        else {
            first.setNext(oldFirst);
            oldFirst.setPrevious(first);
        }
        count++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node oldLast = last;
        Node toBeAdded = new Node(item);
        this.last = toBeAdded;

        if (this.isEmpty()) {
            this.first = this.last;
        }
        else {
            this.last.setPrevious(oldLast);
            oldLast.setNext(this.last);
        }
        count++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }

        Item result = first.getItem();
        if (this.count == 1) {
            this.first = null;
            this.last = null;
        }
        else {
            first = first.getNext();
            first.setPrevious(null);
        }
        count--;
        return result;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        Item result = last.getItem();
        if (this.count == 1) {
            this.first = null;
            this.last = null;
        }
        else {
            last = last.getPrevious();
            last.setNext(null);
        }
        count--;
        return result;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // - throws wrong exception when calling next() when iterator is exhausted
    // - throws a java.lang.NullPointerException
    // - should throw a java.util.NoSuchElementException

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.getItem();
            current = current.getNext();
            return item;
        }
    }
}