# 远程 Hello World 程序 - 架构文档

## 系统架构图

### 整体架构

```
┌─────────────────────────────────────────────────────────┐
│                    客户端 (Client)                        │
│  ┌──────────────────────────────────────────────────┐   │
│  │  命令行界面 (CLI)                                  │   │
│  │  - 参数解析                                        │   │
│  │  - 用户交互                                        │   │
│  └──────────────┬───────────────────────────────────┘   │
│                 │                                        │
│  ┌──────────────▼───────────────────────────────────┐   │
│  │  HTTP 客户端                                       │   │
│  │  - 请求构建                                        │   │
│  │  - 重试机制                                        │   │
│  │  - 超时控制                                        │   │
│  └──────────────┬───────────────────────────────────┘   │
└─────────────────┼───────────────────────────────────────┘
                  │
                  │ HTTP/JSON
                  │
┌─────────────────▼───────────────────────────────────────┐
│                    服务端 (Server)                        │
│                                                           │
│  ┌───────────────────────────────────────────────────┐  │
│  │             中间件层 (Middleware)                   │  │
│  │  ┌─────────┐ ┌─────────┐ ┌──────┐ ┌───────────┐  │  │
│  │  │错误处理 │→│请求ID  │→│日志  │→│  CORS    │  │  │
│  │  └─────────┘ └─────────┘ └──────┘ └───────────┘  │  │
│  └───────────────────┬───────────────────────────────┘  │
│                      │                                   │
│  ┌───────────────────▼───────────────────────────────┐  │
│  │              API 层 (Handlers)                      │  │
│  │  - GET /api/hello                                  │  │
│  │  - GET /api/hello/:name                            │  │
│  │  - GET /api/health                                 │  │
│  └───────────────────┬───────────────────────────────┘  │
│                      │                                   │
│  ┌───────────────────▼───────────────────────────────┐  │
│  │            业务服务层 (Service)                     │  │
│  │  - 消息生成服务                                     │  │
│  │  - 参数验证                                         │  │
│  │  - 语言处理                                         │  │
│  └───────────────────┬───────────────────────────────┘  │
│                      │                                   │
│  ┌───────────────────▼───────────────────────────────┐  │
│  │            数据模型层 (Models)                      │  │
│  │  - 请求模型                                         │  │
│  │  - 响应模型                                         │  │
│  │  - 错误模型                                         │  │
│  └───────────────────────────────────────────────────┘  │
│                                                           │
│  ┌───────────────────────────────────────────────────┐  │
│  │            配置管理层 (Config)                      │  │
│  │  - 环境变量加载                                     │  │
│  │  - 默认值设置                                       │  │
│  └───────────────────────────────────────────────────┘  │
└───────────────────────────────────────────────────────────┘
```

## 请求处理流程

### 客户端请求流程

```
用户输入命令
    ↓
解析命令行参数
    ↓
创建 HTTP 请求
    ↓
发送请求到服务端
    ↓
┌───接收响应?
│   ├─ 成功 → 解析响应 → 格式化输出 → 结束
│   └─ 失败 ┐
│          ↓
└─── 需要重试?
     ├─ 是 → 等待延迟 → 重新发送请求
     └─ 否 → 显示错误信息 → 结束
```

### 服务端请求处理流程

```
接收 HTTP 请求
    ↓
[错误处理中间件] 捕获异常
    ↓
[请求ID中间件] 生成 requestId
    ↓
[日志中间件] 记录请求开始
    ↓
[CORS中间件] 处理跨域
    ↓
路由匹配
    ↓
API 处理器处理
    ↓
┌─ 参数验证
│   ├─ 有效 → 继续
│   └─ 无效 → 返回 400 错误
    ↓
调用业务服务
    ↓
生成响应数据
    ↓
返回 JSON 响应
    ↓
[日志中间件] 记录响应时间
    ↓
发送响应给客户端
```

## 模块依赖关系

```
main.go
  │
  ├─→ config
  │     └─ 加载配置
  │
  ├─→ middleware
  │     ├─ ErrorHandler
  │     ├─ RequestID
  │     ├─ Logger
  │     └─ CORS
  │
  ├─→ handlers
  │     ├─ HelloHandler
  │     │   ├─→ service (MessageService)
  │     │   ├─→ models
  │     │   └─→ middleware (GetRequestID)
  │     └─ 注册路由
  │
  └─→ 启动服务器
```

## 数据流转

### 问候请求数据流

```
1. 客户端构建请求
   HelloRequest { name: "张三", lang: "zh" }
   
2. HTTP 传输 (JSON)
   GET /api/hello?name=张三&lang=zh
   
3. 服务端解析参数
   req := HelloRequest{Name: "张三", Lang: "zh"}
   
4. 消息服务处理
   - 验证参数
   - 选择语言模板: "你好，%s！"
   - 生成消息: "你好，张三！"
   - 生成时间戳
   - 获取 requestId
   
5. 构建响应
   HelloResponse {
     Message: "你好，张三！",
     Timestamp: "2025-10-22T12:10:36Z",
     RequestID: "req_abc123"
   }
   
6. JSON 序列化
   {"message":"你好，张三！","timestamp":"...","requestId":"..."}
   
7. HTTP 响应
   Status: 200 OK
   Content-Type: application/json
   Body: {...}
   
8. 客户端解析显示
   消息: 你好，张三！
   时间戳: 2025-10-22T12:10:36Z
   请求ID: req_abc123
```

## 核心组件说明

