#!/bin/bash

# HelloEnglish 启动脚本
# 用于编译和运行 HelloEnglish.java 程序

# 颜色定义
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${BLUE}================================${NC}"
echo -e "${BLUE}  HelloEnglish 启动脚本${NC}"
echo -e "${BLUE}================================${NC}\n"

# 检查Java环境
if ! command -v javac &> /dev/null; then
    echo -e "${YELLOW}错误: 未找到javac命令，请先安装JDK${NC}"
    exit 1
fi

# 编译Java程序
echo -e "${GREEN}[1/2] 编译 HelloEnglish.java...${NC}"
javac HelloEnglish.java

if [ $? -ne 0 ]; then
    echo -e "${YELLOW}编译失败，请检查代码${NC}"
    exit 1
fi

echo -e "${GREEN}[2/2] 运行 HelloEnglish 程序...${NC}\n"
echo -e "${BLUE}================================${NC}\n"

# 运行程序
if [ $# -eq 0 ]; then
    # 无参数运行
    java HelloEnglish
else
    # 带参数运行
    java HelloEnglish "$1"
fi

echo -e "\n${BLUE}================================${NC}"
echo -e "${GREEN}程序执行完成!${NC}"
