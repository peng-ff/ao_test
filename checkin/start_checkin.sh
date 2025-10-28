#!/bin/bash

# ========================================
# 用户签到系统启动脚本
# ========================================

APP_NAME="checkin-system"
APP_VERSION="1.0.0"
JAR_FILE="target/${APP_NAME}-${APP_VERSION}.jar"
LOG_FILE="logs/application.log"
PID_FILE="${APP_NAME}.pid"

# 检查 Java 环境
if ! command -v java &> /dev/null; then
    echo "错误: 未找到 Java 环境，请先安装 Java 11 或更高版本"
    exit 1
fi

# 检查 JAR 文件是否存在
if [ ! -f "$JAR_FILE" ]; then
    echo "错误: 未找到 JAR 文件: $JAR_FILE"
    echo "请先执行: mvn clean package"
    exit 1
fi

# 检查是否已经在运行
if [ -f "$PID_FILE" ]; then
    PID=$(cat "$PID_FILE")
    if ps -p $PID > /dev/null 2>&1; then
        echo "警告: 应用已在运行 (PID: $PID)"
        echo "如需重启，请先执行停止脚本: ./stop_checkin.sh"
        exit 1
    else
        rm -f "$PID_FILE"
    fi
fi

# 创建日志目录
mkdir -p logs

# 启动应用
echo "========================================"
echo "启动用户签到系统..."
echo "========================================"

nohup java -jar \
    -Xms256m \
    -Xmx512m \
    -Dspring.profiles.active=prod \
    "$JAR_FILE" > "$LOG_FILE" 2>&1 &

# 保存 PID
echo $! > "$PID_FILE"

echo "应用正在启动..."
sleep 3

# 检查启动状态
if ps -p $(cat "$PID_FILE") > /dev/null 2>&1; then
    echo "✓ 启动成功！"
    echo "========================================"
    echo "服务端口: 8081"
    echo "PID: $(cat $PID_FILE)"
    echo "日志文件: $LOG_FILE"
    echo "========================================"
    echo "API 端点:"
    echo "  - POST   http://localhost:8081/api/checkin"
    echo "  - GET    http://localhost:8081/api/checkin/today/{userId}"
    echo "  - GET    http://localhost:8081/api/checkin/stats/{userId}"
    echo "  - GET    http://localhost:8081/api/checkin/history/{userId}"
    echo "========================================"
else
    echo "✗ 启动失败，请查看日志: $LOG_FILE"
    rm -f "$PID_FILE"
    exit 1
fi
