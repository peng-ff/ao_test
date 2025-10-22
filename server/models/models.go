package models

import "time"

// HelloRequest 问候请求模型
type HelloRequest struct {
	Name string `json:"name" form:"name"`
	Lang string `json:"lang" form:"lang"`
}

// HelloResponse 问候响应模型
type HelloResponse struct {
	Message   string `json:"message"`
	Timestamp string `json:"timestamp"`
	RequestID string `json:"requestId"`
}

// ErrorResponse 错误响应模型
type ErrorResponse struct {
	Error     string `json:"error"`
	Message   string `json:"message"`
	RequestID string `json:"requestId"`
}

// HealthResponse 健康检查响应模型
type HealthResponse struct {
	Status  string `json:"status"`
	Uptime  int64  `json:"uptime"`
	Version string `json:"version"`
}

// RequestLog 请求日志模型
type RequestLog struct {
	RequestID    string        `json:"requestId"`
	Method       string        `json:"method"`
	Path         string        `json:"path"`
	ClientIP     string        `json:"clientIp"`
	Timestamp    time.Time     `json:"timestamp"`
	ResponseTime int64         `json:"responseTime"` // 毫秒
	StatusCode   int           `json:"statusCode"`
}
