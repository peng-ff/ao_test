#!/bin/bash
# 编译并运行测试

echo "=== 编译测试程序 ==="

# 创建输出目录
mkdir -p build/classes
mkdir -p build/test-classes

# 下载JUnit 4.13.2 (如果不存在)
JUNIT_JAR="lib/junit-4.13.2.jar"
HAMCREST_JAR="lib/hamcrest-core-1.3.jar"

mkdir -p lib

if [ ! -f "$JUNIT_JAR" ]; then
    echo "下载 JUnit..."
    wget -q -O "$JUNIT_JAR" https://repo1.maven.org/maven2/junit/junit/4.13.2/junit-4.13.2.jar
fi

if [ ! -f "$HAMCREST_JAR" ]; then
    echo "下载 Hamcrest..."
    wget -q -O "$HAMCREST_JAR" https://repo1.maven.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar
fi

# 编译主程序
echo "编译主程序..."
javac -d build/classes src/main/java/com/square/SortProgram.java

# 编译测试
echo "编译测试..."
javac -cp "$JUNIT_JAR:build/classes" -d build/test-classes src/test/java/com/square/SortProgramTest.java

if [ $? -eq 0 ]; then
    echo "✓ 编译成功!"
    echo ""
    echo "=== 运行测试 ==="
    java -cp "$JUNIT_JAR:$HAMCREST_JAR:build/classes:build/test-classes" org.junit.runner.JUnitCore com.square.SortProgramTest
else
    echo "✗ 编译失败!"
    exit 1
fi
