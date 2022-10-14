package ru.promo.javacore.task.madscorpion25;

import java.util.Comparator;

public class QuickSort<T> extends AbstractQuickSort<T>{
    public QuickSort(T[] data, Comparator<? super T> comparator) {
        super(data, comparator);
    }

    public void quickSort(int left, int right){
        if(left >= right) return;
        int middle = partition(left, right);

        quickSort(left, middle - 1);
        quickSort(middle + 1, right);
    }

    @Override
    public void sort(int size) {
        quickSort(0, size - 1);
    }
}
