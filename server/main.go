package main

import (
	"fmt"
	"log"

	"ao_test/server/config"
	"ao_test/server/handlers"
	"ao_test/server/middleware"
	"ao_test/server/service"

	"github.com/gin-gonic/gin"
)

func main() {
	// 加载配置
	cfg := config.LoadConfig()

	// 配置日志
	middleware.SetLogLevel(cfg.Log.Level)
	middleware.SetLogFormat(cfg.Log.Format)

	// 创建消息服务
	messageService := service.NewMessageService(cfg.Message.DefaultLang)

	// 创建处理器
	helloHandler := handlers.NewHelloHandler(messageService, cfg.Version)

	// 创建 Gin 引擎
	router := setupRouter(cfg, helloHandler)

	// 启动服务器
	addr := fmt.Sprintf("%s:%d", cfg.Server.Host, cfg.Server.Port)
	log.Printf("Starting server on %s", addr)
	
	if err := router.Run(addr); err != nil {
		log.Fatalf("Failed to start server: %v", err)
	}
}

// setupRouter 配置路由
func setupRouter(cfg *config.Config, helloHandler *handlers.HelloHandler) *gin.Engine {
	// 设置 Gin 模式
	gin.SetMode(gin.ReleaseMode)

	router := gin.New()

	// 注册中间件
	router.Use(middleware.ErrorHandlerMiddleware())
	router.Use(middleware.RequestIDMiddleware())
	router.Use(middleware.LoggerMiddleware())
	router.Use(middleware.CORSMiddleware(cfg.CORS.Enabled, cfg.CORS.Origins))

	// 注册路由
	api := router.Group("/api")
	{
		// 健康检查
		api.GET("/health", helloHandler.GetHealth)

		// 基本问候接口
		api.GET("/hello", helloHandler.GetHello)

		// 个性化问候接口
		api.GET("/hello/:name", helloHandler.GetHelloWithName)
	}

	return router
}
