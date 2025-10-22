#!/bin/bash

# 远程 Hello World 程序 - 快速启动指南

echo "=================================================="
echo "    远程 Hello World 程序 - 快速启动"
echo "=================================================="
echo ""

# 颜色定义
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# 步骤1: 检查Go环境
echo -e "${BLUE}[1/5] 检查 Go 环境...${NC}"
if ! command -v go &> /dev/null; then
    echo -e "${YELLOW}错误: 未安装 Go，请先安装 Go 1.21 或更高版本${NC}"
    exit 1
fi
GO_VERSION=$(go version)
echo -e "${GREEN}✓${NC} $GO_VERSION"
echo ""

# 步骤2: 下载依赖
echo -e "${BLUE}[2/5] 下载依赖包...${NC}"
go mod download
echo -e "${GREEN}✓${NC} 依赖下载完成"
echo ""

# 步骤3: 运行测试
echo -e "${BLUE}[3/5] 运行测试...${NC}"
go test ./... -cover
if [ $? -eq 0 ]; then
    echo -e "${GREEN}✓${NC} 所有测试通过"
else
    echo -e "${YELLOW}警告: 部分测试失败${NC}"
fi
echo ""

# 步骤4: 编译项目
echo -e "${BLUE}[4/5] 编译项目...${NC}"
mkdir -p build
cd server && go build -o ../build/hello-server main.go && cd ..
cd client && go build -o ../build/hello-client main.go && cd ..
echo -e "${GREEN}✓${NC} 编译完成"
echo ""

# 步骤5: 显示使用说明
echo -e "${BLUE}[5/5] 项目已就绪！${NC}"
echo ""
echo "=================================================="
echo "            使用说明"
echo "=================================================="
echo ""
echo "启动服务端:"
echo "  ./start_server.sh"
echo "  或"
echo "  ./build/hello-server"
echo "  或指定端口"
echo "  SERVER_PORT=8888 ./build/hello-server"
echo ""
echo "使用客户端 (在另一个终端):"
echo "  ./run_client.sh"
echo "  或"
echo "  ./build/hello-client"
echo ""
echo "客户端示例:"
echo "  ./build/hello-client -name=张三 -lang=zh"
echo "  ./build/hello-client -health"
echo ""
echo "测试 API:"
echo "  curl http://localhost:8080/api/hello"
echo "  curl http://localhost:8080/api/health"
echo ""
echo "查看文档:"
echo "  cat README.md"
echo "  cat DEMO.md"
echo "  cat PROJECT_SUMMARY.md"
echo ""
echo "=================================================="
echo -e "${GREEN}✓ 所有准备工作已完成！${NC}"
echo "=================================================="
