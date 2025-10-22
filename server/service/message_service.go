package service

import (
	"fmt"
	"strings"
	"time"

	"ao_test/server/models"
)

// MessageService 消息生成服务
type MessageService struct {
	templates   map[string]string
	defaultLang string
}

// NewMessageService 创建消息服务实例
func NewMessageService(defaultLang string) *MessageService {
	return &MessageService{
		templates: map[string]string{
			"en": "Hello %s!",
			"zh": "你好，%s！",
			"es": "¡Hola %s!",
			"fr": "Bonjour %s!",
		},
		defaultLang: defaultLang,
	}
}

// GenerateGreeting 生成问候消息
func (s *MessageService) GenerateGreeting(req models.HelloRequest, requestID string) models.HelloResponse {
	// 设置默认值
	name := req.Name
	if name == "" {
		name = "World"
	}

	lang := req.Lang
	if lang == "" {
		lang = s.defaultLang
	}

	// 验证语言是否支持
	template, exists := s.templates[lang]
	if !exists {
		template = s.templates[s.defaultLang]
	}

	// 生成消息
	message := fmt.Sprintf(template, name)

	// 生成时间戳 (ISO 8601 格式)
	timestamp := time.Now().UTC().Format(time.RFC3339)

	return models.HelloResponse{
		Message:   message,
		Timestamp: timestamp,
		RequestID: requestID,
	}
}

// ValidateName 验证名称参数
func (s *MessageService) ValidateName(name string) error {
	if len(name) == 0 {
		return fmt.Errorf("name cannot be empty")
	}
	if len(name) > 50 {
		return fmt.Errorf("name too long (max 50 characters)")
	}
	return nil
}

// ValidateLang 验证语言参数
func (s *MessageService) ValidateLang(lang string) bool {
	if lang == "" {
		return true
	}
	_, exists := s.templates[strings.ToLower(lang)]
	return exists
}

// GetSupportedLanguages 获取支持的语言列表
func (s *MessageService) GetSupportedLanguages() []string {
	langs := make([]string, 0, len(s.templates))
	for lang := range s.templates {
		langs = append(langs, lang)
	}
	return langs
}
