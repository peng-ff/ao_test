package com.square;

/**
 * 440+1 计算程序
 * 执行 440 + 1 的加法计算并输出结果
 */
public class PlusOneCalculator {
    
    /**
     * 程序入口
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 初始化变量
        int num1 = 440;
        int num2 = 1;
        
        // 输出计算过程
        System.out.println("计算过程：");
        System.out.println("第一个数：" + num1);
        System.out.println("第二个数：" + num2);
        System.out.println("运算符：+");
        
        // 执行加法运算
        int result = num1 + num2;
        
        // 输出完整计算表达式和最终结果
        System.out.println("计算：" + num1 + " + " + num2 + " = " + result);
        System.out.println("最终结果：" + result);
    }
}
