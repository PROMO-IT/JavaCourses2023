package ru.promo.javacore.task.madscorpion25;

import java.util.Comparator;

public abstract class AbstractQuickSort<T>{
    private T[] data;
    private Comparator<? super T> comparator;

    public AbstractQuickSort(T[] data, Comparator<? super T> comparator) {
        this.data = data;
        this.comparator = comparator;
    }

    public abstract void sort(int size);
    public int partition(int left, int right){
        T supportItem = data[right];
        int leftCursor = left;
        int rightCursor = right;
        while(true){
            while(comparator.compare(data[leftCursor], supportItem) < 0 && leftCursor < rightCursor) leftCursor++;
            while(comparator.compare(data[rightCursor], supportItem) >= 0 && leftCursor < rightCursor) rightCursor--;
            if(leftCursor >= rightCursor) break;
            swapItems(leftCursor, rightCursor);
        }
        swapItems(right, rightCursor);
        return rightCursor;
    }
    private void swapItems(int firstIndex, int secondIndex){
        T temp = data[firstIndex];
        data[firstIndex] = data[secondIndex];
        data[secondIndex] = temp;
    }

    public T[] getData() {
        return data;
    }

    public void setData(T[] data) {
        this.data = data;
    }

    public Comparator<? super T> getComparator() {
        return comparator;
    }

    public void setComparator(Comparator<? super T> comparator) {
        this.comparator = comparator;
    }
}
