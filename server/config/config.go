package config

import (
	"os"
	"strconv"
	"strings"
)

// ServerConfig 服务器配置
type ServerConfig struct {
	Port int
	Host string
}

// LogConfig 日志配置
type LogConfig struct {
	Level  string
	Format string
}

// CORSConfig CORS 配置
type CORSConfig struct {
	Enabled bool
	Origins []string
}

// MessageConfig 消息配置
type MessageConfig struct {
	DefaultLang string
}

// Config 应用配置
type Config struct {
	Server  ServerConfig
	Log     LogConfig
	CORS    CORSConfig
	Message MessageConfig
	Version string
	// 新增环境配置项
	Environment string
}

// LoadConfig 加载配置
func LoadConfig() *Config {
	return &Config{
		Server: ServerConfig{
			Port: getEnvAsInt("SERVER_PORT", 8080),
			Host: getEnv("SERVER_HOST", "0.0.0.0"),
		},
		Log: LogConfig{
			Level:  getEnv("LOG_LEVEL", "info"),
			Format: getEnv("LOG_FORMAT", "json"),
		},
		CORS: CORSConfig{
			Enabled: getEnvAsBool("CORS_ENABLED", true),
			Origins: getEnvAsSlice("CORS_ORIGINS", []string{"*"}),
		},
		Message: MessageConfig{
			DefaultLang: getEnv("MESSAGE_DEFAULT_LANG", "en"),
		},
		Version: getEnv("APP_VERSION", "1.0.0"),
		// 新增环境配置项
		Environment: getEnv("ENVIRONMENT", "development"),
	}
}

// getEnv 获取环境变量,如果不存在则返回默认值
func getEnv(key string, defaultValue string) string {
	value := os.Getenv(key)
	if value == "" {
		return defaultValue
	}
	return value
}

// getEnvAsInt 获取整数类型的环境变量
func getEnvAsInt(key string, defaultValue int) int {
	valueStr := os.Getenv(key)
	if valueStr == "" {
		return defaultValue
	}
	value, err := strconv.Atoi(valueStr)
	if err != nil {
		return defaultValue
	}
	return value
}

// getEnvAsBool 获取布尔类型的环境变量
func getEnvAsBool(key string, defaultValue bool) bool {
	valueStr := os.Getenv(key)
	if valueStr == "" {
		return defaultValue
	}
	value, err := strconv.ParseBool(valueStr)
	if err != nil {
		return defaultValue
	}
	return value
}

// getEnvAsSlice 获取切片类型的环境变量
func getEnvAsSlice(key string, defaultValue []string) []string {
	valueStr := os.Getenv(key)
	if valueStr == "" {
		return defaultValue
	}
	return strings.Split(valueStr, ",")
}
