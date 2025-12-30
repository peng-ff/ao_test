#!/bin/bash

# 选课系统停止脚本

echo "========================================="
echo "停止选课系统..."
echo "========================================="

cd "$(dirname "$0")"

# 查找Java进程
PID=$(ps -ef | grep "course-selection-system.jar" | grep -v grep | awk '{print $2}')

if [ -z "$PID" ]; then
    echo "应用未运行"
else
    echo "找到进程 PID: $PID"
    kill -15 $PID
    
    # 等待进程结束
    for i in {1..30}; do
        if ps -p $PID > /dev/null; then
            echo "等待进程结束... ($i/30)"
            sleep 1
        else
            echo "应用已成功停止"
            exit 0
        fi
    done
    
    # 如果还未结束,强制kill
    if ps -p $PID > /dev/null; then
        echo "强制停止进程..."
        kill -9 $PID
        echo "应用已强制停止"
    fi
fi
