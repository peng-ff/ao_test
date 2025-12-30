#!/bin/bash

# 公交车售票系统启动脚本

echo "=========================================="
echo "正在启动公交车售票系统..."
echo "=========================================="

# 检查Java环境
if ! command -v java &> /dev/null; then
    echo "错误: 未找到Java环境,请先安装JDK 11或更高版本"
    exit 1
fi

# 检查MySQL
if ! command -v mysql &> /dev/null; then
    echo "警告: 未找到MySQL,请确保MySQL已安装并正在运行"
fi

# 检查Redis
if ! command -v redis-cli &> /dev/null; then
    echo "警告: 未找到Redis,请确保Redis已安装并正在运行"
fi

# 构建项目
echo "正在构建项目..."
mvn clean package -DskipTests

if [ $? -ne 0 ]; then
    echo "错误: 项目构建失败"
    exit 1
fi

# 启动应用
echo "正在启动应用..."
java -jar target/bus-ticketing-system-1.0.0.jar

echo "=========================================="
echo "应用已启动"
echo "API地址: http://localhost:8088/api"
echo "=========================================="
