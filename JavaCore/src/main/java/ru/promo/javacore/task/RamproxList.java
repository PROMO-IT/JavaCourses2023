package ru.promo.javacore.task;

import java.lang.reflect.Array;
import java.util.*;

public class RamproxList<T> implements List<T>, AuthorHolder {

    private T[] items;

    private int size;

    private final int maxCapacity;

    private static final int DEFAULT_INIT_CAPACITY = 10;

    private static final int DEFAULT_MAX_CAPACITY = Integer.MAX_VALUE;

    private static final String AUTHOR = "Курбаналиев Рамиль";

    public RamproxList() {
        this(DEFAULT_INIT_CAPACITY);
    }

    public RamproxList(int initCapacity) {
        this(initCapacity, DEFAULT_MAX_CAPACITY);
    }

    public RamproxList(int initCapacity, int maxCapacity) {
        checkCapacities(initCapacity, maxCapacity);
        this.maxCapacity = maxCapacity;
        this.items = createArray(initCapacity);
    }

    private void checkCapacities(int initCapacity, int maxCapacity) {
        checkCapacity(initCapacity, "initCapacity");
        checkCapacity(maxCapacity, "maxCapacity");
        if(initCapacity > maxCapacity) {
            String messageFormat = "initCapacity должен быть меньше или равно maxCapacity. initCapacity = %d, maxCapacity = %d";
            String message = String.format(messageFormat, initCapacity, maxCapacity);
            throw new IllegalArgumentException(message);
        }
    }

