package ru.promo.javacore.task;

import java.util.Arrays;
import java.util.List;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.ListIterator;
import java.util.Iterator;

public class Nail73List<T> implements List<T>, AuthorHolder {
    private T[] item = (T[]) new Object[1];
    private int size;

    public Nail73List() {
    }

    public Nail73List(T[] list) {
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (item[i].equals(o)) return true;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new ElementsIterator();
    }

    @Override
    public Object[] toArray() {
        T[] newItem = (T[]) new Object[this.size()];
        System.arraycopy(item, 0, newItem, 0, this.size());
        return newItem;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < size) return (T1[]) Arrays.copyOf(item, size, a.getClass());
        System.arraycopy(item, 0, a, 0, size);
        if (a.length > size) a[size] = null;
        return a;
    }

    @Override
    public boolean add(T t) {
        if (item.length == size) {
            final T[] oldItem = item;
            item = (T[]) new Object[this.size() * 2];
            System.arraycopy(oldItem, 0, item, 0, oldItem.length);
        }
        item[size++] = t;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size(); i++) {
            if (item[i].equals(o)) {
                this.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (final Object item : c) {
            if (!this.contains(item)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (final T item : c) {
            add(item);
        }
        return true;
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        for (Object item : c) {
            remove(item);
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        for (T item : this) {
            if (!c.contains(item)) this.remove(item);
        }
        return true;
    }

    @Override
    public void clear() {
        item = (T[]) new Object[1];
        size = 0;
    }

    @Override
    public T remove(int index) {
        T element = item[index];
        if (index != this.size() - 1) System.arraycopy(item, index + 1, item, index, this.size() - index - 1);
        size--;
        return element;
    }

    @Override
    public List<T> subList(int start, int end) {
        if (start < 0 || end > size || start > end) throw new IndexOutOfBoundsException();
        List<T> list = new Nail73List<>();
        list.addAll(Arrays.asList(item).subList(start, end));
        return list;
    }

    @Override
    public ListIterator listIterator() {
        return new ElementsIterator(0);
    }

    @Override
    public ListIterator listIterator(int index) {
        return new ElementsIterator(index);
    }

    @Override
    public int lastIndexOf(Object target) {
        for (int i = size() - 1; i >= 0; i--) {
            if (item[i].equals(target)) return i;
        }
        return -1;
    }

    @Override
    public int indexOf(Object target) {
        for (int i = 0; i < size(); i++) {
            if (item[i].equals(target)) return i;
        }
        return -1;
    }

    @Override
    public void add(int index, T element) {
        if (index < 0 || index > size()) throw new IndexOutOfBoundsException();
        if (item.length == size) {
            final T[] oldM = item;
            item = (T[]) new Object[this.size() * 2];
            System.arraycopy(oldM, 0, item, 0, oldM.length);
        }
        if (index == size()) {
            item[size++] = element;
        } else {
            System.arraycopy(item, index, item, index + 1, size() - index);
            item[index] = element;
            size++;
        }
    }

    @Override
    public boolean addAll(int index, Collection elements) {
        int prevSize = size();
        if (index < 0 || index > size()) throw new IndexOutOfBoundsException();
        Iterator elementsIterator = elements.iterator();
        for (int i = index; i < index + elements.size(); i++) {
            add(i, (T) elementsIterator.next());
        }
        return size() > prevSize;
    }

    @Override
    public T set(int index, T element) {
        item[index] = element;
        return element;
    }

    @Override
    public T get(int index) {
        return item[index];
    }

    @Override
    public String toString() {
        return Arrays.toString(item);
    }

    @Override
    public String getAuthor() {
        return "Nail Fakhrtdinov";
    }

    private class ElementsIterator implements ListIterator<T> {
        private int index;
        private int last = -1;

        public ElementsIterator() {
            this(0);
        }

        public ElementsIterator(final int index) {
            this.index = index;
        }

        @Override
        public boolean hasNext() {
            return index < Nail73List.this.size();
        }

        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            last = index;
            return Nail73List.this.item[index++];
        }

        @Override
        public void add(T element) {
            if (last == -1) throw new IllegalStateException();
            Nail73List.this.add(index, element);
            last = -1;
        }

        @Override
        public void set(T element) {
            if (last == -1) throw new IllegalStateException();
            Nail73List.this.set(last, element);
        }

        @Override
        public int previousIndex() {
            if (index == 0) return -1;
            return index - 1;
        }

        @Override
        public int nextIndex() {
            return index;
        }

        @Override
        public boolean hasPrevious() {
            return index > 0;
        }

        @Override
        public T previous() {
            if (!hasPrevious()) throw new NoSuchElementException();
            int prevIndex = previousIndex();
            last = prevIndex;
            index--;
            return Nail73List.this.item[prevIndex];
        }

        @Override
        public void remove() {
            if (last == -1) throw new IllegalStateException();
            Nail73List.this.remove(last);
            index--;
            last = -1;
        }
    }
}
