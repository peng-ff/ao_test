# 用户签到系统 API 文档

## 项目概述

用户签到系统是一个基于 Spring Boot + MyBatis + MySQL 的 RESTful API 服务，提供用户签到、签到记录查询和统计功能。

## 技术栈

- **开发语言**: Java 11
- **框架**: Spring Boot 2.7.18
- **ORM**: MyBatis 2.3.2
- **数据库**: MySQL 8.0+
- **连接池**: HikariCP
- **构建工具**: Maven

## 快速开始

### 1. 环境要求

- Java 11 或更高版本
- MySQL 8.0 或更高版本
- Maven 3.6 或更高版本

### 2. 数据库初始化

```bash
# 进入数据库目录
cd ../database

# 执行初始化脚本
mysql -u root -p < init_database.sql
```

### 3. 配置修改

编辑 `src/main/resources/application.yml`，修改数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/checkin_db?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: your_password  # 修改为你的密码
```

### 4. 构建运行

```bash
# 构建项目
./build_checkin.sh
# 或者
mvn clean package

# 启动服务
./start_checkin.sh

# 停止服务
./stop_checkin.sh
```

服务将在 `http://localhost:8081` 启动

## API 接口说明

### 基础信息

- **Base URL**: `http://localhost:8081`
- **Content-Type**: `application/json`
- **响应格式**: 统一 JSON 格式

### 统一响应格式

```json
{
  "code": 200,
  "message": "操作成功",
  "data": { ... },
  "timestamp": "2025-10-28T10:30:15Z"
}
```

### 错误码说明

| 错误码 | 说明 |
|--------|------|
| 200 | 成功 |
| 400 | 请求参数错误 / 业务异常 |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |

---

## 1. 用户签到

### 接口信息

- **URL**: `/api/checkin`
- **Method**: `POST`
- **描述**: 用户执行签到操作

### 请求参数

```json
{
  "userId": 10001
}
```

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| userId | Long | 是 | 用户ID（必须为正整数） |

### 成功响应示例

```json
{
  "code": 200,
  "message": "签到成功",
  "data": {
    "checkInId": 1,
    "userId": 10001,
    "checkInDate": "2025-10-28",
    "checkInTime": "2025-10-28T09:30:15Z",
    "continuousDays": 7,
    "totalDays": 45,
    "isNewRecord": false
  },
  "timestamp": "2025-10-28T09:30:15Z"
}
```

### 重复签到响应

```json
{
  "code": 400,
  "message": "今日已签到,请勿重复签到",
  "timestamp": "2025-10-28T10:30:15Z"
}
```

### cURL 示例

```bash
curl -X POST http://localhost:8081/api/checkin \
  -H "Content-Type: application/json" \
  -d '{"userId": 10001}'
```

---

## 2. 查询今日签到状态

### 接口信息

- **URL**: `/api/checkin/today/{userId}`
- **Method**: `GET`
- **描述**: 查询用户今天是否已签到

### 路径参数

| 参数名 | 类型 | 说明 |
|--------|------|------|
| userId | Long | 用户ID |

### 已签到响应示例

```json
{
  "code": 200,
  "message": "查询成功",
  "data": {
    "hasCheckedIn": true,
    "checkInTime": "2025-10-28T09:30:15Z",
    "continuousDays": 7
  },
  "timestamp": "2025-10-28T10:30:15Z"
}
```

### 未签到响应示例

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

### cURL 示例

```bash
curl http://localhost:8081/api/checkin/today/10001
```

---

## 3. 查询用户签到统计

### 接口信息

- **URL**: `/api/checkin/stats/{userId}`
- **Method**: `GET`
- **描述**: 查询用户的签到统计信息

### 路径参数

| 参数名 | 类型 | 说明 |
|--------|------|------|
| userId | Long | 用户ID |

### 成功响应示例

```json
{
  "code": 200,
  "message": "查询成功",
  "data": {
    "userId": 10001,
    "totalDays": 45,
    "continuousDays": 7,
    "maxContinuousDays": 15,
    "lastCheckInDate": "2025-10-28"
  },
  "timestamp": "2025-10-28T10:30:15Z"
}
```

### cURL 示例

```bash
curl http://localhost:8081/api/checkin/stats/10001
```

---

## 4. 查询签到历史记录

### 接口信息

- **URL**: `/api/checkin/history/{userId}`
- **Method**: `GET`
- **描述**: 分页查询用户的签到历史记录

### 路径参数

| 参数名 | 类型 | 说明 |
|--------|------|------|
| userId | Long | 用户ID |

### 查询参数

