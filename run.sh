#!/bin/bash
# 简单计算器编译和运行脚本

# 设置颜色输出
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${GREEN}==================================${NC}"
echo -e "${GREEN}   简单计算器 - 编译和运行${NC}"
echo -e "${GREEN}==================================${NC}"
echo ""

# 检查 Java 是否安装
if ! command -v javac &> /dev/null; then
    echo -e "${RED}错误: 未找到 javac 命令${NC}"
    echo -e "${YELLOW}请先安装 JDK:${NC}"
    echo "  sudo apt install openjdk-8-jdk"
    echo "  或"
    echo "  sudo apt install default-jdk"
    exit 1
fi

# 显示 Java 版本
echo -e "${GREEN}Java 版本:${NC}"
java -version
echo ""

# 创建输出目录
echo -e "${GREEN}创建输出目录...${NC}"
mkdir -p target/classes

# 编译源代码
echo -e "${GREEN}编译源代码...${NC}"
javac -d target/classes -sourcepath src/main/java src/main/java/com/square/calculator/*.java

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✓ 编译成功!${NC}"
    echo ""
    
    # 运行程序
    echo -e "${GREEN}启动简单计算器...${NC}"
    echo ""
    java -cp target/classes com.square.calculator.SimpleCalculator
else
    echo -e "${RED}✗ 编译失败!${NC}"
    exit 1
fi
