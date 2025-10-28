# 用户签到系统 - 部署验证指南

## 环境准备检查清单

### 1. 必需软件检查

```bash
# 检查 Java 版本（需要 Java 11+）
java -version

# 检查 Maven 版本（需要 Maven 3.6+）
mvn -version

# 检查 MySQL 版本（需要 MySQL 8.0+）
mysql --version
```

如果缺少任何软件，请先安装：

**Ubuntu/Debian:**
```bash
# 安装 Java 11
sudo apt update
sudo apt install openjdk-11-jdk

# 安装 Maven
sudo apt install maven

# 安装 MySQL
sudo apt install mysql-server
```

**CentOS/RHEL:**
```bash
# 安装 Java 11
sudo yum install java-11-openjdk-devel

# 安装 Maven
sudo yum install maven

# 安装 MySQL
sudo yum install mysql-server
```

---

## 部署步骤

### 第一步：数据库初始化

```bash
# 1. 启动 MySQL 服务
sudo systemctl start mysql

# 2. 登录 MySQL
mysql -u root -p

# 3. 执行初始化脚本
mysql> source /data/workspace/ao_test/database/init_database.sql;

# 4. 验证表创建成功
mysql> USE checkin_db;
mysql> SHOW TABLES;
# 应该看到：check_in_record 和 user_check_in_stats

mysql> DESC check_in_record;
mysql> DESC user_check_in_stats;

# 5. 退出
mysql> EXIT;
```

### 第二步：配置应用

编辑配置文件：
```bash
cd /data/workspace/ao_test/checkin
vi src/main/resources/application.yml
```

修改数据库连接信息：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/checkin_db?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
    username: root
    password: your_password  # 修改为实际密码
```

### 第三步：编译打包

```bash
cd /data/workspace/ao_test/checkin

# 方式1：使用构建脚本
./build_checkin.sh

# 方式2：直接使用 Maven
mvn clean package -DskipTests

# 验证 JAR 文件是否生成
ls -lh target/checkin-system-1.0.0.jar
```

预期输出：
```
-rw-r--r-- 1 user user 35M Oct 28 10:30 target/checkin-system-1.0.0.jar
```

### 第四步：运行单元测试（可选）

```bash
cd /data/workspace/ao_test/checkin

# 运行所有测试
mvn test

