#!/bin/bash
# 三棱锥体积计算程序 - 代码结构验证脚本（无需Java环境）

echo "=========================================="
echo "  三棱锥体积计算程序 - 代码验证"
echo "=========================================="
echo ""

# 检查文件是否存在的函数
check_file() {
    if [ -f "$1" ]; then
        echo "✓ $1"
        return 0
    else
        echo "✗ $1 (缺失)"
        return 1
    fi
}

# 检查项目结构
echo "【1. 检查核心文件】"
echo "--------------------------------------"
check_file "src/main/java/com/square/Pyramid.java"
check_file "src/main/java/com/square/PyramidVolumeCalculator.java"
check_file "run_pyramid.sh"
check_file "demo_pyramid.sh"

echo ""

# 统计代码行数
echo "【2. 代码统计】"
echo "--------------------------------------"

if [ -f "src/main/java/com/square/Pyramid.java" ]; then
    pyramid_lines=$(wc -l < "src/main/java/com/square/Pyramid.java")
    echo "Pyramid.java: $pyramid_lines 行"
fi

if [ -f "src/main/java/com/square/PyramidVolumeCalculator.java" ]; then
    calculator_lines=$(wc -l < "src/main/java/com/square/PyramidVolumeCalculator.java")
    echo "PyramidVolumeCalculator.java: $calculator_lines 行"
fi

total_lines=$((pyramid_lines + calculator_lines))
echo "--------------------------------------"
echo "总代码行数: $total_lines 行"

echo ""

# 检查 Pyramid 类的关键方法
echo "【3. 检查 Pyramid 类的关键功能】"
echo "--------------------------------------"

PYRAMID_FILE="src/main/java/com/square/Pyramid.java"

if [ -f "$PYRAMID_FILE" ]; then
    # 检查属性
    if grep -q "private double baseArea" "$PYRAMID_FILE"; then
        echo "✓ baseArea 属性"
    else
        echo "✗ baseArea 属性 (未找到)"
    fi
    
    if grep -q "private double height" "$PYRAMID_FILE"; then
        echo "✓ height 属性"
    else
        echo "✗ height 属性 (未找到)"
    fi
    
    # 检查构造方法
    if grep -q "public Pyramid" "$PYRAMID_FILE"; then
        echo "✓ 构造方法"
    else
        echo "✗ 构造方法 (未找到)"
    fi
    
    # 检查 getter/setter 方法
    if grep -q "public double getBaseArea" "$PYRAMID_FILE"; then
        echo "✓ getBaseArea() 方法"
    else
        echo "✗ getBaseArea() 方法 (未找到)"
    fi
    
    if grep -q "public void setBaseArea" "$PYRAMID_FILE"; then
        echo "✓ setBaseArea() 方法"
    else
        echo "✗ setBaseArea() 方法 (未找到)"
    fi
    
    if grep -q "public double getHeight" "$PYRAMID_FILE"; then
        echo "✓ getHeight() 方法"
    else
        echo "✗ getHeight() 方法 (未找到)"
    fi
    
    if grep -q "public void setHeight" "$PYRAMID_FILE"; then
        echo "✓ setHeight() 方法"
    else
        echo "✗ setHeight() 方法 (未找到)"
    fi
    
    # 检查核心计算方法
    if grep -q "public double calculateVolume" "$PYRAMID_FILE"; then
        echo "✓ calculateVolume() 方法"
    else
        echo "✗ calculateVolume() 方法 (未找到)"
    fi
    
    # 检查 toString 方法
    if grep -q "public String toString" "$PYRAMID_FILE"; then
        echo "✓ toString() 方法"
    else
        echo "✗ toString() 方法 (未找到)"
    fi
fi

echo ""

# 检查 PyramidVolumeCalculator 类的关键功能
echo "【4. 检查 PyramidVolumeCalculator 类的关键功能】"
echo "--------------------------------------"

CALCULATOR_FILE="src/main/java/com/square/PyramidVolumeCalculator.java"

