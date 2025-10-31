package com.square;

/**
 * 圆锥体类
 * 用于计算圆锥体的体积
 */
public class Cone {
    // 底面半径
    private double radius;
    // 高度
    private double height;

    /**
     * 构造函数
     * @param radius 底面半径
     * @param height 高度
     */
    public Cone(double radius, double height) {
        if (radius <= 0 || height <= 0) {
            throw new IllegalArgumentException("半径和高度必须大于0");
        }
        this.radius = radius;
        this.height = height;
    }

    /**
     * 获取底面半径
     * @return 底面半径
     */
    public double getRadius() {
        return radius;
    }

    /**
     * 设置底面半径
     * @param radius 底面半径
     */
    public void setRadius(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("半径必须大于0");
        }
        this.radius = radius;
    }

    /**
     * 获取高度
     * @return 高度
     */
    public double getHeight() {
        return height;
    }

    /**
     * 设置高度
     * @param height 高度
     */
    public void setHeight(double height) {
        if (height <= 0) {
            throw new IllegalArgumentException("高度必须大于0");
        }
        this.height = height;
    }

    /**
     * 计算圆锥体的体积
     * 公式: V = (1/3) × π × r² × h
     * @return 圆锥体的体积
     */
    public double calculateVolume() {
        return (1.0 / 3.0) * Math.PI * radius * radius * height;
    }

    /**
     * 计算圆锥体的底面积
     * 公式: S = π × r²
     * @return 底面积
     */
    public double calculateBaseArea() {
        return Math.PI * radius * radius;
    }

    /**
     * 计算圆锥体的侧面积
     * 公式: S = π × r × l (l为母线长度)
     * @return 侧面积
     */
    public double calculateLateralArea() {
        // 母线长度 l = √(r² + h²)
        double slantHeight = Math.sqrt(radius * radius + height * height);
        return Math.PI * radius * slantHeight;
    }

    /**
     * 计算圆锥体的表面积
     * 公式: S = 底面积 + 侧面积
     * @return 表面积
     */
    public double calculateSurfaceArea() {
        return calculateBaseArea() + calculateLateralArea();
    }

    @Override
    public String toString() {
        return String.format("圆锥体[半径=%.2f, 高度=%.2f, 体积=%.2f]", 
                           radius, height, calculateVolume());
    }
}
