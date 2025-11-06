#!/bin/bash

# ==============================================================================
# search_file 工具调用示例脚本
# ==============================================================================

echo "=========================================="
echo "示例一：查找所有 Go 主程序入口"
echo "=========================================="
echo "使用场景: 需要定位项目中所有可执行的 Go 程序入口点"
echo "调用模式: glob 模式匹配"
echo ""
echo "查询参数: '*/main.go'"
echo ""
echo "预期结果:"
echo "  - server/main.go"
echo "  - client/main.go"
echo ""

# 实际工具调用示意（注释形式）
# search_file({"query": "*/main.go"})

echo "=========================================="
echo "示例二：查找所有测试文件"
echo "=========================================="
echo "使用场景: 需要检查项目的测试覆盖情况"
echo "调用模式: glob 模式匹配"
echo ""
echo "查询参数: '*_test.go'"
echo ""
echo "预期结果: 返回所有以 _test.go 结尾的测试文件"
echo ""

# search_file({"query": "*_test.go"})

echo "=========================================="
echo "示例三：查找配置文件"
echo "=========================================="
echo "使用场景: 需要查看项目的配置文件位置"
echo "调用模式: glob 模式匹配"
echo ""
echo "查询参数: '**/*.yml'"
echo ""
echo "预期结果: 返回所有 YAML 格式的配置文件，如 application.yml"
echo ""

# search_file({"query": "**/*.yml"})

echo "=========================================="
echo "示例四：查找 Shell 脚本"
echo "=========================================="
echo "使用场景: 了解项目的自动化脚本"
echo "调用模式: glob 模式匹配"
echo ""
echo "查询参数: '*.sh'"
echo ""
echo "预期结果:"
echo "  - build.sh"
echo "  - start_server.sh"
echo "  - run_client.sh"
echo "  - run_tests.sh"
echo "  - 等其他脚本文件"
echo ""

# search_file({"query": "*.sh"})

echo "=========================================="
echo "示例五：查找 Java Controller 类"
echo "=========================================="
echo "使用场景: 定位所有 Spring MVC 控制器"
echo "调用模式: glob 模式匹配"
echo ""
echo "查询参数: '**/controller/*Controller.java'"
echo ""
echo "预期结果: 返回所有控制器类文件，如 CheckInController.java"
echo ""

# search_file({"query": "**/controller/*Controller.java"})

echo "=========================================="
echo "示例六：查找 Markdown 文档"
echo "=========================================="
echo "使用场景: 查看项目文档和说明文件"
echo "调用模式: glob 模式匹配"
echo ""
echo "查询参数: '*.md'"
echo ""
echo "预期结果:"
echo "  - README.md"
echo "  - ARCHITECTURE.md"
echo "  - DEMO.md"
echo "  - PROJECT_SUMMARY.md"
echo "  - 等文档文件"
echo ""

# search_file({"query": "*.md"})

echo ""
echo "=========================================="
echo "search_file 工具调用示例执行完成"
echo "=========================================="
echo ""
echo "最佳实践提示："
echo "  1. 仅支持 glob 模式，不支持正则表达式"
echo "  2. 使用 ** 进行递归目录匹配"
echo "  3. 结果限制为 25 个，需确保模式足够具体"
echo "  4. 善用通配符 * 和 ? 提高匹配灵活性"
