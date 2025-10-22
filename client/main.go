package main

import (
	"encoding/json"
	"flag"
	"fmt"
	"io"
	"net/http"
	"os"
	"time"
)

// HelloResponse 问候响应模型
type HelloResponse struct {
	Message   string `json:"message"`
	Timestamp string `json:"timestamp"`
	RequestID string `json:"requestId"`
}

// HealthResponse 健康检查响应模型
type HealthResponse struct {
	Status  string `json:"status"`
	Uptime  int64  `json:"uptime"`
	Version string `json:"version"`
}

// ErrorResponse 错误响应模型
type ErrorResponse struct {
	Error     string `json:"error"`
	Message   string `json:"message"`
	RequestID string `json:"requestId"`
}

// ClientConfig 客户端配置
type ClientConfig struct {
	ServerURL      string
	Timeout        time.Duration
	RetryEnabled   bool
	RetryMaxAttempts int
}

// Client HTTP 客户端
type Client struct {
	config     ClientConfig
	httpClient *http.Client
}

// NewClient 创建客户端实例
func NewClient(config ClientConfig) *Client {
	return &Client{
		config: config,
		httpClient: &http.Client{
			Timeout: config.Timeout,
		},
	}
}

// SendHelloRequest 发送问候请求
func (c *Client) SendHelloRequest(name, lang string) error {
	url := fmt.Sprintf("%s/api/hello", c.config.ServerURL)
	
	// 添加查询参数
	if name != "" || lang != "" {
		url += "?"
		if name != "" {
			url += fmt.Sprintf("name=%s", name)
		}
		if lang != "" {
			if name != "" {
				url += "&"
			}
			url += fmt.Sprintf("lang=%s", lang)
		}
	}

	return c.executeRequest(url, &HelloResponse{})
}

// CheckHealth 检查服务健康状态
func (c *Client) CheckHealth() error {
	url := fmt.Sprintf("%s/api/health", c.config.ServerURL)
	return c.executeRequest(url, &HealthResponse{})
}

// executeRequest 执行 HTTP 请求
func (c *Client) executeRequest(url string, respModel interface{}) error {
	var lastErr error
	attempts := 1
	if c.config.RetryEnabled {
		attempts = c.config.RetryMaxAttempts
	}

	for i := 0; i < attempts; i++ {
		if i > 0 {
			fmt.Printf("重试第 %d 次...\n", i)
			time.Sleep(time.Second * time.Duration(i))
		}

		resp, err := c.httpClient.Get(url)
		if err != nil {
			lastErr = fmt.Errorf("请求失败: %v", err)
			continue
		}
		defer resp.Body.Close()

		body, err := io.ReadAll(resp.Body)
		if err != nil {
			lastErr = fmt.Errorf("读取响应失败: %v", err)
			continue
		}

		// 检查状态码
		if resp.StatusCode != http.StatusOK {
			var errResp ErrorResponse
			if err := json.Unmarshal(body, &errResp); err == nil {
				lastErr = fmt.Errorf("服务器错误 (%d): %s - %s", resp.StatusCode, errResp.Error, errResp.Message)
			} else {
				lastErr = fmt.Errorf("服务器错误 (%d): %s", resp.StatusCode, string(body))
			}
			continue
		}

		// 解析响应
		if err := json.Unmarshal(body, respModel); err != nil {
			lastErr = fmt.Errorf("解析响应失败: %v", err)
			continue
		}

		// 成功,打印结果
		c.printResponse(respModel)
		return nil
	}

	return lastErr
}

// printResponse 打印响应结果
func (c *Client) printResponse(resp interface{}) {
	fmt.Println("\n========== 响应结果 ==========")
	
	switch v := resp.(type) {
	case *HelloResponse:
		fmt.Printf("消息: %s\n", v.Message)
		fmt.Printf("时间戳: %s\n", v.Timestamp)
		fmt.Printf("请求ID: %s\n", v.RequestID)
	case *HealthResponse:
		fmt.Printf("状态: %s\n", v.Status)
		fmt.Printf("运行时长: %d 秒\n", v.Uptime)
		fmt.Printf("版本: %s\n", v.Version)
	}
	
	fmt.Println("==============================")
}

func main() {
	// 定义命令行参数
	var (
		serverURL = flag.String("server", "http://localhost:8080", "服务端地址")
		timeout   = flag.Int("timeout", 5000, "请求超时时间(毫秒)")
		retry     = flag.Bool("retry", true, "是否启用重试")
		maxRetry  = flag.Int("max-retry", 3, "最大重试次数")
		name      = flag.String("name", "", "接收问候的对象名称")
		lang      = flag.String("lang", "", "语言代码(zh/en/es/fr)")
		health    = flag.Bool("health", false, "检查服务健康状态")
		version   = flag.Bool("version", false, "显示客户端版本")
	)

	flag.Parse()

	// 显示版本
	if *version {
		fmt.Println("Remote Hello World Client v1.0.0")
		return
	}

	// 创建客户端配置
	config := ClientConfig{
		ServerURL:        *serverURL,
		Timeout:          time.Duration(*timeout) * time.Millisecond,
		RetryEnabled:     *retry,
		RetryMaxAttempts: *maxRetry,
	}

	// 创建客户端
	client := NewClient(config)

	fmt.Printf("连接到服务器: %s\n", config.ServerURL)

	// 执行命令
	var err error
	if *health {
		fmt.Println("执行健康检查...")
		err = client.CheckHealth()
	} else {
		fmt.Println("发送问候请求...")
		err = client.SendHelloRequest(*name, *lang)
	}

	if err != nil {
		fmt.Fprintf(os.Stderr, "错误: %v\n", err)
		os.Exit(1)
	}
}