### 1. 配置管理 (Config)

**职责**:
- 从环境变量加载配置
- 提供默认值
- 验证配置有效性

**配置项**:
```go
type Config struct {
    Server  ServerConfig  // 服务器配置
    Log     LogConfig     // 日志配置
    CORS    CORSConfig    // CORS配置
    Message MessageConfig // 消息配置
    Version string        // 版本号
}
```

### 2. 数据模型 (Models)

**请求模型**:
```go
type HelloRequest struct {
    Name string  // 接收问候的对象
    Lang string  // 语言代码
}
```

**响应模型**:
```go
type HelloResponse struct {
    Message   string  // 问候消息
    Timestamp string  // 时间戳
    RequestID string  // 请求ID
}

type ErrorResponse struct {
    Error     string  // 错误类型
    Message   string  // 错误描述
    RequestID string  // 请求ID
}

type HealthResponse struct {
    Status  string  // 服务状态
    Uptime  int64   // 运行时长
    Version string  // 版本号
}
```

### 3. 消息服务 (MessageService)

**核心方法**:
```go
// 生成问候消息
GenerateGreeting(req HelloRequest, requestID string) HelloResponse

// 验证名称
ValidateName(name string) error

// 验证语言
ValidateLang(lang string) bool

// 获取支持的语言
GetSupportedLanguages() []string
```

**语言模板**:
```go
templates := map[string]string{
    "en": "Hello %s!",
    "zh": "你好，%s！",
    "es": "¡Hola %s!",
    "fr": "Bonjour %s!",
}
```

### 4. 中间件层

#### 4.1 请求ID中间件
- 生成唯一请求标识
- 格式: `req_` + 16位随机字符
- 保存到上下文
- 添加到响应头

#### 4.2 日志中间件
- 记录请求开始时间
- 记录请求方法、路径、IP
- 计算响应时间
- 输出结构化日志

#### 4.3 CORS中间件
- 处理跨域请求
- 设置允许的源、方法、头部
- 处理预检请求

#### 4.4 错误处理中间件
- 捕获所有未处理异常
- 转换为统一错误响应
- 记录错误日志

### 5. API 处理器 (Handlers)

**路由映射**:
```
GET  /api/hello         → GetHello()
GET  /api/hello/:name   → GetHelloWithName()
GET  /api/health        → GetHealth()
```

**处理流程**:
1. 绑定请求参数
2. 验证参数有效性
3. 调用业务服务
4. 构建响应
5. 返回 JSON

### 6. 客户端 (Client)

**核心组件**:
```go
type Client struct {
    config     ClientConfig  // 客户端配置
    httpClient *http.Client  // HTTP客户端
}
```

**主要功能**:
- 发送问候请求
- 健康检查
- 自动重试
- 超时控制
- 错误处理
- 结果格式化

## 扩展点设计

### 1. 添加新语言支持

在 `MessageService` 中添加新模板:
```go
templates := map[string]string{
    "en": "Hello %s!",
    "zh": "你好，%s！",
    "ja": "こんにちは、%s！",  // 新增日语
}
```

### 2. 添加新的中间件

在 `main.go` 中注册:
```go
router.Use(middleware.RateLimitMiddleware())  // 速率限制
router.Use(middleware.AuthMiddleware())       // 认证
```

### 3. 添加新的API接口

在 `handlers` 中添加处理器:
```go
func (h *HelloHandler) GetGoodbye(c *gin.Context) {
    // 实现告别接口
}
```

在 `main.go` 中注册路由:
```go
api.GET("/goodbye", helloHandler.GetGoodbye)
```

### 4. 添加数据持久化

创建新的服务层:
```go
type StorageService interface {
    SaveGreeting(greeting HelloResponse) error
    GetGreetingHistory() ([]HelloResponse, error)
}
```

## 性能考虑

### 1. 响应时间优化
- 消息模板缓存 ✅
- 避免不必要的序列化
- 使用高效的路由匹配

### 2. 并发处理
- Gin 框架内置并发支持
- 无状态设计，无锁竞争
- 可水平扩展

### 3. 内存优化
- 对象复用
- 避免内存泄漏
- 合理设置超时时间

### 4. 日志优化
- 异步日志写入 ✅
- 日志级别控制 ✅
- 结构化日志格式 ✅

## 安全措施

### 1. 输入验证
- 名称长度限制: 1-50字符
- 语言代码白名单验证
- 防止注入攻击

### 2. 错误处理
- 统一的错误响应格式
- 不暴露内部实现细节
- 记录详细的错误日志

### 3. 请求追踪
- 每个请求有唯一ID
- 完整的日志链路
- 便于问题定位

### 4. CORS控制
- 可配置允许的源
- 控制允许的方法和头部
- 预检请求处理

## 监控和观测

### 1. 日志系统
- 结构化 JSON 日志
- 记录所有请求/响应
- 包含关键指标

### 2. 健康检查
- `/api/health` 端点
- 返回服务状态
- 包含运行时长和版本

### 3. 请求追踪
- 唯一请求ID
- 完整的调用链路
- 响应时间统计

## 总结

本架构设计遵循以下原则:
- **分层架构**: 清晰的职责划分
- **模块化**: 高内聚低耦合
- **可扩展**: 易于添加新功能
- **可测试**: 便于单元测试
- **可维护**: 代码结构清晰
- **可观测**: 完善的日志和监控

通过这种架构设计，项目具备良好的可维护性和可扩展性，为未来的功能增强奠定了坚实的基础。
