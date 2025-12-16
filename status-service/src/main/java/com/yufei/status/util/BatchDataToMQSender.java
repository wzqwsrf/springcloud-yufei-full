//package com.yufei.status.util;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.yufei.status.entity.StatusEntity;
//import com.yufei.status.mapper.StatusMapper;
//import com.yufei.status.mq.StatusEventProducer;
//import jakarta.annotation.Resource;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// * 批量发送数据库数据到MQ的工具类
// * @author wangzhenqing
// * @date 2025/12/01 18:30
// * @description
// */
////@Component
////@Slf4j
//public class BatchDataToMQSender implements CommandLineRunner {
//
//    @Resource
//    private StatusMapper statusMapper;
//
//    @Resource
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//    @Resource
//    private ObjectMapper objectMapper;
//
//    private static final String TOPIC = "status-created-topic";
//    private static final int BATCH_SIZE = 1000; // 每批处理1000条数据
//    private static final int THREAD_POOL_SIZE = 5; // 线程池大小
//
//    @Override
//    public void run(String... args) throws Exception {
//        log.info("开始批量发送数据到MQ...");
//
//        // 获取总数据量
//        Long totalCount = statusMapper.selectCount(null);
//        log.info("数据库中共有 {} 条数据", totalCount);
//
//        // 创建线程池
//        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
//
//        // 计数器
//        AtomicInteger successCount = new AtomicInteger(0);
//        AtomicInteger failCount = new AtomicInteger(0);
//
//        // 分批处理数据
//        int totalPages = (int) Math.ceil((double) totalCount / BATCH_SIZE);
//
//        for (int page = 0; page < totalPages; page++) {
//            final int currentPage = page;
//
//            CompletableFuture.runAsync(() -> {
//                try {
//                    // 分页查询数据
//                    int offset = currentPage * BATCH_SIZE;
//                    List<StatusEntity> statusList = statusMapper.selectList(null)
//                            .stream()
//                            .skip(offset)
//                            .limit(BATCH_SIZE)
//                            .toList();
//
//                    log.info("处理第 {} 批数据，共 {} 条", currentPage + 1, statusList.size());
//
//                    // 发送数据到MQ
//                    for (StatusEntity status : statusList) {
//                        try {
//                            String jsonMessage = objectMapper.writeValueAsString(status);
//
//                            kafkaTemplate.send(TOPIC, jsonMessage).whenComplete(
//                                    (result, ex) -> {
//                                        if (ex != null) {
//                                            log.error("发送失败：statusId={}, 原因={}", status.getId(), ex.getMessage());
//                                            failCount.incrementAndGet();
//                                        } else {
//                                            successCount.incrementAndGet();
//                                        }
//                                    });
//                        } catch (JsonProcessingException e) {
//                            log.error("序列化消息失败：statusId={}, 原因={}", status.getId(), e.getMessage());
//                            failCount.incrementAndGet();
//                        }
//                    }
//
//                    // 每批处理完成后暂停一下，避免给MQ造成太大压力
//                    Thread.sleep(100);
//                } catch (Exception e) {
//                    log.error("处理第 {} 批数据失败", currentPage + 1, e);
//                }
//            }, executor);
//        }
//
//        // 关闭线程池
//        executor.shutdown();
//
//        // 等待所有任务完成
//        while (!executor.isTerminated()) {
//            Thread.sleep(1000);
//        }
//
//        log.info("批量发送完成，成功：{}，失败：{}", successCount.get(), failCount.get());
//    }
//}
