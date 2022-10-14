package ru.promo.javacore.task;

import java.lang.reflect.Array;
import java.util.*;

public class RailShabayevList<T> implements List<T>, AuthorHolder {
    private int size = 0;
    private Object[] list;

    public RailShabayevList() {
        list = new Object[size];
    }

    public RailShabayevList(int size) {
        this.size = size;
        list = new Object[size];
    }

    public RailShabayevList(Collection<T> collection) {
        size = collection.size();
        list = new Object[size];

        for (int i = 0; i < size; i++) {
            list[i] = collection.toArray()[i];
        }
    }

    @Override
    public void sort(Comparator<? super T> c) {
        if (c == null) {
            c = new Comparator<T>() {
                @Override
                public int compare(T o1, T o2) {
                    return ((Comparable) o1).compareTo(o2);
                }
            };
        }
        QuickSort sort = new QuickSort(c);
        sort.quicksort((T[]) list);

    }

    private class QuickSort {
        private final Comparator<? super T> c;

        QuickSort(Comparator<? super T> comparator) {
            c = comparator;
        }

        public void quicksort(T[] a) {
            quicksort((T[]) list, 0, size - 1);
        }

        private void quicksort(T[] arr, int begin, int end) {
            if (begin < end) {
                int partitionIndex = partition(arr, begin, end);
                quicksort(arr, begin, partitionIndex - 1);
                quicksort(arr, partitionIndex + 1, end);
            }
        }

        private int partition(T arr[], int begin, int end) {
            T pivot = arr[end];
            int i = (begin - 1);
            for (int j = begin; j < end; j++) {
                if (c.compare(arr[j], pivot) <= 0) {
                    i++;
                    T swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
            T swapTemp = arr[i + 1];
            arr[i + 1] = arr[end];
            arr[end] = swapTemp;

            return i + 1;
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
    public boolean contains(Object o) {
        for (T t : this) {
            if (t.equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean add(T t) {
        Object[] listCopy = new Object[++size];
        for (int i = 0; i < size - 1; i++) {
            listCopy[i] = list[i];
        }
        listCopy[size - 1] = t;
        list = new Object[size];
        for (int i = 0; i < size; i++) {
            list[i] = listCopy[i];
        }
        return true;
    }

    @Override
    public void add(int index, T element) {
        Object[] listCopy = new Object[++size];
        for (int i = 0; i < size - 1; i++) {
            listCopy[i] = list[i];
        }
        list = new Object[size];
        for (int i = 0; i < index; i++) {
            list[i] = listCopy[i];
        }
        for (int i = index; i < size - 1; i++) {
            list[i + 1] = listCopy[i];
        }
        list[index] = element;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean flag = false;
        for (Object o : c) {
            add((T) o);
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        int i = index;
        boolean flag = false;
        for (Object o : c) {
            add(i++, (T) o);
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (get(i).equals(o)) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public T remove(int index) {
        T t = (T) get(index);
        Object[] listCopy = new Object[--size];
        for (int i = 0; i < index; i++) {
            listCopy[i] = get(i);
        }
        for (int i = index + 1; i < size + 1; i++) {
            listCopy[i - 1] = get(i);
        }
        list = listCopy;
        return t;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean flag = false;

        for (int i = size - 1; i >= 0; i--) {
            if (c.contains(get(i))) {
                remove(i);
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean flag = false;

        for (int i = size - 1; i >= 0; i--) {
            if (!c.contains(get(i))) {
                remove(i);
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void clear() {
        size = 0;
        list = new Object[size];
    }

    @Override
    public T get(int index) {
        return (T) list[index];
    }

    @Override
    public T set(int index, T element) {
        T t = (T) list[index];
        list[index] = element;
        return t;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (get(i).equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (list[i].equals(o)) {
                index = i;
            }
        }
        return index;
    }

    @Override
    public RailShabayevList<T> subList(int fromIndex, int toIndex) {
        RailShabayevList<T> newList = new RailShabayevList<>();
        for (int i = 0; i < toIndex - fromIndex; i++) {
            newList.add((T) list[fromIndex + i]);
        }
        return newList;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int count = 0;

            @Override
            public boolean hasNext() {
                return count < list.length;
            }

            @Override
            public T next() {
                return (T) list[count++];
            }
        };
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ListIterator<>() {
            private int count = 0;
            private boolean canRemove = false;

            @Override
            public boolean hasNext() {
                return count < size;
            }

            @Override
            public T next() {
                canRemove = true;
                return (T) list[count++];
            }

            @Override
            public boolean hasPrevious() {
                return count > 0;
            }

            @Override
            public T previous() {
                canRemove = true;
                return (T) list[--count];
            }

            @Override
            public int nextIndex() {
                return count;
            }

            @Override
            public int previousIndex() {
                return count - 1;
            }

            @Override
            public void remove() {
                if (canRemove) {
                    RailShabayevList.this.remove(list[count - 1]);
                    canRemove = false;
                } else {
                    throw new IllegalStateException();
                }
            }

            @Override
            public void set(T t) {
                if (canRemove) {
                    RailShabayevList.this.set(count - 1, t);
                    canRemove = false;
                } else {
                    throw new IllegalStateException();
                }
            }

            @Override
            public void add(T t) {
                RailShabayevList.this.add(count++, (T) t);
                canRemove = false;
            }
        };
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new ListIterator<>() {
            private int count = index;
            private boolean canRemove = false;

            @Override
            public boolean hasNext() {
                return count < size;
            }

            @Override
            public T next() {
                canRemove = true;
                return (T) list[count++];
            }

            @Override
            public boolean hasPrevious() {
                return count > 0;
            }

            @Override
            public T previous() {
                canRemove = true;
                return (T) list[--count];
            }

            @Override
            public int nextIndex() {
                return count;
            }

            @Override
            public int previousIndex() {
                return count - 1;
            }

            @Override
            public void remove() {
                if (canRemove) {
                    RailShabayevList.this.remove(list[count - 1]);
                    canRemove = false;
                } else {
                    throw new IllegalStateException();
                }
            }

            @Override
            public void set(T t) {
                if (canRemove) {
                    RailShabayevList.this.set(count - 1, t);
                    canRemove = false;
                } else {
                    throw new IllegalStateException();
                }
            }

            @Override
            public void add(T t) {
                RailShabayevList.this.add(count++, (T) t);
                canRemove = false;
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] newList = (Object[]) Array.newInstance(Object[].class.getComponentType(), size);
        for (int i = 0; i < size; i++) {
            newList[i] = list[i];
        }
        return newList;
    }

    @Override
    public <E> E[] toArray(E[] a) {
        if (a.length < size && a.length != 0) {
            a = (E[]) Array.newInstance(a.getClass().getComponentType(), size);
        } else {
            a = (E[]) Array.newInstance(a.getClass().getComponentType(), a.length);
        }
        for (int i = 0; i < size; i++) {
            a[i] = (E) list[i];
        }
        return a;
    }

    @Override
    public String toString() {
        return "MyList{" + Arrays.toString(list) +
                '}';
    }

    @Override
    public String getAuthor() {
        return "⋱Шабаев Раиль⑨";
    }
}
