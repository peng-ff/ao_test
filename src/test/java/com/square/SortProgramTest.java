package com.square;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;

if (!"com.square".equals(SortProgramTest.class.getPackage().getName())) {
    System.out.println("Warning: SortProgramTest is not in the com.square package.");
}

/**
 * SortProgram 排序测试类
 * 测试各种排序算法的正确性和边界情况
 */
public class SortProgramTest {
    
    /**
     * 验证数组是否升序排列
     */
    private boolean isAscending(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                System.out.println("数组在索引 " + i + " 处未升序: " + arr[i] + " > " + arr[i + 1]);
                return false;
            }
        }
        return true;
    }
    
    /**
     * 测试冒泡排序
     */
    @Test
    public void testBubbleSort() {
        System.out.println("测试冒泡排序...");
        
        // 测试正常情况
        int[] arr1 = {64, 34, 25, 12, 22, 11, 90};
        SortProgram.bubbleSort(arr1);
        assertTrue("冒泡排序应该生成升序数组", isAscending(arr1));
        
        // 测试已排序数组
        int[] arr2 = {1, 2, 3, 4, 5};
        SortProgram.bubbleSort(arr2);
        assertTrue("已排序数组应保持升序", isAscending(arr2));
        
        // 测试倒序数组
        int[] arr3 = {5, 4, 3, 2, 1};
        SortProgram.bubbleSort(arr3);
        assertTrue("倒序数组应变为升序", isAscending(arr3));
        
        // 测试空数组
        int[] arr4 = {};
        SortProgram.bubbleSort(arr4);
        assertEquals("空数组长度应为0", 0, arr4.length);
        
        // 测试单元素数组
        int[] arr5 = {42};
        SortProgram.bubbleSort(arr5);
        assertEquals("单元素数组应保持不变", 42, arr5[0]);
        
        System.out.println("✓ 冒泡排序测试通过");
    }
    
    /**
     * 测试选择排序
     */
    @Test
    public void testSelectionSort() {
        System.out.println("测试选择排序...");
        
        int[] arr1 = {64, 25, 12, 22, 11};
        SortProgram.selectionSort(arr1);
        assertTrue("选择排序应该生成升序数组", isAscending(arr1));
        
        int[] arr2 = {-5, 2, 0, -3, 8};
        SortProgram.selectionSort(arr2);
        assertTrue("包含负数的数组应升序排列", isAscending(arr2));
        
        int[] arr3 = {3, 3, 3, 3};
        SortProgram.selectionSort(arr3);
        assertTrue("相同元素数组应保持升序", isAscending(arr3));
        
        System.out.println("✓ 选择排序测试通过");
    }
    
    /**
     * 测试插入排序
     */
    @Test
    public void testInsertionSort() {
        System.out.println("测试插入排序...");
        
        int[] arr1 = {12, 11, 13, 5, 6};
        SortProgram.insertionSort(arr1);
        assertTrue("插入排序应该生成升序数组", isAscending(arr1));
        
        int[] arr2 = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        SortProgram.insertionSort(arr2);
        assertTrue("大倒序数组应变为升序", isAscending(arr2));
        
        System.out.println("✓ 插入排序测试通过");
    }
    
    /**
     * 测试快速排序
     */
    @Test
    public void testQuickSort() {
        System.out.println("测试快速排序...");
        
        int[] arr1 = {10, 7, 8, 9, 1, 5};
        SortProgram.quickSort(arr1);
        assertTrue("快速排序应该生成升序数组", isAscending(arr1));
        
        int[] arr2 = {100, 50, 25, 75, 10, 90, 30};
        SortProgram.quickSort(arr2);
        assertTrue("快速排序应正确处理多元素", isAscending(arr2));
        
        int[] arr3 = {1};
        SortProgram.quickSort(arr3);
        assertEquals("单元素快速排序应保持不变", 1, arr3[0]);
        
        System.out.println("✓ 快速排序测试通过");
    }
    
    /**
     * 测试归并排序
     */
    @Test
    public void testMergeSort() {
        System.out.println("测试归并排序...");
        
        int[] arr1 = {38, 27, 43, 3, 9, 82, 10};
        SortProgram.mergeSort(arr1);
        assertTrue("归并排序应该生成升序数组", isAscending(arr1));
        
        int[] arr2 = {-10, -5, 0, 5, 10};
        SortProgram.mergeSort(arr2);
        assertTrue("包含负数的归并排序应升序", isAscending(arr2));
        
        System.out.println("✓ 归并排序测试通过");
    }
    
    /**
     * 测试所有排序算法的一致性
     */
    @Test
    public void testAllSortsConsistency() {
        System.out.println("测试所有排序算法一致性...");
        
        int[] original = {64, 34, 25, 12, 22, 11, 90, 88, 45, 50, 30, 17};
        
        int[] arr1 = Arrays.copyOf(original, original.length);
        int[] arr2 = Arrays.copyOf(original, original.length);
        int[] arr3 = Arrays.copyOf(original, original.length);
        int[] arr4 = Arrays.copyOf(original, original.length);
        int[] arr5 = Arrays.copyOf(original, original.length);
        
        SortProgram.bubbleSort(arr1);
        SortProgram.selectionSort(arr2);
        SortProgram.insertionSort(arr3);
        SortProgram.quickSort(arr4);
        SortProgram.mergeSort(arr5);
        
        assertArrayEquals("冒泡排序和选择排序结果应一致", arr1, arr2);
        assertArrayEquals("选择排序和插入排序结果应一致", arr2, arr3);
        assertArrayEquals("插入排序和快速排序结果应一致", arr3, arr4);
        assertArrayEquals("快速排序和归并排序结果应一致", arr4, arr5);
        
        System.out.println("✓ 所有排序算法一致性测试通过");
    }
    
    /**
     * 性能对比测试
     */
    @Test
    public void testPerformanceComparison() {
        System.out.println("\n性能对比测试 (1000个随机数)...");
        
        int size = 1000;
        int[] original = new int[size];
        for (int i = 0; i < size; i++) {
            original[i] = (int) (Math.random() * 10000);
        }
        
        // 冒泡排序
        int[] arr1 = Arrays.copyOf(original, original.length);
        long start = System.nanoTime();
        SortProgram.bubbleSort(arr1);
        long end = System.nanoTime();
        System.out.printf("冒泡排序耗时: %.2f ms\n", (end - start) / 1_000_000.0);
        
        // 选择排序
        int[] arr2 = Arrays.copyOf(original, original.length);
        start = System.nanoTime();
        SortProgram.selectionSort(arr2);
        end = System.nanoTime();
        System.out.printf("选择排序耗时: %.2f ms\n", (end - start) / 1_000_000.0);
        
        // 插入排序
        int[] arr3 = Arrays.copyOf(original, original.length);
        start = System.nanoTime();
        SortProgram.insertionSort(arr3);
        end = System.nanoTime();
        System.out.printf("插入排序耗时: %.2f ms\n", (end - start) / 1_000_000.0);
        
        // 快速排序
        int[] arr4 = Arrays.copyOf(original, original.length);
        start = System.nanoTime();
        SortProgram.quickSort(arr4);
        end = System.nanoTime();
        System.out.printf("快速排序耗时: %.2f ms\n", (end - start) / 1_000_000.0);
        
        // 归并排序
        int[] arr5 = Arrays.copyOf(original, original.length);
        start = System.nanoTime();
        SortProgram.mergeSort(arr5);
        end = System.nanoTime();
        System.out.printf("归并排序耗时: %.2f ms\n", (end - start) / 1_000_000.0);
        
        assertTrue("所有算法应产生升序结果", 
            isAscending(arr1) && isAscending(arr2) && isAscending(arr3) 
            && isAscending(arr4) && isAscending(arr5));
    }
}
