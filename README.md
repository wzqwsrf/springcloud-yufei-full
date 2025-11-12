# Spring Cloud 2023.x Demo

这是一个最小可运行的 Spring Cloud 2023.x 服务治理 Demo，包括：

- **Config Server**（本地 native 模式）
- **service-a**（客户端，读取配置、调用 service-b）
- **service-b**（被调用服务）
- **Feign 调用 + Resilience4j 熔断降级**
- 配置动态刷新示例（@RefreshScope + /actuator/refresh）

---

## ? 项目结构


---

## ? 启动顺序

1. 启动 **Config Server**（默认端口 `8888`）
2. 启动 **service-b**（默认端口 `8081`）
3. 启动 **service-a**（默认端口 `8080`）

---

## ? 配置说明

- Config Server 默认使用 **native** 模式，本地读取 `classpath:/config`
- service-a 请求 Config Server 的 URL：


? 依赖版本

Spring Boot: 3.3.2
Spring Cloud: 2023.0.4
Resilience4j: 2.1.0

? 参考
Spring Cloud Config 官方文档
Resilience4j 官方文档
Spring Cloud OpenFeign
