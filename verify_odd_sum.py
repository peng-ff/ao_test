#!/usr/bin/env python3
"""
300以内奇数求和验证程序（Python版本）
用于验证Java程序的逻辑正确性
"""

def calculate_odd_sum():
    """计算1到300范围内所有奇数的求和"""
    total_sum = 0
    count = 0
    first_odd = 0
    last_odd = 0
    
    for i in range(1, 301):
        if i % 2 != 0:  # 奇数判断
            total_sum += i
            count += 1
            if count == 1:
                first_odd = i
            last_odd = i
    
    return total_sum, count, first_odd, last_odd

def verify_by_formula(count):
    """验证方法1: n²公式"""
    return count * count

def verify_by_reverse():
    """验证方法2: 反向累加"""
    reverse_sum = 0
    for i in range(300, 0, -1):
        if i % 2 != 0:
            reverse_sum += i
    return reverse_sum

def verify_by_arithmetic(first, last, count):
    """验证方法3: 等差数列公式"""
    return (first + last) * count // 2

def main():
    print("=" * 50)
    print("300以内奇数求和验证程序（Python版本）")
    print("=" * 50)
    print()
    
    # 计算奇数求和
    odd_sum, count, first_odd, last_odd = calculate_odd_sum()
    
    print("【计算信息】")
    print(f"计算范围: 1 ~ 300")
    print(f"奇数个数: {count}")
    print(f"首项: {first_odd}")
    print(f"末项: {last_odd}")
    print()
    print("【计算结果】")
    print(f"奇数求和结果: {odd_sum}")
    print()
    
    print("=" * 50)
    print("【验证过程】")
    print("=" * 50)
    print()
    
    # 验证1: 数学公式法
    formula_result = verify_by_formula(count)
    print("验证方式1 - 数学公式法:")
    print(f"  公式: n² (n为奇数个数)")
    print(f"  计算: {count} × {count} = {formula_result}")
    print(f"  结果: {'通过 ✓' if formula_result == odd_sum else '失败 ✗'}")
    print()
    
    # 验证2: 反向累加法
    reverse_result = verify_by_reverse()
    print("验证方式2 - 反向累加法:")
    print(f"  反向遍历求和（从300到1）")
    print(f"  反向遍历求和: {reverse_result}")
    print(f"  结果: {'通过 ✓' if reverse_result == odd_sum else '失败 ✗'}")
    print()
    
    # 验证3: 等差数列公式
    arithmetic_result = verify_by_arithmetic(first_odd, last_odd, count)
    print("验证方式3 - 等差数列公式:")
    print(f"  公式: (首项 + 末项) × 项数 ÷ 2")
    print(f"  计算: ({first_odd} + {last_odd}) × {count} ÷ 2 = {arithmetic_result}")
    print(f"  结果: {'通过 ✓' if arithmetic_result == odd_sum else '失败 ✗'}")
    print()
    
    # 验证结论
    all_passed = (formula_result == odd_sum and 
                  reverse_result == odd_sum and 
                  arithmetic_result == odd_sum)
    
    print("=" * 50)
    print("【验证结论】")
    if all_passed:
        print("所有验证通过，计算结果正确！")
        print(f"300以内奇数求和 = {odd_sum}")
    else:
        print("验证未通过，请检查计算逻辑")
    print("=" * 50)

if __name__ == "__main__":
    main()
