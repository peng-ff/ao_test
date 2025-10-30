package com.square.calculator;

import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * SimpleCalculator - 简单计算器主程序
 * 提供命令行交互界面,支持基础四则运算
 * 
 * @author 系统生成
 * @version 1.0
 */
public class SimpleCalculator {
    
    private static final Calculator calculator = new Calculator();
    private static final Scanner scanner = new Scanner(System.in);
    
    /**
     * 程序入口
     * 
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        displayWelcome();
        runCalculator();
        System.out.println("感谢使用简单计算器!再见!");
        scanner.close();
    }
    
    /**
     * 显示欢迎信息
     */
    private static void displayWelcome() {
        System.out.println("=================================");
        System.out.println("      欢迎使用简单计算器");
        System.out.println("=================================");
        System.out.println();
    }
    
    /**
     * 显示操作菜单
     */
    private static void displayMenu() {
        System.out.println("\n请选择运算类型:");
        System.out.println("1. 加法 (+)");
        System.out.println("2. 减法 (-)");
        System.out.println("3. 乘法 (×)");
        System.out.println("4. 除法 (÷)");
        System.out.println("0. 退出");
        System.out.print("请输入选项 (0-4): ");
    }
    
    /**
     * 运行计算器主循环
     */
    private static void runCalculator() {
        boolean running = true;
        
        while (running) {
            displayMenu();
            
            int operation = getOperationChoice();
            
            // 检查是否退出
            if (operation == 0) {
                running = false;
                continue;
            }
            
            // 无效的运算符
            if (operation < 0) {
                System.out.println("错误: 无效的运算类型,请重新选择");
                continue;
            }
            
            // 获取操作数
            double num1 = getNumber("请输入第一个数字: ");
            if (Double.isNaN(num1)) {
                continue; // 输入无效,重新开始
            }
            
            double num2 = getNumber("请输入第二个数字: ");
            if (Double.isNaN(num2)) {
                continue; // 输入无效,重新开始
            }
            
            // 执行计算
            performCalculation(operation, num1, num2);
            
            // 询问是否继续
            if (!askToContinue()) {
                running = false;
            }
        }
    }
    
    /**
     * 获取用户选择的运算类型
     * 
     * @return 运算类型代码,无效输入返回-1
     */
    private static int getOperationChoice() {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine(); // 清除缓冲区
            
            if (choice >= 0 && choice <= 4) {
                return choice;
            } else {
                return -1;
            }
        } catch (InputMismatchException e) {
            scanner.nextLine(); // 清除无效输入
            return -1;
        }
    }
    
    /**
     * 获取用户输入的数字
     * 
     * @param prompt 提示信息
     * @return 输入的数字,无效输入返回NaN
     */
    private static double getNumber(String prompt) {
        System.out.print(prompt);
        try {
            double number = scanner.nextDouble();
            scanner.nextLine(); // 清除缓冲区
            return number;
        } catch (InputMismatchException e) {
            scanner.nextLine(); // 清除无效输入
            System.out.println("错误: 输入格式不正确,请输入有效数字");
            return Double.NaN;
        }
    }
    
    /**
     * 执行计算并显示结果
     * 
     * @param operation 运算类型
     * @param num1 第一个操作数
     * @param num2 第二个操作数
     */
    private static void performCalculation(int operation, double num1, double num2) {
        try {
            double result;
            String operator;
            
            switch (operation) {
                case 1:
                    result = calculator.add(num1, num2);
                    operator = "+";
                    break;
                case 2:
                    result = calculator.subtract(num1, num2);
                    operator = "-";
                    break;
                case 3:
                    result = calculator.multiply(num1, num2);
                    operator = "×";
                    break;
                case 4:
                    result = calculator.divide(num1, num2);
                    operator = "÷";
                    break;
                default:
                    System.out.println("错误: 无效的运算类型");
                    return;
            }
            
            // 显示计算结果
            System.out.println("\n计算结果:");
            System.out.printf("%.2f %s %.2f = %.2f\n", num1, operator, num2, result);
            
        } catch (ArithmeticException e) {
            System.out.println("错误: " + e.getMessage());
        }
    }
    
    /**
     * 询问用户是否继续计算
     * 
     * @return true继续,false退出
     */
    private static boolean askToContinue() {
        System.out.print("\n是否继续计算? (y/n): ");
        String response = scanner.nextLine().trim().toLowerCase();
        return response.equals("y") || response.equals("yes");
    }
}