# 查看测试报告
cat target/surefire-reports/*.txt
```

预期结果：
```
Tests run: 9, Failures: 0, Errors: 0, Skipped: 0
```

### 第五步：启动应用

```bash
cd /data/workspace/ao_test/checkin

# 使用启动脚本
./start_checkin.sh

# 或者手动启动
java -jar target/checkin-system-1.0.0.jar
```

预期输出：
```
========================================
用户签到系统启动成功！
服务端口: 8081
API文档: http://localhost:8081/api/checkin
========================================
```

查看启动日志：
```bash
tail -f logs/checkin-system.log
```

---

## 功能验证测试

### 测试1：查询今日签到状态（未签到）

```bash
curl http://localhost:8081/api/checkin/today/10001
```

**预期响应：**
```json
{
  "code": 200,
  "message": "查询成功",
  "data": {
    "hasCheckedIn": false
  },
  "timestamp": "2025-10-28T10:30:15Z"
}
```

### 测试2：首次签到

```bash
curl -X POST http://localhost:8081/api/checkin \
  -H "Content-Type: application/json" \
  -d '{"userId": 10001}'
```

**预期响应：**
```json
{
  "code": 200,
  "message": "签到成功",
  "data": {
    "checkInId": 1,
    "userId": 10001,
    "checkInDate": "2025-10-28",
    "checkInTime": "2025-10-28T10:30:15Z",
    "continuousDays": 1,
    "totalDays": 1,
    "isNewRecord": false
  },
  "timestamp": "2025-10-28T10:30:15Z"
}
```

**验证点：**
- ✅ 返回码为 200
- ✅ continuousDays = 1（首次签到）
- ✅ totalDays = 1
- ✅ 返回了 checkInId

### 测试3：查询今日签到状态（已签到）

```bash
curl http://localhost:8081/api/checkin/today/10001
```

**预期响应：**
```json
{
  "code": 200,
  "message": "查询成功",
  "data": {
    "hasCheckedIn": true,
    "checkInTime": "2025-10-28T10:30:15Z",
    "continuousDays": 1
  },
  "timestamp": "2025-10-28T10:30:16Z"
}
```

**验证点：**
- ✅ hasCheckedIn = true
- ✅ 返回了签到时间

### 测试4：重复签到（应失败）

```bash
curl -X POST http://localhost:8081/api/checkin \
  -H "Content-Type: application/json" \
  -d '{"userId": 10001}'
```

**预期响应：**
```json
{
  "code": 400,
  "message": "今日已签到,请勿重复签到",
  "timestamp": "2025-10-28T10:30:17Z"
}
```

**验证点：**
- ✅ 返回码为 400
- ✅ 提示重复签到错误

### 测试5：查询签到统计

```bash
curl http://localhost:8081/api/checkin/stats/10001
```

**预期响应：**
```json
{
  "code": 200,
  "message": "查询成功",
  "data": {
    "userId": 10001,
    "totalDays": 1,
    "continuousDays": 1,
    "maxContinuousDays": 1,
    "lastCheckInDate": "2025-10-28"
  },
  "timestamp": "2025-10-28T10:30:18Z"
}
```

**验证点：**
- ✅ totalDays = 1
- ✅ continuousDays = 1
- ✅ maxContinuousDays = 1

### 测试6：查询签到历史记录

```bash
curl "http://localhost:8081/api/checkin/history/10001?page=1&pageSize=10"
```

**预期响应：**
```json
{
  "code": 200,
  "message": "查询成功",
  "data": {
    "total": 1,
    "page": 1,
    "pageSize": 10,
    "records": [
      {
        "checkInId": 1,
        "checkInDate": "2025-10-28",
        "checkInTime": "2025-10-28T10:30:15Z",
        "continuousDays": 1
      }
    ]
  },
  "timestamp": "2025-10-28T10:30:19Z"
}
```

**验证点：**
- ✅ total = 1
- ✅ records 包含1条记录
- ✅ 分页参数正确

### 测试7：参数校验（无效用户ID）

```bash
curl -X POST http://localhost:8081/api/checkin \
  -H "Content-Type: application/json" \
  -d '{"userId": null}'
```

**预期响应：**
```json
{
  "code": 400,
  "message": "用户ID不能为空",
  "timestamp": "2025-10-28T10:30:20Z"
}
```

**验证点：**
- ✅ 参数校验生效
- ✅ 返回友好的错误提示

---

## 数据持久化验证

### 验证数据库记录

```bash
# 登录数据库
mysql -u root -p checkin_db

# 查询签到记录
SELECT * FROM check_in_record WHERE user_id = 10001;

# 查询用户统计
SELECT * FROM user_check_in_stats WHERE user_id = 10001;
```

**预期结果：**

check_in_record 表：
```
+----+---------+----------------+---------------------+-----------------+---------------------+
| id | user_id | check_in_date  | check_in_time       | continuous_days | created_at          |
+----+---------+----------------+---------------------+-----------------+---------------------+
|  1 |   10001 | 2025-10-28     | 2025-10-28 10:30:15 |               1 | 2025-10-28 10:30:15 |
+----+---------+----------------+---------------------+-----------------+---------------------+
```

user_check_in_stats 表：
```
+----+---------+------------+-----------------+---------------------+--------------------+---------------------+---------------------+
| id | user_id | total_days | continuous_days | max_continuous_days | last_check_in_date | created_at          | updated_at          |
+----+---------+------------+-----------------+---------------------+--------------------+---------------------+---------------------+
|  1 |   10001 |          1 |               1 |                   1 | 2025-10-28         | 2025-10-28 10:30:15 | 2025-10-28 10:30:15 |
+----+---------+------------+-----------------+---------------------+--------------------+---------------------+---------------------+
```

---

## 事务一致性验证

### 测试场景：验证事务回滚

1. **模拟数据库错误**（通过修改配置使用错误的数据库连接）
2. **执行签到请求**
3. **验证数据未被写入**

或者：

1. **查看日志中的事务信息**
```bash
grep "Transaction" logs/checkin-system.log
```

2. **验证唯一索引约束**
```bash
# 尝试手动插入重复记录
mysql -u root -p checkin_db

INSERT INTO check_in_record (user_id, check_in_date, check_in_time, continuous_days) 
VALUES (10001, '2025-10-28', NOW(), 1);
```

预期结果：应该报错 `Duplicate entry`

---

## 连续签到测试

### 模拟连续签到场景

```bash
# 方法1：修改系统日期（需要 root 权限）
# 不推荐在生产环境使用

# 方法2：直接在数据库中插入昨天的签到记录
mysql -u root -p checkin_db

INSERT INTO check_in_record (user_id, check_in_date, check_in_time, continuous_days, created_at) 
VALUES (10002, DATE_SUB(CURDATE(), INTERVAL 1 DAY), NOW(), 1, NOW());

INSERT INTO user_check_in_stats (user_id, total_days, continuous_days, max_continuous_days, last_check_in_date, created_at, updated_at)
VALUES (10002, 1, 1, 1, DATE_SUB(CURDATE(), INTERVAL 1 DAY), NOW(), NOW());

EXIT;

# 今天签到
curl -X POST http://localhost:8081/api/checkin \
  -H "Content-Type: application/json" \
  -d '{"userId": 10002}'
```

**预期响应：**
```json
{
  "code": 200,
  "message": "签到成功",
  "data": {
    "continuousDays": 2,  // 连续天数应该是2
    "totalDays": 2
  }
}
```

---

## 性能测试（可选）

### 使用 Apache Bench 进行压力测试

```bash
# 安装 ab 工具
sudo apt install apache2-utils

# 签到接口性能测试（100并发，1000请求）
ab -n 1000 -c 100 -p post_data.json -T application/json \
   http://localhost:8081/api/checkin
```

post_data.json 内容：
```json
{"userId": 10003}
```

**预期结果：**
- 响应时间 < 200ms (P99)
- 成功率 99%+（除了重复签到的正常失败）

---

## 日志检查

```bash
# 查看应用日志
tail -100 logs/checkin-system.log

# 查看错误日志
grep ERROR logs/checkin-system.log

# 查看警告日志
grep WARN logs/checkin-system.log
```

---

## 停止应用

```bash
cd /data/workspace/ao_test/checkin

# 使用停止脚本
./stop_checkin.sh

# 或者手动停止
kill $(cat checkin-system.pid)
```

---

## 验证清单总结

### ✅ 基础功能验证
- [ ] 首次签到成功
- [ ] 今日签到状态查询正确
- [ ] 重复签到被正确拦截
- [ ] 签到统计数据正确
- [ ] 签到历史查询正常
- [ ] 参数校验生效

### ✅ 数据一致性验证
- [ ] 数据库记录正确写入
- [ ] 签到记录表和统计表数据一致
- [ ] 唯一索引约束生效

### ✅ 业务逻辑验证
- [ ] 连续签到天数计算正确
- [ ] 中断后签到天数重置
- [ ] 最长连续天数更新正确

### ✅ 非功能性验证
- [ ] 异常处理正确
- [ ] 日志记录完整
- [ ] 接口响应时间合理
- [ ] 事务回滚机制正常

---

## 常见问题排查

### 问题1：启动失败 - 端口被占用

```bash
# 查找占用 8081 端口的进程
lsof -i :8081

# 或者
netstat -nltp | grep 8081

# 杀掉占用的进程
kill -9 <PID>
```

### 问题2：数据库连接失败

```bash
# 检查 MySQL 服务状态
sudo systemctl status mysql

# 测试数据库连接
mysql -u root -p -h localhost -P 3306 checkin_db

# 检查防火墙
sudo ufw status
```

### 问题3：Maven 构建失败

```bash
# 清理本地仓库缓存
mvn clean

# 强制更新依赖
mvn clean install -U

# 跳过测试构建
mvn clean package -DskipTests
```

---

## 验证完成标准

当以上所有验证项都通过后，即可认为系统部署成功并可投入使用。

**最终确认：**
```bash
# 1. JAR 文件存在
ls -lh target/checkin-system-1.0.0.jar

# 2. 服务正常运行
curl http://localhost:8081/api/checkin/today/10001

# 3. 数据库表正常
mysql -u root -p -e "USE checkin_db; SHOW TABLES;"

# 4. 日志无严重错误
grep -i error logs/checkin-system.log | tail -20
```

全部正常后，系统即可对外提供服务！
