package middleware

import (
	"net/http"

	"ao_test/server/models"

	"github.com/gin-gonic/gin"
)

// ErrorHandlerMiddleware 错误处理中间件
func ErrorHandlerMiddleware() gin.HandlerFunc {
	return func(c *gin.Context) {
		defer func() {
			if err := recover(); err != nil {
				requestID := GetRequestID(c)
				
				// 记录错误堆栈
				logger.WithField("requestId", requestID).Errorf("Panic recovered: %v", err)

				// 返回统一错误响应
				c.JSON(http.StatusInternalServerError, models.ErrorResponse{
					Error:     "InternalServerError",
					Message:   "An internal server error occurred",
					RequestID: requestID,
				})

				c.Abort()
			}
		}()

		c.Next()
	}
}
