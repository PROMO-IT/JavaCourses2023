package ru.promo.javacore.task;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.UnaryOperator;


public class DonnaQuixoteList<E> implements List<E>, AuthorHolder {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    @Override
    public boolean add(E e) {
        linkLast(e);
        return true;
    }

    private void linkLast(E e) {
        if (head == null) {
            head = tail = new Node<>(e);
        } else {
            Node<E> newNode = new Node<>(e, null, tail);
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
    }

    @Override
    public E get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        return node(index).value;
    }

    @Override
    public E remove(int index) {
        if(index >= size || index < 0)
            throw new IndexOutOfBoundsException();
        if (index == 0) {
            E value = head.value;
            Node<E> prev = head.next;
            prev.prev = null;
            head = head.next;
            size--;
            return value;
        } else {
            return removeBefore(index);
        }
    }

    private E removeBefore(int index) {
        Node<E> temp = node(index - 1);
        E value = temp.next.value;
        temp.setNext(temp.next.next);
        if(temp.next != null) {
            Node<E> prev = temp.next;
            prev.prev = temp;
        }
        size--;
        return value;
    }

    @Override
    public int indexOf(Object o) {
        int idx = 0;
        Node<E> temp = head;
        while (temp != null) {
            if (o.equals(temp.value)) {
                return idx;
            }
            temp = temp.next;
            idx++;
        }
        return -1;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    @Override
    public E set(int index, E element) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        Node<E> temp = node(index);
        E oldValue = temp.value;
        temp.value = element;
        return oldValue;
    }

    private Node<E> node(int index) {
        Node<E> temp = head;
        if (index < (size >> 1)) {
            for (int i = 0; i < index; i++) {
                temp = temp.next;
            }
        } else {
            temp = tail;
            for (int i = size - 1; i > index; i--) {
                temp = temp.prev;
            }
        }
        return temp;
    }
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public void add(int index, E element) {
        if ((index >= size & size != 0) || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        Node<E> temp = new Node<>(element);
        if (index == (size - 1) || size == 0) {
            add(element);
        } else {
            linkBefore(index, temp);
        }
    }

    private void linkBefore(int index, Node<E> temp) {
        if (index == 0) {
            temp.next = head;
            head.prev = temp;
            head = temp;
        } else {
            Node<E> current = node(index);
            Node<E> pred = current.prev;
            temp.prev = pred;
            temp.next = current;
            current.prev = temp;
            pred.next = temp;
        }
        size++;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            E temp = get(i);
            if (o.equals(temp)) return i;
        }
        return -1;
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        int idx = 0;
        Node<E> temp = head;

        while (temp != null) {
            result[idx++] = temp.value;
            temp = temp.next;

        }
        return result;
    }

    @Override
    public void clear() {
        Node<E> temp = this.head;
        while (temp != null) {
            Node<E> next = temp.next;
            temp.value = null;
            temp.prev = null;
            temp.next = null;
            temp = next;
        }
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index == -1) {
            return false;
        }
        remove(index);
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) if (!contains(o)) return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E e : c) add(e);
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        int counter = 0;
        for (E e : c) {
            add(index + counter, e);
            counter++;
        }
        return true;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new MyListIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new MyListIterator(index);
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator<>(head);
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < this.size) {
            a = (T[]) Array.newInstance(a.getClass().getComponentType(), size());
        }
        Node<E> temp = this.head;
        int idx = 0;
        while (idx < this.size) {
            a[idx] = (T) temp.value;
            idx++;
            temp = temp.next;
        }
        return a;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public void sort(Comparator<? super E> c) {
        Object[] o = this.toArray();
        Arrays.sort(o, (Comparator<Object>) c);
        ListIterator<E> i = this.listIterator();
        for (Object e : o) {
            i.next();
            i.set((E) e);
        }
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for (Object o : c) if (contains(o)) remove(o);
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        ListIterator<E> itr = listIterator();
        while(itr.hasNext()) {
            if(!c.contains(itr.next())) {
                itr.remove();
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        List.super.replaceAll(operator);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        DonnaQuixoteList<E> newList = new DonnaQuixoteList<>();
        for (int i = fromIndex; i < toIndex; i++) {
            newList.add(this.node(i).value);
        }
        return newList;
    }

    @Override
    public String toString() {
        return Arrays.toString(toArray());
    }

    private static class Node<E> {
        private E value;
        private Node<E> next;
        private Node<E> prev;

        public Node(E value, Node<E> next, Node<E> prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }

        public Node(E value) {
            this.value = value;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }
    }

    private class MyListIterator implements ListIterator<E> {
        private Node<E> currentNode = head;
        private Node<E> next;
        private int index;

        MyListIterator(int index) {
            next = (index == size) ? null : node(index);
            this.index = index;
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            currentNode = next;
            next = next.next;
            index++;
            return currentNode.value;
        }

        @Override
        public boolean hasPrevious() {
            return index > 0;
        }

        @Override
        public E previous() {
            if (!hasPrevious()) throw new NoSuchElementException();
            E temp = currentNode.value;
            currentNode = currentNode.prev;
            index--;
            return temp;
        }

        @Override
        public int nextIndex() {
            return index;
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public void remove() {
            if (currentNode == null) throw new IllegalStateException();
            if (index - 1 == 0) {
                Node<E> prev = head.next;
                prev.prev = null;
                head = head.next;
                size--;
            } else removeBefore(index-1);
            index--;
        }

        @Override
        public void set(E e) {
            if(currentNode == null) throw new IllegalStateException();
            currentNode.value = e;
        }

        @Override
        public void add(E e) {
            if(next == null) {
                linkLast(e);
            } else {
                Node<E> temp = new Node<>(e);
                linkBefore(index, temp);
            }
        }
    }

    private static class MyIterator<E> implements Iterator<E> {
        private Node<E> currentNode;

        public MyIterator(Node<E> head) {
            currentNode = head;
        }

        @Override
        public boolean hasNext() {
            return currentNode != null;

        }

        @Override
        public E next() {
            if (currentNode == null) throw new NoSuchElementException();
            E value = currentNode.value;
            currentNode = currentNode.next;
            return value;
        }
    }

    @Override
    public String getAuthor() {
        return "Alina Pronina";
    }
}