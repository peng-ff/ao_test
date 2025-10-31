package com.square;

/**
 * 300以内奇数求和验证程序
 * 实现奇数识别、累加计算、结果验证三大核心功能
 * 
 * @author 系统生成
 * @version 1.0
 */
public class OddNumberSumValidator {
    
    // 常量定义
    /** 计算范围的上限值 */
    private static final int UPPER_LIMIT = 300;
    
    /** 计算范围的下限值 */
    private static final int LOWER_LIMIT = 1;
    
    /**
     * 主程序入口
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 输出程序标题
        printHeader();
        
        // 执行奇数求和计算
        CalculationResult result = calculateOddNumberSum();
        
        // 输出计算信息
        printCalculationInfo(result);
        
        // 执行多重验证
        performVerifications(result);
    }
    
    /**
     * 计算结果内部类
     * 用于封装计算过程中的关键数据
     */
    static class CalculationResult {
        int sum;           // 奇数求和结果
        int count;         // 奇数个数
        int firstOdd;      // 首项
        int lastOdd;       // 末项
        
        CalculationResult(int sum, int count, int firstOdd, int lastOdd) {
            this.sum = sum;
            this.count = count;
            this.firstOdd = firstOdd;
            this.lastOdd = lastOdd;
        }
    }
    
    /**
     * 验证结果内部类
     */
    static class VerificationResult {
        String methodName;  // 验证方法名称
        String formula;     // 公式说明
        String calculation; // 计算过程
        int result;         // 验证结果
        boolean passed;     // 是否通过
        
        VerificationResult(String methodName, String formula, String calculation, int result, boolean passed) {
            this.methodName = methodName;
            this.formula = formula;
            this.calculation = calculation;
            this.result = result;
            this.passed = passed;
        }
    }
    
    /**
     * 输出程序标题
     */
    private static void printHeader() {
        System.out.println("==================================================");
        System.out.println("300以内奇数求和验证程序");
        System.out.println("==================================================");
        System.out.println();
    }
    
    /**
     * 计算1到300范围内所有奇数的求和
     * 采用正向遍历方式
     * 时间复杂度: O(n)
     * 空间复杂度: O(1)
     * 
     * @return 计算结果对象
     */
    private static CalculationResult calculateOddNumberSum() {
        int sum = 0;
        int count = 0;
        int firstOdd = 0;
        int lastOdd = 0;
        
        // 遍历从1到300的所有数字
        for (int i = LOWER_LIMIT; i <= UPPER_LIMIT; i++) {
            // 判断是否为奇数（不能被2整除）
            if (i % 2 != 0) {
                sum += i;
                count++;
                
                // 记录首项
                if (count == 1) {
                    firstOdd = i;
                }
                // 更新末项
                lastOdd = i;
            }
        }
        
        return new CalculationResult(sum, count, firstOdd, lastOdd);
    }
    
    /**
     * 输出计算信息
     * @param result 计算结果
     */
    private static void printCalculationInfo(CalculationResult result) {
        System.out.println("【计算信息】");
        System.out.println("计算范围: " + LOWER_LIMIT + " ~ " + UPPER_LIMIT);
        System.out.println("奇数个数: " + result.count);
        System.out.println("首项: " + result.firstOdd);
        System.out.println("末项: " + result.lastOdd);
        System.out.println();
        System.out.println("【计算结果】");
        System.out.println("奇数求和结果: " + result.sum);
        System.out.println();
    }
    
    /**
     * 执行多重验证
     * @param result 计算结果
     */
    private static void performVerifications(CalculationResult result) {
        System.out.println("==================================================");
        System.out.println("【验证过程】");
        System.out.println("==================================================");
        System.out.println();
        
        // 验证方式1: 数学公式法
        VerificationResult verify1 = verifyByMathFormula(result);
        printVerificationResult(verify1);
        
        // 验证方式2: 反向累加法
        VerificationResult verify2 = verifyByReverseSum(result);
        printVerificationResult(verify2);
        
        // 验证方式3: 等差数列公式
        VerificationResult verify3 = verifyByArithmeticSequence(result);
        printVerificationResult(verify3);
        
        // 输出验证结论
        printConclusion(verify1.passed && verify2.passed && verify3.passed, result.sum);
    }
    
    /**
     * 验证方法1: 数学公式验证（n²公式）
     * 原理: 奇数数列求和 = n²（n为奇数个数）
     * 
     * @param result 计算结果
     * @return 验证结果
     */
    private static VerificationResult verifyByMathFormula(CalculationResult result) {
        String methodName = "验证方式1 - 数学公式法";
        String formula = "公式: n² (n为奇数个数)";
        
        int n = result.count;
        int formulaResult = n * n;
        String calculation = "计算: " + n + " × " + n + " = " + formulaResult;
        
        boolean passed = (formulaResult == result.sum);
        
        return new VerificationResult(methodName, formula, calculation, formulaResult, passed);
    }
    
    /**
     * 验证方法2: 反向累加验证
     * 从300开始向1反向遍历，累加所有奇数
     * 
     * @param result 计算结果
     * @return 验证结果
     */
    private static VerificationResult verifyByReverseSum(CalculationResult result) {
        String methodName = "验证方式2 - 反向累加法";
        String formula = "反向遍历求和（从300到1）";
        
        int reverseSum = 0;
        // 从上限开始反向遍历
        for (int i = UPPER_LIMIT; i >= LOWER_LIMIT; i--) {
            if (i % 2 != 0) {
                reverseSum += i;
            }
        }
        
        String calculation = "反向遍历求和: " + reverseSum;
        boolean passed = (reverseSum == result.sum);
        
        return new VerificationResult(methodName, formula, calculation, reverseSum, passed);
    }
    
    /**
     * 验证方法3: 等差数列公式验证
     * 原理: 和 = (首项 + 末项) × 项数 ÷ 2
     * 
     * @param result 计算结果
     * @return 验证结果
     */
    private static VerificationResult verifyByArithmeticSequence(CalculationResult result) {
        String methodName = "验证方式3 - 等差数列公式";
        String formula = "公式: (首项 + 末项) × 项数 ÷ 2";
        
        int arithmeticSum = (result.firstOdd + result.lastOdd) * result.count / 2;
        String calculation = "计算: (" + result.firstOdd + " + " + result.lastOdd 
                           + ") × " + result.count + " ÷ 2 = " + arithmeticSum;
        
        boolean passed = (arithmeticSum == result.sum);
        
        return new VerificationResult(methodName, formula, calculation, arithmeticSum, passed);
    }
    
    /**
     * 输出单个验证结果
     * @param verification 验证结果对象
     */
    private static void printVerificationResult(VerificationResult verification) {
        System.out.println(verification.methodName + ":");
        System.out.println("  " + verification.formula);
        System.out.println("  " + verification.calculation);
        System.out.println("  结果: " + (verification.passed ? "通过 ✓" : "失败 ✗"));
        System.out.println();
    }
    
    /**
     * 输出最终验证结论
     * @param allPassed 所有验证是否都通过
     * @param finalResult 最终结果值
     */
    private static void printConclusion(boolean allPassed, int finalResult) {
        System.out.println("==================================================");
        System.out.println("【验证结论】");
        if (allPassed) {
            System.out.println("所有验证通过，计算结果正确！");
            System.out.println("300以内奇数求和 = " + finalResult);
        } else {
            System.out.println("验证未通过，请检查计算逻辑");
        }
        System.out.println("==================================================");
    }
}
