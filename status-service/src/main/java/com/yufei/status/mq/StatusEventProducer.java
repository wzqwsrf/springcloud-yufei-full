package com.yufei.status.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yufei.status.domain.StatusDto;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author wangzhenqing
 * @date 2025/12/01 12:43
 * @description
 */
@Component
@Slf4j
public class StatusEventProducer {

    private static final String topic = "status-created-topic";

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;
    @Resource
    private ObjectMapper objectMapper;

    public void sendStatusCreatedEvent(StatusDto status) {
        String jsonMessage = null;
        try {
            jsonMessage = objectMapper.writeValueAsString(status);
        } catch (JsonProcessingException e) {
            log.error("StatusEventProducer sendStatusCreatedEvent 序列化消息失败: {}", e.getMessage());
        }
        String finalJsonMessage = jsonMessage;
        kafkaTemplate.send(topic, jsonMessage).whenComplete(
                // 发送成功回调
                (result, ex) -> {
                    if (ex != null) {
                        log.info("Producer 发送失败：message=" + finalJsonMessage + ", 原因=" + ex.getMessage());
                    } else {
                        RecordMetadata metadata = result.getRecordMetadata();
                        log.info("Producer 发送成功：topic=" + metadata.topic() + ", offset=" + metadata.offset());
                        // 可记录发送成功指标（如计数器 +1）
                    }
                });
    }

}
