package ru.promo.javacore.task;

import java.lang.reflect.Array;
import java.util.*;

public class KondorList<T> implements List<T>, AuthorHolder {
    //    private Node<T> next;
//    private Node<T> prev;
    private KondorNode<T> first;
    private KondorNode<T> last;

    private int size = 0;

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size(); i++) {
            if (get(i).equals(o)) return true;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private KondorNode<T> current = first;

            @Override
            public boolean hasNext() {
                if(current == null) {
                    return false;
                }
                else {
                    return true;
                }
            }

            @Override
            public T next() throws IndexOutOfBoundsException {
                T result = current.getItem();

                return result;
            }
        };

        //return null;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];

        for (int i = 0; i < size(); i++) {
            array[i] = getByIndex(i).getItem();
        }

        return array;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return (T1[]) Array.newInstance(Object.class, size());
    }

    @Override
    public boolean add(T t) {
        KondorNode<T> node = new KondorNode<>(t);

        if (size == 0) {
            first = node;
        } else {
            KondorNode<T> prevItem = last;
            prevItem.setNext(node);
        }

        last = node;
        size++;

        return true;
    }

    @Override
    public boolean remove(Object o) {

        KondorNode<T> current = first;
        KondorNode<T> prev = null;

        if (!isEmpty()) {
            return false;
        }

        while (current != null)
        {
            if (current.getItem().equals(o))
            {
                // Узел в середине или в конце.
                if (prev != null)
                {
                    prev.setNext(current.getNext());
                    if (current.getItem() == null) last = prev;
                }
                else
                {
                    first = first.getNext();
                    if (first == null) last = null;
                }

                size--;

                return true;
            }

            prev = current;
            current = current.getNext();
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object item : c) {
            if (!contains(item)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (T item : c) {
            add(item);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for (Object item : c) {
            remove(item);
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        for (Object item : this) {
            if (!c.contains(item)) this.remove(item);
        }
        return true;
    }

    @Override
    public void clear() {
        first = null;
        last = null;
        size = 0;
    }

    private KondorNode<T> getByIndex(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }

        KondorNode<T> node = first;
        for (int i = 0; i < index; i++)
        {
            node = node.getNext();
        }

        return node;
    }

    @Override
    public T get(int index) {
        int i = 0;
        for (KondorNode<T> node = first; node != null; node = node.getNext()) {
            if (i == index) {
                return node.getItem();
            }
            i++;
        }

        return null;
    }

    @Override
    public T set(int index, T element) {
        KondorNode<T> node = first;;

        int i = 0;
        while (i < index)
        {
            node = node.getNext();
            i++;
        }

        T prevItem = node.getItem();
        node.setItem(element);

        return prevItem;
    }

    @Override
    public void add(int index, T element) {
        KondorNode<T> node = new KondorNode<>(element);
        node.setNext(null);

        if (size == 0) {
            first = node;
        } else {
            last = first;
            KondorNode<T> prevItem = last;

            while (prevItem.getNext() != null) {
                prevItem = prevItem.getNext();
            }
            prevItem.setNext(node);
        }
        size++;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }

        int i = 0;
        for (KondorNode<T> node = first.getNext(); node != last; node = node.getNext()) {
            if (i == index) {
                T removedItem = node.getItem();
                remove(removedItem);
                return removedItem;
            }
            i++;
        }

        return null;
    }

    @Override
    public int indexOf(Object o) {
        int i = 0;
        for (KondorNode<T> node = first; node != null; node = node.getNext()) {
            if (node.getItem().equals(o)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int i = size;
        for (KondorNode<T> node = last; node != null; node = node.getPrev()) {
            if (node.getItem().equals(o)) {
                return i;
            }
            i--;
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        return null;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return null;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public String getAuthor() {
        return "Ulianin Artyom";
    }

    public <T> void sort(List<T> list, Comparator<? super T> c) {
        T[] arr = (T[]) list.toArray();
        int j;
        for (int i = 0; i < arr.length; i++) {
            T obj = arr[i];
            for (j = i; j > 0 && !obj.equals(arr[j - 1]); j--) {
                arr[j] = arr[j - 1];
            }
            arr[j] = obj;
        }

        //List.super.sort(c);
    }
}
