# ========================================
# 用户签到系统 Docker 镜像构建文件
# ========================================

# 第一阶段:构建阶段
FROM docker.io/library/maven:3.8-openjdk-11-slim AS builder

WORKDIR /app

# 复制 pom.xml 并下载依赖（利用 Docker 缓存）
COPY pom.xml .
RUN mvn dependency:go-offline -B

# 复制源代码
COPY src ./src

# 构建应用
RUN mvn clean package -DskipTests -B

# 第二阶段:运行阶段
FROM docker.io/library/openjdk:11-jre-slim

WORKDIR /app

# 创建日志目录
RUN mkdir -p /app/logs

# 从构建阶段复制 JAR 文件
COPY --from=builder /app/target/checkin-system-1.0.0.jar /app/app.jar

# 设置环境变量
ENV JAVA_OPTS="-Xms256m -Xmx512m" \
    SPRING_PROFILES_ACTIVE="prod" \
    SERVER_PORT="8081"

# 暴露端口
EXPOSE 8081

# 健康检查
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
    CMD curl -f http://localhost:8081/actuator/health || exit 1

# 启动应用
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE -jar /app/app.jar"]
