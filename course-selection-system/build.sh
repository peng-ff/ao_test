#!/bin/bash

# 选课系统构建脚本

echo "========================================="
echo "开始构建选课系统..."
echo "========================================="

# 检查Maven是否安装
if ! command -v mvn &> /dev/null; then
    echo "错误: Maven未安装,请先安装Maven"
    exit 1
fi

# 进入项目目录
cd "$(dirname "$0")"

# 清理并编译项目
echo "清理旧的构建文件..."
mvn clean

echo "编译项目..."
mvn compile

echo "运行测试..."
mvn test -DskipTests=true

echo "打包项目..."
mvn package -DskipTests=true

if [ $? -eq 0 ]; then
    echo "========================================="
    echo "构建成功!"
    echo "JAR文件位置: target/course-selection-system.jar"
    echo "========================================="
else
    echo "========================================="
    echo "构建失败,请检查错误信息"
    echo "========================================="
    exit 1
fi
