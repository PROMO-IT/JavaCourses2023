package ru.promo.javacore.task;

import java.lang.reflect.Array;
import java.util.*;

public class NatalyaList<T> implements List<T>, AuthorHolder, Iterable<T> {
    private final int DEFAULT_CAPACITY = 16;
    private int capacity;
    private Object[] array;
    private int size = 0;

    public NatalyaList() {
        capacity = DEFAULT_CAPACITY;
        array = new Object[capacity];
    }

    public NatalyaList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + capacity);
        }
        this.capacity = capacity;
        array = new Object[capacity];
    }

    private void increaseCapacity() {
        capacity = capacity * 2;
        Object[] newArray = new Object[capacity];
        System.arraycopy(array, 0, newArray, 0, array.length - 1);
        array = newArray;
    }

    private void isIndexValid(int index) {
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Object[] trimArr() {
        if (size < array.length) {
            Object[] objects = new Object[size];
            System.arraycopy(array, 0, objects, 0, size);
            return objects;
        }
        return array;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (Object arr : array) {
            if (o.equals(arr)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        Iterator<T> iterator = new Iterator<T>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index + 1 <= size;
            }

            @SuppressWarnings("unchecked")
            @Override
            public T next() {
                return (T) array[index++];
            }
        };
        return iterator;
    }

    @Override
    public Object[] toArray() {
        return array;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T1> T1[] toArray(T1[] a) {
        T1[] copy = (T1[]) Array.newInstance(a.getClass().getComponentType(), size);
        System.arraycopy(array, 0, copy, 0, size);
        return copy;
    }

    @Override
    public boolean add(T t) {
        if (size == array.length) {
            increaseCapacity();
        }
        array[size] = t;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (o.equals(array[i])) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            System.arraycopy(array, index + 1, array, index, size - index - 1);
            size--;
            return true;
        } else return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object obj : c) {
            if (!this.contains(obj)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        Object[] collection = c.toArray();
        if (array.length - size < collection.length) {
            Object[] newArr = new Object[size + collection.length];
            System.arraycopy(array, 0, newArr, 0, size);
            System.arraycopy(collection, 0, newArr, size, collection.length);
            array = newArr;
        } else {
            System.arraycopy(collection, 0, array, size, collection.length);
        }
        size += collection.length;
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        isIndexValid(index);
        Object[] collection = c.toArray();

        Object[] newArr = new Object[size + collection.length];
        System.arraycopy(array, 0, newArr, 0, index + 1);
        System.arraycopy(collection, 0, newArr, index + 1, collection.length);
        System.arraycopy(array, index + 1, newArr, index + 1 + collection.length, size - 1 - index);
        array = newArr;

        size += collection.length;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Object[] collection = c.toArray();
        int newSize = size;
        for (Object obj : collection) {
            for (int i = 0; i < size; i++) {
                if (obj.equals(array[i])) {
                    for (int j = i + 1; j <= size; j++) {
                        array[j - 1] = array[j];
                    }
                    newSize--;
                }
            }
        }
        size = newSize;
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Object[] collection = c.toArray();
        Object[] newArr = new Object[size];
        int index = 0;
        for (Object obj : collection) {
            for (int i = 0; i < size; i++) {
                if (obj.equals(array[i])) {
                    newArr[index] = array[i];
                    index++;
                }
            }
        }
        array = newArr;
        size = index;
        return true;
    }

    @Override
    public void clear() {
        capacity = DEFAULT_CAPACITY;
        array = new Object[capacity];
        size = 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T get(int index) {
        isIndexValid(index);
        return (T) array[index];
    }

    @SuppressWarnings("unchecked")
    @Override
    public T set(int index, T element) {
        isIndexValid(index);
        array[index] = element;
        return (T) array[index];
    }

    @Override
    public void add(int index, T element) {
        isIndexValid(index);
        if (size + 1 > capacity) {
            increaseCapacity();
        }
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = element;
        size++;
    }

    @Override
    public T remove(int index) {
        isIndexValid(index);
        System.arraycopy(array, index + 1, array, index, size - index);
        size--;
        return null;
    }

    @Override
    public int indexOf(Object o) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (o.equals(array[i])) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = -1;
        for (int i = size - 1; i > 0; i--) {
            if (o.equals(array[i])) {
                index = i;
                break;
            }
        }
        return index;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ListIterator<T> listIterator() {
        ListIterator<T> listIterator = new ListIterator<T>() {
            int firstIndex = 0;
            int lastIndex = size - 1;
            int currentIndex;

            @Override
            public boolean hasNext() {
                return firstIndex + 1 <= size;
            }

            @Override
            public T next() {
                currentIndex = firstIndex;
                return (T) array[firstIndex++];
            }

            @Override
            public boolean hasPrevious() {
                return lastIndex - 1 >= 0;
            }

            @Override
            public T previous() {
                currentIndex = lastIndex;
                return (T) array[lastIndex--];
            }

            @Override
            public int nextIndex() {
                return firstIndex;
            }

            @Override
            public int previousIndex() {
                return lastIndex;
            }

            @Override
            public void remove() {
                System.arraycopy(array, currentIndex + 1, array, currentIndex, size - currentIndex);
                size--;
            }

            @Override
            public void set(T t) {
                array[currentIndex] = t;
            }

            @Override
            public void add(T t) {
                if (size + 1 > capacity) {
                    increaseCapacity();
                }
                System.arraycopy(array, currentIndex + 1, array, currentIndex + 2, size - currentIndex - 1);
                array[currentIndex + 1] = t;
                size++;
            }
        };
        return listIterator;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ListIterator<T> listIterator(int index) {

        isIndexValid(index);

        ListIterator<T> listIterator = new ListIterator<T>() {

            int currentIndex = index;

            @Override
            public boolean hasNext() {
                return currentIndex + 1 < size;
            }

            @Override
            public T next() {
                if (hasNext()) {
                    return (T) array[currentIndex++];
                }
                return (T) array[currentIndex];
            }

            @Override
            public boolean hasPrevious() {
                return currentIndex - 1 >= 0;
            }

            @Override
            public T previous() {
                return (T) array[--currentIndex];
            }

            @Override
            public int nextIndex() {
                return currentIndex;
            }

            @Override
            public int previousIndex() {
                return currentIndex - 1;
            }

            @Override
            public void remove() {
                System.arraycopy(array, currentIndex + 1, array, currentIndex, size - currentIndex);
                size--;
            }

            @Override
            public void set(T t) {
                array[currentIndex] = t;
            }

            @Override
            public void add(T t) {
                if (size + 1 > capacity) {
                    increaseCapacity();
                }
                System.arraycopy(array, currentIndex, array, currentIndex + 1, size - currentIndex);
                array[currentIndex] = t;
                size++;
            }
        };
        return listIterator;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException("fromIndex(" + fromIndex + ") > toIndex(" + toIndex + ")");
        }
        isIndexValid(fromIndex);
        isIndexValid(toIndex);
        Object[] newArray = new Object[toIndex + 1 - fromIndex];
        System.arraycopy(array, fromIndex, newArray, 0, toIndex + 1 - fromIndex);
        array = newArray;
        size -= size - (toIndex + 1 - fromIndex);
        return this;
    }

    @Override
    public String toString() {
        return Arrays.toString(trimArr());
    }

    @Override
    public String getAuthor() {
        return "Аркадьева Наталья";
    }

    @Override
    public void sort(Comparator<? super T> c) {
        T[] arr = (T[]) array;
        quickSort(c, arr, 0, size - 1);
    }

    private void quickSort(Comparator<? super T> c, T[] arr, int firstIndex, int lastIndex) {
        if (firstIndex >= lastIndex) return;
        int middleIndex = new Random().nextInt(lastIndex - firstIndex) + firstIndex;
        T middle = arr[middleIndex];
        swap(arr, middleIndex, lastIndex);

        int leftPoint = partition(c, arr, firstIndex, lastIndex, middle);

        quickSort(c, arr, firstIndex, leftPoint - 1);
        quickSort(c, arr, leftPoint + 1, lastIndex);
    }

    private void swap(T[] arr, int firstIndex, int lastIndex) {
        T help = arr[firstIndex];
        arr[firstIndex] = arr[lastIndex];
        arr[lastIndex] = help;
    }

    private int partition(Comparator<? super T> c, T[] arr, int firstIndex, int lastIndex, T middle) {
        int rightPoint = lastIndex;
        int leftPoint = firstIndex;
        while (leftPoint < rightPoint) {
            while (c.compare(arr[leftPoint], middle) <= 0 && leftPoint < rightPoint) {
                leftPoint++;
            }
            while (c.compare(arr[rightPoint], middle) >= 0 && leftPoint < rightPoint) {
                rightPoint--;
            }
            swap(arr, leftPoint, rightPoint);
        }
        if (c.compare(arr[leftPoint], arr[lastIndex]) > 0) {
            swap(arr, leftPoint, lastIndex);
        } else {
            leftPoint = lastIndex;
        }
        return leftPoint;
    }
}
