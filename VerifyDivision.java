/**
 * 验证100除以2等于50的正确性
 */
public class VerifyDivision {
    
    /**
     * 基本验证方法
     */
    public static boolean verifyDivision() {
        int dividend = 100;      // 被除数
        int divisor = 2;         // 除数
        int expectedResult = 50; // 期望结果
        
        // 执行除法运算
        int actualResult = dividend / divisor;
        
        // 输出验证信息
        System.out.println("==================================================");
        System.out.println("数学运算验证程序");
        System.out.println("==================================================");
        System.out.println("被除数: " + dividend);
        System.out.println("除数: " + divisor);
        System.out.println("实际结果: " + actualResult);
        System.out.println("期望结果: " + expectedResult);
        System.out.println("--------------------------------------------------");
        
        // 判断是否相等
        if (actualResult == expectedResult) {
            System.out.println("✓ 验证通过: 100 ÷ 2 = 50 是正确的！");
            return true;
        } else {
            System.out.println("✗ 验证失败: 100 ÷ 2 = " + actualResult + "，不等于 " + expectedResult);
            return false;
        }
    }
    
    /**
     * 详细验证方法，包含多种验证方式
     */
    public static void verifyDivisionWithDetails() {
        System.out.println("\n==================================================");
        System.out.println("详细验证");
        System.out.println("==================================================");
        
        // 方法1: 整数除法
        int result1 = 100 / 2;
        System.out.println("方法1 - 整数除法: 100 / 2 = " + result1);
        
        // 方法2: 浮点数除法
        double result2 = 100.0 / 2.0;
        System.out.println("方法2 - 浮点除法: 100.0 / 2.0 = " + result2);
        
        // 方法3: 使用乘法验证（反向验证）
        int reverseCheck = 50 * 2;
        System.out.println("方法3 - 反向验证: 50 × 2 = " + reverseCheck);
        System.out.println("         反向验证" + (reverseCheck == 100 ? "成功" : "失败") + ": 50 × 2 = 100");
        
        // 方法4: 使用循环减法验证
        int temp = 100;
        int count = 0;
        while (temp >= 2) {
            temp -= 2;
            count++;
        }
        System.out.println("方法4 - 循环减法: 100连续减" + count + "次2，剩余 = " + temp);
        System.out.println("         验证结果: 100可以被2整除" + count + "次");
        
        // 方法5: 使用取模运算验证整除性
        int remainder = 100 % 2;
        System.out.println("方法5 - 取模运算: 100 % 2 = " + remainder);
        System.out.println("         " + (remainder == 0 ? "100可以被2整除" : "100不能被2整除"));
        
        System.out.println("==================================================");
    }
    
    /**
     * 主方法
     */
    public static void main(String[] args) {
        // 执行基本验证
        boolean isCorrect = verifyDivision();
        
        // 执行详细验证
        verifyDivisionWithDetails();
        
        // 输出最终结论
        System.out.println("\n==================================================");
        System.out.println("最终结论");
        System.out.println("==================================================");
        if (isCorrect) {
            System.out.println("✓ 100除以2等于50是完全正确的！");
        } else {
            System.out.println("✗ 验证未通过");
        }
        System.out.println("==================================================");
    }
}
