package com.square;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 升序排列程序主类
 * 提供多种排序算法实现
 * 
 * @author Square
 * @version 1.0
 */
public class SortProgram {
    
    /**
     * 冒泡排序 - 升序
     * 时间复杂度: O(n²)
     * 空间复杂度: O(1)
     */
    public static void bubbleSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    // 交换元素
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            // 如果没有发生交换,说明已经有序
            if (!swapped) {
                break;
            }
        }
    }
    
    /**
     * 选择排序 - 升序
     * 时间复杂度: O(n²)
     * 空间复杂度: O(1)
     */
    public static void selectionSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            // 找到最小元素的索引
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            // 交换到当前位置
            if (minIndex != i) {
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
        }
    }
    
    /**
     * 插入排序 - 升序
     * 时间复杂度: O(n²)
     * 空间复杂度: O(1)
     */
    public static void insertionSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;
            
            // 将大于key的元素向后移动
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }
    
    /**
     * 快速排序 - 升序
     * 时间复杂度: O(n log n) 平均情况
     * 空间复杂度: O(log n)
     */
    public static void quickSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        quickSortHelper(arr, 0, arr.length - 1);
    }
    
    private static void quickSortHelper(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            quickSortHelper(arr, low, pivotIndex - 1);
            quickSortHelper(arr, pivotIndex + 1, high);
        }
    }
    
    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        
        return i + 1;
    }
    
    /**
     * 归并排序 - 升序
     * 时间复杂度: O(n log n)
     * 空间复杂度: O(n)
     */
    public static void mergeSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        mergeSortHelper(arr, 0, arr.length - 1);
    }
    
    private static void mergeSortHelper(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSortHelper(arr, left, mid);
            mergeSortHelper(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }
    
    private static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        
        int[] leftArr = new int[n1];
        int[] rightArr = new int[n2];
        
        System.arraycopy(arr, left, leftArr, 0, n1);
        System.arraycopy(arr, mid + 1, rightArr, 0, n2);
        
        int i = 0, j = 0, k = left;
        
        while (i < n1 && j < n2) {
            if (leftArr[i] <= rightArr[j]) {
                arr[k++] = leftArr[i++];
            } else {
                arr[k++] = rightArr[j++];
            }
        }
        
        while (i < n1) {
            arr[k++] = leftArr[i++];
        }
        
        while (j < n2) {
            arr[k++] = rightArr[j++];
        }
    }
    
    /**
     * 打印数组
     */
    public static void printArray(int[] arr) {
        System.out.println(Arrays.toString(arr));
    }
    
    /**
     * 主函数 - 演示各种排序算法
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== 升序排列程序 ===");
        System.out.println("请输入要排序的数字个数:");
        int n = scanner.nextInt();
        
        int[] arr = new int[n];
        System.out.println("请输入 " + n + " 个整数(用空格分隔):");
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
        }
        
        System.out.println("\n原始数组:");
        printArray(arr);
        
        System.out.println("\n请选择排序算法:");
        System.out.println("1. 冒泡排序");
        System.out.println("2. 选择排序");
        System.out.println("3. 插入排序");
        System.out.println("4. 快速排序");
        System.out.println("5. 归并排序");
        System.out.print("请输入选项(1-5): ");
        
        int choice = scanner.nextInt();
        
        // 创建数组副本用于排序
        int[] sortedArr = Arrays.copyOf(arr, arr.length);
        
        long startTime = System.nanoTime();
        
        switch (choice) {
            case 1:
                bubbleSort(sortedArr);
                System.out.println("\n使用冒泡排序:");
                break;
            case 2:
                selectionSort(sortedArr);
                System.out.println("\n使用选择排序:");
                break;
            case 3:
                insertionSort(sortedArr);
                System.out.println("\n使用插入排序:");
                break;
            case 4:
                quickSort(sortedArr);
                System.out.println("\n使用快速排序:");
                break;
            case 5:
                mergeSort(sortedArr);
                System.out.println("\n使用归并排序:");
                break;
            default:
                System.out.println("无效选项,使用快速排序:");
                quickSort(sortedArr);
        }
        
        long endTime = System.nanoTime();
        
        System.out.println("排序后数组:");
        printArray(sortedArr);
        
        double executionTime = (endTime - startTime) / 1_000_000.0;
        System.out.printf("\n排序耗时: %.3f 毫秒\n", executionTime);
        
        scanner.close();
    }
}
