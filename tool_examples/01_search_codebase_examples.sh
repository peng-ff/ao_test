#!/bin/bash

# ==============================================================================
# search_codebase 工具调用示例脚本
# ==============================================================================

echo "=========================================="
echo "示例一：搜索认证逻辑"
echo "=========================================="
echo "使用场景: 需要了解系统如何处理用户认证和授权机制"
echo ""
echo "调用参数:"
echo "  query: 'authentication authorization user login security'"
echo "  key_words: 'authentication,authorization,login'"
echo "  search_scope: 'server/'"
echo ""
echo "预期结果: 返回与认证相关的中间件、处理器和服务层代码"
echo ""

# 实际工具调用示意（注释形式）
# search_codebase({
#   "query": "authentication authorization user login security",
#   "key_words": "authentication,authorization,login",
#   "search_scope": "server/"
# })

echo "=========================================="
echo "示例二：搜索数据库操作"
echo "=========================================="
echo "使用场景: 理解项目的数据持久化策略和数据库交互方式"
echo ""
echo "调用参数:"
echo "  query: 'database repository query persistence entity model'"
echo "  key_words: 'database,repository,persistence'"
echo "  search_scope: 未指定（全局搜索）"
echo ""
echo "预期结果: 返回 repository 层、entity 定义、数据库配置等相关代码"
echo ""

# search_codebase({
#   "query": "database repository query persistence entity model",
#   "key_words": "database,repository,persistence"
# })

echo "=========================================="
echo "示例三：搜索签到功能实现"
echo "=========================================="
echo "使用场景: 查找签到系统的核心业务逻辑"
echo ""
echo "调用参数:"
echo "  query: 'check-in attendance record statistics service'"
echo "  key_words: 'checkin,attendance,statistics'"
echo "  search_scope: 'checkin/'"
echo ""
echo "预期结果: 返回签到服务、控制器、统计计算等相关实现"
echo ""

# search_codebase({
#   "query": "check-in attendance record statistics service",
#   "key_words": "checkin,attendance,statistics",
#   "search_scope": "checkin/"
# })

echo "=========================================="
echo "示例四：搜索错误处理机制"
echo "=========================================="
echo "使用场景: 了解项目的异常处理和错误响应策略"
echo ""
echo "调用参数:"
echo "  query: 'error handling exception middleware global handler'"
echo "  key_words: 'error,exception,handler'"
echo "  search_scope: 'server/middleware/'"
echo ""
echo "预期结果: 返回错误处理中间件、异常处理器等相关代码"
echo ""

# search_codebase({
#   "query": "error handling exception middleware global handler",
#   "key_words": "error,exception,handler",
#   "search_scope": "server/middleware/"
# })

echo "=========================================="
echo "示例五：搜索 API 路由定义"
echo "=========================================="
echo "使用场景: 需要梳理系统提供的所有 RESTful API 端点"
echo ""
echo "调用参数:"
echo "  query: 'HTTP routes endpoints API controller mapping REST'"
echo "  key_words: 'routes,endpoints,controller'"
echo "  search_scope: 未指定（全局搜索）"
echo ""
echo "预期结果: 返回路由配置文件、控制器类和 API 处理器"
echo ""

# search_codebase({
#   "query": "HTTP routes endpoints API controller mapping REST",
#   "key_words": "routes,endpoints,controller"
# })

echo ""
echo "=========================================="
echo "search_codebase 工具调用示例执行完成"
echo "=========================================="
