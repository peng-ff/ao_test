#!/bin/bash

# 启动服务端脚本

echo "正在启动 Hello World 服务端..."

# 检查是否存在配置文件
if [ ! -f .env ]; then
    echo "未找到 .env 配置文件，使用默认配置"
    cp .env.example .env 2>/dev/null || true
fi

# 加载环境变量
if [ -f .env ]; then
    export $(cat .env | grep -v '^#' | xargs)
fi

# 进入服务端目录
cd server

# 启动服务
echo "服务端启动在 ${SERVER_HOST:-0.0.0.0}:${SERVER_PORT:-8080}"
go run main.go
