#!/bin/bash

# ==============================================================================
# 工具组合使用策略演示脚本
# ==============================================================================

echo "=========================================="
echo "工具组合使用策略演示"
echo "=========================================="
echo ""

# ==============================================================================
# 策略一：自顶向下分析
# ==============================================================================
echo "=========================================="
echo "策略一：自顶向下分析"
echo "=========================================="
echo ""
echo "目标：全面了解签到系统的设计和实现"
echo ""

echo "步骤 1: 使用 search_teamdocs 了解系统整体设计"
echo "-----------------------------------------------"
echo "查询: 'check-in system design architecture database schema'"
echo ""
# search_teamdocs({"query": "check-in system design architecture database schema"})

echo "步骤 2: 使用 search_codebase 定位核心模块"
echo "-----------------------------------------------"
echo "查询: 'check-in attendance record statistics service'"
echo "关键词: 'checkin,attendance,statistics'"
echo "范围: 'checkin/'"
echo ""
# search_codebase({
#   "query": "check-in attendance record statistics service",
#   "key_words": "checkin,attendance,statistics",
#   "search_scope": "checkin/"
# })

echo "步骤 3: 使用 search_file 找到具体文件"
echo "-----------------------------------------------"
echo "查询: 'checkin/**/*.go'"
echo ""
# search_file({"query": "checkin/**/*.go"})

echo "步骤 4: 使用 grep_code 提取关键代码片段"
echo "-----------------------------------------------"
echo "正则: 'func.*CheckIn.*\\('"
echo "文件过滤: '*.go'"
echo ""
# grep_code({
#   "regex": "func.*CheckIn.*\\(",
#   "include_pattern": "*.go"
# })

echo ""
echo "=========================================="
echo "策略一完成"
echo "=========================================="
echo ""

# ==============================================================================
# 策略二：问题定位
# ==============================================================================
echo "=========================================="
echo "策略二：问题定位"
echo "=========================================="
echo ""
echo "目标：定位并解决数据库连接错误"
echo ""

echo "步骤 1: 使用 grep_code 查找错误特征码或异常类型"
echo "-----------------------------------------------"
echo "正则: 'database connection failed|connection timeout'"
echo ""
# grep_code({
#   "regex": "database connection failed|connection timeout"
# })

echo "步骤 2: 使用 search_codebase 理解相关业务逻辑"
echo "-----------------------------------------------"
echo "查询: 'database connection pool initialization configuration'"
echo "关键词: 'database,connection,pool'"
echo ""
# search_codebase({
#   "query": "database connection pool initialization configuration",
#   "key_words": "database,connection,pool"
# })

echo "步骤 3: 使用 search_file 定位配置文件"
echo "-----------------------------------------------"
echo "查询: '**/*.yml'"
echo ""
# search_file({"query": "**/*.yml"})

echo "步骤 4: 使用 search_teamdocs 查找故障排查文档"
echo "-----------------------------------------------"
echo "查询: 'database troubleshooting connection pool configuration'"
echo ""
# search_teamdocs({"query": "database troubleshooting connection pool configuration"})

echo ""
echo "=========================================="
echo "策略二完成"
echo "=========================================="
echo ""

# ==============================================================================
# 策略三：功能扩展
# ==============================================================================
echo "=========================================="
echo "策略三：功能扩展"
echo "=========================================="
echo ""
echo "目标：添加新的积分奖励功能"
echo ""

echo "步骤 1: 使用 search_teamdocs 查找设计规范"
echo "-----------------------------------------------"
echo "查询: 'API design guideline RESTful convention best practices'"
echo ""
# search_teamdocs({"query": "API design guideline RESTful convention best practices"})

echo "步骤 2: 使用 search_codebase 找到类似功能实现"
echo "-----------------------------------------------"
echo "查询: 'reward points bonus calculation user balance'"
echo "关键词: 'reward,points,calculation'"
echo ""
# search_codebase({
#   "query": "reward points bonus calculation user balance",
#   "key_words": "reward,points,calculation"
# })

echo "步骤 3: 使用 search_file 定位需要修改的文件"
echo "-----------------------------------------------"
echo "查询: '**/service/*Service.go'"
echo ""
# search_file({"query": "**/service/*Service.go"})

echo "步骤 4: 使用 grep_code 检查代码规范一致性"
echo "-----------------------------------------------"
echo "正则: 'func\\s+\\([^)]+\\)\\s+[A-Z][a-zA-Z]*\\s*\\('"
echo "文件过滤: '*.go'"
echo ""
# grep_code({
#   "regex": "func\\s+\\([^)]+\\)\\s+[A-Z][a-zA-Z]*\\s*\\(",
#   "include_pattern": "*.go"
# })

echo ""
echo "=========================================="
echo "策略三完成"
echo "=========================================="
echo ""

# ==============================================================================
# 工具选择决策演示
# ==============================================================================
echo "=========================================="
echo "工具选择决策指南"
echo "=========================================="
echo ""

echo "何时使用 search_codebase:"
echo "  - 需要通过语义理解查找代码功能"
echo "  - 不确定具体文件位置，只知道功能需求"
echo "  - 需要跨文件、跨模块的功能关联分析"
echo "  - 查找特定设计模式或架构组件的实现"
echo ""

echo "何时使用 search_file:"
echo "  - 明确知道文件名称模式"
echo "  - 需要按文件类型批量查找"
echo "  - 查找特定目录下的文件"
echo "  - 文件名包含特定关键字或后缀"
echo ""

echo "何时使用 grep_code:"
echo "  - 需要精确匹配代码模式"
echo "  - 查找特定的代码语法结构"
echo "  - 提取符合正则表达式的代码片段"
echo "  - 代码审查和模式一致性检查"
echo ""

echo "何时使用 search_teamdocs:"
echo "  - 查找团队知识库和文档"
echo "  - 需要设计规范、最佳实践指导"
echo "  - 了解系统架构决策的背景"
echo "  - 查找操作手册、运维文档"
echo ""

echo "=========================================="
echo "工具组合使用策略演示完成"
echo "=========================================="
