package service

import (
	"testing"

	"ao_test/server/models"

	"github.com/stretchr/testify/assert"
)

func TestMessageService_GenerateGreeting(t *testing.T) {
	service := NewMessageService("en")

	tests := []struct {
		name          string
		request       models.HelloRequest
		expectedMsg   string
		description   string
	}{
		{
			name:        "TC001 - 无参数请求",
			request:     models.HelloRequest{},
			expectedMsg: "Hello World!",
			description: "默认问候消息",
		},
		{
			name:        "TC002 - 指定名称",
			request:     models.HelloRequest{Name: "张三"},
			expectedMsg: "Hello 张三!",
			description: "指定名称的问候消息",
		},
		{
			name:        "TC003 - 指定中文语言",
			request:     models.HelloRequest{Lang: "zh"},
			expectedMsg: "你好，World！",
			description: "中文问候消息",
		},
		{
			name:        "TC004 - 同时指定名称和语言",
			request:     models.HelloRequest{Name: "张三", Lang: "zh"},
			expectedMsg: "你好，张三！",
			description: "中文个性化问候消息",
		},
		{
			name:        "TC005 - 不支持的语言",
			request:     models.HelloRequest{Lang: "xx"},
			expectedMsg: "Hello World!",
			description: "使用默认语言",
		},
		{
			name:        "TC006 - 西班牙语",
			request:     models.HelloRequest{Name: "Mundo", Lang: "es"},
			expectedMsg: "¡Hola Mundo!",
			description: "西班牙语问候",
		},
		{
			name:        "TC007 - 法语",
			request:     models.HelloRequest{Name: "Monde", Lang: "fr"},
			expectedMsg: "Bonjour Monde!",
			description: "法语问候",
		},
	}

	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			response := service.GenerateGreeting(tt.request, "test-req-id")
			
			assert.Equal(t, tt.expectedMsg, response.Message, tt.description)
			assert.Equal(t, "test-req-id", response.RequestID)
			assert.NotEmpty(t, response.Timestamp)
		})
	}
}

func TestMessageService_ValidateName(t *testing.T) {
	service := NewMessageService("en")

	tests := []struct {
		name      string
		input     string
		expectErr bool
		description string
	}{
		{
			name:      "Valid name",
			input:     "John",
			expectErr: false,
			description: "有效的名称",
		},
		{
			name:      "Empty name",
			input:     "",
			expectErr: true,
			description: "空名称应该失败",
		},
		{
			name:      "Too long name",
			input:     "ThisIsAVeryLongNameThatExceedsTheFiftyCharacterLimit123456",
			expectErr: true,
			description: "超长名称应该失败",
		},
		{
			name:      "Chinese name",
			input:     "张三",
			expectErr: false,
			description: "中文名称应该有效",
		},
		{
			name:      "Special characters",
			input:     "@User",
			expectErr: false,
			description: "特殊字符名称应该有效",
		},
	}

	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			err := service.ValidateName(tt.input)
			if tt.expectErr {
				assert.Error(t, err, tt.description)
			} else {
				assert.NoError(t, err, tt.description)
			}
		})
	}
}

func TestMessageService_ValidateLang(t *testing.T) {
	service := NewMessageService("en")

	tests := []struct {
		name   string
		lang   string
		valid  bool
	}{
		{"Empty language", "", true},
		{"English", "en", true},
		{"Chinese", "zh", true},
		{"Spanish", "es", true},
		{"French", "fr", true},
		{"Invalid language", "xx", false},
	}

	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			result := service.ValidateLang(tt.lang)
			assert.Equal(t, tt.valid, result)
		})
	}
}

func TestMessageService_GetSupportedLanguages(t *testing.T) {
	service := NewMessageService("en")
	
	langs := service.GetSupportedLanguages()
	
	assert.NotEmpty(t, langs)
	assert.Contains(t, langs, "en")
	assert.Contains(t, langs, "zh")
	assert.Contains(t, langs, "es")
	assert.Contains(t, langs, "fr")
}
