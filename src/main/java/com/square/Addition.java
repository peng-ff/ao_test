package com.square;

/**
 * 加法计算器程序
 * 功能：计算两个整数的加法运算
 * 
 * @author 系统生成
 * @version 2.0
 * @since 2025-10-31
 * 
 * 这是一个edit_file工具调用示例
 * 演示如何使用edit_file工具修改Java源文件
 */
public class Addition {
    
    /**
     * 程序主入口
     * 执行44 + 55的加法运算并输出详细计算过程
     * 
     * @param args 命令行参数（未使用）
     */
    public static void main(String[] args) {
        // 定义操作数
        int num1 = 44;
        int num2 = 55;
        
        // 输出计算过程信息
        System.out.println("===========================================");
        System.out.println("        加法计算器 v2.0");
        System.out.println("===========================================");
        System.out.println("计算过程：");
        System.out.println("第一个数：" + num1);
        System.out.println("第二个数：" + num2);
        System.out.println("运算符：+");
        System.out.println("-------------------------------------------");
        
        // 执行加法运算
        int result = num1 + num2;
        
        // 输出计算结果
        System.out.println("计算：" + num1 + " + " + num2 + " = " + result);
        System.out.println("最终结果：" + result);
        System.out.println("===========================================");
        System.out.println("计算完成！感谢使用本程序。");
    }
}
