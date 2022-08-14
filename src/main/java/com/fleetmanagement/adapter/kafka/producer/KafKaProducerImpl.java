package com.fleetmanagement.adapter.kafka.producer;

import com.fleetmanagement.adapter.kafka.model.dto.ShipmentKafkaDto;
import com.fleetmanagement.adapter.kafka.model.property.KafkaProperty;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafKaProducerImpl implements KafKaProducer {

    private static Logger logger = LoggerFactory.getLogger(KafKaProducerImpl.class);

    private final KafkaTemplate<String, ShipmentKafkaDto> kafkaTemplate;
    private final KafkaProperty kafkaProperty;

    @Override
    public void send(ShipmentKafkaDto shipmentKafkaDto) {
        kafkaTemplate.send(kafkaProperty.getTopicName(), shipmentKafkaDto);
        logger.info("Shipments are send");
    }
}
