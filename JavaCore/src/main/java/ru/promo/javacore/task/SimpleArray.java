package ru.promo.javacore.task;

import java.util.*;


public class SimpleArray<E> implements List<E>, AuthorHolder {

    private E[] values;

    public SimpleArray() {
        values = (E[]) new Object[0];
    }

    @Override
    public int size() {
        return values.length;
    }

    /* ------------------------------------------------------------------------------------- */
    @Override
    public boolean isEmpty() {
        return values.length == 0;
    }

    /* ------------------------------------------------------------------------------------- */
    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    /* ------------------------------------------------------------------------------------- */
    @Override
    public SimpleIterator<E> iterator() {
        return new SimpleIterator<>(values);
    }

    /* ------------------------------------------------------------------------------------- */
    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < values.length)
            return (T[]) Arrays.copyOf(values, values.length, a.getClass());
        System.arraycopy(values, 0, a, 0, values.length);
        if (a.length > values.length)
            a[values.length] = null;
        return a;
    }

    /* ------------------------------------------------------------------------------------- */
    @Override
    public boolean add(E e) {
        add(this.values.length, e);
        return true;
    }

    /* ------------------------------------------------------------------------------------- */
    @Override
    public boolean remove(Object o) {
        int i = 0;
        E[] temp = (E[]) new Object[this.values.length - 1];

        while (this.values[i] != o) {
            temp[i] = this.values[i];
            i++;
        }
        for (int j = i; j < temp.length; j++) {
            temp[j] = this.values[j + 1];
        }

        this.values = (E[]) new Object[this.values.length - 1];

        for (int c = 0; c < this.values.length; c++) {
            this.values[c] = temp[c];
        }
        return true;
    }

    /* ------------------------------------------------------------------------------------- */
    @Override
    public boolean containsAll(Collection<?> c) {
        //return c.isEmpty();
        for (Object o : c)
            if (!contains(o))
                return false;
        return true;
    }

    /* ------------------------------------------------------------------------------------- */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        addAll(this.values.length, c);
        return true;
    }

    /* ------------------------------------------------------------------------------------- */
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        for (Object elementik : c) {
            add(index, (E) elementik);
        }
        return true;
    }

    /* ------------------------------------------------------------------------------------- */
    @Override
    public boolean removeAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o))
                return false;
            remove(o);
        }
        return true;
    }

    /* ------------------------------------------------------------------------------------- */
    @Override
    public boolean retainAll(Collection<?> c) {

        boolean variable;
        try {
            for (E elementik : this.values) {
                variable = false;

                for (Object o : c) {
                    if (elementik == o)
                        variable = true;
                }
                if (!variable)
                    remove(elementik);
            }
            return true;
        } catch (ClassCastException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /* ------------------------------------------------------------------------------------- */
    @Override
    public void clear() {
        this.values = (E[]) new Object[0];
    }

    /* ------------------------------------------------------------------------------------- */
    @Override
    public E set(int index, E element) {
        this.values[index] = element;
        return null;
    }

    /* ------------------------------------------------------------------------------------- */
    @Override
    public void add(int index, E element) {
        E[] temp = (E[]) new Object[this.values.length + 1];

        for (int i = 0; i < index; i++) {
            temp[i] = this.values[i];
        }

        temp[index] = element;

        if (index != temp.length - 1) {
            for (int i = index; i < temp.length; i++) {
                temp[i] = this.values[i];
            }
        }

        this.values = (E[]) new Object[this.values.length + 1];

        for (int i = 0; i < this.values.length; i++) {
            this.values[i] = temp[i];
        }
    }

    /* ------------------------------------------------------------------------------------- */
    @Override
    public E get(int index) {
        return this.values[index];
    }

    /* ------------------------------------------------------------------------------------- */
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(values, values.length);
    }

    /* ------------------------------------------------------------------------------------- */
    @Override
    public E remove(int index) {
        E[] temp = (E[]) new Object[this.values.length - 1];

        for (int i = 0; i < index; i++) {
            temp[i] = this.values[i];
        }

        for (int i = index; i < temp.length; i++) {
            temp[i] = this.values[i + 1];
        }

        this.values = (E[]) new Object[this.values.length - 1];
        for (int i = 0; i < this.values.length; i++) {
            this.values[i] = temp[i];
        }
        return null;
    }

    /* ------------------------------------------------------------------------------------- */
    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < this.values.length; i++) {
            if (this.values[i] == o)
                return i;
        }
        return -1;
    }

    /* ------------------------------------------------------------------------------------- */
    @Override
    public int lastIndexOf(Object o) {
        for (int i = values.length - 1; i >= 0; i--) {
            if (this.values[i] == o)
                return i;
        }
        return -1;
    }

    /* ------------------------------------------------------------------------------------- */
    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    /* ------------------------------------------------------------------------------------- */
    @Override
    public ListIterator<E> listIterator(int index) {
        return null;

    }

    /* ------------------------------------------------------------------------------------- */
    @Override
    public SimpleArray<E> subList(int fromIndex, int toIndex) {
        /*У интерфейса List есть метод subList, который позволяет работать с подсписком.*/
        /*List subList(int fromIndex, int toIndex) — toIndex не включается*/

        SimpleArray<E> subTestListik = new SimpleArray<>();
        for (int i = fromIndex; i < toIndex; i++) {
            subTestListik.add(this.values[i]);
        }
        return subTestListik;
    }

    /* ------------------------------------------------------------------------------------- */
    @Override
    public String getAuthor() {
        return "Andrey Paklin.";
    }
}
/* ------------------------------------------------------------------------------------- */
