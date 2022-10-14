package ru.promo.javacore.task.madscorpion25;

import java.util.Comparator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelQuickSort<T> extends AbstractQuickSort<T> {
    public static int depth = 0;
    private int DELIMITER;
    private final int DEPTH_LIMIT = 400;
    private final int PARALLEL_RANGE_START = 150_000;

    private ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

    public ParallelQuickSort(T[] data, Comparator<? super T> comparator) {
        super(data, comparator);
    }

    @Override
    public void sort(int size) {
        if(getData().length <= PARALLEL_RANGE_START){
            new QuickSort<T>(getData(), getComparator()).quickSort(0, size - 1);
            return;
        }
        DELIMITER = (getData().length / 100) * (Runtime.getRuntime().availableProcessors() / 2);

        pool.invoke(new ParallelSortAction(0, size - 1));
//        pool.shutdown();
    }
    private class ParallelSortAction extends RecursiveAction{
        private int left;
        private int right;

        public ParallelSortAction(int left, int right) {
            this.left = left;
            this.right = right;
        }

        @Override
        protected void compute() {
            if(left >= right) return;
            if(depth > DEPTH_LIMIT){
                MergeSort.mergeSort(getComparator(), getData(), left, right);
            }
            else if((right - left) <= DELIMITER){
                new QuickSort<T>(getData(), getComparator()).quickSort(left, right);
            }
            else{
                int middle = partition(left, right);
                depth++;
                invokeAll(new ParallelSortAction(left, middle - 1), new ParallelSortAction(middle + 1, right));
            }
        }
    }
}
