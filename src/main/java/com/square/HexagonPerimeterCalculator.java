package com.square;

/**
 * 正六边形周长计算器
 * 
 * 该类负责计算正六边形的周长。正六边形有6条相等的边,
 * 周长等于边长乘以6。
 * 
 * @author Square
 * @version 1.0.0
 */
public class HexagonPerimeterCalculator {
    
    /**
     * 计算正六边形的周长
     * 
     * @param sideLength 正六边形的边长,必须大于0
     * @return 正六边形的周长 (边长 × 6)
     * @throws IllegalArgumentException 如果边长不是有效的正数
     */
    public double calculatePerimeter(double sideLength) {
        // 验证边长是否为NaN或无穷大
        if (Double.isNaN(sideLength)) {
            throw new IllegalArgumentException("边长必须是有效数字");
        }
        
        if (Double.isInfinite(sideLength)) {
            throw new IllegalArgumentException("边长必须是有效数字");
        }
        
        // 验证边长必须大于0
        if (sideLength <= 0) {
            throw new IllegalArgumentException("边长必须大于0");
        }
        
        // 计算周长:边长 × 6
        return sideLength * 6;
    }
}
