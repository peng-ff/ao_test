# 圆锥体体积计算程序

## 功能描述

这是一个用Java编写的圆锥体体积计算程序,提供以下功能:

1. **计算圆锥体体积** - 根据底面半径和高度计算体积
2. **计算底面积** - 计算圆锥体的底面积
3. **计算侧面积** - 计算圆锥体的侧面积  
4. **计算表面积** - 计算圆锥体的总表面积
5. **交互式界面** - 提供友好的用户交互界面

## 文件结构

```
src/main/java/com/square/
├── Cone.java                    # 圆锥体类,封装属性和计算方法
└── ConeVolumeCalculator.java   # 主程序类,提供交互式界面

src/test/java/com/square/
└── ConeTest.java                # 单元测试类
```

## 核心公式

- **体积公式**: V = (1/3) × π × r² × h
  - r: 底面半径
  - h: 高度

- **底面积公式**: S = π × r²

- **侧面积公式**: S = π × r × l
  - l: 母线长度 = √(r² + h²)

- **表面积公式**: S = 底面积 + 侧面积

## 使用方法

### 编译程序

```bash
# 创建目标目录
mkdir -p target/classes

# 编译源代码
javac -d target/classes -encoding UTF-8 \
    src/main/java/com/square/Cone.java \
    src/main/java/com/square/ConeVolumeCalculator.java
```

### 运行程序

```bash
# 运行交互式界面
java -cp target/classes com.square.ConeVolumeCalculator
```

### 使用示例

程序运行后,按照提示输入半径和高度:

```
=================================
   圆锥体体积计算程序
=================================

请输入圆锥体底面半径 (r): 5
请输入圆锥体高度 (h): 10

--- 计算结果 ---
底面半径: 5.00
高度: 10.00
底面积: 78.54
侧面积: 175.62
表面积: 254.16
体积: 261.80
----------------

是否继续计算? (y/n): n

感谢使用圆锥体体积计算程序!
```

## 代码示例

### 创建圆锥体对象

```java
// 创建一个底面半径为5,高度为10的圆锥体
Cone cone = new Cone(5.0, 10.0);

// 计算体积
double volume = cone.calculateVolume();
System.out.println("体积: " + volume);

// 计算表面积
double surfaceArea = cone.calculateSurfaceArea();
System.out.println("表面积: " + surfaceArea);
```

### 使用静态方法

```java
// 直接计算体积
double volume = ConeVolumeCalculator.calculateVolume(5.0, 10.0);
System.out.println("体积: " + volume);
```

## 特性

1. **输入验证** - 自动验证输入数据的有效性(半径和高度必须大于0)
2. **异常处理** - 提供完善的异常处理机制
3. **精确计算** - 使用 Math.PI 保证计算精度
4. **面向对象设计** - 采用OOP设计模式,代码结构清晰
5. **完整的单元测试** - 提供全面的测试用例

## 单元测试

```bash
# 运行测试(需要Maven或Gradle)
mvn test -Dtest=ConeTest

# 或使用Gradle
gradle test --tests ConeTest
```

测试覆盖:
- ✓ 构造函数测试
- ✓ 参数验证测试
- ✓ 体积计算测试
- ✓ 面积计算测试
- ✓ Getter/Setter测试
- ✓ 边界条件测试

## 计算示例

| 半径 | 高度 | 体积 | 表面积 |
|------|------|-------|--------|
| 3.0  | 4.0  | 37.70 | 75.40  |
| 5.0  | 10.0 | 261.80| 254.16 |
| 8.5  | 12.3 | 933.05| 741.25 |

## 注意事项

1. 半径和高度必须为正数
2. 计算结果保留两位小数
3. 使用 Math.PI 提供高精度的π值
4. 支持浮点数输入

## 技术栈

- Java SE (标准版)
- JUnit 5 (单元测试框架)
- 面向对象编程 (OOP)

## 作者信息

这是一个教学示例程序,演示如何使用Java进行几何体积计算。
