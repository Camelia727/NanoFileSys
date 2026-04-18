# 微型内存文件系统 - 迭代一实现计划

## Context

**项目背景**：2026 Spring 软件架构设计课程实验，需实现命令行微型内存文件系统。

**问题描述**：程序通过标准输入读取指令，在内存中构建文件树，输出执行结果。迭代一只需支持根目录、文件、目录三种节点类型，以及 MKDIR/TOUCH/LS/INFO 四条基础命令。

**迭代二预告**（已在设计中预留扩展点）：
- 路径规范化（`.` / `..` / 冗余 `/`）
- FIND 递归搜索
- RM 删除
- LINK 链接机制
- INFO 防环计算

---

## 设计决策（Phase 0 对齐结果）

| # | 问题 | 决策 |
|---|------|------|
| 1 | Node 抽象 | `abstract class Node` |
| 2 | SizeContext | 迭代一预留，简单空类 `class SizeContext {}` |
| 3 | 路径解析 | 迭代一实现 `PathUtil`，处理冗余 `/` |
| 4 | 子节点存储 | `TreeMap<String, Node>` |
| 5 | 包结构 | 分层结构 (node/ctx/util/cmd) |

---

## 核心模块设计

### 1. 节点抽象 (`com.vibehub.fs.node`)

```java
// Node.java - 抽象基类
abstract class Node {
    abstract NodeType type();          // FILE / DIRECTORY
    abstract String name();
    abstract long size(SizeContext ctx);
}

// FileNode.java
class FileNode extends Node {
    private final String name;
    private final long size;
    // constructor, getters
}

// DirectoryNode.java
class DirectoryNode extends Node {
    private final String name;
    private final TreeMap<String, Node> children = new TreeMap<>();
    // putChild, getChild, listChildren
}
```

### 2. 上下文 (`com.vibehub.fs.ctx`)

```java
// SizeContext.java - 迭代一空实现，迭代二扩展
class SizeContext {}
```

### 3. 工具类 (`com.vibehub.fs.util`)

```java
// PathUtil.java - 处理路径切分和冗余斜杠
class PathUtil {
    static List<String> segments(String absPath) {
        // 去除首 /，按 / 切分，过滤空串
        // 例: "/usr//local/" -> ["usr", "local"]
    }
}
```

### 4. 命令处理 (`com.vibehub.fs.cmd`)

```java
// Command.java - 命令接口
interface Command {
    void execute(String[] args, FileSystem fs);
}

// MkdirCommand / TouchCommand / LsCommand / InfoCommand
// 各实现 execute 方法
```

### 5. 文件系统 (`com.vibehub.fs`)

```java
// FileSystem.java
class FileSystem {
    final DirectoryNode root = new DirectoryNode("/", null);

    Node resolve(String absPath);        // 路径解析
    void executeCommand(String line);   // 命令分发
}
```

---

## 项目结构（Maven）

```
src/main/java/com/vibehub/fs/
├── node/
│   ├── Node.java
│   ├── NodeType.java
│   ├── FileNode.java
│   └── DirectoryNode.java
├── ctx/
│   └── SizeContext.java
├── util/
│   └── PathUtil.java
├── cmd/
│   ├── Command.java
│   ├── MkdirCommand.java
│   ├── TouchCommand.java
│   ├── LsCommand.java
│   └── InfoCommand.java
├── FileSystem.java
└── Main.java
```

---

## 关键文件修改清单

| 文件 | 操作 | 说明 |
|------|------|------|
| `pom.xml` | 新建 | Maven 配置 |
| `Node.java` | 新建 | 抽象基类 |
| `NodeType.java` | 新建 | 枚举 FILE/DIRECTORY |
| `FileNode.java` | 新建 | 文件节点实现 |
| `DirectoryNode.java` | 新建 | 目录节点实现，TreeMap 存储 |
| `SizeContext.java` | 新建 | 空实现预留 |
| `PathUtil.java` | 新建 | 路径工具，处理冗余 `/` |
| `Command.java` | 新建 | 命令接口 |
| `MkdirCommand.java` | 新建 | MKDIR 实现 |
| `TouchCommand.java` | 新建 | TOUCH 实现 |
| `LsCommand.java` | 新建 | LS 实现，字典序输出 |
| `InfoCommand.java` | 新建 | INFO 实现，递归计算 |
| `FileSystem.java` | 新建 | 主文件系统类 |
| `Main.java` | 新建 | 程序入口，读取 stdin |

---

## 实现步骤

### Step 1: 项目初始化
- 创建 Maven 项目结构
- 配置 `pom.xml`（Java 17）

### Step 2: 核心模型
- 实现 `Node` 抽象类和 `NodeType` 枚举
- 实现 `FileNode` 和 `DirectoryNode`
- 实现 `SizeContext` 空类

### Step 3: 工具层
- 实现 `PathUtil.segments()` - 路径切分+冗余斜杠处理

### Step 4: 命令层
- 实现 `Command` 接口
- 实现 `MkdirCommand` - 父目录不存在则忽略
- 实现 `TouchCommand` - 同名覆盖
- 实现 `LsCommand` - 字典序输出
- 实现 `InfoCommand` - 递归计算大小

### Step 5: 主程序
- 实现 `FileSystem.resolve()` - 路径解析
- 实现 `FileSystem.executeCommand()` - 命令分发
- 实现 `Main` - stdin 读取循环

### Step 6: 测试验证
- 使用 OJ 示例验证功能正确性

---

## 验证方案

1. **编译检查**：`mvn compile` 无错误
2. **示例测试** - 使用 OJ 格式输入：
   ```
   MKDIR /usr
   MKDIR /usr/local
   TOUCH /usr/local/test.txt 100
   TOUCH /readme.md 50
   LS /
   INFO /
   INFO /usr
   ```
   预期输出：
   ```
   readme.md
   usr
   150
   100
   ```
3. **边界测试**：
   - `MKDIR /a/b/c`（父目录不存在应忽略）
   - `TOUCH /same 10` 两次（覆盖测试）
   - `LS /file`（文件节点输出文件名）
   - 嵌套目录递归计算

---

## 迭代二扩展点（预留）

| 迭代一设计 | 迭代二扩展 |
|-----------|-----------|
| `abstract class Node` | 新增 `LinkNode extends Node` |
| `SizeContext {}` | 添加 `Set<NodeId> visited` 防环 |
| `PathUtil.segments()` | 增强 `normalize()` 处理 `.` / `..` |
| `DirectoryNode.children` | Link 节点复用同一命名空间 |

---

## 交付物清单

| 交付物 | 路径 | 状态 |
|--------|------|------|
| 需求文档 | `docs/detail.md` | ✅ 已有 |
| 实现计划 | `docs/plan.md` | ✅ 本文件 |
| 统一语言表 | `docs/ubiquitous-language.md` | ✅ 已生成 |
| 源代码 | `src/main/java/com/vibehub/fs/` | ⏳ 待实现 |

---

## 下一步

计划批准后，进入 **Phase 3: Implementation (TDD)**：
1. 初始化 Maven 项目
2. 按模块顺序实现：Node → FileNode/DirectoryNode → SizeContext → PathUtil → Commands → FileSystem → Main
