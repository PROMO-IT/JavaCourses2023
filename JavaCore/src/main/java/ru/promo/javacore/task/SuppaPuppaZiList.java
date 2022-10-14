package ru.promo.javacore.task;

import java.lang.reflect.Array;
import java.util.*;


public class SuppaPuppaZiList<T> implements AuthorHolder, List<T>{

    private Object[] list;

    public SuppaPuppaZiList(T[] list){
        this.list = list;
    }

    public SuppaPuppaZiList(){
        this.list = new Object[0];
    }


    @Override
    public String getAuthor() {
        return "Захар Разуткин";
    }

    @Override
    public int size() {
        return this.list.length;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public void clear() {
        this.list = new Object[0];
    }

    @Override
    public T get(int index) {
        return (T) this.list[index];
    }

    @Override
    public int indexOf(Object o) {
        int index = -1;
        for(int i = 0; i < size(); i++){
            if (get(i).equals(o))
            {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = -1;
        for(int i = size() - 1; i > 0; i--){
            if (this.list[i].equals(o))
            {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public boolean contains(Object o) {
        boolean contains = false;
        for (Object value : this.list) {
            if (value.equals(o)) {
                contains = true;
                break;
            }
        }
        return contains;
    }

    @Override
    public Object[] toArray() {
        Object[] newArr = new Object[size()];
        for(int i = 0; i < size(); i++){
            newArr[i] = get(i);
        }
        return newArr;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return (T[]) Array.newInstance(a.getClass(), size());
    }

    @Override
    public boolean add(Object o) {
        Object[] tempArr = new Object[size() + 1];
        for(int i = 0; i < size(); i++){
            tempArr[i] = get(i);
        }
        int lastElem = tempArr.length - 1;
        tempArr[lastElem] = o;
        this.list = tempArr;
        return true;
    }

    @Override
    public void add(int index, Object element) {
        Object[] tempArr = new Object[size() + 1];
        for(int i = 0; i < size(); i++){
            tempArr[i] = get(i);
        }
        for(int i = 0; i < size(); i++){
            if(i == index){
                for(int j = tempArr.length - 1; j > i; j--){
                    tempArr[j] = tempArr[j - 1];
                }
                tempArr[i] = element;
            }
        }
        this.list = tempArr;
    }

    @Override
    public boolean remove(Object o) {
        boolean remove = false;
        for(int i = 0; i < size(); i++){
            if(this.get(i).equals(o)){
                remove = true;
                for(int j = i; j < size() - 1; j++){
                    this.list[j] = this.list[j + 1];
                }
                if(remove) {
                    Object[] tempArr = new Object[size() - 1];
                    for(int j = 0; j < size() - 1; j++){
                        tempArr[j] = list[j];
                    }
                    this.list = tempArr;
                }
                break;
            }
        }
        return remove;
    }

    @Override
    public T remove(int index) {
        Object prevEl = get(index);
        for(int i = 0; i < size(); i++){
            if(i == index){
                for(int j = i; j < size() - 1; j++){
                    this.list[j] = this.list[j + 1];
                }
                Object[] tempArr = new Object[size() - 1];
                for(int j = 0; j < size() - 1; j++){
                    tempArr[j] = list[j];
                }
                this.list = tempArr;
            }
        }
        return (T) prevEl;
    }


    @Override
    public Object set(int index, Object element) {
        Object prevElem = get(index);
        this.list[index] = element;
        return prevElem;
    }

    @Override
    public Iterator iterator() {
        Iterator iterator = new Iterator() {
            private int i = 0;
            @Override
            public boolean hasNext() {
                return i < size();

            }
            @Override
            public Object next() {
                i++;
                return get(i - 1);
            }
        };
        return iterator;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        int prevSize = size();
        for(int i = 0; i < c.size(); i++){
            add(c.toArray()[i]);
        }
        return prevSize != size();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        int prevSize = size();
        for(int i = 0; i < c.size(); i++){
            add(index, c.toArray()[i]);
        }
        return prevSize != size();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        int count = 0;
        for(int i = 0; i < size(); i++){
            if(c.contains(get(i))){
                remove(get(i));
                count++;
                i = 0;
            }
        }
        return count > 0;
    }

    @Override
    public boolean containsAll(Collection c) {
        int cSize = c.size();
        int count = 0;
        for(int i = 0; i < c.size(); i++){
            if(contains(c.toArray()[i])) count++;
        }
        return c.size() == count;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int count = 0;
        for (int i = 0; i < size(); i++) {
            if (!c.contains(list[i])) {
                count++;
                remove(get(i));
                i = 0;
            }
        }
        return count > 0;
    }

    @Override
    public ListIterator listIterator() {
        ListIterator listIterator = new ListIterator() {

            private int i = 0;
            @Override
            public boolean hasNext() {
                return i < size();
            }

            @Override
            public Object next() {
                i++;
                return get(i - 1);
            }

            @Override
            public boolean hasPrevious() {
                return i > 0;
            }

            @Override
            public Object previous() {
                i--;
                return get(i);
            }

            @Override
            public int nextIndex() {
                return i;
            }

            @Override
            public int previousIndex() {
                return i - 1;
            }

            @Override
            public void remove() {
                SuppaPuppaZiList.this.remove(i);
            }

            @Override
            public void set(Object o) {
                SuppaPuppaZiList.this.set(i, o);

            }

            @Override
            public void add(Object o) {
                SuppaPuppaZiList.this.add(i, o);
            }
        };
        return listIterator;
    }

    @Override
    public ListIterator listIterator(int index) {
        ListIterator listIterator = new ListIterator() {

            private int i = index;
            @Override
            public boolean hasNext() {
                return i < size();
            }

            @Override
            public Object next() {
                i++;
                return get(i - 1);
            }

            @Override
            public boolean hasPrevious() {
                return i > 0;
            }

            @Override
            public Object previous() {
                i--;
                return get(i);
            }

            @Override
            public int nextIndex() {
                return i;
            }

            @Override
            public int previousIndex() {
                return i - 1;
            }

            @Override
            public void remove() {
                SuppaPuppaZiList.this.remove(i);
            }

            @Override
            public void set(Object o) {
                SuppaPuppaZiList.this.set(i, o);

            }

            @Override
            public void add(Object o) {
                SuppaPuppaZiList.this.add(i, o);
            }
        };
        return listIterator;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        SuppaPuppaZiList<T> newList = new SuppaPuppaZiList();
        for(int i = fromIndex; i < toIndex; i++){
            newList.add(get(i));
        }
        return newList;
    }
}
