package handlers

import (
	"encoding/json"
	"net/http"
	"net/http/httptest"
	"testing"

	"ao_test/server/middleware"
	"ao_test/server/models"
	"ao_test/server/service"

	"github.com/gin-gonic/gin"
	"github.com/stretchr/testify/assert"
)

func TestGetStats(t *testing.T) {
	gin.SetMode(gin.TestMode)
	
	router := gin.New()
	router.Use(middleware.RequestIDMiddleware())
	
	messageService := service.NewMessageService("en")
	handler := NewHelloHandler(messageService, "1.0.0-test")
	
	router.GET("/api/stats", handler.GetStats)
	router.GET("/api/hello", handler.GetHello)

	tests := []struct {
		name        string
		description string
	}{
		{
			name:        "TC301 - 初始统计信息",
			description: "检查初始状态下的统计信息",
		},
		{
			name:        "TC302 - 请求后统计信息",
			description: "发送请求后检查统计计数",
		},
	}

	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			// 先发送几个hello请求来增加计数
			if tt.name == "TC302 - 请求后统计信息" {
				for i := 0; i < 5; i++ {
					w := httptest.NewRecorder()
					req, _ := http.NewRequest("GET", "/api/hello", nil)
					router.ServeHTTP(w, req)
				}
			}

			// 获取统计信息
			w := httptest.NewRecorder()
			req, _ := http.NewRequest("GET", "/api/stats", nil)
			router.ServeHTTP(w, req)

			assert.Equal(t, http.StatusOK, w.Code, tt.description)

			var response models.StatsResponse
			err := json.Unmarshal(w.Body.Bytes(), &response)
			assert.NoError(t, err)
			
			// 验证字段
			assert.GreaterOrEqual(t, response.TotalRequests, int64(0))
			assert.GreaterOrEqual(t, response.ActiveRequests, 0)
			assert.GreaterOrEqual(t, response.Uptime, int64(0))
			assert.NotEmpty(t, response.StartTime)
			assert.Equal(t, "1.0.0-test", response.Version)
			assert.NotEmpty(t, response.RequestID)
		})
	}
}

func TestGetStatsFields(t *testing.T) {
	gin.SetMode(gin.TestMode)
	
	router := gin.New()
	router.Use(middleware.RequestIDMiddleware())
	
	messageService := service.NewMessageService("en")
	handler := NewHelloHandler(messageService, "1.0.0-test")
	
	router.GET("/api/stats", handler.GetStats)

	w := httptest.NewRecorder()
	req, _ := http.NewRequest("GET", "/api/stats", nil)
	router.ServeHTTP(w, req)

	assert.Equal(t, http.StatusOK, w.Code)

	var response models.StatsResponse
	err := json.Unmarshal(w.Body.Bytes(), &response)
	assert.NoError(t, err)

	// 验证所有字段都存在
	assert.NotNil(t, response.TotalRequests)
	assert.NotNil(t, response.ActiveRequests)
	assert.NotNil(t, response.Uptime)
	assert.NotEmpty(t, response.StartTime)
	assert.NotEmpty(t, response.Version)
	assert.NotEmpty(t, response.RequestID)
}
