#!/bin/bash
# 二叉树算法程序 - 测试脚本

echo "=========================================="
echo "  二叉树算法程序 - 运行测试"
echo "=========================================="

# 创建目标目录
mkdir -p target/classes target/test-classes

# 编译源代码
echo "正在编译源代码..."
javac -d target/classes -encoding UTF-8 src/main/java/com/square/binarytree/*.java

if [ $? -ne 0 ]; then
    echo "✗ 源代码编译失败！"
    exit 1
fi

echo "✓ 源代码编译成功"

# 下载JUnit 5 JAR文件（如果不存在）
JUNIT_PLATFORM_VERSION=1.9.3
JUNIT_JUPITER_VERSION=5.9.3

LIB_DIR="lib"
mkdir -p $LIB_DIR

JUNIT_PLATFORM_CONSOLE="$LIB_DIR/junit-platform-console-standalone-$JUNIT_PLATFORM_VERSION.jar"

if [ ! -f "$JUNIT_PLATFORM_CONSOLE" ]; then
    echo "正在下载JUnit依赖..."
    wget -O "$JUNIT_PLATFORM_CONSOLE" \
        "https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/$JUNIT_PLATFORM_VERSION/junit-platform-console-standalone-$JUNIT_PLATFORM_VERSION.jar"
    
    if [ $? -ne 0 ]; then
        echo "✗ JUnit下载失败！请手动下载或使用Maven运行测试"
        echo "提示: 可以使用Maven命令: mvn test"
        exit 1
    fi
fi

# 编译测试代码
echo "正在编译测试代码..."
javac -cp "target/classes:$JUNIT_PLATFORM_CONSOLE" \
    -d target/test-classes -encoding UTF-8 \
    src/test/java/com/square/binarytree/*.java

if [ $? -ne 0 ]; then
    echo "✗ 测试代码编译失败！"
    exit 1
fi

echo "✓ 测试代码编译成功"

# 运行测试
echo ""
echo "正在运行测试..."
java -jar "$JUNIT_PLATFORM_CONSOLE" \
    --class-path "target/classes:target/test-classes" \
    --scan-class-path

echo ""
echo "=========================================="
echo "  测试完成"
echo "=========================================="
