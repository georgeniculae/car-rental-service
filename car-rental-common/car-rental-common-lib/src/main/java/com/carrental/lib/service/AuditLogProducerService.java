package com.carrental.lib.service;

import com.carrental.dto.AuditLogInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "audit", name = "enabled")
@Slf4j
public class AuditLogProducerService {

    @Value("${kafka.audit-log-topic-name}")
    private String topic;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendAuditLog(AuditLogInfoDto auditLogInfo) {
        kafkaTemplate.send(buildMessage(auditLogInfo, topic))
                .whenComplete((result, e) -> {
                    if (ObjectUtils.isEmpty(e)) {
                        log.info("Sent message=[" + auditLogInfo + "] with offset=["
                                + result.getRecordMetadata().offset() + "]");

                        return;
                    }

                    log.error("Unable to send message=[" + auditLogInfo + "] due to : " + e.getMessage());
                });
    }

    private Message<AuditLogInfoDto> buildMessage(AuditLogInfoDto auditLogInfoDto, String topicName) {
        return MessageBuilder.withPayload(auditLogInfoDto)
                .setHeader(KafkaHeaders.TOPIC, topicName)
                .build();
    }

}
