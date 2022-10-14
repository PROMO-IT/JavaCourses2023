package ru.promo.javacore.task.reznikov;

import java.util.List;
import java.util.ListIterator;

public class ReznikovListIterator<T> implements ListIterator<T> {

    ReznikovCursor cursor;
    List<T> collection;


    public ReznikovListIterator(Node<T> current, List<T> collection) {
        this.collection = collection;
        cursor = new ReznikovCursor(current, collection.indexOf(current.getObject()));
    }

    @Override
    public boolean hasNext() {
        return cursor.next != null;
    }

    @Override
    public T next() {
        T body = cursor.next.getObject();
        cursor.forward();
        return body;
    }

    @Override
    public boolean hasPrevious() {
        return cursor.previous != null;
    }

    @Override
    public T previous() {
        T body = cursor.previous.getObject();
        cursor.back();
        return body;
    }

    @Override
    public int nextIndex() {
        return cursor.index;
    }

    @Override
    public int previousIndex() {
        return cursor.index - 1;
    }

    @Override
    public void remove() {
        if (cursor.previous == cursor.last) {
            cursor.previous = cursor.previous.getPreviousNode();
            cursor.index--;
            collection.remove(cursor.last.getObject());
            cursor.last = null;
        } else if (cursor.next == cursor.last) {
            cursor.next = cursor.next.getNextNode();
            collection.remove(cursor.last.getObject());
            cursor.last = null;
        }
    }

    @Override
    public void set(T t) {
        cursor.last.setObject(t);
    }

    @Override
    public void add(T t) {
        collection.add(cursor.index, t);
        if (cursor.next != null) {
            cursor.previous = cursor.next.getPreviousNode();
        }
        else if (cursor.previous != null) {
            cursor.previous = cursor.previous.getNextNode();
        }
        cursor.index++;
    }

    private class ReznikovCursor {
        Node<T> previous;
        Node<T> next;
        Node<T> last;
        int index;

        ReznikovCursor(Node<T> next, int index) {
            this.next = next;
            this.previous = next.getPreviousNode();
            this.index = index;
        }

        void forward() {
            previous = next;
            next = next.getNextNode();
            last = previous;
            index++;
        }

        void back() {
            next = previous;
            previous = previous.getPreviousNode();
            last = next;
            index--;
        }
    }
}
