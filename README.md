<<<<<<< Local
# 字符反转程序
=======
# 简单计算器程序
>>>>>>> Remote

## 项目简介

<<<<<<< Local
这是一个用Java实现的字符反转程序，能够接收用户输入的字符串并将其反转后输出。程序支持英文、中文、数字、特殊字符等多种字符类型的反转。
=======
这是一个基于命令行的简单计算器程序,支持基础四则运算(加、减、乘、除),提供友好的交互式计算服务。
>>>>>>> Remote

## 功能特性

<<<<<<< Local
- ✅ 支持英文字母（大小写）反转
- ✅ 支持中文字符反转
- ✅ 支持数字反转
- ✅ 支持特殊符号反转
- ✅ 支持包含空格的字符串反转
- ✅ 友好的用户交互界面
- ✅ 完善的异常处理机制
- ✅ 全面的单元测试覆盖

## 技术栈

- **语言**: Java 8
- **构建工具**: Maven
- **测试框架**: JUnit 5
- **编码格式**: UTF-8

=======
- ✅ 支持加法、减法、乘法、除法运算
- ✅ 支持小数运算
- ✅ 命令行交互界面
- ✅ 输入验证和异常处理
- ✅ 除零检测
- ✅ 连续计算功能
- ✅ 完整的单元测试

## 技术要求

- Java 8 或更高版本
- Maven 3.x (可选,用于构建和测试)

>>>>>>> Remote
## 项目结构

```
simple-calculator/
├── src/
<<<<<<< Local
│   ├── main/java/com/square/
│   │   └── StringReversal.java          # 主程序文件
│   └── test/java/com/square/
│       └── StringReversalTest.java      # 单元测试文件
├── run_string_reversal.sh                # 快速运行脚本
├── run_tests.sh                          # 测试运行脚本
└── README.md                             # 项目说明文档
=======
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── square/
│   │               └── calculator/
│   │                   ├── Calculator.java          # 计算器核心类
│   │                   └── SimpleCalculator.java    # 主程序
│   └── test/
│       └── java/
│           └── com/
│               └── square/
│                   └── calculator/
│                       └── CalculatorTest.java      # 单元测试
├── pom.xml                                          # Maven配置文件
└── README.md                                        # 项目说明
>>>>>>> Remote
```

<<<<<<< Local
## 快速开始
=======
## 编译和运行
>>>>>>> Remote

<<<<<<< Local
### 方式一：使用Maven运行
=======
### 方法一: 使用 Maven
>>>>>>> Remote

<<<<<<< Local
```bash
# 编译项目
mvn compile
=======
#### 1. 编译项目
```bash
mvn clean compile
```
>>>>>>> Remote

<<<<<<< Local
# 运行程序
mvn exec:java -Dexec.mainClass="com.square.StringReversal"
=======
#### 2. 运行测试
```bash
mvn test
```
>>>>>>> Remote

<<<<<<< Local
# 运行测试
mvn test
```
=======
#### 3. 打包项目
```bash
mvn package
```
>>>>>>> Remote

<<<<<<< Local
### 方式二：使用脚本运行
=======
#### 4. 运行程序
```bash
mvn exec:java
```
>>>>>>> Remote

<<<<<<< Local
```bash
# 添加执行权限
chmod +x run_string_reversal.sh
chmod +x run_tests.sh
=======
或者运行打包后的 JAR 文件:
```bash
java -jar target/simple-calculator-1.0.0.jar
```
>>>>>>> Remote

<<<<<<< Local
# 运行程序
./run_string_reversal.sh
=======
### 方法二: 使用 javac 和 java
>>>>>>> Remote

<<<<<<< Local
# 运行测试
./run_tests.sh
```
=======
#### 1. 编译源代码
```bash
# 创建输出目录
mkdir -p target/classes
>>>>>>> Remote

<<<<<<< Local
### 方式三：手动编译运行
=======
# 编译代码
javac -d target/classes -sourcepath src/main/java src/main/java/com/square/calculator/*.java
```
>>>>>>> Remote

