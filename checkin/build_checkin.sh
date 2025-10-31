#!/bin/bash

# ========================================
# 用户签到系统构建脚本
# ========================================

echo "========================================"
echo "开始构建用户签到系统..."
echo "========================================"

# 检查 Maven 环境
if ! command -v mvn &> /dev/null; then
    echo "错误: 未找到 Maven，请先安装 Maven"
    exit 1
fi

# 清理并编译
echo "执行: mvn clean package -DskipTests"
mvn clean package -DskipTests

# 检查构建结果
if [ $? -eq 0 ]; then
    echo "========================================"
    echo "✓ 构建成功！"
    echo "========================================"
    echo "JAR 文件位置: target/checkin-system-1.0.0.jar"
    echo ""
    echo "后续步骤:"
    echo "1. 初始化数据库: mysql < ../database/init_database.sql"
    echo "2. 修改配置文件: src/main/resources/application.yml"
    echo "3. 启动应用: ./start_checkin.sh"
    echo "========================================"
else
    echo "========================================"
    echo "✗ 构建失败，请检查错误信息"
    echo "========================================"
    exit 1
fi
