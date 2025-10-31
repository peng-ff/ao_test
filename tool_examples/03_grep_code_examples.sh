#!/bin/bash

# ==============================================================================
# grep_code 工具调用示例脚本
# ==============================================================================

echo "=========================================="
echo "示例一：查找所有 TODO 注释"
echo "=========================================="
echo "使用场景: 识别代码中待完成的任务标记"
echo ""
echo "正则表达式: '//\s*TODO:.*'"
echo "文件过滤: include_pattern: '*.go'"
echo ""
echo "预期结果: 返回所有 Go 文件中的 TODO 注释及其位置"
echo ""

# 实际工具调用示意（注释形式）
# grep_code({
#   "regex": "//\\s*TODO:.*",
#   "include_pattern": "*.go"
# })

echo "=========================================="
echo "示例二：查找日志记录语句"
echo "=========================================="
echo "使用场景: 审查项目的日志记录情况"
echo ""
echo "正则表达式: 'log\\.(Info|Debug|Warn|Error|Fatal)'"
echo "文件过滤: include_pattern: '*.go'"
echo ""
echo "预期结果: 返回所有使用 logrus 记录日志的代码位置"
echo ""

# grep_code({
#   "regex": "log\\.(Info|Debug|Warn|Error|Fatal)",
#   "include_pattern": "*.go"
# })

echo "=========================================="
echo "示例三：查找 HTTP 端点定义"
echo "=========================================="
echo "使用场景: 提取所有 RESTful API 路由定义"
echo ""
echo "正则表达式: '(GET|POST|PUT|DELETE|PATCH)\\s*\\(\\s*[\"']\\/'"
echo "文件过滤: include_pattern: '*.go'"
echo ""
echo "预期结果: 返回所有 HTTP 方法路由注册的代码行"
echo ""

# grep_code({
#   "regex": "(GET|POST|PUT|DELETE|PATCH)\\s*\\(\\s*[\"']\\/",
#   "include_pattern": "*.go"
# })

echo "=========================================="
echo "示例四：查找数据库查询语句"
echo "=========================================="
echo "使用场景: 审查直接的 SQL 查询代码"
echo ""
echo "正则表达式: 'SELECT\\s+.*\\s+FROM\\s+'"
echo "文件过滤: include_pattern: '*.java'"
echo ""
echo "预期结果: 返回 Java 代码中包含的 SQL SELECT 语句"
echo ""

# grep_code({
#   "regex": "SELECT\\s+.*\\s+FROM\\s+",
#   "include_pattern": "*.java"
# })

echo "=========================================="
echo "示例五：查找环境变量读取"
echo "=========================================="
echo "使用场景: 了解系统依赖的环境变量配置"
echo ""
echo "正则表达式: 'os\\.Getenv\\s*\\(\\s*[\"']'"
echo "文件过滤: include_pattern: '*.go'"
echo ""
echo "预期结果: 返回所有读取环境变量的代码位置"
echo ""

# grep_code({
#   "regex": "os\\.Getenv\\s*\\(\\s*[\"']",
#   "include_pattern": "*.go"
# })

echo "=========================================="
echo "示例六：查找错误处理模式"
echo "=========================================="
echo "使用场景: 检查错误处理的一致性"
echo ""
echo "正则表达式: 'if\\s+err\\s*!=\\s*nil\\s*\\{'"
echo "文件过滤: include_pattern: '*.go'"
echo ""
echo "预期结果: 返回所有 Go 语言标准错误处理模式的位置"
echo ""

# grep_code({
#   "regex": "if\\s+err\\s*!=\\s*nil\\s*\\{",
#   "include_pattern": "*.go"
# })

echo "=========================================="
echo "示例七：查找依赖注入注解"
echo "=========================================="
echo "使用场景: 识别 Spring 框架的依赖注入点"
echo ""
echo "正则表达式: '@Autowired|@Inject|@Resource'"
echo "文件过滤: include_pattern: '*.java'"
echo ""
echo "预期结果: 返回所有使用依赖注入注解的代码位置"
echo ""

# grep_code({
#   "regex": "@Autowired|@Inject|@Resource",
#   "include_pattern": "*.java"
# })

echo ""
echo "=========================================="
echo "grep_code 工具调用示例执行完成"
echo "=========================================="
echo ""
echo "最佳实践提示："
echo "  1. 使用 RE2 正则表达式语法"
echo "  2. 通过 include_pattern 过滤文件类型，减少噪音"
echo "  3. 结果上限为 25 条匹配，需精心设计表达式"
echo "  4. 适合精确模式匹配，不适合模糊搜索"
