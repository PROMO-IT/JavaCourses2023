package ru.promo.javacore.task;

import java.util.*;
import java.util.function.UnaryOperator;
import java.lang.reflect.Array;

public class KresiaKuList<T> implements List<T>, AuthorHolder {

    public Object[] myArray;
    private int size = 100;

    public KresiaKuList(T[] obj) {
        this.myArray = obj;
    }

    @Override
    public int size() {
        return this.myArray.length;
    }

    @Override
    public boolean isEmpty() {
        if (myArray.length == 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < myArray.length; i++) {
            if (myArray[i] == o)
                return true;
        }

        return false;
    }

    @Override

    public Iterator iterator() {
        Iterator iterator = new Iterator() {
            int i = 0;

            @Override
            public boolean hasNext() {
                return i < myArray.length;
            }

            @Override
            public Object next() {
                for (int i = 0; i < myArray.length; i++) {
                    return myArray[i + 1];
                }

                return null;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };

        return null;
    }

    @Override
    public Object[] toArray() {
        Object[] newArray = new Object[myArray.length];
        for (int i = 0; i < myArray.length; i++) {
            return new Object[i];
        }
        return new Object[0];
    }

    @Override
    public boolean add(Object o) {
        Object[] newArray = new Object[myArray.length + 1];
        for (int i = 0; i < myArray.length; i++) {
            newArray[i] = myArray[i];
        }
        newArray[newArray.length - 1] = o;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        Object[] newArray = new Object[myArray.length + 1];
        for (int i = 0; i < myArray.length - 1; i++) {
            if (myArray[i] == o) {
                newArray = new Object[myArray.length - 1];
                for (int index = 0; index < i; index++) {
                    newArray[index] = myArray[index];
                }
                for (int j = i; j < myArray.length - 1; j++) {
                    newArray[j] = myArray[j + 1];
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean addAll(Collection c) {
        int csize = c.size();
        Object[] newArray = new Object[myArray.length + c.size()];
        for (int i = 0; i < myArray.length; i++) {
            newArray[i] = myArray[i];
        }
        for (int i = myArray.length; i < (myArray.length + c.size()); i++) {
            newArray[i - 1] = c;
            return true;
        }
        return false;
    }

    @Override
    public boolean addAll(int index, Collection c) {
        int csize = c.size();
        Object[] newArray = new Object[myArray.length + c.size()];
        for (int i = 0; i < index; i++) {
            for (int j = index; j < myArray.length; j++) {
                myArray[j] = newArray[j + csize];
            }
            newArray[index] = c;
        }
        return false;
    }

    @Override
    public void replaceAll(UnaryOperator operator) {
        List.super.replaceAll(operator);
    }

    @Override
    public void sort(Comparator c) {

        List.super.sort(c);
    }

    @Override
    public void clear() {
        for (int i = 0; i < myArray.length; i++) {
            myArray[i] = 0;
        }
    }

    @Override
    public T get(int index) {
        if (!isEmpty() && (index >= 0 && index < myArray.length)) {
            for (int i = 0; i <= index; i++) {
                return (T) myArray[index];
            }
        }
        return null;
    }

    @Override
    public Object set(int index, Object element) {
        Object[] newArray = new Object[myArray.length + 1];
        for (int i = 0; i < myArray.length; i++) {
            newArray[i] = myArray[i];
            newArray[newArray.length - 1] = element;
            return newArray;
        }
        return null;
    }

    @Override
    public void add(int index, Object element) {
        for (int i = 0; i < index; i++) {
            myArray[index] = element;
        }
    }

    @Override
    public T remove(int index) {
        Object[] newArray = new Object[myArray.length - 1];
        for (int i = 0; i < myArray.length; i++) {
            if (i != index) {
                var newIndex = i < index ? i : i - 1;
                newArray[newIndex] = myArray[i];
            }
            return (T) newArray;
        }
        return null;
    }

    @Override
    public int indexOf(Object o) {
        int count = 0;
        for (int i = 0; i < myArray.length; i++) {
            if (myArray[i] == o) {
                count = i;
                return count;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int count = 0;
        for (int i = myArray.length - 1; i >= 0; i--) {
            if (myArray[i] == o) {
                count = i - 1;
                return count;
            }
        }
        return -1;
    }

    @Override

    public ListIterator listIterator() {
        Iterator<Object> iterator = new Iterator<Object>() {
            int i = 0;

            @Override
            public boolean hasNext() {
                return myArray.length > i;
            }

            @Override
            public Object next() {
                return myArray[i + 1];
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return null;
    }

    @Override
    public ListIterator listIterator(int index) {
        for (int i = index - 1; i < myArray.length; i++) {
            return (ListIterator) subList(index, myArray.length);
        }
        return null;
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        KresiaKuList newArray = new KresiaKuList(myArray);
        for (int i = fromIndex; i < toIndex; i++) {
            newArray.add(i);
            return newArray;
        }
        return null;

    }

//    @Override
//    public Spliterator spliterator() {
//
//        return null;
//    }

    @Override
    public boolean retainAll(Collection c) {
        Objects.requireNonNull(c);
        return batchRemove(c, true);
    }

    private boolean batchRemove(Collection<?> c, boolean complement) {
        final Object[] MyArray = this.myArray;
        int r = 0, w = 0;
        boolean modified = false;
        try {
            for (; r < size; r++)
                if (c.contains(MyArray[r]) == complement)
                    MyArray[w++] = MyArray[r];
        } finally {
            if (r != size) {
                System.arraycopy(MyArray, r,
                        MyArray, w,
                        size - r);
                w += size - r;
            }
            if (w != size) {
                for (int i = w; i < size; i++)
                    MyArray[i] = null;
                size = w;
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection c) {
        Objects.requireNonNull(c);
        boolean modified = false;

        if (size() > c.size()) {
            for (Iterator<?> i = c.iterator(); i.hasNext(); )
                modified |= remove(i.next());
        } else {
            for (Iterator<?> i = iterator(); i.hasNext(); ) {
                if (c.contains(i.next())) {
                    i.remove();
                    modified = true;
                }
            }
        }
        return modified;
    }

    @Override
    public boolean containsAll(Collection c) {
        int count = 0;
        for (int i = 0; i < myArray.length; i++) {
            if (myArray[i] == c) {
                count++;
                if (count == myArray.length) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < size)
            return (T1[]) Arrays.copyOf(myArray, size, a.getClass());
        System.arraycopy(myArray,0,a,0,size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

    @Override
    public String getAuthor() {
        return "Kristina Kurianova";
    }
}
