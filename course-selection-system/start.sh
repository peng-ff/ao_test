#!/bin/bash

# 选课系统启动脚本

echo "========================================="
echo "启动选课系统..."
echo "========================================="

# 进入项目目录
cd "$(dirname "$0")"

# 检查JAR文件是否存在
if [ ! -f "target/course-selection-system.jar" ]; then
    echo "错误: JAR文件不存在,请先运行 ./build.sh 构建项目"
    exit 1
fi

# 设置Java选项
JAVA_OPTS="-Xms512m -Xmx1024m"

# 设置环境变量(可选)
export DB_PASSWORD=${DB_PASSWORD:-root}
export SERVER_PORT=${SERVER_PORT:-8080}

# 启动应用
echo "启动应用,端口: $SERVER_PORT"
java $JAVA_OPTS -jar target/course-selection-system.jar

# 如果要后台运行,使用以下命令:
# nohup java $JAVA_OPTS -jar target/course-selection-system.jar > logs/app.log 2>&1 &
# echo $! > app.pid
# echo "应用已在后台启动,PID: $(cat app.pid)"
