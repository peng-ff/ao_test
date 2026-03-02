# 选课系统快速测试指南

## 前置条件

确保已完成以下步骤:
1. MySQL已安装并运行
2. 数据库已初始化(执行了 init_database.sql)
3. 应用已启动(执行了 ./build.sh 和 ./start.sh)

## 测试步骤

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

**预期响应:**
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

**保存token**: 将响应中的token保存,后续请求需要使用。

### 2. 选课成功场景

使用获取的token选择课程ID=3(数据库系统):

**请求:**
```bash
curl -X POST http://localhost:8080/api/selections \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -d '{
    "courseId": 3
  }'
```

**预期结果:**
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

### 3. 时间冲突测试

张三已选课程3(数据库系统:周一5-6节,周五1-2节),现在尝试选课程1(数据结构:周一1-2节,周三3-4节):

**请求:**
```bash
curl -X POST http://localhost:8080/api/selections \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -d '{
    "courseId": 1
  }'
```

**预期结果:** 选课成功(无冲突)

### 4. 前置课程测试

张三尝试选择课程2(算法设计),该课程需要先完成课程1(数据结构):

**请求:**
```bash
curl -X POST http://localhost:8080/api/selections \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -d '{
    "courseId": 2
  }'
```

**预期结果:** 选课成功(张三已完成数据结构)

### 5. 查询已选课程

**请求:**
```bash
curl -X GET http://localhost:8080/api/selections/my \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

**预期结果:**
```json
{
  "code": 200,
  "message": "操作成功",
  "data": [
    {
      "id": 1,
      "studentId": 1,
      "courseId": 3,
      "status": "SELECTED"
    },
    {
      "id": 2,
      "studentId": 1,
      "courseId": 1,
      "status": "SELECTED"
    }
  ]
}
```

### 6. 退课测试

退选第一门课程(选课记录ID=1):

**请求:**
```bash
curl -X DELETE http://localhost:8080/api/selections/1 \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

**预期结果:**
```json
{
  "code": 200,
  "message": "退课成功",
  "data": null
}
```

### 7. 重复选课测试

再次选择已选过的课程:

**请求:**
```bash
curl -X POST http://localhost:8080/api/selections \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -d '{
    "courseId": 1
  }'
```

**预期结果:**
```json
{
  "code": 400,
  "message": "您已选择该课程"
}
```

### 8. 管理员登录

**请求:**
```bash
curl -X POST http://localhost:8080/api/auth/admin/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

**预期响应:**
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "userType": "ADMIN",
    "userId": 1,
    "name": "系统管理员"
  }
}
```

## 错误场景测试

### 1. 无效token

使用错误的token访问:

**请求:**
```bash
curl -X GET http://localhost:8080/api/selections/my \
  -H "Authorization: Bearer invalid_token"
```

**预期结果:** HTTP 401 Unauthorized

### 2. 学号密码错误

**请求:**
```bash
curl -X POST http://localhost:8080/api/auth/student/login \
  -H "Content-Type: application/json" \
  -d '{
    "studentNumber": "2021001",
    "password": "wrong_password"
  }'
```

**预期结果:**
```json
{
  "code": 401,
  "message": "用户名或密码错误"
}
```

### 3. 参数验证失败

缺少必填参数:

**请求:**
```bash
curl -X POST http://localhost:8080/api/auth/student/login \
  -H "Content-Type: application/json" \
  -d '{
    "studentNumber": ""
  }'
```

**预期结果:**
```json
{
  "code": 400,
  "message": "参数验证失败",
  "data": {
    "studentNumber": "学号不能为空",
    "password": "密码不能为空"
  }
}
```

## 使用Swagger测试

访问 `http://localhost:8080/swagger-ui.html` 使用可视化界面测试:

1. 点击 "认证接口" -> "POST /api/auth/student/login"
2. 点击 "Try it out"
3. 输入测试数据
4. 点击 "Execute"
5. 复制响应中的token
6. 点击页面右上角 "Authorize"
7. 输入 `Bearer {token}`
8. 现在可以测试需要认证的接口了

## 数据库验证

连接数据库查看数据变化:

```bash
mysql -u root -p course_selection_db
```

```sql
-- 查看选课记录
SELECT * FROM course_selection;

-- 查看课程选课人数
SELECT id, course_code, course_name, selected_count, capacity 
FROM course;

-- 查看学生已完成课程
SELECT * FROM completed_course WHERE student_id = 1;
```

## 性能测试

使用Apache Bench进行简单的性能测试:

```bash
# 安装ab
sudo apt install apache2-utils

# 测试登录接口(100个请求,10个并发)
ab -n 100 -c 10 -p login.json -T application/json \
  http://localhost:8080/api/auth/student/login

# login.json内容:
# {"studentNumber":"2021001","password":"student123"}
```

## 日志查看

实时查看应用日志:

```bash
tail -f logs/course-selection.log
```

查找错误日志:

```bash
grep ERROR logs/course-selection.log
```

## 清理测试数据

重新初始化数据库:

```bash
mysql -u root -p course_selection_db < database/init_database.sql
```

## 常见问题

### Q1: 403 Forbidden错误

**原因:** 权限不足,学生尝试访问管理员接口  
**解决:** 使用正确角色的token

### Q2: 课程ID不存在

**原因:** 数据库中没有该课程  
**解决:** 使用 `SELECT * FROM course;` 查看可用课程

### Q3: 连接超时

**原因:** 应用未启动或端口不正确  
**解决:** 检查应用是否运行: `ps -ef | grep course-selection-system`

## 测试检查清单

- [ ] 学生登录成功
- [ ] 管理员登录成功
- [ ] 选课成功
- [ ] 时间冲突检测有效
- [ ] 前置课程检测有效
- [ ] 重复选课检测有效
- [ ] 退课成功
- [ ] 查询已选课程成功
- [ ] Token认证有效
- [ ] 参数验证有效
- [ ] Swagger文档可访问

## 下一步

完成基础测试后,可以:
1. 测试课程管理功能(待实现)
2. 测试学生管理功能(待实现)
3. 压力测试
4. 部署到测试环境
5. 编写自动化测试脚本
