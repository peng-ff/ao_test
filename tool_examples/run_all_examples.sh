#!/bin/bash

# ==============================================================================
# 执行所有工具调用示例
# ==============================================================================

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

echo "======================================================"
echo "工具调用示例演示 - 主执行脚本"
echo "======================================================"
echo ""
echo "本脚本将依次执行所有工具调用示例"
echo ""

# 检查脚本文件是否存在
if [ ! -f "$SCRIPT_DIR/01_search_codebase_examples.sh" ]; then
    echo "错误: 找不到 01_search_codebase_examples.sh"
    exit 1
fi

if [ ! -f "$SCRIPT_DIR/02_search_file_examples.sh" ]; then
    echo "错误: 找不到 02_search_file_examples.sh"
    exit 1
fi

if [ ! -f "$SCRIPT_DIR/03_grep_code_examples.sh" ]; then
    echo "错误: 找不到 03_grep_code_examples.sh"
    exit 1
fi

if [ ! -f "$SCRIPT_DIR/04_combined_strategies.sh" ]; then
    echo "错误: 找不到 04_combined_strategies.sh"
    exit 1
fi

# 设置执行权限
chmod +x "$SCRIPT_DIR/01_search_codebase_examples.sh"
chmod +x "$SCRIPT_DIR/02_search_file_examples.sh"
chmod +x "$SCRIPT_DIR/03_grep_code_examples.sh"
chmod +x "$SCRIPT_DIR/04_combined_strategies.sh"

echo "======================================================"
echo "1. 执行 search_codebase 工具调用示例"
echo "======================================================"
echo ""
bash "$SCRIPT_DIR/01_search_codebase_examples.sh"
echo ""

echo "======================================================"
echo "2. 执行 search_file 工具调用示例"
echo "======================================================"
echo ""
bash "$SCRIPT_DIR/02_search_file_examples.sh"
echo ""

echo "======================================================"
echo "3. 执行 grep_code 工具调用示例"
echo "======================================================"
echo ""
bash "$SCRIPT_DIR/03_grep_code_examples.sh"
echo ""

echo "======================================================"
echo "4. 执行工具组合使用策略演示"
echo "======================================================"
echo ""
bash "$SCRIPT_DIR/04_combined_strategies.sh"
echo ""

echo "======================================================"
echo "所有工具调用示例执行完成"
echo "======================================================"
echo ""
echo "示例脚本位置："
echo "  - search_codebase 示例: $SCRIPT_DIR/01_search_codebase_examples.sh"
echo "  - search_file 示例:     $SCRIPT_DIR/02_search_file_examples.sh"
echo "  - grep_code 示例:       $SCRIPT_DIR/03_grep_code_examples.sh"
echo "  - 组合策略示例:         $SCRIPT_DIR/04_combined_strategies.sh"
echo ""
