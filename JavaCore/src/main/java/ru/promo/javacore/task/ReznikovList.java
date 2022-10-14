package ru.promo.javacore.task;

import lombok.SneakyThrows;
import ru.promo.javacore.task.reznikov.Node;
import ru.promo.javacore.task.reznikov.ReznikovIterator;
import ru.promo.javacore.task.reznikov.ReznikovListIterator;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ReznikovList<T> implements List<T>, AuthorHolder {

    Node<T> head = null;
    Node<T> tail = null;

    public ReznikovList() {
    }

    public ReznikovList(Collection<T> collection) {
        addAll(collection);
    }

    @Override
    public int size() {
        int i = 0;
        var ptr = head;
        while (ptr != null) {
            i++;
            ptr = ptr.getNextNode();
        }
        return i;
    }

    int size(Node<T> head, Node<T> tail) {
        int i = 0;
        var ptr = head;
        while (ptr != null) {
            i++;
            ptr = ptr.getNextNode();
        }
        return i;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public boolean contains(Object o) {
        Node<T> ptr = head;
        while (ptr != null) {
            if (Objects.equals(ptr.getObject(), o)) {
                return true;
            }
            ptr = ptr.getNextNode();
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new ReznikovIterator<>(head, this);
    }

    @Override
    public Object[] toArray() {
        int size = size();
        Object[] array = new Object[size];
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            array[i] = node.getObject();
            node = node.getNextNode();
        }
        return array;
    }

    @Override
    public <E> E[] toArray(E[] a) {
        int size = size();
        E[] array = (E[]) new Object[size];
        if (size == 0) {
            return array;
        }

        int i = 0;
        Node<T> node = head;
        while (node != null) {
            array[i] = (E) node.getObject();
            node = node.getNextNode();
            i++;
        }
        return array;
    }

    @Override
    public boolean add(T o) {
        Node<T> node = new Node<>(o);
        if (head == null) {
            tail = node;
            head = node;
            return true;
        }

        node.setPreviousNode(tail);
        tail.setNextNode(node);
        tail = node;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (head == null) {
            return false;
        } else {
            if (o.equals(head.getObject())) {
                head = head.getNextNode();
                head.setPreviousNode(null);
                return true;
            }
        }

        Node<T> node = head;
        while (node != null) {
            if (o.equals(node.getObject())) {
                if (tail == node) {
                    tail = node.getPreviousNode();
                }
                if (node.getNextNode() != null) {
                    node.getNextNode().setPreviousNode(node.getPreviousNode());
                }
                if (node.getPreviousNode() != null) {
                    node.getPreviousNode().setNextNode(node.getNextNode());
                }
                return true;
            }
            node = node.getNextNode();
        }

        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        /*  Я использовал здесь стрим. Не считаю это нарушением, так как для получения всех элементов коллекции
         * необходимо воспользоваться внутренней реализацией итератора, поэтому нет принципиального различия между
         * использованием foreach(:), iterator() или stream()
         * Если это принципиально неприемлемо, могу исправить
         * */
        return c.stream().filter(this::add).count() > 0;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        int size = size();
        if (index >= size) {
            return false;
        }

        Node<T> sub_head = null;
        Node<T> sub_tail = null;
        for (var obj : c
        ) {
            Node<T> node = new Node<>(obj);
            if (sub_head == null) {
                sub_head = node;
            } else {
                sub_tail.setNextNode(node);
                node.setPreviousNode(sub_tail);
            }
            sub_tail = node;
        }

        if (index == 0) {
            assert sub_tail != null;
            sub_tail.setNextNode(head);
            head.setPreviousNode(sub_tail);
            head = sub_head;
            return true;
        } else if (index == size - 1) {
            tail.setNextNode(sub_head);
            assert sub_head != null;
            sub_head.setPreviousNode(tail);
            tail = sub_tail;
            return true;
        } else {
            Node<T> ptr = head;
            for (int i = 0; i < index - 1; i++) {
                ptr = ptr.getNextNode();
            }
            assert sub_tail != null;
            sub_tail.setNextNode(ptr.getNextNode());
            sub_head.setPreviousNode(ptr);
            ptr.getNextNode().setPreviousNode(sub_tail);
            ptr.setNextNode(sub_head);
            return true;
        }
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
    }

    @Override
    public T get(int index) {
        var node = getNode(index, head, tail);
        return node == null ? null : node.getObject();
    }

    private Node<T> getNode(int index, Node<T> head, Node<T> tail) {
        if (index >= size(head, tail) || index < 0) {
            return null;
        }

        Node<T> ptr = head;
        int i = 0;
        while (i < index) {
            ptr = ptr.getNextNode();
            i++;
        }
        return ptr;
    }

    @Override
    public T set(int index, T element) {
        Node<T> ptr = head;
        for (int i = 0; i < index; i++) {
            ptr = ptr.getNextNode();
        }
        T prev_obj = ptr.getObject();
        ptr.setObject(element);
        return prev_obj;
    }

    @Override
    public void add(int index, T element) {
        int size = size();
        size++;
        Node<T> node = new Node<>(element);
        if (index == 0) {
            node.setNextNode(head);
            head.setPreviousNode(node);
            head = node;
            return;
        } else if (index == size - 1) {
            tail.setNextNode(node);
            node.setPreviousNode(tail);
            tail = node;
            return;
        }
        Node<T> ptr = head;
        for (int i = 0; i < index - 1; i++) {
            ptr = ptr.getNextNode();
        }
        node.setNextNode(ptr.getNextNode());
        node.setPreviousNode(ptr);
        ptr.getNextNode().setPreviousNode(node);
        ptr.setNextNode(node);
    }

    @Override
    public T remove(int index) {

        int size = size();
        if (size <= 0 || size <= index) {
            return null;
        } else if (head == null) {
            return null;
        } else if (index == 0) {
            T body = head.getObject();
            if (head.getNextNode() != null) {
                head.getNextNode().setPreviousNode(null);
                head = head.getNextNode();
            } else {
                head = null;
                tail = null;
            }
            return body;
        } else if (index == size - 1) {
            T body = tail.getObject();
            tail.getPreviousNode().setNextNode(null);
            tail = tail.getPreviousNode();
            return body;
        } else {
            Node<T> ptr = head;
            for (int i = 0; i < index; i++) {
                ptr = ptr.getNextNode();
            }
            T body = ptr.getObject();
            ptr.getNextNode().setPreviousNode(ptr.getPreviousNode());
            ptr.getPreviousNode().setNextNode(ptr.getNextNode());
            return body;
        }
    }

    @Override
    public int indexOf(Object o) {
        Node<T> ptr = head;
        int index = 0;
        while (ptr != null) {
            if (ptr.getObject().equals(o)) {
                return index;
            }
            ptr = ptr.getNextNode();
            index++;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        Node<T> ptr = head;
        int previousIndex = -1;
        int index = 0;
        while (ptr != null) {
            if (Objects.equals(ptr.getObject(), o)) {
                previousIndex = index;
            }
            ptr = ptr.getNextNode();
            index++;
        }
        return previousIndex;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ReznikovListIterator<>(head, this);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.getNextNode();
        }
        return new ReznikovListIterator<>(node, this);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        ReznikovList<T> newList = new ReznikovList<>();
        int i = 0;
        Node<T> node = head;
        while (node != null && i < toIndex) {
            if (i >= fromIndex) {
                newList.add(node.getObject());
            }
            node = node.getNextNode();
            i++;
        }
        return newList;
    }

    @Override
    public boolean retainAll(Collection c) {
        Node<T> node = head;
        while (node != null) {
            if (!c.contains(node.getObject())) {
                remove(node.getObject());
            }
            node = node.getNextNode();
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection c) {
        Node<T> node = head;
        while (node != null) {
            if (c.contains(node.getObject())) {
                remove(node.getObject());
            }
            node = node.getNextNode();
        }
        return true;
    }

    @Override
    public boolean containsAll(Collection c) {
        for (var node : c) {
            if (!this.contains(node)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getAuthor() {
        return "Резников С.А. Группа 2";
    }

    /**
     * До и включая 7000 выполняет соритировку рассческой, после - при помощи {@link ForkJoinPool}.
     * Граница разделения получена опытно.
     */
    @Override
    public void sort(Comparator<? super T> c) {
        if (size() <= 7000) {
            sortCurrent(c);
        } else {
            ForkSorting counter = new ForkSorting(this, c);
            ForkJoinPool forkJoinPool = new ForkJoinPool();
            forkJoinPool.invoke(counter);
        }
    }

    /**
     * Сортировка рассческой. Использует метод {@link #replaceNodes(Node, Node)}.
     */
    public void sortCurrent(Comparator<? super T> c) {
        int gap = size(head, tail);
        boolean swapped = true;
        while (gap > 1 || swapped) {
            if (gap > 1)
                gap = (int) (gap / 1.247331);

            int i = 0;
            swapped = false;
            Node<T> first = head;
            Node<T> last = getNode(i + gap, head, tail);
            while (last != null) {
                if (c.compare(first.getObject(), last.getObject()) > 0) {
                    replaceNodes(first, last);
                    swapped = true;
                    var tmpNode = first;
                    first = last;
                    last = tmpNode;
                }
                first = first.getNextNode();
                last = last.getNextNode();
                i++;
            }
        }
    }

    /**
     * Метод меняет местами 2 {@link Node<T>}.
     */
    private void replaceNodes(Node<T> first, Node<T> second) {
        Node<T> firstNext = first.getNextNode();
        Node<T> firstPrev = first.getPreviousNode();
        if (head == first) {
            head = second;
        } else if (tail == second) {
            tail = first;
        }

        if (first.getNextNode() == second) {
            first.setNextNode(second.getNextNode());
            first.setPreviousNode(second);
            if (second.getNextNode() != null) {
                second.getNextNode().setPreviousNode(first);
            }
            second.setNextNode(first);
            second.setPreviousNode(firstPrev);
            if (firstPrev != null) {
                firstPrev.setNextNode(second);
            }
        } else {
            first.setNextNode(second.getNextNode());
            first.setPreviousNode(second.getPreviousNode());
            if (second.getNextNode() != null) {
                second.getNextNode().setPreviousNode(first);
            }
            second.getPreviousNode().setNextNode(first);
            second.setPreviousNode(firstPrev);
            second.setNextNode(firstNext);
            if (firstPrev != null) {
                firstPrev.setNextNode(second);
            }
            firstNext.setPreviousNode(second);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");

        var node = head;
        while (node != null) {
            builder.append(node.getObject().toString());
            if (node.getNextNode() != null) {
                builder.append(", ");
            }
            node = node.getNextNode();
        }

        builder.append("]");
        return builder.toString();
    }


    /**
     * Сливает получившиеся отсортированные связные списки. Для этого берет элемент входного списка и находит
     * первый больший элемент во внутреннем списке. Затем элемент вставляется во внутренний список перед большим,
     * берется следующий элемент входного списка и продолжается поиск большего элемента.
     */
    void merge(ReznikovList<T> list, Comparator<? super T> comparator) {
        var second_element = list.head;
        var first_element = head;
        while (second_element != null) {
            var nextSecondElement = second_element.getNextNode();

            //searching
            while (first_element != null &&
                    comparator.compare(second_element.getObject(), first_element.getObject()) > 0) {
                first_element = first_element.getNextNode();
            }
            //---

            //inserting
            if (first_element == null) {
                tail.setNextNode(second_element);
                second_element.setPreviousNode(tail);
                second_element.setNextNode(null);
                tail = second_element;
            } else if (first_element.getPreviousNode() == null) {
                second_element.setNextNode(head);
                head.setPreviousNode(second_element);
                second_element.setPreviousNode(null);
                head = second_element;
            } else {
                second_element.setNextNode(first_element);
                second_element.setPreviousNode(first_element.getPreviousNode());
                first_element.getPreviousNode().setNextNode(second_element);
                first_element.setPreviousNode(second_element);
            }
            //---

            second_element = nextSecondElement;
        }
    }

    //Со связными списком работает гораздо медленнее, чем с массивом
    /*
     * В методе compute разделяем список на два, создаем еще два экземпляра класса, где работаем
     * уже с половинами исходного списка. Получаем отсортированные половины, которые вставляем друг в друга, получая
     * один отсортированный список. Возвращаем список.
     *
     * Таким образом, мы дробим списки до списков по 4 элемента, сортируем их рассческой (на таком размере это просто сортировка перебором),
     * и потом постепенно склеиваем их между собой. В конце получается отсортированный исходный список.
     */
    class ForkSorting extends RecursiveTask<ReznikovList<T>> {

        ReznikovList<T> list;
        Comparator<? super T> comparator;

        public ForkSorting(ReznikovList<T> list, Comparator<? super T> comparator) {
            this.list = list;
            this.comparator = comparator;
        }

        @SneakyThrows
        @Override
        protected ReznikovList<T> compute() {
            int size = list.size();
            if (size <= 4) {
                list.sortCurrent(comparator);
                return list;
            }
            var middleElem = getNode((size / 2) - 1, list.head, list.tail);
            assert middleElem != null;
            var nextFromMiddle = middleElem.getNextNode();

            middleElem.setNextNode(null);
            nextFromMiddle.setPreviousNode(null);

            var firstList = new ReznikovList<T>();
            firstList.head = list.head;
            firstList.tail = middleElem;

            var secondList = new ReznikovList<T>();
            secondList.head = nextFromMiddle;
            secondList.tail = list.tail;

            ForkSorting firstHalf =
                    new ForkSorting(firstList, comparator);
            ForkSorting secondHalf =
                    new ForkSorting(secondList, comparator);
            firstHalf.fork();
            secondHalf.fork();

            var result = firstHalf.join();
            result.merge(secondHalf.join(), comparator);
            return result;
        }
    }
}
