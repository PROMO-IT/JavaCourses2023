package ru.promo.javacore.task;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Consumer;

public class AbakumovaList<T> implements List<T>, AuthorHolder {

    private Object[] list;
    private int size = 0;

    public AbakumovaList(int initialCapacity) throws Exception {
        try {
            list = new Object[initialCapacity];
        } catch (Exception e) {
            throw new Exception("the size must be greater than 0");
        }
    }

    public AbakumovaList() {
        list = new Object[]{};
    }

    @Override
    public int size() {

        return size;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (list[i].equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new AbakumovaItr();
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        for (int i = 0; i < size; i++) {
            result[i] = list[i];
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T1> T1[] toArray(T1[] a) {
        if (a.length >= size) {
            for (int i = 0; i < size; i++) {
                a[i] = (T1) list[i];
            }
            if (a.length > size) {
                a[size] = null;
            }
            return a;
        } else {
            @SuppressWarnings("unchecked") final T1[] result = (T1[]) Array.newInstance(a.getClass().componentType(), size);
            for (int i = 0; i < size; i++) {
                var obj = (T1) list[i];
                result[i] = (T1) list[i];
            }
            return result;
        }
    }

    @Override
    public boolean add(T t) {
        add(size, t);
        return true;
    }

    @Override
    public void add(int index, T element) {
        if (index > list.length - 1) {
            increaseLength();
        }
        for (int i = size; i > index; i--) {
            list[i] = list[i - 1];
        }
        list[index] = element;
        size++;
    }

    @Override
    public boolean remove(Object o) {
        remove(indexOf(o));
        return true;
    }

    @Override
    public T remove(int index) {
        T oldElement = (T) list[index];
        try {
            for (int i = index; i < size - 1; i++) {
                list[i] = list[i + 1];
            }
            list[size - 1] = null;
            size--;
        } catch (Exception e) {
            //TODO Exception
            throw new RuntimeException(e);
        }
        return oldElement;
    }

    @Override
    public boolean containsAll(Collection<?> c) {

        for (var elementC : c) {
            boolean containsElement = false;
            for (int i = 0; i < size; i++) {
                if (list[i].equals(elementC)) {
                    containsElement = true;
                }
            }
            if (!containsElement) return false;
        }
        return true;

    }

    @Override
    public boolean addAll(Collection<? extends T> c) {

        return addAll(size, c);
    }

    private void increaseLength() {

        Object[] list2 = new Object[list.length * 2 + 1];
        for (int i = 0; i < size; i++) {
            list2[i] = list[i];
        }
        list = list2;

    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        while (list.length - 1 < size + c.size()) increaseLength();
        for (int i = size + c.size(); i >= index + c.size(); i--) {
            list[i] = list[i - c.size()];
        }
        for (var elementC : c) {

            list[index] = elementC;
            size++;
            index++;
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean result = false;
        for (var elementC : c) {
            for (int i = 0; i < size; i++) {
                if (list[i].equals(elementC)) {
                    remove(elementC);
                    result = true;
                }
            }
        }
        return result;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean result = false;
        AbakumovaList<T> removeList = new AbakumovaList<T>();
        for (int i = 0; i < size; i++) {
            if (!c.contains(list[i])) {
                removeList.add((T) list[i]);
                result = true;
            }
        }
        removeAll(removeList);
        return result;

    }

    @Override
    public void sort(Comparator<? super T> c) {
        Object[] sortList = new Object[size];
        for (int i = 0; i < size; i++) {
            sortList[i] = list[i];
        }
        list = quickSort((T[]) sortList, 0, size, c);
    }

    private T[] quickSort(T[] array, int low, int high, Comparator<? super T> c) {
        if (array.length == 0) return array;//завершить выполнение, если длина массива равна 0

        if (low >= high) return array;//завершить выполнение если уже нечего делить

        // выбрать опорный элемент
        int middle = low + (high - low) / 2;
        T opora = array[middle];

        // разделить на подмассивы, который больше и меньше опорного элемента
        int i = low, j = high - 1;
        while (i <= j) {
            while (c.compare(array[i], opora) < 0) {
                i++;
            }

            while (c.compare(array[j], opora) > 0) {
                j--;
            }

            if (i <= j) {//меняем местами
                T temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                i++;
                j--;
            }
        }

        // вызов рекурсии для сортировки левой и правой части
        if (low < j) quickSort(array, low, j, c);

        if (high > i) quickSort(array, i, high, c);

        return array;
    }


    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            list[i] = null;
        }
        size = 0;
    }

    @Override
    public T get(int index) {
        //TODO exception
        return (T) list[index];
    }

    @Override
    public T set(int index, T element) {
        T oldElement = (T) list[index];
        list[index] = element;
        return oldElement;
    }


    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (list[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (list[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new AbakumovaItr();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new AbakumovaItr(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        if (fromIndex >= size || fromIndex < 0 || toIndex >= size || toIndex < 0) {
            try {
                //TODO Exception
                throw new Exception("fromIndex and toIndex should be  >=0 and <size");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        List<T> result = null;
        try {
            result = new AbakumovaList<>(toIndex - fromIndex);
            for (int i = fromIndex; i < toIndex; i++) {
                result.add((T) list[i]);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public String getAuthor() {
        return "AbakumovaOlga";
    }

    private class AbakumovaItr implements ListIterator<T> {

        int current = 0;

        public AbakumovaItr(int index) {
            current = index;
        }

        public AbakumovaItr() {
            current = 0;
        }

        @Override
        public boolean hasNext() {
            return current < size;
        }

        @Override
        public T next() {
            if (current < size) {
                return (T) list[current++];
            } else {
                throw new NoSuchElementException();
            }
        }

        @Override
        public boolean hasPrevious() {
            return current != 0;
        }

        @Override
        public T previous() {
            int previous = current - 1;
            if (previous < 0 || previous>size)
                throw new NoSuchElementException();
            return (T)list[--current];
        }

        @Override
        public int nextIndex() {
            return  current;
        }

        @Override
        public int previousIndex() {
            return current -1;
        }

        @Override
        public void remove() {
            //TODO
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(T t) {
            //TODO
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(T t) {
            //TODO
            throw new UnsupportedOperationException();
        }
    }

    @SuppressWarnings("unchecked")
    static <E> E elementAt(Object[] es, int index) {
        return (E) es[index];
    }

}
