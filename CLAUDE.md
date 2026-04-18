# 🚀 VibeHub Project Protocol (V.P.P.)

## 🌐 语言与文档规范 (Language & Docs Strategy)
- **沟通与文档 (Chinese)**：所有对话、逻辑探讨、以及 `/docs` 目录下的所有说明文档（PRD, Design, ADR 等）必须使用【中文】。
- **工程产物 (English)**：代码 (Code)、代码注释 (Comments)、Git Commit Message 必须使用【专业英文】。
- **存储路径**：
  - 所有规范化文档统一产出至项目根目录的 `/docs` 文件夹下。
  - 项目 codebase 位于 `/submission/filesys` 目录下。

## ⚙️ 核心研发环：Vibe-Engineering Loop (VEL)
在开发任何功能模块前，必须严格遵守以下六步循环。

### 0. Phase 0: Cognitive Alignment (认知对齐)
- **目标**：通过高强度拷问，消除 AI 与开发者之间的认知偏差。
- **工具**：调用 `grill-me` 技能。
- **动作**：AI 针对业务逻辑、Java 实现选型及潜在风险发起挑战。Camii 回答并达成共识后进入下一步。

### 1. Phase 1: Anchor (需求锚定)
- **目标**：将共识转化为可执行的需求定义。
- **工具**：调用 `write-a-prd` 技能。
- **产出**：`/docs/prd/` 目录下的【中文】PRD 文档。

### 2. Phase 2: Contract (契约设计)
- **目标**：定义术语统一性、API 契约与数据库结构。
- **工具**：先调用 `ubiquitous-language`，再调用 `design-an-interface`。
- **产出**：
    1. 更新本文件下方的【统一语言表】。
    2. `/docs/design/` 目录下的【中文】设计文档（含 OpenAPI Spec 与 DB Schema）。

### 3. Phase 3: Implementation (规范实现)
- **目标**：TDD 驱动，高质量交付。
- **工具**：调用 `tdd` 技能。
- **要求**：先编写英文 JUnit 测试 -> 运行失败 -> 编写 Java 代码 -> 测试通过。

### 4. Phase 4: Refinement (代码精进)
- **目标**：优化架构，记录决策。
- **工具**：调用 `improve-codebase-architecture` 技能。
- **产出**：如有重大架构变更，记录【中文】ADR 至 `/docs/adr/`。

### 5. Phase 5: Delivery (交付护栏)
- **工具**：调用 `git-guardrails-claude-code` 技能。
- **要求**：生成符合 Angular 规范的【英文】Commit Message。
