# 公交车线上售票系统

## 项目简介

这是一个基于Spring Boot开发的公交车线上售票系统,为乘客提供便捷的公交车票查询、购买和使用服务。

### 核心功能

- **乘客端功能**
  - 线路查询与票价计算
  - 在线购票与支付
  - 电子票券管理与使用
  - 订单查询与退票

- **验票端功能**
  - 电子票据扫码验证
  - 离线验票支持
  - 验票记录管理

- **管理后台功能**
  - 线路与站点管理
  - 票务类型与票价规则配置
  - 车辆信息管理
  - 销售数据统计与分析

## 技术栈

- **后端框架**: Spring Boot 2.7.14
- **数据库**: MySQL 8.0
- **缓存**: Redis
- **ORM**: Spring Data JPA
- **安全认证**: JWT
- **二维码生成**: ZXing
- **构建工具**: Maven

## 项目结构

```
bus-ticketing-system/
├── database/                          # 数据库脚本
│   └── init_database.sql             # 数据库初始化脚本
├── src/
│   ├── main/
│   │   ├── java/com/bus/ticketing/
│   │   │   ├── entity/               # 实体类
│   │   │   │   ├── enums/           # 枚举类
│   │   │   │   ├── User.java
│   │   │   │   ├── Route.java
│   │   │   │   ├── Order.java
│   │   │   │   ├── Ticket.java
│   │   │   │   └── ...
│   │   │   ├── repository/          # 数据访问层
│   │   │   ├── service/             # 业务逻辑层
│   │   │   ├── controller/          # 控制器层
│   │   │   ├── dto/                 # 数据传输对象
│   │   │   │   ├── request/
│   │   │   │   └── response/
│   │   │   ├── exception/           # 异常处理
│   │   │   ├── config/              # 配置类
│   │   │   ├── util/                # 工具类
│   │   │   └── BusTicketingApplication.java
│   │   └── resources/
│   │       └── application.yml      # 应用配置
│   └── test/                        # 测试代码
├── pom.xml                          # Maven配置
└── README.md                        # 项目说明
```

## 快速开始

### 环境要求

- JDK 11+
- MySQL 8.0+
- Redis 5.0+
- Maven 3.6+

### 安装步骤

1. **克隆项目**
   ```bash
   git clone <repository-url>
   cd bus-ticketing-system
   ```

2. **创建数据库**
   ```bash
   mysql -u root -p < database/init_database.sql
   ```

3. **配置数据库连接**
   
   编辑 `src/main/resources/application.yml` 文件,修改数据库连接信息:
   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/bus_ticketing_db
       username: your_username
       password: your_password
   ```

4. **启动Redis**
   ```bash
   redis-server
   ```

5. **构建项目**
   ```bash
   mvn clean install
   ```

6. **运行应用**
   ```bash
   mvn spring-boot:run
   ```
   
   或者使用启动脚本:
   ```bash
   chmod +x scripts/start.sh
   ./scripts/start.sh
   ```

7. **访问应用**
   
   应用启动后,可通过以下地址访问:
   - API基础地址: http://localhost:8088/api

## API文档

### 用户认证接口

#### 登录/注册
```
POST /api/auth/login
Content-Type: application/json

{
  "phone": "13800138000",
  "verificationCode": "123456"
}
```

### 线路查询接口

#### 获取所有线路
```
GET /api/routes
```

#### 获取线路详情
```
GET /api/routes/{routeId}
```

### 订单接口

#### 创建订单
```
POST /api/orders
Authorization: Bearer {token}
Content-Type: application/json

{
  "routeId": 1,
  "ticketTypeId": 1,
  "quantity": 1
}
```

#### 查询我的订单
```
GET /api/orders/my
Authorization: Bearer {token}
```

### 车票接口

#### 获取我的车票
```
GET /api/tickets/my
Authorization: Bearer {token}
```

#### 获取车票详情
```
GET /api/tickets/{ticketId}
Authorization: Bearer {token}
```

#### 申请退票
```
POST /api/tickets/{ticketId}/refund
Authorization: Bearer {token}

{
  "reason": "行程取消"
}
```

### 验票接口

#### 验证车票
```
POST /api/validation/verify
Authorization: Bearer {token}

{
  "ticketCode": "TICKET20250101000001",
  "routeId": 1
}
```

## 数据模型

### 核心实体

1. **User (用户)**
   - 用户基本信息
   - 身份类型(普通/学生/老年人)
   - 账户状态

2. **Route (线路)**
   - 线路基本信息
   - 运营时间
   - 线路状态

3. **Station (站点)**
   - 站点名称与位置
   - 地理坐标

4. **Order (订单)**
   - 订单信息
   - 支付状态
   - 支付方式

5. **Ticket (车票)**
   - 票据信息
   - 有效期
   - 使用状态
   - 二维码数据

6. **TicketType (票务类型)**
   - 单次票/日票/月票
   - 有效期类型
   - 适用范围

7. **PriceRule (票价规则)**
   - 票价配置
   - 用户身份折扣
   - 生效时间

## 配置说明

### 数据库配置
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/bus_ticketing_db
    username: root
    password: root
```

### JWT配置
```yaml
jwt:
  secret: your-secret-key
  expiration: 604800000  # 7天,单位毫秒
```

### Redis配置
```yaml
spring:
  redis:
    host: localhost
    port: 6379
    password:
```

## 测试

运行单元测试:
```bash
mvn test
```

## 部署

### 打包应用
```bash
mvn clean package -DskipTests
```

生成的JAR文件位于 `target/bus-ticketing-system-1.0.0.jar`

### 运行JAR
```bash
java -jar target/bus-ticketing-system-1.0.0.jar
```

### Docker部署(可选)
```bash
# 构建镜像
docker build -t bus-ticketing-system:1.0.0 .

# 运行容器
docker run -d -p 8088:8088 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/bus_ticketing_db \
  bus-ticketing-system:1.0.0
```

## 常见问题

### 1. 数据库连接失败
- 检查MySQL服务是否启动
- 确认数据库连接信息是否正确
- 检查数据库是否已创建

### 2. Redis连接失败
- 检查Redis服务是否启动
- 确认Redis配置信息是否正确

### 3. JWT Token无效
- 检查Token是否过期
- 确认JWT密钥配置是否正确

## 贡献指南

欢迎提交Issue和Pull Request来改进这个项目。

## 许可证

MIT License

## 联系方式

如有问题,请提交Issue或联系项目维护者。
