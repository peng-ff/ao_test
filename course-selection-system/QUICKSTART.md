# 选课系统 - 5分钟快速启动

## 一、环境检查

```bash
# 检查JDK版本(需要11+)
java -version

# 检查Maven版本
mvn -version

# 检查MySQL服务
sudo systemctl status mysql
```

## 二、数据库初始化

```bash
# 1. 登录MySQL
mysql -u root -p

# 2. 执行初始化脚本(在MySQL命令行中)
source /data/workspace/ao_test/course-selection-system/database/init_database.sql

# 3. 验证数据库
USE course_selection_db;
SHOW TABLES;
SELECT COUNT(*) FROM student;  -- 应该返回3
SELECT COUNT(*) FROM course;   -- 应该返回5
EXIT;
```

## 三、构建和启动

```bash
# 1. 进入项目目录
cd /data/workspace/ao_test/course-selection-system

# 2. 构建项目
./build.sh

# 3. 启动应用
./start.sh
```

启动成功后会显示:
```
==============================================
选课系统启动成功!
API文档地址: http://localhost:8080/swagger-ui.html
==============================================
```

## 四、验证系统

### 方式1: 使用curl测试

```bash
# 测试学生登录
curl -X POST http://localhost:8080/api/auth/student/login \
  -H "Content-Type: application/json" \
  -d '{"studentNumber":"2021001","password":"student123"}'
```

成功返回示例:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "userType": "STUDENT",
    "userId": 1,
    "name": "张三"
  }
}
```

### 方式2: 使用浏览器

访问: `http://localhost:8080/swagger-ui.html`

## 五、测试选课功能

### 1. 获取Token

使用上面登录接口返回的token,复制保存。

### 2. 选课

```bash
# 替换YOUR_TOKEN为实际token
curl -X POST http://localhost:8080/api/selections \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{"courseId":3}'
```

成功返回:
```json
{
  "code": 200,
  "message": "选课成功",
  "data": {
    "id": 1,
    "studentId": 1,
    "courseId": 3,
    "status": "SELECTED"
  }
}
```

### 3. 查看已选课程

```bash
curl -X GET http://localhost:8080/api/selections/my \
  -H "Authorization: Bearer YOUR_TOKEN"
```

## 六、使用Swagger测试(推荐)

1. 访问: `http://localhost:8080/swagger-ui.html`
2. 展开 "认证接口" -> "POST /api/auth/student/login"
3. 点击 "Try it out"
4. 输入:
   ```json
   {
     "studentNumber": "2021001",
     "password": "student123"
   }
   ```
5. 点击 "Execute"
6. 复制响应中的 `token`
7. 点击页面右上角的 "Authorize" 按钮
8. 输入: `Bearer {复制的token}`
9. 点击 "Authorize"
10. 现在可以测试其他接口了!

## 七、测试账户

| 角色 | 账号 | 密码 | 说明 |
|-----|------|------|------|
| 学生 | 2021001 | student123 | 张三,已完成数据结构课程 |
| 学生 | 2021002 | student123 | 李四 |
| 学生 | 2022001 | student123 | 王五 |
| 管理员 | admin | admin123 | 系统管理员 |

## 八、可选课程列表

| ID | 课程代码 | 课程名称 | 教师 | 容量 | 学分 |
|----|---------|---------|------|------|------|
| 1 | CS101 | 数据结构 | 刘教授 | 60 | 4 |
| 2 | CS102 | 算法设计与分析 | 陈教授 | 50 | 4 |
| 3 | CS201 | 数据库系统 | 王教授 | 55 | 3 |
| 4 | CS202 | 操作系统 | 李教授 | 50 | 4 |
| 5 | MATH101 | 高等数学A | 张教授 | 80 | 5 |

**注意:** 课程2(算法设计)需要先完成课程1(数据结构)

## 九、停止应用

```bash
# 方式1: 使用停止脚本
./stop.sh

# 方式2: 使用Ctrl+C(如果是前台运行)
```

## 十、常见问题

### Q1: 端口8080被占用

**解决方案:**
```bash
# 查看占用端口的进程
lsof -i:8080

# 或修改端口(编辑 application.yml)
server:
  port: 8081
```

### Q2: 数据库连接失败

**检查:**
```bash
# 1. 检查MySQL是否运行
sudo systemctl status mysql

# 2. 检查数据库密码(application.yml)
spring:
  datasource:
    password: root  # 改为你的密码
```

### Q3: Maven构建失败

**解决方案:**
```bash
# 清理后重新构建
mvn clean
mvn package -DskipTests
```

## 十一、下一步

- 查看详细文档: `README.md`
- 查看部署指南: `DEPLOYMENT_GUIDE.md`
- 查看测试指南: `TESTING_GUIDE.md`
- 查看项目总结: `PROJECT_SUMMARY.md`

## 十二、技术支持

- API文档: http://localhost:8080/swagger-ui.html
- 日志文件: `logs/course-selection.log`
- 项目位置: `/data/workspace/ao_test/course-selection-system`

---

**恭喜!** 选课系统已成功启动,现在可以开始使用了! 🎉
