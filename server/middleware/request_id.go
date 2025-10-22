package middleware

import (
	"math/rand"
	"time"

	"github.com/gin-gonic/gin"
)

const (
	RequestIDHeader = "X-Request-ID"
	RequestIDKey    = "requestId"
)

// RequestIDMiddleware 为每个请求生成唯一标识
func RequestIDMiddleware() gin.HandlerFunc {
	return func(c *gin.Context) {
		// 尝试从请求头获取 requestId
		requestID := c.GetHeader(RequestIDHeader)
		
		// 如果没有,则生成新的 requestId
		if requestID == "" {
			requestID = generateRequestID()
		}

		// 将 requestId 保存到上下文
		c.Set(RequestIDKey, requestID)

		// 在响应头中返回 requestId
		c.Header(RequestIDHeader, requestID)

		c.Next()
	}
}

// generateRequestID 生成请求唯一标识
func generateRequestID() string {
	const charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
	const length = 16
	
	rand.Seed(time.Now().UnixNano())
	b := make([]byte, length)
	for i := range b {
		b[i] = charset[rand.Intn(len(charset))]
	}
	return "req_" + string(b)
}

// GetRequestID 从上下文获取 requestId
func GetRequestID(c *gin.Context) string {
	if requestID, exists := c.Get(RequestIDKey); exists {
		return requestID.(string)
	}
	return ""
}
