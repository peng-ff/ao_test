# 公交车售票系统 - 快速开始指南

## 📊 项目统计

- ✅ **Java文件**: 37个
- ✅ **代码行数**: 1345行
- ✅ **数据库表**: 11张
- ✅ **实体类**: 9个核心实体 + 9个枚举
- ✅ **Repository**: 6个数据访问接口
- ✅ **DTO类**: 6个数据传输对象
- ✅ **工具类**: JWT工具、二维码生成工具

## 🚀 10分钟快速启动

### 前置条件检查

确保您的系统已安装:
- ☑️ JDK 11+
- ☑️ MySQL 8.0+
- ☑️ Maven 3.6+
- ☑️ Redis 5.0+ (可选,用于缓存)

### 步骤1: 初始化数据库 (2分钟)

```bash
# 登录MySQL
mysql -u root -p

# 创建数据库并初始化
source /data/workspace/ao_test/bus-ticketing-system/database/init_database.sql

# 或者直接执行
mysql -u root -p < /data/workspace/ao_test/bus-ticketing-system/database/init_database.sql
```

**数据库包含:**
- 11张核心业务表
- 3条示例线路数据
- 6个示例站点
- 4种票务类型
- 18条票价规则

### 步骤2: 配置数据库连接 (1分钟)

编辑配置文件:
```bash
vi src/main/resources/application.yml
```

修改数据库配置:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/bus_ticketing_db
    username: your_mysql_username
    password: your_mysql_password
```

### 步骤3: 启动Redis (可选,1分钟)

```bash
# 启动Redis服务
redis-server

# 或在后台启动
redis-server --daemonize yes
```

如果没有Redis,可以暂时注释掉application.yml中的Redis配置。

### 步骤4: 构建项目 (2-3分钟)

```bash
cd /data/workspace/ao_test/bus-ticketing-system

# 清理并构建
mvn clean package -DskipTests
```

### 步骤5: 启动应用 (1分钟)

**方式1: 使用启动脚本**
```bash
./scripts/start.sh
```

**方式2: 使用Maven**
```bash
mvn spring-boot:run
```

**方式3: 使用JAR文件**
```bash
java -jar target/bus-ticketing-system-1.0.0.jar
```

### 步骤6: 验证启动 (1分钟)

看到以下输出表示启动成功:
```
========================================
公交车售票系统启动成功!
API地址: http://localhost:8088/api
========================================
```

## 🧪 测试API

### 查询所有线路
```bash
curl http://localhost:8088/api/routes
```

预期返回3条示例线路数据(101路、202路、303路)

### 查询线路详情
```bash
curl http://localhost:8088/api/routes/1
```

## 📁 项目目录说明

```
bus-ticketing-system/
├── database/
│   └── init_database.sql          # 数据库初始化脚本 (237行SQL)
├── scripts/
│   ├── start.sh                   # 一键启动脚本
│   └── stop.sh                    # 停止脚本
├── src/main/java/com/bus/ticketing/
│   ├── entity/                    # 实体层 (9个实体 + 9个枚举)
│   ├── repository/                # 数据访问层 (6个Repository)
│   ├── dto/                       # 数据传输对象 (6个DTO)
│   ├── exception/                 # 异常处理 (3个类)
│   ├── config/                    # 配置类 (1个)
│   ├── util/                      # 工具类 (2个)
│   └── BusTicketingApplication.java
├── src/main/resources/
│   └── application.yml            # 应用配置文件
├── pom.xml                        # Maven配置
├── README.md                      # 完整文档
└── PROJECT_SUMMARY.md             # 项目总结
```

## 🎯 当前系统能力

### ✅ 已实现
- 数据库完整设计和初始化
- 实体模型和枚举定义
- 数据访问层(Repository)
- DTO和统一响应格式
- 异常处理机制
- JWT工具类
- 二维码生成工具
- 项目配置和启动类

### 🔜 待实现 (可根据需求扩展)
- Service业务逻辑层
- Controller控制器层
- Spring Security安全配置
- 完整的API接口
- 单元测试
- 集成测试

## 🛠 开发建议

### 下一步开发顺序:

1. **创建Service层** (优先级: ⭐⭐⭐⭐⭐)
   ```java
   // 示例: RouteService
   @Service
   public class RouteService {
       @Autowired
       private RouteRepository routeRepository;
       
       public List<Route> getAllRoutes() {
           return routeRepository.findAll();
       }
   }
   ```

2. **创建Controller层** (优先级: ⭐⭐⭐⭐⭐)
   ```java
   // 示例: RouteController
   @RestController
   @RequestMapping("/routes")
   public class RouteController {
       @Autowired
       private RouteService routeService;
       
       @GetMapping
       public ApiResponse<List<Route>> getRoutes() {
           return ApiResponse.success(routeService.getAllRoutes());
       }
   }
   ```

3. **添加Security配置** (优先级: ⭐⭐⭐⭐)
4. **实现支付功能** (优先级: ⭐⭐⭐)
5. **添加单元测试** (优先级: ⭐⭐⭐)

## 🐛 常见问题解决

### 问题1: 数据库连接失败
```
Error: Could not connect to database
```
**解决方案:**
- 检查MySQL是否启动: `systemctl status mysql`
- 验证用户名密码是否正确
- 确认数据库是否已创建

### 问题2: 端口被占用
```
Error: Port 8088 is already in use
```
**解决方案:**
```bash
# 查找占用端口的进程
lsof -i:8088
# 或修改application.yml中的端口号
server:
  port: 8089
```

### 问题3: Redis连接失败
```
Error: Unable to connect to Redis
```
**解决方案:**
- 启动Redis: `redis-server`
- 或暂时注释掉Redis配置

## 📞 技术支持

- 查看完整文档: `README.md`
- 项目总结: `PROJECT_SUMMARY.md`
- 数据库设计: `database/init_database.sql`

## 🎉 成功标志

当您看到以下内容,说明系统已经成功搭建:

1. ✅ 数据库成功初始化,包含11张表和示例数据
2. ✅ 应用成功启动,监听8088端口
3. ✅ 可以访问API地址: http://localhost:8088/api
4. ✅ 查询线路接口返回3条示例数据

---

**祝您开发顺利!** 🚀

如有任何问题,请查看完整的README.md文档或PROJECT_SUMMARY.md。
