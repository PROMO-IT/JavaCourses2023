package ru.promo.javacore.task;



import java.util.*;


public class EgorovVSList<T> implements List<T>, AuthorHolder{


    private int size = 0; //размер списка
    private int capacity = 0; //вместимость списка
    private final int CAPACITY = 10; //вместимость по умолчанию
    private Object[] array; //массив каркас для работы списка

    public EgorovVSList() {
        capacity = CAPACITY;
        array = new Object[capacity]; //при отсутсвтии аргументов, создается по умолчанию
    }

    public EgorovVSList(int capacity) {
        this.capacity = capacity;
        array = new Object[capacity]; //при указании размера, создается с этим размером
    }

    private void inscreaseCapacity() {
        capacity = capacity * 2;
        Object[] temp = new Object[capacity];
        for (int i = 0; i < size; i++) {
            temp[i] = array[i];
            array[i] = null;
        }
        array = temp; //увеличивает вместимость в два раза
    }

    private void trimToSizeArray() {
        capacity = size + 1;
        Object[] temp = new Object[capacity];
        System.arraycopy(array, 0, temp, 0, size);
        array = temp; //сокращает вместимость до длины заполненного массива
    }

    @Override
    public int size() {
        return size; //возвращает значение размера
    }

    @Override
    public boolean isEmpty() {
        return size == 0; //если размер == 0, возвращает true
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0; //возвращает индекс объекта, если он есть в списке
    }

    @Override
    public Iterator<T> iterator() {
        MyIterator iterator = new MyIterator();
        return iterator; //возвращает экземпляр класса MyIterator
    }

    @Override
    public Object[] toArray() {
        Object[] temp = new Object[size];
        System.arraycopy(array, 0, temp, 0, size);
        return temp; //преобразует список в массив
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        T1[] temp = (T1[]) new Object[size];
        System.arraycopy(array, 0, temp, 0, size);
        return temp; //преобразует список в массив объектов типа T1
    }

    @Override
    public boolean add(T t) {
        if (size >= capacity) {
            inscreaseCapacity(); //если размер >= вместимости, увеличивает вместимость
        }
        array[size] = t; //добавляет элемент в конец списка,
        size++;     //размер увеличивается
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if ((size == 0)) {
            return false;
        }
        int i;
        for (i = 0; i < size; i++) {
            if (array[i] == null && o == null) {
                break;
            }
            if ((array[i] != null) && (array[i].equals(o))) {
                break;
            }
        }
        if (i < size) {
            size--;
            T[] temp = (T[]) new Object[size];
            System.arraycopy(array, i + 1, array, i, size - i);
            array[size] = null;
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object obj : c) {
            if (c.contains(obj)) {
                return true; //возвращает true, если коллекция-аргумент есть в списке
            }
        }
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (c == null) {
            return false;
        }
        if (c.isEmpty()) {
            return false;
        }
        for (T element : c) {
            add(element); //добавляет Т элементы в конец коллекции
        }
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (c == null) {
            return false;
        }
        if (c.isEmpty() || index < 0) {
            return false;
        }
        if (index > size) {
            index = size;
        }
        for (Object element : c) {
            add(index++, (T) element); // добавляет элементы в коллекцию с индекса i
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        array = new Object[1];
        size = 0;
    }

    @Override
    public T get(int index) {
        if ((index < size) && (index >= 0)) {
            return (T) array[index];
        }
        return null;
    }

    @Override
    public T set(int index, T element) {
        if ((index < size) && (index >= 0)) {
            array[index] = element;
            return element;
        }
        return null;
    }

    @Override
    public void add(int index, T element) {
        T[] temp = (T[]) new Object[size + 1];
        System.arraycopy(array, 0, temp, index - 1, size);
        temp[index] = element;
        System.arraycopy(array, index + 1, temp, size - 1, size);
    }

    @Override
    public T remove(int index) {
        T temp = (T) array[index];
        System.arraycopy(array, index + 1, array, index, size - index - 1);
        size--; // перезаписывает список без элемента-аргумента, затем размер списка сокращается на 1
        return temp;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(0)) {
                return i;  //возвращает индекс первого вхождения аргумента о
            }
        }
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size; i > 0; i--) {
            if (array[i].equals(0)) {
                return i; //возвращает индекс последнего вхождения аргумента о
            }
        }
        return 0;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new MyIterator(0);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new MyIterator(index);
    }

    @Override
    public EgorovVSList<T> subList(int fromIndex, int toIndex) {
        EgorovVSList<T> temp = new EgorovVSList<T>();
        System.arraycopy(array, fromIndex, temp, toIndex+1, size);
        trimToSizeArray();
        return temp; //возвращает вхождение под-списка в список MyList
    }

    @Override
    public String getAuthor() {
        String Author = "Viktor Egorov";
        return Author;
    }

    class MyIterator implements ListIterator<T> {
        int index;
        int cursor = -1; //т.н. указатель

        public MyIterator(int index) {
            this.index = index;  //итератор стартует с индекса index
        }

        public MyIterator() {
            this(0); //итератор стартует с индекса 0
        }

        @Override
        public boolean hasNext() {
            return index < size; //возвращает true, если есть следующий после указателя элемент в Iterable
        }

        @Override
        public T next() {
            if (hasNext()) {
                cursor = index;
                return (T) array[index++]; // возвращает элемент следующий после текущего значения указателя

            }
            throw new NoSuchElementException();
        }

        @Override
        public boolean hasPrevious() {
            return index > 0;   //возвращает true, если есть предыдущий перед указателем элемент в Iterable
        }

        @Override
        public T previous() {
            if (hasPrevious()) {
                cursor = previousIndex();
                index--;
                return (T) array[cursor];  // возвращает элемент следующий перед текущим значением указателя
            }
            throw new NoSuchElementException();
        }

        @Override
        public int nextIndex() {
            if (index < size) {
                return index + 1;    //возвращает индекс следующего после указателя элемента в Iterable
            }
            throw new NoSuchElementException();
        }

        @Override
        public int previousIndex() {
            if (index == 0) {
                return -1;
            }
            return index - 1; //возвращает индекс предыдущего перед указателем элемента в Iterable
        }

        @Override
        public void remove() {
            if (cursor == -1) {
                throw new IllegalStateException();
            }
            EgorovVSList.this.remove(cursor); //удаляет значение последнего next или previous
            index--;
            cursor = -1;
        }

        @Override
        public void set(T t) {
            if (cursor == -1) {
                throw new IllegalStateException();
            } else {
            }
            EgorovVSList.this.set(cursor, t); //заменяет значение элемента array[cursor]
        }

        @Override
        public void add(T t) {
            if (cursor == -1) {
                throw new IllegalStateException();
            } else {
                EgorovVSList.this.add(cursor, t); //добавляет элемент t по индексу cursor
            }
        }
    }
}
