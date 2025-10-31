#!/bin/bash
# 300以内奇数求和验证程序（Bash版本）

echo "=================================================="
echo "300以内奇数求和验证程序（Bash版本）"
echo "=================================================="
echo ""

# 计算奇数求和
sum=0
count=0
first_odd=0
last_odd=0

for ((i=1; i<=300; i++)); do
    if [ $((i % 2)) -ne 0 ]; then
        sum=$((sum + i))
        count=$((count + 1))
        if [ $count -eq 1 ]; then
            first_odd=$i
        fi
        last_odd=$i
    fi
done

echo "【计算信息】"
echo "计算范围: 1 ~ 300"
echo "奇数个数: $count"
echo "首项: $first_odd"
echo "末项: $last_odd"
echo ""
echo "【计算结果】"
echo "奇数求和结果: $sum"
echo ""

echo "=================================================="
echo "【验证过程】"
echo "=================================================="
echo ""

# 验证1: 数学公式法 (n²)
formula_result=$((count * count))
echo "验证方式1 - 数学公式法:"
echo "  公式: n² (n为奇数个数)"
echo "  计算: $count × $count = $formula_result"
if [ $formula_result -eq $sum ]; then
    echo "  结果: 通过 ✓"
else
    echo "  结果: 失败 ✗"
fi
echo ""

# 验证2: 反向累加法
reverse_sum=0
for ((i=300; i>=1; i--)); do
    if [ $((i % 2)) -ne 0 ]; then
        reverse_sum=$((reverse_sum + i))
    fi
done
echo "验证方式2 - 反向累加法:"
echo "  反向遍历求和（从300到1）"
echo "  反向遍历求和: $reverse_sum"
if [ $reverse_sum -eq $sum ]; then
    echo "  结果: 通过 ✓"
else
    echo "  结果: 失败 ✗"
fi
echo ""

# 验证3: 等差数列公式
arithmetic_result=$(((first_odd + last_odd) * count / 2))
echo "验证方式3 - 等差数列公式:"
echo "  公式: (首项 + 末项) × 项数 ÷ 2"
echo "  计算: ($first_odd + $last_odd) × $count ÷ 2 = $arithmetic_result"
if [ $arithmetic_result -eq $sum ]; then
    echo "  结果: 通过 ✓"
else
    echo "  结果: 失败 ✗"
fi
echo ""

echo "=================================================="
echo "【验证结论】"
if [ $formula_result -eq $sum ] && [ $reverse_sum -eq $sum ] && [ $arithmetic_result -eq $sum ]; then
    echo "所有验证通过，计算结果正确！"
    echo "300以内奇数求和 = $sum"
else
    echo "验证未通过，请检查计算逻辑"
fi
echo "=================================================="
