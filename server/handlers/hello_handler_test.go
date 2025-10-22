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

func setupTestRouter() (*gin.Engine, *HelloHandler) {
	gin.SetMode(gin.TestMode)
	
	router := gin.New()
	router.Use(middleware.RequestIDMiddleware())
	
	messageService := service.NewMessageService("en")
	handler := NewHelloHandler(messageService, "1.0.0-test")
	
	return router, handler
}

func TestGetHello(t *testing.T) {
	router, handler := setupTestRouter()
	router.GET("/api/hello", handler.GetHello)

	tests := []struct {
		name           string
		queryParams    string
		expectedStatus int
		expectedMsg    string
		description    string
	}{
		{
			name:           "TC001 - 无参数请求",
			queryParams:    "",
			expectedStatus: http.StatusOK,
			expectedMsg:    "Hello World!",
			description:    "默认问候",
		},
		{
			name:           "TC002 - 指定名称",
			queryParams:    "?name=张三",
			expectedStatus: http.StatusOK,
			expectedMsg:    "Hello 张三!",
			description:    "指定名称",
		},
		{
			name:           "TC003 - 指定中文语言",
			queryParams:    "?lang=zh",
			expectedStatus: http.StatusOK,
			expectedMsg:    "你好，World！",
			description:    "中文语言",
		},
		{
			name:           "TC004 - 同时指定名称和语言",
			queryParams:    "?name=张三&lang=zh",
			expectedStatus: http.StatusOK,
			expectedMsg:    "你好，张三！",
			description:    "中文个性化问候",
		},
	}

	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			w := httptest.NewRecorder()
			req, _ := http.NewRequest("GET", "/api/hello"+tt.queryParams, nil)
			router.ServeHTTP(w, req)

			assert.Equal(t, tt.expectedStatus, w.Code, tt.description)

			var response models.HelloResponse
			err := json.Unmarshal(w.Body.Bytes(), &response)
			assert.NoError(t, err)
			assert.Equal(t, tt.expectedMsg, response.Message)
			assert.NotEmpty(t, response.RequestID)
			assert.NotEmpty(t, response.Timestamp)
		})
	}
}

func TestGetHelloWithName(t *testing.T) {
	router, handler := setupTestRouter()
	router.GET("/api/hello/:name", handler.GetHelloWithName)

	tests := []struct {
		name           string
		pathParam      string
		queryParams    string
		expectedStatus int
		expectedMsg    string
		description    string
	}{
		{
			name:           "TC101 - 正常路径参数",
			pathParam:      "World",
			queryParams:    "",
			expectedStatus: http.StatusOK,
			expectedMsg:    "Hello World!",
			description:    "基本路径参数",
		},
		{
			name:           "TC102 - 中文名称",
			pathParam:      "世界",
			queryParams:    "",
			expectedStatus: http.StatusOK,
			expectedMsg:    "Hello 世界!",
			description:    "中文路径参数",
		},
		{
			name:           "TC103 - 特殊字符名称",
			pathParam:      "@User",
			queryParams:    "",
			expectedStatus: http.StatusOK,
			expectedMsg:    "Hello @User!",
			description:    "特殊字符",
		},
		{
			name:           "TC104 - 带语言参数",
			pathParam:      "World",
			queryParams:    "?lang=zh",
			expectedStatus: http.StatusOK,
			expectedMsg:    "你好，World！",
			description:    "路径参数+语言参数",
		},
	}

	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			w := httptest.NewRecorder()
			req, _ := http.NewRequest("GET", "/api/hello/"+tt.pathParam+tt.queryParams, nil)
			router.ServeHTTP(w, req)

			assert.Equal(t, tt.expectedStatus, w.Code, tt.description)

			if tt.expectedStatus == http.StatusOK {
				var response models.HelloResponse
				err := json.Unmarshal(w.Body.Bytes(), &response)
				assert.NoError(t, err)
				assert.Equal(t, tt.expectedMsg, response.Message)
				assert.NotEmpty(t, response.RequestID)
			}
		})
	}
}

func TestGetHealth(t *testing.T) {
	router, handler := setupTestRouter()
	router.GET("/api/health", handler.GetHealth)

	tests := []struct {
		name        string
		description string
	}{
		{
			name:        "TC201 - 健康检查",
			description: "检查服务状态",
		},
		{
			name:        "TC202 - 版本信息",
			description: "验证版本字段",
		},
	}

	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			w := httptest.NewRecorder()
			req, _ := http.NewRequest("GET", "/api/health", nil)
			router.ServeHTTP(w, req)

			assert.Equal(t, http.StatusOK, w.Code, tt.description)

			var response models.HealthResponse
			err := json.Unmarshal(w.Body.Bytes(), &response)
			assert.NoError(t, err)
			assert.Equal(t, "healthy", response.Status)
			assert.GreaterOrEqual(t, response.Uptime, int64(0))
			assert.Equal(t, "1.0.0-test", response.Version)
		})
	}
}
