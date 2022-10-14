package ru.promo.javacore.task.reznikov;


import java.util.Collection;
import java.util.Iterator;

public class ReznikovIterator<T> implements Iterator<T> {

    Node<T> current;
    Collection<T> collection;
    Node<T> lastElem;

    public ReznikovIterator(Node<T> current, Collection<T> collection) {
        this.collection = collection;
        this.current = current;
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public T next() {
        T body = current.getObject();
        lastElem = current;
        current = current.getNextNode();
        return body;
    }

    @Override
    public void remove() {
        collection.remove(lastElem.getObject());
    }
}
