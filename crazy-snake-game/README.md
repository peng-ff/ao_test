# 疯狂蟒蛇游戏

一个基于Web的创新贪吃蛇游戏，包含特殊道具系统、障碍物、难度递进等疯狂玩法。

## 项目特色

- 🎮 **创新玩法**: 7种特殊道具（加速、减速、双倍积分、缩短身体、穿墙、混乱控制、无敌）
- 🎯 **难度递进**: 6个阶段，速度和障碍物数量逐步增加
- 🏆 **排行榜系统**: 全球排行榜和每日排行榜
- 📊 **数据统计**: 记录玩家成绩、历史记录和个人统计
- 🔐 **用户系统**: JWT身份认证，安全可靠

## 技术栈

### 后端
- Spring Boot 2.7.18
- Spring Data JPA
- Spring Security
- MySQL 8.0+
- JWT (jjwt 0.11.5)
- Lombok

### 前端
- HTML5 + CSS3 + JavaScript
- Canvas API (游戏渲染)
- Fetch API (HTTP请求)

## 项目结构

```
crazy-snake-game/
├── backend/                    # 后端代码
│   ├── src/main/
│   │   ├── java/com/snake/game/
│   │   │   ├── entity/        # 实体类
│   │   │   ├── repository/    # 数据访问层
│   │   │   ├── service/       # 业务逻辑层
│   │   │   ├── controller/    # 控制器
│   │   │   ├── dto/           # 数据传输对象
│   │   │   ├── config/        # 配置类
│   │   │   └── util/          # 工具类
│   │   └── resources/
│   │       └── application.yml # 配置文件
│   └── pom.xml                 # Maven配置
├── frontend/                   # 前端代码
│   ├── index.html             # 主页面
│   ├── css/
│   │   └── style.css          # 样式文件
│   └── js/
│       ├── api.js             # API客户端
│       ├── game.js            # 游戏引擎
│       └── main.js            # 主控制逻辑
└── database/
    └── init_database.sql      # 数据库初始化脚本
```

## 快速开始

### 1. 环境要求

- JDK 11或更高版本
- MySQL 8.0或更高版本
- Maven 3.6+
- 现代浏览器（Chrome 90+, Firefox 88+, Safari 14+, Edge 90+）

### 2. 数据库配置

```bash
# 登录MySQL
mysql -u root -p

# 执行初始化脚本
source /path/to/crazy-snake-game/database/init_database.sql
```

或者手动创建数据库：
```sql
CREATE DATABASE crazy_snake_game CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 3. 配置后端

编辑 `backend/src/main/resources/application.yml`，修改数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/crazy_snake_game?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=UTC
    username: root
    password: your_password
```

### 4. 启动后端

```bash
cd crazy-snake-game/backend
mvn clean install
mvn spring-boot:run
```

后端将在 http://localhost:8080 启动

### 5. 启动前端

方式一：使用Python简单HTTP服务器（推荐）
```bash
cd crazy-snake-game/frontend
python3 -m http.server 3000
```

方式二：使用Node.js http-server
```bash
cd crazy-snake-game/frontend
npx http-server -p 3000
```

方式三：直接用浏览器打开
```bash
# 直接用浏览器打开 index.html 文件
```

前端访问地址: http://localhost:3000

## 游戏说明

### 基本操作

- **方向键**: ⬆️⬇️⬅️➡️ 控制蛇的移动方向
- **空格键**: 暂停/继续游戏
- **鼠标**: 点击按钮进行各种操作

### 游戏目标

1. 控制蛇吃食物增加长度和分数
2. 避免撞墙、撞自己、撞障碍物
3. 收集特殊道具获得增益效果
4. 在难度逐渐提升的情况下尽可能获得高分

### 道具说明

| 道具 | 图标 | 效果 | 持续时间 |
|------|------|------|---------|
| 加速道具 | ⚡ | 移动速度提升50% | 5秒 |
| 减速道具 | ❄️ | 移动速度降低30% | 5秒 |
| 双倍积分 | ⭐ | 获得分数翻倍 | 10秒 |
| 缩短身体 | ✂️ | 蛇身长度减少3节 | 立即生效 |
| 穿墙模式 | 🛡️ | 可穿过边界和障碍物 | 8秒 |
| 混乱控制 | 🌀 | 方向键控制反转 | 7秒 |
| 无敌状态 | 🌈 | 免疫所有碰撞伤害 | 6秒 |

### 难度阶段

游戏共6个阶段，每个阶段持续30秒：

| 阶段 | 速度 | 障碍物数量 | 分数倍率 |
|------|------|-----------|---------|
| 1 | 慢 | 3 | 1.0x |
| 2 | 较慢 | 5 | 1.2x |
| 3 | 中等 | 7 | 1.5x |
| 4 | 较快 | 10 | 2.0x |
| 5 | 快 | 13 | 2.5x |
| 6+ | 很快 | 15+ | 2.5x |

## API文档

### 认证接口

- `POST /api/auth/register` - 用户注册
- `POST /api/auth/login` - 用户登录

### 游戏接口

- `POST /api/game/record` - 保存游戏记录 (需认证)
- `GET /api/game/records` - 获取个人游戏记录 (需认证)
- `GET /api/game/items` - 获取道具配置列表

### 排行榜接口

- `GET /api/ranking/global` - 获取全局排行榜
- `GET /api/ranking/daily` - 获取每日排行榜

### 用户接口

- `GET /api/user/profile` - 获取用户信息 (需认证)
- `GET /api/user/stats` - 获取用户统计数据 (需认证)

## 常见问题

### 1. 数据库连接失败

确保MySQL服务已启动，数据库已创建，用户名密码正确。

### 2. 跨域问题

后端已配置CORS允许所有源访问，如仍有问题，检查浏览器控制台错误信息。

### 3. JWT认证失败

检查Token是否过期（默认24小时），清除浏览器LocalStorage重新登录。

### 4. 游戏卡顿

降低浏览器缩放比例，关闭其他占用资源的标签页，检查电脑性能。

## 开发说明

### 后端开发

```bash
# 编译
mvn clean compile

# 运行测试
mvn test

# 打包
mvn clean package

# 跳过测试打包
mvn clean package -DskipTests
```

### 前端开发

前端使用原生JavaScript开发，无需构建步骤。直接修改HTML/CSS/JS文件，刷新浏览器即可看到效果。

## 许可证

MIT License

## 作者

疯狂蟒蛇游戏开发团队

## 更新日志

### v1.0.0 (2026-01-04)
- ✨ 初始版本发布
- 🎮 实现基础游戏玩法
- ⚡ 7种特殊道具系统
- 🏆 全球和每日排行榜
- 🔐 用户认证系统
- 📊 游戏数据统计
