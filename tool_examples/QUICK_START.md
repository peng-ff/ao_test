# ⚡ 快速开始指南

## 🎯 5 秒开始

```bash
cd /data/workspace/ao_test/tool_examples
./run_all_examples.sh
```

## 📚 单个示例执行

### 1️⃣ search_codebase 示例

```bash
./01_search_codebase_examples.sh
```

**包含示例**:
- 搜索认证逻辑
- 搜索数据库操作
- 搜索签到功能
- 搜索错误处理
- 搜索 API 路由

---

### 2️⃣ search_file 示例

```bash
./02_search_file_examples.sh
```

**包含示例**:
- 查找 Go 主程序
- 查找测试文件
- 查找配置文件
- 查找 Shell 脚本
- 查找 Java Controller
- 查找 Markdown 文档

---

### 3️⃣ grep_code 示例

```bash
./03_grep_code_examples.sh
```

**包含示例**:
- 查找 TODO 注释
- 查找日志语句
- 查找 HTTP 端点
- 查找 SQL 查询
- 查找环境变量
- 查找错误处理模式
- 查找依赖注入注解

---

### 4️⃣ 工具组合策略

```bash
./04_combined_strategies.sh
```

**包含策略**:
- 自顶向下分析
- 问题定位
- 功能扩展

---

## 📖 文档阅读

### 主文档

```bash
cat README.md
```

### 使用指南

```bash
cat TOOL_USAGE_GUIDE.md
```

### 实施总结

```bash
cat IMPLEMENTATION_SUMMARY.md
```

---

## 🔧 常用命令

### 检查文件列表

```bash
ls -lh
```

### 统计代码行数

```bash
wc -l *.sh *.md
```

### 搜索特定内容

```bash
grep -r "search_codebase" .
```

---

## 💡 使用技巧

### 查看某个工具的所有示例

```bash
grep -A 10 "示例" 01_search_codebase_examples.sh
```

### 提取所有正则表达式

```bash
grep "正则表达式:" 03_grep_code_examples.sh
```

### 查看工具选择指南

```bash
grep -A 20 "工具选择决策指南" 04_combined_strategies.sh
```

---

## 🎓 学习路径

### 初学者路径

1. 阅读 `README.md` 了解项目概况
2. 执行 `run_all_examples.sh` 看整体效果
3. 逐个执行单个示例脚本
4. 阅读 `TOOL_USAGE_GUIDE.md` 深入学习

### 进阶路径

1. 学习 `04_combined_strategies.sh` 中的组合策略
2. 阅读每个示例的参数配置
3. 尝试修改参数进行实验
4. 将学到的知识应用到实际项目

---

## 🚀 实战练习

### 练习 1: 查找特定功能

尝试使用 search_codebase 查找项目中的某个功能：

```bash
# 示例：查找日志记录功能
# search_codebase({
#   "query": "logging logger log record",
#   "key_words": "logging,logger,record"
# })
```

### 练习 2: 文件模式匹配

使用 search_file 查找特定类型的文件：

```bash
# 示例：查找所有 Python 文件
# search_file({"query": "**/*.py"})
```

### 练习 3: 代码模式提取

使用 grep_code 提取特定的代码模式：

```bash
# 示例：查找所有函数定义
# grep_code({
#   "regex": "func\\s+\\w+\\s*\\(",
#   "include_pattern": "*.go"
# })
```

---

## 📞 获取帮助

### 命令行帮助

```bash
# 查看脚本内容
cat 01_search_codebase_examples.sh

# 查看文档
less TOOL_USAGE_GUIDE.md
```

### 文档索引

- **项目概述**: `README.md`
- **详细指南**: `TOOL_USAGE_GUIDE.md`
- **实施总结**: `IMPLEMENTATION_SUMMARY.md`
- **快速开始**: `QUICK_START.md` (本文档)

---

## ✅ 检查清单

完成以下步骤，确保你已经掌握了基本用法：

- [ ] 执行了 `run_all_examples.sh`
- [ ] 查看了所有 4 个工具的示例
- [ ] 阅读了 README.md
- [ ] 理解了工具选择决策
- [ ] 学习了至少一个组合策略
- [ ] 尝试了修改示例参数

---

**Happy Learning! 🎉**

**下一步**: 阅读 `TOOL_USAGE_GUIDE.md` 获取详细的使用指导
