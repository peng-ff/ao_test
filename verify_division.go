package main

import (
	"fmt"
)

// verifyDivision 基本验证函数
func verifyDivision() bool {
	dividend := 100      // 被除数
	divisor := 2         // 除数
	expectedResult := 50 // 期望结果

	// 执行除法运算
	actualResult := dividend / divisor

	// 输出验证信息
	fmt.Println("==================================================")
	fmt.Println("数学运算验证程序")
	fmt.Println("==================================================")
	fmt.Printf("被除数: %d\n", dividend)
	fmt.Printf("除数: %d\n", divisor)
	fmt.Printf("实际结果: %d\n", actualResult)
	fmt.Printf("期望结果: %d\n", expectedResult)
	fmt.Println("--------------------------------------------------")

	// 判断是否相等
	if actualResult == expectedResult {
		fmt.Println("✓ 验证通过: 100 ÷ 2 = 50 是正确的！")
		return true
	} else {
		fmt.Printf("✗ 验证失败: 100 ÷ 2 = %d，不等于 %d\n", actualResult, expectedResult)
		return false
	}
}

// verifyDivisionWithDetails 详细验证函数
func verifyDivisionWithDetails() {
	fmt.Println("\n==================================================")
	fmt.Println("详细验证")
	fmt.Println("==================================================")

	// 方法1: 整数除法
	result1 := 100 / 2
	fmt.Printf("方法1 - 整数除法: 100 / 2 = %d\n", result1)

	// 方法2: 浮点数除法
	result2 := 100.0 / 2.0
	fmt.Printf("方法2 - 浮点除法: 100.0 / 2.0 = %.1f\n", result2)

	// 方法3: 使用乘法验证（反向验证）
	reverseCheck := 50 * 2
	fmt.Printf("方法3 - 反向验证: 50 × 2 = %d\n", reverseCheck)
	if reverseCheck == 100 {
		fmt.Println("         反向验证成功: 50 × 2 = 100")
	} else {
		fmt.Println("         反向验证失败")
	}

	// 方法4: 使用循环减法验证
	temp := 100
	count := 0
	for temp >= 2 {
		temp -= 2
		count++
	}
	fmt.Printf("方法4 - 循环减法: 100连续减%d次2，剩余 = %d\n", count, temp)
	fmt.Printf("         验证结果: 100可以被2整除%d次\n", count)

	// 方法5: 使用取模运算验证整除性
	remainder := 100 % 2
	fmt.Printf("方法5 - 取模运算: 100 %% 2 = %d\n", remainder)
	if remainder == 0 {
		fmt.Println("         100可以被2整除")
	} else {
		fmt.Println("         100不能被2整除")
	}

	fmt.Println("==================================================")
}

func main() {
	// 执行基本验证
	isCorrect := verifyDivision()

	// 执行详细验证
	verifyDivisionWithDetails()

	// 输出最终结论
	fmt.Println("\n==================================================")
	fmt.Println("最终结论")
	fmt.Println("==================================================")
	if isCorrect {
		fmt.Println("✓ 100除以2等于50是完全正确的！")
	} else {
		fmt.Println("✗ 验证未通过")
	}
	fmt.Println("==================================================")
}
