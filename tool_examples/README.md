# 🛠️ 代码分析工具调用示例集

## 📖 简介

这是一个完整的代码分析工具调用示例项目，展示了 **search_codebase**、**search_file**、**grep_code** 和 **search_teamdocs** 四种核心工具的实际应用场景和最佳实践。

## 🎯 项目目标

- 提供 21 个实际使用场景的完整示例
- 演示 3 种工具组合使用策略
- 帮助开发者快速掌握代码分析工具的使用方法
- 提供可执行的参考实现

## 📦 项目内容

| 文件名 | 描述 | 行数 |
|--------|------|------|
| `01_search_codebase_examples.sh` | search_codebase 工具的 5 个示例 | 104 |
| `02_search_file_examples.sh` | search_file 工具的 6 个示例 | 107 |
| `03_grep_code_examples.sh` | grep_code 工具的 7 个示例 | 129 |
| `04_combined_strategies.sh` | 3 种工具组合使用策略 | 194 |
| `run_all_examples.sh` | 一键执行所有示例 | 80 |
| `TOOL_USAGE_GUIDE.md` | 详细使用指南 | 262 |
| `IMPLEMENTATION_SUMMARY.md` | 项目实施总结 | 228 |

**总计**: 1,104 行代码和文档

## 🚀 快速开始

### 方法一：执行所有示例

```bash
cd tool_examples
./run_all_examples.sh
```

### 方法二：单独执行某个示例

```bash
# 查看 search_codebase 示例
./01_search_codebase_examples.sh

# 查看 search_file 示例
./02_search_file_examples.sh

# 查看 grep_code 示例
./03_grep_code_examples.sh

# 查看工具组合策略
./04_combined_strategies.sh
```

## 📚 工具概览

### 🔍 search_codebase
**用途**: 语义搜索代码功能

**适用场景**:
- 查找认证逻辑
- 搜索数据库操作
- 定位签到功能
- 查找错误处理
- 搜索 API 路由

**示例数量**: 5 个

---

### 📁 search_file
**用途**: 使用 glob 模式查找文件

**适用场景**:
- 查找 Go 主程序
- 查找测试文件
- 查找配置文件
- 查找 Shell 脚本
- 查找 Java Controller
- 查找 Markdown 文档

**示例数量**: 6 个

---

### 🔎 grep_code
**用途**: 使用正则表达式精确匹配代码

**适用场景**:
- 查找 TODO 注释
- 查找日志语句
- 查找 HTTP 端点
- 查找 SQL 查询
- 查找环境变量
- 查找错误处理模式
- 查找依赖注入注解

**示例数量**: 7 个

---

### 📖 search_teamdocs
**用途**: 搜索团队知识库文档

**适用场景**:
- 部署文档
- API 设计规范
- 数据库设计文档
- 安全相关文档
- 测试策略文档
- 性能优化文档

**示例数量**: 6 个（包含在组合策略中）

## 🎓 工具组合策略

### 策略一：自顶向下分析
1. search_teamdocs → 了解系统整体设计
2. search_codebase → 定位核心模块
3. search_file → 找到具体文件
4. grep_code → 提取关键代码片段

### 策略二：问题定位
1. grep_code → 查找错误特征码
2. search_codebase → 理解相关业务逻辑
3. search_file → 定位配置文件
4. search_teamdocs → 查找故障排查文档

### 策略三：功能扩展
1. search_teamdocs → 查找设计规范
2. search_codebase → 找到类似功能实现
3. search_file → 定位需要修改的文件
4. grep_code → 检查代码规范一致性

## 📖 文档说明

### TOOL_USAGE_GUIDE.md
详细的使用指南，包括：
- 每个工具的详细说明
- 适用场景分析
- 参数配置方法
- 最佳实践建议
- 工具选择决策树
- 实际应用示例

### IMPLEMENTATION_SUMMARY.md
项目实施总结，包括：
- 完成的任务清单
- 项目结构说明
- 设计文档覆盖率
- 核心亮点介绍
- 统计数据分析
- 验证结果

## 💡 使用技巧

### search_codebase 最佳实践
- 关键词聚焦业务领域术语
- 使用 search_scope 缩小搜索范围
- 最多提取三个最相关的关键词

### search_file 最佳实践
- 仅支持 glob 模式，不支持正则
- 使用 `**` 进行递归目录匹配
- 结果限制为 25 个，需确保模式足够具体

### grep_code 最佳实践
- 使用 RE2 正则表达式语法
- 通过 include_pattern 过滤文件类型
- 结果上限为 25 条匹配

### search_teamdocs 最佳实践
- 查询应包含多个相关关键词
- 关注概念、流程和设计决策类信息
- 适合探索性查询和知识发现

## 🔧 技术要求

- **操作系统**: Linux / macOS / Windows (WSL)
- **Shell**: bash 4.0+
- **权限**: 需要执行权限（自动设置）

## 📊 项目统计

- **脚本文件**: 5 个
- **文档文件**: 3 个
- **代码行数**: 1,104 行
- **示例场景**: 21 个
- **组合策略**: 3 个
- **工具类型**: 4 种
- **覆盖率**: 100%

## 🎯 适用对象

- **初学者**: 学习代码分析工具的使用
- **中级开发者**: 掌握工具组合策略
- **高级开发者**: 参考最佳实践和优化技巧
- **团队负责人**: 制定代码分析规范

## 📝 示例输出预览

```
==========================================
示例一：搜索认证逻辑
==========================================
使用场景: 需要了解系统如何处理用户认证和授权机制

调用参数:
  query: 'authentication authorization user login security'
  key_words: 'authentication,authorization,login'
  search_scope: 'server/'

预期结果: 返回与认证相关的中间件、处理器和服务层代码
```

## 🤝 贡献

如果您有更好的使用模式或改进建议：
1. Fork 本项目
2. 创建特性分支
3. 提交您的改进
4. 创建 Pull Request

## ⚠️ 注意事项

- 所有脚本仅展示工具调用示例，实际调用已注释
- 结果限制为 25 条，需合理设计查询参数
- 建议结合实际项目进行学习和实践

## 📞 支持

如有问题或建议，请：
- 查看 `TOOL_USAGE_GUIDE.md` 获取详细说明
- 查看 `IMPLEMENTATION_SUMMARY.md` 了解项目详情
- 提交 Issue 或 PR

## 📄 许可

MIT License

---

**项目状态**: ✅ 已完成

**版本**: 1.0.0

**最后更新**: 2025-10-31

**Happy Coding! 🎉**
