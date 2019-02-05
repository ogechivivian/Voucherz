package com.iswAcademy.Voucherz.Producer;

import com.iswAcademy.Voucherz.VoucherzApplication;
import com.iswAcademy.Voucherz.event.AuditMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditProducer {
    private static final Logger log = LoggerFactory.getLogger(AuditProducer.class);

    private final RabbitTemplate rabbitTemplate;
    public AuditProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendAudit(AuditMessage message ){
        log.info("Sending Audit...");
        System.out.println(message);
        rabbitTemplate.convertAndSend(VoucherzApplication.EXCHANGE_NAME,VoucherzApplication.ROUTING_KEY, message);

    }
}