<<<<<<< Local
```bash
# 创建输出目录
mkdir -p target/classes
=======
#### 2. 运行程序
```bash
java -cp target/classes com.square.calculator.SimpleCalculator
```
>>>>>>> Remote

<<<<<<< Local
# 编译
javac -encoding UTF-8 -d target/classes src/main/java/com/square/StringReversal.java
=======
#### 3. 编译和运行测试(需要 JUnit 5)
```bash
# 下载 JUnit 5 依赖
# 或使用 Maven 运行测试: mvn test
```
>>>>>>> Remote

<<<<<<< Local
# 运行
java -cp target/classes com.square.StringReversal
```
=======
## 使用说明
>>>>>>> Remote

<<<<<<< Local
## 使用示例
=======
1. 启动程序后,会显示欢迎信息和操作菜单
2. 选择运算类型:
   - 1: 加法 (+)
   - 2: 减法 (-)
   - 3: 乘法 (×)
   - 4: 除法 (÷)
   - 0: 退出
3. 输入第一个数字
4. 输入第二个数字
5. 查看计算结果
6. 选择是否继续计算
>>>>>>> Remote

<<<<<<< Local
### 示例1：反转英文字符串

=======
## 使用示例

>>>>>>> Remote
```
<<<<<<< Local
=== 字符反转程序 ===
请输入要反转的字符串：
Hello World
=======
=================================
      欢迎使用简单计算器
=================================
>>>>>>> Remote

<<<<<<< Local
原始字符串：Hello World
反转后字符串：dlroW olleH
```
=======
请选择运算类型:
1. 加法 (+)
2. 减法 (-)
3. 乘法 (×)
4. 除法 (÷)
0. 退出
请输入选项 (0-4): 1
请输入第一个数字: 10.5
请输入第二个数字: 5.3
>>>>>>> Remote

<<<<<<< Local
### 示例2：反转中文字符串

=======
计算结果:
10.50 + 5.30 = 15.80

是否继续计算? (y/n): n
感谢使用简单计算器!再见!
>>>>>>> Remote
```
=== 字符反转程序 ===
请输入要反转的字符串：
你好世界

<<<<<<< Local
原始字符串：你好世界
反转后字符串：界世好你
```
=======
## 异常处理
>>>>>>> Remote

<<<<<<< Local
### 示例3：反转混合字符串
=======
程序包含完善的异常处理机制:
>>>>>>> Remote

<<<<<<< Local
```
=== 字符反转程序 ===
请输入要反转的字符串：
Hello你好123
=======
- **除零错误**: 当除法运算的除数为零时,会提示"除数不能为零"
- **非法输入**: 当输入的不是有效数字时,会提示"输入格式不正确,请输入有效数字"
- **无效运算符**: 当选择的运算类型不在 0-4 范围内时,会提示"无效的运算类型,请重新选择"
>>>>>>> Remote

<<<<<<< Local
原始字符串：Hello你好123
反转后字符串：321好你olleH
```
=======
## 测试覆盖
>>>>>>> Remote

<<<<<<< Local
## API说明
=======
项目包含完整的单元测试,覆盖以下场景:
>>>>>>> Remote

<<<<<<< Local
### reverseString方法
=======
### 加法测试
- 正数相加
- 负数相加
- 正数和负数相加
- 小数相加
>>>>>>> Remote

<<<<<<< Local
```java
public static String reverseString(String input)
```
=======
### 减法测试
- 正数相减
- 负数相减
- 小数相减
>>>>>>> Remote

<<<<<<< Local
**功能**: 反转输入的字符串
=======
### 乘法测试
- 正数相乘
- 负数相乘
- 乘以零
- 小数相乘
>>>>>>> Remote

<<<<<<< Local
**参数**: 
- `input` - 待反转的字符串
=======
### 除法测试
- 正数相除
- 负数相除
- 小数相除
- 除以零异常
- 零除以非零数
>>>>>>> Remote

<<<<<<< Local
**返回值**: 反转后的字符串
=======
### 边界测试
- 极大值运算
- 极小值运算
>>>>>>> Remote

<<<<<<< Local
**异常**: 
- `IllegalArgumentException` - 当输入为null时抛出
=======
## 核心类说明
>>>>>>> Remote

