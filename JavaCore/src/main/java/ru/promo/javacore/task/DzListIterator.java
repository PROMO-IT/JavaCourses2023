package ru.promo.javacore.task;

import java.util.Iterator;

public class DzListIterator<E> implements Iterator{

    private int index = 0;
    E[] values;

    public DzListIterator(E[] values) {
        this.values = values;
    }

    @Override
    public boolean hasNext() {
        return index < values.length;
    }

    @Override
    public Object next() {
        return values[index++];
    }
}
