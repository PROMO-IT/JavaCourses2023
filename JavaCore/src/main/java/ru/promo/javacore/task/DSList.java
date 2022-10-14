package ru.promo.javacore.task;

import java.util.*;

public class DSList<E> implements List<E>, Iterable<E>, DescendingIterator<E>, AuthorHolder {

    transient int size = 0;
    transient Node firstNode = null;
    transient Node lastNode = null;

    public DSList() {
    }

    @Override
    public Iterator<E> descendingIterator() {
        return new Iterator<>() {
            int counter = size - 1;

            @Override
            public boolean hasNext() {
                return counter >= 0;
            }

            @Override
            public E next() {
                return get(counter--);
            }
        };
    }

    @Override
    public String getAuthor() {
        return "Danil Shakurov";
    }

    private static class Node {
        Object data;
        Node prev;
        Node next;

        Node(Node prev, Object data, Node next) {
            this.prev = prev;
            this.data = data;
            this.next = next;
        }

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
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            int counter = 0;

            @Override
            public boolean hasNext() {
                return counter < size;
            }

            @Override
            public E next() {
                return get(counter++);
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[this.size];
        int i = 0;
        for (Node x = this.firstNode; x != null; x = x.next)
            result[i++] = x.data;
        return result;
    }

    @Override
    public boolean add(Object object) {
        this.addLast(object);
        return true;
    }

    public void addFirst(Object object) {
        Node node = new Node(null, object, null);
        if (this.size == 0) {
            this.firstNode = node;
            this.lastNode = node;
        } else {
            this.firstNode.prev = node;
            node.next = this.firstNode;
            this.firstNode = node;
        }
        this.size++;
    }

    public void addLast(Object object) {
        Node node = new Node(null, object, null);

        if (this.size == 0) {
            this.addFirst(object);
        } else {
            this.lastNode.next = node;
            node.prev = this.lastNode;
            this.lastNode = node;
            this.size++;
        }
    }

    @Override
    public void add(int index, E element) {
        checkElementIndex(index);
        if (index == 0) {
            this.addFirst(element);
        } else {
            Node addNode = new Node(null, element, null);
            Node tempNode = node(index);
            addNode.prev = tempNode.prev;
            addNode.next = tempNode;
            tempNode.prev.next = addNode;
            tempNode.prev = addNode;
            this.size++;
        }
    }

    @Override
    public void addAll(List DSList) {
        for (int i = 0; i < DSList.size(); i++) {
            add(DSList.get(i));
        }
    }

    @Override
    public void addAll(int index, List DSList) {
        checkElementIndex(index);
        DSList tempDSList = new DSList();
        if (index == 0) {
            for (int i = 0; i < DSList.size(); i++) {
                tempDSList.add(DSList.get(i));
            }

            this.firstNode.prev = tempDSList.lastNode;
            tempDSList.lastNode.next = this.firstNode;
            this.firstNode = tempDSList.firstNode;

        } else {
            for (int i = 0; i < DSList.size(); i++) {
                tempDSList.add(DSList.get(i));
            }

            Node tempNode = this.node(index);

            tempDSList.firstNode.prev = tempNode.prev;
            tempDSList.lastNode.next = tempNode;

            tempNode.prev.next = tempDSList.firstNode;
            tempNode.prev = tempDSList.lastNode;

        }
        this.size = this.size + DSList.size();
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    public void removeFirst() {
        Node tempRemoveFirstNode = this.firstNode;
        this.firstNode = tempRemoveFirstNode.next;
        this.firstNode.prev = null;
        tempRemoveFirstNode.next = null;
        tempRemoveFirstNode.data = null;
        this.size--;
    }

    public void removeLast() {
        Node tempRemoveLastNode = this.lastNode;
        this.lastNode = tempRemoveLastNode.prev;
        this.lastNode.next = null;
        tempRemoveLastNode.prev = null;
        tempRemoveLastNode.data = null;
        this.size--;
    }

    @Override
    public void clear() {
        for (Node node = this.firstNode; node != null; ) {
            Node next = node.next;
            node.prev = null;
            node.data = null;
            node.next = null;
            node = next;
        }
        this.firstNode = this.lastNode = null;
        this.size = 0;
    }

    private Node node(int index) {
        checkElementIndex(index);
        int ind = 0;

        Node tempNode = null;
        for (Node n = this.firstNode; n != null; n = n.next) {
            if (ind == index) {
                tempNode = n;
                break;
            }
            ind++;
        }
        return tempNode;
    }

    @Override
    public E get(int index) {
        return (E) this.node(index).data;
    }

    @Override
    public <T> T[] toArray(T[] array) {
        if (array.length != size()) {
            array = Arrays.copyOf(array, size());
        }
        Object[] result = array;
        for (int i = 0; i < size(); i++) {
            result[i] = get(i);
        }
        return array;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public E remove(int index) {
        checkElementIndex(index);
        if (index == 0) {
            this.removeFirst();
        } else if (index == this.size - 1) {
            this.removeLast();
        } else {

            Node tempRemoveNode = this.node(index);
            tempRemoveNode.prev.next = tempRemoveNode.next;
            tempRemoveNode.next.prev = tempRemoveNode.prev;

            tempRemoveNode.prev = null;
            tempRemoveNode.data = null;
            tempRemoveNode.next = null;
            this.size--;

        }
        return null;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    public void printList() {
        StringBuilder list = new StringBuilder("｛");
        for (int i = 0; i < this.size; i++) {
            list.append(this.get(i));
            if (i < this.size - 1) {
                list.append(", ");
            }
        }
        list.append("｝");
        System.out.println(list);
    }

    public E set(int index, E element) {
        checkElementIndex(index);
        this.node(index).data = element;
        return element;
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index)) throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size);
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }
}
