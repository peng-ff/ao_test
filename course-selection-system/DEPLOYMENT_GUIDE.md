# 选课系统部署指南

## 目录
1. [环境准备](#环境准备)
2. [数据库部署](#数据库部署)
3. [应用部署](#应用部署)
4. [运维管理](#运维管理)
5. [问题排查](#问题排查)

## 环境准备

### 1.1 服务器配置要求

**最低配置:**
- CPU: 2核
- 内存: 4GB
- 磁盘: 50GB
- 操作系统: Linux (Ubuntu 20.04 / CentOS 8)

**推荐配置:**
- CPU: 4核
- 内存: 8GB
- 磁盘: 100GB SSD
- 操作系统: Linux (Ubuntu 22.04 / CentOS 8)

### 1.2 软件依赖安装

#### 安装JDK 11

**Ubuntu/Debian:**
```bash
sudo apt update
sudo apt install openjdk-11-jdk -y
java -version
```

**CentOS/RHEL:**
```bash
sudo yum install java-11-openjdk-devel -y
java -version
```

#### 安装Maven

```bash
# 下载Maven
wget https://dlcdn.apache.org/maven/maven-3/3.9.5/binaries/apache-maven-3.9.5-bin.tar.gz

# 解压
tar -xzf apache-maven-3.9.5-bin.tar.gz
sudo mv apache-maven-3.9.5 /opt/maven

# 配置环境变量
echo 'export MAVEN_HOME=/opt/maven' >> ~/.bashrc
echo 'export PATH=$PATH:$MAVEN_HOME/bin' >> ~/.bashrc
source ~/.bashrc

# 验证安装
mvn -version
```

#### 安装MySQL 8.0

**Ubuntu/Debian:**
```bash
sudo apt update
sudo apt install mysql-server -y
sudo systemctl start mysql
sudo systemctl enable mysql
sudo mysql_secure_installation
```

**CentOS/RHEL:**
```bash
sudo yum install mysql-server -y
sudo systemctl start mysqld
sudo systemctl enable mysqld
sudo mysql_secure_installation
```

#### 安装Redis (可选)

```bash
# Ubuntu/Debian
sudo apt install redis-server -y

# CentOS/RHEL
sudo yum install redis -y

# 启动Redis
sudo systemctl start redis
sudo systemctl enable redis
```

## 数据库部署

### 2.1 创建数据库和用户

```bash
# 登录MySQL
mysql -u root -p

# 执行以下SQL
CREATE DATABASE course_selection_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE USER 'courseselection'@'localhost' IDENTIFIED BY 'YourStrongPassword123!';
GRANT ALL PRIVILEGES ON course_selection_db.* TO 'courseselection'@'localhost';
FLUSH PRIVILEGES;

EXIT;
```

### 2.2 初始化数据库表

```bash
mysql -u courseselection -p course_selection_db < database/init_database.sql
```

### 2.3 验证数据库

```bash
mysql -u courseselection -p course_selection_db

# 查看表
SHOW TABLES;

# 查看示例数据
SELECT * FROM student;
SELECT * FROM course;

EXIT;
```

## 应用部署

### 3.1 上传代码

将项目代码上传到服务器:

```bash
# 使用git
git clone <your-repo-url>
cd course-selection-system

# 或使用scp
scp -r course-selection-system/ user@server:/opt/
```

### 3.2 配置应用

编辑配置文件 `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/course_selection_db?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: courseselection
    password: YourStrongPassword123!
```

或使用环境变量:

```bash
# 创建环境配置文件
cat > /opt/course-selection-system/.env << EOF
DB_PASSWORD=YourStrongPassword123!
SERVER_PORT=8080
JWT_SECRET=your-very-long-secret-key-at-least-256-bits-change-this-in-production
EOF
```

### 3.3 构建应用

```bash
cd /opt/course-selection-system
chmod +x *.sh
./build.sh
```

### 3.4 启动应用

**前台启动(用于测试):**
```bash
./start.sh
```

**后台启动(生产环境):**
```bash
nohup java -Xms512m -Xmx1024m -jar target/course-selection-system.jar > logs/app.log 2>&1 &
echo $! > app.pid
```

### 3.5 验证部署

```bash
# 检查应用是否启动
ps -ef | grep course-selection-system

# 检查端口是否监听
netstat -tlnp | grep 8080

# 测试健康检查
curl http://localhost:8080/swagger-ui.html

# 测试登录接口
curl -X POST http://localhost:8080/api/auth/student/login \
  -H "Content-Type: application/json" \
  -d '{"studentNumber":"2021001","password":"student123"}'
```

### 3.6 配置Nginx反向代理(推荐)

安装Nginx:
```bash
sudo apt install nginx -y  # Ubuntu
sudo yum install nginx -y  # CentOS
```

配置Nginx:
```bash
sudo vi /etc/nginx/sites-available/course-selection

# 添加以下内容:
server {
    listen 80;
    server_name your-domain.com;  # 修改为你的域名

    location / {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }

    # 上传文件大小限制
    client_max_body_size 10M;
}

# 启用配置
sudo ln -s /etc/nginx/sites-available/course-selection /etc/nginx/sites-enabled/
sudo nginx -t
sudo systemctl restart nginx
```

### 3.7 配置systemd服务(推荐)

创建systemd服务文件:

```bash
sudo vi /etc/systemd/system/course-selection.service

# 添加以下内容:
[Unit]
Description=Course Selection System
After=mysql.service

[Service]
Type=simple
User=root
WorkingDirectory=/opt/course-selection-system
Environment="DB_PASSWORD=YourStrongPassword123!"
Environment="SERVER_PORT=8080"
ExecStart=/usr/bin/java -Xms512m -Xmx1024m -jar /opt/course-selection-system/target/course-selection-system.jar
Restart=on-failure
RestartSec=10
StandardOutput=journal
StandardError=journal

[Install]
WantedBy=multi-user.target
```

启用服务:
```bash
sudo systemctl daemon-reload
sudo systemctl enable course-selection
sudo systemctl start course-selection
sudo systemctl status course-selection
```

## 运维管理

### 4.1 日志管理

查看日志:
```bash
# 查看应用日志
tail -f /opt/course-selection-system/logs/course-selection.log

# 查看systemd日志
sudo journalctl -u course-selection -f

# 查看最近100行日志
tail -n 100 /opt/course-selection-system/logs/course-selection.log
```

日志轮转配置:
```bash
sudo vi /etc/logrotate.d/course-selection

# 添加以下内容:
/opt/course-selection-system/logs/*.log {
    daily
    rotate 30
    compress
    delaycompress
    missingok
    notifempty
    create 0644 root root
}
```

### 4.2 备份策略

数据库备份脚本:
```bash
#!/bin/bash
# /opt/scripts/backup-database.sh

BACKUP_DIR="/opt/backups/database"
DATE=$(date +%Y%m%d_%H%M%S)
FILENAME="course_selection_db_${DATE}.sql"

mkdir -p $BACKUP_DIR

mysqldump -u courseselection -p'YourStrongPassword123!' \
  course_selection_db > ${BACKUP_DIR}/${FILENAME}

# 压缩备份
gzip ${BACKUP_DIR}/${FILENAME}

# 删除30天前的备份
find $BACKUP_DIR -name "*.sql.gz" -mtime +30 -delete

echo "Backup completed: ${FILENAME}.gz"
```

添加定时任务:
```bash
crontab -e

# 每天凌晨2点执行备份
0 2 * * * /opt/scripts/backup-database.sh >> /var/log/db-backup.log 2>&1
```

### 4.3 监控配置

检查服务状态:
```bash
# 检查进程
ps aux | grep course-selection-system

# 检查内存使用
free -h

# 检查磁盘空间
df -h

# 检查数据库连接
mysql -u courseselection -p -e "SHOW PROCESSLIST;"
```

### 4.4 性能优化

优化JVM参数:
```bash
# 根据服务器内存调整
java -Xms1g -Xmx2g \
     -XX:+UseG1GC \
     -XX:MaxGCPauseMillis=200 \
     -XX:+HeapDumpOnOutOfMemoryError \
     -XX:HeapDumpPath=/opt/course-selection-system/logs/ \
     -jar target/course-selection-system.jar
```

优化MySQL:
```bash
sudo vi /etc/mysql/mysql.conf.d/mysqld.cnf

# 添加优化参数
[mysqld]
max_connections=500
innodb_buffer_pool_size=1G
innodb_log_file_size=256M
query_cache_size=32M
```

## 问题排查

### 5.1 应用无法启动

检查步骤:
```bash
# 1. 检查JDK版本
java -version

# 2. 检查JAR文件
ls -lh target/course-selection-system.jar

# 3. 检查数据库连接
mysql -u courseselection -p -e "SELECT 1;"

# 4. 查看详细错误日志
tail -n 200 logs/course-selection.log
```

### 5.2 数据库连接失败

```bash
# 检查MySQL服务
sudo systemctl status mysql

# 检查防火墙
sudo ufw status
sudo firewall-cmd --list-all

# 测试数据库连接
telnet localhost 3306
```

### 5.3 内存溢出

```bash
# 检查内存使用
jmap -heap <pid>

# 导出堆转储
jmap -dump:format=b,file=heapdump.hprof <pid>

# 增加JVM内存
java -Xms2g -Xmx4g -jar target/course-selection-system.jar
```

### 5.4 性能问题

```bash
# 查看线程状态
jstack <pid> > thread-dump.txt

# 查看GC情况
jstat -gcutil <pid> 1000

# 慢查询分析
mysql> SHOW PROCESSLIST;
mysql> SHOW FULL PROCESSLIST;
```

## 安全加固

### 6.1 防火墙配置

```bash
# Ubuntu (UFW)
sudo ufw allow 22/tcp
sudo ufw allow 80/tcp
sudo ufw allow 443/tcp
sudo ufw enable

# CentOS (firewalld)
sudo firewall-cmd --permanent --add-service=http
sudo firewall-cmd --permanent --add-service=https
sudo firewall-cmd --reload
```

### 6.2 SSL证书配置

使用Let's Encrypt免费证书:
```bash
sudo apt install certbot python3-certbot-nginx
sudo certbot --nginx -d your-domain.com
```

### 6.3 修改默认密码

登录系统后,立即修改所有默认密码:
- 管理员密码
- 示例学生密码
- 数据库密码

## 升级部署

```bash
# 1. 备份数据
./scripts/backup-database.sh

# 2. 停止应用
sudo systemctl stop course-selection

# 3. 更新代码
git pull origin main

# 4. 构建新版本
./build.sh

# 5. 启动应用
sudo systemctl start course-selection

# 6. 验证
curl http://localhost:8080/swagger-ui.html
```

## 联系支持

如遇到部署问题,请联系技术支持团队。