| 参数名 | 类型 | 必填 | 默认值 | 说明 |
|--------|------|------|--------|------|
| page | Integer | 否 | 1 | 页码（从1开始） |
| pageSize | Integer | 否 | 20 | 每页记录数（最大100） |
| startDate | String | 否 | - | 开始日期（格式：yyyy-MM-dd） |
| endDate | String | 否 | - | 结束日期（格式：yyyy-MM-dd） |

### 成功响应示例

```json
{
  "code": 200,
  "message": "查询成功",
  "data": {
    "total": 45,
    "page": 1,
    "pageSize": 10,
    "records": [
      {
        "checkInId": 98765,
        "checkInDate": "2025-10-28",
        "checkInTime": "2025-10-28T09:30:15Z",
        "continuousDays": 7
      },
      {
        "checkInId": 98764,
        "checkInDate": "2025-10-27",
        "checkInTime": "2025-10-27T08:15:30Z",
        "continuousDays": 6
      }
    ]
  },
  "timestamp": "2025-10-28T10:30:15Z"
}
```

### cURL 示例

```bash
# 基础查询
curl http://localhost:8081/api/checkin/history/10001

# 分页查询
curl "http://localhost:8081/api/checkin/history/10001?page=1&pageSize=10"

# 日期范围查询
curl "http://localhost:8081/api/checkin/history/10001?startDate=2025-10-01&endDate=2025-10-28"
```

---

## 测试示例

### 完整签到流程测试

```bash
USER_ID=10001

# 1. 查询今日签到状态（预期：未签到）
curl http://localhost:8081/api/checkin/today/$USER_ID

# 2. 执行签到
curl -X POST http://localhost:8081/api/checkin \
  -H "Content-Type: application/json" \
  -d "{\"userId\": $USER_ID}"

# 3. 再次查询今日签到状态（预期：已签到）
curl http://localhost:8081/api/checkin/today/$USER_ID

# 4. 查询签到统计
curl http://localhost:8081/api/checkin/stats/$USER_ID

# 5. 查询签到历史
curl http://localhost:8081/api/checkin/history/$USER_ID?page=1&pageSize=10

# 6. 重复签到（预期：失败）
curl -X POST http://localhost:8081/api/checkin \
  -H "Content-Type: application/json" \
  -d "{\"userId\": $USER_ID}"
```

---

## 项目结构

```
checkin/
├── src/main/java/com/square/checkin/
│   ├── CheckInApplication.java          # 主应用类
│   ├── controller/                      # 控制器层
│   │   ├── CheckInController.java       # 签到接口
│   │   └── CheckInQueryController.java  # 查询接口
│   ├── service/                         # 服务层
│   │   ├── CheckInService.java          # 签到服务
│   │   └── CheckInStatisticsService.java # 统计服务
│   ├── repository/                      # 数据访问层
│   │   ├── CheckInRecordRepository.java
│   │   └── UserCheckInStatsRepository.java
│   ├── entity/                          # 实体类
│   │   ├── CheckInRecord.java
│   │   └── UserCheckInStats.java
│   ├── dto/                             # 数据传输对象
│   │   ├── request/
│   │   └── response/
│   ├── exception/                       # 异常处理
│   │   ├── BusinessException.java
│   │   ├── CheckInException.java
│   │   └── GlobalExceptionHandler.java
│   ├── config/                          # 配置类
│   │   └── WebConfig.java
│   └── util/                            # 工具类
│       └── DateUtil.java
├── src/main/resources/
│   ├── application.yml                  # 应用配置
│   └── mapper/                          # MyBatis映射文件
│       ├── CheckInRecordMapper.xml
│       └── UserCheckInStatsMapper.xml
├── src/test/java/                       # 测试代码
├── pom.xml                              # Maven配置
├── build_checkin.sh                     # 构建脚本
├── start_checkin.sh                     # 启动脚本
└── stop_checkin.sh                      # 停止脚本
```

---

## 常见问题

### 1. 连接数据库失败

检查 `application.yml` 中的数据库连接配置是否正确，确保 MySQL 服务已启动。

### 2. 启动端口冲突

如果 8081 端口被占用，可以修改 `application.yml` 中的 `server.port` 配置。

### 3. 时区问题

系统使用服务器本地时区，数据库连接 URL 中指定了 `serverTimezone=Asia/Shanghai`。

---

## 开发与维护

### 运行单元测试

```bash
mvn test
```

### 查看日志

```bash
tail -f logs/checkin-system.log
```

### 数据库备份

```bash
mysqldump -u root -p checkin_db > backup_$(date +%Y%m%d).sql
```

---

## 联系方式

如有问题或建议，请联系开发团队。

---

**版本**: 1.0.0  
**更新时间**: 2025-10-28
