#!/bin/bash

# 编译脚本 - 编译服务端和客户端

echo "开始编译远程 Hello World 程序..."
echo ""

# 创建输出目录
mkdir -p build

# 编译服务端
echo "1. 编译服务端..."
cd server
go build -o ../build/hello-server main.go
if [ $? -eq 0 ]; then
    echo "   ✓ 服务端编译成功: build/hello-server"
else
    echo "   ✗ 服务端编译失败"
    exit 1
fi
cd ..

# 编译客户端
echo ""
echo "2. 编译客户端..."
cd client
go build -o ../build/hello-client main.go
if [ $? -eq 0 ]; then
    echo "   ✓ 客户端编译成功: build/hello-client"
else
    echo "   ✗ 客户端编译失败"
    exit 1
fi
cd ..

echo ""
echo "=========================================="
echo "编译完成！"
echo "=========================================="
echo ""
echo "运行服务端:"
echo "  ./build/hello-server"
echo ""
echo "运行客户端:"
echo "  ./build/hello-client -server=http://localhost:8080"
