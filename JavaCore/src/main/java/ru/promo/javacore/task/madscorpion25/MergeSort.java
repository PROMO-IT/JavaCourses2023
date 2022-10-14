package ru.promo.javacore.task.madscorpion25;

import java.util.Comparator;

public class MergeSort {
    public static <T> T[] sort(Comparator<? super T> comparator, T[] data){
        if(data.length == 0) return data;
        mergeSort(comparator, data, 0, data.length - 1);
        return data;
    }

    public static <T> void mergeSort(Comparator<? super T> comparator, T[] data, int startIndex, int endIndex){
        if(endIndex - startIndex < 1) return;
        int middle = (endIndex + startIndex) / 2;

        mergeSort(comparator, data, startIndex, middle);
        mergeSort(comparator, data, middle + 1, endIndex);

        merge(comparator, data, startIndex, middle, endIndex);
    }
    private static <T> void merge(Comparator<? super T> comparator, T[] data, int startIndex, int middle, int endIndex){
        if(startIndex >= endIndex) return;

        Object[] mergeArray = new Object[endIndex - startIndex + 1];

        int leftHalf = startIndex;
        int rightHalf = middle + 1;
        int mergePos = 0;
        while(leftHalf <= middle && rightHalf <= endIndex){
            if(comparator.compare(data[leftHalf], data[rightHalf]) <= 0){
                mergeArray[mergePos] = data[leftHalf];
                leftHalf++;
            }
            else if(comparator.compare(data[leftHalf], data[rightHalf]) > 0){
                mergeArray[mergePos] = data[rightHalf];
                rightHalf++;
            }
            mergePos++;
        }
        while(leftHalf <= middle){
            mergeArray[mergePos] = data[leftHalf];
            leftHalf++;
            mergePos++;
        }
        while(rightHalf <= endIndex){
            mergeArray[mergePos] = data[rightHalf];
            rightHalf++;
            mergePos++;
        }
        System.arraycopy(mergeArray, 0, data, startIndex, mergeArray.length);
    }
}