if [ -f "$CALCULATOR_FILE" ]; then
    # 检查 main 方法
    if grep -q "public static void main" "$CALCULATOR_FILE"; then
        echo "✓ main() 方法"
    else
        echo "✗ main() 方法 (未找到)"
    fi
    
    # 检查静态计算方法
    if grep -q "public static double calculateVolume" "$CALCULATOR_FILE"; then
        echo "✓ calculateVolume() 静态方法"
    else
        echo "✗ calculateVolume() 静态方法 (未找到)"
    fi
    
    # 检查演示方法
    if grep -q "public static void demonstrateExamples" "$CALCULATOR_FILE"; then
        echo "✓ demonstrateExamples() 方法"
    else
        echo "✗ demonstrateExamples() 方法 (未找到)"
    fi
fi

echo ""

# 检查异常处理
echo "【5. 检查异常处理】"
echo "--------------------------------------"

if [ -f "$PYRAMID_FILE" ]; then
    if grep -q "IllegalArgumentException" "$PYRAMID_FILE"; then
        echo "✓ 参数验证异常处理"
    else
        echo "✗ 参数验证异常处理 (未找到)"
    fi
    
    # 统计异常检查次数
    exception_count=$(grep -c "throw new IllegalArgumentException" "$PYRAMID_FILE")
    echo "  - 异常抛出次数: $exception_count"
fi

if [ -f "$CALCULATOR_FILE" ]; then
    if grep -q "catch.*IllegalArgumentException" "$CALCULATOR_FILE"; then
        echo "✓ 主程序异常捕获"
    else
        echo "✗ 主程序异常捕获 (未找到)"
    fi
    
    if grep -q "catch.*Exception" "$CALCULATOR_FILE"; then
        echo "✓ 通用异常处理"
    else
        echo "✗ 通用异常处理 (未找到)"
    fi
fi

echo ""

# 检查关键算法
echo "【6. 检查体积计算公式】"
echo "--------------------------------------"

if [ -f "$PYRAMID_FILE" ]; then
    if grep -q "(1.0 / 3.0) \* baseArea \* height" "$PYRAMID_FILE"; then
        echo "✓ 体积计算公式正确: V = (1/3) × S × h"
    else
        echo "⚠ 请手动检查体积计算公式"
    fi
fi

echo ""

# 检查文档注释
echo "【7. 检查代码文档】"
echo "--------------------------------------"

if [ -f "$PYRAMID_FILE" ]; then
    javadoc_count=$(grep -c "/\*\*" "$PYRAMID_FILE")
    echo "Pyramid.java JavaDoc 注释数: $javadoc_count"
fi

if [ -f "$CALCULATOR_FILE" ]; then
    javadoc_count=$(grep -c "/\*\*" "$CALCULATOR_FILE")
    echo "PyramidVolumeCalculator.java JavaDoc 注释数: $javadoc_count"
fi

echo ""

# 检查用户交互
echo "【8. 检查用户交互功能】"
echo "--------------------------------------"

if [ -f "$CALCULATOR_FILE" ]; then
    if grep -q "Scanner" "$CALCULATOR_FILE"; then
        echo "✓ Scanner 输入处理"
    else
        echo "✗ Scanner 输入处理 (未找到)"
    fi
    
    if grep -q "printf\|println" "$CALCULATOR_FILE"; then
        echo "✓ 格式化输出"
    else
        echo "✗ 格式化输出 (未找到)"
    fi
    
    if grep -q "continueCalculation\|while.*continue" "$CALCULATOR_FILE"; then
        echo "✓ 循环计算支持"
    else
        echo "✗ 循环计算支持 (未找到)"
    fi
fi

echo ""
echo "=========================================="
echo "  验证完成"
echo "=========================================="
echo ""
echo "【使用说明】"
echo "1. 编译运行: ./run_pyramid.sh"
echo "2. 自动演示: ./demo_pyramid.sh"
echo "3. 需要 JDK 8 或更高版本"
echo ""
echo "【程序功能】"
echo "✓ 接收底面积和高度输入"
echo "✓ 计算三棱锥体积 (V = 1/3 × S × h)"
echo "✓ 参数有效性验证"
echo "✓ 异常处理"
echo "✓ 支持循环计算"
echo "✓ 格式化输出结果（保留两位小数）"
echo ""
