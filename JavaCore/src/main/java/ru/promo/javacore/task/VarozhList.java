package ru.promo.javacore.task;

import java.util.*;

public class VarozhList<T> implements List<T>, AuthorHolder {
    public Object[] list = new Object[0];
    @Override
    public int size() {
        return list.length;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (Object t: list) {
            if (t.equals(o))
                return true;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return (Iterator<T>) Arrays.stream(list).iterator();
    }

    @Override
    public Object[] toArray() {
        Object[] newList = new Object[list.length];
        for (int i = 0; i < list.length; i++)
            list[i] = newList[i];
        return newList;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return (T1[]) Arrays.stream(a).toArray();
    }

    @Override
    public boolean add(T t) {
        int size = list.length;
        Object[] newList = Arrays.copyOf(list, list.length + 1);
        if (list.length == 0)
            newList[0] = t;
        else
            newList[newList.length - 1] = t;
        list = Arrays.copyOf(newList, newList.length);
        return size + 1 == list.length;
    }

    @Override
    public boolean remove(Object o) {
        boolean flag = false;
        for(int i = 0; i < list.length - 1; i++) {
            if (list[i].equals(o))
                flag = true;
            if (flag) {
                list[i] = list[i + 1];
            }
        }
        if (flag)
            list = Arrays.copyOf(list, list.length - 1);
        return flag;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean flag = false;
        int count = 0;
        for (int i = 0; i < list.length; i++) {
            for (Object ob: c) {
                if (list[i].equals(ob)) {
                    count++;
                    break;
                }
            }
        }
        if (count == c.size())
            flag = true;
        return flag;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        int size = list.length;
        Object[] newList = Arrays.copyOf(list, list.length + c.size());
        Object[] array = c.toArray();
        int j = 0;
        for (int i = list.length; i < newList.length; i++) {
            newList[i] = array[j];
            j++;
        }
        list = Arrays.copyOf(newList, newList.length);
        return size + c.size() == list.length;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        int size = list.length;
        Object[] newList = Arrays.copyOf(list, list.length + c.size());
        Object[] array = c.toArray();
        int count = 0;
        for (int i = index + array.length; i < newList.length; i++)
            newList[i] = newList[i - array.length];
        count = 0;
        for (int i = index; i < newList.length - (list.length - index); i++)
            newList[i] = array[count++];
        list = Arrays.copyOf(newList, newList.length);
        return size + c.size() == list.length;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        int count = 0;
        int size = list.length;
        Object[] array = c.toArray();
        int i = 0;
        while (i < list.length) {
            boolean flag = false;
            for (int j = 0; j < array.length; j++) {
                if (list[i].equals(array[j])) {
                    count++;
                    flag = true;
                }
            }
            if (flag) {
                for (int j = 0; j < array.length; j++) {
                    list[j] = list[j + 1];
                }
            } else i++;
        }
        list = Arrays.copyOf(list, list.length - count);
        return size > list.length;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int count = 0;
        int size = list.length;
        Object[] array = c.toArray();
        int index = 0;
        for (int i = 0; i < list.length; i++) {
            for (int j = 0; j < array.length; j++) {
                if (list[i].equals(array[j])) {
                    count++;
                    list[index++] = list[i];
                }
            }
        }
        list = Arrays.copyOf(list, list.length - count);

        return size > list.length;
    }

    @Override
    public void clear() {
        list = Arrays.copyOf(list, 0);
    }

    @Override
    public T get(int index) {
        return (T) list[index];
    }

    @Override
    public T set(int index, T element) {
        list[index] = element;
        return element;
    }

    @Override
    public void add(int index, T element) {
        Object[] newList = Arrays.copyOf(list, list.length + 1);
        for (int i = newList.length - 1; i >= index; i--)
            newList[i] = newList[i - 1];
        newList[index] = element;
        list = Arrays.copyOf(newList, newList.length);
    }

    @Override
    public T remove(int index) {
        Object[] newList = Arrays.copyOf(list, list.length);
        for (int i = index; i < newList.length - 1; i++)
            newList[i] = newList[i + 1];
        list = Arrays.copyOf(newList, newList.length - 1);
        return (T) list[index];
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < list.length; i++) {
            if (list[i].equals(0))
                return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = list.length; i >= 0; i--) {
            if (list[i].equals(0))
                return i;
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        return (ListIterator<T>) Arrays.stream(list).iterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return (ListIterator<T>) Arrays.stream(new Object[]{list[index]}).iterator();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        List<T> newList = new ArrayList<>();
        for (int i = fromIndex; i < toIndex; i++)
            newList.add((T) list[i]);
        return newList;
    }

    @Override
    public void sort(Comparator<? super T> c) {
        Comparator<Object> com = (Comparator<Object>) c;
        boolean isSort = false;
        while (!isSort) {
            isSort = true;
            for (int i = 1; i < list.length; i++) {
                if (com.compare(list[i], list[i - 1]) < 0) {
                    Object min = list[i];
                    list[i] = list[i - 1];
                    list[i - 1] = min;
                    isSort = false;
                }
            }
        }

    }

    public void print() {
        for (int i = 0; i < list.length; i++)
            System.out.print(list[i] + " ");
        System.out.println();
    }

    @Override
    public String getAuthor() {
        return "varozh / Ворожейкин Андрей";
    }
}
