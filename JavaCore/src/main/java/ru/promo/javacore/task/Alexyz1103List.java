package ru.promo.javacore.task;

import java.util.*;


public class Alexyz1103List<T> implements List<T>,AuthorHolder {
    private int size; // поле содержащее размер коллекции
    private Object[] list; // поле содержащие коллекцию ввиде масива
    private static final int DEFAULT_LENGTH = 10;

    //Конструктор без параметров, создает массив на 10 пустых элементов
    //и задает размер коллекции 0
    public Alexyz1103List() {
        this.size = 0;
        this.list = new Object[DEFAULT_LENGTH];
    }
    //Конструктор с параметрой другой коллекции,
    //создает коллекцию нашего типа из любой другой коллекции
    public Alexyz1103List(Collection<? extends T> c){
        Object[] arr = c.toArray();
        if ((size = arr.length) != 0) {
            if (c.getClass() == Alexyz1103List.class) {
                list = arr;
            } else{
                addAll(c);
            }
        } else{
            this.size = 0;
            this.list = new Object[DEFAULT_LENGTH];
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size()==0;
    }

    //метод возвращающий индекс первого вхождения элемента в коллекцию
    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size(); i++) {
            if (list[i].equals(o)) return i;
        }
        return -1;
    }
    //метод возвращающий индекс последнего вхождения элемента в коллекцию
    @Override
    public int lastIndexOf(Object o) {
        for (int i = size()-1; i >= 0; i--) {
            if (list[i].equals(o)) return i;
        }
        return -1;
    }

    //метод для опеределения что объект входит в состав коллекции
    //реализован с помощью indexOf, если у объекта нет индекса, те -1
    //вернет false
    @Override
    public boolean contains(Object o) {
        return indexOf(o)!=-1;
    }
    //Возвращает массив коллекции равный размеру коллекции
    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size()];
        for (int i = 0; i < size; i++) {
            arr[i] = get(i);
        }
        return arr;
    }
    //
    @Override
    public <T1> T1[] toArray(T1[] a) {
        T1[] arr = (T1[]) new Object[size];
        for (int i = 0; i < size(); i++) {
            if (list[i].getClass().getCanonicalName() == arr.getClass().getCanonicalName()){
                arr[i] = (T1) list[i];
            }
        }
        return arr;
    }
    //вспомогательный метод проверяющий вхождение индекса в размер коллекции
    public boolean checkIndex(int index){
        return index >= 0 & index <= size;
    }

    //метод который возвращает элемент коллекции если индекс входит в размер коллекции
    @Override
    public T get(int index) {
        return (checkIndex(index)? (T) list[index]: null);
    }

    //метод заменяет старое значение коллекции в указаном индексе на новое,
    // если индекс выходит за пределы коллекции возвращает null
    @Override
    public T set(int index, T element) {
        if (checkIndex(index)){
            T old = (T) list[index];
            list[index] = element;
            return old;
        }
        return null;
    }

    //Вспомогательный метод который возвращает срез массива коллекции,
    //если стартовый и конечный индексы входят в размерность коллекции
    public Object[] slice(int startIndex, int endIndex){
        Object[] arr = new Object[endIndex-startIndex+1];
        for (int i = 0; i < endIndex-startIndex+1; i++) {
            arr[i] = list[startIndex+i];
        }
        return arr;
    }
    //вспомогательный метод расширяющий массив вдвое
    private void grow(Object[] arr){
        Object[] newArr = new Object[list.length*2];
        for (int i = 0; i < size; i++) {
            newArr[i] = list[i];
        }
        list = newArr;
    }
    //вспомогательный метод проверяет надо ли расширять массив
    private boolean checkGrow(Object[] arr){
        return arr.length == size;
    }

    //метод вставляет элемент в коллекцию в указанной позиции индекса,
    // если индекс входит в диапазон, со смещением остальных элементов
    // в право
    @Override
    public void add(int index, Object o) {
        if (checkIndex(index)){
            if (checkGrow(list)) {grow(list);}
            Object[] arr = slice(index,size-1);
            list[index] = o;
            for (int i = 0; i < arr.length; i++) {
                list[index+i+1] = arr[i];
            }
            size++;
        }
    }

    //Метод для добавления элемента коллекции в конец
    @Override
    public boolean add(Object o) {
        add(size,o);
        return true;
    }

    //Метод удаляет первое вхождение объекта из коллекции
    //со смещением остальных элементов влево
    @Override
    public boolean remove(Object o) {
        remove(indexOf(o));
        return true;
    }

    //Метод удаляет объект коллекции по индексу
    //со смещением остальных элементов влево
    @Override
    public T remove(int index) {
        if (checkIndex(index)) {
            Object[] arr = slice(index + 1, size - 1);
            for (int i = 0; i < arr.length; i++) {
                list[index + i] = arr[i];
            }
        }
        T elemnet = (T) list[--size];
        list[size]=null;
        return elemnet;
    }
    //Метод удаляет все элементы путем сосздания нового пустого массива
    //с изначальным размером и передачи ссылки на новый пустой массив
    @Override
    public void clear() {
        Object[] newArray = new Object[DEFAULT_LENGTH];
        list = newArray;
        size = 0;
    }

    //Метод проверяет на наличие всех элементов другой коллекции,
    // если хотябы один элемент не совпадет вернет false
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object item: c){
            if (indexOf(item) == -1) { return false; }
        }
        return true;
    }

    private void growAll(int index, Collection<? extends T> c){
        if (list.length < this.size() + c.size()){
            Object[] newArray = new Object[this.size()+c.size()];
            for (int i = 0; i < index; i++) {
                newArray[i] = list[i];
            }
            list = newArray;
        }
    }

    private void fastAdd(int index, T item){
        list[index] = item;
    }

    //Метод добавляет все элементы другой коллекции в конец списка
    //возвращает true или false
    @Override
    public boolean addAll(Collection<? extends T> c) {
        return addAll(size,c);
    }

    //Метод добавляет в указаную позицию индекса все элементы из другой коллекции,
    //надо добавить быстрое добавление без лишних проверок
    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (checkIndex(index)){
            Object[] arr = slice(index,size-1);
            this.growAll(index, c);
            for (T item: c){
                fastAdd(index++,item);
                size++;
            }
            for (Object item: arr){  fastAdd(index++, (T) item);  }
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (containsAll(c)){
            for (Object item: c){
                remove(item);
            }
        }
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean flag = false;
        for(Object item: c){
            if (indexOf(item) == -1){
                add(item);
                flag = true;
            }
        }
        return flag;
    }


    @Override
    public Iterator<T> iterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<T> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new ListItr(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        List<T> newlist = new Alexyz1103List<>();
        if (checkIndex(fromIndex) & checkIndex(toIndex)){
            for(int i = fromIndex; i <= toIndex; i++){
                newlist.add((T) list[i]);
            }
        }
        return newlist;
    }
    private String listString(){
        String txt = "";
        for(int i = 0 ; i<size; i++){
            if (i != size-1){
                txt += list[i] + ", ";
            } else{
                txt += list[i];
            }
        }
        return txt;
    }

    @Override
    public String toString() {
        return  "size=" + size +
                ", list=[" + listString() + "]";
    }


    @Override
    public String getAuthor() {
        return "Костылев Александр";
    }


    public class ListItr implements ListIterator<T>{
        int cursor;
        int lastRet = -1;
        ListItr(){
            cursor = 0;
        }
        ListItr(int index){
            cursor = index;
            lastRet = index - 1;
        }
        @Override
        public boolean hasNext() {
            return cursor != size();
        }

        @Override
        public T next() {
            int i = cursor;
            Object[] arr = Alexyz1103List.this.list;
            if (i >= arr.length)
                throw new ConcurrentModificationException();
            cursor= i + 1;
            return (T) arr[lastRet = i];
        }

        @Override
        public boolean hasPrevious() {
            return cursor != 0;
        }

        @Override
        public T previous() {
            return Alexyz1103List.this.get(previousIndex());
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void remove() {
            Alexyz1103List.this.remove(previousIndex());
        }

        @Override
        public void set(T t) {
            Alexyz1103List.this.set(previousIndex(), t);
        }

        @Override
        public void add(T t) {
            Alexyz1103List.this.add(previousIndex(),t);
        }

    }

}
