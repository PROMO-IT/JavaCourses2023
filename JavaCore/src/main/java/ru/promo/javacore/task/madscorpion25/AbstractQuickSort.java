package ru.promo.javacore.task.madscorpion25;

import java.util.Comparator;

public abstract class AbstractQuickSort<T>{
    private T[] data;
    private Comparator<? super T> comparator;
    public final int DEPTH_LIMIT = 400;

    public AbstractQuickSort(T[] data, Comparator<? super T> comparator) {
        this.data = data;
        this.comparator = comparator;
    }

    public abstract void sort(int size);
    public int partition(int left, int right){
        setAverageToRight(left, (left + right) / 2, right);
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
    public void setAverageToRight(int i1, int i2, int i3){
        if((getComparator().compare(getData()[i1], getData()[i2]) > 0 && getComparator().compare(getData()[i1], getData()[i3]) < 0)
                || (getComparator().compare(getData()[i1], getData()[i2]) < 0 && getComparator().compare(getData()[i1], getData()[i3]) > 0)) {
            swapItems(i1, i3);
        }
        else if((getComparator().compare(getData()[i2], getData()[i1]) > 0 && getComparator().compare(getData()[i2], getData()[i3]) < 0)
                || (getComparator().compare(getData()[i2], getData()[i1]) < 0 && getComparator().compare(getData()[i2], getData()[i3]) > 0)) {
            swapItems(i2, i3);
        }
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
