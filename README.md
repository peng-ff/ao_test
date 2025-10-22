# 远程 Hello World 程序

一个基于客户端-服务器架构的远程 Hello World 程序，展示分布式系统中最基本的远程通信模式。

## 项目结构

```
ao_test/
├── server/                 # 服务端
│   ├── main.go            # 服务端主程序
│   ├── config/            # 配置管理
│   │   └── config.go
│   ├── models/            # 数据模型
│   │   └── models.go
│   ├── service/           # 业务服务
│   │   ├── message_service.go
│   │   └── message_service_test.go
│   ├── middleware/        # 中间件
│   │   ├── request_id.go
│   │   ├── logger.go
│   │   ├── cors.go
│   │   └── error_handler.go
│   └── handlers/          # API 处理器
│       ├── hello_handler.go
│       └── hello_handler_test.go
├── client/                # 客户端
│   └── main.go           # 客户端主程序
├── go.mod                # Go 模块配置
├── .env.example          # 环境变量示例
├── start_server.sh       # 服务端启动脚本
├── run_client.sh         # 客户端运行脚本
└── README.md             # 项目说明文档
```

## 功能特性

### 服务端功能
- ✅ RESTful API 接口
- ✅ 多语言支持 (中文、英文、西班牙语、法语)
- ✅ 请求唯一标识生成
- ✅ 结构化日志记录
- ✅ CORS 跨域支持
- ✅ 错误处理机制
- ✅ 健康检查接口
- ✅ 配置管理 (环境变量)

### 客户端功能
- ✅ 命令行界面
- ✅ 请求重试机制
- ✅ 超时控制
- ✅ 友好的错误提示
- ✅ 格式化输出结果

## API 接口

### 1. 基本问候接口

**请求:**
```bash
GET /api/hello?name=张三&lang=zh
```

**响应:**
```json
{
  "message": "你好，张三！",
  "timestamp": "2024-01-20T10:30:00Z",
  "requestId": "req_abc123xyz"
}
```

### 2. 个性化问候接口

**请求:**
```bash
GET /api/hello/World
```

**响应:**
```json
{
  "message": "Hello World!",
  "timestamp": "2024-01-20T10:30:00Z",
  "requestId": "req_def456uvw"
}
```

### 3. 健康检查接口

**请求:**
```bash
GET /api/health
```

**响应:**
```json
{
  "status": "healthy",
  "uptime": 3600,
  "version": "1.0.0"
}
```

## 快速开始

### 环境要求
- Go 1.21 或更高版本
- Linux/macOS/Windows 操作系统

### 安装依赖

```bash
# 下载依赖
go mod download
```

### 启动服务端

**方式一：使用启动脚本**
```bash
chmod +x start_server.sh
./start_server.sh
```

**方式二：直接运行**
```bash
cd server
go run main.go
```

服务端默认在 `http://localhost:8080` 启动

### 使用客户端

**方式一：使用运行脚本**
```bash
chmod +x run_client.sh

# 基本问候
./run_client.sh

# 指定名称和语言
./run_client.sh http://localhost:8080 --name=张三 --lang=zh

# 健康检查
./run_client.sh http://localhost:8080 --health

# 查看帮助
./run_client.sh --help
```

**方式二：直接运行**
```bash
cd client

# 基本问候
go run main.go

# 指定参数
go run main.go -name=张三 -lang=zh

# 健康检查
go run main.go -health

# 自定义服务器地址
go run main.go -server=http://localhost:8080 -name=World
```

## 配置说明

### 服务端配置

配置通过环境变量设置，支持的配置项：

| 配置项 | 默认值 | 说明 |
|--------|--------|------|
| SERVER_PORT | 8080 | 服务监听端口 |
| SERVER_HOST | 0.0.0.0 | 服务监听地址 |
| LOG_LEVEL | info | 日志级别 (debug/info/warn/error) |
| LOG_FORMAT | json | 日志格式 (json/text) |
| CORS_ENABLED | true | 是否启用 CORS |
| CORS_ORIGINS | * | 允许的源列表 (逗号分隔) |
| MESSAGE_DEFAULT_LANG | en | 默认语言 |
| APP_VERSION | 1.0.0 | 应用版本 |

**使用方式：**

1. 复制配置示例文件
```bash
cp .env.example .env
```

2. 修改 `.env` 文件中的配置项

3. 启动服务端（自动加载配置）

### 客户端配置

客户端配置通过命令行参数设置：

| 参数 | 默认值 | 说明 |
|------|--------|------|
| -server | http://localhost:8080 | 服务端地址 |
| -timeout | 5000 | 请求超时时间(毫秒) |
| -retry | true | 是否启用重试 |
| -max-retry | 3 | 最大重试次数 |
| -name | "" | 接收问候的对象名称 |
| -lang | "" | 语言代码 |
| -health | false | 检查服务健康状态 |
| -version | false | 显示客户端版本 |

## 支持的语言

| 语言代码 | 语言名称 | 示例 |
|----------|----------|------|
| en | 英语 | Hello World! |
| zh | 中文 | 你好，世界！ |
| es | 西班牙语 | ¡Hola World! |
| fr | 法语 | Bonjour World! |

## 测试

### 运行单元测试

