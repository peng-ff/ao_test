#!/bin/bash

# 运行所有测试用例

echo "=========================================="
echo "运行远程 Hello World 程序测试套件"
echo "=========================================="
echo ""

# 清理测试缓存
echo "1. 清理测试缓存..."
go clean -testcache

# 运行所有测试
echo ""
echo "2. 运行所有单元测试..."
echo ""
go test ./... -v

# 生成测试覆盖率报告
echo ""
echo "3. 生成测试覆盖率报告..."
echo ""
go test ./... -coverprofile=coverage.out
go tool cover -func=coverage.out

echo ""
echo "=========================================="
echo "测试完成！"
echo "=========================================="
echo ""
echo "查看详细覆盖率报告:"
echo "  go tool cover -html=coverage.out"
