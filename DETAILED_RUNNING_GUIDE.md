# Summer Framework 详细运行指南

## 环境要求

- JDK 21 或更高版本（已验证 JDK 25 可用）
- Maven 3.6 或更高版本

## 构建步骤

### 1. 构建框架

1. 打开命令行终端，导航到项目根目录：
   ```bash
   cd d:\wwwroot\github\mode-review\summer-framework
   ```

2. 构建框架并安装到本地 Maven 仓库：
   ```bash
   mvn clean install -DskipTests
   ```

   这将构建所有模块并安装到本地 Maven 仓库。

### 2. 构建测试应用

1. 导航到测试应用目录：
   ```bash
   cd test-app
   ```

2. 编译测试应用：
   ```bash
   mvn clean compile
   ```

3. 运行测试应用：
   ```bash
   mvn exec:java -Dexec.mainClass="com.test.Application"
   ```

   应用将在 http://localhost:8080 启动。

## 可能遇到的问题及解决方案

### 1. Maven 命令不可用

**问题**：在终端中运行 `mvn` 命令时出现 "mvn 不是内部或外部命令" 错误。

**解决方案**：
- 确保 Maven 已正确安装
- 确保 Maven 的 bin 目录已添加到系统 PATH 环境变量中
- 或者使用完整路径运行 Maven，例如：
  ```bash
  "C:\Program Files\Apache Maven\bin\mvn" clean install -DskipTests
  ```

### 2. 依赖项缺失

**问题**：编译时出现 "找不到符号" 或 "程序包不存在" 错误。

**解决方案**：
- 确保已成功构建框架并安装到本地 Maven 仓库
- 检查 pom.xml 文件中的依赖项版本是否正确
- 运行 `mvn dependency:tree` 检查依赖项解析情况

### 3. 端口被占用

**问题**：启动应用时出现 "端口已被占用" 错误。

**解决方案**：
- 修改 `application.yml` 或 `application.properties` 文件中的端口配置：
  ```yaml
  server:
    port: 8081  # 使用不同的端口
  ```
- 或者停止占用端口的其他应用

### 4. 模板文件未找到

**问题**：访问 `/hello` 路径时出现 "模板文件未找到" 错误。

**解决方案**：
- 确保 `src/main/resources/WEB-INF/templates` 目录存在
- 确保 `hello.html` 文件已正确创建
- 检查 `SummerApplication.run()` 方法中的 `webDir` 参数是否正确

### 5. 注解扫描问题

**问题**：控制器未被扫描到，访问路径返回 404 错误。

**解决方案**：
- 确保 `AppConfig.java` 中的 `@ComponentScan` 注解指定了正确的包路径
- 检查控制器类是否使用了 `@Controller` 注解
- 检查方法是否使用了正确的 `@GetMapping` 或其他请求映射注解

## 测试应用功能

### 1. HTML 页面

访问 http://localhost:8080/hello 查看 HTML 页面，默认显示 "Hello, World!"

访问 http://localhost:8080/hello?name=Summer 查看带参数的 HTML 页面，显示 "Hello, Summer!"

### 2. JSON API

访问 http://localhost:8080/api/hello 查看 JSON 响应：
```json
{"message": "Hello, World!"}
```

访问 http://localhost:8080/api/hello?name=Summer 查看带参数的 JSON 响应：
```json
{"message": "Hello, Summer!"}
```

## 中间件配置

### 1. Tomcat 配置

Tomcat 是内置的 Web 服务器，默认配置如下：

- 默认端口：8080
- 上下文路径：/

可以在 `application.yml` 中修改配置：

```yaml
server:
  port: 8080
  context-path: /
```

### 2. FreeMarker 配置

FreeMarker 是默认的模板引擎，默认配置如下：

- 模板路径：/WEB-INF/templates/
- 模板后缀：.html

可以在 `application.yml` 中修改配置：

```yaml
spring:
  freemarker:
    template-loader-path: /WEB-INF/templates/
    suffix: .html
```

### 3. 日志配置

框架使用 Logback 作为日志系统，可以在 `src/main/resources` 目录中创建 `logback.xml` 文件进行配置：

```xml
<configuration>
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>
    
    <root level="info">
        <appender-ref ref="CONSOLE" />
    </root>
</configuration>
```

## 项目结构

### 框架结构

```
summer-framework/
├── framework/
│   ├── summer-aop/         # AOP 支持
│   ├── summer-boot/        # 应用启动
│   ├── summer-context/     # IoC 容器
│   ├── summer-jdbc/        # 数据库访问
│   ├── summer-web/         # Web MVC
│   └── summer-parent/      # 父 POM
├── step-by-step/           # 分步示例
├── test-app/               # 测试应用
└── README.md               # 项目说明
```

### 测试应用结构

```
test-app/
├── pom.xml                 # Maven 配置
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── test/
        │           ├── AppConfig.java        # 应用配置
        │           ├── Application.java      # 启动类
        │           └── controller/
        │               └── HelloController.java  # 控制器
        └── resources/
            └── WEB-INF/
                └── templates/
                    └── hello.html           # 模板文件
```

## 总结

Summer Framework 是一个轻量级的 Java 框架，提供了类似 Spring 的核心功能，但设计更加简洁。通过本文档的说明，您可以在本地环境中成功构建和运行 Summer Framework 应用。

如果您遇到任何问题，请参考本文档中的解决方案部分，或查看 [Chinese Tutorial](https://liaoxuefeng.com/books/summerframework/) 获取更详细的教程。