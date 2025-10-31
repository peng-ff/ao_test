# 工具调用示例使用指南

## 📋 概述

本指南提供了代码库分析中四种核心工具的详细调用示例和最佳实践，帮助开发者高效地分析和理解代码库。

## 📂 文件结构

```
tool_examples/
├── 01_search_codebase_examples.sh  # search_codebase 工具示例
├── 02_search_file_examples.sh      # search_file 工具示例
├── 03_grep_code_examples.sh        # grep_code 工具示例
├── 04_combined_strategies.sh       # 工具组合使用策略
├── run_all_examples.sh             # 执行所有示例的主脚本
└── TOOL_USAGE_GUIDE.md            # 本文档
```

## 🚀 快速开始

### 执行所有示例

```bash
cd tool_examples
chmod +x run_all_examples.sh
./run_all_examples.sh
```

### 单独执行某个示例

```bash
# 执行 search_codebase 示例
./01_search_codebase_examples.sh

# 执行 search_file 示例
./02_search_file_examples.sh

# 执行 grep_code 示例
./03_grep_code_examples.sh

# 执行组合策略示例
./04_combined_strategies.sh
```

## 🔍 工具详解

### 1. search_codebase

**用途**: 语义搜索代码功能

**适用场景**:
- 不确定具体文件位置，只知道功能需求
- 需要跨文件、跨模块的功能关联分析
- 查找特定设计模式或架构组件

**示例**:

```json
{
  "query": "authentication authorization user login security",
  "key_words": "authentication,authorization,login",
  "search_scope": "server/"
}
```

**最佳实践**:
- 关键词聚焦业务领域术语
- 使用 search_scope 缩小搜索范围
- 最多提取三个最相关的关键词

### 2. search_file

**用途**: 通过 glob 模式查找文件

**适用场景**:
- 明确知道文件名称模式
- 需要按文件类型批量查找
- 查找特定目录下的文件

**示例**:

```bash
# 查找所有 Go 主程序
search_file("*/main.go")

# 查找所有测试文件
search_file("*_test.go")

# 查找所有配置文件
search_file("**/*.yml")
```

**最佳实践**:
- 仅支持 glob 模式，不支持正则
- 使用 `**` 进行递归目录匹配
- 结果限制为 25 个，需确保模式足够具体

### 3. grep_code

**用途**: 使用正则表达式精确匹配代码

**适用场景**:
- 需要精确匹配代码模式
- 查找特定的代码语法结构
- 代码审查和模式一致性检查

**示例**:

```json
{
  "regex": "//\\s*TODO:.*",
  "include_pattern": "*.go"
}
```

**最佳实践**:
- 使用 RE2 正则表达式语法
- 通过 include_pattern 过滤文件类型
- 结果上限为 25 条匹配

### 4. search_teamdocs

**用途**: 搜索团队知识库文档

**适用场景**:
- 查找设计规范、最佳实践指导
- 了解系统架构决策的背景
- 查找操作手册、运维文档

**示例**:

```json
{
  "query": "deployment docker kubernetes container orchestration"
}
```

## 🎯 工具组合策略

### 策略一：自顶向下分析

**目标**: 全面了解系统的设计和实现

1. **search_teamdocs** - 了解系统整体设计
2. **search_codebase** - 定位核心模块
3. **search_file** - 找到具体文件
4. **grep_code** - 提取关键代码片段

### 策略二：问题定位

**目标**: 快速定位并解决问题

1. **grep_code** - 查找错误特征码
2. **search_codebase** - 理解相关业务逻辑
3. **search_file** - 定位配置文件
4. **search_teamdocs** - 查找故障排查文档

### 策略三：功能扩展

**目标**: 添加新功能或优化现有功能

1. **search_teamdocs** - 查找设计规范
2. **search_codebase** - 找到类似功能实现
3. **search_file** - 定位需要修改的文件
4. **grep_code** - 检查代码规范一致性

## 📊 工具选择决策树

```
需要查找什么？
│
├─ 文档/规范/设计 → search_teamdocs
│
├─ 功能/模块/业务逻辑 → search_codebase
│
├─ 特定文件名/文件类型 → search_file
│
└─ 特定代码模式/语法结构 → grep_code
```

## ⚠️ 限制说明

### 通用限制
- 所有搜索工具结果数量均有上限（通常 25 条）
- 并行调用多个独立工具可提高效率

### 特定限制
- **search_file**: 仅支持 glob，不支持正则
- **grep_code**: 仅支持 RE2 正则语法
- **search_codebase**: 需要明确的关键词和查询描述
- **search_teamdocs**: 依赖团队知识库的内容质量

## 💡 实际应用示例

### 示例 1: 查找认证相关代码

```bash
# 步骤 1: 搜索认证模块
search_codebase({
  "query": "authentication authorization user login security",
  "key_words": "authentication,authorization,login",
  "search_scope": "server/"
})

# 步骤 2: 查找中间件文件
search_file("server/middleware/*.go")

# 步骤 3: 提取具体实现
grep_code({
  "regex": "func.*Auth.*\\(",
  "include_pattern": "*.go"
})
```

### 示例 2: 审查错误处理

```bash
# 查找所有错误处理模式
grep_code({
  "regex": "if\\s+err\\s*!=\\s*nil\\s*\\{",
  "include_pattern": "*.go"
})

# 查找自定义错误类型
grep_code({
  "regex": "type\\s+\\w+Error\\s+struct",
  "include_pattern": "*.go"
})
```

### 示例 3: 分析数据库操作

```bash
# 搜索数据库相关代码
search_codebase({
  "query": "database repository query persistence entity model",
  "key_words": "database,repository,persistence"
})

# 查找所有 repository 文件
search_file("**/repository/*.go")

# 提取 SQL 查询
grep_code({
  "regex": "SELECT\\s+.*\\s+FROM\\s+",
  "include_pattern": "*.go"
})
```

## 📚 参考资料

- **glob 模式语法**: https://en.wikipedia.org/wiki/Glob_(programming)
- **RE2 正则表达式**: https://github.com/google/re2/wiki/Syntax
- **语义搜索最佳实践**: 使用业务领域术语而非通用编程词汇

## 🤝 贡献

如果您发现更好的使用模式或有改进建议，欢迎提交 PR 或创建 Issue。

## 📄 许可

本示例项目遵循 MIT 许可协议。
