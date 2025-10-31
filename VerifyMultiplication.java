/**
 * 验证1000乘以0的结果
 * 
 * 该程序通过多种方式验证数学运算：1000 × 0 = 0
 */
public class VerifyMultiplication {
    
    /**
     * 执行乘法运算
     * @param a 第一个操作数
     * @param b 第二个操作数
     * @return 乘法结果
     */
    public static int multiply(int a, int b) {
        return a * b;
    }
    
    /**
     * 验证乘法结果是否正确
     * @param a 第一个操作数
     * @param b 第二个操作数
     * @param expected 期望的结果
     * @return 验证是否通过
     */
    public static boolean verifyMultiplication(int a, int b, int expected) {
        int result = multiply(a, b);
        return result == expected;
    }
    
    /**
     * 使用循环累加方式验证乘法
     * @param a 被乘数
     * @param b 乘数
     * @return 累加结果
     */
    public static int multiplyByLoop(int a, int b) {
        if (b == 0) {
            return 0;
        }
        int result = 0;
        int absB = Math.abs(b);
        for (int i = 0; i < absB; i++) {
            result += a;
        }
        return b > 0 ? result : -result;
    }
    
    /**
     * 打印验证结果
     */
    public static void printVerificationResult(String method, int a, int b, int result, int expected) {
        boolean isCorrect = (result == expected);
        System.out.println("==========================================");
        System.out.println("验证方法: " + method);
        System.out.println("计算式: " + a + " × " + b + " = " + result);
        System.out.println("期望值: " + expected);
        System.out.println("验证结果: " + (isCorrect ? "✓ 通过" : "✗ 失败"));
        System.out.println("==========================================\n");
    }
    
    public static void main(String[] args) {
        // 定义测试数据
        int number1 = 1000;
        int number2 = 0;
        int expectedResult = 0;
        
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║      1000 × 0 结果验证程序              ║");
        System.out.println("╚════════════════════════════════════════════╝\n");
        
        // 方法1: 直接使用乘法运算符
        int result1 = multiply(number1, number2);
        printVerificationResult("直接乘法运算", number1, number2, result1, expectedResult);
        
        // 方法2: 使用循环累加方式验证
        int result2 = multiplyByLoop(number1, number2);
        printVerificationResult("循环累加验证", number1, number2, result2, expectedResult);
        
        // 方法3: 使用数学定律验证（任何数乘以0等于0）
        int result3 = (number2 == 0) ? 0 : number1 * number2;
        printVerificationResult("数学定律验证", number1, number2, result3, expectedResult);
        
        // 方法4: 使用Math类的方法验证
        long result4 = Math.multiplyExact(number1, number2);
        printVerificationResult("Math.multiplyExact方法", number1, number2, (int)result4, expectedResult);
        
        // 综合验证结果
        boolean allTestsPassed = (result1 == expectedResult) && 
                                 (result2 == expectedResult) && 
                                 (result3 == expectedResult) && 
                                 (result4 == expectedResult);
        
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║          最终验证结果                    ║");
        System.out.println("╠════════════════════════════════════════════╣");
        System.out.println("║  所有测试: " + (allTestsPassed ? "全部通过 ✓" : "存在失败 ✗") + "            ║");
        System.out.println("║  结论: 1000 × 0 = 0 (正确)            ║");
        System.out.println("╚════════════════════════════════════════════╝\n");
        
        // 退出状态码
        System.exit(allTestsPassed ? 0 : 1);
    }
}
