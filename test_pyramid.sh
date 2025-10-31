#!/bin/bash
# 三棱锥体积计算程序 - 自动化测试脚本

echo "=========================================="
echo "  三棱锥体积计算程序 - 自动化测试"
echo "=========================================="

# 创建目标目录
mkdir -p target/classes

# 编译源代码
echo "正在编译源代码..."
javac -d target/classes -encoding UTF-8 \
    src/main/java/com/square/Pyramid.java \
    src/main/java/com/square/PyramidVolumeCalculator.java

if [ $? -ne 0 ]; then
    echo "✗ 编译失败！"
    exit 1
fi

echo "✓ 编译成功！"
echo ""

# 测试1: 正常输入 - 底面积=12.0, 高度=5.0
echo "=========================================="
echo "测试1: 正常输入 (底面积=12.0, 高度=5.0)"
echo "=========================================="
echo -e "12.0\n5.0\nn" | java -cp target/classes com.square.PyramidVolumeCalculator
echo ""

# 测试2: 正常输入 - 底面积=20.0, 高度=8.0
echo "=========================================="
echo "测试2: 正常输入 (底面积=20.0, 高度=8.0)"
echo "=========================================="
echo -e "20.0\n8.0\nn" | java -cp target/classes com.square.PyramidVolumeCalculator
echo ""

# 测试3: 边界值测试 - 小数值
echo "=========================================="
echo "测试3: 边界值测试 (底面积=0.5, 高度=0.1)"
echo "=========================================="
echo -e "0.5\n0.1\nn" | java -cp target/classes com.square.PyramidVolumeCalculator
echo ""

# 测试4: 异常输入 - 负数底面积
echo "=========================================="
echo "测试4: 异常输入 (底面积=-5.0, 高度=10.0)"
echo "=========================================="
echo -e "-5.0\n10.0\nn" | java -cp target/classes com.square.PyramidVolumeCalculator
echo ""

# 测试5: 异常输入 - 零值
echo "=========================================="
echo "测试5: 异常输入 (底面积=0, 高度=5.0)"
echo "=========================================="
echo -e "0\n5.0\nn" | java -cp target/classes com.square.PyramidVolumeCalculator
echo ""

echo "=========================================="
echo "测试完成"
echo "=========================================="
