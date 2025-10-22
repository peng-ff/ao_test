package middleware

import (
	"github.com/gin-contrib/cors"
	"github.com/gin-gonic/gin"
)

// CORSMiddleware CORS 中间件配置
func CORSMiddleware(enabled bool, allowedOrigins []string) gin.HandlerFunc {
	if !enabled {
		return func(c *gin.Context) {
			c.Next()
		}
	}

	config := cors.Config{
		AllowOrigins:     allowedOrigins,
		AllowMethods:     []string{"GET", "POST", "PUT", "DELETE", "OPTIONS"},
		AllowHeaders:     []string{"Origin", "Content-Type", "Accept", "X-Request-ID"},
		ExposeHeaders:    []string{"X-Request-ID"},
		AllowCredentials: true,
	}

	return cors.New(config)
}
