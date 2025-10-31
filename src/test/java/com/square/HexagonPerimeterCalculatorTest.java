package com.square;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * HexagonPerimeterCalculator 单元测试类
 * 
 * 测试覆盖范围:
 * 1. 正常功能测试:正整数边长、小数边长
 * 2. 边界测试:极小正数、极大数值
 * 3. 异常测试:零值、负数、NaN、无穷大
 * 
 * @author Square
 * @version 1.0.0
 */
@DisplayName("正六边形周长计算器测试")
class HexagonPerimeterCalculatorTest {
    
    private final HexagonPerimeterCalculator calculator = new HexagonPerimeterCalculator();
    
    // ========== 正常功能测试 ==========
    
    @Test
    @DisplayName("测试正整数边长计算")
    void testCalculatePerimeter_WithPositiveInteger() {
        double sideLength = 5.0;
        double expected = 30.0;
        double actual = calculator.calculatePerimeter(sideLength);
        assertEquals(expected, actual, 0.0001, "边长为5.0时,周长应为30.0");
    }
    
    @Test
    @DisplayName("测试小数边长计算")
    void testCalculatePerimeter_WithDecimal() {
        double sideLength = 10.5;
        double expected = 63.0;
        double actual = calculator.calculatePerimeter(sideLength);
        assertEquals(expected, actual, 0.0001, "边长为10.5时,周长应为63.0");
    }
    
    @Test
    @DisplayName("测试边长为1的情况")
    void testCalculatePerimeter_WithOne() {
        double sideLength = 1.0;
        double expected = 6.0;
        double actual = calculator.calculatePerimeter(sideLength);
        assertEquals(expected, actual, 0.0001, "边长为1.0时,周长应为6.0");
    }
    
    // ========== 边界测试 ==========
    
    @Test
    @DisplayName("测试极小正数边长")
    void testCalculatePerimeter_WithVerySmallPositive() {
        double sideLength = 0.0001;
        double expected = 0.0006;
        double actual = calculator.calculatePerimeter(sideLength);
        assertEquals(expected, actual, 0.000001, "极小正数边长应能正常计算");
    }
    
    @Test
    @DisplayName("测试较大数值边长")
    void testCalculatePerimeter_WithLargeNumber() {
        double sideLength = 1000000.0;
        double expected = 6000000.0;
        double actual = calculator.calculatePerimeter(sideLength);
        assertEquals(expected, actual, 0.0001, "大数值边长应能正常计算");
    }
    
    @Test
    @DisplayName("测试Double.MAX_VALUE不会溢出")
    void testCalculatePerimeter_WithMaxValue() {
        double sideLength = Double.MAX_VALUE / 10;
        double actual = calculator.calculatePerimeter(sideLength);
        assertFalse(Double.isInfinite(actual), "大数值计算结果不应溢出为无穷大");
    }
    
    // ========== 异常测试 ==========
    
    @Test
    @DisplayName("测试零值输入抛出异常")
    void testCalculatePerimeter_WithZero_ThrowsException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> calculator.calculatePerimeter(0.0),
            "边长为0应抛出IllegalArgumentException"
        );
        assertEquals("边长必须大于0", exception.getMessage());
    }
    
    @Test
    @DisplayName("测试负数输入抛出异常")
    void testCalculatePerimeter_WithNegative_ThrowsException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> calculator.calculatePerimeter(-3.0),
            "负数边长应抛出IllegalArgumentException"
        );
        assertEquals("边长必须大于0", exception.getMessage());
    }
    
    @Test
    @DisplayName("测试NaN输入抛出异常")
    void testCalculatePerimeter_WithNaN_ThrowsException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> calculator.calculatePerimeter(Double.NaN),
            "NaN边长应抛出IllegalArgumentException"
        );
        assertEquals("边长必须是有效数字", exception.getMessage());
    }
    
    @Test
    @DisplayName("测试正无穷大输入抛出异常")
    void testCalculatePerimeter_WithPositiveInfinity_ThrowsException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> calculator.calculatePerimeter(Double.POSITIVE_INFINITY),
            "正无穷大边长应抛出IllegalArgumentException"
        );
        assertEquals("边长必须是有效数字", exception.getMessage());
    }
    
    @Test
    @DisplayName("测试负无穷大输入抛出异常")
    void testCalculatePerimeter_WithNegativeInfinity_ThrowsException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> calculator.calculatePerimeter(Double.NEGATIVE_INFINITY),
            "负无穷大边长应抛出IllegalArgumentException"
        );
        assertEquals("边长必须是有效数字", exception.getMessage());
    }
    
    @Test
    @DisplayName("测试接近零的负数抛出异常")
    void testCalculatePerimeter_WithNegativeCloseToZero_ThrowsException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> calculator.calculatePerimeter(-0.0001),
            "接近零的负数应抛出IllegalArgumentException"
        );
        assertEquals("边长必须大于0", exception.getMessage());
    }
}
