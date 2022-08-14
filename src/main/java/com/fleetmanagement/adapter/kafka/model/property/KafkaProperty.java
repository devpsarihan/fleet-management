package com.fleetmanagement.adapter.kafka.model.property;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public final class KafkaProperty {

    @Value("${kafka.fleet-management.bootstrap-server}")
    private String bootstrapServer;

    @Value("${kafka.fleet-management.ack}")
    private String ack;

    @Value("${kafka.fleet-management.topic-name}")
    private String topicName;

    @Value("${kafka.fleet-management.consumer-group}")
    private String consumerGroup;

    @Value("${kafka.fleet-management.messageSize}")
    private String messageSize;

    @Value("${kafka.fleet-management.concurrency}")
    private String concurrency;

    @Value("${kafka.fleet-management.enable-auto-commit}")
    private boolean enableAutoCommit;

}
