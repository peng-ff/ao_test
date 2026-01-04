#!/bin/bash

# 疯狂蟒蛇游戏停止脚本

echo "======================================"
echo "   停止疯狂蟒蛇游戏服务"
echo "======================================"

# 停止后端
if [ -f "logs/backend.pid" ]; then
    BACKEND_PID=$(cat logs/backend.pid)
    echo "停止后端服务 (PID: $BACKEND_PID)..."
    kill $BACKEND_PID 2>/dev/null
    rm logs/backend.pid
    echo "✓ 后端服务已停止"
else
    echo "⚠️  未找到后端进程ID文件"
    # 尝试通过端口查找并终止
    PID=$(lsof -ti:8080)
    if [ ! -z "$PID" ]; then
        kill $PID
        echo "✓ 已终止占用8080端口的进程"
    fi
fi

# 停止前端
if [ -f "logs/frontend.pid" ]; then
    FRONTEND_PID=$(cat logs/frontend.pid)
    echo "停止前端服务 (PID: $FRONTEND_PID)..."
    kill $FRONTEND_PID 2>/dev/null
    rm logs/frontend.pid
    echo "✓ 前端服务已停止"
else
    echo "⚠️  未找到前端进程ID文件"
    # 尝试通过端口查找并终止
    PID=$(lsof -ti:3000)
    if [ ! -z "$PID" ]; then
        kill $PID
        echo "✓ 已终止占用3000端口的进程"
    fi
fi

echo ""
echo "所有服务已停止"
