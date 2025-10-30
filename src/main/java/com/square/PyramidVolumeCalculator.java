package com.square;

import java.util.Scanner;

/**
 * 三棱锥体积计算程序的主类
 * 提供交互式界面用于计算三棱锥的体积
 */
public class PyramidVolumeCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueCalculation = true;

        System.out.println("=================================");
        System.out.println("   三棱锥体积计算程序");
        System.out.println("=================================");

        while (continueCalculation) {
            try {
                // 获取用户输入
                System.out.print("\n请输入底面积 (S): ");
                double baseArea = scanner.nextDouble();

                System.out.print("请输入高度 (h): ");
                double height = scanner.nextDouble();

                // 创建三棱锥对象并计算
                Pyramid pyramid = new Pyramid(baseArea, height);

                // 显示结果
                System.out.println("\n--- 计算结果 ---");
                System.out.printf("底面积: %.2f\n", pyramid.getBaseArea());
                System.out.printf("高度: %.2f\n", pyramid.getHeight());
                System.out.printf("体积: %.2f\n", pyramid.calculateVolume());
                System.out.println("----------------");

            } catch (IllegalArgumentException e) {
                System.out.println("错误: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("输入错误，请输入有效的数字!");
                scanner.nextLine(); // 清除输入缓冲区
            }

            // 询问是否继续
            System.out.print("\n是否继续计算? (y/n): ");
            scanner.nextLine(); // 消耗前面的换行符
            String answer = scanner.nextLine().trim().toLowerCase();
            continueCalculation = answer.equals("y") || answer.equals("yes");
        }

        System.out.println("\n感谢使用三棱锥体积计算程序!");
        scanner.close();
    }

    /**
     * 快速计算三棱锥体积的静态方法
     * @param baseArea 底面积
     * @param height 高度
     * @return 体积
     * @throws IllegalArgumentException 如果底面积或高度小于等于0
     */
    public static double calculateVolume(double baseArea, double height) {
        Pyramid pyramid = new Pyramid(baseArea, height);
        return pyramid.calculateVolume();
    }

    /**
     * 演示计算示例
     */
    public static void demonstrateExamples() {
        System.out.println("=== 三棱锥计算示例 ===\n");

        // 示例1
        Pyramid pyramid1 = new Pyramid(12.0, 5.0);
        System.out.println("示例1: " + pyramid1);
        System.out.printf("  体积: %.2f 立方单位\n\n", pyramid1.calculateVolume());

        // 示例2
        Pyramid pyramid2 = new Pyramid(20.0, 8.0);
        System.out.println("示例2: " + pyramid2);
        System.out.printf("  体积: %.2f 立方单位\n\n", pyramid2.calculateVolume());

        // 示例3
        Pyramid pyramid3 = new Pyramid(15.5, 10.3);
        System.out.println("示例3: " + pyramid3);
        System.out.printf("  体积: %.2f 立方单位\n", pyramid3.calculateVolume());
    }
}
