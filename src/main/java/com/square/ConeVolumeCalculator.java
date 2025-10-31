package com.square;

import java.util.Scanner;

/**
 * 圆锥体体积计算程序的主类
 * 提供交互式界面用于计算圆锥体的体积
 */
public class ConeVolumeCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueCalculation = true;

        System.out.println("=================================");
        System.out.println("   圆锥体体积计算程序");
        System.out.println("=================================");

        while (continueCalculation) {
            try {
                // 获取用户输入
                System.out.print("\n请输入圆锥体底面半径 (r): ");
                double radius = scanner.nextDouble();

                System.out.print("请输入圆锥体高度 (h): ");
                double height = scanner.nextDouble();

                // 创建圆锥体对象并计算
                Cone cone = new Cone(radius, height);

                // 显示结果
                System.out.println("\n--- 计算结果 ---");
                System.out.printf("底面半径: %.2f\n", cone.getRadius());
                System.out.printf("高度: %.2f\n", cone.getHeight());
                System.out.printf("底面积: %.2f\n", cone.calculateBaseArea());
                System.out.printf("侧面积: %.2f\n", cone.calculateLateralArea());
                System.out.printf("表面积: %.2f\n", cone.calculateSurfaceArea());
                System.out.printf("体积: %.2f\n", cone.calculateVolume());
                System.out.println("----------------");

            } catch (IllegalArgumentException e) {
                System.out.println("错误: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("输入错误,请输入有效的数字!");
                scanner.nextLine(); // 清除输入缓冲区
            }

            // 询问是否继续
            System.out.print("\n是否继续计算? (y/n): ");
            scanner.nextLine(); // 消耗前面的换行符
            String answer = scanner.nextLine().trim().toLowerCase();
            continueCalculation = answer.equals("y") || answer.equals("yes");
        }

        System.out.println("\n感谢使用圆锥体体积计算程序!");
        scanner.close();
    }

    /**
     * 快速计算圆锥体体积的静态方法
     * @param radius 底面半径
     * @param height 高度
     * @return 体积
     */
    public static double calculateVolume(double radius, double height) {
        Cone cone = new Cone(radius, height);
        return cone.calculateVolume();
    }

    /**
     * 演示示例
     */
    public static void demonstrateExamples() {
        System.out.println("=== 圆锥体计算示例 ===\n");

        // 示例1
        Cone cone1 = new Cone(5.0, 10.0);
        System.out.println("示例1: " + cone1);
        System.out.printf("  体积: %.2f 立方单位\n\n", cone1.calculateVolume());

        // 示例2
        Cone cone2 = new Cone(3.0, 7.0);
        System.out.println("示例2: " + cone2);
        System.out.printf("  体积: %.2f 立方单位\n\n", cone2.calculateVolume());

        // 示例3
        Cone cone3 = new Cone(8.5, 12.3);
        System.out.println("示例3: " + cone3);
        System.out.printf("  体积: %.2f 立方单位\n", cone3.calculateVolume());
        System.out.printf("  表面积: %.2f 平方单位\n", cone3.calculateSurfaceArea());
    }
}
