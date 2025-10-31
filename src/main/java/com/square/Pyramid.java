package com.square;

/**
 * 三棱锥类
 * 用于计算三棱锥的体积
 */
public class Pyramid {
    // 底面积
    private double baseArea;
    // 高度
    private double height;

    /**
     * 构造函数
     * @param baseArea 底面积
     * @param height 高度
     * @throws IllegalArgumentException 如果底面积或高度小于等于0
     */
    public Pyramid(double baseArea, double height) {
        if (baseArea <= 0) {
            throw new IllegalArgumentException("底面积必须大于0");
        }
        if (height <= 0) {
            throw new IllegalArgumentException("高度必须大于0");
        }
        this.baseArea = baseArea;
        this.height = height;
    }

    /**
     * 获取底面积
     * @return 底面积
     */
    public double getBaseArea() {
        return baseArea;
    }

    /**
     * 设置底面积
     * @param baseArea 底面积
     * @throws IllegalArgumentException 如果底面积小于等于0
     */
    public void setBaseArea(double baseArea) {
        if (baseArea <= 0) {
            throw new IllegalArgumentException("底面积必须大于0");
        }
        this.baseArea = baseArea;
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
     * @throws IllegalArgumentException 如果高度小于等于0
     */
    public void setHeight(double height) {
        if (height <= 0) {
            throw new IllegalArgumentException("高度必须大于0");
        }
        this.height = height;
    }

    /**
     * 计算三棱锥的体积
     * 公式: V = (1/3) × S × h
     * 其中 S 为底面积，h 为高度
     * @return 三棱锥的体积
     */
    public double calculateVolume() {
        return (1.0 / 3.0) * baseArea * height;
    }

    /**
     * 返回三棱锥的字符串表示
     * @return 格式化的字符串，包含底面积、高度和体积信息
     */
    @Override
    public String toString() {
        return String.format("三棱锥[底面积=%.2f, 高度=%.2f, 体积=%.2f]", 
                           baseArea, height, calculateVolume());
    }
}
