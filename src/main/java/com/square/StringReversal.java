package com.square;

import java.util.Scanner;

/**
 * 字符反转程序
 * 提供字符串反转功能，支持多种字符类型
 * 
 * @author Square
 * @version 1.0
 */
public class StringReversal {
    
    /**
     * 字符串反转方法
     * 使用StringBuilder的reverse方法实现高效反转
     * 
     * @param input 待反转的字符串
     * @return 反转后的字符串
     * @throws IllegalArgumentException 如果输入为null
     */
    public static String reverseString(String input) {
        if (input == null) {
            throw new IllegalArgumentException("输入字符串不能为null");
        }
        
        if (input.isEmpty()) {
            return input;
        }
        
        return new StringBuilder(input).reverse().toString();
    }
    
    /**
     * 主程序入口
     * 提供用户交互界面
     * 
     * @param args 命令行参数（未使用）
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== 字符反转程序 ===");
        
        while (true) {
            System.out.println("请输入要反转的字符串：");
            String input = scanner.nextLine();
            
            // 检查输入是否为空
            if (input.isEmpty()) {
                System.out.println("输入不能为空，请重新输入！");
                System.out.println();
                continue;
            }
            
            // 执行反转
            String reversed = reverseString(input);
            
            // 显示结果
            System.out.println();
            System.out.println("原始字符串：" + input);
            System.out.println("反转后字符串：" + reversed);
            
            break;
        }
        
        scanner.close();
    }
}