    private void checkCapacity(int capacity, String name) {
        if(capacity < 0) {
            String message = String.format("%s должен быть больше или равно нулю. %<s = %d", name, capacity);
            throw new IllegalArgumentException(message);
        }
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
    public boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        copy(items, 0, result, 0, result.length);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T1> T1[] toArray(T1[] array) {
        Objects.requireNonNull(array);
        T1[] result = array.length >= size ? array :
                (T1[]) Array.newInstance(array.getClass().getComponentType(), size);
        copy(items, 0, result, 0, size);
        if(result.length > size) {
            fill(result, size, result.length, null);
        }
        return result;
    }

    @Override
    public boolean add(T item) {
        add(size, item);
        return true;
    }

    @Override
    public void add(int index, T element) {
        checkIndexExclusiveLastIndex(index);
        T[] array = getArray(index, 1);
        array[index] = element;
        items = array;
        size++;
    }

    @Override
    public boolean addAll(Collection<? extends T> col) {
        return addAll(size, col);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> col) {
        Objects.requireNonNull(col);
        checkIndexExclusiveLastIndex(index);
        int itemsCount = col.size();
        T[] array = getArray(index, itemsCount);
        for (T item : col) {
            array[index++] = item;
        }
        items = array;
        size += itemsCount;
        return true;
    }

    private T[] getArray(int index, int itemsCount) {
        T[] array = canAccommodate(itemsCount) ? this.items : increaseInternalArray(index, itemsCount);
        copy(items, index, array, index + itemsCount, size - index);
        return array;
    }

    private boolean canAccommodate(int addingItemsCount) {
        return items.length - size >= addingItemsCount;
    }

    private T[] increaseInternalArray(int index, int addingItemsCount) {
        checkFreePlace(addingItemsCount);
        int newSize = addingItemsCount == 1 ? getNewSize() : size + addingItemsCount;
        T[] newInternalItems = createArray(newSize);
        copy(items, 0, newInternalItems, 0, index);
        return newInternalItems;
    }

    @Override
    public boolean remove(Object obj) {
        int index = indexOf(obj);
        if(index != -1) {
            remove(index);
            return true;
        }
        return false;
    }

    @Override
    public T remove(int index) {
        checkIndexInclusiveLastIndex(index);
        T previous = items[index];
        copy(items, index + 1, items, index, size - index - 1);
        items[--size] = null;
        return previous;
    }

    @Override
    public boolean removeAll(Collection<?> col) {
        Objects.requireNonNull(col);
        int oldSize = size;
        for (Object item : col) {
            int indexOfItem;
            while((indexOfItem = indexOf(item)) != -1) {
                remove(indexOfItem);
            }
        }
        return oldSize != size;
    }

    @Override
    public boolean containsAll(Collection<?> col) {
        Objects.requireNonNull(col);
        if(this.isEmpty() || col.isEmpty()) {
            return false;
        }
        for(Object item : col) {
            if(!contains(item)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> col) {
        Objects.requireNonNull(col);
        int oldSize = size;
        for(int index = 0; index < size; index++) {
            if(!col.contains(items[index])) {
                remove(index--);
            }
        }
        return oldSize != size;
    }

    @Override
    public void clear() {
        fill(items,0, size, null);
        size = 0;
    }

    @Override
    public T get(int index) {
        checkIndexInclusiveLastIndex(index);
        return items[index];
    }

    @Override
    public T set(int index, T element) {
        checkIndexInclusiveLastIndex(index);
        T previous = get(index);
        items[index] = element;
        return previous;
    }

    @Override
    public int indexOf(Object obj) {
        for(int index = 0; index < size; index++) {
            if(Objects.equals(obj, items[index])) {
                return index;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object obj) {
        for(int index = size - 1; index >= 0; index--) {
            if(Objects.equals(obj, items[index])) {
                return index;
            }
        }
        return -1;
    }

    @Override
    public Iterator<T> iterator() {
        return listIterator();
    }

    @Override
    public ListIterator<T> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        checkIndexExclusiveLastIndex(index);
        return new MyListIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        checkSubListIndexes(fromIndex, toIndex);
        int length = toIndex - fromIndex;
        List<T> subList = new RamproxList<>(length);
        for(int index = fromIndex; index < toIndex; index++) {
            subList.add(items[index]);
        }
        return subList;
    }

    private void checkSubListIndexes(int fromIndex, int toIndex) {
        if(fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public String getAuthor() {
        return AUTHOR;
    }

    @Override
    public void sort(Comparator<? super T> comparator) {
        Objects.requireNonNull(comparator);
        if(isEmpty()) {
            return;
        }
        RamproxForkJoinPoolSorting<T> sorting = new RamproxForkJoinPoolSorting<>(this.items, size, comparator);
        sorting.sort();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for(int index = 0; index < size; index++) {
            builder.append(items[index]);
            if(index != size - 1) {
                builder.append(", ");
            }
        }
        builder.append("]");
        return builder.toString();
    }

    private <T1, T2> void copy(T1[] src, int srcStartIndex, T2[] dest, int destStartIndex, int length) {
        if(src != dest) {
            copyFromStart(src, srcStartIndex, dest, destStartIndex, length);
            return;
        }
        if(srcStartIndex == destStartIndex) {
            return;
        }
        if(srcStartIndex < destStartIndex) {
            copyFromEnd(src, srcStartIndex, dest, destStartIndex, length);
        } else {
            copyFromStart(src, srcStartIndex, dest, destStartIndex, length);
        }
    }

    @SuppressWarnings("unchecked")
    private <T1, T2> void copyFromEnd(T1[] src, int srcStartIndex, T2[] dest, int destStartIndex, int length) {
        int srcEndIndex = srcStartIndex + length;
        int destEndIndex = destStartIndex + length;
        while(srcEndIndex > srcStartIndex) {
            dest[--destEndIndex] = (T2)src[--srcEndIndex];
        }
    }

    @SuppressWarnings("unchecked")
    private <T1, T2> void copyFromStart(T1[] src, int srcStartIndex, T2[] dest, int destStartIndex, int length) {
        int srcEndIndex = srcStartIndex + length;
        while(srcStartIndex < srcEndIndex) {
            dest[destStartIndex++] = (T2)src[srcStartIndex++];
        }
    }

    private <T1> void fill(T1[] array, int startIndex, int endIndex, T1 value) {
        while(startIndex < endIndex) {
            array[startIndex++] = value;
        }
    }

    @SuppressWarnings("unchecked")
    private T[] createArray(int size) {
        return (T[])new Object[size];
    }

    private void checkFreePlace(int addingItemsCount) {
        if(maxCapacity - size < addingItemsCount) {
            String message = String.format("Невозможно добавить %d элементов", addingItemsCount);
            throw new IllegalStateException(message);
        }
    }

    private void checkIndexExclusiveLastIndex(int index) {
        if(index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bound");
        }
    }

    private void checkIndexInclusiveLastIndex(int index) {
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bound");
        }
    }

    private int getNewSize() {
        int newSize = items.length < 10 ? 10 : items.length / 2 * 3;
        if(newSize > maxCapacity) {
            newSize = maxCapacity;
        }
        return newSize;
    }

    private class MyListIterator implements ListIterator<T> {

        private int currentIndex;

        private int lastReturnedItemIndex = -1;

        MyListIterator(int currentIndex) {
            this.currentIndex = currentIndex;
        }

        @Override
        public boolean hasNext() {
            return currentIndex != size;
        }

        @Override
        public T next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            lastReturnedItemIndex = currentIndex;
            return items[currentIndex++];
        }

        @Override
        public boolean hasPrevious() {
            return currentIndex != 0;
        }

        @Override
        public T previous() {
            if(!hasPrevious()) {
                throw new NoSuchElementException();
            }
            lastReturnedItemIndex = --currentIndex;
            return items[currentIndex];
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
            if(lastReturnedItemIndex == -1) {
                throw new IllegalStateException();
            }
            RamproxList.this.remove(lastReturnedItemIndex);
            lastReturnedItemIndex = -1;
            currentIndex--;
        }

        @Override
        public void set(T item) {
            if(lastReturnedItemIndex == -1) {
                throw new IllegalStateException();
            }
            items[lastReturnedItemIndex] = item;
        }

        @Override
        public void add(T item) {
            RamproxList.this.add(currentIndex, item);
            currentIndex++;
            lastReturnedItemIndex = -1;
        }
    }
}
