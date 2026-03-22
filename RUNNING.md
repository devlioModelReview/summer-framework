# Summer Framework 运行说明

## 项目结构

Summer Framework 是一个轻量级的Java框架，类似于Spring Framework，提供了依赖注入、AOP、数据库访问和Web MVC等功能。框架由以下核心模块组成：

- **summer-context**: 核心IoC容器，支持基于注解的依赖注入
- **summer-aop**: AOP支持，基于注解的子类代理
- **summer-jdbc**: 提供JdbcTemplate和声明式事务管理
- **summer-web**: 支持基于Servlet 6.0的Web应用
- **summer-boot**: 简化Summer应用的启动和运行

## 构建框架

### 前提条件

- JDK 21 或更高版本
- Maven 3.6 或更高版本

### 构建步骤

1. 克隆项目到本地：
   ```bash
   git clone https://github.com/michaelliao/summer-framework.git
   cd summer-framework
   ```

2. 构建框架：
   ```bash
   mvn clean install -DskipTests
   ```

   这将构建所有模块并安装到本地Maven仓库。

## 创建测试应用

### 项目结构

创建一个简单的测试应用，目录结构如下：

```
test-app/
├── pom.xml
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── test/
        │           ├── AppConfig.java
        │           ├── Application.java
        │           └── controller/
        │               └── HelloController.java
        └── resources/
            └── WEB-INF/
                └── templates/
                    └── hello.html
```

### 配置文件

#### pom.xml

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <groupId>com.test</groupId>
    <artifactId>test-app</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <packaging>jar</packaging>
    
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
    </properties>
    
    <dependencies>
        <!-- Summer Framework dependencies -->
        <dependency>
            <groupId>com.itranswarp.summer</groupId>
            <artifactId>summer-boot</artifactId>
            <version>1.0.6</version>
        </dependency>
        <dependency>
            <groupId>com.itranswarp.summer</groupId>
            <artifactId>summer-web</artifactId>
            <version>1.0.6</version>
        </dependency>
        <dependency>
            <groupId>com.itranswarp.summer</groupId>
            <artifactId>summer-context</artifactId>
            <version>1.0.6</version>
        </dependency>
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>1.4.12</version>
        </dependency>
    </dependencies>
    
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.10.1</version>
                <configuration>
                    <source>21</source>
                    <target>21</target>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

#### AppConfig.java

```java
package com.test;

import com.itranswarp.summer.annotation.ComponentScan;
import com.itranswarp.summer.annotation.Configuration;

@Configuration
@ComponentScan("com.test")
public class AppConfig {
}
```

#### Application.java

```java
package com.test;

import com.itranswarp.summer.boot.SummerApplication;

public class Application {
    public static void main(String[] args) throws Exception {
        SummerApplication.run(
            "src/main/resources",
            "target/classes",
            AppConfig.class,
            args
        );
    }
}
```

#### HelloController.java

```java
package com.test.controller;

import com.itranswarp.summer.annotation.Controller;
import com.itranswarp.summer.annotation.GetMapping;
import com.itranswarp.summer.annotation.RequestParam;
import com.itranswarp.summer.web.ModelAndView;

@Controller
public class HelloController {
    
    @GetMapping("/hello")
    public ModelAndView hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        ModelAndView mv = new ModelAndView("hello");
        mv.addObject("name", name);
        return mv;
    }
    
    @GetMapping("/api/hello")
    public String apiHello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "{\"message\": \"Hello, \" + name + \"!\"}";
    }
}
```

#### hello.html

```html
<!DOCTYPE html>
<html>
<head>
    <title>Hello</title>
</head>
<body>
    <h1>Hello, ${name}!</h1>
    <p>Welcome to Summer Framework Test Application</p>
</body>
</html>
```

## 运行应用

### 编译应用

```bash
cd test-app
mvn compile
```

### 运行应用

```bash
mvn exec:java -Dexec.mainClass="com.test.Application"
```

应用将在 http://localhost:8080 启动。

### 测试应用

1. 访问 http://localhost:8080/hello 查看HTML页面
2. 访问 http://localhost:8080/api/hello 查看JSON响应
3. 访问 http://localhost:8080/hello?name=Summer 测试参数传递

## 中间件配置

Summer Framework 内置了以下中间件：

1. **Tomcat**：内置的Web服务器，用于处理HTTP请求
2. **FreeMarker**：模板引擎，用于渲染HTML页面
3. **Jackson**：JSON解析库，用于处理JSON请求和响应

### 配置文件

可以在 `src/main/resources` 目录下创建 `application.yml` 或 `application.properties` 文件来配置应用：

#### application.yml

```yaml
server:
  port: 8080

spring:
  freemarker:
    template-loader-path: /WEB-INF/templates/
    suffix: .html
```

#### application.properties

```properties
server.port=8080
spring.freemarker.template-loader-path=/WEB-INF/templates/
spring.freemarker.suffix=.html
```

## 依赖项

Summer Framework 依赖以下库：

| 依赖项 | 版本 | 用途 |
|-------|------|------|
| byte-buddy | 1.14.2 | 用于AOP代理生成 |
| jackson-databind | 2.14.2 | JSON解析 |
| jakarta.servlet-api | 6.0.0 | Servlet API |
| HikariCP | 5.0.1 | 数据库连接池 |
| logback-classic | 1.4.12 | 日志记录 |
| slf4j-api | 2.0.7 | 日志接口 |
| freemarker | 2.3.32 | 模板引擎 |
| tomcat-embed-core | 10.1.47 | 内嵌Tomcat |
| sqlite-jdbc | 3.41.2.2 | 测试用数据库 |

## 总结

Summer Framework 是一个轻量级的Java框架，提供了类似Spring的核心功能，但设计更加简洁。通过本文档的说明，您可以快速构建和运行一个基于Summer Framework的Web应用。

如果您需要更详细的教程，请参考 [Chinese Tutorial](https://liaoxuefeng.com/books/summerframework/)。