/** A linked queue skeleton. */
import java.util.ArrayList;
import java.util.List;

/** Simple singly linked queue used by the snake model. */
public class LinkedQueue<T> {
    private Node<T> tail; // newest element
    private Node<T> head; // oldest element
    private int size;

    /** Adds an item to the tail of the queue. */
    public void enqueue(T item) {
        Node<T> node = new Node<>();
        node.item = item;
        if (tail == null) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    /** Removes and returns the head element, or null if empty. */
    public T dequeue() {
        if (head == null) {
            return null;
        }
        T item = head.item;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        size--;
        return item;
    }

    /** Returns the head element without removing it. */
    public T peek() {
        return (head == null) ? null : head.item;
    }

    /** Returns true if the queue contains no elements. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Returns a list view of the queue in iteration order (head → tail). */
    public List<T> toList() {
        List<T> list = new ArrayList<>(size);
        Node<T> cur = head;
        while (cur != null) {
            list.add(cur.item);
            cur = cur.next;
        }
        return list;
    }

    /** Returns the number of elements in the queue. */
    public int getSize() {
        return size;
    }

    /** Queue node. */
    private static class Node<T> {
        private T item;
        private Node<T> next;
    }
}