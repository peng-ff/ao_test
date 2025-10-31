================================================================================
                欧洲七大卡车管理系统 (European Truck Management System)
================================================================================

项目概述：
---------
这是一个基于Java 17开发的欧洲七大卡车品牌管理系统，采用MVC分层架构设计。
支持对Mercedes-Benz、Volvo、Scania、MAN、DAF、Iveco、Renault七大品牌卡车的完整管理。

技术栈：
-------
- Java 17
- JUnit 5.10.1 (单元测试)
- Mockito 5.7.0 (Mock框架)
- Maven 3.x (构建工具)

项目结构：
---------
src/main/java/com/truck/
├── domain/                    # 领域层（实体类）
│   ├── Truck.java            # 卡车实体
│   ├── TruckBrand.java       # 品牌枚举
│   └── TruckStatus.java      # 状态枚举
├── repository/                # 数据访问层
│   ├── TruckRepository.java  # 仓储接口
│   └── impl/
│       └── InMemoryTruckRepository.java  # 内存实现
├── service/                   # 业务逻辑层
│   ├── TruckService.java     # 服务接口
│   └── impl/
│       └── TruckServiceImpl.java  # 服务实现
├── controller/                # 控制层
│   └── TruckController.java  # 控制器（CLI交互）
└── TruckManagementApp.java   # 主程序入口

src/test/java/com/truck/
├── repository/impl/
│   └── InMemoryTruckRepositoryTest.java  # Repository层测试
└── service/impl/
    └── TruckServiceImplTest.java         # Service层测试

核心功能：
---------
1. 卡车信息管理（CRUD操作）
2. 按品牌查询卡车
3. 按状态查询卡车
4. 按价格区间查询卡车
5. 品牌统计
6. 状态统计
7. VIN码唯一性验证

编译与测试：
-----------
# 编译项目
mvn clean compile

# 运行测试
mvn test

# 打包项目
mvn clean package

# 运行程序
java -jar target/european-truck-management-1.0.0.jar

测试覆盖：
---------
- Repository层测试：19个测试用例
- Service层测试：30个测试用例
- 总计：49个测试用例，全部通过

设计特点：
---------
1. 分层架构：清晰的MVC分层，便于维护和扩展
2. 接口编程：Repository和Service都定义了接口，便于替换实现
3. 线程安全：使用ConcurrentHashMap保证并发安全
4. 扩展性强：Repository实现可轻松替换为数据库持久化
5. 完整注释：所有类和方法都有详细的JavaDoc注释
6. 单元测试：完整的单元测试覆盖，使用Mockito进行依赖隔离

代码规范：
---------
- 遵循Java命名规范
- 方法内使用 // 注释
- 类和方法使用 /** */ JavaDoc注释
- 实体类只在类级别添加注释

================================================================================
