# 选课系统 (Course Selection System)

## 项目简介

这是一个面向中小型学校的选课管理系统,支持学生在线选课、退课,管理员管理课程和选课数据。系统实现了选课冲突检测(时间冲突、人数限制、前置课程检查)等核心功能。

## 技术栈

- **后端框架**: Spring Boot 2.7.18
- **数据访问**: Spring Data JPA
- **数据库**: MySQL 8.0+
- **缓存**: Redis (可选)
- **认证**: JWT (JSON Web Token)
- **安全**: Spring Security
- **API文档**: Swagger/OpenAPI 3.0
- **构建工具**: Maven
- **JDK版本**: 11+

## 核心功能

### 学生端
- ✅ 学生登录
- ✅ 浏览课程列表
- ✅ 查看课程详情
- ✅ 选课(含冲突检测)
- ✅ 退课
- ✅ 查看已选课程
- ✅ 查看个人课表

### 管理员端
- ✅ 管理员登录
- ✅ 创建/修改/删除课程
- ✅ 管理课程时间表
- ✅ 查看选课统计
- ✅ 管理学生账户

### 选课规则
- **时间冲突检测**: 不能选择上课时间重叠的课程
- **课程容量限制**: 选课人数不能超过课程容量
- **前置课程检查**: 必须先完成前置课程才能选课
- **重复选课检查**: 不能重复选择同一课程

## 快速开始

### 1. 环境要求

- JDK 11 或更高版本
- Maven 3.6+
- MySQL 8.0+
- Redis (可选)

### 2. 数据库初始化

```bash
# 登录MySQL
mysql -u root -p

# 执行初始化脚本
source database/init_database.sql
```

### 3. 配置文件

编辑 `src/main/resources/application.yml`,修改数据库连接信息:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/course_selection_db
    username: root
    password: your_password  # 修改为你的密码
```

### 4. 构建项目

```bash
./build.sh
```

### 5. 启动应用

```bash
./start.sh
```

应用将在 `http://localhost:8080` 启动

### 6. 访问API文档

打开浏览器访问: `http://localhost:8080/swagger-ui.html`

## 测试账户

### 管理员账户
- 用户名: `admin`
- 密码: `admin123`

### 学生账户
- 学号: `2021001`, 密码: `student123` (张三)
- 学号: `2021002`, 密码: `student123` (李四)
- 学号: `2022001`, 密码: `student123` (王五)

## API接口示例

### 1. 学生登录

**请求:**
```bash
curl -X POST http://localhost:8080/api/auth/student/login \
  -H "Content-Type: application/json" \
  -d '{
    "studentNumber": "2021001",
    "password": "student123"
  }'
```

**响应:**
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "userType": "STUDENT",
    "userId": 1,
    "name": "张三"
  },
  "timestamp": "2024-12-30T10:00:00"
}
```

### 2. 选课

**请求:**
```bash
curl -X POST http://localhost:8080/api/selections \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "courseId": 1
  }'
```

**响应:**
```json
{
  "code": 200,
  "message": "选课成功",
  "data": {
    "id": 1,
    "studentId": 1,
    "courseId": 1,
    "selectionTime": "2024-12-30T10:05:00",
    "status": "SELECTED"
  },
  "timestamp": "2024-12-30T10:05:00"
}
```

### 3. 退课

**请求:**
```bash
curl -X DELETE http://localhost:8080/api/selections/1 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### 4. 查询我的选课

**请求:**
```bash
curl -X GET http://localhost:8080/api/selections/my \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## 项目结构

```
course-selection-system/
├── src/
│   ├── main/
│   │   ├── java/com/courseselection/
│   │   │   ├── entity/              # 实体类
│   │   │   ├── repository/          # 数据访问层
│   │   │   ├── service/             # 业务逻辑层
│   │   │   ├── controller/          # 控制器层
│   │   │   ├── dto/                 # 数据传输对象
│   │   │   ├── security/            # 安全相关
│   │   │   ├── config/              # 配置类
│   │   │   ├── exception/           # 异常处理
│   │   │   └── CourseSelectionApplication.java
│   │   └── resources/
│   │       └── application.yml      # 配置文件
│   └── test/                        # 测试代码
├── database/
│   └── init_database.sql            # 数据库初始化脚本
├── pom.xml                          # Maven配置
├── build.sh                         # 构建脚本
├── start.sh                         # 启动脚本
├── stop.sh                          # 停止脚本
└── README.md                        # 项目说明
```

## 核心设计

### 选课冲突检测流程

```
1. 验证课程是否存在
2. 检查课程状态是否开放
3. 检查是否已选该课程
4. 检查课程容量是否已满
5. 检查前置课程是否完成
6. 检查时间是否冲突
7. 创建选课记录
8. 增加课程选课人数
```

### 并发控制

- 使用JPA乐观锁(@Version)防止课程超额选课
- 使用数据库事务保证数据一致性
- 使用唯一约束防止重复选课

### 安全设计

- 使用BCrypt加密存储密码
- JWT令牌认证,有效期24小时
- 基于角色的访问控制(RBAC)
- 学生只能操作自己的选课数据

## 环境变量配置

可以通过环境变量覆盖默认配置:

```bash
export DB_PASSWORD=your_password
export SERVER_PORT=8080
export REDIS_HOST=localhost
export REDIS_PORT=6379
export JWT_SECRET=your_secret_key
```

## 日志

应用日志默认输出到:
- 控制台: 实时输出
- 文件: `logs/course-selection.log`

日志配置可在 `application.yml` 中修改。

## 故障排查

### 1. 数据库连接失败

检查:
- MySQL是否运行: `systemctl status mysql`
- 数据库配置是否正确
- 用户名密码是否正确

### 2. 端口被占用

修改 `application.yml` 中的端口配置,或使用环境变量:
```bash
export SERVER_PORT=8081
```

### 3. JWT认证失败

检查:
- Token是否过期(默认24小时)
- Authorization header格式是否正确: `Bearer {token}`

## 性能优化建议

1. 启用Redis缓存热门课程数据
2. 为高频查询添加数据库索引
3. 使用连接池优化数据库连接
4. 选课高峰期增加服务器资源

## 扩展功能

系统预留了以下扩展点:
- 选课轮次管理
- 课程评价功能
- 学分管理
- 专业限制
- 候补名单

## 贡献指南

欢迎提交Issue和Pull Request!

## 许可证

MIT License

## 联系方式

如有问题,请联系系统管理员。
