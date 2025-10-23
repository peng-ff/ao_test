package com.square;

import java.util.Scanner;

/**
 * 正方形面积计算器主程序
 * 接收用户输入并计算正方形面积
 */
public class SquareCalculator {
    
    /**
     * 程序入口
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("==============================");
        System.out.println("   正方形面积计算程序");
        System.out.println("==============================");
        
        try {
            System.out.print("请输入正方形的边长: ");
            String input = scanner.nextLine();
            
            // 验证输入
            double sideLength = validateInput(input);
            
            // 创建正方形对象并计算面积
            Square square = new Square(sideLength);
            double area = square.calculateArea();
            
            // 显示结果
            displayResult(sideLength, area);
            
        } catch (IllegalArgumentException e) {
            System.out.println("错误: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
    
    /**
     * 验证用户输入
     * @param input 用户输入的字符串
     * @return 验证后的边长值
     * @throws IllegalArgumentException 如果输入无效
     */
    private static double validateInput(String input) throws IllegalArgumentException {
        double value;
        
        // 验证数据类型
        try {
            value = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("请输入有效数字");
        }
        
        // 验证数值范围
        if (value <= 0) {
            throw new IllegalArgumentException("边长必须是正数");
        }
        
        return value;
    }
    
    /**
     * 显示计算结果
     * @param sideLength 边长
     * @param area 面积
     */
    private static void displayResult(double sideLength, double area) {
        System.out.println("\n==============================");
        System.out.println("计算结果:");
        System.out.println("------------------------------");
        System.out.printf("边长: %.2f%n", sideLength);
        System.out.printf("面积: %.2f%n", area);
        System.out.println("==============================");
    }
}
