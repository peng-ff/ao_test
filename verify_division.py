#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
验证100除以2等于50的正确性
"""

def verify_division():
    """
    验证100除以2是否等于50
    """
    dividend = 100  # 被除数
    divisor = 2     # 除数
    expected_result = 50  # 期望结果
    
    # 执行除法运算
    actual_result = dividend / divisor
    
    # 验证结果
    print("=" * 50)
    print("数学运算验证程序")
    print("=" * 50)
    print(f"被除数: {dividend}")
    print(f"除数: {divisor}")
    print(f"实际结果: {actual_result}")
    print(f"期望结果: {expected_result}")
    print("-" * 50)
    
    # 判断是否相等
    if actual_result == expected_result:
        print("✓ 验证通过: 100 ÷ 2 = 50 是正确的！")
        return True
    else:
        print(f"✗ 验证失败: 100 ÷ 2 = {actual_result}，不等于 {expected_result}")
        return False


def verify_division_with_details():
    """
    详细验证，包含多种验证方法
    """
    print("\n" + "=" * 50)
    print("详细验证")
    print("=" * 50)
    
    # 方法1: 直接计算
    result1 = 100 / 2
    print(f"方法1 - 直接除法: 100 / 2 = {result1}")
    
    # 方法2: 整数除法
    result2 = 100 // 2
    print(f"方法2 - 整数除法: 100 // 2 = {result2}")
    
    # 方法3: 使用乘法验证（反向验证）
    reverse_check = 50 * 2
    print(f"方法3 - 反向验证: 50 × 2 = {reverse_check}")
    print(f"         反向验证{'成功' if reverse_check == 100 else '失败'}: 50 × 2 = 100")
    
    # 方法4: 使用减法验证
    subtraction_check = 100 - 2 - 2 - 2 - 2 - 2 - 2 - 2 - 2 - 2 - 2 - \
                       2 - 2 - 2 - 2 - 2 - 2 - 2 - 2 - 2 - 2 - \
                       2 - 2 - 2 - 2 - 2 - 2 - 2 - 2 - 2 - 2 - \
                       2 - 2 - 2 - 2 - 2 - 2 - 2 - 2 - 2 - 2 - \
                       2 - 2 - 2 - 2 - 2 - 2 - 2 - 2 - 2 - 2
    print(f"方法4 - 连续减法: 100连续减50次2 = {subtraction_check}")
    
    print("=" * 50)


if __name__ == "__main__":
    # 执行基本验证
    is_correct = verify_division()
    
    # 执行详细验证
    verify_division_with_details()
    
    # 输出最终结论
    print("\n" + "=" * 50)
    print("最终结论")
    print("=" * 50)
    if is_correct:
        print("✓ 100除以2等于50是完全正确的！")
    else:
        print("✗ 验证未通过")
    print("=" * 50)
