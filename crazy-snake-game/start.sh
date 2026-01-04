#!/bin/bash

# 疯狂蟒蛇游戏启动脚本

echo "======================================"
echo "   疯狂蟒蛇游戏 - 启动脚本"
echo "======================================"
echo ""

# 检查Java环境
if ! command -v java &> /dev/null; then
    echo "❌ 错误: 未找到Java环境，请先安装JDK 11或更高版本"
    exit 1
fi

echo "✓ Java环境检查通过"

# 检查MySQL
if ! command -v mysql &> /dev/null; then
    echo "⚠️  警告: 未找到MySQL命令，请确保MySQL已安装并正在运行"
fi

# 检查数据库
echo ""
echo "检查数据库..."
mysql -u root -p -e "USE crazy_snake_game;" 2>/dev/null
if [ $? -ne 0 ]; then
    echo "⚠️  数据库不存在，正在创建..."
    mysql -u root -p < database/init_database.sql
    if [ $? -eq 0 ]; then
        echo "✓ 数据库创建成功"
    else
        echo "❌ 数据库创建失败，请手动执行 database/init_database.sql"
    fi
else
    echo "✓ 数据库已存在"
fi

# 启动后端
echo ""
echo "======================================"
echo "启动后端服务..."
echo "======================================"
cd backend

if [ ! -f "target/crazy-snake-game-1.0.0.jar" ]; then
    echo "编译后端项目..."
    mvn clean package -DskipTests
fi

echo "启动Spring Boot应用..."
nohup mvn spring-boot:run > ../logs/backend.log 2>&1 &
BACKEND_PID=$!
echo "后端进程ID: $BACKEND_PID"
echo $BACKEND_PID > ../logs/backend.pid

cd ..

# 等待后端启动
echo "等待后端启动..."
sleep 10

# 检查后端是否启动成功
if curl -s http://localhost:8080/api/game/items > /dev/null; then
    echo "✓ 后端服务启动成功: http://localhost:8080"
else
    echo "⚠️  后端服务可能未完全启动，请查看日志: logs/backend.log"
fi

# 启动前端
echo ""
echo "======================================"
echo "启动前端服务..."
echo "======================================"

cd frontend

# 检查Python3
if command -v python3 &> /dev/null; then
    echo "使用Python3启动HTTP服务器..."
    nohup python3 -m http.server 3000 > ../logs/frontend.log 2>&1 &
    FRONTEND_PID=$!
    echo "前端进程ID: $FRONTEND_PID"
    echo $FRONTEND_PID > ../logs/frontend.pid
    echo "✓ 前端服务启动成功: http://localhost:3000"
elif command -v python &> /dev/null; then
    echo "使用Python启动HTTP服务器..."
    nohup python -m SimpleHTTPServer 3000 > ../logs/frontend.log 2>&1 &
    FRONTEND_PID=$!
    echo "前端进程ID: $FRONTEND_PID"
    echo $FRONTEND_PID > ../logs/frontend.pid
    echo "✓ 前端服务启动成功: http://localhost:3000"
else
    echo "⚠️  未找到Python环境，请手动启动前端服务或直接用浏览器打开 frontend/index.html"
fi

cd ..

echo ""
echo "======================================"
echo "   启动完成!"
echo "======================================"
echo ""
echo "前端地址: http://localhost:3000"
echo "后端地址: http://localhost:8080"
echo ""
echo "查看后端日志: tail -f logs/backend.log"
echo "查看前端日志: tail -f logs/frontend.log"
echo ""
echo "停止服务: ./stop.sh"
echo ""
