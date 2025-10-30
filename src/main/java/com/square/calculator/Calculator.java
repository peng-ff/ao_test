package com.square.calculator;

/**
 * Calculator - 计算器核心类
 * 实现基础四则运算功能
 * 
 * @author 系统生成
 * @version 1.0
 */
public class Calculator {
    
    /**
     * 加法运算
     * 
     * @param num1 第一个操作数
     * @param num2 第二个操作数
     * @return 两数之和
     */
    public double add(double num1, double num2) {
        return num1 + num2;
    }
    
    /**
     * 减法运算
     * 
     * @param num1 被减数
     * @param num2 减数
     * @return 两数之差
     */
    public double subtract(double num1, double num2) {
        return num1 - num2;
    }
    
    /**
     * 乘法运算
     * 
     * @param num1 第一个操作数
     * @param num2 第二个操作数
     * @return 两数之积
     */
    public double multiply(double num1, double num2) {
        return num1 * num2;
    }
    
    /**
     * 除法运算
     * 
     * @param num1 被除数
     * @param num2 除数
     * @return 两数之商
     * @throws ArithmeticException 当除数为零时抛出
     */
    public double divide(double num1, double num2) {
        if (num2 == 0) {
            throw new ArithmeticException("除数不能为零");
        }
        return num1 / num2;
    }
}
