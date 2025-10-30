package com.square.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * CalculatorTest - 计算器核心类单元测试
 * 测试四则运算的正确性和异常处理
 * 
 * @author 系统生成
 * @version 1.0
 */
@DisplayName("计算器核心功能测试")
public class CalculatorTest {
    
    private Calculator calculator;
    
    @BeforeEach
    public void setUp() {
        calculator = new Calculator();
    }
    
    // ========== 加法测试 ==========
    
    @Test
    @DisplayName("加法: 正数相加")
    public void testAddPositiveNumbers() {
        assertEquals(5.0, calculator.add(2.0, 3.0), 0.0001);
        assertEquals(100.0, calculator.add(45.5, 54.5), 0.0001);
    }
    
    @Test
    @DisplayName("加法: 负数相加")
    public void testAddNegativeNumbers() {
        assertEquals(-5.0, calculator.add(-2.0, -3.0), 0.0001);
        assertEquals(-10.5, calculator.add(-5.5, -5.0), 0.0001);
    }
    
    @Test
    @DisplayName("加法: 正数和负数相加")
    public void testAddMixedNumbers() {
        assertEquals(1.0, calculator.add(3.0, -2.0), 0.0001);
        assertEquals(-1.0, calculator.add(-3.0, 2.0), 0.0001);
    }
    
    @Test
    @DisplayName("加法: 小数相加")
    public void testAddDecimalNumbers() {
        assertEquals(5.5, calculator.add(2.2, 3.3), 0.0001);
        assertEquals(10.123, calculator.add(5.123, 5.0), 0.0001);
    }
    
    // ========== 减法测试 ==========
    
    @Test
    @DisplayName("减法: 正数相减")
    public void testSubtractPositiveNumbers() {
        assertEquals(2.0, calculator.subtract(5.0, 3.0), 0.0001);
        assertEquals(-1.0, calculator.subtract(3.0, 4.0), 0.0001);
    }
    
    @Test
    @DisplayName("减法: 负数相减")
    public void testSubtractNegativeNumbers() {
        assertEquals(1.0, calculator.subtract(-2.0, -3.0), 0.0001);
        assertEquals(-3.0, calculator.subtract(-5.0, -2.0), 0.0001);
    }
    
    @Test
    @DisplayName("减法: 小数相减")
    public void testSubtractDecimalNumbers() {
        assertEquals(1.1, calculator.subtract(3.3, 2.2), 0.0001);
        assertEquals(0.5, calculator.subtract(5.5, 5.0), 0.0001);
    }
    
    // ========== 乘法测试 ==========
    
    @Test
    @DisplayName("乘法: 正数相乘")
    public void testMultiplyPositiveNumbers() {
        assertEquals(6.0, calculator.multiply(2.0, 3.0), 0.0001);
        assertEquals(12.5, calculator.multiply(2.5, 5.0), 0.0001);
    }
    
    @Test
    @DisplayName("乘法: 负数相乘")
    public void testMultiplyNegativeNumbers() {
        assertEquals(6.0, calculator.multiply(-2.0, -3.0), 0.0001);
        assertEquals(-10.0, calculator.multiply(-2.0, 5.0), 0.0001);
    }
    
    @Test
    @DisplayName("乘法: 乘以零")
    public void testMultiplyByZero() {
        assertEquals(0.0, calculator.multiply(5.0, 0.0), 0.0001);
        assertEquals(0.0, calculator.multiply(0.0, 5.0), 0.0001);
    }
    
    @Test
    @DisplayName("乘法: 小数相乘")
    public void testMultiplyDecimalNumbers() {
        assertEquals(7.26, calculator.multiply(2.2, 3.3), 0.0001);
        assertEquals(6.25, calculator.multiply(2.5, 2.5), 0.0001);
    }
    
    // ========== 除法测试 ==========
    
    @Test
    @DisplayName("除法: 正数相除")
    public void testDividePositiveNumbers() {
        assertEquals(2.0, calculator.divide(6.0, 3.0), 0.0001);
        assertEquals(2.5, calculator.divide(5.0, 2.0), 0.0001);
    }
    
    @Test
    @DisplayName("除法: 负数相除")
    public void testDivideNegativeNumbers() {
        assertEquals(2.0, calculator.divide(-6.0, -3.0), 0.0001);
        assertEquals(-2.5, calculator.divide(-5.0, 2.0), 0.0001);
    }
    
    @Test
    @DisplayName("除法: 小数相除")
    public void testDivideDecimalNumbers() {
        assertEquals(1.5, calculator.divide(4.5, 3.0), 0.0001);
        assertEquals(2.0, calculator.divide(5.5, 2.75), 0.0001);
    }
    
    @Test
    @DisplayName("除法: 除以零抛出异常")
    public void testDivideByZero() {
        ArithmeticException exception = assertThrows(
            ArithmeticException.class,
            () -> calculator.divide(5.0, 0.0)
        );
        assertEquals("除数不能为零", exception.getMessage());
    }
    
    @Test
    @DisplayName("除法: 零除以非零数")
    public void testZeroDividedByNumber() {
        assertEquals(0.0, calculator.divide(0.0, 5.0), 0.0001);
    }
    
    // ========== 边界测试 ==========
    
    @Test
    @DisplayName("边界测试: 极大值运算")
    public void testLargeNumbers() {
        double largeNum = 1000000.0;
        assertEquals(2000000.0, calculator.add(largeNum, largeNum), 0.0001);
        assertEquals(0.0, calculator.subtract(largeNum, largeNum), 0.0001);
    }
    
    @Test
    @DisplayName("边界测试: 极小值运算")
    public void testSmallNumbers() {
        double smallNum = 0.0001;
        assertEquals(0.0002, calculator.add(smallNum, smallNum), 0.00001);
        assertTrue(calculator.multiply(smallNum, smallNum) < 0.001);
    }
}
