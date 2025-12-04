# ? Spring Cloud 2023.x 微服务治理 社区内容与搜索Demo

这是一个基于 **Spring Boot 3.3.x** 和 **JDK 17** 的最小可运行 Spring Cloud 2023.x 服务治理项目，展示了微服务组件的集成与协作。
希望能做一个完整的社区内容与搜索微服务示例，帮助大家理解 Spring Cloud 在实际项目中的应用。
---

## 核心服务组件

该 Demo 模拟了一个包含社区内容和搜索功能的微服务系统，包括以下 **5 个核心服务**：

* **config-server** (`8888`): **配置中心**，采用本地 native 模式，用于集中管理所有客户端的配置。
* **service-a** (`8080`): **网关/调用端模拟服务**，负责读取配置并调用 `service-b`。
* **service-b** (`8081`): **基础被调用服务**，提供基础 API。
* **status-service** (`8082`): **社区内容服务**，处理用户状态（Status/Post）的创建、更新与存储（如通过 Kafka/DB）。
* **search-service** (`8083`): **搜索服务**，针对 `status-service` 的内容提供高性能的搜索、索引和聚合功能。

---

## 关键技术亮点

* **服务调用:** 使用 **Spring Cloud OpenFeign** 进行声明式 REST 调用。
* **弹性与容错:** 集成 **Resilience4j** 实现服务的熔断 (Circuit Breaker)、限流和降级。
* **配置管理:** 使用 **Config Server** 实现集中式配置。
* **动态刷新:** 配置动态刷新示例（`@RefreshScope` + `/actuator/refresh`）。

---

## 项目结构

---

## 启动顺序 (推荐)

为了确保服务依赖关系正确，请按以下顺序启动应用：

1. 启动 **Config Server**（默认端口 `8888`）
2. 启动 **Eureka Server**（默认端口 `8761`）
3. 启动 **Gateway**（默认端口 `8080`）
4. 启动 **status-service**（端口 `8081`）
5. 启动 **search-service**（端口 `8082`）


---

## ?? 配置说明

* **Config Server** 默认使用 **native** 模式，本地读取 `classpath:/config` 目录下的配置文件。
* 所有客户端服务启动时，会请求 Config Server 的 URL：`http://localhost:8888/application/default`

---

## 依赖版本

| 组件            | 版本       | 备注 |
|:--------------|:---------| :--- |
| Java Versio   | JDK 17   | 长期支持版本 (LTS) |
| Spring Boot   | 3.3.6    | |
| Spring Cloud  | 2023.0.4 | |
| Resilience4j  | 2.1.0    | |
| ElasticSearch | 8.12.2   | |
 | MySQL         | 8.0      | |

---

## 参考资料

* [Spring Cloud Config 官方文档](https://docs.spring.io/spring-cloud-config/docs/current/reference/html/)
* [Resilience4j 官方文档](https://resilience4j.readme.io/)
* [Spring Cloud OpenFeign 官方文档](https://docs.spring.io/spring-cloud-openfeign/docs/current/reference/html/)

## 其他

* 写内容
```bash
  curl -X POST "http://localhost:8081/status/create" \
  -H "Content-Type: application/json" \
  -d '{
  "userId": 4,
  "title": "我的第二条状态",
  "content": "Hello，测试一下Kafka"
  }'
```  
* 创建topic
```bash
  docker exec kafka \
  kafka-topics --create \
  --topic status-created-topic \
  --bootstrap-server localhost:9092 \
  --partitions 3 \
  --replication-factor 1
```