package com.yufei.status.mq;

import com.yufei.status.domain.StatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author wangzhenqing
 * @date 2025/12/01 12:43
 * @description
 */
@Component
@RequiredArgsConstructor
public class StatusEventProducer {

    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendStatusCreatedEvent(StatusDto status) {
        kafkaTemplate.send("status-index", "");
    }
}
