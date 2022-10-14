package ru.promo.javacore.task;

import java.util.*;
import java.util.Collection;

public class AntoshkeenList<L> implements List<L>, AuthorHolder {
    L[] myData;

    @Override
    public List<L> subList(int fromIndex, int toIndex) {
        AntoshkeenList<L> list = new AntoshkeenList<>();
        for (int i = fromIndex; i < toIndex; i++) {
            list.add(this.myData[i]);
        }
        return list;
    }

    public AntoshkeenList() {
        Object[] objects = new Object[0];
        this.myData = (L[])objects;
    }


    @Override
    public int size() {
        return this.myData.length;
    }

    @Override
    public boolean add(L l) {
        boolean exists = false;
        L objToAdd = l;

        for (L element : this.myData) {
            if (l.equals(element)) {
                exists = true;
            }
        }
        if (exists) {
            System.out.println("ELEMENT '" + objToAdd + "' ALREADY EXISTS!");
            exists = false;
        }
        else {
            Object[] data = new Object[myData.length + 1];

            for (int i = 0; i < myData.length; i++) {
                data[i] = myData[i];
            }

            data[myData.length] = objToAdd;
            this.myData = (L[]) data;
            exists = true;
            System.out.println("ELEMENT '" + objToAdd + "' SUCCESSFULLY ADDED.");
        }

        return exists;
    }

    @Override
    public boolean isEmpty() {

        if (this.myData.length == 0) {
            System.out.println("THIS LIST IS EMPTY");
            return true;
        } else
            System.out.println("THIS LIST IS NOT EMPTY");
        return false;
    }


    @Override
    public boolean contains(Object o) {

        boolean contains = false;

        for (Object element : myData) {
            if (o.equals(element)) {
                System.out.println("ELEMENT ALREADY EXISTS!");
                contains = true;
            }
        }

        if (contains) {
            return true;
        } else {
            System.out.println("ELEMENT NOT FOUND!");
            return false;
        }

    }

    @Override
    public Object[] toArray() {
        Object[] objects = new Object[this.myData.length];
        for (int i = 0; i < myData.length; i++) {
            objects[i] = this.myData[i];
        }
        return objects;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        T[] myData = a;
        return myData;
    }

    @Override
    public boolean remove(Object o) {

        int len = this.myData.length;
        boolean contains = false;
        int indexToDelete = 0;

        for (int i = 0; i < len; i++) {
            if (o.equals(myData[i])) {
                contains = true;
                indexToDelete = i;
            }
        }

        if (contains) {
            Object[] data = new Object[len - 1];
            for (int i = 0; i < indexToDelete; i++) {
                data[i] = this.myData[i];
            }
            for (int i = indexToDelete; i < len - 1; i++) {
                data[i] = this.myData[i + 1];
            }
            this.myData = (L[]) data;
            return true;
        } else {
            System.out.println("ELEMENT NOT FOUND!");
            return false;
        }
    }

    @Override
    public void clear() {
        Object[] data = new Object[0];
        this.myData = (L[])data;
    }

    @Override
    public L get(int index) {
        L element = (L) this.myData[index];
        return element;
    }

    @Override
    public L set(int index, L element) {
        L obj = element;
        this.myData[index] = obj;
        return obj;
    }

    @Override
    public void add(int index, L element) {

        int len = myData.length;
        Object[] data = new Object[len + 1];
        data[index] = element;

        for (int i = 0; i < index; i++) {
            data[i] = this.myData[i];
        }

        for (int i = index; i < myData.length - 1; i++) {
            data[i + 1] = this.myData[i];
        }

        this.myData = (L[])data;
    }


    @Override
    public L remove(int index) {

        L object = (L) myData[index];
        Object[] data = new Object[myData.length - 1];

        for (int i = 0; i < index; i++) {
            data[i] = this.myData[i];
        }

        for (int i = index; i < myData.length - 1; i++) {
            data[i] = this.myData[i + 1];
        }

        this.myData = (L[])data;
        return object;
    }

    @Override
    public int indexOf(Object o) {
        int index = -1;
        for (int i = 0; i < myData.length; i++) {
            if (o.equals(this.myData[i])) {
                index = i;
                return index;
            }
        }
        return index;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = -1;
        for (int i = myData.length - 1; i >= 0; i--) {
            if (o.equals(this.myData[i])) {
                index = i;
                return index;
            }
        }
        return index;
    }

    public void printElements() {
        System.out.println("--- ELEMENTS ---");
        for (Object i : myData) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    @Override
    public String getAuthor() {
        return "Anton Pyatakov";
    }


    @Override
    public boolean addAll(Collection<? extends L> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends L> c) {
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

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public ListIterator<L> listIterator() {
        return null;
    }

    @Override
    public ListIterator<L> listIterator(int index) {
        return null;
    }

    @Override
    public Iterator<L> iterator() {
        return null;
    }
}