```bash
# 测试所有模块
go test ./...

# 测试服务层
go test ./server/service -v

# 测试处理器层
go test ./server/handlers -v

# 查看测试覆盖率
go test ./... -cover
```

### 测试用例说明

#### 消息服务测试 (message_service_test.go)
- ✅ TC001: 无参数请求 - 返回默认问候
- ✅ TC002: 指定名称 - 返回个性化问候
- ✅ TC003: 指定中文语言 - 返回中文问候
- ✅ TC004: 同时指定名称和语言 - 返回中文个性化问候
- ✅ TC005: 不支持的语言 - 使用默认语言
- ✅ TC006: 西班牙语问候
- ✅ TC007: 法语问候
- ✅ 名称验证测试
- ✅ 语言验证测试

#### API 处理器测试 (hello_handler_test.go)
- ✅ TC001-TC004: GET /api/hello 接口测试
- ✅ TC101-TC104: GET /api/hello/:name 接口测试
- ✅ TC201-TC202: GET /api/health 接口测试

## 使用示例

### 示例 1: 基本问候

**客户端:**
```bash
./run_client.sh
```

**输出:**
```
连接到服务器: http://localhost:8080
发送问候请求...

========== 响应结果 ==========
消息: Hello World!
时间戳: 2024-01-20T10:30:00Z
请求ID: req_abc123xyz
==============================
```

### 示例 2: 中文个性化问候

**客户端:**
```bash
./run_client.sh http://localhost:8080 --name=张三 --lang=zh
```

**输出:**
```
连接到服务器: http://localhost:8080
发送问候请求...

========== 响应结果 ==========
消息: 你好，张三！
时间戳: 2024-01-20T10:31:00Z
请求ID: req_def456uvw
==============================
```

### 示例 3: 健康检查

**客户端:**
```bash
./run_client.sh http://localhost:8080 --health
```

**输出:**
```
连接到服务器: http://localhost:8080
执行健康检查...

========== 响应结果 ==========
状态: healthy
运行时长: 120 秒
版本: 1.0.0
==============================
```

### 示例 4: 使用 curl 测试

```bash
# 基本问候
curl "http://localhost:8080/api/hello"

# 指定参数
curl "http://localhost:8080/api/hello?name=张三&lang=zh"

# 个性化问候
curl "http://localhost:8080/api/hello/World"

# 健康检查
curl "http://localhost:8080/api/health"
```

## 架构说明

### 系统架构

```
┌─────────────┐         HTTP          ┌─────────────┐
│   客户端     │ ───────────────────> │   服务端     │
│  (Client)   │ <─────────────────── │  (Server)   │
└─────────────┘      JSON 响应        └─────────────┘
                                            │
                                            ├─ API 层
                                            ├─ 服务层
                                            ├─ 中间件层
                                            └─ 配置层
```

### 中间件执行链

```
请求 → 错误处理 → 请求ID → 日志 → CORS → 业务处理 → 响应
```

### 业务流程

```
客户端发起请求
    ↓
生成请求ID
    ↓
记录请求日志
    ↓
参数验证
    ↓
消息生成服务
    ↓
返回JSON响应
    ↓
记录响应日志
```

## 性能优化

- ✅ 缓存消息模板，减少查找时间
- ✅ 异步日志写入，避免阻塞
- ✅ 结构化日志，提高解析效率
- ✅ 无状态设计，支持水平扩展

## 安全考虑

- ✅ 输入参数验证 (长度限制)
- ✅ CORS 跨域控制
- ✅ 错误信息脱敏
- ✅ 请求唯一标识追踪

## 部署建议

### 单机部署
```bash
# 1. 编译服务端
cd server
go build -o hello-server main.go

# 2. 运行服务
./hello-server
```

### Docker 部署
可根据需要创建 Dockerfile：
```dockerfile
FROM golang:1.21-alpine
WORKDIR /app
COPY . .
RUN go mod download
RUN go build -o server ./server/main.go
EXPOSE 8080
CMD ["./server"]
```

### 负载均衡部署
- 使用 Nginx 或其他负载均衡器
- 启动多个服务实例
- 配置健康检查路径: `/api/health`

## 故障排查

### 常见问题

**1. 服务端启动失败**
- 检查端口是否被占用: `lsof -i :8080`
- 检查 Go 版本: `go version`
- 检查依赖是否安装: `go mod download`

**2. 客户端连接失败**
- 确认服务端已启动
- 检查服务器地址是否正确
- 检查防火墙设置

**3. 测试失败**
- 清理缓存: `go clean -testcache`
- 重新运行: `go test ./... -v`

## 扩展建议

### 功能扩展
- [ ] 添加更多语言支持
- [ ] 实现认证授权机制
- [ ] 添加速率限制
- [ ] 支持 WebSocket 连接
- [ ] 添加 Metrics 监控
- [ ] 实现配置热更新

### 性能优化
- [ ] 添加响应缓存
- [ ] 实现连接池
- [ ] 启用 HTTP/2
- [ ] 添加压缩中间件

## 许可证

本项目采用 MIT 许可证。

## 贡献指南

欢迎提交 Issue 和 Pull Request！

---

**版本:** 1.0.0  
**最后更新:** 2024-01-20
