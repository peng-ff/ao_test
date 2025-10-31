package handlers

import (
	"net/http"
	"time"

	"ao_test/server/middleware"
	"ao_test/server/models"
	"ao_test/server/service"

	"github.com/gin-gonic/gin"
)

// HelloHandler Hello World API 处理器
type HelloHandler struct {
	messageService *service.MessageService
	startTime      time.Time
	version        string
	totalRequests  int64
	activeRequests int
}

// NewHelloHandler 创建处理器实例
func NewHelloHandler(messageService *service.MessageService, version string) *HelloHandler {
	return &HelloHandler{
		messageService: messageService,
		startTime:      time.Now(),
		version:        version,
	}
}

// GetHello 处理基本问候请求
// GET /api/hello?name=xxx&lang=xxx
func (h *HelloHandler) GetHello(c *gin.Context) {
	h.activeRequests++
	defer func() {
		h.activeRequests--
		h.totalRequests++
	}()
	
	var req models.HelloRequest
	
	// 绑定查询参数
	if err := c.ShouldBindQuery(&req); err != nil {
		requestID := middleware.GetRequestID(c)
		c.JSON(http.StatusBadRequest, models.ErrorResponse{
			Error:     "BadRequest",
			Message:   "Invalid query parameters",
			RequestID: requestID,
		})
		return
	}

	// 生成问候消息
	requestID := middleware.GetRequestID(c)
	response := h.messageService.GenerateGreeting(req, requestID)

	c.JSON(http.StatusOK, response)
}

// GetHelloWithName 处理个性化问候请求
// GET /api/hello/:name
func (h *HelloHandler) GetHelloWithName(c *gin.Context) {
	h.activeRequests++
	defer func() {
		h.activeRequests--
		h.totalRequests++
	}()
	
	name := c.Param("name")
	
	// 验证名称参数
	if err := h.messageService.ValidateName(name); err != nil {
		requestID := middleware.GetRequestID(c)
		c.JSON(http.StatusBadRequest, models.ErrorResponse{
			Error:     "BadRequest",
			Message:   err.Error(),
			RequestID: requestID,
		})
		return
	}

	// 获取语言参数
	lang := c.DefaultQuery("lang", "en")

	// 构建请求
	req := models.HelloRequest{
		Name: name,
		Lang: lang,
	}

	// 生成问候消息
	requestID := middleware.GetRequestID(c)
	response := h.messageService.GenerateGreeting(req, requestID)

	c.JSON(http.StatusOK, response)
}

// GetHealth 处理健康检查请求
// GET /api/health
func (h *HelloHandler) GetHealth(c *gin.Context) {
	uptime := time.Since(h.startTime).Seconds()

	response := models.HealthResponse{
		Status:  "healthy",
		Uptime:  int64(uptime),
		Version: h.version,
	}

	c.JSON(http.StatusOK, response)
}

// GetStats 处理系统统计请求
// GET /api/stats
func (h *HelloHandler) GetStats(c *gin.Context) {
	uptime := time.Since(h.startTime).Seconds()
	requestID := middleware.GetRequestID(c)

	response := models.StatsResponse{
		TotalRequests:  h.totalRequests,
		ActiveRequests: h.activeRequests,
		Uptime:         int64(uptime),
		StartTime:      h.startTime.UTC().Format(time.RFC3339),
		Version:        h.version,
		RequestID:      requestID,
	}

	c.JSON(http.StatusOK, response)
}
