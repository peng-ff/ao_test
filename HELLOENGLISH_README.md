# HelloEnglish 程序

一个简洁的英文问候程序，展示英文书本图案和英文问候语。

## 📋 功能特性

- 📚 显示英文书本ASCII艺术图案
- 💬 随机问候语（多条英文问候）
- 🏷️ 支持自定义用户名字
- ✨ 精美的控制台输出效果

## 🚀 快速开始

### 方式一：使用启动脚本（推荐）

```bash
# 给脚本添加执行权限
chmod +x run_hello_english.sh

# 默认运行
./run_hello_english.sh

# 带名字参数运行
./run_hello_english.sh John
```

### 方式二：直接运行

```bash
# 编译程序
javac HelloEnglish.java

# 运行程序
java HelloEnglish

# 带参数运行
java HelloEnglish John
```

## 📁 文件说明

- `HelloEnglish.java` - Java 主程序文件
- `run_hello_english.sh` - 启动脚本
- `HELLOENGLISH_README.md` - 本文档

## 🎨 程序输出示例

### 无参数运行

```
╔════════════════════════════════╗
║   欢迎来到 HelloEnglish 程序   ║
╚════════════════════════════════╝

    ___________
   |  ENGLISH  |
   |  ~~~~~~~~ |
   |           |
   |   HELLO   |
   |___________|

Hello, English! Welcome to the world of learning! 📚

提示: 运行时可以传入名字作为参数
例如: java HelloEnglish John

════════════════════════════════
```

### 带参数运行

```
╔════════════════════════════════╗
║   欢迎来到 HelloEnglish 程序   ║
╚════════════════════════════════╝

┌────────────────────────────────┐
│  Hello, John!  │
└────────────────────────────────┘
    ___________
   |  ENGLISH  |
   |  ~~~~~~~~ |
   |           |
   |   HELLO   |
   |___________|

Welcome to English World! 🌍

════════════════════════════════
```

## 🔧 环境要求

- **Java**: JDK 8 或更高版本

## 💡 使用建议

1. 首次运行建议使用启动脚本，自动完成编译和运行
2. 可以传入不同的名字来个性化问候
3. 程序会随机显示不同的英文问候语，每次运行可能不同

## 🎯 设计思路

该程序设计遵循项目现有Hello系列程序风格：

- 参考了 `HelloDog.java` 和 `HelloMan.java` 的简洁结构
- 使用装饰性边框增强视觉效果
- 支持命令行参数进行个性化问候
- 提供随机问候语增加趣味性
- 使用Unicode表情符号（📚、🌍、🎯、👋）

## 📝 代码结构

程序包含以下核心方法：

- `printEnglish()` - 打印ASCII艺术图案
- `getRandomGreeting()` - 随机获取一条问候语
- `greetEnglish(String name)` - 个性化问候
- `main(String[] args)` - 主入口方法

## 🤝 与现有项目的关系

HelloEnglish 程序作为 Hello 系列程序的补充：

- 与 HelloDog、HelloMan、HelloWoman 保持一致的代码风格
- 可作为项目的示例代码和学习材料
- 展示Java基础编程和命令行交互
- 适合作为新手入门示例

---

**祝您使用愉快！Hello English! 📚**
