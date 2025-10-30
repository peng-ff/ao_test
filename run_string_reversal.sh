#!/bin/bash

# 字符反转程序运行脚本

echo "=== 编译字符反转程序 ==="
javac -encoding UTF-8 -d target/classes -cp target/classes src/main/java/com/square/StringReversal.java

if [ $? -eq 0 ]; then
    echo "编译成功！"
    echo ""
    echo "=== 运行字符反转程序 ==="
    java -cp target/classes com.square.StringReversal
else
    echo "编译失败！"
    exit 1
fi
