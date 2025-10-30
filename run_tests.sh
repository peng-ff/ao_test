#!/bin/bash

# 字符反转程序测试脚本

echo "=== 编译主程序 ==="
mkdir -p target/classes
mkdir -p target/test-classes

javac -encoding UTF-8 -d target/classes src/main/java/com/square/StringReversal.java

if [ $? -ne 0 ]; then
    echo "主程序编译失败！"
    exit 1
fi

echo "主程序编译成功！"
echo ""

echo "=== 编译测试程序 ==="
# 查找JUnit JAR文件
JUNIT_JAR=$(find ~/.m2/repository/org/junit/jupiter/junit-jupiter-api -name "*.jar" 2>/dev/null | head -1)

if [ -z "$JUNIT_JAR" ]; then
    echo "未找到JUnit库，请先运行 mvn test 下载依赖"
    exit 1
fi

javac -encoding UTF-8 -cp "target/classes:$JUNIT_JAR" -d target/test-classes src/test/java/com/square/StringReversalTest.java

if [ $? -eq 0 ]; then
    echo "测试程序编译成功！"
    echo ""
    echo "=== 运行单元测试 ==="
    mvn test -Dtest=StringReversalTest
else
    echo "测试程序编译失败！"
    exit 1
fi
