package com.square;

/**
 * 正方形类
 * 负责存储边长并计算面积
 */
public class Square {
    // 正方形边长
    private double sideLength;

    /**
     * 构造函数
     * @param sideLength 正方形边长
     */
    public Square(double sideLength) {
        this.sideLength = sideLength;
    }

    /**
     * 计算正方形面积
     * @return 面积 = 边长 × 边长
     */
    public double calculateArea() {
        return sideLength * sideLength;
    }

    /**
     * 获取边长
     * @return 边长值
     */
    public double getSideLength() {
        return sideLength;
    }

    /**
     * 设置边长
     * @param sideLength 新的边长值
     */
    public void setSideLength(double sideLength) {
        this.sideLength = sideLength;
    }
}
