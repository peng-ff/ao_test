package middleware

import (
	"time"

	"github.com/gin-gonic/gin"
	"github.com/sirupsen/logrus"
)

var logger = logrus.New()

// LoggerMiddleware 日志中间件
func LoggerMiddleware() gin.HandlerFunc {
	return func(c *gin.Context) {
		// 记录开始时间
		startTime := time.Now()

		// 处理请求
		c.Next()

		// 计算响应时间
		responseTime := time.Since(startTime).Milliseconds()

		// 获取请求信息
		requestID := GetRequestID(c)
		method := c.Request.Method
		path := c.Request.URL.Path
		clientIP := c.ClientIP()
		statusCode := c.Writer.Status()

		// 输出结构化日志
		logger.WithFields(logrus.Fields{
			"requestId":    requestID,
			"method":       method,
			"path":         path,
			"clientIp":     clientIP,
			"statusCode":   statusCode,
			"responseTime": responseTime,
			"timestamp":    startTime.Format(time.RFC3339),
		}).Info("Request processed")
	}
}

// SetLogLevel 设置日志级别
func SetLogLevel(level string) {
	switch level {
	case "debug":
		logger.SetLevel(logrus.DebugLevel)
	case "info":
		logger.SetLevel(logrus.InfoLevel)
	case "warn":
		logger.SetLevel(logrus.WarnLevel)
	case "error":
		logger.SetLevel(logrus.ErrorLevel)
	default:
		logger.SetLevel(logrus.InfoLevel)
	}
}

// SetLogFormat 设置日志格式
func SetLogFormat(format string) {
	if format == "json" {
		logger.SetFormatter(&logrus.JSONFormatter{})
	} else {
		logger.SetFormatter(&logrus.TextFormatter{
			FullTimestamp: true,
		})
	}
}
