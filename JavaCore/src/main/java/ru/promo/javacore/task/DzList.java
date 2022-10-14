package ru.promo.javacore.task;


import java.util.*;

public class DzList<T> implements List<T> ,AuthorHolder {


    private int size;
    private T[] list;


    public DzList() {
        this.list = (T[]) new Object[0];
    }

    public DzList(int size) {
        this.size = size;
        this.list = (T[]) new Object[size];

    }


    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (var c : list) {
            if (c.equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new DzListIterator<>(list);
    }

    @Override
    public Object[] toArray() {
        T[] toArr = list;
        return toArr;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < size)
            // Make a new array of a's runtime type, but my contents:
            return (T1[]) Arrays.copyOf(list, size, a.getClass());
        System.arraycopy(list, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;

    }

    @Override
    public boolean add(T t) {
        try {
            T[] add = list;
            list = (T[]) new Object[add.length + 1];
            System.arraycopy(add, 0, list, 0, add.length);
            list[list.length - 1] = t;
            size++;
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        T[] remove = list;
        list = (T[]) new Object[remove.length - 1];
        System.arraycopy(remove, 0, list, 0, size);
        System.arraycopy(remove, size + 1, list, size, remove.length - size - 1);
        size--;
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (var l : list) {
            for (var C : c) {
                if (l.equals(C)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (size < c.size()) {
            return false;
        }
        for (Object a : c) {
            add((T) a);

        }

        return true;

    }


    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (size < c.size()) {
            return false;
        }
        for (Object a : c) {
            add(index, (T) a);
            index++;

        }

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        DzList<Object> remAll = new DzList<>();
        remAll.addAll(List.of(list));
        for (var rem : c) {
            if (remAll.contains(rem)) {
                remAll.remove(rem);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        list = (T[]) new Object[0];
    }

    @Override
    public T get(int index) {
        return (T) list[index];
    }

    @Override
    public T set(int index, T element) {
        list[index] = element;
        return (T) list;
    }

    @Override
    public void add(int index, T element) {

        Object[] add = new Object[list.length + 1];

        T t = list[index];
        for (int i = index+1;i < add.length;i++){
            add[index] = element;
            add[i] = t;
        }


    }


    @Override
    public T remove(int index) {
        T[] remove = list;
        list = (T[]) new Object[remove.length - 1];
        System.arraycopy(remove, 0, list, 0, index);
        System.arraycopy(remove, index + 1, list, index, remove.length - index - 1);
        return (T) remove;

    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        for (int i = 0; i < list.length; i++) {
            if (list[i].toString().contains(o.toString())) {
                index = i;
            }
        }

        return index;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = 0;
        for (int i = 0; i < list.length; i++) {
            if (list[i].toString().contains(o.toString())) {
                index = i;
                return index;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        return (ListIterator<T>) iterator().next();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        if(iterator().hasNext()){
            if (iterator().next().equals(index)){
                return (ListIterator<T>) iterator().next();
            }
        }
        return null;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        DzList<T> sub = new DzList<>();
        int count = 0;
        for (int i = fromIndex; i <= toIndex; i++) {
            sub.add(list[i]);
            count++;
        }

        return sub;

    }

    @Override
    public String toString() {
        return "ru.arzamasov.dzjava.DzList{" +
                "size=" + size +
                ", list=" + Arrays.toString(list) +
                '}';
    }

    @Override
    public String getAuthor() {
        return "Maksim Arzamasov";
    }

    public  void sort(T element, T element1){
        if (list.length == 0){
            return;
        }
        Arrays.sort(list);
    }


}
