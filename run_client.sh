#!/bin/bash

# 客户端使用脚本

SERVER_URL=${1:-"http://localhost:8080"}

echo "远程 Hello World 客户端"
echo "========================"
echo "服务器地址: $SERVER_URL"
echo ""

# 显示使用示例
if [ "$2" == "--help" ] || [ "$2" == "-h" ]; then
    echo "使用方法:"
    echo "  ./run_client.sh [服务器地址] [选项]"
    echo ""
    echo "选项:"
    echo "  --name <名称>        指定接收问候的对象名称"
    echo "  --lang <语言>        指定语言 (zh/en/es/fr)"
    echo "  --health             检查服务健康状态"
    echo "  --version            显示客户端版本"
    echo ""
    echo "示例:"
    echo "  ./run_client.sh                              # 基本问候"
    echo "  ./run_client.sh http://localhost:8080 --name=张三 --lang=zh"
    echo "  ./run_client.sh http://localhost:8080 --health"
    exit 0
fi

# 进入客户端目录并运行
cd client
shift
go run main.go -server="$SERVER_URL" "$@"
