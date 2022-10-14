package ru.promo.javacore.task;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Comparator;


public class KirillList<T> implements List<T>, AuthorHolder {
    private int maxSize = 10;
    private Object[] list = new Object[maxSize];
    private int currentSize = 0;
    @Override
    public int size() {
        return currentSize;
    }

    @Override
    public boolean isEmpty() {
        return currentSize ==0;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < currentSize; i++) {
            if (list[i].equals(o)) return true;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private int index = 0;

            @Override
            public boolean hasNext() {
                return index<currentSize && list[index] != null;
            }

            @Override
            public T next() {
                return (T) list[index++];
            }

            @Override
            public void remove() {
                KirillList.this.remove(index-1);
                index--;
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] arr = new Object[currentSize];
        for (int i = 0; i < currentSize; i++) {
            arr[i] = list[i];
        }
        //System.arraycopy(list, 0, arr, 0, currentSize);
        return arr;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        T1[] arr = (T1[]) Array.newInstance(a.getClass().getComponentType(), currentSize);
        for (int i = 0; i < currentSize; i++){
            arr[i] = (T1)list[i];
        }
        return arr;
    }

    @Override
    public boolean add(T t) {
        if(currentSize == maxSize){
            grow();
        }
        list[currentSize] = t;
        currentSize++;
        return true;
    }

    private void grow() {
        maxSize *=2;
        Object[] newList = new Object[maxSize];
        for (int i = 0; i < list.length; i++) {
            newList[i] = list[i];
        }
        //System.arraycopy(list, 0, newList, 0, list.length);
        list = newList;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < currentSize; i++) {
            if (list[i].equals(o)){
                remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (var el : c) {
            if(!contains(el)){
                return false;
            }
        }
        return true;

    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (var el : c) {
            add(el);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        for (var el : c) {
            add(index, el);
            index++;
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for (var el : c) {
            remove(el);
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        KirillList<T> buffer = new KirillList<>();
        for (var el:c) {
            if(contains(el)){
                buffer.add((T) el);
            }
        }
        for (int i = 0; i < currentSize; i++) {
            if(!buffer.contains(list[i])){
                remove(i);
            }
        }
        return true;
    }

    @Override
    public void sort(Comparator<? super T> c) {
        quickSort(toArray(),0, currentSize - 1, c);
    }

    private void quickSort(Object[] arr, int first, int last, Comparator<? super T> c) {
        if (first < last) {
            int left = first;
            int right = last;

            Object middle = medianOfThree((T) arr[left], (T) arr[(left + right) / 2], (T) arr[right], c);

            while (left <= right) {
                while (c.compare((T) arr[left], (T) middle) < 0) {
                    left++;
                }
                while (c.compare((T) arr[right], (T) middle) > 0) {
                    right--;
                }
                if (left <= right) {
                    Object t = arr[left];
                    arr[left] = arr[right];
                    arr[right] = t;
                    left++;
                    right--;
                }
            }
            quickSort(arr, first, right, c);
            quickSort(arr, left, last, c);
        }
        list = arr;
        maxSize = list.length;
        currentSize = list.length;
    }

    private T medianOfThree(T t1, T t2, T t3, Comparator<? super T> c){
        if (c.compare(t1,t2)>0 ^ c.compare(t1,t3)>0)
            return t1;
        else if (c.compare(t2,t1) < 0 ^ c.compare(t2,t3)<0)
            return t2;
        else
            return t3;
    }
    @Override
    public void clear() {
        for (int i = 0; i < currentSize; i++) {
            list[i]=null;
        }
        currentSize = 0;
    }

    @Override
    public T get(int index) {
        checkOutOfBounds(index);

        return (T) list[index];
    }

    @Override
    public T set(int index, T element) {
        checkOutOfBounds(index);

        T old = (T) list[index];
        list[index] = element;
        return old;
    }

    @Override
    public void add(int index, T element) {
        checkOutOfBounds(index);

        if(currentSize == maxSize){
            grow();
        }
        for (int i = currentSize; i >= index; i--) {
            list[i+1]=list[i];
        }
        list[index]=element;
        currentSize++;
    }

    @Override
    public T remove(int index) {
        checkOutOfBounds(index);

        Object removeObj = list[index];
        for (int i = index + 1; i < currentSize; i++) {
            list[i-1]=list[i];
        }
        currentSize--;
        return (T)removeObj;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < currentSize; i++) {
            if(list[i].equals(o)) return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = -1;
        for (int i = 0; i < currentSize; i++) {
            if(list[i].equals(o)) index=i;
        }
        return index;
    }

    @Override
    public ListIterator<T> listIterator() {

        return new ListIterator<T>() {
            private int index=0;
            private int indexForUpdate;
            private boolean isNext;
            @Override
            public boolean hasNext() {
                return index<currentSize && list[index] != null;
            }

            @Override
            public T next() {
                indexForUpdate = index;
                isNext = true;
                return (T) list[index++];
            }

            @Override
            public boolean hasPrevious() {
                return index!=0;
            }

            @Override
            public T previous() {
                indexForUpdate = --index;
                isNext = false;
                return (T) list[index];
            }

            @Override
            public int nextIndex() {
                return index+1;
            }

            @Override
            public int previousIndex() {
                return index-1;
            }

            @Override
            public void remove() {
                if(isNext){
                    index--;
                }
                KirillList.this.remove(indexForUpdate);
            }

            @Override
            public void set(T t) {
                KirillList.this.set(indexForUpdate, t);
            }

            @Override
            public void add(T t) {
                if(isNext){
                    KirillList.this.add(indexForUpdate - 1, t);
                    index++;
                }
                else {
                    KirillList.this.add(indexForUpdate + 1, t);
                }
            }
        };
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        checkOutOfBounds(index);
        return new ListIterator<T>() {
            private int thisIndex = index;
            private int indexForUpdate;
            private boolean isNext;
            @Override
            public boolean hasNext() {
                return thisIndex < currentSize && list[thisIndex] != null;
            }

            @Override
            public T next() {
                indexForUpdate = thisIndex;
                isNext = true;
                return (T) list[thisIndex++];
            }

            @Override
            public boolean hasPrevious() {
                return thisIndex !=index;
            }

            @Override
            public T previous() {
                indexForUpdate = --thisIndex;
                isNext = false;
                return (T) list[thisIndex];
            }

            @Override
            public int nextIndex() {
                return thisIndex +1;
            }

            @Override
            public int previousIndex() {
                return thisIndex -1;
            }

            @Override
            public void remove() {
                if(isNext){
                    thisIndex--;
                }
                KirillList.this.remove(indexForUpdate);
            }

            @Override
            public void set(T t) {
                KirillList.this.set(indexForUpdate, t);
            }

            @Override
            public void add(T t) {
                if(isNext){
                    KirillList.this.add(indexForUpdate - 1, t);
                    thisIndex++;
                }
                else {
                    KirillList.this.add(indexForUpdate + 1, t);
                }
            }
        };
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        checkOutOfBounds(toIndex);

        KirillList<T> myList = new KirillList<>();

        for (int i = fromIndex; i < toIndex; i++) {
            myList.add((T) this.list[i]);
        }
        return myList;
    }

    private void checkOutOfBounds(int index){
        if(index>= currentSize){
            throw new IndexOutOfBoundsException(String.format("Index %s out of bounds for length %s", index, currentSize));
        }
    }

    @Override
    public String getAuthor() {
        return "Тепечин Кирилл";
    }
}
