package ru.promo.javacore.task;

import ru.promo.javacore.task.madscorpion25.AbstractQuickSort;
import ru.promo.javacore.task.madscorpion25.ParallelQuickSort;

import java.lang.reflect.Array;
import java.util.*;

public class MadScorpion25List<T> implements List<T>, AuthorHolder{
    private static final int DEFAULT_LIST_CAPACITY = 10;
    private static final double DEFAULT_CAPACITY_INCREASE_FACTOR = 1.4d;
    private static final String AUTHOR_NAME = "Malkova Anastasia";
    private int size;
    private int capacity;
    private final double INCREASE_FACTOR;
    private T[] listData;

    public MadScorpion25List(){
        this(DEFAULT_LIST_CAPACITY, DEFAULT_CAPACITY_INCREASE_FACTOR);
    }
    public MadScorpion25List(int capacity){
        this(capacity, DEFAULT_CAPACITY_INCREASE_FACTOR);
    }
    public MadScorpion25List(int capacity, double increaseFactor){
        if(capacity <= 0){
            throw new IllegalArgumentException("Illegal list capacity: " + capacity);
        }
        if(increaseFactor <= 1.0d){
            throw new IllegalArgumentException("Illegal list increase factor: " + increaseFactor);
        }
        size = 0;
        this.capacity = capacity;
        INCREASE_FACTOR = increaseFactor;
        listData = (T[])new Object[capacity];
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
        for(int i = 0; i < size; i++){
            if(listData[i].equals(o)) return true;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new CustomIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];

        copyArray(listData, 0, array, 0, size);
        return array;
    }

    @Override
    public <T1> T1[] toArray(T1[] templateArray) {
        templateArray = (T1[]) Array.newInstance(templateArray.getClass().getComponentType(), size);
        copyArrayWithCast(listData, 0, templateArray, 0, size);
        return templateArray;
    }

    @Override
    public boolean add(T item) {
        if(item == null) return false;
        checkLegalListCapacity(1);
        listData[size++] = item;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if(index < 0) return false;
        moveBackwardFromIndex(index);
        listData[--size] = null;
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        if(collection == null) return false;
        for(Object obj : collection){
            if(!contains(obj)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        if(collection == null) return false;

        checkLegalListCapacity(collection.size());
        for(T obj : collection){
            add(obj);
        }
        return true;
    }

    @Override
    public boolean addAll(int i, Collection<? extends T> collection) {
        if(i < 0 || i > size)
            throw new IllegalArgumentException("Insert index is out of range: " + i);
        if(collection == null) return false;

        checkLegalListCapacity(collection.size());
        for(T obj : collection){
            add(i, obj);
            i++;
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        if(collection == null) return false;
        for(Object obj : collection){
            remove(obj);
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        if(collection == null) return false;
        for(int i = 0; i < size; i++){
            boolean toRemove = true;
            for(Object obj : collection){
                if(listData[i].equals(obj)) {
                    toRemove = false;
                    break;
                }
            }
            if(toRemove){
                remove(i);
                i--;
            }
        }
        return true;
    }

    @Override
    public void clear() {
        for(int i = 0; i < size; i++){
            listData[i] = null;
        }
        size = 0;
    }

    @Override
    public T get(int i) {
        if(i < 0 || i >= size) return null;
        return listData[i];
    }

    @Override
    public T set(int i, T item) {
        if(i < 0 || i >= size)
            throw new IllegalArgumentException("Set index is out of range: " + i);
        listData[i] = item;
        return item;
    }

    @Override
    public void add(int i, T item) {
        if(i < 0 || i > size)
            throw new IllegalArgumentException("Insert index is out of range: " + i);
        if(item == null) return;
        checkLegalListCapacity(1);
        moveForwardFromIndex(i);
        listData[i] = item;
        size++;
    }

    @Override
    public T remove(int i) {
        if(i < 0 || i >= size) return null;
        T removeItem = listData[i];
        moveBackwardFromIndex(i);
        listData[--size] = null;
        return removeItem;
    }

    @Override
    public int indexOf(Object o) {
        for(int i = 0; i < size; i++){
            if(listData[i].equals(o)) return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for(int i = size - 1; i > -1; i--){
            if(listData[i].equals(o)) return i;
        }
        return  -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new CustomIterator();
    }

    @Override
    public ListIterator<T> listIterator(int i) {
        if(i < 0 || i >= size)
            throw new IllegalArgumentException("Iterator index is out of range: " + i);
        return new CustomIterator(i);
    }

    @Override
    public List<T> subList(int inclusive, int exclusive) {
        if(inclusive >= exclusive || inclusive < 0 || exclusive > size)
            throw new IllegalArgumentException("Index for sublist is out of range: [" + inclusive + "; " + exclusive + ")");
        MadScorpion25List<T> newList = new MadScorpion25List<>(exclusive - inclusive + 1);
        for(int j = inclusive; j < exclusive; j++){
            newList.add(listData[j]);
        }
        return newList;
    }

    @Override
    public void sort(Comparator<? super T> c) {
        AbstractQuickSort<T> sort = new ParallelQuickSort<T>(listData, c);
        sort.sort(size);
    }

    private void checkLegalListCapacity(int extraSize){
        int newSize = size + extraSize;
        if(capacity >= newSize) return;
        while(capacity < newSize)
            capacity *= INCREASE_FACTOR;
        increaseListCapacity(capacity);
    }

    private void increaseListCapacity(int capacity){
        T[] newList = (T[])new Object[capacity];
        copyArray(listData, 0, newList, 0, size);
        listData = newList;
    }

    private void copyArray(T[] res, int startRes, Object[] finArr, int startFin, int itemsCount){
        for(int i = 0; i < itemsCount; i++){
            finArr[startFin] = res[startRes];
            startFin++;
            startRes++;
        }
    }

    private <T1> void copyArrayWithCast(Object[] res, int startRes, T1[] finArr, int startFin, int itemsCount){
        for(int i = 0; i < itemsCount; i++){
            finArr[startFin] = (T1)res[startRes];
            startFin++;
            startRes++;
        }
    }

    private void moveForwardFromIndex(int i){
        for(int j = size; j > i; j--){
            listData[j] = listData[j - 1];
        }
    }
    private void moveBackwardFromIndex(int i){
        for(int j = i; j < size; j++){
            listData[j] = listData[j + 1];
        }
    }

    @Override
    public String getAuthor() {
        return AUTHOR_NAME;
    }

    private class CustomIterator implements ListIterator<T>{
        private int cursor;
        public CustomIterator(){
            cursor = 0;
        }

        public CustomIterator(int cursor){
            this.cursor = cursor;
        }

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public T next() {
            if(cursor == size)
                throw new NoSuchElementException();
            return listData[cursor++];
        }

        @Override
        public boolean hasPrevious() {
            return cursor != 0;
        }

        @Override
        public T previous() {
            if(cursor == 0)
                throw new NoSuchElementException();
            return listData[--cursor];
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
            MadScorpion25List.this.remove(cursor);
        }

        @Override
        public void set(T item) {
            listData[cursor] = item;
        }

        @Override
        public void add(T item) {
            MadScorpion25List.this.add(cursor, item);
            next();
        }
    }
}

