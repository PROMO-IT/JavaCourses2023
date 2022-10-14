package ru.promo.javacore.task;

import java.util.Comparator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class RamproxForkJoinPoolSorting<T> {

    private final Comparator<? super T> comparator;

    private final T[] items;

    private final int size;

    public RamproxForkJoinPoolSorting(T[] items, int size, Comparator<? super T> comparator) {
        this.items = items;
        this.size = size;
        this.comparator = comparator;
    }

    public void sort() {
        Sorting sorting = new Sorting(0, size - 1);
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        pool.invoke(sorting);
        pool.shutdown();
    }

    private class Sorting extends RecursiveAction {

        private static final int THRESHOLD = 3;

        private final int start;

        private final int end;

        public Sorting(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            int size = end - start + 1;
            if(size <= THRESHOLD) {
                manualSort(start, end, comparator);
            } else {
                T median = medianOf3(start, end, comparator);
                int index = partitionItMedian(start, end, median, comparator);
                Sorting left = new Sorting(start, index - 1);
                Sorting right = new Sorting(index + 1, end);
                invokeAll(left, right);
            }
        }

    }

    private int partitionItMedian(int left, int right, T pivot, Comparator<? super T> comparator) {
        int leftPtr = left;
        int rightPtr = right - 1;
        while (true) {
            while(comparator.compare(items[++leftPtr], pivot) < 0);
            while(comparator.compare(items[--rightPtr], pivot) > 0);
            if(leftPtr >= rightPtr) {
                break;
            }
            swap(leftPtr, rightPtr);
        }
        swap(leftPtr, right - 1);
        return leftPtr;
    }

    private T medianOf3(int left, int right, Comparator<? super T> comparator) {
        int center = (left + right) / 2;
        if(comparator.compare(items[left], items[center]) > 0) {
            swap(left, center);
        }
        if(comparator.compare(items[left], items[right]) > 0) {
            swap(left, right);
        }
        if(comparator.compare(items[center], items[right]) > 0) {
            swap(center, right);
        }
        swap(center, right - 1);
        return items[right - 1];
    }

    private void manualSort(int left, int right, Comparator<? super T> comparator) {
        int size = right - left + 1;
        if(size == 1) {
            return;
        }
        if(size == 2) {
            if(comparator.compare(items[left], items[right]) > 0) {
                swap(left, right);
                return;
            }
        }
        if(comparator.compare(items[left], items[right - 1]) > 0) {
            swap(left, right - 1);
        }
        if(comparator.compare(items[left], items[right]) > 0) {
            swap(left, right);
        }
        if(comparator.compare(items[right - 1], items[right]) > 0) {
            swap(right - 1, right);
        }
    }

    private void swap(int index1, int index2) {
        T temp = items[index1];
        items[index1] = items[index2];
        items[index2] = temp;
    }

}
