package ru.promo.javacore.task;

import java.lang.reflect.Array;
import java.util.*;

public class ProdigyGirlList<T> implements List<T>, AuthorHolder {
    private T[] array;
    private int capacity;
    private final int LENGTH = 10;

    public ProdigyGirlList() {
        array = (T[])new Object[LENGTH];
        capacity = 0;
    }

    @Override
    public int size() {
        return capacity;
    }

    @Override
    public boolean isEmpty() {
        return capacity == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) > -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new ProdigyGirlList.MyListIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] resultList = new Object[array.length];
        for (int i = 0; i < array.length; i++) {
            resultList[i] = array[i];
        }
        return resultList;
    }

    @Override
    public <T1> T1[] toArray(T1[] t1s) {
        t1s = (T1[])Array.newInstance( t1s.getClass().getComponentType(), array.length);
        for (int i = 0; i < array.length; i++) {
            t1s[i] = (T1) get(i);
        }
        return t1s;
    }

    @Override
    public boolean add(T t) {
        add(capacity, t);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index == -1)
            return false;
        return remove(index) != null;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object o: collection) {
            if (!contains(o))
                return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        for (Object o: collection) {
            add((T) o);
        }
        return true;
    }

    @Override
    public boolean addAll(int i, Collection<? extends T> collection) {
        rangeCheck(i);
        for (Object o: collection) {
            add(i, (T) o);
            i++;
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        for (Object o: collection) {
            remove(o);
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        Object[] tempArray = new Object[array.length];
        for (int i = 0; i < tempArray.length; i++) {
            tempArray[i] = array[i];
        }
        for (Object o: tempArray) {
            if (!collection.contains(o)) {
                remove(o);
            }
        }
        return tempArray.length == array.length;
    }

    @Override
    public void clear() {
        for (int i = 0; i < capacity; i++) {
            array[i] = null;
        }
        capacity = 0;
    }

    @Override
    public T get(int i) {
        rangeCheck(i);
        return (T) array[i];
    }

    @Override
    public T set(int i, T t) {
        rangeCheck(i);
        T curr_elem = (T) array[i];
        array[i] = t;
        return curr_elem;
    }

    @Override
    public void add(int i, T t) {
        rangeCheck(i);
        // если массив уже полон, создаем новый массив большего размера
        if (array.length == capacity) {
            Object[] temp_list = new Object[array.length * 2];
            for (int j = 0; j < array.length; j++) {
                temp_list[j] = array[j];
            }
            array = (T[]) temp_list;
        }
        // сдвиг элементов на одну позицию вправо
        for (int j = capacity; j > i; j--) {
            array[j] = array[j - 1];
        }
        array[i] = t;
        capacity++;
    }

    @Override
    public T remove(int i) {
        rangeCheck(i);
        T removedObj = (T)array[i];
        array[i] = null;
        // сдвиг элементов массива влево после удаления
        for (int j = i + 1; j < capacity; j++) {
            array[j - 1] = array[j];
        }
        array[capacity - 1] = null;
        capacity--;
        return removedObj;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < capacity; i++) {
            if (array[i].equals(o))
                return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = capacity - 1; i >= 0; i--) {
            if (array[i].equals(o))
                return i;
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ProdigyGirlList.MyListIterator();
    }

    @Override
    public ListIterator<T> listIterator(int i) {
        return new ProdigyGirlList.MyListIterator(i);
    }

    @Override
    public List<T> subList(int i, int i1) {
        if (i < 0 || i1 > size() || i > i1)
            return null;
        ProdigyGirlList<T> resultList = new ProdigyGirlList<>();
        for (int j = i; i < i1; i++) {
            resultList.add(get(j));
        }
        return resultList;
    }

    @Override
    public void sort(Comparator<? super T> c) {
        List.super.sort(c);
        quickSort(array, 0, size() - 1, c);
    }
    // сложность O(N*logN) - быстрая сортировка
    public void quickSort(T[] arr, int low, int high, Comparator<? super T> c) {
        if (low >= 0 && high >= 0 && low < high) {
            int p = partition(arr, low, high, c);
            quickSort(arr, low, p - 1, c);
            quickSort(arr, p + 1, high, c);
        }

    }

    private int partition(T[] arr, int low, int high, Comparator<? super T> c) {
        int m = low;
        for (int i = low; i <= high; i++) {
            if (c.compare(arr[i], arr[high]) <= 0) {
                T temp = arr[i];
                arr[i] = arr[m];
                arr[m] = temp;
                m++;
            }
        }
        return m - 1;
    }

    private void rangeCheck(int index) {
        if (index > this.size() || index < 0) {
            throw new IndexOutOfBoundsException(this.outOfBoundsMsg(index));
        }
    }

    private String outOfBoundsMsg(int index) {
        return "Index" + index +" out of bounds for length " + this.size();
    }

    @Override
    public String getAuthor() {
        return "Имя автора: Борщевская Анна";
    }

    private class MyListIterator implements ListIterator<T> {
        int cursor;
        int lastRet = -1;
        MyListIterator() {}
        MyListIterator(int index) {
            super();
            this.cursor = index;
        }
        @Override
        public boolean hasNext() {
            return this.cursor != ProdigyGirlList.this.size();
        }

        @Override
        public T next() {
            int i = this.cursor;
            if (i >= ProdigyGirlList.this.size()) {
                throw new NoSuchElementException();
            } else {
                T[] elementData = ProdigyGirlList.this.array;
                if (i >= elementData.length) {
                    throw new ConcurrentModificationException();
                } else {
                    this.cursor = i + 1;
                    return elementData[this.lastRet = i];
                }
            }
        }

        @Override
        public boolean hasPrevious() {
            return this.cursor != 0;
        }

        @Override
        public T previous() {
            int i = this.cursor - 1;
            if (i < 0) {
                throw new NoSuchElementException();
            } else {
                T[] elementData = ProdigyGirlList.this.array;
                if (i >= elementData.length) {
                    throw new ConcurrentModificationException();
                } else {
                    this.cursor = i;
                    return elementData[this.lastRet = i];
                }
            }
        }

        @Override
        public int nextIndex() {
            return this.cursor;
        }

        @Override
        public int previousIndex() {
            return this.cursor-1;
        }

        @Override
        public void remove() {
            if (this.lastRet < 0) {
                throw new IllegalStateException();
            } else {
                try {
                    ProdigyGirlList.this.remove(this.lastRet);
                    this.cursor = this.lastRet;
                    this.lastRet = -1;

                } catch (IndexOutOfBoundsException var2) {
                    throw new ConcurrentModificationException();
                }
            }
        }

        @Override
        public void set(T t) {
            if (this.lastRet < 0) {
                throw new IllegalStateException();
            } else {
                try {
                    ProdigyGirlList.this.set(this.lastRet, t);
                } catch (IndexOutOfBoundsException var3) {
                    throw new ConcurrentModificationException();
                }
            }
        }

        @Override
        public void add(T t) {
            try {
                int i = this.cursor;
                ProdigyGirlList.this.add(i, t);
                this.cursor = i + 1;
                this.lastRet = -1;
            } catch (IndexOutOfBoundsException var3) {
                throw new ConcurrentModificationException();
            }
        }


    }
}
