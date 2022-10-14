package ru.promo.javacore.task;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class VladisList<T> implements List<T>, AuthorHolder {
    private static final int DEFAULT_CAPACITY = 10;
    private static final int CUT = 3;
    private int size;
    private transient Object[] elementData;

    public VladisList() {
        elementData = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    public VladisList(int initialCapacity) {
        elementData = new Object[initialCapacity];
        size = 0;
    }

    private VladisList(Object[] o) {
        elementData = o;
        size = o.length;
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        if(size == 0) return true;

        return false;
    }

    @Override
    public boolean contains(Object o) {
        for (Object element : elementData) {
            if(element == null) return false;

            if (element.equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new myIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] newArray = new Object[size];
        System.arraycopy(elementData, 0, newArray, 0, size);
        return newArray;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if(a.length < size){
            a = (T1[]) Array.newInstance(a.getClass().getComponentType(), size);
        }
        System.arraycopy(elementData, 0, a, 0, size);
        return a;
    }

    @Override
    public boolean add(T t) {
        try{
            if (size == elementData.length-1){
                Object[] newArray = new Object[elementData.length*2];
                System.arraycopy(elementData, 0, newArray, 0, size);
                elementData = newArray;
            }
            elementData[size] = t;
            size++;
            return true;

        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean remove(Object o) {
        try{
            if(size < elementData.length/CUT){
                Object[] newArray = new Object[elementData.length/2];
                System.arraycopy(elementData, 0, newArray, 0, size);
                elementData = newArray;
            }
            for (int i = 0; i < size; i++) {
                if(elementData[i] == o){
                    System.arraycopy(elementData, i+1, elementData, i, size-i-1);
                    elementData[size-1] = null;
                    size--;
                    return true;
                }
            }

            return false;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object element : c) {
            if(!contains(element)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        Object[] arrC = c.toArray();
        try{
            if(size==0){
                elementData = c.toArray();
                size = c.size();
                return true;
            }
            Object[] newArray = (T[]) Array.newInstance(elementData.getClass().getComponentType(), elementData.length + c.size());
            System.arraycopy(elementData, 0, newArray, 0, size);
            System.arraycopy(arrC, 0, newArray, size, c.size());
            elementData = newArray;

            size += c.size();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        Object[] arrC = c.toArray();
        Object[] newArray = (T[]) Array.newInstance(elementData.getClass().getComponentType(), size + c.size());
        System.arraycopy(elementData, 0, newArray, 0, index);
        System.arraycopy(arrC, 0, newArray, index, c.size());
        System.arraycopy(elementData, index, newArray, index+c.size(), size-index);
        elementData = newArray;
        size = newArray.length;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        int count = 0;
        for (Object obj: c) {
            if(remove(obj)) count++;
        }

        if(count > c.size()){
            return true;
        }
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int count = 0;

        for (int i = 0; i < size; i++) {
            boolean retain = false;
            for (Object obj: c) {
                if(obj.equals(elementData[i])){
                    retain = true;
                    count++;
                    break;
                }
            }
            if(!retain){
                System.arraycopy(elementData, i+1, elementData, i, size-i);
                i--;
                size--;
            }
        }
        if(count == c.size()){
            return true;
        }
        return false;
    }

    @Override
    public void sort(Comparator<? super T> c) {
        MergeSortForkJoin.sort((T[])elementData, c);
    }

    @Override
    public void clear() {
        elementData = new Object[]{};
        size = 0;
    }

    @Override
    public T get(int index) {
        return (T) elementData[index];
    }

    @Override
    public T set(int index, T element) {
        T old = (T) elementData[index];
        elementData[index] = element;
        return old;
    }

    @Override
    public void add(int index, T element) {
        if (size == elementData.length-1){
            Object[] newArray = new Object[elementData.length*2];
            System.arraycopy(elementData, 0, newArray, 0, size);
            elementData = newArray;
        }
        System.arraycopy(elementData, index, elementData, index+1, size-index);
        elementData[index] = element;
        size++;
    }

    @Override
    public T remove(int index) {
        T obj = (T) elementData[index];
        System.arraycopy(elementData, index+1, elementData, index, elementData.length-index-1);
        elementData[size-1] = null;
        size--;

        return obj;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if(elementData[i].equals(o)) return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if(elementData[i].equals(o)) index = i;
        }
        return index;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ListItr(0);

    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new ListItr(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        Object[] newArray = new Object[toIndex-fromIndex];
        System.arraycopy(elementData, fromIndex, newArray, 0, toIndex-fromIndex);
        return new VladisList<T>(newArray);
    }

    @Override
    public String toString(){
        Object[] newArray = new Object[size];
        System.arraycopy(elementData, 0, newArray, 0, size);
        return Arrays.toString(newArray);
    }

    @Override
    public String getAuthor() {
        return "Аппанов В. С.";
    }


    private class myIterator implements Iterator<T> {
        int current = 0; // index of next element to return
        int lastIndex;
        myIterator() {}

        @Override
        public boolean hasNext() {
            if(lastIndex+1<size) return true;
            return false;
        }

        @Override
        public T next() {
            lastIndex = current;
            current++;
            return (T) elementData[lastIndex];
        }

        @Override
        public void remove(){
            if(lastIndex+1 >= size){
                elementData[lastIndex] = null;
                size--;
                return;
            }
            System.arraycopy(elementData, lastIndex+1, elementData, lastIndex, size-lastIndex);
            elementData[size-1] = null;
            current--;
            size--;
        }
    }


    private class ListItr extends myIterator implements ListIterator<T>{
        ListItr(int index) {
            super();
            current = index;
        }

        @Override
        public boolean hasPrevious() {
            if(current-1 >= 0 && elementData[current-1] != null){
                return true;
            }
            return false;
        }

        @Override
        public T previous() {
            return (T) elementData[lastIndex];
        }

        @Override
        public int nextIndex() {
            if(current+1 < size && current+1 >=0){
                return current+1;
            }
            return -1;
        }

        @Override
        public int previousIndex() {
            return lastIndex;
        }

        @Override
        public void set(T t) {
            elementData[current] = t;
        }

        @Override
        public void add(T t) {
            if (size == elementData.length-1){
                Object[] newArray = new Object[elementData.length*2];
                System.arraycopy(elementData, 0, newArray, 0, size);
                elementData = newArray;
            }
            System.arraycopy(elementData, current, elementData, current+1, size-current);
            elementData[current] = t;
            lastIndex = current;
            current++;

            size++;
        }
    }

    private class MergeSortForkJoin {
        public static <T> void sort(T[] a, Comparator<? super T> c) {
            @SuppressWarnings("unchecked")
            T[] helper = (T[]) Array.newInstance(a[0].getClass(), a.length);
            ForkJoinPool forkJoinPool = new ForkJoinPool(6);

            forkJoinPool.invoke(new MergeSortTask<T>(a, helper, 0, a.length - 1, c));

            forkJoinPool.shutdown();


        }


        private static class MergeSortTask<T> extends RecursiveAction {
            private final T[] a;
            private final T[] helper;
            private final int lo;
            private final int hi;
            private final Comparator<? super T> c;

            public MergeSortTask(T[] a, T[] helper, int lo, int hi, Comparator<? super T> c) {
                this.a = a;
                this.helper = helper;
                this.lo = lo;
                this.hi = hi;
                this.c = c;
            }

            @Override
            protected void compute() {
                if (lo >= hi) return;
                int mid = lo + (hi - lo) / 2;
                MergeSortTask<T> left = new MergeSortTask<>(a, helper, lo, mid, c);
                MergeSortTask<T> right = new MergeSortTask<>(a, helper, mid + 1, hi, c);

                invokeAll(left, right);
                merge(this.a, this.helper, this.lo, mid, this.hi);

            }

            private void merge(T[] a, T[] helper, int lo, int mid, int hi) {
                for (int i = lo; i <= hi; i++) {
                    helper[i] = a[i];
                }
                int i = lo, j = mid + 1;
                for (int k = lo; k <= hi; k++) {
                    if (i > mid) {
                        a[k] = helper[j++];
                    } else if (j > hi) {
                        a[k] = helper[i++];
                    } else if (isLess(helper[i], helper[j])) {
                        a[k] = helper[i++];
                    } else {
                        a[k] = helper[j++];
                    }
                }
            }

            private boolean isLess(T a, T b) {
                return c.compare(a, b) < 0;
            }
        }
    }

}