<<<<<<< Local
**示例**:
```java
String result = StringReversal.reverseString("Hello");
// result = "olleH"
```
=======
### Calculator.java
计算器核心类,提供四则运算方法:
- `add(double, double)`: 加法运算
- `subtract(double, double)`: 减法运算
- `multiply(double, double)`: 乘法运算
- `divide(double, double)`: 除法运算,包含除零检测
>>>>>>> Remote

<<<<<<< Local
## 测试用例
=======
### SimpleCalculator.java
主程序类,负责:
- 用户交互界面
- 输入验证
- 异常处理
- 程序流程控制
>>>>>>> Remote

<<<<<<< Local
项目包含11个全面的单元测试用例，覆盖以下场景：
=======
## 设计特点
>>>>>>> Remote

<<<<<<< Local
| 测试场景 | 输入示例 | 预期输出 |
|---------|---------|---------|
| 英文字符串 | "Hello" | "olleH" |
| 中文字符串 | "你好世界" | "界世好你" |
| 数字字符串 | "12345" | "54321" |
| 混合字符串 | "Hello你好123" | "321好你olleH" |
| 包含空格 | "A B C" | "C B A" |
| 单字符 | "A" | "A" |
| 空字符串 | "" | "" |
| null输入 | null | IllegalArgumentException |
| 特殊字符 | "!@#$%^&*()" | ")(*&^%$#@!" |
| 长字符串 | "The quick brown fox..." | "...xof nworb kciuq ehT" |
| 回文字符串 | "noon" | "noon" |
=======
1. **模块化设计**: 计算逻辑与交互逻辑分离
2. **健壮性**: 完善的输入验证和异常处理
3. **易用性**: 清晰的提示信息和友好的用户界面
4. **可测试性**: 核心逻辑独立,便于单元测试
5. **可扩展性**: 易于添加新的运算类型
>>>>>>> Remote

<<<<<<< Local
## 算法说明
=======
## 版本信息
>>>>>>> Remote

<<<<<<< Local
程序使用`StringBuilder`的`reverse()`方法实现字符串反转，具有以下优势：
=======
- 版本: 1.0.0
- 作者: 系统生成
- 许可: MIT License
>>>>>>> Remote

<<<<<<< Local
- **时间复杂度**: O(n)，其中n是字符串长度
- **空间复杂度**: O(n)，需要创建新的字符串对象
- **性能**: 利用Java内置优化，性能优秀
- **简洁性**: 代码简洁易懂，可维护性强
=======
## 后续改进方向
>>>>>>> Remote

<<<<<<< Local
## 边界处理
=======
- [ ] 支持表达式解析(如: 2 + 3 * 4)
- [ ] 添加计算历史记录功能
- [ ] 支持科学计算功能
- [ ] 提供图形用户界面(GUI)版本
- [ ] 添加内存存储功能(M+, M-, MR等)
>>>>>>> Remote

<<<<<<< Local
- ✅ **空字符串**: 返回空字符串
- ✅ **null输入**: 抛出IllegalArgumentException异常
- ✅ **单字符**: 返回原字符串
- ✅ **超长字符串**: 正常处理，无长度限制

## 开发规范

- 使用Java 8标准
- 遵循Google Java代码风格
- 完整的JavaDoc注释
- 完善的单元测试覆盖
- UTF-8编码格式

## 扩展建议

未来可以考虑以下扩展功能：

1. **循环运行模式**: 支持连续反转多个字符串，直到用户选择退出
2. **批量处理**: 一次输入多个字符串，批量反转
3. **文件处理**: 从文件读取字符串并将结果写入文件
4. **性能统计**: 显示反转操作的耗时信息
5. **算法选择**: 提供多种反转算法供用户选择
6. **单词级反转**: 支持仅反转单词顺序，保持单词内字符顺序不变

## 许可证

本项目采用MIT许可证。

## 作者

Square Team

## 版本历史

- **v1.0.0** (2025-10-29): 初始版本
  - 实现基本字符串反转功能
  - 添加用户交互界面
  - 完善单元测试覆盖

=======
>>>>>>> Remote