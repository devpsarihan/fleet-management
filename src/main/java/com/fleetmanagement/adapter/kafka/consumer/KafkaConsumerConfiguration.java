package com.fleetmanagement.adapter.kafka.consumer;

import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;

import com.fleetmanagement.adapter.kafka.model.dto.ShipmentKafkaDto;
import com.fleetmanagement.adapter.kafka.model.property.KafkaProperty;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfiguration {

    private final KafkaProperty kafkaProperty;

    @Bean
    public ConsumerFactory<String, ShipmentKafkaDto> consumerFactory() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(BOOTSTRAP_SERVERS_CONFIG, kafkaProperty.getBootstrapServer());
        properties.put(GROUP_ID_CONFIG, kafkaProperty.getConsumerGroup());
        properties.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        properties.put(ENABLE_AUTO_COMMIT_CONFIG, kafkaProperty.isEnableAutoCommit());
        return new DefaultKafkaConsumerFactory<>(properties,
            new StringDeserializer(), new JsonDeserializer<>(ShipmentKafkaDto.class));
    }

    @Bean("concurrentKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, ShipmentKafkaDto> concurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ShipmentKafkaDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

}
