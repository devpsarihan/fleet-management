package com.fleetmanagement.adapter.kafka.consumer;

import com.fleetmanagement.application.usecases.BagService;
import com.fleetmanagement.adapter.kafka.model.dto.ShipmentKafkaDto;
import com.fleetmanagement.application.usecases.PackageService;
import com.fleetmanagement.domain.model.enumerated.DeliveryType;
import com.fleetmanagement.domain.model.enumerated.ShipmentState;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class KafkaConsumerImpl implements KafkaConsumer {

    private static Logger logger = LoggerFactory.getLogger(KafkaConsumerImpl.class);

    private final BagService bagService;
    private final PackageService packageService;

    @KafkaListener(containerFactory = "concurrentKafkaListenerContainerFactory", concurrency = "3",
        topics = "${kafka.fleet-management.topic-name}")
    @Override
    public void consume(ConsumerRecord<String, ShipmentKafkaDto> consumerRecord) {
        ShipmentKafkaDto shipmentKafkaDto = consumerRecord.value();

        List<Long> ids = shipmentKafkaDto.getIds();
        if(DeliveryType.BAG.equals(shipmentKafkaDto.getType())){
            unloadPackagesIntoBag(ids);
            logger.info("Consumed to unload packages into bag");
        } else {
            unloadBags(ids);
            logger.info("Consumed to unload bags by unloaded packages");
        }
    }

    private void unloadPackagesIntoBag(List<Long> bagIds){
        packageService.updatePackagesStateByBagIds(ShipmentState.UNLOADED, bagIds);
    }

    private void unloadBags(List<Long> packageIds) {
        List<Long> bagIds = packageService.getBagIdsByPackageIds(packageIds);
        bagService.updateBagsState(ShipmentState.UNLOADED, bagIds);
    }
}
