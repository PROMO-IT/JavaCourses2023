package ru.promo.javacore.task;

import java.util.*;

public class MyListik<T> implements List<T>, AuthorHolder{

    // хранение элементов в массиве
    private T[] myData = (T[])new Object[0];

    // просто length для вывода размерности
    @Override
    public int size() {
        return this.myData.length;
    }

    // соответсвенно если длина 0 (не знаю, может ли уйти в минус, наверное, да, поэтому <=), то пусто
    @Override
    public boolean isEmpty() {
        return size() <= 0;
    }

    // простая итерация по массиву и поиск на совпадение
    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return this.myData;
    }

    // добавление в конец массива нового элемента
    @Override
    public boolean add(T t) {
        add(size(), t);
        return true;
    }

    // удаление элемента по значению
    @Override
    public boolean remove(Object o) {

        if (!contains(o))
            return false;

        T[] tempArray = (T[])new Object[this.myData.length - 1];

        int i = 0;
        while (this.myData[i] != o){
            tempArray[i] = this.myData[i];
            i++;
        }

        for (int j = i; j<tempArray.length ;j++){
            tempArray[j] = this.myData[j+1];
        }

        this.myData = (T[])new Object[this.myData.length - 1];

        for (int k = 0; k < size(); k++){
            this.myData[k] = tempArray[k];
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        addAll(size(), c);
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        for (Object el: c){
            add(index, (T)el);
        }
        return true;
    }

    @Override
    public void sort(Comparator c) {
        List.super.sort(c);
    }

    // задание (присвоение) массиву размерности 0
    @Override
    public void clear() {
        this.myData = (T[])new Object[0];
    }

    @Override
    public T get(int index) {
        return this.myData[index];
    }

    @Override
    public T set(int index, T element) {
        this.myData[index] = element;

        return null;
    }

    // добавление элемента по индексу
    @Override
    public void add(int index, T element){
        // временный массив с размерностью + 1
        T[] tempArray = (T[])new Object[size() + 1];
        // заполняю временный массив до нужного индекса
        for (int i = 0; i < index; i++){
            tempArray[i] = this.myData[i];
        }
        // заполняю и-тый элемент
        tempArray[index] = element;
        // если индекс не конец массива, то заполняю оставшуюся часть
        if (index != tempArray.length - 1){
            for (int i = index; i < tempArray.length; i++) {
                tempArray[i] = this.myData[i];
            }
        }
        // увеличиваю (присваиваю новый?) уже исходный массив на 1
        this.myData = (T[])new Object[size() + 1];

        for (int i = 0; i < size(); i++){
            this.myData[i] = tempArray[i];
        }
    }

    // удаление элемента по индексу
    @Override
    public T remove(int index) {
        T[] tempArray = (T[])new Object[size() - 1];

        for (int i = 0; i < index; i++){
            tempArray[i] = this.myData[i];
        }

        for (int i = index; i < tempArray.length; i++){
            tempArray[i] = this.myData[i + 1];
        }

        this.myData = (T[])new Object[size() - 1];

        for (int i = 0; i < size(); i++){
            this.myData[i] = tempArray[i];
        }

        return null;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size(); i++){
            if (this.myData[i] == o)
                return i;
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size() - 1; i >= 0; i--){
            if (this.myData[i] == o)
                return i;
        }

        return -1;
    }

    @Override
    public ListIterator listIterator() {
        return null;
    }

    @Override
    public ListIterator listIterator(int index) {
        return null;
    }

    @Override
    public MyListik<T> subList(int fromIndex, int toIndex) {

        MyListik<T> subListik = new MyListik<>();
        // toIndex вроде не включается
        // ну или минусики убрать
        for(int i = fromIndex - 1; i < toIndex - 1; i++){
            subListik.add(this.myData[i]);
        }

        return subListik;
    }

    @Override
    public boolean retainAll(Collection c) {
        boolean isHave;
        for (T el : this.myData){
            isHave = false;
            for (Object o : c){
                if (el == o)
                    isHave = true;
            }
            if (!isHave)
                remove(el);
        }


        return true;
    }

    @Override
    public boolean removeAll(Collection c) {
        for (Object o : c){
            if(!contains(o))
                return false;
            remove(o);
        }

        return true;
    }

    @Override
    public boolean containsAll(Collection c) {
        for (Object o : c)
            if (!contains(o))
                return false;

        return true;
    }

    @Override
    public T[] toArray(Object[] a) {
        return this.myData;
    }

    @Override
    public String getAuthor() {
        return "Danila Kozlov";
    }
}
