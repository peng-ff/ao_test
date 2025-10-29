# 升序排序程序

## 项目简介

这是一个完整的Java升序排序程序，实现了五种经典的排序算法：

1. **冒泡排序 (Bubble Sort)** - 简单直观，适合小规模数据
2. **选择排序 (Selection Sort)** - 交换次数少
3. **插入排序 (Insertion Sort)** - 对部分有序数据效率高
4. **快速排序 (Quick Sort)** - 平均性能最优
5. **归并排序 (Merge Sort)** - 稳定且性能稳定

## 项目结构

```
.
├── src/
│   ├── main/java/com/square/
│   │   └── SortProgram.java          # 主程序(包含所有排序算法)
│   └── test/java/com/square/
│       └── SortProgramTest.java      # 测试类
├── pom.xml                            # Maven配置文件
├── compile_and_run.sh                 # 编译运行脚本
├── run_tests.sh                       # 测试运行脚本
└── SORT_README.md                     # 本文档
```

## 功能特性

### 1. 多种排序算法
- 提供5种经典排序算法实现
- 每种算法都有详细注释和时间/空间复杂度说明
- 支持用户交互式选择排序方法

### 2. 交互式使用
```
=== 升序排列程序 ===
请输入要排序的数字个数: 5
请输入 5 个整数(用空格分隔): 64 34 25 12 22

原始数组: [64, 34, 25, 12, 22]

请选择排序算法:
1. 冒泡排序
2. 选择排序
3. 插入排序
4. 快速排序
5. 归并排序
请输入选项(1-5): 4

使用快速排序:
排序后数组: [12, 22, 25, 34, 64]

排序耗时: 0.123 毫秒
```

### 3. 完整测试套件
- 单元测试覆盖所有排序算法
- 边界条件测试(空数组、单元素、已排序等)
- 算法一致性验证
- 性能对比测试

## 使用方法

### 方式一: 使用脚本运行

```bash
# 1. 赋予脚本执行权限
chmod +x compile_and_run.sh

# 2. 运行程序
./compile_and_run.sh
```

### 方式二: 使用Maven

```bash
# 1. 编译项目
mvn clean compile

# 2. 运行程序
mvn exec:java -Dexec.mainClass="com.square.SortProgram"

# 3. 运行测试
mvn test
```

### 方式三: 使用javac直接编译

```bash
# 1. 创建输出目录
mkdir -p build/classes

# 2. 编译
javac -d build/classes src/main/java/com/square/SortProgram.java

# 3. 运行
java -cp build/classes com.square.SortProgram
```

## 运行测试

### 使用测试脚本

```bash
# 1. 赋予脚本执行权限
chmod +x run_tests.sh

# 2. 运行测试
./run_tests.sh
```

### 使用Maven

```bash
mvn test
```

## 算法复杂度对比

| 算法 | 时间复杂度(平均) | 时间复杂度(最坏) | 空间复杂度 | 稳定性 |
|------|-----------------|-----------------|-----------|--------|
| 冒泡排序 | O(n²) | O(n²) | O(1) | 稳定 |
| 选择排序 | O(n²) | O(n²) | O(1) | 不稳定 |
| 插入排序 | O(n²) | O(n²) | O(1) | 稳定 |
| 快速排序 | O(n log n) | O(n²) | O(log n) | 不稳定 |
| 归并排序 | O(n log n) | O(n log n) | O(n) | 稳定 |

## 代码示例

### 使用冒泡排序

```java
int[] arr = {64, 34, 25, 12, 22};
SortProgram.bubbleSort(arr);
SortProgram.printArray(arr);
// 输出: [12, 22, 25, 34, 64]
```

### 使用快速排序

```java
int[] arr = {10, 7, 8, 9, 1, 5};
SortProgram.quickSort(arr);
SortProgram.printArray(arr);
// 输出: [1, 5, 7, 8, 9, 10]
```

### 使用归并排序

```java
int[] arr = {38, 27, 43, 3, 9, 82, 10};
SortProgram.mergeSort(arr);
SortProgram.printArray(arr);
// 输出: [3, 9, 10, 27, 38, 43, 82]
```

## 性能测试结果

在1000个随机数的测试中（仅供参考）:

- 冒泡排序: ~15-20 ms
- 选择排序: ~8-12 ms
- 插入排序: ~5-10 ms
- 快速排序: ~0.5-1 ms
- 归并排序: ~0.8-1.5 ms

**结论**: 对于大规模数据，推荐使用快速排序或归并排序。

## 常见问题

### Q1: 如何选择合适的排序算法？
- **数据量小(<50)**: 插入排序
- **数据量大**: 快速排序或归并排序
- **需要稳定性**: 归并排序或冒泡排序
- **内存受限**: 选择排序或插入排序(O(1)空间)

### Q2: 程序支持哪些输入？
- 支持正整数、负整数、零
- 支持任意长度的数组
- 支持包含重复元素的数组

### Q3: 如何验证排序正确性？
运行测试套件：`./run_tests.sh` 或 `mvn test`

## 技术栈

- Java 8+
- JUnit 4.13.2 (测试框架)
- Maven 3.x (可选)

## 作者

Square Team

## 许可证

MIT License

---

**提示**: 这个程序设计为教学和学习用途，展示了各种排序算法的实现和性能对比。
