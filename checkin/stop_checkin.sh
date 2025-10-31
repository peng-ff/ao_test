#!/bin/bash

# ========================================
# 用户签到系统停止脚本
# ========================================

APP_NAME="checkin-system"
PID_FILE="${APP_NAME}.pid"

# 检查 PID 文件是否存在
if [ ! -f "$PID_FILE" ]; then
    echo "应用未在运行（未找到 PID 文件）"
    exit 0
fi

# 读取 PID
PID=$(cat "$PID_FILE")

# 检查进程是否存在
if ! ps -p $PID > /dev/null 2>&1; then
    echo "应用未在运行（进程不存在）"
    rm -f "$PID_FILE"
    exit 0
fi

# 停止应用
echo "========================================"
echo "停止用户签到系统 (PID: $PID)..."
echo "========================================"

kill $PID

# 等待进程结束
TIMEOUT=30
COUNT=0
while ps -p $PID > /dev/null 2>&1; do
    sleep 1
    COUNT=$((COUNT + 1))
    if [ $COUNT -ge $TIMEOUT ]; then
        echo "警告: 优雅停止超时，强制终止进程..."
        kill -9 $PID
        break
    fi
done

# 删除 PID 文件
rm -f "$PID_FILE"

echo "✓ 应用已停止"
echo "========================================"
