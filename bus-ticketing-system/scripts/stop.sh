#!/bin/bash

# 公交车售票系统停止脚本

echo "正在停止公交车售票系统..."

# 查找进程
PID=$(ps -ef | grep "bus-ticketing-system-1.0.0.jar" | grep -v grep | awk '{print $2}')

if [ -z "$PID" ]; then
    echo "未找到运行中的应用"
else
    echo "找到进程 PID: $PID"
    kill $PID
    echo "应用已停止"
fi
